let timeRemaining;
let quizId = 0;
const answers = {};


function setState(data){
    let lastEndDate = new Date(sessionStorage.getItem('endTime'));
    quizId = data.quizId;

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
    }else {
        timeRemaining = lastEndDate-(new Date());
        for (let i = 0; i < document.querySelectorAll('.question-box').length; i++) {
            document.querySelectorAll('.question-box')[i].innerHTML = sessionStorage.getItem('quest'+i);
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
            setState(data);
            answerListeners();
            updateCountdown(); // Update countdown initially
            countdownInterval = setInterval(updateCountdown, 1000); // Start countdown
        })
        .catch(function(error) {
            console.error('There was a problem with fetch operation:', error.message);
        });
}

// Fetch quiz attributes on page load
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

// Event listener for submit quiz button
document.getElementById('submitQuiz').addEventListener('click', function() {
    clearInterval(countdownInterval);
    finish();
});

window.addEventListener('beforeunload', function() {
    for (let i = 0; i < document.querySelectorAll('.question-box').length; i++) {
        sessionStorage.setItem('quest'+i, document.querySelectorAll('.question-box')[i].innerHTML);
    }
});

function detectDivsCallingSpecificMethod() {
    const divs = document.querySelectorAll('.answer_radio');
    let used = new Map();
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
        console.log(div.getAttribute('name'));
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
