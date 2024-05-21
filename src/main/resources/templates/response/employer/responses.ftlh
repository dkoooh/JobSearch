<#import "../../layout.ftlh" as main>

<@main.layout; spring>
    <h4><@spring.message 'responses.title' />: </h4>

    <#if responses??>
        <#if responses?size !=0>
            <div class="row row-cols-1 row-gap-2">
                <#list responses as response>
                    <div class="col col-md-10 col-lg-9">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">
                                    <a href="/resumes/${response.RESUME.id}"
                                       style="text-decoration: none; color: black">
                                        ${response.RESUME.name}
                                    </a>
                                </h5>
                                <h6 class="card-subtitle mb-2 text-body-secondary">
                                    <@spring.message 'responses.vacancy' />: <a
                                            href="/vacancies/${response.VACANCY.id}"
                                            style="text-decoration: none; color: black">
                                        ${response.VACANCY.name}
                                    </a>
                                </h6>
                                <div class="card-text">
                                    <@spring.message 'responses.applicant' />:
                                    <a href="/applicant/${response.RESUME.applicant.email}">
                                        ${response.RESUME.applicant.name} ${response.RESUME.applicant.surname}
                                    </a>
                                </div>
                                <#if response.confirmation??>
                                    <div class="card-text">
                                        <#assign confirmed><@spring.message 'responses.confirmed' /></#assign>
                                        <#assign notConfirmed><@spring.message 'responses.notConfirmed' /></#assign>
                                        <@spring.message 'responses.status' />:
                                        ${response.CONFIRMATION?string("${confirmed}", "${notConfirmed}")}
                                    </div>
                                </#if>
                            </div>
                            <div class="card-footer bg-white border-0">
                                <a href="/chats/${response.ID}?currentUser=${response.VACANCY.author.id}"
                                   class="btn btn-outline-primary"><@spring.message 'responses.chat' /></a>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <h6><@spring.message 'responses.notFound' /></h6>
        </#if>
    <#else>
        <h6><@spring.message 'responses.notFound' /></h6>
    </#if>
</@main.layout>