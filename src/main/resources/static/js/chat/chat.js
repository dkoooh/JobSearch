'use strict';

const path = window.location.pathname;
const segments = path.split('/');
const responseId = segments[segments.length - 1];
const currentUser = Number(new URLSearchParams(window.location.search).get("currentUser"));


window.addEventListener('load', restoreMessages)
window.addEventListener('load', connect)

const socket = new SockJS("/ws");
let stompClient = Stomp.over(socket);

async function restoreMessages () {
    try {
        let requestMessages = await fetch(`http://localhost:1234/api/chats/${responseId}`)
        if (requestMessages.ok) {
            let messages = await requestMessages.json();
            for (let message of messages) {
                addHtmlMessage(message)
                console.log(message);
            }
        }
    } catch (e) {
        console.log(e);
    }
}

function connect(event) {
    event.preventDefault();
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/response/test', onMessageReceived)
    stompClient.subscribe(`/response/${responseId}/queue/messages`, onMessageReceived)
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    addHtmlMessage(message);
}

function addHtmlMessage (message) {
    let htmlMessage = document.createElement('div');
    htmlMessage.classList.add('col-11', 'col-md-9');
    if (currentUser !== message.senderId) {
        htmlMessage.classList.add('ms-auto');
        htmlMessage.innerHTML = `<div id="msg" class="card border-0 rounded-3">
                        <div class="card-body">
                            <p class="card-text my-0" style="white-space: pre-line;">${message.content}
                            </p>
                            <div class="text-body-secondary" style="text-align: right;">${message.timeStamp}</div>
                        </div>
                    </div>`;
    } else {
        htmlMessage.innerHTML = `<div id="msg" class="card bg-warning-subtle border-0 rounded-3">
                        <div class="card-body">
                            <p class="card-text my-0" style="white-space: pre-line;">${message.content}
                            </p>
                            <div class="text-body-secondary" style="text-align: right;">${message.timeStamp}</div>
                        </div>
                    </div>`;
    }

    document.getElementById("messages").appendChild(htmlMessage);

    document.getElementById("messages").scrollTop = document.getElementById("messages").scrollHeight
}

function onMessageSend(event) {
    event.preventDefault();
    let messageText = document.getElementById('messageForm').value;
    stompClient.send(`/app/${responseId}`, {}, JSON.stringify(
        {
            content: messageText,
            responseId: responseId,
            timeStamp: new Date(Date.now()).toLocaleString()
        }
    ));
    document.getElementById('messageForm').value = ''
    console.log(document.getElementById('messageForm').value);
}

function onError(e) {
    console.log(e);
}



