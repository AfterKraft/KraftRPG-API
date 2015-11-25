/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.common.skill.base;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.projectile.explosive.fireball.Fireball;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.skill.SkillCastResult;
import com.afterkraft.kraftrpg.api.skill.SkillSetting;
import com.afterkraft.kraftrpg.common.skill.AbstractActiveSkill;

public class SkillFireball extends AbstractActiveSkill {

    public static final Text DESCRIPTION = Texts.of();

    public SkillFireball(RpgPlugin plugin) {
        super(plugin, "Fireball", DESCRIPTION);
        setDefault(SkillSetting.COOLDOWN, 10);
        setDefault(SkillSetting.DAMAGE, 3);
        setDefault(SkillSetting.MANA_COST, 4);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public SkillCastResult useSkill(SkillCaster caster) {
        Fireball fireball = (Fireball) caster.getWorld().createEntity(EntityTypes.SNOWBALL, caster
                .getLocation
                ().getPosition()).get();
        fireball.offer(Keys.FIRE_TICKS, 1000);
        fireball.offer(Keys.INVULNERABILITY, 1000);
        fireball.setShooter(caster);
        caster.launchProjectile(Fireball.class, caster.getLocation());
        return null;
    }
}
