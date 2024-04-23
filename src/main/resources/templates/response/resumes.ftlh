<#import "../layout.ftlh" as main>

<@main.layout>
     <h3>Выберите резюме</h3>
    <#if resumes??>
        <#if resumes?size != 0>
            <div class="row row-gap-2">
                <#list resumes as resume>
                    <div class="col">
                        <div class="card h-100">
                            <div class="card-body">
                                <h5 class="card-title">
                                    <a href="/responses/respond?vacancyId=${vacancyId}&resumeId=${resume.id}"
                                       class="text-decoration-none text-black">
                                        ${resume.name}
                                    </a>
                                </h5>
                                <p class="card-subtitle">${resume.category}</p>
                                <p class="card-text">Дата обновления: <br>${resume.updateTime}</p>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <h6>Резюме не найдены</h6>
        </#if>
    <#else>
        <h6>Резюме не найдены</h6>
    </#if>
</@main.layout>