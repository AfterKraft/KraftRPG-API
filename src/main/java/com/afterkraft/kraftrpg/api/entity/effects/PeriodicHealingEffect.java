package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.IEntity;
import com.afterkraft.kraftrpg.api.entity.Mage;
import com.afterkraft.kraftrpg.api.events.entity.EntityRegainHealthEvent;
import com.afterkraft.kraftrpg.api.events.entity.champion.ChampionRegainHealthEvent;
import com.afterkraft.kraftrpg.api.spells.Spell;

/**
 * @author gabizou
 */
public class PeriodicHealingEffect extends PeriodicExpirableEffect implements Heal {
    private double tickHealth;

    public PeriodicHealingEffect(Spell spell, Champion applier, String name, long period, long duration, double tickHealth) {
        this(spell, null, applier, name, period, duration, tickHealth, null, null);
    }

    public PeriodicHealingEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickHealth, String applyText, String expireText) {
        super(spell, plugin, applier, name, period, duration);

        types.add(EffectType.BENEFICIAL);
        types.add(EffectType.HEALING);

        this.tickHealth = tickHealth;
    }

    public PeriodicHealingEffect(Spell spell, Champion applier, String name, long period, long duration, double tickHealth, String applyText, String expireText) {
        this(spell, null, applier, name, period, duration, tickHealth, applyText, expireText);
    }

    public PeriodicHealingEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickHealth) {
        this(spell, plugin, applier, name, period, duration, tickHealth, null, null);
    }

    @Override
    public double getTickHealth() {
        return tickHealth;
    }

    @Override
    public void setTickHealth(double tickHealth) {
        this.tickHealth = tickHealth;
    }

    @Override
    public void tick(Mage mage) {
        if ((mage instanceof IEntity) && (getApplier() instanceof IEntity)) {
            IEntity entity = (IEntity) mage;
            IEntity healer = (IEntity) getApplier();
            if (!entity.isEntityValid() || !healer.isEntityValid()) {
                return;
            }
            EntityRegainHealthEvent event;
            if (entity instanceof Champion) {
                event = new ChampionRegainHealthEvent((Champion) entity, tickHealth, spell, healer);

            } else {
                event = new EntityRegainHealthEvent(entity, tickHealth, spell, healer);
            }
            plugin.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            entity.heal(event.getAmount());
        }
    }
}
