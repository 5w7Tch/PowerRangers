<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Quiz</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/createQuizStyles.css">
  <%--  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/styles/navbarStyles.css">--%>
<%--  <script type="text/javascript" src="<%=request.getContextPath()%>/static/scripts/questions/questionResponse.js"></script>--%>
</head>
<body>

<div id="main-box">
  <div class="container" id="desc-box">
    <h2>Create a New Quiz</h2>
    <div>
      <div class="form-group">
        <label for="quizTitle">Quiz Title</label>
        <input type="text" class="form-control" id="quizTitle" name="quizTitle" required>
      </div>
      <div class="form-group">
        <label for="quizDescription">Quiz Description</label>
        <textarea id="quizDescription" class="form-control" name="quizDescription" rows="3" required></textarea>
      </div>
      <div class="form-group">
        <label for="randomQuestions">Randomize Questions</label>
        <select class="form-control" id="randomQuestions" name="randomQuestions">
          <option value="true">Yes</option>
          <option value="false">No</option>
        </select>
      </div>
      <div class="form-group">
        <label for="practiceMode">Practice Mode</label>
        <select class="form-control" id="practiceMode" name="practiceMode">
          <option value="true">Yes</option>
          <option value="false">No</option>
        </select>
      </div>
      <div class="form-group">
        <label for="immediateCorrection">Immediate Correction</label>
        <select class="form-control" id="immediateCorrection" name="immediateCorrection">
          <option value="true">Yes</option>
          <option value="false">No</option>
        </select>
      </div>
      <div class="form-group">
        <label for="duration">Duration in minutes</label>
        <input type="number" id="duration" class="form-control" value="60">
      </div>
      <div class="form-group">
        <button id="submit-btn" class="form-control btn btn-success">Create Quiz</button>
      </div>
    </div>
  </div>
  <div class="container" style="margin-top:30px">
    <div id="questions-container" class="overflow-auto container border border-primary rounded"></div>
    <div id="button-container">
      <div class="form-group">
        <button id="showAddForm" type="button" class="form-control btn btn-primary" data-toggle="modal" data-target="#addQuest">Add question</button>
      </div>
      <div style="width: 190px;margin-left: 10px" class="form-group">
        <select id="question-types" class="form-control form-select" aria-label="Default select example">
          <option>questionResponse</option>
          <option>fillInBlank</option>
          <option>multipleChoice</option>
          <option>matching</option>
        </select>
      </div>
    </div>
  </div>
</div>


<div class="modal fade" id="addQuest" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">New Question</h5>
      </div>
      <div id="addQuestBody" class="modal-body">

      </div>
      <div id="footer" class="modal-footer">
        <button type="button" id="submit-quest" class="btn btn-primary">Save changes</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>



<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script type="module" src="<%=request.getContextPath()%>/static/scripts/createQuiz.js"></script>
</body>
</html>
