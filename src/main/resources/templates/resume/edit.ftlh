<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <#assign errorClassList = "text-danger d-block error" />
    <#assign eduInfoIndex = 0>

    <form class="col-md-9 col-lg-8 mx-auto my-3" action="/resumes/${resumeUpdateDto.id}/edit" method="POST">
        <h1 class="text-center mb-4">Редактирование резюме:</h1>

        <div class="col-sm-9 col-lg-7 col-xl-6 mb-4">
            <div class="mb-2 ">
                <label for="name">Название резюме:</label>
                <@spring.formInput 'resumeUpdateDto.name' 'id="name" class="form-control"' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-2">
                <label for="category">Категория:</label>

                <#list categories as category>
                    <#if category.id == resumeUpdateDto.categoryId>
                        <#assign oldCategory = category>
                    </#if>
                </#list>

                <#assign options = {"${oldCategory.id}": "${oldCategory.name}"}>
                <#list categories as category>
                    <#if category.id != resumeUpdateDto.categoryId>
                        <#assign options += {"${category.id}": "${category.name}"}>
                    </#if>
                </#list>

                <@spring.formSingleSelect 'resumeUpdateDto.categoryId' options
                'class="form-select" aria-label="Choose account type" id="category"' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-2">
                <label for="salary">Ожидаемый уровень оклада:</label>
                <@spring.formInput 'resumeUpdateDto.salary' 'step="0.01" id="salary" class="form-control"' 'number' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
        </div>

        <p class="py-0 mb-1">О себе:</p>
        <div class="row row-cols-1 row-gap-4 mb-3">

            <div id="workExpInfo" class="col row row-cols-1 row-gap-2">
                <#list resumeUpdateDto.workExperienceInfo as workExp>
                    <#assign workExpIndex = workExp_index>
                    <div class="col-12 col-sm-10 col-lg-8 col-xl-7">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Опыт работы: </h5>

                                <#if workExp.id??>
                                    <input type="hidden" class="form-control form-control-sm" value="${workExp.id}"
                                           name="workExperienceInfo[${workExpIndex}].id" >
                                </#if>

                                <div class="card-text row my-1">
                                    <div class="col-12 col-sm-auto">
                                        <label for="years" class="col-form-label">Год работы:</label>
                                    </div>
                                    <div class="col-12 col-sm-auto">
                                        <@spring.formInput 'resumeUpdateDto.workExperienceInfo[${workExpIndex}].years'
                                        'class="form-control form-control-sm"' 'number' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                                <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                    <div class="col-12 col-sm-auto">
                                        <label for="companyName" class="col-form-label">Название компании:</label>
                                    </div>
                                    <div class="col-12 col-sm-auto">
                                        <@spring.formInput 'resumeUpdateDto.workExperienceInfo[${workExpIndex}].companyName'
                                        'id="companyName" class="form-control form-control-sm"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                                <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                    <div class="col-12 col-sm-auto">
                                        <label for="position" class="col-form-label">Должность:</label>
                                    </div>
                                    <div class="col-12 col-sm-auto">
                                        <@spring.formInput 'resumeUpdateDto.workExperienceInfo[${workExpIndex}].position'
                                        'id="position" class="form-control form-control-sm"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                                <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                    <div class="col-12 col-sm-auto">
                                        <label for="responsibilities" class="col-form-label">Обязанности:</label>
                                    </div>
                                    <div class="col-12 col-sm-auto">
                                        <@spring.formInput 'resumeUpdateDto.workExperienceInfo[${workExpIndex}].responsibilities'
                                        'id="responsibilities" class="form-control form-control-sm"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
                <div class="col-1 align-items-center justify-start">
                    <a class="btn btn-danger d-flex border border-1 justify-content-center align-items-center fs-2"
                       style="height: 100px; width: 100px; text-align: center" onclick="addWorkExp(event)">+</a>
                </div>
            </div>

            <div id="eduInfo" class="col row row-cols-1 row-gap-2">
                <#list resumeUpdateDto.educationInfo as eduInfo>
                    <#assign eduInfoIndex = eduInfo_index>
                    <div class="col-12 col-sm-10 col-lg-8 col-xl-7">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Образование:</h5>

                                <#if eduInfo.id??>
                                    <input type="hidden" class="form-control form-control-sm" value="${eduInfo.id}" name="educationInfo[${eduInfoIndex}].id" >
                                </#if>

                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="institution" class="col-form-label">Учреждение:</label>
                                    </div>
                                    <div class="col-auto">
                                        <@spring.formInput 'resumeUpdateDto.educationInfo[${eduInfoIndex}].institution'
                                        'id="institution" class="form-control form-control-sm"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="program" class="col-form-label">Программа:</label>
                                    </div>
                                    <div class="col-auto">
                                        <@spring.formInput 'resumeUpdateDto.educationInfo[${eduInfoIndex}].program'
                                        'id="program" class="form-control form-control-sm"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="startDate" class="col-form-label">Начало обучения:</label>
                                    </div>
                                    <div class="col-auto">
                                        <@spring.formInput 'resumeUpdateDto.educationInfo[${eduInfoIndex}].startDate'
                                        'id="startDate" class="form-control form-control-sm"'
                                         'date' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="endDate" class="col-form-label">Конец обучения:</label>
                                    </div>
                                    <div class="col-auto">
                                        <@spring.formInput 'resumeUpdateDto.educationInfo[${eduInfoIndex}].endDate'
                                        'id="endDate" class="form-control form-control-sm"'
                                        'date' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="degree" class="col-form-label">Степень:</label>
                                    </div>
                                    <div class="col-auto">
                                        <@spring.formInput 'resumeUpdateDto.educationInfo[${eduInfoIndex}].degree'
                                        'id="degree" class="form-control form-control-sm"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
                <div class="col-1 align-items-center justify-start">
                    <a class="btn btn-danger d-flex border border-1 justify-content-center align-items-center fs-2"
                       style="height: 100px; width: 100px; text-align: center" onclick="addEdu(event)">+</a>
                </div>
            </div>
        </div>


        <p class="py-0 mb-1 mt-4">Сссылки на соц. сети:</p>
        <div class="row row-cols-1 row-cols-sm-2 row-gap-2 mb-3">
            <div class="col">
                <div class="input-group">
                    <label for="telegram" class="input-group-text py-0"><i class="bi bi-telegram fs-5"></i></label>
                    <@spring.formInput 'resumeUpdateDto.contacts[0].contactValue'
                    'class="form-control" id="telegram"' />
                    <input type="hidden" name="contacts[0].typeId" value="1">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="facebook" class="input-group-text py-0"><i class="bi bi-facebook fs-5"></i></label>
                    <@spring.formInput 'resumeUpdateDto.contacts[1].contactValue'
                    'class="form-control" id="facebook"' />
                    <input type="hidden" name="contacts[1].typeId" value="4">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="email" class="input-group-text py-0"><i class="bi bi-envelope-at fs-5"></i></label>
                    <@spring.formInput 'resumeUpdateDto.contacts[2].contactValue'
                    'class="form-control" id="email"' />
                    <input type="hidden" name="contacts[2].typeId" value="2">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="linkedIn" class="input-group-text py-0"><i class="bi bi-linkedin fs-5"></i></label>
                    <@spring.formInput 'resumeUpdateDto.contacts[3].contactValue'
                    'class="form-control" id="linkedIn"' />
                    <input type="hidden" name="contacts[3].typeId" value="5">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="phoneNumber" class="input-group-text py-0"><i class="bi bi-telephone fs-5"></i></label>
                    <@spring.formInput 'resumeUpdateDto.contacts[4].contactValue'
                    'class="form-control" id="phoneNumber"' />
                    <input type="hidden" name="contacts[4].typeId" value="3">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
        </div>

        <div class="form-check mb-3 mt-4">
            <#if resumeUpdateDto.isActive??>
                <#if resumeUpdateDto.isActive>
                <#--                <@spring.checkSelected />-->
                <#--                <@spring.formCheckbox />-->
                    <input class="form-check-input"  type="checkbox" value="true" name="isActive" id="isActive" checked>
                <#else>
                    <input class="form-check-input"  type="checkbox" value="true" name="isActive" id="isActive">
                </#if>
            <#else>
                <input class="form-check-input"  type="checkbox" value="true" name="isActive" id="isActive">
            </#if>
            <label class="form-check-label" for="isActive">
                Активно
            </label>
        </div>

        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                   value="${(_csrf.token)!'--no-token--'}"/>
        </#if>

        <button type="submit" class="btn btn-success w-100">Сохранить изменения</button>
    </form>

    <script src="https://code.jquery.com/jquery-3.6.1.min.js"
            integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
    <script>
        'use strict'

        function addWorkExp(event) {
            event.preventDefault();
            appendWorkExp()
        }

        function addEdu(event) {
            event.preventDefault();
            appendEdu()
        }

        function appendEdu () {
            <#assign eduInfoIndex++>
            $('#eduInfo').append('<div class="col-12 col-sm-10 col-lg-8 col-xl-7"> ' +
                '<div class="card">\n' +
                '                        <div class="card-body">\n' +
                '                            <h5 class="card-title">Образование:</h5>\n' +
                '                            <div class="card-text row my-1">\n' +
                '                                <div class="col-auto">\n' +
                '                                    <label for="institution" class="col-form-label">Учреждение:</label>\n' +
                '                                </div>\n' +
                '                                <div class="col-auto">\n' +
                '                                    <input type="text" id="institution" class="form-control form-control-sm" name="educationInfo[' + ${eduInfoIndex} + '].institution">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="card-text row my-1">\n' +
                '                                <div class="col-auto">\n' +
                '                                    <label for="program" class="col-form-label">Программа:</label>\n' +
                '                                </div>\n' +
                '                                <div class="col-auto">\n' +
                '                                    <input type="text" id="program" class="form-control form-control-sm" name="educationInfo[' + ${eduInfoIndex} + '].program">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="card-text row my-1">\n' +
                '                                <div class="col-auto">\n' +
                '                                    <label for="startDate" class="col-form-label">Начало обучения:</label>\n' +
                '                                </div>\n' +
                '                                <div class="col-auto">\n' +
                '                                    <input type="date" id="startDate" class="form-control form-control-sm" name="educationInfo[' + ${eduInfoIndex} + '].startDate">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="card-text row my-1">\n' +
                '                                <div class="col-auto">\n' +
                '                                    <label for="endDate" class="col-form-label">Конец обучения:</label>\n' +
                '                                </div>\n' +
                '                                <div class="col-auto">\n' +
                '                                    <input type="date" id="endDate" class="form-control form-control-sm" name="educationInfo[' + ${eduInfoIndex} + '].endDate">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="card-text row my-1">\n' +
                '                                <div class="col-auto">\n' +
                '                                    <label for="degree" class="col-form-label">Степень:</label>\n' +
                '                                </div>\n' +
                '                                <div class="col-auto">\n' +
                '                                    <input type="text" id="degree" class="form-control form-control-sm" name="educationInfo[' + ${eduInfoIndex} + '].degree">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>' +
                '</div> ')
        }

        function appendWorkExp () {
            <#assign workExpIndex++>
            $('#workExpInfo').append('<div class="col-12 col-sm-10 col-lg-8 col-xl-7">' +
                '<div class="card">\n' +
                '                        <div class="card-body">\n' +
                '                            <h5 class="card-title">Опыт работы: </h5>\n' +
                '                            <div class="card-text row my-1">\n' +
                '                                <div class="col-12 col-sm-auto">\n' +
                '                                    <label for="years" class="col-form-label">Год работы:</label>\n' +
                '                                </div>\n' +
                '                                <div class="col-12 col-sm-auto">\n' +
                '                                    <input type="number" id="years" class="form-control form-control-sm" name="workExperienceInfo[' + ${workExpIndex} + '].years">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">\n' +
                '                                <div class="col-12 col-sm-auto">\n' +
                '                                    <label for="companyName" class="col-form-label">Название компании:</label>\n' +
                '                                 </div>\n' +
                '                                <div class="col-12 col-sm-auto">\n' +
                '                                    <input type="text" id="companyName" class="form-control form-control-sm" name="workExperienceInfo[' + ${workExpIndex} + '].companyName">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">\n' +
                '                                <div class="col-12 col-sm-auto">\n' +
                '                                    <label for="position" class="col-form-label">Должность:</label>\n' +
                '                                </div>\n' +
                '                                <div class="col-12 col-sm-auto">\n' +
                '                                    <input type="text" id="position" class="form-control form-control-sm" name="workExperienceInfo[' + ${workExpIndex} + '].position">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">\n' +
                '                                <div class="col-12 col-sm-auto">\n' +
                '                                    <label for="responsibilities" class="col-form-label">Обязанности:</label>\n' +
                '                                </div>\n' +
                '                                <div class="col-12 col-sm-auto">\n' +
                '                                    <input type="text" id="responsibilities" class="form-control form-control-sm" name="workExperienceInfo[' + ${workExpIndex} + '].responsibilities">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>' +
                '</div> ');
        }
    </script>
</@main.layout>