package com.afterkraft.kraftrpg.api.entity.effects;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.entity.Champion;
import com.afterkraft.kraftrpg.api.entity.Monster;
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

    public PeriodicHealingEffect(Spell spell, Champion applier, String name, long period, long duration, double tickHealth, String applyText, String expireText) {
        this(spell, null, applier, name, period, duration, tickHealth, applyText, expireText);
    }

    public PeriodicHealingEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickHealth) {
        this(spell, plugin, applier, name, period, duration, tickHealth, null, null);
    }

    public PeriodicHealingEffect(Spell spell, RPGPlugin plugin, Champion applier, String name, long period, long duration, double tickHealth, String applyText, String expireText) {
        super(spell, plugin, applier, name, period, duration);

        types.add(EffectType.BENEFICIAL);
        types.add(EffectType.HEALING);

        this.tickHealth = tickHealth;
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
    public void tickMonster(Monster monster) {
        if (monster.getEntity() == null || plugin.getEntityManager().getEntity(getApplier().getPlayer()) == null) {
            return;
        }
        Champion applyHero = plugin.getEntityManager().getChampion(getApplier().getPlayer());
        final EntityRegainHealthEvent hrhEvent = new EntityRegainHealthEvent(monster, tickHealth, spell, applyHero);
        plugin.getServer().getPluginManager().callEvent(hrhEvent);
        if (hrhEvent.isCancelled()) {
            return;
        }
        monster.heal(hrhEvent.getAmount());
    }

    @Override
    public void tickChampion(Champion champion) {
        if (champion.getPlayer() == null || plugin.getEntityManager().getChampion(getApplier().getPlayer()) == null) {
            return;
        }
        Champion applyHero = plugin.getEntityManager().getChampion(getApplier().getPlayer());
        final ChampionRegainHealthEvent hrhEvent = new ChampionRegainHealthEvent(champion, tickHealth, spell, applyHero);
        plugin.getServer().getPluginManager().callEvent(hrhEvent);
        if (hrhEvent.isCancelled()) {
            return;
        }
        champion.heal(hrhEvent.getAmount());
    }
}
