const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
let notificationsButton = document.getElementById('addFriendButton');


notificationsButton.addEventListener("click", function () {
    let userId = this.getAttribute('name');
    let url = '/sendFriendRequest?receiverId='+ userId;
    fetch(url, {
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
            if(data.res == 1){
                notificationsButton.style.display = "none";
            }else{
                alert("issue detected! Friend request wasn't sent");
            }
        })
        .catch(function(error) {
            console.error('There was a problem with fetch operation:', error.message);
        });
});


let deleteAccountBtn = document.getElementById('deleteAccountButton');
deleteAccountBtn.addEventListener("click", function () {
    let userId = this.getAttribute('name');
    let url = '/deleteAccount?userId='+ userId;
    fetch(url, {
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
            if(data.res == 1){
                window.location.replace('/');
            }else{
                alert("issue detected! Delete Account failed");
            }
        })
        .catch(function(error) {
            console.error('There was a problem with fetch operation:', error.message);
        });
});

let makeAdminButton = document.getElementById('makeAdminButton');
makeAdminButton.addEventListener("click", function () {
    let userId = this.getAttribute('name');
    let url = '/makeAdmin?userId='+ userId;
    fetch(url, {
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
            if(data.res == 1){
                makeAdminButton.style.display = "none";
            }else{
                alert("issue detected! User Can't become Admin");
            }
        })
        .catch(function(error) {
            console.error('There was a problem with fetch operation:', error.message);
        });
});


let leaveNoteButton = document.getElementById('leaveNoteButton');
$('#noteBtn').click(function (){
    let text = $('#noteText').val();
    let userId = $('#noteBtn').attr('name');
    console.log(userId);
    if(text === ''){
        alert("Fill all fields");
        return;
    }

    let noteJson = {
        'text' : text
    }

    $.ajax({
        url: '/sendNote?receiverId='+userId,
        type: 'post',
        data: JSON.stringify(noteJson),
        contentType: 'application/json; charset=UTF-8',
        beforeSend: function (xhr){
            $('#noteBtn').attr("disabled",true);
            $('#noteCloseBtn').attr("disabled",true);
            $('#noteBtn').text('uploading...');
        },
        success: function (result,status,xhr){
            window.location.reload();
        },
        error: function (xhr,status,error){
            alert("couldn't announce");
            $('#noteCloseBtn').attr("disabled",false);
            $('#noteCloseBtn').click();
        }
    })
});

let challengeButton = document.getElementById('challengeButton');
challengeButton.addEventListener("click", function () {
    //todo
});



$(document).ready(function (){
    $('#createQuizBtn').click(function (){
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