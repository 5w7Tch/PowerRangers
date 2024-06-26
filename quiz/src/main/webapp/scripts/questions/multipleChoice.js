
export class multipleChoice{
    constructor() {
        this.answers = [];
    }

    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="question">Question</label>
                <textarea class="form-control" id="question" rows="3"></textarea>
                <label for="answer">Correct Answer</label>
                <textarea class="form-control" id="answer" rows="3"></textarea>
                <label for="answer">Wrong Answers</label>
                <div id="answersContainer"></div>
                <button id="addAnswerButton" style="background-color: #1fe100">Add Possible Answer</button>           
            </div>
            <script>
              document.getElementById('addAnswerButton').addEventListener('click', function() {
                    var answersContainer = document.getElementById('answersContainer');
                    var textareaId = '' + answersContainer.children.length;
                    var textarea = document.createElement('textarea');
                    textarea.className = 'form-control';
                    textarea.style.marginBottom = '5px';
                    textarea.id = textareaId;
                    textarea.rows = 3;
                    answersContainer.appendChild(textarea);
              });
            </script>
        `;
    }

    readInfo(){
        this.question = $('#question').val();
        this.answers = [];
        let anss = this.answers;
        this.answer = $('#answer').val();
        $('#answersContainer textarea').each(function() {
            anss.push($(this).val());
        });
    }

    setPreview(){
        $('#question').text(this.question);
        $('#answer').text(this.answer);
        var answersHtml = '';
        let idx = 0;
        this.answers.forEach(function(answer) {
            answersHtml += '<textarea class="form-control" id="'+ idx+'" rows="3" >'+ answer +'</textarea>';
            idx++;
        });
        $('#answersContainer').html(answersHtml);
    }

    generateJson() {

    }
}



