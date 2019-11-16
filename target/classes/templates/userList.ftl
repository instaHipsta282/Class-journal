<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>

    <table class="table">
        <thead class="badge-primary badge-lg badge-block">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Last name</th>
            <th scope="col">First name</th>
            <th scope="col">Courses</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>
                    <a href="/userList/${user.id}">${user.id}</a>
                </td>
                <td>${user.lastName}</td>
                <td>${user.firstName}</td>
                <td>
                    <div class="btn-group">
                        <button type="button" class="btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Course list
                        </button>
                        <div class="dropdown-menu">
                            <#list user.getCourses() as course>
                                <a class="dropdown-item">${course.getTitle()}</a>
                            </#list>
                        </div>
                    </div>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

</@c.page>
