let form = document.getElementById('login')

form.addEventListener('submit', onLoginHandler)

async function onLoginHandler (event) {
    event.preventDefault();
    let data = new FormData(event.target);
    let user = Object.fromEntries(data);

    await registerFetch(user);
}

async function registerFetch (user) {
    let credentials = JSON.stringify(user);
    console.log(credentials)
    try {
        let response = await fetch ("http://localhost:1234/users/login", {
            method: "POST",
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            body: credentials
        });

        // TODO почему при ошибке NotFoundException не заходит в catch и дальше продолжает выполняться?
        if (response.ok) window.location.href = 'http://localhost:1234/users'
        else {
            let loginText = $('#login-text')

            $(loginText).text('Неверный email или пароль...')
            $(loginText).addClass('text-danger');
            $(loginText).removeClass('text-black-50');
        }
    } catch (err) {
        console.log(err)
    }

}

