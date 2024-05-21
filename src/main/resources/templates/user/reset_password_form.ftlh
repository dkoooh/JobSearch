<#import "../layout.ftlh" as main>

<@main.layout; spring>
    <div class="text-center">
        <h2><@spring.message 'resetPassword.title' /></h2>
        <#if error??>
            <b class="text-danger">${error}</b>
        </#if>
    </div>

    <div class="py-5 h-100">
        <div class="row d-flex align-items-center justify-content-center h-100">
            <div class="col-md-7 col-lg-5 col-xl-5">
                <form action="/account/reset_password" method="post">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>
                    <input type="hidden" name="token" value="${token!}">
                    <label for="password"><@spring.message 'resetPassword.label' /></label>
                    <input type="password" id="password" name="password" class="form-control"
                          required autofocus/>
                    <p class="text-center">
                        <button type="submit" class="btn btn-primary mt-3"><@spring.message 'resetPassword.submit' /></button>
                    </p>
                </form>
            </div>
        </div>
    </div>
</@main.layout>