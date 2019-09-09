<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Roles</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Phone number</th>
            <th scope="col">Last name</th>
            <th scope="col">First name</th>
            <th scope="col">Second name</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.id}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.lastName}</td>
                <td>${user.firstName}</td>
                <td>${user.secondName}</td>
                <td><a href="/user/${user.id}">EDIT</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>