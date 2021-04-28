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

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.UserModel;
import javax.ws.rs.core.Response;

/** @author <a href="mailto:hokum@dived.me">Andrey Kotov</a> */
public class AllowByDomainAction implements RequiredActionProvider {

    String[] allowedDomains = new String[] {};
    String templateName = "";

    @Override
    public void evaluateTriggers(RequiredActionContext requiredActionContext) {

    }


    @Override
    public void requiredActionChallenge(RequiredActionContext requiredActionContext) {

        // if action lacks configuration, let's allow user to be successful with this action
        if (templateName.equals("") || allowedDomains.length==0) {
            requiredActionContext.success();
            return;
        }

        UserModel userModel = requiredActionContext.getUser();
        String email = userModel.getEmail();

        boolean endsWithDomain = false;
        for (String domain : allowedDomains) {
            if (email.endsWith("@" + domain)) {
                endsWithDomain = true;
                break;
            }
        }

        if (endsWithDomain) {
            requiredActionContext.success();
            return;
        }

        LoginFormsProvider loginFormsProvider = requiredActionContext.form();
        Response challenge = loginFormsProvider.createForm(templateName);
        requiredActionContext.challenge(challenge);
    }

    @Override
    public void processAction(RequiredActionContext requiredActionContext) {

        // Еhere is no happy path for this challenge!
        // If challenge occurs, this means user's email doesn't matches allowed domains
        requiredActionContext.failure();
    }

    @Override
    public void close() {

    }
}
