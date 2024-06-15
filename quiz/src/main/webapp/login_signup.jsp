<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="./loginStyles.css">
    <title>Qwiztime</title>
    <link rel="icon" href="icons/logo.png" type="image/png">
</head>
<body>
<div class="container" id="container">
    <div class="form-container sign-up">
        <form id="signUpForm">
            <h1>Create Account</h1>
            <input type="text" placeholder="Username" id="signUpUsername">
            <h6 id="signUpUsernameError">Username already used!</h6>
            <input type="email" placeholder="Email" id="signUpEmail">
            <h6 id="signUpEmailError">Not valid input!</h6>
            <input type="password" placeholder="Password" id="signUpPassword">
            <h6 id="passwordError">unsupported password!</h6>
            <button type="button" id="SignUp">Sign Up</button>
        </form>
    </div>
    <div class="form-container sign-in">
        <form id="signInForm">
            <h1>Sign In</h1>
            <span>use your Username and password</span>
            <input type="text" placeholder="Username" id="signInUsername">
            <input type="password" placeholder="Password" id="signInPassword">
            <h6 id="signInError">Either your Password or Username is Wrong!</h6>
            <button type="button" id="SignIn">Sign In</button>
        </form>
    </div>
    <div class="toggle-container">
        <div class="toggle">
            <div class="toggle-panel toggle-left">
                <h1>Hello, Friend!</h1>
                <p>Register with your personal details to use site features</p>
                <p>or</p>
                <button class="hidden" id="login">Sign In</button>
            </div>
            <div class="toggle-panel toggle-right">
                <h1>Welcome Back!</h1>
                <p>Enter your personal details to use site features</p>
                <p>or</p>
                <button class="hidden" id="register">Sign Up</button>
            </div>
        </div>
    </div>
</div>

<script>
    const container = document.getElementById('container');
    const registerPage = document.getElementById('register');
    const loginPage = document.getElementById('login');
    const registerBtn = document.getElementById('SignUp');
    const loginBtn = document.getElementById('SignIn');

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
                    let resCode = data.string1;
                    console.log(resCode);
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
                    let resCode = data.string1;
                    console.log(resCode);
                    signUp(resCode);
                })
                .catch(function(error) {
                    console.error('There was a problem with fetch operation:', error.message);
                });
        });
    });


    function login(){
        //to do
        //call post method for login
    }

    function register(){
        //to do
        //call post method for registration
    }

    function passwordIsValid(password){
        //to do
        //check if password contains correct characters
        return false;
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
            register();
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
            login();
        }
    }
    //
    //
    // var xhr = null;
    // function sighUpHome() {
    //     // instantiate  XMLHttpRequest object
    //     try {
    //         xhr = new XMLHttpRequest();
    //     } catch (e) {
    //         xhr = new ActiveXObject("Microsoft.XMLHTTP");
    //     }
    //
    //     // handle old browsers
    //     if (xhr == null) {
    //         alert("Ajax not supported by your browser!");
    //         return;
    //     }
    //
    //     // construct URL
    //     //write servlet end instead of login
    //     var url = "login?username=" + document.getElementById("signUpUsername").value+
    //                         "&password="+document.getElementById("signUpPassword").value+
    //                             "&email="+document.getElementById("signUpEmail").value;
    //
    //     // get quote
    //     xhr.onreadystatechange = handler; // sets 'listener'
    //     xhr.open("post", url, true); // true == async
    //     xhr.send(null); // there is no request body request
    // }
    //
    // function logInHome() {
    //     // instantiate  XMLHttpRequest object
    //     try {
    //         xhr = new XMLHttpRequest();
    //     } catch (e) {
    //         xhr = new ActiveXObject("Microsoft.XMLHTTP");
    //     }
    //
    //     // handle old browsers
    //     if (xhr == null) {
    //         alert("Ajax not supported by your browser!");
    //         return;
    //     }
    //
    //     // construct URL
    //     //write servlet end instead of login
    //     var url = "login?username=" + document.getElementById("signInUsername").value+
    //                             "&password="+document.getElementById("signInPassword").value;
    //
    //     // get quote
    //     xhr.onreadystatechange = handler; // sets 'listener'
    //     xhr.open("post", url, true); // true == async
    //     xhr.send(null); // there is no request body request
    // }
    //
    // /**
    //  * Handles the Ajax response
    //  */
    // function handler() {
    //     // only handle loaded requests
    //     if (xhr.readyState === 4) {
    //         if (xhr.status === 200) {
    //             alert(xhr.responseText);
    //         } else {
    //             alert("Error with Ajax call");
    //         }
    //     }
    // }



</script>
</body>
</html>
