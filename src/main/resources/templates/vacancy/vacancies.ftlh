<#import "../layout.ftlh" as main>

<@main.layout>
    <h2>Все вакансии:</h2>
    <div class="row row-cols-1 row-gap-2">
        <#list vacancies as vacancy>
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
</@main.layout>