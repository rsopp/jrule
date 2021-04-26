/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.jrule.internal;

import java.io.File;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link JRuleMachineThingConfig} encapsulates all the configuration options for an instance of the
 * {@link JRuleClientThingHandler}.
 *
 * @author Joseph (Seaside) Hagberg - Initial contribution
 */
@NonNullByDefault
public class JRuleConfig {
    public static final String ITEMS_PACKAGE = "org.openhab.binding.jrule.items.generated.";
    public static final String RULES_PACKAGE = "org.openhab.binding.jrule.rules.user.";

    public static final String ITEMS_DIR_START = "gen";

    public static final String ITEMS_DIR = ITEMS_DIR_START + File.separator + "org" + File.separator + "openhab"
            + File.separator + "binding" + File.separator + "jrule" + File.separator + "items" + File.separator
            + "generated" + File.separator;

    public static final String RULES_DIR_START = "rules";
    public static final String RULES_DIR = RULES_DIR_START + File.separator + "org" + File.separator + "openhab"
            + File.separator + "binding" + File.separator + "jrule" + File.separator + "rules" + File.separator + "user"
            + File.separator;

    private String workingDirectory = "";

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}