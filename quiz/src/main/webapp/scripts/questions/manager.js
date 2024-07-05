import {questionResponse} from "/static/scripts/questions/questionResponse.js";
import {fillInBlank} from "/static/scripts/questions/fillInBlank.js";
import {multipleChoice} from "/static/scripts/questions/multipleChoice.js";
import {matching} from "/static/scripts/questions/matching.js";
import {multipleAnswerChoice} from "/static/scripts/questions/multipleAnswerChoice.js";
import {multipleAnswerQuestion} from "/static/scripts/questions/multipleAnswerQuestion.js";
import {pictureResponse} from "/static/scripts/questions/pictureResponse.js";

export class manager{
    constructor() {
        this.questionClasses = {};
        this.questionClasses['questionResponse']=questionResponse;
        this.questionClasses['fillInBlank']=fillInBlank;
        this.questionClasses['multipleChoice']=multipleChoice;
        this.questionClasses['matching']=matching;
        this.questionClasses['multipleAnswerChoice']=multipleAnswerChoice;
        this.questionClasses['multipleAnswerQuestion']=multipleAnswerQuestion;
        this.questionClasses['pictureResponse']=pictureResponse;
    }

    generateInstance(name){
        return new this.questionClasses[name]();
    }
}