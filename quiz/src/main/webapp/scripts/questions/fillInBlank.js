export class fillInBlank{
    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="question">Question</label>
                <textarea class="form-control" id="question" rows="3"></textarea>
                <button class="btn btn-success" id="add-answer">Add blanck</button>
                <hr>
                <h3 style="border: none" class="form-control">Answers</h3>
                <div id="answers" >
     
                </div>
                <label style="color: red;font-weight: bold;"> Score:</label>     
                <input type="number" id="score" style="width: 80px" value="1">
            </div>
        `
    }

    readInfo(){
        this.question = $('#question').val();
        let answers = [];
        $('.fillInBlank-answer').each(function (index){
            let val = $(this).val();
            answers.push(val);
        });
        this.answers = answers;
        this.score = $('#score').val();
    }

    setPreview(){
        $('#question').text(this.question);
        let answers = this.answers;
        for (let i = 0; i < answers.length; i++) {
            addAnswerHtml();
        }
        $('.fillInBlank-answer').each(function (index){
            $(this).text(answers[index]);
        })
        $('#score').val(this.score);
    }

    setValues(answer,question,score){
        this.question = question.question;
        this.answers = answer.answers;
        this.score = score;
    }

    generateJson(){
        return {
            'type': this.getType(),
            'question' : {
                'question' : this.question
            },
            'answer' : {
                'answers' : this.answers
            },
            'score' : this.score
        }
    }

    isValid(){
        let quest = $('#question').val();
        let startCount = quest.split('<>').length - 1;
        let fieldsCount = 0;
        $('.fillInBlank-answer').each(function (index){
            fieldsCount++;
        });
        return startCount === fieldsCount;
    }

    getType(){
        return 'fillInBlank';
    }
    allertMsg(){
        return 'Fields and empty space count Differ';
    }
}


$(document).ready(function (){
    $('body').on('click','#add-answer',function (){
        insertSymbol();
        addAnswerHtml();
    })
    $('body').on('click','#add-field',function (){
        addAnswerHtml();
    })

    $('body').on('click','.remove-btn',function (){
        $(this).closest('.answer-div').remove();
    })
})

function addAnswerHtml(){
    let elem = `
            <div class="answer-div">
                <button class="remove-btn" style="background-color: #ff0000; padding: 1px; width: 15px;"></button>
                <textarea style="margin: 5px auto" class="fillInBlank-answer form-control"></textarea>
            </div>
        `
    $('#answers').append(elem);
}
function insertSymbol() {
    const textarea = document.getElementById('question');
    const symbol = ' <> ';
    const cursorPosition = textarea.selectionStart;
    const textBefore = textarea.value.substring(0, cursorPosition);
    const textAfter = textarea.value.substring(cursorPosition);
    textarea.value = textBefore + symbol + textAfter;

    textarea.selectionStart = textarea.selectionEnd = cursorPosition + symbol.length;
    textarea.focus();

}