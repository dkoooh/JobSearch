<#import "../layout.ftlh" as main>

<@main.layout>
   <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">

    <section class="vh-100 gradient-custom">
        <div class="container py-2 ">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card bg-white text-black" style="border-radius: 1rem;">
                        <div class="card-body p-5 text-center">

                            <form id="login" class="mt-md-4 pb-5" method="POST" th:action="/login">

                                <h2 class="fw-bold mb-2 text-uppercase">Вход</h2>
                                <p id="login-text" class="text-black-50 mb-5">Введите email и пароль</p>

                                <div data-mdb-input-init class="form-outline form-black mb-4">
                                    <input type="email" id="typeEmailX" class="form-control form-control-lg"
                                           name="username"/>
                                    <label class="form-label" for="typeEmailX">Email</label>
                                </div>

                                <div data-mdb-input-init class="form-outline form-black mb-4">
                                    <input type="password" id="typePasswordX" class="form-control form-control-lg"
                                           name="password"/>
                                    <label class="form-label" for="typePasswordX">Пароль</label>
                                </div>

                                <button data-mdb-button-init data-mdb-ripple-init class="btn btn-outline-primary btn-lg
                                px-5" type="submit">Войти</button>

                            </form>

                            <div>
                                <p class="mb-0">Нет учетной записи? <a href="/users/register" class="text-primary-50
                                 text-decoration-none fw-bold">Зарегестрируйтесь</a>
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
    <script <#--src="/static/js/user/login.js"-->>
        let params = new URLSearchParams(window.location.search);
        if (params.has("error")) {
            window.onload = () => {
                let msg = $('#login-text')

                $(msg).text('Неверный email или пароль...')
                $(msg).addClass('text-danger');
                $(msg).removeClass('text-black-50');
            }
        }

        let form = document.getElementById('login')
        form.addEventListener('submit', onSubmitAddToLocalStorage)

        async function onSubmitAddToLocalStorage (event) {
            let data = new FormData(event.target);
            let user = Object.fromEntries(data);

            try {
                let request = await fetch("http://localhost:1234/users/login")

                if (request.ok) {
                    localStorage.setItem("user", JSON.stringify(user));
                }

            } catch (e) {
                console.log(e)
            }
        }
    </script>
</@main.layout>