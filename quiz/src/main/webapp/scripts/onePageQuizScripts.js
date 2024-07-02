let timeRemaining;
let quizId = 0;

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
            // Check if there is already time remaining stored in sessionStorage
            timeRemaining = sessionStorage.getItem('timeRemaining');
            if (timeRemaining === null) {
                // If not stored, calculate from server data
                timeRemaining = data.quizTime * 60000;
            } else {
                // If stored, parse it as integer
                timeRemaining = parseInt(timeRemaining);
            }
            quizId = data.quizId;
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
        sessionStorage.setItem('timeRemaining', timeRemaining);
    }
}

// Function to show timeout message
function showTimeoutMessage() {
    clearInterval(countdownInterval);
    window.location.replace('/timeOut');
}

// Event listener for submit quiz button
document.getElementById('submitQuiz').addEventListener('click', function() {
    clearInterval(countdownInterval);
    // Submit logic TO-DO
    window.location.replace('/finished');
});


window.addEventListener('beforeunload', function() {
    sessionStorage.removeItem('timeRemaining');
});