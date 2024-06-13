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

function accExists(username, password){
    //to do
    //check credentials
    return false;
}

function usernameExists(username){
    //to do
    //check if username is already used
    return true;
}

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

registerBtn.addEventListener('click', function (){
    let username = document.getElementById('signUpUsername').value;
    let email = document.getElementById('signUpEmail').value;
    let password = document.getElementById('signUpPassword').value;

    // Get error elements, red sentences
    let usernameError = document.getElementById('signUpUsernameError');
    let emailError = document.getElementById('signUpEmailError');
    let passwordError = document.getElementById('PasswordError');

    // Reset error messages
    usernameError.style.visibility = 'hidden';
    emailError.style.visibility = 'hidden';
    passwordError.style.visibility = 'hidden';

    //determines weather to let user register with this info and call post method
    let hasError = false;

    if (usernameExists(username)) {
        usernameError.style.visibility = 'visible';
        document.getElementById('signUpUsername').value = '';
        hasError = true;
    }
    if (!email.contains('@')) {
        //fix doesnt  work
        document.getElementById('signUpEmailError').style.display = 'visible';
        document.getElementById('signUpEmail').value = '';
        hasError = true;
    }
    if(!passwordIsValid(password)){
        //fix doesnt  work
        document.getElementById('PasswordError').style.visibility = 'visible';
        document.getElementById('signUpPassword').value = '';
        hasError = true;
    }

    if(!hasError){
        register();
    }
});

loginBtn.addEventListener('click', function () {
    let username = document.getElementById('signInUsername').value;
    let password = document.getElementById('signInPassword').value;

    // Get error elements red words
    let error = document.getElementById('signInError');

    // Reset error messages
    error.style.visibility = 'hidden';

    // checks entered infos validity
    if (!accExists(username, password)) {
        document.getElementById('signInUsername').value = '';
        document.getElementById('signInPassword').value = '';
        error.style.visibility = 'visible';
    }else {
        login();
    }
})

