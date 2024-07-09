
export class multipleAnswerChoice{
    constructor() {
        this.wrongAnswers = [];
        this.correctAnswers = [];
        this.score = 0;
    }

    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="question">Question</label>
                <textarea class="form-control" id="question" rows="3"></textarea>
                <label for="answers">Correct Answers</label>
                <div id = "correctAnswersContainer">
                    <textarea class="form-control" id="answer" rows="3"></textarea>
                </div>
                <label for="answer">Wrong Answers</label>
                <div id="wrongAnswersContainer"></div>
                <div class = "button-container">
                    <button id="addCorrectAnswers" style="background-color: #1fe100">add Correct Answer</button>  
                    <button id="addWrongAnswers" style="background-color: #1fe100">add Wrong Answer</button>  
                </div>
                <div class = "score-container">
                    <div>
                        <label style="color: red;font-weight: bold;"> Score:</label>     
                        <input type="number" id="score" style="width: 80px" value="1">  
                    </div> 
                </div>     
            </div>
        `;
    }

    readInfo(){
        this.wrongAnswers = [];
        this.correctAnswers = [];
        this.score = $('#score').val();
        this.question = $('#question').val();

        let correctAns = this.correctAnswers;
        $('#correctAnswersContainer textarea').each(function() {
            correctAns.push($(this).val());
        });

        let wrongAns = this.wrongAnswers;
        $('#wrongAnswersContainer textarea').each(function() {
            wrongAns.push($(this).val());
        });
    }

    setPreview(){
        $('#question').text(this.question);
        $('#score').val(this.score);
        var correctAnswers = document.getElementById('correctAnswersContainer');
        var wrongAnswers = document.getElementById('wrongAnswersContainer');
        correctAnswers.innerHTML = '';
        this.correctAnswers.forEach(function(answer) {
            if(answer.length !== 0){
                addTextarea(correctAnswers , answer);
            }
        });

        this.wrongAnswers.forEach(function(answer) {
            if(answer.length !== 0){
                addTextarea(wrongAnswers , answer);
            }
        });
    }

    setValues(answer , question , score){
        this.question = question.description;
        this.correctAnswers = question.correctAnswers;
        this.wrongAnswers = question.wrongAnswers;
        this.score = score;
    }

    generateJson() {
        return {
            'type': this.getType(),
            'question': {
                'description': this.question,
                'correctAnswers': this.correctAnswers,
                'wrongAnswers': this.wrongAnswers
            },
            'answer': {
                'answers': this.correctAnswers
            },
            'score': this.score
        };
    }

    isValid(){
        return this.question !== '' && this.correctAnswers.length !== 0;
    }

    getType(){
        return 'multipleAnswerChoice';
    }

    allertMsg(){
        return 'Fill Question Description And Add Correct Answer';
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
    deleteBtn.style.padding = "1px";
    deleteBtn.style.width = '15px';
    textarea.className = 'form-control';
    textarea.style.marginBottom = '5px';
    textarea.id = textareaId;
    textarea.rows = 3;
    textarea.value = textValue;
    container.appendChild(deleteBtn);
    container.appendChild(textarea);
}

$(document).ready(function(){
    $('body').on('click', '#addCorrectAnswers', function() {
        var correctAnswersContainer = document.getElementById('correctAnswersContainer');
        addTextarea(correctAnswersContainer , '');
    });

    $('body').on('click','#addWrongAnswers',function (){
        var answersContainer = document.getElementById('wrongAnswersContainer');
        addTextarea(answersContainer , '');
    })
})

