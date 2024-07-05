let timeRemaining;
let quizId = 0;
const answers = {};


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
        for (let i = 0; i < document.querySelectorAll('.question-box').length; i++) {
            document.querySelectorAll('.question-box')[i].innerHTML = sessionStorage.getItem('quest'+i);
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


function answerListeners() {
    const answerResponseDivs = document.querySelectorAll('.answer_response');

    answerResponseDivs.forEach(div => {
        div.addEventListener('input', function() {
            answerChange(this, this.getAttribute('name'));
        });
        answerChange(div, div.getAttribute('name'));
    });

}

function radioChange(thisObj, name) {
    const radios = document.getElementsByName(name);

    for (let i = 0; i < radios.length; i++) {
        radios[i].style.backgroundColor = '#f9f9f9';
    }
    thisObj.style.backgroundColor = 'yellow';

    for (let i = 0; i < radios.length; i++) {
        if (radios[i].style.backgroundColor === 'yellow') {
            answers[name] = [radios[i].innerText];
            break;
        }
    }

    console.log(answers);
}

function answerChange(thisObj, name) {
    const fillers = document.getElementsByName(name);
    let arr = new Array(fillers.length);

    for (let i = 0; i < fillers.length; i++) {
        arr[i] = fillers[i].innerText;
    }
    answers[name] = arr;
}
