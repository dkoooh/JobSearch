<#import "../layout.ftlh" as main>

<@main.layout ; spring>
    <#assign errorClassList = "text-danger d-block error" />

    <form method="POST" action="/vacancies/${vacancyUpdateDto.id}/edit" class="col-md-9 col-lg-8 mx-auto my-3">
        <h2 class="text-center">Редактирование вакансии:</h2>
        <div class="col-sm-9 col-lg-7 col-xl-6 mb-4">
            <div class="mb-2 ">
                <label for="name">Название вакансии:</label>
                <@spring.formInput 'vacancyUpdateDto.name' 'id="name" class="form-control"
                placeholder="Введите название"' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-2">
                <label for="category">Категория:</label>
<#--                <select class="form-select" aria-label="Choose account type" name="categoryId" id="category" required>-->
<#--                    <option selected disabled>Выберите категорию</option>-->
<#--                    <#list categories as category>-->
<#--                        <#if vacancy.categoryId == category.id>-->
<#--                            <option value="${category.id}" selected>${category.name}</option>-->
<#--                        <#else>-->
<#--                            <option value="${category.id}">${category.name}</option>-->
<#--                        </#if>-->
<#--                    </#list>-->
<#--                </select>-->

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
                <label for="salary">Заработная плата:</label>
<#--                <input type="number" step="0.01" id="salary" class="form-control" name="salary"-->
<#--                       placeholder="Введите оклад" value="${vacancy.salary}">-->
                <@spring.formInput 'vacancyUpdateDto.salary' 'step="0.01" id="salary" class="form-control"
                placeholder="Введите оклад"' 'number' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>

            <div class="form-floating mb-3">
<#--                <#if vacancy.description??>-->
<#--                    <textarea class="form-control" placeholder="Описание вакансии:" name="description" id="floatingTextarea2"-->
<#--                              style="height: 200px">-->
<#--                        ${vacancy.description}-->
<#--                    </textarea>-->
<#--                <#else>-->
<#--                    <textarea class="form-control" placeholder="Описание вакансии:" name="description" id="floatingTextarea2"-->
<#--                              style="height: 200px"></textarea>-->
<#--                </#if>-->
                <@spring.formTextarea 'vacancyUpdateDto.description' 'placeholder="Описание вакансии:"
                 class="form-control" id="floatingTextarea2" style="height: 200px"' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
                <label for="floatingTextarea2">Описание вакансии:</label>
            </div>

            <p class="mb-0">Требуемый опыт:</p>
            <div class="row">
                <div class="col-auto">
                    <div class="input-group" style="width: 100px">
                        <div class="input-group-text">
                            <label for="expFrom">От</label>
                        </div>
                        <@spring.formInput 'vacancyUpdateDto.expFrom' 'id="expFrom" class="form-control"' 'number' />
                        <@spring.showErrors '<br/>' '${errorClassList}' />
<#--                        <input type="number" id="expFrom" class="form-control" name="expFrom"-->
<#--                               value="${vacancy.expFrom}">-->
                    </div>
                </div>
                <div class="col-auto">
                    <div class="input-group" style="width: 100px">
                        <div class="input-group-text">
                            <label for="expTo">До</label>
                        </div>
<#--                        <input type="number" id="expTo" class="form-control" name="expTo" value="${vacancy.expTo}">-->
                        <@spring.formInput 'vacancyUpdateDto.expTo' 'id="expTo" class="form-control"' 'number' />
                        <@spring.showErrors '<br/>' '${errorClassList}' />
                    </div>
                </div>
            </div>
        </div>

        <div class="form-check mb-3 mt-4">
<#--            <#if vacancy.isActive>-->
<#--                <input class="form-check-input" type="checkbox" value="true" name="isActive" id="isActive" checked>-->
<#--            <#else>-->
<#--                <input class="form-check-input" type="checkbox" value="true" name="isActive" id="isActive">-->
<#--            </#if>-->

            <@spring.formCheckbox 'vacancyUpdateDto.isActive' 'class="form-check-input" id="isActive"' />
            <@spring.showErrors '<br/>' '${errorClassList}' />
            <label class="form-check-label" for="isActive">
                Активно
            </label>
        </div>

        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                   value="${(_csrf.token)!'--no-token--'}"/>
        </#if>

        <button type="submit" class="btn btn-success w-100">Опубликовать</button>
    </form>
</@main.layout>