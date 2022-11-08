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
package org.openhab.binding.jrule.internal.codegenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openhab.automation.jrule.actions.JRuleActionClassGenerator;
import org.openhab.automation.jrule.internal.JRuleConfig;
import org.openhab.automation.jrule.internal.compiler.JRuleCompiler;
import org.openhab.binding.jrule.internal.thingaction.MyThingActions;
import org.openhab.core.magic.binding.handler.MagicActionModuleThingHandler;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.openhab.core.thing.binding.ThingHandlerService;
import org.openhab.core.thing.internal.ThingImpl;

/**
 * The {@link JRuleActionClassGeneratorTest}
 *
 * 
 * @author Robert Delbrück - Initial contribution
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JRuleActionClassGeneratorTest {

    private JRuleActionClassGenerator sourceFileGenerator;
    private File targetFolder;
    private JRuleCompiler compiler;

    @BeforeAll
    public void setup() {
        targetFolder = new File("target/gen/org/openhab/automation/jrule/generated/actions/");
        targetFolder.mkdirs();

        Map<String, Object> map = new HashMap<>();
        map.put("org.openhab.automation.jrule.directory", "target");
        JRuleConfig config = new JRuleConfig(map);
        sourceFileGenerator = new JRuleActionClassGenerator(config);
        compiler = new JRuleCompiler(config);
    }

    @BeforeEach
    public void wipeFiles() {
        // Wipe any existing files
        Arrays.stream(targetFolder.listFiles()).forEach(File::delete);
    }

    @Test
    public void testGenerateAndCompileActionFile() {

        ThingImpl thing = new ThingImpl(new ThingTypeUID("mybinding", "thingtype"),
                new ThingUID("mybinding", "thingtype", "id"));
        thing.setHandler(new MagicActionModuleThingHandler(thing) {
            @Override
            public Collection<Class<? extends ThingHandlerService>> getServices() {
                return List.of(MyThingActions.class);
            }
        });
        generateAndCompile(thing);
    }

    @Test
    public void testGenerateActionsFile() {
        Thing thing1 = new ThingImpl(new ThingTypeUID("mybinding", "thingtype"),
                new ThingUID("mybinding", "thingtype", "id1"));
        thing1.setHandler(new MagicActionModuleThingHandler(thing1) {
            @Override
            public Collection<Class<? extends ThingHandlerService>> getServices() {
                return List.of(MyThingActions.class);
            }
        });
        generateAndCompile(thing1);

        Thing thing2 = new ThingImpl(new ThingTypeUID("mybinding", "thingtype"),
                new ThingUID("mybinding", "thingtype", "id2"));
        thing2.setHandler(new MagicActionModuleThingHandler(thing2) {
            @Override
            public Collection<Class<? extends ThingHandlerService>> getServices() {
                return List.of(MyThingActions.class);
            }
        });
        generateAndCompile(thing2);

        List<Thing> things = List.of(thing1, thing2);

        boolean success = sourceFileGenerator.generateActionsSource(things);
        assertTrue(success, "Failed to generate source file for things");

        compiler.compile(List.of(new File(targetFolder, "JRuleActions.java")), "target/classes:target/gen");

        File compiledClass = new File(targetFolder, "JRuleActions.class");
        assertTrue(compiledClass.exists());
    }

    private void generateAndCompile(Thing thing) {
        boolean success = sourceFileGenerator.generateActionSource(thing);
        assertTrue(success, "Failed to generate source file for " + thing);

        compiler.compile(List.of(new File(targetFolder, "_" + getActionFriendlyName(thing) + ".java")),
                "target/classes");

        File compiledClass = new File(targetFolder, "_" + getActionFriendlyName(thing) + ".class");
        assertTrue(compiledClass.exists());
    }

    public static String getActionFriendlyName(Thing thing) {
        return Arrays.stream(thing.getUID().toString().split("[:\\-]")).map(StringUtils::capitalize)
                .collect(Collectors.joining(""));
    }
}