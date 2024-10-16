function appendEdu (eduNum) {
    $('#eduInfo').append('<div class="col-12 col-sm-10 col-lg-8 col-xl-7"> ' +
        '<div class="card">\n' +
        '                        <div class="card-body">\n' +
        '                            <h5 class="card-title">Образование:</h5>\n' +
        '                            <div class="card-text row my-1">\n' +
        '                                <div class="col-auto">\n' +
        '                                    <label for="institution" class="col-form-label">Учреждение:</label>\n' +
        '                                </div>\n' +
        '                                <div class="col-auto">\n' +
        '                                    <input type="text" id="institution" class="form-control form-control-sm" name="educationInfo[' + eduNum + '].institution">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <div class="card-text row my-1">\n' +
        '                                <div class="col-auto">\n' +
        '                                    <label for="program" class="col-form-label">Программа:</label>\n' +
        '                                </div>\n' +
        '                                <div class="col-auto">\n' +
        '                                    <input type="text" id="program" class="form-control form-control-sm" name="educationInfo[' + eduNum + '].program">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <div class="card-text row my-1">\n' +
        '                                <div class="col-auto">\n' +
        '                                    <label for="startDate" class="col-form-label">Начало обучения:</label>\n' +
        '                                </div>\n' +
        '                                <div class="col-auto">\n' +
        '                                    <input type="date" id="startDate" class="form-control form-control-sm" name="educationInfo[' + eduNum + '].startDate">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <div class="card-text row my-1">\n' +
        '                                <div class="col-auto">\n' +
        '                                    <label for="endDate" class="col-form-label">Конец обучения:</label>\n' +
        '                                </div>\n' +
        '                                <div class="col-auto">\n' +
        '                                    <input type="date" id="endDate" class="form-control form-control-sm" name="educationInfo[' + eduNum + '].endDate">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <div class="card-text row my-1">\n' +
        '                                <div class="col-auto">\n' +
        '                                    <label for="degree" class="col-form-label">Степень:</label>\n' +
        '                                </div>\n' +
        '                                <div class="col-auto">\n' +
        '                                    <input type="text" id="degree" class="form-control form-control-sm" name="educationInfo[' + eduNum + '].degree">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>' +
        '</div> ')
}

function appendWorkExp (workExpNum) {
    $('#workExpInfo').append('<div class="col-12 col-sm-10 col-lg-8 col-xl-7">' +
        '<div class="card">\n' +
        '                        <div class="card-body">\n' +
        '                            <h5 class="card-title">Опыт работы: </h5>\n' +
        '                            <div class="card-text row my-1">\n' +
        '                                <div class="col-12 col-sm-auto">\n' +
        '                                    <label for="years" class="col-form-label">Год работы:</label>\n' +
        '                                </div>\n' +
        '                                <div class="col-12 col-sm-auto">\n' +
        '                                    <input type="number" id="years" class="form-control form-control-sm" name="workExperienceInfo[' + workExpNum + '].years">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">\n' +
        '                                <div class="col-12 col-sm-auto">\n' +
        '                                    <label for="companyName" class="col-form-label">Название компании:</label>\n' +
        '                                 </div>\n' +
        '                                <div class="col-12 col-sm-auto">\n' +
        '                                    <input type="text" id="companyName" class="form-control form-control-sm" name="workExperienceInfo[' + workExpNum + '].companyName">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">\n' +
        '                                <div class="col-12 col-sm-auto">\n' +
        '                                    <label for="position" class="col-form-label">Должность:</label>\n' +
        '                                </div>\n' +
        '                                <div class="col-12 col-sm-auto">\n' +
        '                                    <input type="text" id="position" class="form-control form-control-sm" name="workExperienceInfo[' + workExpNum + '].position">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">\n' +
        '                                <div class="col-12 col-sm-auto">\n' +
        '                                    <label for="responsibilities" class="col-form-label">Обязанности:</label>\n' +
        '                                </div>\n' +
        '                                <div class="col-12 col-sm-auto">\n' +
        '                                    <input type="text" id="responsibilities" class="form-control form-control-sm" name="workExperienceInfo[' + workExpNum + '].responsibilities">\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>' +
        '</div> ');
}