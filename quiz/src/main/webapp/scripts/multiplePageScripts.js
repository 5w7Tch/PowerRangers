// let quiz = document.getElementById('quizContent').innerHTML;
let questions = document.querySelectorAll('.question-box');
let timeRemaining;
let quizId = 0;
let currentQuestionIndex = 0;
let countdownInterval;
const answers = {};
let dontUse = new Map();
function setState(data){
    let lastEndDate = new Date(sessionStorage.getItem('endTime'));
    quizId = data.quizId;
    if(data.practise == 'off'){

        if(lastEndDate == null){
            sessionStorage.clear();
            timeRemaining = data.quizTime * 60000;
            sessionStorage.setItem('endTime', new Date(data.endDate));
            sessionStorage.setItem('quizId', quizId);
        }else if(lastEndDate<(new Date(data.startDate))){
            sessionStorage.clear();
            timeRemaining = data.quizTime * 60000;
            sessionStorage.setItem('endTime', new Date(data.endDate));
            sessionStorage.setItem('quizId', quizId);
        }else if(sessionStorage.getItem('quizId').toString() != quizId){
            sessionStorage.clear();
            timeRemaining = data.quizTime * 60000;
            sessionStorage.setItem('endTime', new Date(data.endDate));
            sessionStorage.setItem('quizId', quizId);
        }else{
            timeRemaining = lastEndDate-(new Date());
            if(sessionStorage.getItem('currentQuestion') != null){
                currentQuestionIndex = Number.parseInt(sessionStorage.getItem('currentQuestion'));
            }
            for (let i = 0; i < questions.length; i++) {
                questions[i].innerHTML = sessionStorage.getItem('quest'+i);
            }
        }
    }else{
        let prevLvl = {};
        if(sessionStorage.getItem('practise') == null){
            sessionStorage.setItem('practise', data.practise);
            for (const answersKey in answers) {
                prevLvl[answersKey] = 0;
            }
            sessionStorage.setItem("prevLvL", JSON.stringify(prevLvl));
            for (let i = 0; i < document.querySelectorAll('.question-box').length; i++) {
                sessionStorage.setItem('quest'+i, document.querySelectorAll('.question-box')[i].innerHTML);
            }
        }
    }
}


