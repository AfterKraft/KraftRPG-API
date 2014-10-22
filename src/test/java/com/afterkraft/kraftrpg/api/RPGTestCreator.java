/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Gabriel Harris-Rouquette
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.afterkraft.kraftrpg.api;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Level;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.api.skills.SkillManager;
import com.afterkraft.kraftrpg.api.skills.TestSkill;
import com.afterkraft.kraftrpg.api.util.FileUtils;
import com.afterkraft.kraftrpg.api.util.Util;

/**
 * Creates a mock server specifically designed for most tests required for the API tests.
 */
public class RPGTestCreator {

    public static final File pluginDirectory = new File("bin/test/server/plugins/rpgtest");
    public static final File serverDirectory = new File("bin/test/server");
    private RPGPlugin mockPlugin;
    private ISkill mockSkill;

    public RPGPlugin getMockPlugin() {
        return this.mockPlugin;
    }

    public ISkill getMockSkill() {
        return this.mockSkill;
    }

    public boolean setup() {
        try {
            pluginDirectory.mkdirs();

            assertTrue(pluginDirectory.exists());

            // Set up mockServer prep
            Server mockServer = createNiceMock(Server.class);
            expect(mockServer.getName()).andStubReturn("MockServer");
            expect(mockServer.getVersion()).andStubReturn("1");
            expect(mockServer.getBukkitVersion()).andStubReturn("1.7.9");
            expect(mockServer.getLogger()).andStubReturn(Util.logger);

            PluginDescriptionFile descriptionFile = new PluginDescriptionFile("TestRPG", "1.0.0",
                    "com.afterkraft.kraftrpg.api.RPGTestCreator");

            // Set up mockPlugin prep
            this.mockPlugin = createNiceMock(RPGPlugin.class);
            expect(this.mockPlugin.getDataFolder()).andStubReturn(pluginDirectory);
            expect(this.mockPlugin.getName()).andStubReturn("TestRPG");
            expect(this.mockPlugin.getServer()).andStubReturn(mockServer);
            expect(this.mockPlugin.getDescription()).andStubReturn(descriptionFile);

            // Create test skill
            this.mockSkill = new TestSkill(this.mockPlugin);

            // Set up mockSkillManager
            SkillManager mockSkillManager = createNiceMock(SkillManager.class);
            expect(mockSkillManager.getSkill("TestSkill")).andReturn(this.mockSkill).times(3);
            expect(mockSkillManager.getSkills())
                    .andStubReturn(ImmutableList.<ISkill>builder().add(this.mockSkill).build());
            expect(mockSkillManager.hasSkill("TestSkill")).andStubReturn(true);

            // Finalize mockSkillManager
            replay(mockSkillManager);

            // Tell the mockPlugin to return the now ready mockSkillManager
            expect(this.mockPlugin.getSkillManager()).andReturn(mockSkillManager).times(4);


            Plugin[] plugins = new Plugin[]{this.mockPlugin};

            // Set up mockPluginManager
            PluginManager mockPluginManager = createNiceMock(PluginManager.class);
            expect(mockPluginManager.getPlugins()).andStubReturn(plugins);
            expect(mockPluginManager.getPlugin("KraftRPG")).andStubReturn(this.mockPlugin);
            expect(mockPluginManager.getPlugin("TestRPG")).andStubReturn(this.mockPlugin);
            expect(mockPluginManager.getPermission(anyString())).andStubReturn(null);

            // Finalize mockPluginManager
            replay(mockPluginManager);

            // Set the return for plugin manager
            expect(mockServer.getPluginManager()).andStubReturn(mockPluginManager);

            // Finalize mockServer
            replay(mockServer);

            // Set the return for server
            expect(this.mockPlugin.getServer()).andReturn(mockServer).times(1);

            // Finalize mockPlugin
            replay(this.mockPlugin);

            // API call for the server
            Bukkit.setServer(mockServer);

            // Just the little checks
            assertThat(this.mockPlugin.getSkillManager() != null, is(true));
            assertThat(this.mockPlugin.getSkillManager().getSkill("TestSkill"), is(this.mockSkill));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cleanUp() {
        try {
            Field serverField = Bukkit.class.getDeclaredField("server");
            serverField.setAccessible(true);
            serverField.set(Class.forName("org.bukkit.Bukkit"), null);
        } catch (Exception e) {
            Util.log(Level.SEVERE,
                    "Error while trying to unregister the server. "
                            + "Has the Bukkit implementation changed?");
            e.printStackTrace();
            fail(e.getMessage());
            return false;
        }

        FileUtils.deleteFolder(serverDirectory);
        return true;
    }

}
