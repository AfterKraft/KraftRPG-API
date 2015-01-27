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
package com.afterkraft.kraftrpg.common.handler;

import java.util.Map;

import org.spongepowered.api.entity.living.Living;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Insentient;
import com.afterkraft.kraftrpg.api.skills.ISkill;
import com.afterkraft.kraftrpg.common.DamageCause;
import com.afterkraft.kraftrpg.common.DamageType;

/**
 * Standard utility class for handling version and platform specific code.
 */
public abstract class ServerInternals {

    private static ServerInternals activeInterface;
    protected RPGPlugin plugin;

    public static ServerInternals getInterface() {
        // TODO need to redo this
        return activeInterface;
    }


    public abstract boolean damageCheck(Insentient attacker, IEntity victim);

    public abstract void knockBack(Insentient target, Insentient attacker, double damage);

    public abstract void knockBack(Living target, Living attacker, double damage);

    public abstract boolean healEntity(Insentient being, double tickHealth, ISkill skill,
                                       Insentient applier);

    public abstract boolean damageEntity(Living target, Insentient attacker,
                                         ISkill skill,
                                         double damage, DamageCause cause, boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                         double damage, DamageCause cause, boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                         Map<DamageType, Double> modifiers, DamageCause cause,
                                         boolean knockback);

    public abstract boolean damageEntity(Insentient target, Insentient attacker, ISkill skill,
                                         Map<DamageType, Double> modifiers, DamageCause cause,
                                         boolean knockback, boolean ignoreDamageCheck);


    //NMS methods required by effects
    public abstract void hideInsentient(Insentient player);

    //Bukkit specific NMS Requirements to fulfill deficiencies in API
    public abstract void setProjectileDamage(IEntity arrow, double damage);

}