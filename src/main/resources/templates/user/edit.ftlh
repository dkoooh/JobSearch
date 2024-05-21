<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>

    <div class="container pt-2 pb-4 h-100 fw-medium">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-12 col-lg-9 col-xl-7">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body py-3 px-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0"><@spring.message 'profile.edit.editing'/></h3>
                        <form action="/account/edit" method="POST" id="form" enctype="multipart/form-data">

                            <#assign errorClassList = "text-danger d-block error" />

                            <div class="row">
                                <div class="col-12 mb-2">
                                    <div class="form-text"><@spring.message 'profile.edit.warning'/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <label for="emailAddress"><@spring.message 'profile.edit.email'/>
                                    </label>
                                    <input type="email" id="emailAddress" class="form-control form-control-lg"
                                           value="${user.email}" disabled>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <label for="accountType"><@spring.message 'profile.edit.accountType'/></label>
                                    <select id="accountType" class="form-select form-select-lg" name="accountType" disabled>
                                        <#if user.authorities[0].authority == "APPLICANT">
                                            <option><@spring.message 'profile.edit.accountType.applicant'/></option>
                                        <#else>
                                            <option><@spring.message 'profile.edit.accountType.employer'/></option>
                                        </#if>
                                    </select>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <label for="name">
                                        <#if user.authorities[0].authority == "APPLICANT">
                                            <@spring.message 'profile.edit.name'/>
                                        </#if>
                                        <#if user.authorities[0].authority == "EMPLOYER">
                                            <@spring.message 'profile.edit.companyName'/>
                                        </#if>
                                    </label>
                                    <@spring.formInput 'userUpdateDto.name' 'id="name" class="form-control form-control-lg"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                                <div class="col-md-6 mb-4">
                                    <#if user.authorities[0].authority == "APPLICANT">
                                        <label for="surname"><@spring.message 'profile.edit.surname'/></label>
                                        <@spring.formInput 'userUpdateDto.surname'
                                        'id="surname" class="form-control form-control-lg"' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </#if>
                                    <#if user.authorities[0].authority == "EMPLOYER">
                                        <label for="phoneNumber"><@spring.message 'profile.edit.phoneNumber'/></label>
                                        <@spring.formInput 'userUpdateDto.phoneNumber' 'id="phoneNumber"
                                        class="form-control form-control-lg"' 'tel'/>
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </#if>
                                </div>
                            </div>

                            <#if user.authorities[0].authority == "APPLICANT">
                                <div id="row" class="row">
                                    <div class="col-md-6 mb-4 pb-2">
                                        <label for="Age"><@spring.message 'profile.edit.age'/></label>
                                        <@spring.formInput 'userUpdateDto.age' 'id="Age"
                                        class="form-control form-control-lg"' 'number' />
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                    <div class="col-md-6 mb-4 pb-2">
                                        <label for="phoneNumber"><@spring.message 'profile.edit.phoneNumber'/></label>
                                        <@spring.formInput 'userUpdateDto.phoneNumber' 'id="phoneNumber"
                                         class="form-control form-control-lg"' 'tel'/>
                                        <@spring.showErrors '<br/>' '${errorClassList}' />
                                    </div>
                                </div>
                            </#if>

                            <div class="row">
                                <div class="col-md-6">
                                    <label for="password"><@spring.message 'profile.edit.password'/></label>
                                    <@spring.formPasswordInput 'userUpdateDto.password' 'id="password"
                                    class="form-control form-control-lg" value="${user.password}"' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                                <div class="col-md-6">
                                    <label for="avatar"><@spring.message 'profile.edit.avatar'/></label>
                                    <@spring.formInput 'userUpdateDto.avatar' 'class="form-control form-control-lg" id="avatar"' 'file' />
                                    <@spring.showErrors '<br/>' '${errorClassList}' />
                                </div>
                            </div>

                            <div class="row mt-4 pt-2">
                                <div class="col">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                               value="${(_csrf.token)!'--no-token--'}"/>
                                    </#if>
                                    <button type="submit" class="btn btn-outline-primary fw-medium btn-lg"><@spring.message 'profile.edit.save'/></button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@main.layout>