// Function to fetch quiz attributes and initialize timer
function fetchQuizAttribute() {
    fetch('/getQuizSessionAttribute', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(function(data) {
            answerListeners();
            setState(data);
            loadFirstQuest();
            if(data.practise == 'off'){
                answerListeners();
                updateCountdown();
                countdownInterval = setInterval(updateCountdown, 1000);
            }
        })
        .catch(function(error) {
            console.error('There was a problem with fetch operation:', error.message);
        });
}


fetchQuizAttribute();

// Function to update countdown timer
function updateCountdown() {
    if (timeRemaining <= 0) {
        document.getElementById('time').textContent = '00:00:00';
        showTimeoutMessage();
    } else {
        let hours = Math.floor(timeRemaining / 3600000);
        let minutes = Math.floor(timeRemaining / 60000);
        let seconds = Math.floor((timeRemaining % 60000) / 1000); // Corrected calculation
        document.getElementById('time').textContent = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
        timeRemaining -= 1000;
        // Store timeRemaining in sessionStorage to persist across page reloads
    }
}

// Function to show timeout message
function showTimeoutMessage() {
    clearInterval(countdownInterval);
    sessionStorage.clear();
    window.location.replace('/timeOut');
}



function finish() {

    fetch('/finished', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },

        body: JSON.stringify(answers)

    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(function(data) {
            if(data.bad == 1){
                sessionStorage.clear();
                window.location.replace('/timeOut');
            }else{
                sessionStorage.clear();
                window.location.replace('/success');
            }
        })
        .catch((error) => {
            console.error('There was a problem with fetch operation:', error.message);
        });
}

function next() {

    fetch('/practise', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(answers)
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(function(data) {
            let last = JSON.parse(sessionStorage.getItem('prevLvL').toString());
            for (const ansKey in answers){
                if(data[ansKey] == '1'){
                    last[ansKey] =  Number(last[ansKey])+1;
                    if(last[ansKey] == '3'){
                        dontUse.set(ansKey,ansKey);
                    }
                }else{
                    last[ansKey] = 0;
                }
            }
            sessionStorage.setItem('prevLvL', JSON.stringify(last));
            if(dontUse.size == Object.keys(answers).length){
                let url = '/quiz?quizid='+quizId+'&practise=done';

                window.location.replace(url);
            }
            for (let i = 0; i < questions.length; i++) {
                questions[i].innerHTML = sessionStorage.getItem('quest'+i);
            }
            answerListeners();
            showQuestion(0);
        })
        .catch((error) => {
            console.error('There was a problem with fetch operation:', error.message);
        });
}




document.getElementById('submitQuiz').addEventListener('click', function() {
    clearInterval(countdownInterval);
    if(sessionStorage.getItem('practise') == null){
        finish();
    }else{
        next();
    }
});

window.addEventListener('beforeunload', function() {
    for (let i = 0; i < document.querySelectorAll('.question-box').length; i++) {
        sessionStorage.setItem('quest'+i, document.querySelectorAll('.question-box')[i].innerHTML);
    }
    if(sessionStorage.getItem('practise') == 'on'){
        sessionStorage.clear();
    }
});


function loadFirstQuest() {
    questions[currentQuestionIndex].style.display = 'block';
    questions[currentQuestionIndex].classList.add('slide-in', 'active');
}


function showQuestion(index) {
    while (dontUse.has(index+'')){
        index++;
    }
    questions[currentQuestionIndex].classList.remove('active');
    questions[currentQuestionIndex].classList.add('fade-out');

    setTimeout(() => {
        questions[currentQuestionIndex].classList.remove('fade-out');
        questions[currentQuestionIndex].style.display = 'none';

        currentQuestionIndex = index;
        sessionStorage.setItem('currentQuestion',currentQuestionIndex);
        questions[currentQuestionIndex].style.display = 'block';
        questions[currentQuestionIndex].classList.add('slide-in', 'active');

        setTimeout(() => {
            questions[currentQuestionIndex].classList.remove('slide-in');
        }, 500);
    }, 500);
}
function parseJson(arr){
    let i = currentQuestionIndex;
    let res = '{';
    for (const arrKey in arr) {
        let id = ''+i;
        res+= id + ':'+ arr[arrKey]+',';
        i++;
    }
    if(res.length !== 1){
        res = res.substring(0, res.length-1);
    }else{
        res+=''+currentQuestionIndex+':';
    }
    res+='}';

    return res;
}

function ckeckout(index) {
    let result;

    fetch('/checkAnswer', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(parseJson(answers[currentQuestionIndex]))
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(function(data) {
            result = data.res
            if (result == 0) {
                questions[index].style.backgroundColor = 'red';
            } else if(result == 2){
                questions[index].style.backgroundColor = 'green';
            }else{
                questions[index].style.backgroundColor = 'yellow';
            }
        })
        .catch((error) => {
            console.error('There was a problem with fetch operation:', error.message);
        });

    return result;
}



document.getElementById('next').addEventListener('click', function() {
    ckeckout(currentQuestionIndex);
    if (currentQuestionIndex < questions.length - 1) {
        setTimeout(() => {
            showQuestion(currentQuestionIndex + 1);
        }, 500);
    } else {
        setTimeout(() => {
            if(sessionStorage.getItem('practise') == null){
                finish();
            }else{
                next();
            }
        }, 500);
    }
});

document.getElementById('nextButton').addEventListener('click', function() {
    if(sessionStorage.getItem('practise') == 'on'){
        let plus = 1;
        while(dontUse.has(currentQuestionIndex+plus+'')){
            plus++;
        }
        if (currentQuestionIndex + plus < questions.length) {
            showQuestion(currentQuestionIndex + plus);
        }
    }else{
        if (currentQuestionIndex < questions.length - 1) {
            showQuestion(currentQuestionIndex + 1);
        }
    }
});

document.getElementById('prevButton').addEventListener('click', function() {
    if(sessionStorage.getItem('practise') == 'on'){
        let plus = 1;
        while(dontUse.has(currentQuestionIndex-plus+'')){
            plus++;
        }
        if (currentQuestionIndex - plus >= 0) {
            showQuestion(currentQuestionIndex - plus);
        }
    }else{
        if (currentQuestionIndex > 0) {
            showQuestion(currentQuestionIndex - 1);
        }
    }

});

function detectDivsCallingSpecificMethod() {
    const divs = document.querySelectorAll('.answer_radio');
    divs.forEach(div => {
        const onclickAttr = div.getAttribute('onclick');
        if (onclickAttr && onclickAttr.includes('radioChange')) {
            answers[div.getAttribute('name')] = [""];
        }
        if (onclickAttr && onclickAttr.includes('radioChangeMultiple')) {
            const radios = document.getElementsByName(div.getAttribute('name'));
            let arr = new Array(radios.length);
            for (let i = 0; i < radios.length; i++) {
                if (radios[i].style.backgroundColor === 'orange') {
                    arr[i] = radios[i].innerText;
                }else{
                    arr[i] = "";
                }
            }
            answers[div.getAttribute('name')] = arr;

        }
    });
}

function answerListeners() {
    const answerResponseDivs = document.querySelectorAll('.answer_response');

    answerResponseDivs.forEach(div => {
        div.addEventListener('input', function() {
            answerChange(this, this.getAttribute('name'));
        });
        answerChange(div, div.getAttribute('name'));
    });
    detectDivsCallingSpecificMethod();
}

function radioChange(thisObj, name) {
    const radios = document.getElementsByName(name);

    for (let i = 0; i < radios.length; i++) {
        radios[i].style.backgroundColor = 'white';
    }
    thisObj.style.backgroundColor = 'orange';

    for (let i = 0; i < radios.length; i++) {
        if (radios[i].style.backgroundColor === 'orange') {
            answers[name] = [radios[i].innerText];
            break;
        }else{
            answers[name] = [""];
        }
    }
}

function answerChange(thisObj, name) {
    const fillers = document.getElementsByName(name);
    let arr = new Array(fillers.length);

    for (let i = 0; i < fillers.length; i++) {
        arr[i] = fillers[i].innerText.toString().trim();
    }
    answers[name] = arr;
}

function radioChangeMultiple(thisObj, name) {
    if(thisObj.style.backgroundColor.toString() === 'orange'){
        thisObj.style.backgroundColor = 'white'
    }else{
        thisObj.style.backgroundColor = 'orange'
    }
    const radios = document.getElementsByName(name);

    let arr = new Array(radios.length);
    for (let i = 0; i < radios.length; i++) {
        if (radios[i].style.backgroundColor === 'orange') {
            arr[i] = radios[i].innerText;
        }else{
            arr[i] = "";
        }
    }
    answers[name] = arr;

}

