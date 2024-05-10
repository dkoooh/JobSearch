<#import "../../layout.ftlh" as main>

<@main.layout>
    <h4>Отклики: </h4>

    <#if responses??>
        <#if responses?size !=0>
            <div class="row row-cols-1 row-gap-2">
                <#list responses as response>
                    <div class="col col-md-10 col-lg-9">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">
                                    <a href = "/vacancies/${response.VACANCY.id}" style="text-decoration: none; color: black">
                                        ${response.VACANCY.name}
                                    </a>
                                </h5>
                                <h6 class="card-subtitle mb-2 text-body-secondary">
                                    Резюме:
                                    <a href = "/resumes/${response.RESUME.id}" style="text-decoration: none; color: black">
                                        ${response.RESUME.name}
                                    </a></h6>
                                <div class="card-text">
                                    <a href="/employer/${response.VACANCY.author.email}" class="link-secondary">
                                        ${response.VACANCY.author.name} ${response.VACANCY.author.surname}
                                    </a>
                                </div>
                                <#if response.confirmation??>
                                    <div class="card-text">
                                        Статус: ${response.CONFIRMATION?string("подтверждено", "не подтверждено")}
                                    </div>
                                </#if>
                            </div>
                            <div class="card-footer bg-white border-0">
                                <a href="/chats/${response.ID}?currentUser=${response.RESUME.applicant.id}" class="btn btn-outline-primary">Открыть чат</a>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <h6>Отклики не найдены</h6>
        </#if>
    <#else>
        <h6>Отклики не найдены</h6>
    </#if>
</@main.layout>