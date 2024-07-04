
export class pictureResponse{
    constructor() {
        this.answers = [];
        this.score = 0;
    }

    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="question">Picture</label>
                <div class = "picture-container"> 
                    <textarea class = "form-control" for = "url" rows="1" placeholder="Please Enter JPG URL" id = "question"></textarea>
                    <button id="generatePicture">Generate Picture</button>
                </div>
                <div id = "imagePreview"></div>
                <label for="answers">Answers</label>
                <div id = "pictureAnswers">
                    <textarea class="form-control" id="answer" rows="3"></textarea>
                </div>
                <div class = "multipleAnswer-footer">
                    <button id="pictureAddAnswers" style="background-color: #1fe100">add Answer</button>
                    <div>
                        <label style="color: red;font-weight: bold;"> Score:</label>     
                        <input type="number" id="score" style="width: 80px" value="1">
                    </div>
                </div>
            </div>
        `;
    }

    readInfo(){
        this.score = $('#score').val();
        this.question = $('#question').val();

        this.answers = [];
        let answers = this.answers;
        $('#pictureAnswers textarea').each(function() {
            if ($(this).val() !== ''){
                answers.push($(this).val());
            }
        });
    }

    setPreview(){
        $('#question').text(this.question);
        $('#score').val(this.score);
        var answers = document.getElementById('pictureAnswers');
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
            'score': this.score
        };
    }

    isValid(){
        return this.question !== '' && this.answers.length !== 0;
    }

    getType(){
        return 'pictureResponse';
    }

    allertMsg(){
        return 'Please Enter Valid JPG url And Add Answers';
    }
}


function addTextarea(container , textValue){
    console.log(container);
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
    $('body').on('click', '#pictureAddAnswers', function() {
        var answersContainer =  document.getElementById('pictureAnswers');
        addTextarea(answersContainer , '');
    });

    $('body').on('click', '#generatePicture', function() {
        var imageUrl = $('#question').val();
        if (imageUrl) {
            validateImageUrl(imageUrl, function(isValid) {
                if (isValid) {
                    $('#imagePreview').html(`<img src="${imageUrl}" alt="Image preview" style="max-width: 100%; height: auto;">`);
                } else {
                    alert('Invalid image URL. Please enter a valid JPG URL.');
                }
            });
        } else {
            alert('Please enter a JPG URL.');
        }
    });
})

function validateImageUrl(url, callback) {
    var img = new Image();
    img.onload = function() { callback(true); };
    img.onerror = function() { callback(false); };
    img.src = url;
}