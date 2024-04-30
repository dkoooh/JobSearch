<#import "../layout.ftlh" as main>

<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUser = SPRING_SECURITY_CONTEXT.authentication>
    </#if>
    <a href="/responses" class="btn btn-outline-primary"><i class="bi bi-arrow-left"> Вернуться в отклики</i></a>

    <div class="row row-cols-1 row-cols-md-2 justify-content-md-around mt-3 row-gap-3">
        <#if authorizedUser.name == response.resume.applicant.email>
            <div class="col col-md-4 col-lg-3 px-md-0">
                <div class="card">
                    <div class="card-header">
                        <div class="row row-cols-2">
                            <div class="col-auto text-center pe-0 d-flex my-auto">
                                <img id="receiverAvatar"
                                     src="http://localhost:1234/api/users/user/image/${response.vacancy.author.email}"
                                     class="img" width="50px" alt="avatar">
                            </div>
                            <div class="col-auto d-flex my-auto">
                                <p id="receiverName" class="py-0 my-1">${response.vacancy.author.name} ${response.vacancy.author.surname}</p>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="card-text"><i class="bi bi-envelope"></i>
                            <a id="receiverEmail" href="mailto:${response.vacancy.author.email}">${response.vacancy.author.email}</a>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
        <#if authorizedUser.name == response.vacancy.author.email>
            <div class="col col-md-4 col-lg-3 px-md-0">
                <div class="card">
                    <div class="card-header">
                        <div class="row row-cols-2">
                            <div class="col-auto text-center pe-0 d-flex my-auto">
                                <img id="receiverAvatar"
                                     src="http://localhost:1234/api/users/user/image/${response.resume.applicant.email}"
                                     class="img" width="50px" alt="avatar">
                            </div>
                            <div class="col-auto d-flex my-auto">
                                <p id="receiverName" class="py-0 my-1">${response.resume.applicant.name} ${response.resume.applicant.surname}</p>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="card-text"><i class="bi bi-envelope"></i>
                            <a id="receiverEmail" href="mailto:${response.resume.applicant.email}">${response.resume.applicant.email}</a>
                        </div>
                    </div>
                </div>
            </div>
        </#if>

        <div class="col col-md-7 col-lg-8 rounded-3 py-2 bg-light">
            <div id="messages" class="row row-cols-1 row-gap-2 mb-3 align-content-start" style="height: 65vh; overflow-y: auto">
            </div>

            <div class="row mb-1 mt-auto">
                <div class="form-floating col px-0 mx-2">
                        <textarea id="messageForm" class="form-control border-0" placeholder="Leave a message here"></textarea>
                        <label for="messageForm">Message</label>
                </div>
                <div class="col-auto ps-0 pe-2">
                        <button onclick="onMessageSend(event)" class="btn btn-outline-primary h-100"><i class="bi bi-send"></i> </button>
                </div>
            </div>
        </div>

    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/static/js/chat/chat.js"></script>

</@main.layout>