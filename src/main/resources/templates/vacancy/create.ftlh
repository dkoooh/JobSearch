<#import "../layout.ftlh" as main>

<@main.layout; spring>
    <#assign errorClassList = "text-danger d-block error" />

    <form method="POST" action="/vacancies/create" class="col-md-9 col-lg-8 mx-auto my-3">
        <h2 class="text-center">Создание вакансии:</h2>
        <div class="col-sm-9 col-lg-7 col-xl-6 mb-4">
            <div class="mb-2 ">
                <label for="name">Название вакансии:</label>
                <@spring.formInput 'vacancyCreateDto.name' 'id="name" class="form-control"
                placeholder="Введите название"'/>
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-2">
                <label for="category">Категория:</label>
                <#assign options = {"": "Выберите категорию"}>
                <#list categories as category>
                    <#assign options += {"${category.id}": "${category.name}"}>
                </#list>
                <@spring.formSingleSelect 'vacancyCreateDto.categoryId' options
                'class="form-select" aria-label="Choose account type" id="category"' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>
            <div class="mb-4">
                <label for="salary">Заработная плата:</label>
                <@spring.formInput 'vacancyCreateDto.salary' 'id="salary" class="form-control" step="0.01"' 'number' />
                <@spring.showErrors '<br/>' '${errorClassList}' />
            </div>

            <div class="form-floating mb-3">
                <@spring.formTextarea 'vacancyCreateDto.description' 'class="form-control" placeholder="Описание вакансии:" id="floatingTextarea2" style="height: 200px"' />
                <label for="floatingTextarea2">Описание вакансии:</label>
            </div>
            <@spring.showErrors '<br/>' '${errorClassList}' />

            <p class="mb-0">Требуемый опыт:</p>
            <div class="row">
                <div class="col-auto">
                    <div class="input-group" style="width: 100px">
                        <div class="input-group-text">
                            <label for="expFrom">От</label>
                        </div>
                        <@spring.formInput 'vacancyCreateDto.expFrom' 'id="expFrom" class="form-control"' 'number' />
                        <@spring.showErrors '<br/>' '${errorClassList}' />
                    </div>
                </div>
                <div class="col-auto">
                    <div class="input-group" style="width: 100px">
                        <div class="input-group-text">
                            <label for="expTo">До</label>
                        </div>
                        <@spring.formInput 'vacancyCreateDto.expTo' 'id="expTo" class="form-control"' 'number' />
                        <@spring.showErrors '<br/>' '${errorClassList}' />
                    </div>
                </div>
            </div>
        </div>

        <div class="form-check mb-3 mt-4">
            <input class="form-check-input"  type="checkbox" value="true" name="isActive" id="isActive">
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