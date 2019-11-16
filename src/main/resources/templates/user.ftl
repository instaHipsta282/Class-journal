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

</@c.page>