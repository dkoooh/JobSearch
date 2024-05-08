<#import "../layout.ftlh" as main>

<@main.layout; spring>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <h2 class="m-1">Вакансии:</h2>
    <form class="my-3 row align-content-baseline" method="GET" action="http://localhost:1234/vacancies">
        <div class="col-auto">
            <select class="form-select border-0 bg-body h-100" name="categoryId">
                <option selected disabled>Выберите категорию</option>
                <#list categories as category>
                    <option value="${category.id}">${category.name}</option>
                </#list>
            </select>
        </div>
        <div class="col-auto">
            <select class="form-select border-0 bg-body h-100" name="sortedBy">
                <option value="createdDate">По дате публикации</option>
                <option value="responses">По откликам</option>
            </select>
        </div>
        <div class="col-auto px-0">
            <button type="submit" class="btn btn-primary">Поиск</button>
        </div>
    </form>
    <div class="row row-cols-1 row-gap-2 fw-medium">
        <#list vacancies.content as vacancy>
            <div class="col">
                <div class="card border-0 bg-body-tertiary rounded-4">
                    <div class="card-body px-md-4">
                        <h5 class="m-0 p-0">
                            <a href="/vacancies/${vacancy.id}" class="text-decoration-none">${vacancy.name}</a>
                        </h5>
                        <#if vacancy.salary??>
                            <#if vacancy.salary != 0>
                                <h3 class="card-title fw-bolder">${vacancy.salary} сом</h3>
                            <#else>
                                <h3 class="card-title fw-bolder">Договорная</h3>
                            </#if>
                        <#else>
                            <h3 class="card-title fw-bolder">Договорная</h3>
                        </#if>

                        <a href="#" class="link-secondary text-decoration-none"> <#-- TODO профиль компании -->
                            <i class="bi bi-briefcase"></i> ${vacancy.author.name}
                        </a>
                        <#if vacancy.expFrom?? && vacancy.expFrom gt 0>
                            <#if vacancy.expTo?? && vacancy.expFrom gt 0>
                                <p class="card-text mb-0 pb-0">Опыт от ${vacancy.expFrom} до ${vacancy.expTo} лет</p>
                            <#else>
                                <p class="card-text mb-0 pb-0">Опыт от ${vacancy.expFrom} лет</p>
                            </#if>
                        </#if>
                    </div>

                    <div class="card-footer pb-3 bg-body-tertiary border-0 d-flex rounded-4 px-md-4">
                        <#if authorizedUser??>
                            <#list authorizedUser.authorities as grantedAuthority>
                                <#if grantedAuthority.authority == 'APPLICANT'>
                                    <button type="button" class="btn btn-success fw-medium" id="${vacancy.id}"
                                            data-bs-target="#resumeChoiceModal" data-bs-toggle="modal">
                                        Откликнуться
                                    </button>
                                </#if>
                            </#list>
                        </#if>
                        <div class="text-body-tertiary ms-auto mt-auto ">${vacancy.createdDate}</div>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <nav class="my-5">
        <ul class="pagination">
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

    <div class="modal fade" id="resumeChoiceModal" tabindex="-1" aria-labelledby="resumeChoiceModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-lg">
            <div class="modal-content rounded-4">
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

    <script>
        let modal = document.getElementById('resumeChoiceModal');
        modal.addEventListener('show.bs.modal', onShowModal);

        async function onShowModal (event) {
            try {
                let resumesRequest = await fetch (`http://localhost:1234/api/resumes/applicant`);
                if (resumesRequest.ok) {
                    let resumes = await resumesRequest.json();

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
                                exp = years + ' год';
                            } else if (years % 10 > 1 && years % 10 < 5) {
                                exp = years + ' года';
                            } else {
                                exp = years + ' лет';
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
    </script>
</@main.layout>