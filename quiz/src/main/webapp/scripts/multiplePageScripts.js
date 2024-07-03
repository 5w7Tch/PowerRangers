// let quiz = document.getElementById('quizContent').innerHTML;
let questions = document.querySelectorAll('.question-box');
let timeRemaining;
let quizId = 0;
let currentQuestionIndex = 0;
let countdownInterval;

function setState(data){
    let lastEndDate = new Date(sessionStorage.getItem('endTime'));
    if(lastEndDate == null){
        sessionStorage.clear();
        timeRemaining = data.quizTime * 60000;
        sessionStorage.setItem('endTime', new Date(data.endDate));
    }else if(lastEndDate<(new Date(data.startDate))){
        sessionStorage.clear();
        timeRemaining = data.quizTime * 60000;
        sessionStorage.setItem('endTime', new Date(data.endDate));
    }else{
        timeRemaining = lastEndDate-(new Date());
        if(sessionStorage.getItem('currentQuestion') != null){
            currentQuestionIndex = Number.parseInt(sessionStorage.getItem('currentQuestion'));
        }
        for (let i = 0; i < questions.length; i++) {
            questions[i].innerHTML = sessionStorage.getItem('quest'+i);
        }
    }
    quizId = data.quizId;

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
            loadFirstQuest();
            updateCountdown();
            countdownInterval = setInterval(updateCountdown, 1000);
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
    window.location.replace('/timeOut');
}



function finish() {
    clearInterval(countdownInterval);


    fetch('/finished', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ inputs: inputValues })
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(function(data) {
            if(data.bad == 1){
                window.location.replace('/timeOut');
            }else{
                window.location.replace('/success');
            }
        })
        .catch((error) => {
            console.error('There was a problem with fetch operation:', error.message);
        });

}


// Event listener for submit quiz button
document.getElementById('submitQuiz').addEventListener('click', function() {
    finish();
});


window.addEventListener('beforeunload', function() {
    for (let i = 0; i < document.querySelectorAll('.question-box').length; i++) {
        sessionStorage.setItem('quest'+i, document.querySelectorAll('.question-box')[i].innerHTML);
    }
});


function loadFirstQuest() {
    questions[currentQuestionIndex].style.display = 'block';
    questions[currentQuestionIndex].classList.add('slide-in', 'active');
}

function showQuestion(index) {

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

function check(index) {
    // TO-DO: Implement your check logic
    let correct = true;
    if (correct) {
        questions[index].style.backgroundColor = 'red';
    } else {
        questions[index].style.backgroundColor = 'green';
    }
}

document.getElementById('next').addEventListener('click', function() {
    check(currentQuestionIndex);
    if (currentQuestionIndex < questions.length - 1) {
        showQuestion(currentQuestionIndex + 1);
    } else {
        finish();
    }
});

document.getElementById('nextButton').addEventListener('click', function() {
    if (currentQuestionIndex < questions.length - 1) {
        showQuestion(currentQuestionIndex + 1);
    }


});

document.getElementById('prevButton').addEventListener('click', function() {
    if (currentQuestionIndex > 0) {
        showQuestion(currentQuestionIndex - 1);
    }


});

function radioChange(thisObj, name) {
    const radios = document.getElementsByName(name.id);
    for (let i = 0; i < radios.length; i++) {
        radios[i].style.backgroundColor = '#f9f9f9'
    }
    thisObj.style.backgroundColor = "yellow";
}

function questionResponseChange(name) {

}

function fillInChange(name) {

}

function matchChange(name) {

}

function pictureResponseChange(name) {

}

function multiResponseChange(name) {
    
}
