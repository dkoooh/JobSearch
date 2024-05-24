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
                    <p class="card-text mt-2"> <pre style="white-space: pre-line;">${vacancy.description}</pre></p>
                </#if>
            </div>
            <div class="card-footer bg-white border-0">
                <#if authorizedUser??>
                    <#list authorizedUser.authorities as grantedAuthority>
                        <#if grantedAuthority.authority == 'APPLICANT'>
                            <a data-bs-target="#resumeChoiceModal" data-bs-toggle="modal"
                               class="btn btn-success">
                                <@spring.message 'vacancy.respond'/>
                            </a>
                        </#if>
                    </#list>
                    <#if authorizedUser.name == vacancy.author.email>
                        <a href="/vacancies/${vacancy.id}/edit" class="btn btn-success"><@spring.message 'vacancy.edit'/></a>
                    </#if>
                <#else>
                    <a href="/login" class="btn btn-primary"><@spring.message 'vacancy.login'/></a> <@spring.message 'vacancy.or'/>
                    <a href="/register" class="btn btn-warning"><@spring.message 'vacancy.register'/></a>
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

    <div class="modal fade" id="resumeChoiceModal" tabindex="-1" aria-labelledby="resumeChoiceModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-lg">
            <div class="modal-content rounded-4 fw-medium">
                <div class="modal-header border-0">
                    <h1 class="modal-title fs-5" id="resumeChoiceModalLabel"><@spring.message 'vacancies.choose.resume' /> </h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="resumes" class="row row-cols-1 row-gap-2">

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        let modal = document.getElementById('resumeChoiceModal');
        modal.addEventListener('show.bs.modal', onShowModal);

        async function onShowModal() {
            try {
                let resumesRequest = await fetch(`/api/resumes/applicant`);
                if (resumesRequest.ok) {
                    let resumes = await resumesRequest.json()

                    document.getElementById('resumes').innerHTML = '';
                    for (let resume of resumes) {
                        let resumeHtml = document.createElement('div')
                        resumeHtml.classList.add('col')

                        let salary;
                        if (!resume['salary'] || resume['salary'] === 0.0) {
                            salary = '<@spring.message 'vacancies.salary.negotiable' />'
                        } else {
                            salary = resume['salary'] + ' <@spring.message 'vacancies.currency' />'
                        }

                        let exp = "";
                        if (resume['workExperienceInfo'].length !== 0) {
                            let years = 0;
                            for (let info of resume['workExperienceInfo']) {
                                years += info['years'];
                            }

                            if (years % 10 === 1) {
                                exp = "<@spring.message 'vacancies.resume.experience' />: " + years +
                                    ' <@spring.message 'vacancies.resume.experience.years1' />';
                            } else if (years % 10 > 1 && years % 10 < 5) {
                                exp = "<@spring.message 'vacancies.resume.experience' />: " + years +
                                    ' <@spring.message 'vacancies.resume.experience.years2' />';
                            } else {
                                exp = "<@spring.message 'vacancies.resume.experience' />: " + years +
                                    ' <@spring.message 'vacancies.resume.experience.years3' />';
                            }
                        }

                        resumeHtml.innerHTML = `<div class="card border-0 bg-body-tertiary rounded-4">
                                <div class="card-body px-md-4">
                                    <h5 class="m-0 p-0">
                                        <form action="/responses/respond" method="post">
                                            <input type="hidden" name="resumeId" value="` + resume['id'] + `">
                                            <input type="hidden" name="vacancyId" value="` + ${vacancy.id} + `">
                                            <#if _csrf??>
                                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                                       value="${(_csrf.token)!'--no-token--'}"/>
                                            </#if>
                                            <button type="submit" class="border-0 bg-body-tertiary p-0 m-0 fw-medium
                                             link-primary">` + resume['name'] + `</button>
                                        </form>
                                    </h5>
                                    <h3 class="card-title">` + salary + `</h3>
                                    <p class="card-text m-0 p-0">` + exp + `</p>
                                </div>
                                <div
                                        class="card-footer px-md-4 pb-3 pt-0 bg-body-tertiary border-0 d-flex rounded-4">
                                    <div class="text-body-tertiary mt-auto "><@spring.message 'vacancies.resume.created.date' />: `
                            + resume['createdDate'] + `</div>
                                    <div class="text-body-tertiary ms-auto mt-auto "><@spring.message 'vacancies.resume.update.time' />:
                                        ` + resume['updateTime'] + `</div>
                                </div>
                            </div>`;

                        document.getElementById('resumes').appendChild(resumeHtml)
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