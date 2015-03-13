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

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.powermock.api.easymock.PowerMock;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.text.message.Message.Text;
import org.spongepowered.api.text.message.Messages;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.skills.Skill;
import com.afterkraft.kraftrpg.api.skills.SkillManager;
import com.afterkraft.kraftrpg.api.skills.TestSkillAbstract;
import com.afterkraft.kraftrpg.api.util.FileUtils;
import com.afterkraft.kraftrpg.api.util.Util;

/**
 * Creates a mock server specifically designed for most tests required for the API tests.
 */
public class RPGTestCreator {

    public static final File pluginDirectory = new File("bin/test/server/plugins/rpgtest");
    public static final File serverDirectory = new File("bin/test/server");
    private RPGPlugin mockPlugin;
    private Skill mockSkill;

    public RPGPlugin getMockPlugin() {
        return this.mockPlugin;
    }

    public Skill getMockSkill() {
        return this.mockSkill;
    }

    public boolean setup() {
        try {
            pluginDirectory.mkdirs();

            assertTrue(pluginDirectory.exists());

            // Set up mockServer prep


            Server mockServer = createNiceMock(Server.class);
            Game mockGame = createNiceMock(Game.class);
            expect(mockGame.getApiVersion()).andStubReturn("1");
            expect(mockGame.getServer()).andStubReturn(Optional.of(mockServer));

            // Set up mockPlugin prep
            this.mockPlugin = createNiceMock(RPGPlugin.class);

            PowerMock.mockStatic(RpgCommon.class);
            expect(RpgCommon.getLogger()).andStubReturn(Util.logger);
            expect(RpgCommon.getPlugin()).andStubReturn(this.mockPlugin);

            PowerMock.mockStatic(Messages.class);
            Text mockText = createNiceMock(Text.class);
            expect(Messages.of(anyString())).andStubReturn(mockText);
            replay(mockText);
            PowerMock.replay(Messages.class);
            // Create test skill
            this.mockSkill = new TestSkillAbstract(this.mockPlugin);

            // Set up mockSkillManager
            SkillManager mockSkillManager = createNiceMock(SkillManager.class);
            expect(mockSkillManager.getSkill("TestSkill")).andReturn(Optional.of(this.mockSkill))
                    .times
                            (3);
            expect(mockSkillManager.getSkills())
                    .andStubReturn(ImmutableList.<Skill>builder().add(this.mockSkill).build());
            expect(mockSkillManager.hasSkill("TestSkill")).andStubReturn(true);

            // Finalize mockSkillManager
            replay(mockSkillManager);

            // Tell the mockPlugin to return the now ready mockSkillManager
            expect(this.mockPlugin.getSkillManager()).andReturn(mockSkillManager).times(4);


            // Finalize mockServer
            replay(mockServer);

            // Set the return for server
            expect(RpgCommon.getServer()).andReturn(mockServer).times(1);

            RpgCommon.finish();
            PowerMock.replay(RpgCommon.class);
            // Finalize mockPlugin
            replay(this.mockPlugin);

            // Just the little checks
            assertThat(this.mockPlugin.getSkillManager() != null, is(true));
            assertTrue(this.mockPlugin.getSkillManager().getSkill("TestSkill").isPresent());
            assertThat(this.mockPlugin.getSkillManager().getSkill("TestSkill").get(),
                       is(this.mockSkill));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cleanUp() {
        FileUtils.deleteFolder(serverDirectory);
        return true;
    }



}
