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
                    <div class="collapse <#if somePasswordError??>show</#if>" id="collapseOne">
                        <div class="form-group mt-3">
                            <form method="post"  action="/changePassword" enctype="multipart/form-data">
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
                                                        <form method="post">
                                                            <div class="form-group row">
                                                                <label class="col-sm-5 col-form-label">Old
                                                                    password: </label>
                                                                <div class="col-sm-7">
                                                                    <input type="password" name="oldPassword"
                                                                           class="form-control ${(oldPasswordError??)?string('is-invalid', '')}"
                                                                           placeholder="Password"/>
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
                                                                           placeholder="Password"/>
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
                                                                           placeholder="Password"/>
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

                                                <div class="collapse <#if newPasswordError??>show</#if>"
                                                     id="collapseThree">
                                                    <div class="form-group mt-3">
                                                        <div>hohoho</div>
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

                                                <div class="collapse <#if newPasswordError??>show</#if>"
                                                     id="collapseFour">
                                                    <div class="form-group mt-3">
                                                        <div>hohoho</div>
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

                                                <div class="collapse <#if newPasswordError??>show</#if>"
                                                     id="collapseFive">
                                                    <div class="form-group mt-3">
                                                        <div>hohoho</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </div>
                                </div>

                            </form>
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


            <#--            <div id="accordion" role="tablist" aria-multiselectable="true">-->
            <#--&lt;#&ndash;                секретная информация&ndash;&gt;-->
            <#--                <div class="panel panel-default">-->
            <#--                    <div class="panel-heading" role="tab" id="headingOne">-->
            <#--                        <h4 class="panel-title">-->
            <#--                            -->
            <#--                            <a class="list-group-item list-group-item-action active" data-toggle="collapse"-->
            <#--                               data-parent="#accordion" href="#collapseOne" aria-expanded="false"-->
            <#--                               aria-controls="collapseOne">-->
            <#--                                -->
            <#--                                Edit your security information-->
            <#--                            -->
            <#--                            </a>-->
            <#--                            -->
            <#--                        </h4>-->
            <#--                    </div>-->
            <#--                    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel"-->
            <#--                         aria-labelledby="headingThree">-->
            <#--                        <ul class="list-group m-2">-->
            <#--                            <li class="list-group-item">-->
            <#--                                <div class="panel panel-default">-->
            <#--                                    <div class="panel-heading" role="tab" id="headingThree">-->
            <#--                                        <h4 class="panel-title">-->
            <#--                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree"-->
            <#--                                               aria-expanded="false" aria-controls="collapseThree">-->
            <#--                                                Edit your password-->
            <#--                                            </a>-->
            <#--                                        </h4>-->
            <#--                                    </div>-->
            <#--                                    <div id="collapseThree" class="panel-collapse collapse in" role="tabpanel"-->
            <#--                                         aria-labelledby="headingThree">-->
            <#--                                        <form method="post">-->
            <#--                                            <div class="form-group row">-->
            <#--                                                <label class="col-sm-5 col-form-label">Old password: </label>-->
            <#--                                                <div class="col-sm-7">-->
            <#--                                                    <input type="password" name="oldPassword"-->
            <#--                                                           class="form-control ${(oldPasswordError??)?string('is-invalid', '')}"-->
            <#--                                                           placeholder="Password"/>-->
            <#--                                                    <#if oldPasswordError??>-->
            <#--                                                        <div class="invalid-feedback">-->
            <#--                                                            ${oldPasswordError}-->
            <#--                                                        </div>-->
            <#--                                                    </#if>-->
            <#--                                                </div>-->
            <#--                                            </div>-->
            <#--                                        <div class="form-group row">-->
            <#--                                            <label class="col-sm-5 col-form-label">New password: </label>-->
            <#--                                            <div class="col-sm-7">-->
            <#--                                                <input type="password" name="newPassword"-->
            <#--                                                       class="form-control ${(newPasswordError??)?string('is-invalid', '')}"-->
            <#--                                                       placeholder="Password"/>-->
            <#--                                                <#if newPasswordError??>-->
            <#--                                                    <div class="invalid-feedback">-->
            <#--                                                        ${newPasswordError}-->
            <#--                                                    </div>-->
            <#--                                                </#if>-->
            <#--                                            </div>-->
            <#--                                        </div>-->
            <#--                                        <div class="form-group row">-->
            <#--                                            <label class="col-sm-5 col-form-label">Re-enter new password: </label>-->
            <#--                                            <div class="col-sm-7">-->
            <#--                                                <input type="password" name="newPasswordRe"-->
            <#--                                                       class="form-control ${(newPasswordReError??)?string('is-invalid', '')}"-->
            <#--                                                       placeholder="Password"/>-->
            <#--                                                <#if newPasswordReError??>-->
            <#--                                                    <div class="invalid-feedback">-->
            <#--                                                        ${newPasswordReError}-->
            <#--                                                    </div>-->
            <#--                                                </#if>-->
            <#--                                            </div>-->
            <#--                                        </div>-->
            <#--                                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
            <#--                                            <button class="btn btn-primary" type="submit">Submit</button>-->
            <#--                                        </form>-->
            <#--                                    </div>-->
            <#--                                </div>-->
            <#--                            </li>-->
            <#--                            <li class="list-group-item">-->
            <#--                                <div class="panel panel-default">-->
            <#--                                    <div class="panel-heading" role="tab" id="headingFour">-->
            <#--                                        <h4 class="panel-title">-->
            <#--                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour"-->
            <#--                                               aria-expanded="true" aria-controls="collapseFour">-->
            <#--                                                Edit your email-->
            <#--                                            </a>-->
            <#--                                        </h4>-->
            <#--                                    </div>-->
            <#--                                    <div id="collapseFour" class="panel-collapse collapse in" role="tabpanel"-->
            <#--                                         aria-labelledby="headingFour">-->
            <#--                                        Form-->
            <#--                                    </div>-->
            <#--                                </div>-->
            <#--                            </li>-->
            <#--                            <li class="list-group-item">-->
            <#--                                <div class="panel panel-default">-->
            <#--                                    <div class="panel-heading" role="tab" id="headingFive">-->
            <#--                                        <h4 class="panel-title">-->
            <#--                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive"-->
            <#--                                               aria-expanded="true" aria-controls="collapseFive">-->
            <#--                                                Edit your phone number-->
            <#--                                            </a>-->
            <#--                                        </h4>-->
            <#--                                    </div>-->
            <#--                                    <div id="collapseFive" class="panel-collapse collapse in" role="tabpanel"-->
            <#--                                         aria-labelledby="headingFive">-->
            <#--                                        Form-->
            <#--                                    </div>-->
            <#--                                </div>-->
            <#--                            </li>-->
            <#--                            <li class="list-group-item">-->
            <#--                                <div class="panel panel-default">-->
            <#--                                    <div class="panel-heading" role="tab" id="headingSix">-->
            <#--                                        <h4 class="panel-title">-->
            <#--                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseSix"-->
            <#--                                               aria-expanded="true" aria-controls="collapseSix">-->
            <#--                                                Edit your first, second or last name-->
            <#--                                            </a>-->
            <#--                                        </h4>-->
            <#--                                    </div>-->
            <#--                                    <div id="collapseSix" class="panel-collapse collapse in" role="tabpanel"-->
            <#--                                         aria-labelledby="headingSix">-->
            <#--                                        Form-->
            <#--                                    </div>-->
            <#--                                </div>-->
            <#--                            </li>-->
            <#--                        </ul>-->
            <#--                    </div>-->
            <#--                </div>-->
            <#--&lt;#&ndash;                курсы&ndash;&gt;-->
            <#--                <div class="panel panel-default">-->
            <#--                    <div class="panel-heading" role="tab" id="headingTwo">-->
            <#--                        <h4 class="panel-title">-->
            <#--                            <a class="list-group-item list-group-item-action active" data-toggle="collapse"-->
            <#--                               data-parent="#accordion" href="#collapseTwo" aria-expanded="false"-->
            <#--                               aria-controls="collapseTwo">-->
            <#--                                Edit your courses-->
            <#--                            </a>-->
            <#--                        </h4>-->
            <#--                    </div>-->
            <#--                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">-->
            <#--                        Some text-->
            <#--                    </div>-->
            <#--                </div>-->

            <#--            </div>-->
            <#--        </div>-->
        </div>
    </div>
</@c.page>