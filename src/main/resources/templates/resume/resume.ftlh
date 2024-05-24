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
                                <a data-bs-target="#vacancyChoiceModal" data-bs-toggle="modal"
                                   class="btn btn-success mb-2">
                                    <@spring.message 'resume.respond'/>
                                </a>
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

    <div class="modal fade" id="vacancyChoiceModal" tabindex="-1" aria-labelledby="vacancyChoiceModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-lg">
            <div class="modal-content rounded-4">
                <div class="modal-header border-0">
                    <h1 class="modal-title fs-5" id="vacancyChoiceModalLabel"><@spring.message 'resumes.choose.vacancy' />: </h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="vacancies" class="row row-cols-1 row-gap-2">

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        let modal = document.getElementById('vacancyChoiceModal');
        modal.addEventListener('show.bs.modal', onShowModal);

        async function onShowModal (event) {
            try {
                let vacanciesRequest = await fetch (`http://localhost:1234/api/vacancies/employer`);
                if (vacanciesRequest.ok) {
                    let vacancies = await vacanciesRequest.json();

                    document.getElementById('vacancies').innerHTML = '';
                    for (let vacancy of vacancies) {
                        let vacancyHtml = document.createElement('div')
                        vacancyHtml.classList.add('col')

                        let salary;
                        if (!vacancy['salary'] || vacancy['salary'] === 0.0) {
                            salary = '<@spring.message 'resumes.vacancies.salary.negotiable' />'
                        } else {
                            salary = vacancy['salary'] + ' <@spring.message 'resumes.vacancies.currency' />'
                        }

                        let exp = "";
                        if (vacancy['expFrom'] && vacancy['expFrom'] > 0) {
                            if (vacancy['expTo'] && vacancy['expTo'] > 0) {
                                exp = '<@spring.message 'resumes.vacancies.experience.from' /> ' + vacancy['expFrom']
                                    + ' <@spring.message 'resumes.vacancies.experience.to' /> ' + vacancy['expTo']
                                    + ' <@spring.message 'resumes.vacancies.experience.years' />';
                            } else {
                                exp = '<@spring.message 'resumes.vacancies.experience.from' /> ' + vacancy['expFrom']
                                    + ' <@spring.message 'resumes.vacancies.experience.years' />'
                            }
                        }

                        vacancyHtml.innerHTML = `<div class="card border-0 bg-body-tertiary rounded-4">
                                <div class="card-body px-md-4">
                                    <h5 class="m-0 p-0">
                                        <form action="/responses/respond" method="post">
                                            <input type="hidden" name="vacancyId" value="` + vacancy['id'] + `">
                                            <input type="hidden" name="resumeId" value="` + ${resume.id} + `">
                                            <#if _csrf??>
                                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                                       value="${(_csrf.token)!'--no-token--'}"/>
                                            </#if>
                                            <button type="submit" class="border-0 bg-body-tertiary p-0 m-0 fw-medium
                                             link-primary">` + vacancy['name'] + `</button>
                                        </form>
                                    </h5>
                                    <h3 class="card-title">` + salary + `</h3>
                                    <p class="card-text m-0 p-0">` + exp + `</p>
                                </div>
                                <div
                                        class="card-footer px-md-4 pb-3 pt-0 bg-body-tertiary border-0 d-flex rounded-4">
                                    <div class="text-body-tertiary mt-auto "><@spring.message 'resumes.vacancies.created.date' />: ` + vacancy['createdDate'] + `</div>
                                    <div class="text-body-tertiary ms-auto mt-auto "><@spring.message 'resumes.vacancies.update.time' />:
                                        ` + vacancy['updateTime'] + `</div>
                                </div>
                            </div>`;

                        document.getElementById('vacancies').appendChild(vacancyHtml)
                    }
                } else {
                    throw new Error();
                }
            } catch (error) {
                console.log(error)
            }
        }
    </script>
</@main.layout>