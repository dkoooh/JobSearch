<#import "../layout.ftlh" as main>

<@main.layout>
    <form class="col-md-9 col-lg-8 mx-auto my-3" action="/resumes/${resume.id}/edit" method="POST">
        <h1 class="text-center mb-4">Редактирование резюме:</h1>

        <div class="col-sm-9 col-lg-7 col-xl-6 mb-4">
            <div class="mb-2 ">
                <label for="name">Название резюме:</label>
                <input type="text" id="name" class="form-control" name="name" value="${resume.name}" required>
            </div>
            <div class="mb-2">
                <label for="category">Категория:</label>
                <select class="form-select" aria-label="Choose account type" name="categoryId" id="category" required>
                    <option disabled>Выберите категорию</option>P
                    <#list categories as category>
                        <#if resume.category == category.name>
                            <option value="${category.id}" selected>${category.name}</option>
                        <#else>
                            <option value="${category.id}">${category.name}</option>
                        </#if>
                    </#list>
                </select>
            </div>
            <div class="mb-2">
                <label for="salary">Ожидаемый уровень оклада:</label>
                <input type="number" step="0.01" id="salary" class="form-control" name="salary" value="${resume.salary}">
            </div>
        </div>

        <p class="py-0 mb-1">О себе:</p>
        <div class="row row-cols-1 row-gap-4 mb-3">
            <div class="col row row-cols-1 row-gap-2">
                <#list resume.workExperienceInfo as workExp>
                    <div class="col-12 col-sm-10 col-lg-8 col-xl-7">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Опыт работы: </h5>
                                <div class="card-text row my-1">
                                    <div class="col-12 col-sm-auto">
                                        <label for="years" class="col-form-label">Год работы:</label>
                                    </div>
                                    <div class="col-12 col-sm-auto">
                                        <input type="number" id="years" class="form-control form-control-sm" value="${workExp.years}" name="years">
                                    </div>
                                </div>
                                <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                    <div class="col-12 col-sm-auto">
                                        <label for="companyName" class="col-form-label">Название компании:</label>
                                    </div>
                                    <div class="col-12 col-sm-auto">
                                        <input type="text" id="companyName" class="form-control form-control-sm" value="${workExp.companyName}" name="companyName">
                                    </div>
                                </div>
                                <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                    <div class="col-12 col-sm-auto">
                                        <label for="position" class="col-form-label">Должность:</label>
                                    </div>
                                    <div class="col-12 col-sm-auto">
                                        <input type="text" id="position" class="form-control form-control-sm" value="${workExp.position}" name="position">
                                    </div>
                                </div>
                                <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                    <div class="col-12 col-sm-auto">
                                        <label for="responsibilities" class="col-form-label">Обязанности:</label>
                                    </div>
                                    <div class="col-12 col-sm-auto">
                                        <input type="text" id="responsibilities" class="form-control form-control-sm" value="${workExp.responsibilities}" name="responsibilities">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
                <div class="col-1 align-items-center justify-start">
                    <a class="btn btn-danger d-flex border border-1 justify-content-center align-items-center fs-2"
                       style="height: 100px; width: 100px; text-align: center">+</a>
                </div>
            </div>
            <div class="col row row-cols-1 row-gap-2">
                <#list resume.educationInfo as eduInfo>
                    <div class="col-12 col-sm-10 col-lg-8 col-xl-7">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Образование:</h5>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="institution" class="col-form-label">Учреждение:</label>
                                    </div>
                                    <div class="col-auto">
                                        <input type="text" id="institution" class="form-control form-control-sm" value="${eduInfo.institution}" name="institution">
                                    </div>
                                </div>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="program" class="col-form-label">Программа:</label>
                                    </div>
                                    <div class="col-auto">
                                        <input type="text" id="program" class="form-control form-control-sm" value="${eduInfo.program}" name="program">
                                    </div>
                                </div>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="startDate" class="col-form-label">Начало обучения:</label>
                                    </div>
                                    <div class="col-auto">
                                        <input type="date" id="startDate" class="form-control form-control-sm" value="${eduInfo.startDate}" name="startDate">
                                    </div>
                                </div>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="endDate" class="col-form-label">Конец обучения:</label>
                                    </div>
                                    <div class="col-auto">
                                        <input type="date" id="endDate" class="form-control form-control-sm" value="${eduInfo.endDate}" name="endDate">
                                    </div>
                                </div>
                                <div class="card-text row my-1">
                                    <div class="col-auto">
                                        <label for="degree" class="col-form-label">Степень:</label>
                                    </div>
                                    <div class="col-auto">
                                        <input type="text" id="degree" class="form-control form-control-sm" value="${eduInfo.degree}" name="degree">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
                <div class="col-1 align-items-center justify-start">
                    <a class="btn btn-danger d-flex border border-1 justify-content-center align-items-center fs-2"
                       style="height: 100px; width: 100px; text-align: center">+</a>
                </div>
            </div>
        </div>


        <p class="py-0 mb-1 mt-4">Сссылки на соц. сети:</p>
        <div class="row row-cols-1 row-cols-sm-2 row-gap-2 mb-3">
            <#list resume.contactInfos as contact>
                <#if contact.typeId == 1>
                    <div class="col">
                        <div class="input-group">
                            <label for="telegram" class="input-group-text py-0"><i class="bi bi-telegram fs-5"></i></label>
                            <input class="form-control" type="text" id="telegram" value="${contact.value}">
                        </div>
                    </div>
                <#else>
                    <div class="col">
                        <div class="input-group">
                            <label for="telegram" class="input-group-text py-0"><i class="bi bi-telegram fs-5"></i></label>
                            <input class="form-control" type="text" id="telegram">
                        </div>
                    </div>
                </#if>
                <#if contact.typeId == 4>
                    <div class="col">
                        <div class="input-group">
                            <label for="facebook" class="input-group-text py-0"><i class="bi bi-facebook fs-5"></i></label>
                            <input class="form-control" type="text" id="facebook" value="${contact.value}">
                        </div>
                    </div>
                <#else>
                    <div class="col">
                        <div class="input-group">
                            <label for="facebook" class="input-group-text py-0"><i class="bi bi-facebook fs-5"></i></label>
                            <input class="form-control" type="text" id="facebook">
                        </div>
                    </div>
                </#if>
                <#if contact.typeId == 2>
                    <div class="col">
                        <div class="input-group">
                            <label for="email" class="input-group-text py-0"><i class="bi bi-envelope-at fs-5"></i></label>
                            <input class="form-control" type="email" id="email" value="${contact.value}">
                        </div>
                    </div>
                <#else>
                    <div class="col">
                        <div class="input-group">
                            <label for="email" class="input-group-text py-0"><i class="bi bi-envelope-at fs-5"></i></label>
                            <input class="form-control" type="email" id="email">
                        </div>
                    </div>
                </#if>
                <#if contact.typeId == 5>
                    <div class="col">
                        <div class="input-group">
                            <label for="linkedIn" class="input-group-text py-0"><i class="bi bi-linkedin fs-5"></i></label>
                            <input class="form-control" type="text" id="linkedIn" value="${contact.value}">
                        </div>
                    </div>
                <#else>
                    <div class="col">
                        <div class="input-group">
                            <label for="linkedIn" class="input-group-text py-0"><i class="bi bi-linkedin fs-5"></i></label>
                            <input class="form-control" type="text" id="linkedIn">
                        </div>
                    </div>
                </#if>
                <#if contact.typeId == 3>
                    <div class="col">
                        <div class="input-group">
                            <label for="phoneNumber" class="input-group-text py-0"><i class="bi bi-telephone fs-5"></i></label>
                            <input class="form-control" type="tel" id="phoneNumber" value="${contact.value}">
                        </div>
                    </div>
                <#else>
                    <div class="col">
                        <div class="input-group">
                            <label for="phoneNumber" class="input-group-text py-0"><i class="bi bi-telephone fs-5"></i></label>
                            <input class="form-control" type="tel" id="phoneNumber">
                        </div>
                    </div>
                </#if>
            </#list>

<#--            TODO сделать другим спобосом контакты-->
        </div>

        <div class="form-check mb-3 mt-4">
            <#if resume.isActive>
                <input class="form-check-input"  type="checkbox" value="true" name="isActive" id="isActive" checked>
            <#else>
                <input class="form-check-input"  type="checkbox" value="true" name="isActive" id="isActive">
            </#if>
            <label class="form-check-label" for="isActive">
                Активно
            </label>
        </div>

        <button type="submit" class="btn btn-success w-100">Сохранить изменения</button>
    </form>
</@main.layout>