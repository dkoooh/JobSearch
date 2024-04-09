<#import "../layout.ftlh" as main>

<@main.layout>
    <h2>Все вакансии:</h2>
    <div class="row row-cols-1 row-gap-2">
        <#list resumes.content as resume>
            <div class="col-9">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="/resumes/${resume.id}" class="text-decoration-none text-black">${resume.name}</a>
                        </h5>
                        <#if resume.salary gt 0>
                            <h6 class="card-subtitle">${resume.salary} сом</h6>
                        </#if>
<#--                        <#assign workExp = resume.workExperienceInfo.stream().mapToInt(value -> value.getYears()).sum()>-->
<#--                        <p>Опыт работы: ${workExp} лет</p>-->
                        <a href="#" class="btn btn-success">Откликнуться</a>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <nav class="my-4">
        <ul class="pagination">
            <li class="page-item">
                <#if page == 1>
                    <a class="page-link disabled" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                <#else>
                    <a class="page-link" href="/resumes?page=${page-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </#if>
            </li>
            <#list 1..resumes.getTotalPages() as p>
                <#if p == resumes.getNumber() + 1>
                    <li class="page-item"><a class="page-link active" href="#">${p}</a></li>
                <#else>
                    <li class="page-item"><a class="page-link" href="/resumes?page=${p}">${p}</a></li>
                </#if>
            </#list>
            <li class="page-item">
                <#if page == resumes.getTotalPages()>
                    <a class="page-link disabled" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                <#else>
                    <a class="page-link" href="/resumes?page=${page-1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </#if>
            </li>
        </ul>
    </nav>
</@main.layout>