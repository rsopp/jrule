/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
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
package org.openhab.automation.jrule.internal.engine.excutioncontext;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openhab.automation.jrule.internal.engine.JRuleEngine;
import org.openhab.automation.jrule.internal.engine.JRuleInvocationCallback;
import org.openhab.automation.jrule.rules.event.JRuleEvent;
import org.openhab.automation.jrule.rules.event.JRuleStartupEvent;
import org.openhab.core.events.AbstractEvent;

/**
 * The {@link JRuleStartupExecutionContext}
 *
 * @author Rüdiger Sopp - Initial contribution
 */
public class JRuleStartupExecutionContext extends JRuleExecutionContext {

    public JRuleStartupExecutionContext(String uid, String logName, String[] loggingTags,
            JRuleInvocationCallback invocationCallback, List<JRulePreconditionContext> preconditionContextList,
            Duration timedLock, Duration delayed) {
        super(uid, logName, loggingTags, invocationCallback, preconditionContextList, timedLock, delayed);
    }

    @Override
    public boolean match(AbstractEvent event, JRuleAdditionalCheckData checkData) {
        return false;
    }

    @Override
    public JRuleEvent createJRuleEvent(AbstractEvent event) {
        return new JRuleStartupEvent();
    }

    @Override
    public void setEnabled(boolean enabled) {
        boolean oldEnabled = this.isEnabled();

        super.setEnabled(enabled);

        if (enabled && !oldEnabled) {
            JRuleEngine jRuleEngine = JRuleEngine.get();
            if (jRuleEngine.matchPrecondition(this)) {
                jRuleEngine.invokeRule(this, createJRuleEvent(null));
            }
        }
    }

    @Override
    public String toString() {
        return "JRuleStartupExecutionContext{" + "logName='" + logName + '\'' + ", uid=" + uid + ", invocationCallback="
                + invocationCallback + ", loggingTags=" + Arrays.toString(loggingTags) + ", preconditionContextList="
                + preconditionContextList + '}';
    }
}
