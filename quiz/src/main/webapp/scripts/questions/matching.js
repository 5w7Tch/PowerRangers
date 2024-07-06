
export class matching{
    constructor() {
        this.score = 0;
    }

    getCreateHtml(type){
        return `
            <p id="type" style="display: none">${type}</p>
            <div class="form-group">
                <label for="matches">Matches:</label>
                <div id="answersContainer"></div>
                <button id="addMatchButton" style="background-color: #1fe100">Add Match</button>    
                <label style="color: red;font-weight: bold;"> Score:</label>     
                <input type="number" id="score" style="width: 80px" value="1">
            </div>
        `;
    }

    readInfo(){
        this.matches = [];
        this.score = $('#score').val();
        let anss = this.matches;
        let $textareas = $('#answersContainer textarea');
        for (let index = 0; index < $textareas.length; index += 2) {
            if (index < $textareas.length - 1) {
                let $current = $textareas.eq(index);
                let $next = $textareas.eq(index + 1);
                anss.push([$current.val(), $next.val()]);
            }
        }
    }

    setPreview(){
        $('#score').val(this.score);
        let idx = 0;
        var answersContainer = document.getElementById('answersContainer');
        answersContainer.innerHTML = '';
        this.matches.forEach(function(elem) {
            addMatches(elem[0] , elem[1]);
        });
    }

    setValues(answer, question ,score){
        this.matches = [];
        for(let i=0;i<answer.left.length;i++){
            this.matches.push([answer.left[i],answer.right[i]]);
        }


        this.score = score;
    }

    generateJson() {
        let left = [];
        let right = [];

        for(let i=0;i<this.matches.length;i++){
            left.push(this.matches[i][0])
            right.push(this.matches[i][1])
        }


        return {
            'type': this.getType(),
            'question':{},
            'answer': {
                'left' : left,
                'right' : right
            },
            'score': this.score
        }
    }

    isValid(){
        return this.matches.length !== 0;
    }

    allertMsg(){
        return 'Please Add Matches';
    }

    getType(){
        return 'matching';
    }
}

function addMatches(firstMatch , secondMatch){
    var answersContainer = document.getElementById('answersContainer');
    var textareaId = '' + answersContainer.children.length;
    var textarea1 = document.createElement('textarea');
    var textarea2 = document.createElement('textarea');
    var deleteBtn = document.createElement('button');
    deleteBtn.id = textareaId;
    deleteBtn.name = "delete";
    deleteBtn.addEventListener('click', function() {
        answersContainer.removeChild(textarea2);
        answersContainer.removeChild(textarea1);
        answersContainer.removeChild(deleteBtn);
    });
    deleteBtn.style.backgroundColor = "#ff0000";
    textarea1.className = 'form-control';
    textarea1.style.marginBottom = '5px';
    textarea1.id = 'first_'+textareaId;
    textarea1.rows = 3;
    textarea1.value = firstMatch;
    textarea2.className = 'form-control';
    textarea2.style.marginBottom = '10px';
    textarea2.id = 'second_'+textareaId;
    textarea2.rows = 3;
    textarea2.value = secondMatch;
    answersContainer.appendChild(deleteBtn);
    answersContainer.appendChild(textarea1);
    answersContainer.appendChild(textarea2);
}

$(document).ready(function(){
    $('body').on('click','#addMatchButton',function (){
        addMatches('' , '');
    })
})

