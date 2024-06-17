<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Quiz</title>
  <style><%@include file="./styles/createQuizStyles.css"%></style>
  <style><%@include file="./styles/navbarStyles.css"%></style>
</head>
<body>
<%@ include file="./html/navbar.html" %>
<div class="container">
  <h2>Create a New Quiz</h2>
  <form action="SaveQuizServlet" method="post">
    <div class="form-group">
      <label for="quizTitle">Quiz Title</label>
      <input type="text" id="quizTitle" name="quizTitle" required>
    </div>
    <div class="form-group">
      <label for="quizDescription">Quiz Description</label>
      <textarea id="quizDescription" name="quizDescription" rows="3" required></textarea>
    </div>
    <div class="form-group">
      <label for="randomQuestions">Randomize Questions</label>
      <select id="randomQuestions" name="randomQuestions">
        <option value="true">Yes</option>
        <option value="false">No</option>
      </select>
    </div>
    <div class="form-group">
      <label for="pageMode">Page Mode</label>
      <select id="pageMode" name="pageMode">
        <option value="single">Single Page</option>
        <option value="multiple">Multiple Pages</option>
      </select>
    </div>
    <div class="form-group">
      <label for="immediateCorrection">Immediate Correction</label>
      <select id="immediateCorrection" name="immediateCorrection">
        <option value="true">Yes</option>
        <option value="false">No</option>
      </select>
    </div>
    <div class="form-group">
      <label for="practiceMode">Practice Mode</label>
      <select id="practiceMode" name="practiceMode">
        <option value="true">Yes</option>
        <option value="false">No</option>
      </select>
    </div>
    <button type="submit" class="btn">Create Quiz</button>
  </form>
</div>
</body>
</html>
