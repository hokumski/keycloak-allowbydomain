<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=true; section>
    <#if section = "header">
        ${msg("abdTitle")}
    <#elseif section = "form">
    <div id="kc-terms-text">
        ${kcSanitize(msg("abdText"))?no_esc}
    </div>
    <form class="form-actions" action="${url.loginAction}" method="POST">
    <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="accept" id="kc-accept" type="submit" value="${msg("abdUnderstand")}"/>
    </form>
    <div class="clearfix"></div>
    </#if>
</@layout.registrationLayout>