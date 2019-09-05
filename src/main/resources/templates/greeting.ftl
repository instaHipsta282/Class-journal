<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container">
    <h5>Hello, guest</h5>
    <div class="mx-auto mr-3">Class journal will be here soon</div>
    <div class="mx-auto mr-3">(c) Uncle George</div>
    <#if !user??>
        <div class="col-4 mx-auto ml-3">
            <form action="/login" method="post">
                <button type="submit" class="btn btn-primary btn-lg btn-block">Turn to login page</button>
            </form>
        </div>
    </#if>
    </div>
</@c.page>