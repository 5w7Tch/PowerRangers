const container = document.getElementById('container');
const registerPage = document.getElementById('register');
const loginPage = document.getElementById('login');
const registerBtn = document.getElementById('SignUp');
const loginBtn = document.getElementById('SignIn');
let charset = "abcdefghijklmnopqrstuvwxyz0123456789.,-!";

registerPage.addEventListener('click', (event) => {
    container.classList.add("active");
});

loginPage.addEventListener('click', () => {
    container.classList.remove("active");
});


document.addEventListener('DOMContentLoaded', function() {
    var fetchStringsBtn = document.getElementById('SignIn');

    fetchStringsBtn.addEventListener('click', function() {
        var username = document.getElementById('signInUsername').value;
        var password = document.getElementById('signInPassword').value;

        var url = '/login?username=' + encodeURIComponent(username) +
            '&password=' + encodeURIComponent(password);
        fetch(url, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
                'Cache-Control': 'no-cache',
                'Pragma': 'no-cache',
                'Expires': '0'
            }
        })
            .then(function(response) {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(function(data) {
                let resCode = data.res;
                signIn(resCode);
            })
            .catch(function(error) {
                console.error('There was a problem with fetch operation:', error.message);
            });
    });
});

document.addEventListener('DOMContentLoaded', function() {
    var fetchStringsBtn = document.getElementById('SignUp');

    fetchStringsBtn.addEventListener('click', function() {
        var username = document.getElementById('signUpUsername').value;
        let email = document.getElementById('signUpEmail').value;
        let password = document.getElementById('signUpPassword').value;

        var url = '/signup?username=' + encodeURIComponent(username)+
                            '&email='+encodeURIComponent(email)+
                            '&password='+encodeURIComponent(password);
        fetch(url, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
                'Cache-Control': 'no-cache',
                'Pragma': 'no-cache',
                'Expires': '0'
            }
        })
            .then(function(response) {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(function(data) {
                console.log(data);
                let resCodeUN = data.usernameRP;
                let resCodeE = data.emailRP;
                let resCodeP = data.passwordRP;
                signUp(resCodeUN, resCodeE, resCodeP);
            })
            .catch(function(error) {
                console.error('There was a problem with fetch operation:', error.message);
            });
    });
});

function signUp(resCodeUN, resCodeE, resCodeP){
    // Get error elements, red sentences
    let emailError = document.getElementById('signUpEmailError');
    let passwordError = document.getElementById('passwordError');
    let usernameError = document.getElementById('signUpUsernameError');

    // Reset error messages
    usernameError.style.visibility = 'hidden';
    emailError.style.visibility = 'hidden';
    passwordError.style.visibility = 'hidden';

    //determines weather to let user register with this info and call post method
    let change = true;

    if(resCodeUN == '0'){
        usernameError.style.visibility = 'visible';
        document.getElementById('signUpUsername').value = '';
        chenge = false;
    }

    if (resCodeE == '0') {
        document.getElementById('signUpEmailError').style.visibility = 'visible';
        document.getElementById('signUpEmail').value = '';
        chenge = false;
    }

    if (resCodeP == '0') {
        document.getElementById('passwordError').style.visibility = 'visible';
        document.getElementById('signUpPassword').value = '';
        chenge = false;
    }
    if(change){
        window.location.href = "/";
    }
}

function signIn(resCode) {

    // Get error elements red words
    let error = document.getElementById('signInError');

    // Reset error messages
    error.style.visibility = 'hidden';

    // checks entered infos validity
    if (resCode === "notFound") {
        document.getElementById('signInUsername').value = '';
        document.getElementById('signInPassword').value = '';
        error.style.visibility = 'visible';
    }else{
        window.location.href = "/";
    }
}

