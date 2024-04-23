<#import "../layout.ftlh" as main>

<@main.layout>
    <a href="/responses" class="btn btn-outline-primary"><i class="bi bi-arrow-left"> Вернуться в отклики</i></a>
    <div class="row row-cols-1 row-cols-md-2 justify-content-md-around mt-3 row-gap-3">
        <div class="col col-md-4 col-lg-3 px-md-0">
            <div class="card">
                <div class="card-header">
                    <div class="row row-cols-2">
                        <div class="col-auto text-center pe-0 d-flex my-auto">
                            <img id="receiverAvatar" src="https://www.svgrepo.com/show/384674/account-avatar-profile-user-11.svg" class="img" width="50px" alt="avatar">
                        </div>
                        <div class="col-auto d-flex my-auto">
                            <p id="receiverName" class="py-0 my-1"></p>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <p class="my-1 py-0"><i class="bi bi-envelope"></i> <a id="receiverEmail" href="mailto:emailemail@email.com">emailemail@email.com</a> </p>
                </div>
            </div>
        </div>

        <div id="messages" class="col col-md-7 col-lg-8">
        </div>

    </div>

    <script src="https://code.jquery.com/jquery-3.6.1.min.js"
            integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
    <script src="/static/js/chat/chat.js"></script>

</@main.layout>