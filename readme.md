# Keycloak-AllowByDomain

This required action checks user email's domain against whitelist, 
and blocks registration.

## Setup

Add node to **standalone.xml** or **standalone-ha.xml**

Here, template is a name of template to show, if registration is being blocked.

```xml
<spi name="required-action">
    <provider name="allow_by_domain" enabled="true">
        <properties>
            <property name="template" value="required-action-abd.ftl"/>
            <property name="domains" value="yourfirstdomain.com;anotherdomain.org"/>
        </properties>
    </provider>
</spi>
```

Next, add new template to your (or *keycloak*) theme: **required-action-abd.ftl**.

This template contains some new messages; add messages from attached messages_en.properties to the theme messages file.

## Realm configuration
Add new action: Authentication > Required actions, click "Register" button and select **"Allow user registration by email domain"**, then check it as **Default Action**.

