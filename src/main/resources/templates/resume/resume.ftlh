<#import "../layout.ftlh" as main>

<@main.layout; spring>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>

    <div class="row row-cols-1 row-cols-md-2 justify-content-md-evenly align-items-start">
        <div class="col-12 col-md-8">
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">${resume.name}</h3>
                    <#if resume.salary?? && resume.salary gt 0>
                        <h5 class="card-subtitle">- ${resume.salary} <@spring.message 'resume.currency'/></h5>
                    </#if>
                    <h6 class="card-subtitle my-1">- <@spring.message 'resume.category'/>: ${resume.category.name}</h6>
                    <h6 CLASS="card-subtitle my-1">- <@spring.message 'resume.createdDate'/>: ${resume.createdDate}</h6>
                    <div class="card-text border-top border-bottom border-1 my-2 py-2">
                        <h5><@spring.message 'resume.edu.title'/>: </h5>
                        <pre style="white-space: pre-line;">
                            <#list resume.educationInfo as edus>
                                ${edus.institution} (${edus.program})
                                    - <@spring.message 'resume.edu.startDate'/>: ${edus.startDate}
                                    - <@spring.message 'resume.edu.endDate'/>: ${edus.endDate}
                                    - <@spring.message 'resume.edu.degree'/>: ${edus.degree}
                            </#list>
                        </pre>
                        <h5><@spring.message 'resume.workExp.title'/>: </h5>
                        <pre style="white-space: pre-line;">
                            <#list resume.workExperienceInfo as job>
                                ${job.companyName} (${job.position})
                                - <@spring.message 'resume.workExp.years'/>: ${job.years}
                                - <@spring.message 'resume.workExp.responsibilities'/>: ${job.responsibilities}
                            </#list>
                        </pre>
                    </div>
                </div>
                <div class="card-footer bg-white border-0">
                    <#if authorizedUser??>
                        <#if resume.applicant.email == authorizedUser.name>
                            <a href="/resumes/${resume.id}/edit" class="btn btn-success mb-2"><@spring.message 'resume.edit'/></a>
                        </#if>
                        <#list authorizedUser.authorities as grantedAuthority>
                            <#if grantedAuthority.authority == 'EMPLOYER'>
                                <a href="#" class="btn btn-success mb-2"><@spring.message 'resume.respond'/></a> <#--TODO отклик на соискателя со стороны работодателя-->
                            </#if>
                        </#list>
                    </#if>
                </div>
            </div>
        </div>
        <div class="col-8 col-md-3">
            <div class="card px-0">
                <div class="card-header">
                    <@spring.message 'resume.applicant'/>
                </div>
                <div class="card-body">
                    <h3 class="card-title">${resume.applicant.name} ${resume.applicant.surname!("")}</h3>
                    <div class="my-2">
                        <#if resume.contactInfos[0].value != "">
                            <div>
                                <i class="bi bi-telegram"></i> ${resume.contactInfos[0].value}
                            </div>
                        </#if>
                        <#if resume.contactInfos[1].value != "">
                            <div>
                                <i class="bi bi-facebook"></i> ${resume.contactInfos[1].value}
                            </div>
                        </#if>
                        <#if resume.contactInfos[2].value != "">
                            <div>
                                <i class="bi bi-envelope-at"></i> ${resume.contactInfos[2].value}
                            </div>
                        </#if>
                        <#if resume.contactInfos[3].value != "">
                            <div>
                                <i class="bi bi-linkedin"></i> ${resume.contactInfos[3].value}
                            </div>
                        </#if>
                        <#if resume.contactInfos[4].value != "">
                            <div>
                                <i class="bi bi-telephone"></i> ${resume.contactInfos[4].value}
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@main.layout>