<#import "parts/common.ftl" as c>
<#assign currentUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal />
<script>
    $('#myModal').on('submit', function() {
        $(#myModal).on('hide.bs.modal', function ( e ) {
            e.preventDefault();
        })
    });
</script>

<@c.page>
    <div class="row">
        <div class="col-md-4">
            <div class="m-2">
                <form action="/userList" method="post">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <#if roles??>
                                <#list roles as role>
                                    <div class="form-check form-check-inline">
                                        <label><input type="checkbox"
                                                      name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
                                        </label>
                                    </div>
                                </#list>
                            </#if>
                        </li>

                        <li class="list-group-item">
                            <input type="text"
                                   value="<#if user.username??>${user.username}</#if>"
                                   name="username"
                                   placeholder="Enter user`s username">
                        </li>
                        <li class="list-group-item">
                            <input type="text"
                                   value="<#if user.email??>${user.email}</#if>"
                                   name="email"
                                   placeholder="Enter user`s email">
                        </li>
                        <li class="list-group-item">
                            <input type="text"
                                   value="<#if user.phone??>${user.phone}</#if>"
                                   name="phone"
                                   placeholder="Enter user`s phone number">
                        </li>
                        <li class="list-group-item">
                            <input type="text"
                                   value="<#if user.lastName??>${user.lastName}</#if>"
                                   name="lastName"
                                   placeholder="Enter user`s last name">
                        </li>
                        <li class="list-group-item">
                            <input type="text"
                                   value="<#if user.firstName??>${user.firstName}</#if>"
                                   name="firstName"
                                   placeholder="Enter user`s first name">
                        </li>
                        <li class="list-group-item">
                            <input type="text"
                                   value="<#if user.secondName??>${user.secondName}</#if>"
                                   name="secondName"
                                   placeholder="Enter user`s second name">
                        </li>

                        <input type="hidden" value="${user.id}" name="userId">
                        <input type="hidden" value="${_csrf.token}" name="_csrf">

                        <button type="submit" class="btn btn-primary btn-lg btn-block">Save</button>


                    </ul>
                </form>
            </div>
            <div class="m-2">
                <ul class="list-group">

                    <button type="button" class="btn btn-danger btn-lg btn-block" data-toggle="modal"
                            data-target="#myModal" >
                        Delete
                    </button>
                    <!-- Модальное окно -->

                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                    <h4 class="modal-title" id="myModalLabel">Are you sure that you want to delete the
                                        user profile</h4>
                                </div>
                                <div class="modal-body">
                                    <form method="post"
                                          enctype="multipart/form-data">
                                        <div class="form-group row">
                                            <label class="col-sm-5 col-form-label">Password: </label>
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
                                        <input type="hidden" value="${user.id}" name="userId">
                                        <input type="hidden" name="_csrf"
                                               value="${_csrf.token}"/>
                                        <button class="btn btn-primary" type="submit" formaction="/userList/confirmPassword">Submit
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
        <div class="col-md-4">
            Courses list
        </div>
    </div>
</@c.page>