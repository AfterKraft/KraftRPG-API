package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Monster;
import com.afterkraft.kraftrpg.api.spells.Spell;

/**
 * @author gabizou
 */
public class PeriodicDamageEffect extends PeriodicExpirableEffect implements Damage {

    protected double tickDamage;
    private final boolean knockback;

    public PeriodicDamageEffect(Spell spell, String name, Champion applier, long period, long duration, double tickDamage) {
        this(spell, name, applier, period, duration, tickDamage, false, null, null);
    }
    public PeriodicDamageEffect(Spell spell, String name, Champion applier, long period, long duration, double tickDamage, boolean knockback) {
        this(spell, name, applier, period, duration, tickDamage, knockback, null, null);
    }

    public PeriodicDamageEffect(Spell spell, String name, Champion applier, long period, long duration, double tickDamage, String applyText, String expireText) {
        this(spell, name, applier, period, duration, tickDamage, false, applyText, expireText);
    }

    public PeriodicDamageEffect(Spell spell, String name, Champion applier, long period, long duration, double tickDamage, boolean knockback, String applyText, String expireText) {
        this(spell, spell.plugin, applier, name, period, duration, tickDamage, knockback, applyText, expireText);
    }

    public PeriodicDamageEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickDamage) {
        this(spell, plugin, applier, name, period, duration, tickDamage, false, null, null);
    }

    public PeriodicDamageEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickDamage, boolean knockback) {
        this(spell, plugin, applier, name, period, duration, tickDamage, knockback, null, null);
    }

    public PeriodicDamageEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickDamage, String applyText, String expireText) {
        this(spell, plugin, applier, name, period, duration, tickDamage, false, applyText, expireText);
    }

    public PeriodicDamageEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickDamage, boolean knockback, String applyText, String expireText) {
        super(spell, plugin, applier, name, period, duration, applyText, expireText);

        types.add(EffectType.HARMFUL);

        this.tickDamage = tickDamage;
        this.knockback = knockback;
    }

    @Override
    public double getTickDamage() {
        return 0;
    }

    @Override
    public void setTickDamage(double tickDamage) {

    }

    @Override
    public boolean isKnockback() {
        return this.knockback;
    }

    @Override
    public void tickMonster(Monster monster) {

    }

    @Override
    public void tickChampion(Champion player) {

    }
}
