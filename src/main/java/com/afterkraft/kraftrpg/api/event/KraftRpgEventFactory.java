/*
 * The MIT License (MIT)
 *
 * Copyright (c) Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.event;

import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.entity.combat.EnterCombatReason;
import com.afterkraft.kraftrpg.api.entity.combat.LeaveCombatReason;
import com.afterkraft.kraftrpg.api.event.combat.CombatEvent;
import com.afterkraft.kraftrpg.api.event.skill.SkillCastEvent;
import com.afterkraft.kraftrpg.api.event.skill.SkillCooldownEvent;
import com.afterkraft.kraftrpg.api.event.skill.SkillRegistrationEvent;
import com.afterkraft.kraftrpg.api.skill.Skill;
import java.util.HashMap;
import org.spongepowered.api.event.SpongeEventFactoryUtils;

public final class KraftRpgEventFactory {
    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link com.afterkraft.kraftrpg.api.event.combat.CombatEvent.Join}.
     * 
     * @param attacker The attacker
     * @param reason The reason
     * @param victim The victim
     * @return A new join combat event
     */
    public static CombatEvent.Join createCombatEventJoin(Insentient attacker, EnterCombatReason reason, Insentient victim) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("attacker", attacker);
        values.put("reason", reason);
        values.put("victim", victim);
        return SpongeEventFactoryUtils.createEventImpl(CombatEvent.Join.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link com.afterkraft.kraftrpg.api.event.combat.CombatEvent.Leave}.
     * 
     * @param attacker The attacker
     * @param reason The reason
     * @param victim The victim
     * @return A new leave combat event
     */
    public static CombatEvent.Leave createCombatEventLeave(Insentient attacker, LeaveCombatReason reason, Insentient victim) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("attacker", attacker);
        values.put("reason", reason);
        values.put("victim", victim);
        return SpongeEventFactoryUtils.createEventImpl(CombatEvent.Leave.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link com.afterkraft.kraftrpg.api.event.skill.SkillCastEvent}.
     * 
     * @param skill The skill
     * @param skillCaster The skill caster
     * @return A new skill cast event
     */
    public static SkillCastEvent createSkillCastEvent(Skill skill, SkillCaster skillCaster) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("skill", skill);
        values.put("skillCaster", skillCaster);
        return SpongeEventFactoryUtils.createEventImpl(SkillCastEvent.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link com.afterkraft.kraftrpg.api.event.skill.SkillCooldownEvent.End}.
     * 
     * @param caster The caster
     * @param skill The skill
     * @return A new end skill cooldown event
     */
    public static SkillCooldownEvent.End createSkillCooldownEventEnd(SkillCaster caster, Skill skill) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("caster", caster);
        values.put("skill", skill);
        return SpongeEventFactoryUtils.createEventImpl(SkillCooldownEvent.End.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link com.afterkraft.kraftrpg.api.event.skill.SkillCooldownEvent.Start}.
     * 
     * @param caster The caster
     * @param skill The skill
     * @param finalCooldown The final cooldown
     * @param originalCooldown The original cooldown
     * @return A new start skill cooldown event
     */
    public static SkillCooldownEvent.Start createSkillCooldownEventStart(SkillCaster caster, Skill skill, int finalCooldown, int originalCooldown) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("caster", caster);
        values.put("skill", skill);
        values.put("finalCooldown", finalCooldown);
        values.put("originalCooldown", originalCooldown);
        return SpongeEventFactoryUtils.createEventImpl(SkillCooldownEvent.Start.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link com.afterkraft.kraftrpg.api.event.skill.SkillCooldownEvent.Tick}.
     * 
     * @param caster The caster
     * @param skill The skill
     * @param originalCooldown The original cooldown
     * @param remainingCooldown The remaining cooldown
     * @return A new tick skill cooldown event
     */
    public static SkillCooldownEvent.Tick createSkillCooldownEventTick(SkillCaster caster, Skill skill, int originalCooldown, int remainingCooldown) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("caster", caster);
        values.put("skill", skill);
        values.put("originalCooldown", originalCooldown);
        values.put("remainingCooldown", remainingCooldown);
        return SpongeEventFactoryUtils.createEventImpl(SkillCooldownEvent.Tick.class, values);
    }

    /**
     * AUTOMATICALLY GENERATED, DO NOT EDIT.
     * Creates a new instance of
     * {@link com.afterkraft.kraftrpg.api.event.skill.SkillRegistrationEvent}.
     * 
     * @param plugin The plugin
     * @return A new skill registration event
     */
    public static SkillRegistrationEvent createSkillRegistrationEvent(RpgPlugin plugin) {
        HashMap<String, Object> values = new HashMap<>();
        values.put("plugin", plugin);
        return SpongeEventFactoryUtils.createEventImpl(SkillRegistrationEvent.class, values);
    }
}

