'use strict'

// TODO переименовать везде title
let workExpInfos = 0;

function addWorkExp(event) {
    event.preventDefault();
    workExpInfos++;
    appendWorkExp(workExpInfos)
}

let eduInfos = 0;

function addEdu (event) {
    event.preventDefault();
    eduInfos++;
    appendEdu(eduInfos)
}
