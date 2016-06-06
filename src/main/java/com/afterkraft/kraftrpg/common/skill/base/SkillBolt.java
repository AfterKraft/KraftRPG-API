/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 Gabriel Harris-Rouquette
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

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.RpgPlugin;
import com.afterkraft.kraftrpg.api.entity.Being;
import com.afterkraft.kraftrpg.api.entity.SkillCaster;
import com.afterkraft.kraftrpg.api.event.damage.SkillDamageSource;
import com.afterkraft.kraftrpg.api.skill.SkillCastResult;
import com.afterkraft.kraftrpg.api.skill.SkillSetting;
import com.afterkraft.kraftrpg.api.skill.SkillType;
import com.afterkraft.kraftrpg.common.skill.SkillUtils;
import com.afterkraft.kraftrpg.common.skill.TargetedSkillAbstract;

public class SkillBolt extends TargetedSkillAbstract<Living> {

    public static final Text DESCRIPTION = Texts.of("Strikes down lightning strikes at the targeted entity dealing "
                                                            + "damage and striking down entities near the target.");
    public static final ItemStack REAGENT = ItemStack.of(ItemTypes.GUNPOWDER, 1);

    public SkillBolt(RpgPlugin plugin) {
        super(plugin, "Bolt", DESCRIPTION, Living.class);
        setDefault(SkillSetting.MAX_DISTANCE, 20);
        setDefault(SkillSetting.DAMAGE, 100, 10);
        setDefault(SkillSetting.REAGENT, REAGENT);
        setSkillTypes(SkillType.DAMAGING, SkillType.ABILITY_PROPERTY_LIGHTNING,
                      SkillType.AREA_OF_EFFECT, SkillType.AGGRESSIVE);
    }

    @Override
    public SkillCastResult useSkill(final SkillCaster caster, final Being target,
                                    final Living entity) {
        double damage = RpgCommon.getSkillConfigManager()
                .getUsedDoubleSetting(caster, this, SkillSetting.DAMAGE);
        double damageIncrease = RpgCommon.getSkillConfigManager()
                .getUsedDoubleSetting(caster, this, SkillSetting.DAMAGE.scalingNode());
        damage += (damageIncrease * caster.get(Rolecaster.getPrimaryRole()));

        float volume = (float) RpgCommon.getSkillConfigManager()
                .getUsedDoubleSetting(caster, this, CustomSkillSettings.LIGHTNING_VOLUME);
        target.getWorld().strikeLightning(target.getLocation());
        target.getWorld().playSound(target.getLocation(), Sound.AMBIENCE_THUNDER, volume, 1.0F);

        addSkillTarget(entity, caster);
        target.getEntity().ifPresent(entity -> {
            entity.damage(damage, SkillDamageSource.)
        });
        SkillUtils.damageEntity((Insentient) target, caster, this, ImmutableMap.of(DamageType
                                                                                          .MAGICAL,
                                                                                   damage), DamageCause.MAGIC);
        return SkillCastResult.SUCCESS;
    }
}
