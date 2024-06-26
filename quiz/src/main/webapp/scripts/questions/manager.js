import {questionResponse} from "/static/scripts/questions/questionResponse.js";
import {fillInBlank} from "/static/scripts/questions/fillInBlank.js";
import {multipleChoice} from "/static/scripts/questions/multipleChoice.js";

export class manager{
    constructor() {
        this.questionClasses = {};
        this.questionClasses['questionResponse']=questionResponse;
        this.questionClasses['fillInBlank']=fillInBlank;
        this.questionClasses['multipleChoice']=multipleChoice;
    }

    generateInstance(name){
        return new this.questionClasses[name]();
    }
}