<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>
    <form class="col-6 mx-auto" action="/users/profile/edit" method="POST" enctype="multipart/form-data">
        <h1>Editing</h1>
        <div class="mb-3">
            <input type="email" name="email" class="form-control" aria-describedby="emailHelp" placeholder="${userDto.email}" disabled>
            <div id="emailHelp" class="form-text">You cannot change email</div>
        </div>
        <div class="mb-3">
            <label for="phoneNumber">Phone number</label>
            <@spring.formInput 'userDto.phoneNumber' 'type="text" id="phoneNumber" class="form-control" value="${userDto.phoneNumber}"' />
<#--            <input type="text" id="phoneNumber" class="form-control" name="phoneNumber" value="${user.phoneNumber}" required>-->
            <@spring.showErrors '<br/>' 'error' />
        </div>
        <div class="mb-3">
            <label for="name">Name</label>
            <input type="text" id="name" class="form-control" name="name" value="${userDto.name}" required>
            <@spring.showErrors '<br/>' 'error' />
        </div>
        <#list authorizedUser.authorities as grantedAuthority>
            <#if grantedAuthority.authority == "APPLICANT">
                <div class="mb-3">
                    <label for="surname">Surname</label>
                    <@spring.formInput 'userDto.surname' 'type="text" id="surname" class="form-control" value="${userDto.surname}' />
                    <@spring.showErrors '<br/>' 'error' />
                </div>
                <div class="mb-3">
                    <label for="age">Age</label>
                    <@spring.formInput 'userDto.age' 'type="number" id="age" min="16" max="100" class="form-control" value="${userDto.age}"' />
                    <@spring.showErrors '<br/>' 'error' />
                </div>
            </#if>
        </#list>
        <div class="mb-3">
            <label for="password">New password</label>
            <@spring.formPasswordInput 'userDto.password' 'id="password" class="form-control" placeholder="Write new password if you wanna change it"' />
            <@spring.showErrors '<br/>' 'error' />
        </div>
        <div class="mb-3">
            <label for="formFileMultiple" class="form-label">Multiple files input example</label>
            <input class="form-control" name="avatar" type="file" id="formFileMultiple" multiple>
        </div>

        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                   value="${(_csrf.token)!'--no-token--'}"/>
        </#if>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@main.layout>