package com.afterkraft.kraftrpg.api.entity.effects;

import org.bukkit.event.entity.EntityDamageEvent;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Mage;
import com.afterkraft.kraftrpg.api.entity.SpellCaster;
import com.afterkraft.kraftrpg.api.spells.Spell;
import com.afterkraft.kraftrpg.api.spells.SpellType;

/**
 * @author gabizou
 */
public class PeriodicDamageEffect extends PeriodicExpirableEffect implements Damage {

    private final boolean knockback;
    protected double tickDamage;

    public PeriodicDamageEffect(Spell spell, String name, Champion applier, long period, long duration, double tickDamage) {
        this(spell, name, applier, period, duration, tickDamage, false, null, null);
    }

    public PeriodicDamageEffect(Spell spell, String name, Champion applier, long period, long duration, double tickDamage, boolean knockback, String applyText, String expireText) {
        this(spell, spell.plugin, applier, name, period, duration, tickDamage, knockback, applyText, expireText);
    }

    public PeriodicDamageEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickDamage, boolean knockback, String applyText, String expireText) {
        super(spell, plugin, applier, name, period, duration, applyText, expireText);

        types.add(EffectType.HARMFUL);

        this.tickDamage = tickDamage;
        this.knockback = knockback;
    }

    public PeriodicDamageEffect(Spell spell, String name, Champion applier, long period, long duration, double tickDamage, boolean knockback) {
        this(spell, name, applier, period, duration, tickDamage, knockback, null, null);
    }

    public PeriodicDamageEffect(Spell spell, String name, Champion applier, long period, long duration, double tickDamage, String applyText, String expireText) {
        this(spell, name, applier, period, duration, tickDamage, false, applyText, expireText);
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
    public void tick(Mage mage) {
        if (mage instanceof IEntity && ((IEntity) mage).isEntityValid() && (getApplier() instanceof IEntity) && ((IEntity) getApplier()).isEntityValid()) {
            final IEntity entity = (IEntity) mage;
            final IEntity damager = (IEntity) getApplier();

            if (!spell.damageCheck(damager, entity.getEntity())) {
                return;
            }
            if (mage instanceof SpellCaster) {
                SpellCaster caster = (SpellCaster) mage;
                spell.addSpellTarget(entity.getEntity(), caster);
                spell.damageEntity(entity.getEntity(), damager.getEntity(), tickDamage, spell.isType(SpellType.ABILITY_PROPERTY_PHYSICAL) ? EntityDamageEvent.DamageCause.ENTITY_ATTACK : EntityDamageEvent.DamageCause.MAGIC, knockback);
            }
        }
    }
}
