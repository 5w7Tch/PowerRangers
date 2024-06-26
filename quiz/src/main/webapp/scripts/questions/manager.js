import {questionResponse} from "/static/scripts/questions/questionResponse.js";
import {fillInBlank} from "/static/scripts/questions/fillInBlank.js";

export class manager{
    constructor() {
        this.questionClasses = {};
        this.questionClasses['questionResponse']=questionResponse;
        this.questionClasses['fillInBlank']=fillInBlank;
    }

    generateInstance(name){
        return new this.questionClasses['questionResponse']();
    }
}