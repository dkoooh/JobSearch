<#import "../layout.ftlh" as main>


<@main.layout ; spring>
    <div class="container pt-2 pb-4 h-100 fw-medium">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-12 col-lg-9 col-xl-7">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body py-3 px-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0"><@spring.message 'registration.registration'/></h3>
                        <form action="/register" method="POST" id="form">

                            <#assign errorClassList = "text-danger d-block error" />

                            <div class="row">

                                <div class="col-12 mb-2">
                                    <div id="emailHelp" class="form-text"><@spring.message 'registration.emailHelp'/>
                                    </div>
                                </div>

                                <div class="col-md-6 mb-4 pb-2">
                                    <div data-mdb-input-init class="form-outline">
                                        <label for="emailAddress"><@spring.message 'registration.email'/>
                                        </label>
                                        <@spring.formInput 'userCreationDto.email' 'type="email" id="emailAddress"
                                               class="form-control form-control-lg"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />

                                    </div>
                                </div>

                                <div class="col-md-6 mb-4">
                                    <label for="accountType"><@spring.message 'registration.accountType'/></label>
                                    <#assign applicant><@spring.message 'registration.accountType.applicant'/></#assign>
                                    <#assign employer><@spring.message 'registration.accountType.employer'/></#assign>
                                    <@spring.formSingleSelect 'userCreationDto.accountType'
                                    {"EMPLOYER": "${employer}",
                                    "APPLICANT": "${applicant}"}
                                    'id="accountType" class="form-select form-select-lg"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>

                            </div>

                            <div id="nameSurname" class="row">
                                <div class="col-md-6 mb-4">

                                    <div data-mdb-input-init class="form-outline">
                                        <label for="name" id="nameLabel">
                                            <@spring.message 'registration.companyName'/>
                                        </label>
                                        <@spring.formInput "userCreationDto.name" 'type="text" id="name"
                                               class="form-control form-control-lg"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>

                                </div>

                                <div id='phoneNumberCol' class="col-md-6 mb-4 pb-2">

                                    <div data-mdb-input-init class="form-outline">
                                        <label for="phoneNumber"><@spring.message 'registration.phoneNumber'/></label>
                                        <@spring.formInput 'userCreationDto.phoneNumber' 'type="tel" id="phoneNumber"
                                                   class="form-control form-control-lg" ' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>

                                </div>
                            </div>

                            <div id="passwordRow" class="row">
                                <div class="col-md-6">
                                    <label for="password"><@spring.message 'registration.password'/></label>
                                    <@spring.formPasswordInput 'userCreationDto.password' 'id="password" class="form-control
                                    form-control-lg"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>

                            <div class="row mt-4 pt-2">
                                <div class="col">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                               value="${(_csrf.token)!'--no-token--'}"/>
                                    </#if>
                                    <button type="submit" class="btn btn-outline-primary fw-medium btn-lg"><@spring.message 'registration.register'/></button>
                                </div>
                                <div class="col align-content-center">
                                    <p class="mb-0"><@spring.message 'registration.registered'/>?
                                        <a href="/login" class="text-primary-50
                                            text-decoration-none fw-bold"><@spring.message 'registration.login'/>
                                        </a>
                                    </p>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<#--    <style>-->
<#--        body {-->
<#--            /* fallback for old browsers */-->
<#--            background: #f093fb;-->

<#--            /* Chrome 10-25, Safari 5.1-6 */-->
<#--            background: -webkit-linear-gradient(to bottom right, rgba(240, 147, 251, 1), rgba(245, 87, 108, 1));-->

<#--            /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */-->
<#--            background: linear-gradient(to bottom right, rgba(240, 147, 251, 1), rgba(245, 87, 108, 1))-->
<#--        }-->

