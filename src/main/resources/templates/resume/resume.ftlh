<#import "../layout.ftlh" as main>


<#--    TODO исправить контакты-->
<@main.layout>
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
                        <h5 class="card-subtitle">- ${resume.salary} сом</h5>
                    </#if>
                    <h6 class="card-subtitle my-1">- Категория: ${resume.category.name}</h6>
                    <h6 CLASS="card-subtitle my-1">- Дата создания: ${resume.createdDate}</h6>
                    <div class="card-text border-top border-bottom border-1 my-2 py-2">
                        <h5>Образование: </h5>
                        <pre style="white-space: pre-line;">
                            <#list resume.educationInfo as edus>
                                ${edus.institution} (${edus.program})
                                    - Начало обучения: ${edus.startDate}
                                    - Конец обучения: ${edus.endDate}
                                    - Степень: ${edus.degree}
                            </#list>
                        </pre>
                        <h5>Опыт работы: </h5>
                        <pre style="white-space: pre-line;">
                            <#list resume.workExperienceInfo as job>
                                ${job.companyName} (${job.position})
                                - Количество лет работы: ${job.years}
                                - Обязанности: ${job.responsibilities}
                            </#list>
                        </pre>
                    </div>
                </div>
                <div class="card-footer bg-white border-0">
                    <#if authorizedUser??>
                        <#if resume.applicant.email == authorizedUser.name>
                            <a href="/resumes/${resume.id}/edit" class="btn btn-success mb-2">Редактировать</a>
                        </#if>
                        <#list authorizedUser.authorities as grantedAuthority>
                            <#if grantedAuthority.authority == 'EMPLOYER'>
                                <a href="#" class="btn btn-success mb-2">Откликнуться</a> <#--TODO отклик на соискателя со стороны работодателя-->
                            </#if>
                        </#list>
                    </#if>
                </div>
            </div>
        </div>
        <div class="col-8 col-md-3">
            <div class="card px-0">
                <div class="card-header">
                    Соискатель
                </div>
                <div class="card-body">
                    <h3 class="card-title">${resume.applicant.name} ${resume.applicant.surname!("")}</h3>
                    <div class="my-2">
                        <#if resume.contactInfos[0].value != "">
                            <div>
                                <i class="bi bi-telegram"></i> : ${resume.contactInfos[0].value}
                            </div>
                        </#if>
                        <#if resume.contactInfos[1].value != "">
                            <div>
                                <i class="bi bi-facebook"></i> : ${resume.contactInfos[1].value}
                            </div>
                        </#if>
                        <#if resume.contactInfos[2].value != "">
                            <div>
                                <i class="bi bi-envelope-at"></i> : ${resume.contactInfos[2].value}
                            </div>
                        </#if>
                        <#if resume.contactInfos[3].value != "">
                            <div>
                                <i class="bi bi-linkedin"></i> : ${resume.contactInfos[3].value}
                            </div>
                        </#if>
                        <#if resume.contactInfos[4].value != "">
                            <div>
                                <i class="bi bi-telephone"></i> : ${resume.contactInfos[4].value}
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@main.layout>