import {questionResponse} from "/static/scripts/questions/questionResponse.js";

export class manager{
    constructor() {
        this.questionClasses = {};
        this.questionClasses['questionResponse']=questionResponse;
    }

    generateInstance(name){
        return new this.questionClasses['questionResponse']();
    }

}