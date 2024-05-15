<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <form class="col-md-9 col-lg-8 mx-auto my-3" action="/resumes/create" method="POST">
        <h1 class="text-center mb-4">Создание резюме:</h1>

        <div class="col-sm-9 col-lg-7 col-xl-6 mb-4">

            <#assign errorClassList = "text-danger d-block error" />

            <div class="mb-2 ">
                <label for="name">Название резюме:</label>
                <@spring.formInput 'resumeCreateDto.name' 'id="name" class="form-control"' />
<#--                <input type="text" id="name" class="form-control" name="name" required>-->
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-2">
                <label for="category">Категория:</label>
                <#assign options = {"": "Выберите категорию"}>
                <#list categories as category>
                    <#assign options += {"${category.id}": "${category.name}"}>
                </#list>
                <@spring.formSingleSelect 'resumeCreateDto.categoryId' options
                'class="form-select" aria-label="Choose account type" id="category"' />
<#--                <select class="form-select" aria-label="Choose account type" name="categoryId" id="category" required>-->
<#--                    <option selected disabled>Выберите категорию</option>P-->
<#--                    <#list categories as category>-->
<#--                        <option value="${category.id}">${category.name}</option>-->
<#--                    </#list>-->
<#--                </select>-->
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-2">
                <label for="salary">Ожидаемый уровень оклада:</label>
<#--                <input type="number" id="salary" class="form-control" step="0.01" name="salary">-->
                <@spring.formInput 'resumeCreateDto.salary' 'id="salary" class="form-control" step="0.01"' 'number' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
        </div>

        <p class="py-0 mb-1">О себе:</p>
        <div class="row row-cols-1 row-gap-4 mb-3">
            <div id="workExpInfo" class="col row row-cols-1 row-gap-2">
                <div class="col-12 col-sm-10 col-lg-8 col-xl-7">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Опыт работы: </h5>
                            <div class="card-text row my-1">
                                <div class="col-12 col-sm-auto">
                                    <label for="years" class="col-form-label">Год работы:</label>
                                </div>
                                <div class="col-12 col-sm-auto">
<#--                                    <input type="number" id="years" class="form-control form-control-sm" name="workExperienceInfo[0].years">-->
                                    <@spring.formInput 'resumeCreateDto.workExperienceInfo[0].years'
                                    'id="years" class="form-control form-control-sm"' 'number' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                <div class="col-12 col-sm-auto">
                                    <label for="companyName" class="col-form-label">Название компании:</label>
                                </div>
                                <div class="col-12 col-sm-auto">
<#--                                    <input type="text" id="companyName" class="form-control form-control-sm" name="workExperienceInfo[0].companyName">-->
                                    <@spring.formInput 'resumeCreateDto.workExperienceInfo[0].companyName'
                                     'id="companyName" class="form-control form-control-sm"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                <div class="col-12 col-sm-auto">
                                    <label for="position" class="col-form-label">Должность:</label>
                                </div>
                                <div class="col-12 col-sm-auto">
<#--                                    <input type="text" id="position" class="form-control form-control-sm" name="workExperienceInfo[0].position">-->
                                    <@spring.formInput 'resumeCreateDto.workExperienceInfo[0].position' 'id="position" class="form-control form-control-sm"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                <div class="col-12 col-sm-auto">
                                    <label for="responsibilities" class="col-form-label">Обязанности:</label>
                                </div>
                                <div class="col-12 col-sm-auto">
<#--                                    <input type="text" id="responsibilities" class="form-control form-control-sm" name="workExperienceInfo[0].responsibilities">-->
                                    <@spring.formInput 'resumeCreateDto.workExperienceInfo[0].responsibilities'
                                    'id="responsibilities" class="form-control form-control-sm"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-1 align-items-center justify-start">
                    <button id="add-work-exp" onclick="addWorkExp(event)" class="btn btn-danger d-flex border border-1 justify-content-center align-items-center fs-2"
                       style="height: 100px; width: 100px; text-align: center">+</button>
                </div>
            </div>
            <div id="eduInfo" class="col row row-cols-1 row-gap-2">
                <div class="col-12 col-sm-10 col-lg-8 col-xl-7 row-gap-2">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Образование:</h5>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="institution" class="col-form-label">Учреждение:</label>
                                </div>
                                <div class="col-auto">
