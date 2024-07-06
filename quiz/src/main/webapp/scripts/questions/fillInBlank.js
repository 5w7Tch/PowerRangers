export class fillInBlank{
    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="question">Question</label>
                <textarea class="form-control" id="question" rows="3"></textarea>
                <hr>
                <h3 style="border: none" class="form-control">Answers</h3>
                <div id="answers" >
                    
                </div>
                <button id="add-answer" class="btn btn-success">add Answer</button> 
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
        this.question=question;
        this.answer=answer;
        this.score=score;
    }

    generateJson(){

    }

    isValid(){
        return true;
    }

    getType(){
        return 'fillInBlank';
    }
}

$(document).ready(function (){
    $('body').on('click','#add-answer',function (){
        addAnswerHtml();
    })

    $('body').on('click','.remove-btn',function (){
        $(this).closest('.answer-div').remove();
    })
})

function addAnswerHtml(){
    let elem = `
            <div class="answer-div">
                <button class="remove-btn" style="background-color: #ff0000"></button>
                <textarea style="margin: 5px auto" class="fillInBlank-answer form-control"></textarea>
            </div>
        `
    $('#answers').append(elem);
}