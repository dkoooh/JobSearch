<#import "../layout.ftlh" as main>

<@main.layout>
    <div class="row row-cols-2 row-cols-md-3 gap-3 mb-4 justify-content-center">
        <div class="col px-0" style="max-width: 128px">
            <#if user.avatar??>
                <img src="api/users/user/image" class="img w-100 my-auto" alt="avatar">
            <#else>
                <img src="#дефолт_аватар" class="img w-100 my-auto" alt = avatar>
            </#if>
        </div>
        <div class="col">
            <h3>${user.name} ${user.surname}</h3>
            <#if user.accountType == "applicant">
                <p>Возраст: ${user.age}</p>
            </#if>
            <a href="/users/profile/edit" class="btn btn-success">Редактировать</a>
        </div>
        <div class="col my-auto d-flex flex-row gap-3 gap-md-2 justify-content-center">
            <a class="btn btn-danger d-flex justify-content-center align-items-center"
               style="height: 100px; width: 100px; text-align: center">
                <#if user.accountType = "applicant">
                    +<br>Создать<br>резюме
                <#else>
                    +<br>Создать<br>вакансию
                </#if>
            </a>
            <a class="btn btn-light d-flex border border-1 justify-content-center align-items-center"
               style="height: 100px; width: 100px; text-align: center">Отклики</a>
        </div>
    </div>
    <#if user.accountType == "applicant">
        <h5>Резюме: </h5>
        <div class="row rol-cols gap-2">
            <#list userResumes as resume>
                <div class="col-7 col-sm-4 col-md-3 px-auto px-sm-0">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><a href="/resumes/${resume.id}" class="text-decoration-none text-black">
                                ${resume.name}
                                </a></h5>
                            <p class="card-text">Дата обновления: <br>${resume.updateTime}</p>
                        </div>
                        <div class="card-footer bg-white border-0 gap-1 mt-auto">
                            <a href="#" class="btn btn-info text-white wh m-1" style="padding: 3px 5px;">Обновить</a>
                            <a href="/resumes/${resume.id}/edit" class="btn btn-success m-1" style="padding: 3px 5px;">Ред.</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    <#else>
        <h5>Вакансии: </h5>
        <div class="row rol-cols gap-2">
            <#list vacancies as vacancy>
                <div class="col-7 col-sm-4 col-md-3 px-auto px-sm-0">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">
                                <a href="/vacancies/${vacancy.id}" class="text-decoration-none text-black">
                                    ${vacancy.name}
                                </a>
                            </h5>
                            <p class="card-text">Дата обновления: <br>${vacancy.updateTime}</p>
                        </div>
                        <div class="card-footer bg-white border-0 gap-1 mt-auto">
                            <a href="#" class="btn btn-info text-white wh m-1" style="padding: 3px 5px;">Обновить</a>
                            <a href="/vacancies/${vacancy.id}/edit" class="btn btn-success m-1" style="padding: 3px 5px;">Ред.</a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</@main.layout>
