export class questionResponse{
    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="question">Question</label>
                <textarea class="form-control" id="question" rows="3"></textarea>
                <label for="answer">Answer</label>
                <textarea class="form-control" id="answer" rows="3"></textarea>
            </div>   
        `;
    }

    readInfo(){
        this.question = $('#question').val();
        this.answer = $('#answer').val();
    }

    setPreview(){
        $('#question').text(this.question);
        $('#answer').text(this.answer);
    }

    generateJson(){
        return {
            'type' : 'questionResponse',
            'question' : this.question,
            'answer' : this.answer
        }
    }
}

