<#macro layout>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>JobSearch</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    </head>
    <body>
        <#assign known = SPRING_SECURITY_CONTEXT??>
        <#if known>
            <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication.principal>
        </#if>
        <nav class="navbar navbar-expand-lg bg-dark fw-medium" data-bs-theme="dark">
            <div class="container">
                <a class="navbar-brand" href="/">JobSearch</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 w-100">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/vacancies">Все вакансии</a>
                        </li>
                        <#if authorizedUser??>
                            <li class="nav-item ms-lg-auto">
                                <a class="btn btn-primary me-2 mb-2 mb-md-0" aria-current="page" href="/users">${authorizedUser.getUsername()}</a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-outline-danger fw-medium" aria-current="page" href="/logout">Выйти</a>
                            </li>
                        <#else>
                            <li class="nav-item ms-lg-auto">
                                <a class="nav-link" aria-current="page" href="/users/register">Регистрация</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="/users/login">Войти</a>
                            </li>
                        </#if>
                    </ul>

                </div>
            </div>
        </nav>

        <main class="container my-4">
            <#nested >
        </main>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.1.min.js"
            integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
    </body>
    </html>
    
</#macro>

