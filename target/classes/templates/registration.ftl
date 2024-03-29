<#import "parts/common.ftl" as c>

<@c.page>

    <form action="/registration" method="post" enctype="multipart/form-data">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-7">
                <input type="text" name="username"
                       value="<#if user??>${user.username}</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="User name"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-7">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Re-enter password:</label>
            <div class="col-sm-7">
                <input type="password" name="password2"
                       class="form-control ${(password2Error??)?string('is-invalid', '')}"
                       placeholder="Retype password"/>
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-7">
                <input type="email" name="email"
                       value="<#if user??>${user.email}</#if>"
                       class="form-control ${(emailError??)?string('is-invalid', '')}"
                       placeholder="Enter your email"/>
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Last name:</label>
            <div class="col-sm-7">
                <input type="text" name="lastName"
                       value="<#if user??>${user.lastName}</#if>"
                       class="form-control ${(lastNameError??)?string('is-invalid', '')}"
                       placeholder="Enter your last name"/>
                <#if lastNameError??>
                    <div class="invalid-feedback">
                        ${lastNameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">First name:</label>
            <div class="col-sm-7">
                <input type="text" name="firstName"
                       value="<#if user??>${user.firstName}</#if>"
                       class="form-control ${(firstNameError??)?string('is-invalid', '')}"
                       placeholder="Enter your first name"/>
                <#if firstNameError??>
                    <div class="invalid-feedback">
                        ${firstNameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Second name:</label>
            <div class="col-sm-7">
                <input type="text" name="secondName"
                       value="<#if user??>${user.secondName}</#if>"
                       class="form-control ${(secondNameError??)?string('is-invalid', '')}"
                       placeholder="Enter your second name"/>
                <#if secondNameError??>
                    <div class="invalid-feedback">
                        ${secondNameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Phone number:</label>
            <div class="col-sm-7">
                <input type="text" name="phone"
                       value="<#if user??>${user.phone}</#if>"
                       class="form-control ${(phoneError??)?string('is-invalid', '')}"
                       placeholder="Enter your phone number"/>
                <#if phoneError??>
                    <div class="invalid-feedback">
                        ${phoneError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="usersPhoto" id="usersPhoto">
                <label class="custom-file-label col-sm-9" for="usersPhoto">Choose your photo</label>
            </div>
        </div>
        <div class="form-group">
            <div class="g-recaptcha" data-sitekey="6LcOVbQUAAAAAEPYcX8DRbIeN0VV7pUNzoIMn7d6"></div>
            <#if captchaError??>
                <div class="alert alert-danger" role="alert">
                    ${captchaError}
                </div>
            </#if>
        </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Sign up</button>
    </form>

</@c.page>