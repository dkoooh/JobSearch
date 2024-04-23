let urlSegments = window.location.href.split("/")
let responseId = urlSegments[urlSegments.length - 1];
console.log(responseId)

let urlParams = new URLSearchParams(window.location.search);

window.addEventListener('load', onLoadMessages)
window.addEventListener('load', onLoadChatDetails)

async function onLoadMessages(event) {
    try {
        let messagesRequest = await makeRequest (`http://localhost:1234/api/chats/${responseId}`);
        if (messagesRequest.ok) {
            let messagesJson = await messagesRequest.json()

            for (message of messagesJson) {
                let sender = await getSender(message['SENDER_ID']);

                console.log(message['TIMESTAMP'])

                let card = document.createElement('div');
                card.classList.add('card', 'border-start-0', 'border-end-0', 'border-bottom-0', 'rounded-0');
                card.innerHTML = `<div class="card-body">
                        <h5 class="card-title">${sender['name']} (${sender['email']})</h5>
                        <p class="card-subtitle text-body-secondary">${new Date(message['TIMESTAMP']).toLocaleString()}</p>
                        <p style="white-space: pre-line" class="card-text my-2">${message['CONTENT']}</p>
                    </div>`;

                console.log(card)
                document.getElementById('messages').append(card)
            }

        } else {
            throw new Error();
        }

    } catch (e) {
        console.log(e);
    }

}

async function onLoadChatDetails(event) {
    try {
        let requestChatInfo = await makeRequest(`http://localhost:1234/api/responses/${responseId}`)

        if (requestChatInfo.ok) {
            let infoJson = await requestChatInfo.json();
            // let sender = await getSender(infoJson['VACANCY_ID'])

            console.log(infoJson)



            $('#receiverEmail').text(sender['email'])
            $('#receiverAvatar').attr('src', `api/users/user/image/${sender['email']}`)
            if (sender['surname'] != null) {
                $('#receiverName').text(sender['name'], " ", sender['surname']);
            } else {
                $('#receiverName').text(sender['name']);
            }
        } else {
            throw new Error()
        }

    } catch (e) {
        console.log(e)
    }


}

async function getSender (senderId){ // TODO переименовать
    try {
        let request = await makeRequest (`http://localhost:1234/api/users/employers/${senderId}`)
        if (request.ok) {
            return await request.json();
        } else if (request.status === 403) {
            let anotherRequest = await makeRequest (`http://localhost:1234/api/users/employers/${senderId}`)

            if (anotherRequest.ok) {
                return await anotherRequest.json();
            } else {
                throw new Error();
            }
        } else {
            throw new Error ();
        }
    } catch (e) {
        console.log(e);
    }
}

function makeHeaders (){
    let user = restoreUser()
    let headers = new Headers()
    headers.set('Content-Type','application/json')
    if(user){
        headers.set(  'Authorization', 'Basic ' + btoa(user.username + ':' + user.password))
    }
    return headers
}

const requestSettings = {
    method: 'GET',
    headers: makeHeaders()
}

async function makeRequest(url, options) {
    let userValidation = await fetch ('http://localhost:1234/users/login')
    if (userValidation.ok) {
        let settings = options || requestSettings
        let response = await fetch(url, settings)

        if (response.ok) {
            return await response.json()
        } else {
            let error = new Error(response.statusText);
            error.response = response;
            throw error;
        }
    }

}

function restoreUser() {
    let userAsJSON = localStorage.getItem('user');
    return JSON.parse(userAsJSON);
}