<#--        section {-->
<#--            height: 100vh;-->
<#--        }-->
<#--    </style>-->

    <script>
        let accountType = document.getElementById('accountType');
        accountType.addEventListener("change", changeForm);

        let form = document.getElementById('form')
        let surnameCol = document.getElementById('surnameCol')
        let nameLabel = document.getElementById('nameLabel')
        let nameSurnameRow = document.getElementById('nameSurname')
        let passwordRow = document.getElementById('passwordRow')


        if (accountType.value === "APPLICANT") {
            setFormForApplicant(surnameCol, nameSurnameRow, passwordRow, nameLabel, form)
        } else if (accountType.value === "EMPLOYER") {
            setFormForEmployer(nameLabel, surnameCol, nameSurnameRow)
        }

        function changeForm (event) {
            let form = document.getElementById('form')
            let surnameCol = document.getElementById('surnameCol')
            let nameLabel = document.getElementById('nameLabel')
            let nameSurnameRow = document.getElementById('nameSurname')
            let passwordRow = document.getElementById('passwordRow')

            if (event.target.value === "APPLICANT") {
                setFormForApplicant(surnameCol, nameSurnameRow, passwordRow, nameLabel, form)
            } else if (event.target.value === "EMPLOYER") {
                setFormForEmployer(nameLabel, surnameCol, nameSurnameRow)
            }
        }

        function setFormForEmployer (nameLabel, surnameCol, nameSurnameRow) {
            let removableRow = document.getElementById('removableRow')

            if (surnameCol) {
                surnameCol.remove();
                removableRow.remove();

                let phoneNumberHtml = document.createElement('div');
                phoneNumberHtml.classList.add('col-md-6', 'mb-4', 'pb-2');
                phoneNumberHtml.setAttribute('id', 'phoneNumberCol');
                phoneNumberHtml.innerHTML = `
                                        <div data-mdb-input-init class="form-outline">
                                            <label for="phoneNumber"><@spring.message 'registration.phoneNumber'/></label>
                                            <@spring.formInput "userCreationDto.phoneNumber" 'type="tel" id="phoneNumber"
                                                   class="form-control form-control-lg"' />
                                            <@spring.showErrors "<br/>" "${errorClassList}"/>
                                        </div>`;
                nameSurnameRow.appendChild(phoneNumberHtml);

                nameLabel.innerText = '<@spring.message 'registration.companyName'/>';
            }
        }

        function setFormForApplicant (surnameCol, nameSurnameRow, passwordRow, nameLabel, form) {
            if (!surnameCol) {
                let phoneNumberCol = document.getElementById('phoneNumberCol')

                let surnameHtml = document.createElement('div');
                surnameHtml.setAttribute('id', 'surnameCol');
                surnameHtml.classList.add('col-md-6', 'mb-4');
                surnameHtml.innerHTML = `<div data-mdb-input-init class="form-outline">
                                            <label for="lastName"><@spring.message 'registration.surname'/></label>
                                            <@spring.formInput "userCreationDto.surname" 'type="text" id="surname"
                                                   class="form-control form-control-lg"' />
                                            <@spring.showErrors "<br/>" "${errorClassList}"/>
                                        </div>`;

                nameSurnameRow.appendChild(surnameHtml);
                phoneNumberCol.remove()

                let removableRowHtml = document.createElement('div')
                removableRowHtml.classList.add('row')
                removableRowHtml.setAttribute('id', 'removableRow');
                removableRowHtml.innerHTML = `<div class="col-md-6 mb-4 d-flex">
                                             <div data-mdb-input-init class="form-outline datepicker w-100">
                                                 <label for="birthdayDate"><@spring.message 'registration.age'/></label>
                                                 <@spring.formInput 'userCreationDto.age' 'type="number" class="form-control form-control-lg"
                                                     id="birthdayDate"' />
                                                 <@spring.showErrors "<br/>" "${errorClassList}"/>
                                             </div>
                                         </div>

                                         <div class="col-md-6 mb-4 pb-2">
                                             <div data-mdb-input-init class="form-outline">
                                                 <label for="phoneNumber"><@spring.message 'registration.phoneNumber'/></label>
                                                 <@spring.formInput 'userCreationDto.phoneNumber' 'type="tel" id="phoneNumber"
                                                     class="form-control form-control-lg"' />
                                                 <@spring.showErrors "<br/>" "${errorClassList}"/>
                                             </div>

                                         </div>`

                form.insertBefore(removableRowHtml, passwordRow);

                nameLabel.innerText = '<@spring.message 'registration.name'/>';
            }
        }
    </script>
</@main.layout>