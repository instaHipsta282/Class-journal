<#import "parts/common.ftl" as c>

<@c.page>
    <div class="row">
        <div class="col-md-4">
            <form action="/user" method="post">
                <ul class="list-group">
                    <li class="list-group-item">
                        <#list roles as role>
                            <div class="form-check form-check-inline">
                                <label><input type="checkbox"
                                              name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
                                </label>
                            </div>
                        </#list>
                    </li>

                    <li class="list-group-item"><input type="text" value="${user.username}" name="username" placeholder="Enter user`s username"></li>
                    <li class="list-group-item"><input type="text" value="${user.email}" name="email" placeholder="Enter user`s email"></li>
                    <li class="list-group-item"><input type="text" value="${user.phone}" name="phone" placeholder="Enter user`s phone number"></li>
                    <li class="list-group-item"><input type="text" value="${user.lastName}" name="lastName" placeholder="Enter user`s last name"></li>
                    <li class="list-group-item"><input type="text" value="${user.firstName}" name="firstName" placeholder="Enter user`s first name"></li>
                    <li class="list-group-item"><input type="text" value="${user.secondName}" name="secondName" placeholder="Enter user`s second name"></li>

                    <input type="hidden" value="${user.id}" name="userId">
                    <input type="hidden" value="${_csrf.token}" name="_csrf">
                    <button type="submit" class="btn btn-primary">Save</button>
                </ul>
            </form>
        </div>
        <div class="col-md-4">
            kek
        </div>
    </div>
</@c.page>