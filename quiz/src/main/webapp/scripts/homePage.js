const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
let notificationsButton = document.getElementById('notificationsButton');
notificationsButton.addEventListener("click", onNotifications);
let showNotifications = false;
function onNotifications() {
    let notificationList = document.getElementById('notificationsList');
    if(showNotifications) {
        notificationList.style.display = 'none';
        notificationsButton.classList.add('btn-secondary');
        notificationsButton.classList.remove('btn-primary');
    } else {
        notificationList.style.display = 'block';
        notificationsButton.classList.remove('btn-secondary');
        notificationsButton.classList.add('btn-primary');
    }
    showNotifications = !showNotifications;
}

$(document).ready(function (){
    $('#createQuizBtn').click(function (){
        window.location.href = "/createQuiz";
    })
})