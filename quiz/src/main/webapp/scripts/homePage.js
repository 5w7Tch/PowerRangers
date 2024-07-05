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

    if (showNotifications) {
        // fetch('/notifications?userId=1') // Assuming userId is 1
        //     .then(response => response.json())
        //     .then(data => {
        //         notificationList.innerHTML = '';
        //         data.forEach(notification => {
        //             let item = document.createElement('a');
        //             item.href = '#';
        //             item.className = 'dropdown-item';
        //             item.textContent = notification.message;
        //             notificationList.appendChild(item);
        //         });
        //     });
    }
}