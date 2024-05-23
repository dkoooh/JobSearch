<#import "../layout.ftlh" as main>

<@main.layout; spring>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-1 row-cols-md-2 justify-content-md-evenly align-items-start">
        <div class="card col-9 col-md-8">
            <div class="card-body">
                <h3 class="card-title">${vacancy.name}</h3>
                <#if vacancy.salary?? && vacancy.salary gt 0>
                    <h5 class="card-subtitle">${vacancy.salary} <@spring.message 'vacancy.currency'/></h5>
                </#if>
                <h6 class="card-subtitle my-1">- <@spring.message 'vacancy.category'/>: ${vacancy.category.name}</h6>
                <#if vacancy.expFrom??>
                    <#if vacancy.expTo?? && vacancy.expTo != 0>
                        <h6 class="card-subtitle my-1">
                            - <@spring.message 'vacancy.exp.from'/> ${vacancy.expFrom} <@spring.message 'vacancy.exp.to'/> ${vacancy.expTo} <@spring.message 'vacancy.exp.years'/>
                        </h6>
                    <#else>
                        <h6 class="card-subtitle my-1">
                            - <@spring.message 'vacancy.exp.from'/> ${vacancy.expFrom} <@spring.message 'vacancy.exp.years'/>
                        </h6>
                    </#if>
                </#if>
                <h6 CLASS="card-subtitle my-1">- <@spring.message 'vacancy.createdDate'/>: ${vacancy.createdDate}</h6>
                <#if vacancy.description??>
                    <p class="card-text mt-2"><pre style="white-space: pre-line;">${vacancy.description}</pre></p>
                </#if>
            </div>
            <div class="card-footer bg-white border-0">
                <#if authorizedUser??>
                    <#list authorizedUser.authorities as grantedAuthority>
                        <#if grantedAuthority.authority == 'APPLICANT'>
                            <a href="/responses/chooseResume?vacancyId=${vacancy.id}" class="btn btn-success"><@spring.message 'vacancy.respond'/></a>
                        </#if>
                    </#list>
                    <#if authorizedUser.name == vacancy.author.email>
                        <a href="/vacancies/${vacancy.id}/edit" class="btn btn-success"><@spring.message 'vacancy.edit'/></a>
                    </#if>
                <#else>
                    <a href="login" class="btn btn-primary"><@spring.message 'vacancy.login'/></a> <@spring.message 'vacancy.or'/>
                    <a href="register" class="btn btn-warning"><@spring.message 'vacancy.register'/></a>
                </#if>
            </div>

        </div>
        <div class="card px-0 col-8 col-md-3">
            <div class="card-header">
                <@spring.message 'vacancy.employer'/>
            </div>
            <div class="card-body">
                <h3 class="card-title"><a class="text-decoration-none" href="/employer/${vacancy.author.email}">${vacancy.author.name}</a></h3>
                <div class="card-text mt-2"><i class="bi bi-envelope-at"></i> ${vacancy.author.email}</div>
                <div class="card-text"><i class="bi bi-telephone"></i> ${vacancy.author.phoneNumber}</div>
            </div>
        </div>
    </div>
</@main.layout>