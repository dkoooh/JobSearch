<#import "../layout.ftlh" as main>

<@main.layout>
    <form class="col-6 mx-auto" method="POST" >
        <h1>Registration</h1>
        <div class="mb-3">
            <input type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Email" required>
            <div id="emailHelp" class="form-text">You won't be able to change it later</div>
        </div>
        <div class="mb-3">
            <input type="text" class="form-control" id="phoneNumber" placeholder="Phone number" required>
        </div>
        <div class="mb-3">
            <input type="text" class="form-control" id="name" placeholder="Name" required>
        </div>
        <div class="mb-3">
            <input type="text" class="form-control" id="surname" placeholder="Surname">
        </div>
        <div class="mb-3">
            <input type="number" min="16" max="100" class="form-control" id="age" placeholder="Your age" required>
        </div>
        <div class="mb-3">
            <input type="password" class="form-control" id="password" placeholder="New password" required>
        </div>
        <div class="mb-3">
            <select class="form-select" aria-label="Choose account type" required>
                <option selected disabled>Account type</option>
                <option value="APPLICANT">Applicant</option>
                <option value="EMPLOYER">Employer</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</@main.layout>