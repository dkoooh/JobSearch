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
                            <i class="bi bi-person-circle"></i> ${resume.applicant.name}
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
                                    <a href="/responses/chooseVacancy?resumeId=${resume.id}"
                                       class="btn btn-success fw-medium">
                                        Откликнуться
                                    </a>
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
</@main.layout>