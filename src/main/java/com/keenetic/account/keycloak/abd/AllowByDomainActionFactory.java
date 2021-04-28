/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.keenetic.account.keycloak.abd;

import org.keycloak.Config;
import org.keycloak.authentication.*;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/** @author <a href="mailto:hokum@dived.me">Andrey Kotov</a> */
public class AllowByDomainActionFactory implements RequiredActionFactory  {

    private static final AllowByDomainAction SINGLETON = new AllowByDomainAction();

    @Override
    public String getDisplayText() {
        return "Allow user registration by email domain";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession keycloakSession) {
        return SINGLETON;
    }

    @Override
    public void init(Config.Scope scope) {
        // load allowed email domains from config

        String allowedDomainsS = scope.get("domains", "");
        String[] allowedDomains;
        if (allowedDomainsS.contains(",")) {
            allowedDomains = allowedDomainsS.split(",");
        } else {
            allowedDomains = allowedDomainsS.split(";");
        }

        for (int i=0; i< allowedDomains.length; i++) {
            allowedDomains[i] = allowedDomains[i].trim();
        }
        SINGLETON.allowedDomains = allowedDomains;
        SINGLETON.templateName = scope.get("template", "");
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "allow_by_domain";
    }

}
