function addEdu (event) {
    event.preventDefault();
    let eduInfo = document.getElementById("eduInfo")
    let eduInfos = eduInfo.childElementCount - 1;
    appendEdu(eduInfos)
}

function addWorkExp(event) {
    event.preventDefault();
    let workExpInfo = document.getElementById("workExpInfo")
    let workExpInfos = workExpInfo.childElementCount - 1
    appendWorkExp(workExpInfos)
}