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
import java.util.Set;

import org.openhab.automation.jrule.internal.JRuleLog;
import org.openhab.automation.jrule.internal.handler.JRuleEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link JRuleGroupColorItem} Items
 *
 * @author Arne Seime - Initial contribution
 */
public abstract class JRuleGroupNumberItem extends JRuleGroupItem {

    private static final String LOG_NAME = "JRuleGroupNumberItem";
    private static final Logger logger = LoggerFactory.getLogger(JRuleGroupNumberItem.class);

    protected JRuleGroupNumberItem(String itemName) {
        super(itemName);
    }

    public static JRuleGroupNumberItem forName(String itemName) {
        return JRuleItemRegistry.get(itemName, JRuleGroupNumberItem.class);
    }

    public Double getState() {
        return JRuleEventHandler.get().getStateFromItemAsDouble(itemName);
    }

    public void sendCommand(double value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(itemName);
        groupMemberNames.forEach(m -> JRuleEventHandler.get().sendCommand(m, value));
    }

    public void postUpdate(double value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(itemName);
        groupMemberNames.forEach(m -> JRuleEventHandler.get().postUpdate(m, value));
    }

    public void sendCommand(int value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(itemName);
        groupMemberNames.forEach(m -> JRuleEventHandler.get().sendCommand(m, value));
    }

    public void postUpdate(int value) {
        final Set<String> groupMemberNames = JRuleEventHandler.get().getGroupMemberNames(itemName);
        groupMemberNames.forEach(m -> JRuleEventHandler.get().postUpdate(m, value));
    }

    // Persistence method
    public Double getHistoricState(ZonedDateTime timestamp, String persistenceServiceId) {
        String string = JRulePersistenceExtentions.historicState(itemName, timestamp, persistenceServiceId);
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            JRuleLog.error(logger, LOG_NAME,
                    "Failed to get Historic state for value: {} itemName: {} timestamp: {} persistanceServiceId: {}",
                    string, itemName, timestamp, persistenceServiceId);
            return null;
        }
    }
}
