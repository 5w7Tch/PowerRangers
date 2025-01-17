export class questionResponse{
    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="question">Question</label>
                <textarea class="form-control" id="question" rows="3"></textarea>
                <label for="answer">Answer</label>
                <textarea class="form-control" id="answer" rows="3"></textarea>
                <label style="color: red;font-weight: bold;"> Score:</label>     
                <input type="number" id="score" style="width: 80px;margin-top: 5px" value="1"> 
            </div>   
        `;
    }

    setValues(answer,question,score){
        this.question=question.description;
        this.answer=answer.description;
        this.score=score;
    }

    readInfo(){
        this.question = $('#question').val();
        this.answer = $('#answer').val();
        this.score = $('#score').val();
    }

    setPreview(){
        $('#question').text(this.question);
        $('#answer').text(this.answer);
        $('#score').val(this.score);
    }

    generateJson(){
        return {
            'type' : this.getType(),
            'question' : {
                'description' : this.question
            },
            'answer' : {
                'description' : this.answer
            },
            'score' : this.score
        }
    }

    isValid(){
        return this.question!=='' && this.answer!=='';
    }

    getType(){
        return 'questionResponse';
    }

    allertMsg(){
        return 'Fill all Fields';
    }



}

