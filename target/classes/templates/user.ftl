<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
    <div class="row">
        <div class="col-md-4">
            <div class="card-deck">
                <div class="card">
                    <#if currentUser.photo??>
                        <img src="/img/${currentUser.photo}" class="card-img-top">
                    </#if>
                    <div class="m-2">
                        <span>${currentUser.lastName}</span>
                        <span>${currentUser.firstName}</span>
                        <#if currentUser.secondName??>${currentUser.secondName}</#if>
                    </div>
                    <div class="m-2">
                        <span>Phone number: ${currentUser.phone}</span>
                    </div>
                    <div class="m-2">
                        <#if currentUser.email??>
                            <span>Email: ${currentUser.email}</span>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <table class="table">
                <thead class="badge-primary badge-lg badge-block">
                <tr>
                    <th scope="col">Title</th>
                    <th scope="col">Start date</th>
                    <th scope="col">End date</th>
                    <th scope="col">AVG score</th>
                </tr>
                </thead>
                <tbody>
                <#list courses as course, score>
                    <tr>
                        <td>${course.getTitle()}</td>
                        <td>${course.getStartDate()}</td>
                        <td>${course.getEndDate()}</td>
                        <td>${score}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

</@c.page>