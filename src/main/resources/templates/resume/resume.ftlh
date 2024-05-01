<#import "../layout.ftlh" as main>


<#--    TODO исправить контакты-->
<@main.layout>
    <div class="row row-cols-1 row-cols-md-2 justify-content-md-evenly align-items-start">
        <div class="col-12 col-md-8">
            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">${resume.name}</h3>
                    <#if resume.salary?? && resume.salary gt 0>
                        <h5 class="card-subtitle">- ${resume.salary} сом</h5>
                    </#if>
                    <h6 class="card-subtitle my-1">- Категория: ${resume.category}</h6>
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
                    <a href="#" class="btn btn-success mb-2">Откликнуться</a>
                </div>
            </div>
        </div>
        <div class="col-8 col-md-3">
            <div class="card px-0">
                <div class="card-header">
                    Applicant
                </div>
                <div class="card-body">
                    <h3 class="card-title">${resume.applicant.name}</h3>
                    <h6 class="card-subtitle">
                        <i class="bi bi-envelope"></i>
                        <a href="mailto:${resume.applicant.email}" style="text-decoration: none; color:black;">${resume.applicant.email}</a>
                    </h6>
                    <div class="my-2">

                        <#list resume.contactInfos as contact>
                            <p class="my-1">${contact.type}: ${contact.getContactValue}</p>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@main.layout>