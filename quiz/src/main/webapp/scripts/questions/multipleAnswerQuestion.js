
export class multipleAnswerQuestion{
    constructor() {
        this.answers = [];
        this.score = 0;
        this.orderMatters = false;
    }

    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="question">Question</label>
                <textarea class="form-control" id="question" rows="3"></textarea>
                <label for="answers">Answers</label>
                <div id = "answersContainer">
                    <textarea class="form-control" id="answer" rows="3"></textarea>
                </div>
                <div class = "multipleAnswer-footer">
                    <button id="addAnswers" style="background-color: #1fe100">add Answer</button>
                    <div>
                        <label style="color: red;font-weight: bold;"> Score:</label>     
                        <input type="number" id="score" style="width: 80px" value="1">  
                    </div>
                    <div>
                        <label for="orderMatters">Order Matters</label>
                        <input type="checkbox" id="orderMatters">
                    </div>
                </div>
                
            </div>
        `;
    }

    readInfo(){
        this.score = $('#score').val();
        this.question = $('#question').val();
        this.orderMatters = $('#orderMatters').is(':checked');

        this.answers = [];
        let answers = this.answers;
        $('#answersContainer textarea').each(function() {
            if ($(this).val() !== ''){
                answers.push($(this).val());
            }
        });
    }

    setPreview(){
        $('#question').text(this.question);
        $('#score').val(this.score);
        $('#orderMatters').prop('checked', this.orderMatters);
        var answers = document.getElementById('answersContainer');
        answers.innerHTML = '';
        this.answers.forEach(function(answer) {
            if(answer.length !== 0){
                addTextarea(answers , answer);
            }
        });
    }

    generateJson() {
        return {
            'question': this.question,
            'type': this.getType(),
            'answers': this.answers,
            'score': this.score,
            'orderMatters': this.orderMatters
        };
    }

    isValid(){
        return this.question !== '' && this.answers.length !== 0;
    }

    getType(){
        return 'multipleAnswerQuestion';
    }

    allertMsg(){
        return 'Fill Question Description And Answer';
    }
}


function addTextarea(container , textValue){
    var textareaId = '' + container.children.length;
    var textarea = document.createElement('textarea');
    var deleteBtn = document.createElement('button');
    deleteBtn.id = textareaId;
    deleteBtn.name = "delete";
    deleteBtn.addEventListener('click', function() {
        container.removeChild(textarea);
        container.removeChild(deleteBtn);
    });
    deleteBtn.style.backgroundColor = "#ff0000";
    textarea.className = 'form-control';
    textarea.style.marginBottom = '5px';
    textarea.id = textareaId;
    textarea.rows = 3;
    textarea.value = textValue;
    container.appendChild(deleteBtn);
    container.appendChild(textarea);
}

$(document).ready(function(){
    $('body').on('click', '#addAnswers', function() {
        var answersContainer = document.getElementById('answersContainer');
        addTextarea(answersContainer , '');
    });
})

