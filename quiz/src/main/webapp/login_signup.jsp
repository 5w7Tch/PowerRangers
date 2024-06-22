<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/loginStyles.css">
    <title>Qwiztime</title>
    <link rel="icon" href="<%=request.getContextPath()%>/static/icons/logo.png" type="image/png">
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
<%--<script src="<%=request.getContextPath()%>/static/scripts/loginAnimations.js"></script>--%>
<script><%@include file="scripts/loginAnimations.js"%></script>
</body>
</html>
