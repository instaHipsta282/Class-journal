<#--<#import "parts/common.ftl" as c>-->
<#--<#import "parts/login.ftl" as l>-->

<#--<@c.page>-->
<#--    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>-->
<#--        <div class="alert alert-danger" role="alert">-->
<#--            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}-->
<#--        </div>-->
<#--    </#if>-->
<#--    <#if message??>-->
<#--        <div class="alert alert-${messageType}" role="alert">-->
<#--            ${message}-->
<#--        </div>-->
<#--    </#if>-->
<#--    <@l.login "/login" false />-->
<#--</@c.page>-->

<#import "parts/common.ftl" as c>

<@c.page>

    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>

    <form action="/login" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" for="username">User Name :</label>
            <div class="col-sm-7">
                <input type="text" name="username" id="username"
                       value="<#if user??>${user.username}</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="User name"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label" for="password">Password:</label>
            <div class="col-sm-7">
                <input type="password" name="password" id="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Log in</button>
    </form>
</@c.page>
