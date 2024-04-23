<#import "../layout.ftlh" as main>

<@main.layout>
    <form class="col-md-9 col-lg-8 mx-auto my-3" action="/resumes/create" method="POST">
        <h1 class="text-center mb-4">Создание резюме:</h1>

        <div class="col-sm-9 col-lg-7 col-xl-6 mb-4">
            <div class="mb-2 ">
                <label for="name">Название резюме:</label>
                <input type="text" id="name" class="form-control" name="name" required>
            </div>
            <div class="mb-2">
                <label for="category">Категория:</label>
                <select class="form-select" aria-label="Choose account type" name="categoryId" id="category" required>
                    <option selected disabled>Выберите категорию</option>P
                    <#list categories as category>
                        <option value="${category.id}">${category.name}</option>
                    </#list>
                </select>
            </div>
            <div class="mb-2">
                <label for="salary">Ожидаемый уровень оклада:</label>
                <input type="number" id="salary" class="form-control" step="0.01" name="salary">
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
                                    <input type="number" id="years" class="form-control form-control-sm" name="workExperienceInfo[0].years">
                                </div>
                            </div>
                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                <div class="col-12 col-sm-auto">
                                    <label for="companyName" class="col-form-label">Название компании:</label>
                                </div>
                                <div class="col-12 col-sm-auto">
                                    <input type="text" id="companyName" class="form-control form-control-sm" name="workExperienceInfo[0].companyName">
                                </div>
                            </div>
                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                <div class="col-12 col-sm-auto">
                                    <label for="position" class="col-form-label">Должность:</label>
                                </div>
                                <div class="col-12 col-sm-auto">
                                    <input type="text" id="position" class="form-control form-control-sm" name="workExperienceInfo[0].position">
                                </div>
                            </div>
                            <div class="card-text row row-cols-1 row-cols-sm-2 my-1">
                                <div class="col-12 col-sm-auto">
                                    <label for="responsibilities" class="col-form-label">Обязанности:</label>
                                </div>
                                <div class="col-12 col-sm-auto">
                                    <input type="text" id="responsibilities" class="form-control form-control-sm" name="workExperienceInfo[0].responsibilities">
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
                                    <input type="text" id="institution" class="form-control form-control-sm" name="educationInfo[0].institution">
                                </div>
                            </div>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="program" class="col-form-label">Программа:</label>
                                </div>
                                <div class="col-auto">
                                    <input type="text" id="program" class="form-control form-control-sm" name="educationInfo[0].program">
                                </div>
                            </div>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="startDate" class="col-form-label">Начало обучения:</label>
                                </div>
                                <div class="col-auto">
                                    <input type="date" id="startDate" class="form-control form-control-sm" name="educationInfo[0].startDate">
                                </div>
                            </div>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="endDate" class="col-form-label">Конец обучения:</label>
                                </div>
                                <div class="col-auto">
                                    <input type="date" id="endDate" class="form-control form-control-sm" name="educationInfo[0].endDate">
                                </div>
                            </div>
                            <div class="card-text row my-1">
                                <div class="col-auto">
                                    <label for="degree" class="col-form-label">Степень:</label>
                                </div>
                                <div class="col-auto">
                                    <input type="text" id="degree" class="form-control form-control-sm" name="educationInfo[0].degree">
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
<#--            TODO 2-ой инпут - временный костыль-->
            <div class="col">
                <div class="input-group">
                    <label for="telegram" class="input-group-text py-0"><i class="bi bi-telegram fs-5"></i></label>
                    <input class="form-control" type="text" id="telegram" name="contacts[0].contactValue">
                    <input style="display: none" type="number" name="contacts[0].typeId" value="1">
                </div>
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="facebook" class="input-group-text py-0"><i class="bi bi-facebook fs-5"></i></label>
                    <input class="form-control" type="text" id="facebook" name="contacts[1].contactValue">
                    <input style="display: none" type="number" name="contacts[1].typeId" value="4">
                </div>
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="email" class="input-group-text py-0"><i class="bi bi-envelope-at fs-5"></i></label>
                    <input class="form-control" type="email" id="email" name="contacts[2].contactValue">
                    <input style="display: none" type="number" name="contacts[2].typeId" value="2">
                </div>
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="linkedIn" class="input-group-text py-0"><i class="bi bi-linkedin fs-5"></i></label>
                    <input class="form-control" type="text" id="linkedIn" name="contacts[3].contactValue">
                    <input style="display: none" type="number" name="contacts[3].typeId" value="5">
                </div>
            </div>
            <div class="col">
                <div class="input-group">
                    <label for="phoneNumber" class="input-group-text py-0"><i class="bi bi-telephone fs-5"></i></label>
                    <input class="form-control" type="tel" id="phoneNumber" name="contacts[4].contactValue">
                    <input style="display: none" type="number" name="contacts[4].typeId" value="3">
                </div>
            </div>

        </div>

        <div class="form-check mb-3 mt-4">
            <input class="form-check-input"  type="checkbox" value="true" name="isActive" id="isActive">
            <label class="form-check-label" for="isActive">
               Активно
            </label>
        </div>

        <button type="submit" class="btn btn-success w-100">Опубликовать</button>


        <script src="https://code.jquery.com/jquery-3.6.1.min.js"
                integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
        <script src="/static/js/resume/create.js">
        </script>
    </form>
</@main.layout>