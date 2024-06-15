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

        var url = '/fetchStrings?username=' + encodeURIComponent(username) +
            '&password=' + encodeURIComponent(password) +
            '&identifier=signIn';
        fetch(url, {
            method: 'GET',
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

        var url = '/fetchStrings?username=' + encodeURIComponent(username) +
                                        '&identifier=signUp';

        fetch(url, {
            method: 'GET',
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
                signUp(resCode);
            })
            .catch(function(error) {
                console.error('There was a problem with fetch operation:', error.message);
            });
    });
});




function passwordIsValid(password){
    for (let i = 0; i < password.length; i++) {
        if(!charset.includes(password[i]))return false;
    }
    return password.length !== 0;
}

function signUp(resCode){
    let email = document.getElementById('signUpEmail').value;
    let password = document.getElementById('signUpPassword').value;

    // Get error elements, red sentences
    let usernameError = document.getElementById('signUpUsernameError');
    let emailError = document.getElementById('signUpEmailError');
    let passwordError = document.getElementById('passwordError');

    // Reset error messages
    usernameError.style.visibility = 'hidden';
    emailError.style.visibility = 'hidden';
    passwordError.style.visibility = 'hidden';

    //determines weather to let user register with this info and call post method
    let hasError = false;
    if (resCode === 'found') {
        usernameError.style.visibility = 'visible';
        document.getElementById('signUpUsername').value = '';
        hasError = true;
    }

    if (!email.includes('@')) {
        document.getElementById('signUpEmailError').style.visibility = 'visible';
        document.getElementById('signUpEmail').value = '';
        hasError = true;
    }

    if(!passwordIsValid(password)){
        document.getElementById('passwordError').style.visibility = 'visible';
        document.getElementById('signUpPassword').value = '';
        hasError = true;
    }

    if(!hasError){
        sighUpHome();
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
    }else {
        logInHome();
    }
}


var xhr = null;
/*
redirects to servlet that sign ups the account
*/
function sighUpHome() {
    // instantiate  XMLHttpRequest object
    try {
        xhr = new XMLHttpRequest();
    } catch (e) {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }

    // handle old browsers
    if (xhr == null) {
        alert("Ajax not supported by your browser!");
        return;
    }

    // construct URL
    //write servlet end instead of login
    var url = "signup?username=" + document.getElementById("signUpUsername").value+
                        "&password="+document.getElementById("signUpPassword").value+
                            "&email="+document.getElementById("signUpEmail").value;

    // get quote
    xhr.onreadystatechange = handler; // sets 'listener'
    xhr.open("post", url, true); // true == async
    xhr.send(null); // there is no request body request
}

/*
redirects to servlet that logs into account
*/
function logInHome() {
    // instantiate  XMLHttpRequest object
    try {
        xhr = new XMLHttpRequest();
    } catch (e) {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }

    // handle old browsers
    if (xhr == null) {
        alert("Ajax not supported by your browser!");
        return;
    }

    // construct URL
    //write servlet end instead of login
    var url = "login?username=" + document.getElementById("signInUsername").value+
                            "&password="+document.getElementById("signInPassword").value;

    // get quote
    xhr.onreadystatechange = handler; // sets 'listener'
    xhr.open("post", url, true); // true == async
    xhr.send(null); // there is no request body request
}

/**
 * Handles the Ajax response
 */
function handler() {
    // only handle loaded requests
    if (xhr.readyState === 4) {
        if (xhr.status === 200) {
            alert(xhr.responseText);
        } else {
            alert("Error with Ajax call");
        }
    }
}

