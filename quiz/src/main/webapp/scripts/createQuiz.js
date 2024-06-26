import {manager} from "/static/scripts/questions/manager.js"

let questions = [];
let man = new manager();

$(document).ready(function (){
    $('#submit-quest').click(function (){
        let type = $('#type').text();
        let instance;
        if(isNumber(type)){
            instance = questions[type-1];
        }else{
            instance = man.generateInstance(type);
            addQuestionToDOMList(questions.length);
            questions[questions.length]=instance;
        }
        instance.readInfo();
        $('#addQuest').modal('hide')
    })

    $('#showAddForm').click(function (){
        $('#delete-quest').remove();
        let type = $('#question-types').val();
        $('#addQuestBody').html(man.generateInstance(type).getCreateHtml(type));
    })

    $('#questions-container').on('click','.prev-btn',function (){
        console.log($('#delete-quest').length)
        $('#delete-quest').remove();
        let id = $(this).text();
        let instance = questions[id-1];
        $('#addQuestBody').html(instance.getCreateHtml(id));
        $('#footer').prepend(`<button type="button" id="delete-quest" class="btn btn-danger">delete</button>`)
        instance.setPreview();
    })


    $('body').on('click','#delete-quest',function (){
        let idx = $('#type').val();
        questions.splice(idx,1);
        $('#addQuest').modal('hide')
        $('#questions-container').empty()
        addRemainigQuestions();
    })


})

function addRemainigQuestions(){
    questions.forEach(function (val,idx){
        addQuestionToDOMList(idx);
    })
}

function addQuestionToDOMList(val){
    if(val%2===0){
        $('#questions-container').append(`<div class="row mb-3" id="quest-box-${Math.floor(val/2)}"></div>`);
    }
    $('#quest-box-'+Math.floor(val/2)).append(`
                <div class="col">
                    <button class="prev-btn btn btn-primary" data-toggle="modal" data-target="#addQuest">${val+1}</button>
                </div>`);
}


function isNumber(value) {
    return !isNaN(value) && !isNaN(parseFloat(value));
}