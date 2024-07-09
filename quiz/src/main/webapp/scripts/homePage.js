const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
let notificationsButton = document.getElementById('notificationsButton');
notificationsButton.addEventListener("click", onNotifications);
let showNotifications = false;
let showAppSummary = false;

function onNotifications() {
    showAppSummary = false;
    let appSummaryList = document.getElementById('appSummaryList');
    if(appSummaryList != null){
        appSummaryList.style.display = 'none';
        appSummaryButton.classList.add('btn-secondary');
        appSummaryButton.classList.remove('btn-primary');
    }

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

let appSummaryButton = document.getElementById('appSummaryButton');
if($('#appSummaryButton').length!=0){
    appSummaryButton.addEventListener("click", onAppSummary);
}
function onAppSummary() {
    showNotifications = false;
    let notificationList = document.getElementById('notificationsList');
    notificationList.style.display = 'none';
    notificationsButton.classList.add('btn-secondary');
    notificationsButton.classList.remove('btn-primary');

    let appSummaryList = document.getElementById('appSummaryList');
    if(showAppSummary) {
        appSummaryList.style.display = 'none';
        appSummaryButton.classList.add('btn-secondary');
        appSummaryButton.classList.remove('btn-primary');
    } else {
        appSummaryList.style.display = 'block';
        appSummaryButton.classList.remove('btn-secondary');
        appSummaryButton.classList.add('btn-primary');
    }
    showAppSummary = !showAppSummary;
}

$(document).ready(function (){
    $('#createQuizBtn').click(function (){
        console.log("hello");
        window.location.href = "/createQuiz";
    })

    $('#announceBtn').click(function (){
        let text = $('#announcementText').val();
        if(text === ''){
            alert("Fill all fields");
            return;
        }

        let announcementJson = {
            'text' : text
        }

        $.ajax({
            url: '/announce',
            type: 'post',
            data: JSON.stringify(announcementJson),
            contentType: 'application/json; charset=UTF-8',
            beforeSend: function (xhr){
                $('#announceBtn').attr("disabled",true);
                $('#announceCloseBtn').attr("disabled",true);
                $('#announceBtn').text('uploading...');
            },
            success: function (result,status,xhr){
                window.location.reload();
            },
            error: function (xhr,status,error){
                alert("couldn't announce");
                $('#announceCloseBtn').attr("disabled",false);
                $('#announceCloseBtn').click();
            }
        })
    })
})