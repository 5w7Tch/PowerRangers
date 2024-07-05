document.getElementById('retake').addEventListener('click', function () {
    const url = 'quiz?quizid=' + this.getAttribute('name');
    window.location.replace(url);
});

document.getElementById('cancel').addEventListener('click', function () {
    window.location.replace('/');
});