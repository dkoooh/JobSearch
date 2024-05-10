<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-2 row-cols-md-3 gap-3 mb-4 justify-content-center">
        <div class="col px-0" style="max-width: 128px">
            <img src="/api/users/user/image/${user.email}" class="img w-100 my-auto" alt="avatar">
        </div>
        <div class="col">
            <h3>${user.name} ${user.surname!("")}</h3>
            <#if user.accountType == "APPLICANT">
                <p>Возраст: ${user.age}</p>
            </#if>
            <#if (authorizedUser)??>
                <#if authorizedUser.name == user.email>
                    <a href="/account/edit" type="button" class="btn btn-success" id="editBtn">Редактировать</a>
                </#if>
            </#if>
        </div>
        <div class="col my-auto d-flex flex-row gap-3 gap-md-2 justify-content-center">
            <#if (authorizedUser)??>
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
                                    <#if authorizedUser.name == user.email>
                                        <a href="#" class="btn btn-info text-white wh m-1" style="padding: 3px 5px;">Обновить</a>
                                        <a href="/resumes/${resume.id}/edit" class="btn btn-success m-1" style="padding: 3px 5px;">Ред.</a>
                                    <#else>
                                        <a href="/res" class="btn btn-success m-1" style="padding: 3px 5px;">Откликнуться</a>

                                        <button type="button" id="${resume.id}" class="btn btn-success m-1" style="padding: 3px 5px;"
                                                data-bs-target="#vacancyChoiceModal" data-bs-toggle="modal">
                                            Откликнуться
                                        </button>
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
                <div class="modal fade" id="vacancyChoiceModal" tabindex="-1" aria-labelledby="vacancyChoiceModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-scrollable modal-lg">
                        <div class="modal-content rounded-4">
                            <div class="modal-header border-0">
                                <h1 class="modal-title fs-5" id="vacancyChoiceModalLabel">Выберите вакансию: </h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div id="vacancies" class="row row-cols-1 row-gap-2">

                                </div>
                            </div>
                        </div>
                    </div>
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
                                    <#if authorizedUser??>
                                        <#if authorizedUser.name == user.email>
                                            <a href="#" class="btn btn-info text-white wh m-1" style="padding: 3px 5px;">Обновить</a>
                                            <a href="/vacancies/${vacancy.id}/edit" class="btn btn-success m-1" style="padding: 3px 5px;">Ред.</a>
                                        <#else>
                                            <#if authorizedUser.principal.accountType == "APPLICANT">
                                                <button type="button" class="btn btn-success m-1" style="padding: 3px 5px;"
                                                        id="${vacancy.id}" data-bs-target="#resumeChoiceModal" data-bs-toggle="modal">
                                                    Откликнуться
                                                </button>
                                            </#if>
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
                <div class="modal fade" id="resumeChoiceModal" tabindex="-1" aria-labelledby="resumeChoiceModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog modal-dialog-scrollable modal-lg">
                        <div class="modal-content rounded-4 fw-medium">
                            <div class="modal-header border-0">
                                <h1 class="modal-title fs-5" id="resumeChoiceModalLabel">Выберите вакансию: </h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div id="resumes" class="row row-cols-1 row-gap-2">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <#else>
                <h5>Вакансии не найдены</h5>
            </#if>
        <#else>
            <h5>Вакансии не найдены</h5>
        </#if>
    </#if>

    <script>
        let resumesModal = document.getElementById('resumeChoiceModal');
        resumesModal.addEventListener('show.bs.modal', onShowResumesModal);

        async function onShowResumesModal(event) {
            try {
                let resumesRequest = await fetch(`http://localhost:1234/api/resumes/applicant`);
                if (resumesRequest.ok) {
                    let resumes = await resumesRequest.json()

                    document.getElementById('resumes').innerHTML = '';
                    for (let resume of resumes) {
                        let resumeHtml = document.createElement('div')
                        resumeHtml.classList.add('col')

                        let salary;
                        if (!resume['salary'] || resume['salary'] === 0.0) {
                            salary = 'Договорная'
                        } else {
                            salary = resume['salary'] + ' сом'
                        }

                        let exp = "";
                        if (resume['workExperienceInfo'].length !== 0) {
                            let years = 0;
                            for (let info of resume['workExperienceInfo']) {
                                years += info['years'];
                            }

                            if (years % 10 === 1) {
                                exp = "Опыт: " + years + ' год';
                            } else if (years % 10 > 1 && years % 10 < 5) {
                                exp = "Опыт: " + years + ' года';
                            } else {
                                exp = "Опыт: " + years + ' лет';
                            }
                        }

                        resumeHtml.innerHTML = `<div class="card border-0 bg-body-tertiary rounded-4">
                                <div class="card-body px-md-4">
                                    <h5 class="m-0 p-0">
                                        <form action="/responses/respond" method="post">
                                            <input type="hidden" name="resumeId" value="` + resume['id'] + `">
                                            <input type="hidden" name="vacancyId" value="` + event.relatedTarget.id + `">
                                            <#if _csrf??>
                                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                                       value="${(_csrf.token)!'--no-token--'}"/>
                                            </#if>
                                            <button type="submit" class="border-0 bg-body-tertiary p-0 m-0 fw-medium
                                             link-primary">` + resume['name'] + `</button>
                                        </form>
                                    </h5>
                                    <h3 class="card-title">` + salary + `</h3>
                                    <p class="card-text m-0 p-0">` + exp + `</p>
                                </div>
                                <div
                                        class="card-footer px-md-4 pb-3 pt-0 bg-body-tertiary border-0 d-flex rounded-4">
                                    <div class="text-body-tertiary mt-auto ">Дата создания: ` + resume['createdDate'] + `</div>
                                    <div class="text-body-tertiary ms-auto mt-auto ">Последнее обновление:
                                        ` + resume['updateTime'] + `</div>
                                </div>
                            </div>`;

                        document.getElementById('resumes').appendChild(resumeHtml)
                    }
                } else {
                    throw new Error();
                }
            } catch (error) {
                console.log(error)
            }
        }

        let vacanciesModal = document.getElementById('vacancyChoiceModal');
        vacanciesModal.addEventListener('show.bs.modal', onShowVacanciesModal);

        async function onShowVacanciesModal (event) {
            try {
                let vacanciesRequest = await fetch (`http://localhost:1234/api/vacancies/employer`);
                if (vacanciesRequest.ok) {
                    let vacancies = await vacanciesRequest.json();

                    document.getElementById('vacancies').innerHTML = '';
                    for (let vacancy of vacancies) {
                        let vacancyHtml = document.createElement('div')
                        vacancyHtml.classList.add('col')

                        let salary;
                        if (!vacancy['salary'] || vacancy['salary'] === 0.0) {
                            salary = 'Договорная'
                        } else {
                            salary = vacancy['salary'] + ' сом'
                        }

                        let exp = "";
                        if (vacancy['expFrom'] && vacancy['expFrom'] > 0) {
                            if (vacancy['expTo'] && vacancy['expTo'] > 0) {
                                exp = 'Опыт от ' + vacancy['expFrom'] + ' до ' + vacancy['expTo'];
                            } else {
                                exp = 'Опыт от ' + vacancy['expFrom']
                            }
                        }

                        vacancyHtml.innerHTML = `<div class="card border-0 bg-body-tertiary rounded-4">
                                <div class="card-body px-md-4">
                                    <h5 class="m-0 p-0">
                                        <form action="/responses/respond" method="post">
                                            <input type="hidden" name="vacancyId" value="` + vacancy['id'] + `">
                                            <input type="hidden" name="resumeId" value="` + event.relatedTarget.id + `">
                                            <#if _csrf??>
                                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                                       value="${(_csrf.token)!'--no-token--'}"/>
                                            </#if>
                                            <button type="submit" class="border-0 bg-body-tertiary p-0 m-0 fw-medium
                                             link-primary">` + vacancy['name'] + `</button>
                                        </form>
                                    </h5>
                                    <h3 class="card-title">` + salary + `</h3>
                                    <p class="card-text m-0 p-0">` + exp + `</p>
                                </div>
                                <div
                                        class="card-footer px-md-4 pb-3 pt-0 bg-body-tertiary border-0 d-flex rounded-4">
                                    <div class="text-body-tertiary mt-auto ">Дата создания: ` + vacancy['createdDate'] + `</div>
                                    <div class="text-body-tertiary ms-auto mt-auto ">Последнее обновление:
                                        ` + vacancy['updateTime'] + `</div>
                                </div>
                            </div>`;

                        document.getElementById('vacancies').appendChild(vacancyHtml)
                    }
                } else {
                    throw new Error();
                }
            } catch (error) {
                console.log(error)
            }
        }
    </script>
</@main.layout>
