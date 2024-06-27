
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
        let idx = 0;
        var answersContainer = document.getElementById('answersContainer');
        answersContainer.innerHTML = '';
        this.answers.forEach(function(answer) {
            if(answer.length !== 0){
                // answersHtml += '<textarea class="form-control" id="'+ idx+'" rows="3" style="margin-bottom: 5px;">'+ answer +'</textarea>';
                var textarea = document.createElement('textarea');
                var deleteBtn = document.createElement('button');
                deleteBtn.id = idx.toString();
                deleteBtn.name = "delete";
                deleteBtn.addEventListener('click', function() {
                    answersContainer.removeChild(textarea);
                    answersContainer.removeChild(deleteBtn);
                });
                deleteBtn.style.backgroundColor = "#ff0000";
                textarea.className = 'form-control';
                textarea.style.marginBottom = '5px';
                textarea.id = '' + idx;
                textarea.rows = 3;
                textarea.value = answer;
                answersContainer.appendChild(deleteBtn);
                answersContainer.appendChild(textarea);
                idx++;
            }
        });
    }

    generateJson() {

    }
}

$(document).ready(function(){
    $('body').on('click','#addAnswerButton',function (){
        var answersContainer = document.getElementById('answersContainer');
        var textareaId = '' + answersContainer.children.length;
        var textarea = document.createElement('textarea');
        var deleteBtn = document.createElement('button');
        deleteBtn.id = textareaId;
        deleteBtn.name = "delete";
        deleteBtn.addEventListener('click', function() {
            answersContainer.removeChild(textarea);
            answersContainer.removeChild(deleteBtn);
        });
        deleteBtn.style.backgroundColor = "#ff0000";
        textarea.className = 'form-control';
        textarea.style.marginBottom = '5px';
        textarea.id = textareaId;
        textarea.rows = 3;
        answersContainer.appendChild(deleteBtn);
        answersContainer.appendChild(textarea);
    })
})

