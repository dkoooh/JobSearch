<#import "../layout.ftlh" as main>

<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>
    <h2 class="m-1">Вакансии:</h2>
<#--    TODO добавить название орг-ции-->

    <form class="my-3 row align-content-baseline" method="GET" action="http://localhost:1234/vacancies/categories">
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
                                    <a href="/responses/chooseResume?vacancyId=${vacancy.id}"
                                       class="btn btn-success fw-medium">
                                        Откликнуться
                                    </a>
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
                    <a class="page-link" href="/vacancies?page=${page-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </#if>
            </li>
            <#list 1..vacancies.getTotalPages() as p>
                <#if p == vacancies.getNumber() + 1>
                    <li class="page-item"><a class="page-link active" href="#">${p}</a></li>
                <#else>
                    <li class="page-item"><a class="page-link" href="/vacancies?page=${p}">${p}</a></li>
                </#if>
            </#list>
            <li class="page-item">
                <#if page == vacancies.getTotalPages()>
                    <a class="page-link disabled" href="#" aria-label="Next">
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
</@main.layout>