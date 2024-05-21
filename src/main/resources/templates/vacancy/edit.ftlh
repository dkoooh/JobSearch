<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <#assign errorClassList = "text-danger d-block error" />

    <form method="POST" action="/vacancies/${vacancyUpdateDto.id}/edit" class="col-md-9 col-lg-8 mx-auto my-3">
        <h2 class="text-center"><@spring.message 'edit.vacancy' />:</h2>
        <div class="col-sm-9 col-lg-7 col-xl-6 mb-4">
            <div class="mb-2 ">
                <label for="name"><@spring.message 'create.vacancy.name' />:</label>
                <@spring.formInput 'vacancyUpdateDto.name' 'id="name" class="form-control"
                placeholder="Введите название"' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-2">
                <label for="category"><@spring.message 'create.vacancy.category' />:</label>
                <#assign options = {}>

                <#list categories as category>
                    <#assign options += {"${category.id}": "${category.name}"}>
                </#list>

                <@spring.checkSelected '${vacancyUpdateDto.categoryId}' />
                <@spring.formSingleSelect 'vacancyUpdateDto.categoryId' options
                'class="form-select" aria-label="Choose account type" id="category"' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-4">
                <label for="salary"><@spring.message 'create.vacancy.salary' />:</label>
                <@spring.formInput 'vacancyUpdateDto.salary' 'step="0.01" id="salary" class="form-control"' 'number' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>

            <div class="form-floating mb-3">
                <@spring.formTextarea 'vacancyUpdateDto.description' 'placeholder=""
                 class="form-control" id="floatingTextarea2" style="height: 200px"' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
                <label for="floatingTextarea2"><@spring.message 'create.vacancy.description' />:</label>
            </div>

            <p class="mb-0"><@spring.message 'create.vacancy.experience' />:</p>
            <div class="row">
                <div class="col-auto">
                    <div class="input-group" style="width: 100px">
                        <div class="input-group-text">
                            <label for="expFrom"><@spring.message 'create.vacancy.experience.from' /></label>
                        </div>
                        <@spring.formInput 'vacancyUpdateDto.expFrom' 'id="expFrom" class="form-control"' 'number' />
                        <@spring.showErrors '<br/>' '${errorClassList}' />
                    </div>
                </div>
                <div class="col-auto">
                    <div class="input-group" style="width: 100px">
                        <div class="input-group-text">
                            <label for="expTo"><@spring.message 'create.vacancy.experience.to' /></label>
                        </div>
                        <@spring.formInput 'vacancyUpdateDto.expTo' 'id="expTo" class="form-control"' 'number' />
                        <@spring.showErrors '<br/>' '${errorClassList}' />
                    </div>
                </div>
            </div>
        </div>

        <div class="form-check mb-3 mt-4">
            <@spring.formCheckbox 'vacancyUpdateDto.isActive' 'class="form-check-input" id="isActive"' />
            <@spring.showErrors '<br/>' '${errorClassList}' />
            <label class="form-check-label" for="isActive">
                <@spring.message 'create.vacancy.active' />
            </label>
        </div>

        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                   value="${(_csrf.token)!'--no-token--'}"/>
        </#if>

        <button type="submit" class="btn btn-success w-100"><@spring.message 'edit.vacancy.save' /></button>
    </form>
</@main.layout>