<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-2 row-cols-md-3 gap-3 mb-4 justify-content-center">
        <div class="col px-0" style="max-width: 128px">
            <img src="/api/users/user/image" class="img w-100 my-auto" alt="avatar">
        </div>
        <div class="col">
            <h3>${user.name} ${user.surname!("")}</h3>
            <#if user.accountType == "APPLICANT">
                <p>Возраст: ${user.age}</p>
            </#if>
            <#if authorizedUser??>
                <#if authorizedUser.name == user.email>
                    <a href="/account/edit" type="button" class="btn btn-success" id="editBtn">Редактировать</a>
                </#if>
            </#if>
        </div>
        <div class="col my-auto d-flex flex-row gap-3 gap-md-2 justify-content-center">
            <#if authorizedUser??>
                <#if authorizedUser.name == user.email>
                    <#if user.accountType = "APPLICANT">
                        <a href="/resumes/create" class="btn btn-danger d-flex justify-content-center align-items-center"
                           style="height: 100px; width: 100px; text-align: center">+<br>Создать<br>резюме</a>
                    <#else>
                        <a href="/vacancies/create" class="btn btn-danger d-flex justify-content-center align-items-center"
                           style="height: 100px; width: 100px; text-align: center">+<br>Создать<br>вакансию</a>
                    </#if>
                    <a href="/responses" class="btn btn-light d-flex border border-1 justify-content-center align-items-center"
                       style="height: 100px; width: 100px; text-align: center">Отклики</a>
                </#if>
            </#if>
        </div>
    </div>
    <#if user.accountType = "APPLICANT">
        <h5>Резюме: </h5>
        <#if userResumes??>
            <#if userResumes.content?size != 0>
                <div class="row rol-cols gap-2">
                    <#list userResumes.content as resume>
                        <div class="col-7 col-sm-4 col-md-3 px-auto px-sm-0">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h5 class="card-title"><a href="/resumes/${resume.id}" class="text-decoration-none text-black">
                                        ${resume.name}
                                        </a></h5>
                                    <p class="card-text">Дата обновления: <br>${resume.updateTime}</p>
                                </div>
                                <div class="card-footer bg-white border-0 gap-1 mt-auto">
                                    <#if authorizedUser??>
                                        <#if authorizedUser.name == user.email>
                                            <a href="#" class="btn btn-info text-white wh m-1" style="padding: 3px 5px;">Обновить</a>
                                            <a href="/resumes/${resume.id}/edit" class="btn btn-success m-1" style="padding: 3px 5px;">Ред.</a>
                                        <#else>
                                            <a href="/res" class="btn btn-success m-1" style="padding: 3px 5px;">Откликнуться</a>
                                        </#if>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#list>
                    <nav class="my-5">
                        <#assign page = userResumes.number + 1>
                        <ul class="pagination">
                            <li class="page-item">
                                <#if page == 1>
                                    <a class="page-link disabled" href="#" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                <#else>
                                    <a class="page-link" href="/vacancies?page=${page-1}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </#if>
                            </li>
                            <#list 1..userResumes.getTotalPages() as p>
                                <#if p == userResumes.getNumber() + 1>
                                    <li class="page-item"><a class="page-link active" href="#">${p}</a></li>
                                <#else>
                                    <li class="page-item"><a class="page-link" href="/vacancies?page=${p}">${p}</a></li>
                                </#if>
                            </#list>
                            <li class="page-item">
                                <#if page == userResumes.getTotalPages()>
                                    <a class="page-link disabled" href="/" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                <#else>
                                    <a class="page-link" href="/vacancies?page=${page-1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </#if>
                            </li>
                        </ul>
                    </nav>
                </div>
            <#else>
                <h5>Резюме не найдены</h5>
            </#if>
        <#else>
            <h5>Резюме не найдены</h5>
        </#if>
    </#if>
    <#if user.accountType = "EMPLOYER">
        <h5>Вакансии: </h5>
        <#if (vacancies)??>
            <#if vacancies.content?size != 0>
                <div class="row rol-cols gap-2">
                    <#list vacancies.content as vacancy>
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
                                    <#if authorizedUser.name == user.email>
                                        <a href="#" class="btn btn-info text-white wh m-1" style="padding: 3px 5px;">Обновить</a>
                                        <a href="/vacancies/${vacancy.id}/edit" class="btn btn-success m-1" style="padding: 3px 5px;">Ред.</a>
                                    <#else>
                                        <#if authorizedUser.principal.accountType == "APPLICANT">
                                            <a href="#" class="btn btn-success m-1" style="padding: 3px 5px;">Откликнуться</a>
                                        </#if>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#list>
                    <nav class="my-5">
                        <ul class="pagination">
                            <#assign page = vacancies.number + 1>
                            <li class="page-item">
                                <#if page == 1>
                                    <a class="page-link disabled" href="#" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                <#else>
                                    <a class="page-link" href="/vacancies?${attributes!("")}page=${page-1}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </#if>
                            </li>
                            <#list 1..vacancies.getTotalPages() as p>
                                <#if p == vacancies.getNumber() + 1>
                                    <li class="page-item"><a class="page-link active" href="#">${p}</a></li>
                                <#else>
                                    <li class="page-item"><a class="page-link" href="/vacancies?${attributes!("")}page=${p}">${p}</a></li>
                                </#if>
                            </#list>
                            <li class="page-item">
                                <#if page == vacancies.getTotalPages()>
                                    <a class="page-link disabled" href="/" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                <#else>
                                    <a class="page-link" href="/vacancies?${attributes!("")}page=${page-1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </#if>
                            </li>
                        </ul>
                    </nav>
                </div>
            <#else>
                <h5>Вакансии не найдены</h5>
            </#if>
        <#else>
            <h5>Вакансии не найдены</h5>
        </#if>
    </#if>
</@main.layout>
