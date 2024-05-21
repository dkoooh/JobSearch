<#import "../layout.ftlh" as main>

<@main.layout; spring>
    <section class="vh-100 gradient-custom">
        <div class="container py-2 ">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card bg-white text-black" style="border-radius: 1rem;">
                        <div class="card-body p-5 text-center">

                            <form id="login" class="mt-md-4 pb-5" method="POST" action="/login">

                                <h2 class="fw-bold mb-2 text-uppercase"><@spring.message 'login.login'/></h2>
                                <p id="login-text" class="text-black-50 mb-5"><@spring.message 'login.text'/></p>

                                <div class="form-outline form-black mb-4">
                                    <input type="email" id="typeEmailX" class="form-control form-control-lg"
                                           name="username"/>
                                    <label class="form-label" for="typeEmailX"><@spring.message 'login.email'/></label>
                                </div>

                                <div class="form-outline form-black mb-4">
                                    <input type="password" id="typePasswordX" class="form-control form-control-lg"
                                           name="password"/>
                                    <label class="form-label" for="typePasswordX"><@spring.message 'login.password'/></label>
                                </div>

                                <#if _csrf??>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                           value="${(_csrf.token)!'--no-token--'}"/>
                                </#if>

                                <p class="small mb-5 pb-lg-2">
                                    <a class="link-secondary text-decoration-none" href="/account/forgot_password">
                                        <@spring.message 'login.forgotPassword'/>?
                                    </a>
                                </p>
                                <button data-mdb-button-init data-mdb-ripple-init class="btn btn-outline-primary btn-lg
                                px-5" type="submit"><@spring.message 'login.submit'/></button>

                            </form>

                            <div>
                                <p class="mb-0"><@spring.message 'login.noAccount'/>? <a href="/register" class="text-primary-50
                                 text-decoration-none fw-bold"><@spring.message 'login.register'/></a>
                                </p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <script src="https://code.jquery.com/jquery-3.6.1.min.js"
            integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
    <script>
        let params = new URLSearchParams(window.location.search);
        if (params.has("error")) {
            window.onload = () => {
                let msg = $('#login-text')

                $(msg).text('<@spring.message "login.badCredentials"/>')
                $(msg).addClass('text-danger');
                $(msg).removeClass('text-black-50');
            }
        }
    </script>
</@main.layout>