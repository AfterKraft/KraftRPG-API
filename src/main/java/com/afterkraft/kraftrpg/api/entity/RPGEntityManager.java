package com.afterkraft.kraftrpg.api.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.storage.RPGStorage;

/**
 * Author: gabizou
 */
public abstract class RPGEntityManager {

    protected final RPGPlugin plugin;
    protected final Map<UUID, RPGPlayer> rpgPlayers;
    protected final Map<UUID, RPGMonster> monsters;

    private RPGStorage storage;
    protected final static int WARMUP_INTERVAL = 5;
//    private final Map<RPGPlayer, DelayedSkill> delayedSkills;
    protected final List<RPGPlayer> completedSkills;



    public RPGEntityManager(RPGPlugin plugin) {
        this.plugin = plugin;
        this.rpgPlayers = new HashMap<UUID, RPGPlayer>();
        this.monsters = new ConcurrentHashMap<UUID, RPGMonster>();
        completedSkills = new ArrayList<RPGPlayer>();
        Bukkit.getScheduler().runTaskTimer(plugin, new RPGEntityTask(), 100, 1000);
    }

    public final RPGEntity getRPGEntity(LivingEntity entity) {
        if (entity instanceof Player) {
            return getRPGPlayer((Player) entity);
        } else {
            return getRPGMonster(entity);
        }
    }

    public void shutdown() {}

    public abstract Class<? extends RPGEntity> getEntityClass();

    public abstract Class<? extends RPGMonster> getMonsterClass();

    public abstract Class<? extends RPGPlayer> getPlayerClass();

    public abstract RPGPlayer getRPGPlayer(Player player);

    public abstract RPGMonster getRPGMonster(LivingEntity entity);

    public boolean isRPGMonsterSetup(LivingEntity entity) {
        return monsters.containsKey(entity.getUniqueId());
    }

    protected boolean addRPGPlayer(RPGPlayer player) {
        if (!player.isEntityValid()) {
            return false;
        }
        rpgPlayers.put(player.getPlayer().getUniqueId(), player);
        return true;
    }

    public boolean addRPGMonster(RPGMonster monster) {
        if (!monster.isEntityValid()) {
            return false;
        }
        final UUID id = monster.getEntity().getUniqueId();
        if (monsters.containsKey(id)) {
            return false;
        } else {
            monsters.put(id, monster);
            return true;
        }
    }

    public RPGMonster getRPGPlayer(UUID uuid) {
        return monsters.get(uuid);
    }

    /**
     * A Timer task to remove the potentially GC'ed LivingEntities either due to
     * death, chunk unload, or reload.
     */
    private class RPGEntityTask implements Runnable {

        @Override
        public void run() {
            Map<UUID, RPGPlayer> rpgPlayerMap = RPGEntityManager.this.rpgPlayers;
            Map<UUID, RPGMonster> monsterMap = RPGEntityManager.this.monsters;

            Iterator<Map.Entry<UUID, RPGPlayer>> playerIterator = rpgPlayerMap.entrySet().iterator();
            Iterator<Map.Entry<UUID, RPGMonster>> monsterIterator = monsterMap.entrySet().iterator();

            while (playerIterator.hasNext()) {
                RPGPlayer tempPlayer = playerIterator.next().getValue();
                if (!tempPlayer.isEntityValid()) {
                    playerIterator.remove();
                }
            }

            while (monsterIterator.hasNext()) {
                RPGMonster tempMonster = monsterIterator.next().getValue();
                if (!tempMonster.isEntityValid()) {
                    monsterIterator.remove();
                }
            }

        }
    }
}
