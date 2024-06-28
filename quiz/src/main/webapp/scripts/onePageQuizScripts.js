
let timeRemaining ;
let quizId = 0;

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
            timeRemaining = data.quizTime*60000
            quizId = data.quizId;
        })
        .catch(function(error) {
            console.error('There was a problem with fetch operation:', error.message);
        });
}
fetchQuizAttribute();
let countdownInterval = setInterval(updateCountdown, 1000);

function updateCountdown() {
    if (timeRemaining <= 0) {
        document.getElementById('time').textContent = '00:00:00';
        showTimeoutMessage();
    } else {
        let hours = Math.floor(timeRemaining / 3600000);
        let minutes = Math.floor(timeRemaining / 60000);
        let seconds = (timeRemaining-(minutes*60000))/1000;
        document.getElementById('time').textContent = `${hours.toString().padStart(2,'0')}:${minutes.toString().padStart(2,'0')}:${seconds.toString().padStart(2, '0')}`;
        timeRemaining -= 1000;
    }
}

updateCountdown();

function showTimeoutMessage() {
    clearInterval(countdownInterval);
    document.getElementById('quizContent').style.display = 'none';
    document.getElementById('timeoutMessage').style.display = 'block';
}

document.getElementById('retakeQuiz').addEventListener('click', function() {
    window.location.href = '/quiz?quizid='+quizId; //
});

document.getElementById('goToAccount').addEventListener('click', function() {
    window.location.href = '/';
});

document.getElementById('submitQuiz').addEventListener('click', function() {
    clearInterval(countdownInterval);
    //submit logic TO-DO
    window.location.href = '/finished';
});

