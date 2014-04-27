package com.afterkraft.kraftrpg.api.entity;

import java.util.UUID;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.Manager;

/**
 * @author gabizou
 */
public interface EntityManager extends Manager {

    public IEntity getEntity(LivingEntity entity);

    public IEntity getEntity(SpellCaster spellCaster);

    public Champion getChampion(Player player);

    public Monster getMonster(LivingEntity entity);

    public boolean isMonsterSetup(LivingEntity entity);

    public boolean addChampion(Champion champion);

    public boolean addMonster(Monster monster);

    public Monster getMonster(UUID uuid);

    public Champion getChampion(UUID uuid);

}
