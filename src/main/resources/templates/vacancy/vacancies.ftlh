<#import "../layout.ftlh" as main>

<@main.layout>
    <h2>Вакансии:</h2>

<#--    TODO добавить название орг-ции-->

    <form class="my-3 col-12 col-sm-8 col-md-6" method="GET" action="http://localhost:1234/vacancies/categories">
        <select class="form-select" name="categoryId" aria-label="Default select example">
            <option selected disabled>Выберите категорию</option>
            <#list categories as category>
                <option value="${category.id}">${category.name}</option>
            </#list>
        </select>
        <button type="submit" class="btn btn-primary my-2">Поиск</button>
    </form>
    <div class="row row-cols-1 row-gap-2">
        <#list vacancies.content as vacancy>
            <div class="col-9">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="/vacancies/${vacancy.id}" class="text-decoration-none text-black">${vacancy.name}</a>
                        </h5>
                        <#if vacancy.salary gt 0>
                            <h6 class="card-subtitle">${vacancy.salary} сом</h6>
                        </#if>
                        <#if vacancy.expFrom?? && vacancy.expFrom gt 0>
                            <#if vacancy.expTo?? && vacancy.expFrom gt 0>
                                <p class="card-text">Опыт от ${vacancy.expFrom} до ${vacancy.expTo} лет</p>
                            <#else>
                                <p class="card-text">Опыт от ${vacancy.expFrom} лет</p>
                            </#if>
                        </#if>
                        <a href="#" class="btn btn-success">Откликнуться</a>
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