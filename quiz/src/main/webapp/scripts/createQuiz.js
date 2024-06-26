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
            if(questions.length%4===0){
                $('#questions-container').append(`<div class="row mb-3" id="quest-box-${Math.floor(questions.length/4)}"></div>`);
            }
            $('#quest-box-'+Math.floor(questions.length/4)).append(`
                <div class="col">
                    <button class="prev-btn btn btn-primary" data-toggle="modal" data-target="#addQuest">${questions.length+1}</button>
                </div>`);
            questions[questions.length]=instance;
        }
        instance.readInfo();

        $('#addQuest').modal('hide')
        window.scrollTo(0, document.body.scrollHeight);
    })

    $('#showAddForm').click(function (){
        let type = $('#question-types').val();
        $('#addQuestBody').html(man.generateInstance(type).getCreateHtml(type));
    })

    $('#questions-container').on('click','.prev-btn',function (){
        let id = $(this).text();
        let instance = questions[id-1];
        $('#addQuestBody').html(instance.getCreateHtml(id));
        instance.setPreview();
    })


})


function isNumber(value) {
    return !isNaN(value) && !isNaN(parseFloat(value));
}