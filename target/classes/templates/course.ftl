<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <h1>${course.title}</h1>
    <table class="table table-bordered">
        <thead>
        <tr>
              <#list users as user>
            <th scope="col">${user.getLastName()} ${user.getFirstName()}</th>
            </#list>
        </tr>
        </thead>
        <tbody>
<#--        <#list users as user>-->
<#--            <tr>-->
<#--                <td>${user.id}</td>-->
<#--                <td><#list user.roles as role>${role}<#sep>, </#list></td>-->
<#--                <td>${user.username}</td>-->
<#--                <td><#if user.email??>${user.email}</#if></td>-->
<#--                <td>${user.phone}</td>-->
<#--                <td>${user.lastName}</td>-->
<#--                <td>${user.firstName}</td>-->
<#--                <td><#if user.secondName??>${user.secondName}</#if></td>-->
<#--                <td><a href="/userList/${user.id}">EDIT</a></td>-->
<#--            </tr>-->
<#--        </#list>-->
        </tbody>
    </table>


</@c.page>