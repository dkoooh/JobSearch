<#import "../layout.ftlh" as main>

<@main.layout>
    <form class="col-6 mx-auto" action="/users/register" method="POST">
        <h1>Registration</h1>
        <div class="mb-3">
            <input type="email" name="email" class="form-control" aria-describedby="emailHelp" placeholder="Email" required>
            <div id="emailHelp" class="form-text">You won't be able to change it later</div>
        </div>
        <div class="mb-3">
            <input type="text" class="form-control" name="phoneNumber" placeholder="Phone number" required>
        </div>
        <div class="mb-3">
            <input type="text" class="form-control" name="name" placeholder="Name" required>
        </div>
        <div class="mb-3">
            <input type="text" class="form-control" name="surname" placeholder="Surname">
        </div>
        <div class="mb-3">
            <input type="number" min="16" max="100" class="form-control" name="age" placeholder="Your age" required>
        </div>
        <div class="mb-3">
            <input type="password" class="form-control" name="password" placeholder="New password" required>
        </div>
        <div class="mb-3">
            <select class="form-select" aria-label="Choose account type" name="accountType" required>
                <option selected disabled>Account type</option>
                <option value="APPLICANT">Applicant</option>
                <option value="EMPLOYER">Employer</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@main.layout>