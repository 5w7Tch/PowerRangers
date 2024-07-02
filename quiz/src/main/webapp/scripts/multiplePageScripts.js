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
        currentQuestionIndex = sessionStorage.getItem('currentQuestion');


        questions = sessionStorage.getItem('quiz');
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

        sessionStorage.setItem('quiz', questions);
        sessionStorage.setItem('currentQuestion', currentQuestionIndex);
    }
}

// Function to show timeout message
function showTimeoutMessage() {
    clearInterval(countdownInterval);
    window.location.replace('/timeOut');
}

function finish() {
    clearInterval(countdownInterval);
    // Submit logic TO-DO
    window.location.replace('/finished');
}

// Event listener for submit quiz button
document.getElementById('submitQuiz').addEventListener('click', function() {
    finish();
});




// Load the first question

loadFirstQuest();

function loadFirstQuest() {
    currentQuestionIndex = 0;
    let cur = sessionStorage.getItem('currentQuestion');
    if (cur) {
        currentQuestionIndex = cur;
    }
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
    console.log('next');

});

document.getElementById('prevButton').addEventListener('click', function() {
    if (currentQuestionIndex > 0) {
        showQuestion(currentQuestionIndex - 1);
    }
    console.log('prev');

});
