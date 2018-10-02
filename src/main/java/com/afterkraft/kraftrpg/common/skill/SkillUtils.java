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
package com.afterkraft.kraftrpg.common.skill;

import java.util.function.Function;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.entity.damage.DamageModifier;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.ImmutableList;

import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Insentient;

public final class SkillUtils {

    private SkillUtils() {
    }


    /**
     * Transform the name of a skill to a normal form. The results of this method should not be
     * compared with anything other than other results of this method.
     *
     * @param skillName skill.getName() to check
     * @return normalized name
     */
    public static String getNormalizedName(String skillName) {
        return skillName.toLowerCase().replace("skill", "");
    }

    public static boolean damageCheck(Insentient attacking, Insentient victim) {
        return damageCheck(attacking, victim.getEntity().get());
    }

    /**
     * Attempts to damage the defending LivingEntity, this allows for various protection plugins to
     * cancel damage events.
     *
     * @param attacking  attempting to deal the damage
     * @param defenderLE entity being damaged
     * @return true if the damage check was successful
     */
    public static boolean damageCheck(Insentient attacking, Living defenderLE) {
        if (attacking.getEntity().get().equals(defenderLE)) {
            return false;
        }
        if (defenderLE instanceof Player && attacking instanceof Champion) {
            if (!attacking.getWorld().getProperties().isPVPEnabled()) {
                ((Champion) attacking).sendMessage(Text.of(TextColors.RED, "PVP is disabled!"));
                return false;
            }
        }
////        //DamageEntityEvent event = SpongeEventFactory.createDamageEntityEvent(EventContext.builder().add(DamageSource.builder().type(DamageTypes.MAGIC)).build());
////        Sponge.getEventManager().post(event);
////        return event.isCancelled();
//    }
        return false;
    }
}