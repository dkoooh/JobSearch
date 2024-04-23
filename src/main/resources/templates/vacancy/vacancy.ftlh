<#import "../layout.ftlh" as main>

<@main.layout>
    <div class="row row-cols-1 row-cols-md-2 justify-content-md-evenly align-items-start">
        <div class="card col-9 col-md-8">
            <div class="card-body">
                <h3 class="card-title">${vacancy.name}</h3>
                <#if vacancy.salary?? && vacancy.salary gt 0>
                    <h5 class="card-subtitle">${vacancy.salary} сом</h5>
                </#if>
                <h6 class="card-subtitle my-1">- Категория: ${vacancy.category}</h6>
                <#if vacancy.expFrom??>
                    <#if vacancy.expTo?? && vacancy.expTo != 0>
                        <h6 class="card-subtitle my-1">- От ${vacancy.expFrom} до ${vacancy.expTo} лет опыта</h6>
                    <#else>
                        <h6 class="card-subtitle my-1">- От ${vacancy.expFrom} лет опыта</h6>
                    </#if>
                </#if>
                <h6 CLASS="card-subtitle my-1">- Дата создания: ${vacancy.createdDate}</h6>
                <#if vacancy.description??>
                    <p class="card-text mt-2"><pre style="white-space: pre-line;">${vacancy.description}</pre></p>
                </#if>
            </div>
            <div class="card-footer bg-white border-0">
                <a href="#" class="btn btn-success mb-2">Откликнуться</a>
            </div>

        </div>
        <div class="card px-0 col-8 col-md-3">
            <div class="card-header">
                Employer
            </div>
            <div class="card-body">
                <h3 class="card-title">${vacancy.author.name} ${vacancy.author.name}</h3>
                <h6 class="card-subtitle">${vacancy.author.email}</h6>
                <p class="card-text mt-2">Тел: ${vacancy.author.phoneNumber}</p>
            </div>
        </div>
    </div>
</@main.layout>