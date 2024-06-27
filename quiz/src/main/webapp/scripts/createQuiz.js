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

    $('#submit-btn').click(function (){
        if(!allDataSubmitted()){
            console.log("submit quiz info first");
            return;
        }

        let quizJson = generateJsonObject();
        $.ajax({
            url: '/createQuiz',
            type: 'POST',
            data: JSON.stringify(quizJson),
            contentType: 'application/json; charset=UTF-8',
            beforeSend: function (xhr){
                $('#submit-btn').attr("disabled",true)
                $('#showAddForm').attr("disabled",true)
                $('#submit-btn').text('uploading...')
            },
            success: function (result,status,xhr){
                // todo: redirect ot quiz home page
                console.log(result.status)
            },
            error: function (xhr,status,error){
                // todo: redirect to error page
            }
        })
    })
})

function allDataSubmitted(){
    return true;
}


function generateJsonObject(){
    return {
        'title' : $('#quizTitle').val(),
        'description' : $('#quizDescription').val(),
        'pageType' : $('#pageType').val(),
        'isRandom' : $('#randomQuestions').val(),
        'immediateCorrection' : $('#immediateCorrection').val(),
        'practiceMode' : $('#practiceMode').val(),
        'questions' : generateQuestionsJson()
    }
}

function generateQuestionsJson(){
    let jsonArr = [];
    for(let i=0;i<questions.length;i++){
        jsonArr.push(questions[i].generateJson());
    }
    return jsonArr;
}

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
        </div>
    `);
}


function isNumber(value) {
    return !isNaN(value) && !isNaN(parseFloat(value));
}