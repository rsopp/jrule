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
package org.openhab.automation.jrule.rules.value;

import org.openhab.core.types.Command;
import org.openhab.core.types.State;

/**
 * The {@link JRuleValue} JRule Command
 *
 * @author Robert Delbrück - Initial contribution
 */
public interface JRuleValue {
    String toString();

    String stringValue();

    Command toOhCommand();

    State toOhState();

    <T extends JRuleValue> T as(Class<T> target);
}
