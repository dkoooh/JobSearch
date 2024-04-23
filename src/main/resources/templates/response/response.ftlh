<#import "../layout.ftlh" as main>

<@main.layout>
    <h4>Отклики: </h4>
    <#if responses??>
        <#if responses?size !=0>
            <div class="row gap-2">
                <#list responses as response>
                    <div class="card" style="width: 18rem;">
                        <div class="card-body">
                            <h5 class="card-title"><a href = "/vacancies/${response.VACANCY.id}"
                                                      style="text-decoration: none; color: black">${response.VACANCY.name}
                                </a></h5>
                            <h6 class="card-subtitle mb-2 text-body-secondary">${response.VACANCY.category}</h6>
                            <p class="card-text">${response.VACANCY.author.name} ${response.VACANCY.author.surname}</p>
                        </div>
                        <div class="card-footer bg-white border-0">
                            <a href="/chats/${response.ID}" class="btn btn-outline-info">Open the chat</a>
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