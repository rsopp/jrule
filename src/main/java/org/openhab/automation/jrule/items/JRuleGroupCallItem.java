/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
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
package org.openhab.automation.jrule.items;

import java.time.ZonedDateTime;

import org.openhab.automation.jrule.internal.handler.JRuleEventHandler;

/**
 * The {@link JRuleGroupCallItem} Items
 *
 * @author Arne Seime - Initial contribution
 */
public abstract class JRuleGroupCallItem extends JRuleGroupItem {

    protected JRuleGroupCallItem(String itemName) {
        super(itemName);
    }

    public static JRuleGroupCallItem forName(String itemName) {
        return JRuleItemRegistry.get(itemName, JRuleGroupCallItem.class);
    }

    public String getState() {
        return JRuleEventHandler.get().getStringValue(itemName);
    }

    // Persistence method
    public String getHistoricState(ZonedDateTime timestamp, String persistenceServiceId) {
        return JRulePersistenceExtentions.historicState(itemName, timestamp, persistenceServiceId);
    }
}
