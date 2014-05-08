/*
 * Copyright 2014 Gabriel Harris-Rouquette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.afterkraft.kraftrpg.api.skills;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.afterkraft.kraftrpg.api.RPGPlugin;
import com.afterkraft.kraftrpg.api.util.Utilities;


/**
 * A skill that requires a {@link org.bukkit.entity.LivingEntity} as a target.
 * Targeted skills do
 */
public abstract class TargetedSkill extends ActiveSkill implements Targeted {

    public TargetedSkill(RPGPlugin plugin, String name) {
        super(plugin, name);
    }
    public static boolean inLineOfSight(LivingEntity a, LivingEntity b) {
        if (a.equals(b)) {
            return true;
        }

        final Location aLoc = a.getEyeLocation();
        final Location bLoc = b.getEyeLocation();
        final int distance = (int) aLoc.distance(bLoc);
        if (distance > 120) {
            return false;
        }
        final Vector vector = new Vector(bLoc.getX() - aLoc.getX(), bLoc.getY() - aLoc.getY(), bLoc.getZ() - aLoc.getZ());
        try {
            final Iterator<Block> iterator = new BlockIterator(a.getWorld(), aLoc.toVector(), vector, 0, distance + 1);
            while (iterator.hasNext()) {
                final Block block = iterator.next();
                final Material type = block.getType();
                if (!Utilities.getTransparentBlocks().contains(type)) {
                    return false;
                }
            }
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public static LivingEntity getPlayerTarget(LivingEntity lentity, int maxDistance) {
        if (lentity.getLocation().getBlockY() > lentity.getLocation().getWorld().getMaxHeight()) {
            return null;
        }
        List<Block> lineOfSight;
        try {
            lineOfSight = lentity.getLineOfSight(Utilities.getTransparentBlockIDs(), maxDistance);
        } catch (final IllegalStateException e) {
            return null;
        }

        final Set<Location> locations = new HashSet<Location>();
        for (final Block block : lineOfSight) {
            locations.add(block.getRelative(BlockFace.UP).getLocation());
            locations.add(block.getLocation());
            locations.add(block.getRelative(BlockFace.DOWN).getLocation());
        }
        final List<Entity> nearbyEntities = lentity.getNearbyEntities(maxDistance, maxDistance, maxDistance);
        for (final Entity entity : nearbyEntities) {
            if ((entity instanceof LivingEntity) && !entity.isDead() && !(((LivingEntity) entity).getHealth() == 0)) {
                if (locations.contains(entity.getLocation().getBlock().getLocation())) {
                    return (LivingEntity) entity;
                }
            }
        }
        return null;
    }

}
