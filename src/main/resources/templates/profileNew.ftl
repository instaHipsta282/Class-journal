<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
<#--    вся страница -->
    <div class="row">
        <#--        карточка профиля-->
        <div class="col-md-4">
            <div class="card-deck">
                <div class="card">
                    <#if user.photo??>
                        <img src="/img/${user.photo}" class="card-img-top">
                    </#if>
                    <div class="m-2">
                        <span>${user.lastName}</span>
                        <span>${user.firstName}</span>
                        <#if user.secondName??>${user.secondName}</#if>
                    </div>
                    <div class="m-2">
                        <span>Phone number: ${user.phone}</span>
                    </div>
                    <div class="m-2">
                        <#if user.email??>
                            <span>Email: ${user.email}</span>
                        </#if>
                    </div>
                </div>
            </div>
        </div>

        <#--        список из трех панелек-->
        <div class="col-md-6 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">

                            <a class="list-group-item list-group-item-action active" data-toggle="collapse"
                               href="#collapseOne" aria-expanded="false"
                               aria-controls="collapseOne">
                                Edit your security information
                            </a>
                        </h4>
                    </div>
                    <div class="collapse <#if somePasswordError?? || someEmailError?? || somePhoneError?? || someNameError??>show</#if>"
                         id="collapseOne">

                        <div class="form-group mt-3">
                            <div class="form-group">
                                <div class="panel panel-default">
                                    <li class="list-group-item">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" role="tab">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse"
                                                       href="#collapseTwo"
                                                       aria-expanded="false" aria-controls="collapseTwo">
                                                        Edit your password
                                                    </a>
                                                </h4>
                                            </div>

                                            <div class="collapse <#if somePasswordError??>show</#if>"
                                                 id="collapseTwo">
                                                <div class="form-group mt-3">
                                                    <form method="post" action="/changePassword"
                                                          enctype="multipart/form-data">
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">Current
                                                                password: </label>
                                                            <div class="col-sm-7">
                                                                <input type="password" name="oldPassword"
                                                                       class="form-control ${(oldPasswordError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter your current password"/>
                                                                <#if oldPasswordError??>
                                                                    <div class="invalid-feedback">
                                                                        ${oldPasswordError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">New
                                                                password: </label>
                                                            <div class="col-sm-7">
                                                                <input type="password" name="newPassword"
                                                                       class="form-control ${(newPasswordError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter new password"/>
                                                                <#if newPasswordError??>
                                                                    <div class="invalid-feedback">
                                                                        ${newPasswordError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">Re-enter new
                                                                password: </label>
                                                            <div class="col-sm-7">
                                                                <input type="password" name="newPasswordRe"
                                                                       class="form-control ${(newPasswordReError??)?string('is-invalid', '')}"
                                                                       placeholder="Re-enter new password"/>
                                                                <#if newPasswordReError??>
                                                                    <div class="invalid-feedback">
                                                                        ${newPasswordReError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                                        <button class="btn btn-primary" type="submit">Submit
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </div>
                            </div>


                            <div class="form-group">
                                <div class="panel panel-default">
                                    <li class="list-group-item">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" role="tab">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse"
                                                       href="#collapseThree"
                                                       aria-expanded="false" aria-controls="collapseThree">
                                                        Edit your email
                                                    </a>
                                                </h4>
                                            </div>

                                            <div class="collapse <#if someEmailError??>show</#if>"
                                                 id="collapseThree">
                                                <div class="form-group mt-3">
                                                    <form method="post" action="/changeEmail"
                                                          enctype="multipart/form-data">
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">Current
                                                                email: </label>
                                                            <div class="col-sm-7">
                                                                <input type="email" name="oldEmail"
                                                                       class="form-control ${(oldEmailError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter your current email"/>
                                                                <#if oldEmailError??>
                                                                    <div class="invalid-feedback">
                                                                        ${oldEmailError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">New
                                                                email: </label>
                                                            <div class="col-sm-7">
                                                                <input type="email" name="newEmail"
                                                                       class="form-control ${(newEmailError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter new email"/>
                                                                <#if newEmailError??>
                                                                    <div class="invalid-feedback">
                                                                        ${newEmailError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">Password:</label>
                                                            <div class="col-sm-7">
                                                                <input type="password" name="password"
                                                                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter your password"/>
                                                                <#if passwordError??>
                                                                    <div class="invalid-feedback">
                                                                        ${passwordError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <input type="hidden" name="_csrf"
                                                               value="${_csrf.token}"/>
                                                        <button class="btn btn-primary" type="submit">Submit
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </div>
                            </div>


                            <div class="form-group">
                                <div class="panel panel-default">
                                    <li class="list-group-item">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" role="tab">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse"
                                                       href="#collapseFour"
                                                       aria-expanded="false" aria-controls="collapseFour">
                                                        Edit your phone number
                                                    </a>
                                                </h4>
                                            </div>

                                            <div class="collapse <#if somePhoneError??>show</#if>"
                                                 id="collapseFour">
                                                <div class="form-group mt-3">
                                                    <form method="post" action="/changePhone"
                                                          enctype="multipart/form-data">
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">Current
                                                                phone number: </label>
                                                            <div class="col-sm-7">
                                                                <input type="text" name="oldPhone"
                                                                       class="form-control ${(oldPhoneError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter your current phone number"/>
                                                                <#if oldPhoneError??>
                                                                    <div class="invalid-feedback">
                                                                        ${oldPhoneError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">New
                                                                phone number: </label>
                                                            <div class="col-sm-7">
                                                                <input type="text" name="newPhone"
                                                                       class="form-control ${(newPhoneError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter new phone number"/>
                                                                <#if newPhoneError??>
                                                                    <div class="invalid-feedback">
                                                                        ${newPhoneError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">Password:</label>
                                                            <div class="col-sm-7">
                                                                <input type="password" name="password"
                                                                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter your password"/>
                                                                <#if passwordError??>
                                                                    <div class="invalid-feedback">
                                                                        ${passwordError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <input type="hidden" name="_csrf"
                                                               value="${_csrf.token}"/>
                                                        <button class="btn btn-primary" type="submit">Submit
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="panel panel-default">
                                    <li class="list-group-item">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" role="tab">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse"
                                                       href="#collapseFive"
                                                       aria-expanded="false" aria-controls="collapseFive">
                                                        Edit your first, second and last name
                                                    </a>
                                                </h4>
                                            </div>

                                            <div class="collapse <#if someNameError??>show</#if>"
                                                 id="collapseFive">
                                                <div class="form-group mt-3">
                                                    <form method="post" action="/changeName"
                                                          enctype="multipart/form-data">
                                                        <div class="form-group row">
                                                            <label class="col-sm-5 col-form-label">First name:</label>
                                                            <div class="col-sm-7">
                                                                <input type="text" name="firstName"
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
                                                            <label class="col-sm-5 col-form-label">Second name:</label>
                                                            <div class="col-sm-7">
                                                                <input type="text" name="secondName"
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
                                                            <label class="col-sm-5 col-form-label">Last name:</label>
                                                            <div class="col-sm-7">
                                                                <input type="text" name="lastName"
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
                                                            <label class="col-sm-5 col-form-label">Password:</label>
                                                            <div class="col-sm-7">
                                                                <input type="password" name="password"
                                                                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                                                                       placeholder="Enter your password"/>
                                                                <#if passwordError??>
                                                                    <div class="invalid-feedback">
                                                                        ${passwordError}
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <input type="hidden" name="_csrf"
                                                               value="${_csrf.token}"/>
                                                        <button class="btn btn-primary" type="submit">Submit
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingSix">
                        <h4 class="panel-title">

                            <a class="list-group-item list-group-item-action active" data-toggle="collapse"
                               href="#collapseSix" aria-expanded="false"
                               aria-controls="collapseSix">
                                Edit your courses
                            </a>
                        </h4>
                    </div>
                    <div class="collapse <#if newPasswordError??>show</#if>" id="collapseSix">
                        <div class="form-group mt-3">
                            <form method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <div class="panel panel-default">
                                        <div>kek</div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@c.page>