<#--                                    <input type="text" id="institution" class="form-control form-control-sm" name="educationInfo[0].institution">-->
                                    <@spring.formInput 'resumeCreateDto.educationInfo[0].institution'
                                    'id="institution" class="form-control form-control-sm"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="program" class="col-form-label">Программа:</label>
                                </div>
                                <div class="col-auto">
<#--                                    <input type="text" id="program" class="form-control form-control-sm" name="educationInfo[0].program">-->
                                    <@spring.formInput 'resumeCreateDto.educationInfo[0].program'
                                    'id="program" class="form-control form-control-sm"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="startDate" class="col-form-label">Начало обучения:</label>
                                </div>
                                <div class="col-auto">
<#--                                    <input type="date" id="startDate" class="form-control form-control-sm" name="educationInfo[0].startDate">-->
                                    <@spring.formInput 'resumeCreateDto.educationInfo[0].startDate'
                                    'id="startDate" class="form-control form-control-sm"' 'date' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="endDate" class="col-form-label">Конец обучения:</label>
                                </div>
                                <div class="col-auto">
<#--                                    <input type="date" id="endDate" class="form-control form-control-sm" name="educationInfo[0].endDate">-->
                                    <@spring.formInput 'resumeCreateDto.educationInfo[0].endDate'
                                    'id="endDate" class="form-control form-control-sm"' 'date' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="degree" class="col-form-label">Степень:</label>
                                </div>
                                <div class="col-auto">
<#--                                    <input type="text" id="degree" class="form-control form-control-sm" name="educationInfo[0].degree">-->
                                    <@spring.formInput 'resumeCreateDto.educationInfo[0].degree'
                                    'id="degree" class="form-control form-control-sm"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-1 align-items-center justify-start">
                    <button onclick="addEdu(event)" id="add-edu" class="btn btn-danger d-flex border border-1 justify-content-center align-items-center fs-2"
                       style="height: 100px; width: 100px; text-align: center">+</button>
                </div>
            </div>
        </div>

        <p class="py-0 mb-1 mt-4">Сссылки на соц. сети:</p>
        <div class="row row-cols-1 row-cols-sm-2 row-gap-2 mb-3">
            <div class="col">
                <div class="input-group">
                    <label for="telegram" class="input-group-text py-0"><i class="bi bi-telegram fs-5"></i></label>
                    <@spring.formInput 'resumeCreateDto.contacts[0].contactValue' 'class="form-control" id="telegram"' />
                    <input type="hidden" name="contacts[0].typeId" value="1">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="facebook" class="input-group-text py-0"><i class="bi bi-facebook fs-5"></i></label>
                    <@spring.formInput 'resumeCreateDto.contacts[1].contactValue' 'class="form-control" id="telegram"' />
                    <input type="hidden" name="contacts[1].typeId" value="4">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="email" class="input-group-text py-0"><i class="bi bi-envelope-at fs-5"></i></label>
                    <@spring.formInput 'resumeCreateDto.contacts[2].contactValue' 'class="form-control" id="email"' />
                    <input type="hidden" name="contacts[2].typeId" value="2">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="linkedIn" class="input-group-text py-0"><i class="bi bi-linkedin fs-5"></i></label>
                    <@spring.formInput 'resumeCreateDto.contacts[3].contactValue' 'class="form-control" id="linkedIn"' />
                    <input type="hidden" name="contacts[3].typeId" value="5">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="phoneNumber" class="input-group-text py-0"><i class="bi bi-telephone fs-5"></i></label>
                    <@spring.formInput 'resumeCreateDto.contacts[4].contactValue' 'class="form-control" id="phoneNumber"' />
                    <input type="hidden" name="contacts[4].typeId" value="3">
                </div>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>

        </div>

        <div class="form-check mb-3 mt-4">
            <@spring.formInput 'resumeCreateDto.isActive' 'class="form-check-input" value="true" id="isActive"' 'checkbox'/>
            <label class="form-check-label" for="isActive">
               Активно
            </label>
        </div>

        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                   value="${(_csrf.token)!'--no-token--'}"/>
        </#if>

        <button type="submit" class="btn btn-success w-100">Опубликовать</button>


        <script src="https://code.jquery.com/jquery-3.6.1.min.js"
                integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
        <script src="/static/js/resume/append.js">
        </script>
        <script src="/static/js/resume/create.js">
        </script>
    </form>
</@main.layout>