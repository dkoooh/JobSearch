<#import "../layout.ftlh" as main>

<@main.layout>
    <form class="col-6 mx-auto" action="/users/profile/edit" method="POST">
        <h1>Editing</h1>
        <div class="mb-3">
            <input type="email" name="email" class="form-control" aria-describedby="emailHelp" placeholder="${user.email}" disabled>
            <div id="emailHelp" class="form-text">You cannot change email</div>
        </div>
        <div class="mb-3">
            <label for="phoneNumber">Phone number</label>
            <input type="text" id="phoneNumber" class="form-control" name="phoneNumber" value="${user.phoneNumber}" required>
        </div>
        <div class="mb-3">
            <label for="name">Name</label>
            <input type="text" id="name" class="form-control" name="name" value="${user.name}" required>
        </div>
        <div class="mb-3">
            <label for="surname">Surname</label>
            <input type="text" id="surname" class="form-control" name="surname" value="${user.surname}">
        </div>
        <div class="mb-3">
            <label for="age">Age</label>
            <input type="number" id="age" min="16" max="100" class="form-control" name="age" value="${user.age}" required>
        </div>
        <div class="mb-3">
            <label for="password">New password</label>
            <input type="password" id="password" class="form-control" name="password" placeholder="Write new password if you wanna change it">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@main.layout>