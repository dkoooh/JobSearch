<#import "../layout.ftlh" as main>

<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <h2>Все вакансии:</h2>
    <form class="my-3 row align-content-baseline" method="GET" action="http://localhost:1234/resumes/categories">
        <div class="col-auto">
            <select class="form-select border-0 bg-body h-100" name="categoryId" aria-label="Default select example">
                <option selected disabled>Выберите категорию</option>
                <#list categories as category>
                    <option value="${category.id}">${category.name}</option>
                </#list>
            </select>
        </div>
        <div class="col-auto px-0">
            <button type="submit" class="btn btn-primary">Поиск</button>
        </div>
    </form>
    <div class="row row-cols-1 row-gap-2 fw-medium">
        <#list resumes.content as resume>
            <div class="col">
                <div class="card border-0 bg-body-tertiary rounded-4">
                    <div class="card-body px-md-4">
                        <h5 class="m-0 p-0">
                            <a href="/resumes/${resume.id}" class="text-decoration-none">${resume.name}</a>
                        </h5>
                        <#if resume.salary??>
                            <#if resume.salary != 0>
                                <h3 class="card-title fw-bolder">${resume.salary} сом</h3>
                            <#else>
                                <h3 class="card-title fw-bolder">Договорная</h3>
                            </#if>
                        <#else>
                            <h3 class="card-title fw-bolder">Договорная</h3>
                        </#if>

                        <a href="#" class="link-secondary text-decoration-none"> <#-- TODO профиль компании -->
                            <i class="bi bi-person-circle"></i> ${resume.applicant.name} ${resume.applicant.surname!("")}
                        </a>
<#--                        <#if resume.expFrom?? && resume.expFrom gt 0>-->
<#--                            <#if resume.expTo?? && resume.expFrom gt 0>-->
<#--                                <p class="card-text mb-0 pb-0">Опыт от ${resume.expFrom} до ${resume.expTo} лет</p>-->
<#--                            <#else>-->
<#--                                <p class="card-text mb-0 pb-0">Опыт от ${resume.expFrom} лет</p>-->
<#--                            </#if>-->
<#--                        </#if>-->

                        <#if resume.workExperienceInfo??>
                            <#assign sum = 0>
                            <#list resume.workExperienceInfo?map(info -> info.years) as yearsInJob>
                                <#assign sum += yearsInJob>
                            </#list>
                            <#assign wordForm>
                                <#if sum % 10 = 1>
                                    год
                                </#if>
                                <#if sum % 10 gt 1 && sum % 10 lt 5>
                                    года
                                </#if>
                                <#if sum % 10 gt 4 || sum % 10 = 0>
                                    лет
                                </#if>
                            </#assign>
                            <p class="card-text mb-0 pb-0">Опыт работы: ${sum} ${wordForm}</p>
                        </#if>


                    </div>

                    <div class="card-footer pb-3 bg-body-tertiary border-0 d-flex rounded-4 px-md-4">
<#--                        <#if authorizedUser??>  Незарег. пользователи не могут смотреть список резюме-->
                            <#list authorizedUser.authorities as grantedAuthority>
                                <#if grantedAuthority.authority == 'EMPLOYER'>
                                    <button type="button" data-bs-toggle="modal" id="${resume.id}" data-bs-target="#vacancyChoiceModal"
                                       class="btn btn-success fw-medium">
                                        Откликнуться
                                    </button>
                                </#if>
                            </#list>
<#--                        </#if>-->
                        <div class ="text-body-tertiary ms-auto mt-auto ">${resume.createdDate}</div>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <nav class="my-4">
        <ul class="pagination">
            <li class="page-item">
                <#if page == 1>
                    <a class="page-link disabled" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                <#else>
                    <a class="page-link" href="/resumes?page=${page-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </#if>
            </li>
            <#list 1..resumes.getTotalPages() as p>
                <#if p == resumes.getNumber() + 1>
                    <li class="page-item"><a class="page-link active" href="#">${p}</a></li>
                <#else>
                    <li class="page-item"><a class="page-link" href="/resumes?page=${p}">${p}</a></li>
                </#if>
            </#list>
            <li class="page-item">
                <#if page == resumes.getTotalPages()>
                    <a class="page-link disabled" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                <#else>
                    <a class="page-link" href="/resumes?page=${page-1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </#if>
            </li>
        </ul>
    </nav>

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

    <script>
        let modal = document.getElementById('vacancyChoiceModal');
        modal.addEventListener('show.bs.modal', onShowModal);

        async function onShowModal (event) {
            try {
                let vacanciesRequest = await fetch (`http://localhost:1234/api/vacancies/byEmployer`);
                if (vacanciesRequest.ok) {
                    let vacancies = await vacanciesRequest.json();

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
                                        <a href="/responses/respond?vacancyId=` + vacancy['id'] + `&resumeId=` + event.relatedTarget.id + `"<#--+ `&resumeId?=${resumeId}"-->
                                           class="text-decoration-none">
                                            ` + vacancy['name'] +  `
                                        </a>
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