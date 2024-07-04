import {manager} from "/static/scripts/questions/manager.js"

let questions = [];
let man = new manager();
let isEditPage = false;

$(document).ready(function (){

    if($('#editQuizId').length > 0){
        isEditPage = true;
        initEditPage($('#editQuizId').text());
    }


    $('#submit-quest').click(function (){
        let type = $('#type').text();
        let instance;
        if(isNumber(type)){
            instance = questions[type-1];
            let tmpInstance = man.generateInstance(instance.getType());
            tmpInstance.readInfo();
            if(tmpInstance.isValid()){
                instance.readInfo();
            }else{
                alert(instance.allertMsg());
            }
        }else{
            instance = man.generateInstance(type);
            instance.readInfo();
            if(instance.isValid()){
                addQuestionToDOMList(questions.length);
                questions[questions.length]=instance;
            }else{
                alert(instance.allertMsg());
            }
        }
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
        deleteQuestion(idx);
    })

    $('#submit-btn').click(function (){
        if(!allDataSubmitted()){
            alert("Fill all fields");
            return;
        }

        let quizJson = generateJsonObject();
        let url;
        let method;
        if(isEditPage){
            url = '/createQuiz?quizId='+$('#editQuizId').text();
            method = 'put';
        }else{
            url = '/createQuiz';
            method = 'post';
        }

        $.ajax({
            url: url,
            type: method,
            data: JSON.stringify(quizJson),
            contentType: 'application/json; charset=UTF-8',
            beforeSend: function (xhr){
                $('#submit-btn').attr("disabled",true)
                $('#showAddForm').attr("disabled",true)
                $('#submit-btn').text('uploading...')
            },
            success: function (result,status,xhr){
                // todo: redirect ot quiz home page
                window.location.href = "/";
                console.log(result.status)
            },
            error: function (xhr,status,error){
                // todo: redirect to 500 error page
            }
        })
    })
})

function deleteQuestion(idx){
    questions.splice(idx,1);
    $('#addQuest').modal('hide')
    $('#questions-container').empty()
    addRemainigQuestions();
}

function allDataSubmitted(){
    return $('#quizTitle').val()!=='' || $('#quizDescription').val()!=='';
}


function generateJsonObject(){
    return {
        'title' : $('#quizTitle').val(),
        'description' : $('#quizDescription').val(),
        'isRandom' : $('#randomQuestions').val(),
        'immediateCorrection' : $('#immediateCorrection').val(),
        'practiceMode' : $('#practiceMode').val(),
        'duration' : $('#duration').val(),
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

function initEditPage(id){
    $.ajax({
        url: '/editQuiz',
        type: 'POST',
        data: JSON.stringify({"quizId":id}),
        contentType: 'application/json; charset=UTF-8',
        beforeSend: function (xhr){
            $('#submit-btn').attr("disabled",true)
            $('#showAddForm').attr("disabled",true)
            $('#submit-btn').text('wait...')
        },
        success: function (result,status,xhr){
            fillValues(result);
            $('#submit-btn').attr("disabled",false)
            $('#showAddForm').attr("disabled",false)
            $('#submit-btn').text('Submit')
        },
        error: function (xhr,status,error){
            // todo: redirect to 500 error page
        }
    })
}

function fillValues(data){
    addQuizInfo(data);
    addQuestions(data);
}

function addQuizInfo(data){
    $('#quizTitle').val(data.title);
    $('#quizDescription').val(data.description);
    generateBoolean($('#randomQuestions'),data.randomSeq)
    generateBoolean($('#practiceMode'),data.isPracticable)
    generateBoolean($('#immediateCorrection'),data.immediateCorrection)
    $('#duration').val(data.duration);
}

function addQuestions(data){
    let quests = data.questions;
    for(let i=0;i<quests.length;i++){
        let instance = man.generateInstance(quests[i].type)
        instance.setValues(quests[i].answer,quests[i].question,quests[i].score)
        addQuestionToDOMList(questions.length)
        questions[questions.length] = instance;
    }
}

function generateBoolean(tag,bool){
    if(bool){
        tag.val("true");
    }else{
        tag.val("false");
    }
}


function isNumber(value) {
    return !isNaN(value) && !isNaN(parseFloat(value));
}