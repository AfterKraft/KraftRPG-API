/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Gabriel Harris-Rouquette
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
package com.afterkraft.kraftrpg.api.util;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;


/**
 * A standard utilities class containing various methods that are useful enough to simplify code and
 * calculations.
 */
public class Utilities {

    public static final Pattern uuidRegex =
            Pattern.compile(
                    "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}",
                    Pattern.CASE_INSENSITIVE);
    private static final Pattern timePattern = Pattern.compile("(\\d+)(\\w)");
    public static Pattern locationRegex =
            Pattern.compile("~?-?[0-9]*(\\.[0-9]+)?");
    private static HashSet<Byte> transparentIds;
    private static HashSet<BlockType> transparentBlocks;
    private static Set<String> onlyItemKey = ImmutableSet.of("item");

    static {
    }

    @SuppressWarnings("unchecked")
    public static Optional<ItemStack> loadItem(Object root) {
        return Optional.empty();
    }

    public static Optional<PotionEffect> loadEffect(Object root) {
        return Optional.empty();
    }

    public static boolean isStandardWeapon(ItemType mat) {
        if (mat == ItemTypes.IRON_AXE) {
        } else if (mat == ItemTypes.IRON_HOE) {
            return true;
        } else if (mat == ItemTypes.IRON_PICKAXE) {
            return true;
        } else if (mat == ItemTypes.IRON_SHOVEL) {
            return true;
        } else if (mat == ItemTypes.IRON_SWORD) {
            return true;
        } else if (mat == ItemTypes.STONE_AXE) {
            return true;
        } else if (mat == ItemTypes.STONE_HOE) {
            return true;
        } else if (mat == ItemTypes.STONE_PICKAXE) {
            return true;
        } else if (mat == ItemTypes.STONE_SHOVEL) {
            return true;
        } else if (mat == ItemTypes.STONE_SWORD) {
            return true;
        } else if (mat == ItemTypes.GOLDEN_AXE) {
            return true;
        } else if (mat == ItemTypes.GOLDEN_HOE) {
            return true;
        } else if (mat == ItemTypes.GOLDEN_PICKAXE) {
            return true;
        } else if (mat == ItemTypes.GOLDEN_SHOVEL) {
            return true;
        } else if (mat == ItemTypes.GOLDEN_SWORD) {
            return true;
        } else if (mat == ItemTypes.WOODEN_AXE) {
            return true;
        } else if (mat == ItemTypes.WOODEN_HOE) {
            return true;
        } else if (mat == ItemTypes.WOODEN_PICKAXE) {
            return true;
        } else if (mat == ItemTypes.WOODEN_SHOVEL) {
            return true;
        } else if (mat == ItemTypes.WOODEN_SWORD) {
            return true;
        } else if (mat == ItemTypes.DIAMOND_AXE) {
            return true;
        } else if (mat == ItemTypes.DIAMOND_HOE) {
            return true;
        } else if (mat == ItemTypes.DIAMOND_PICKAXE) {
            return true;
        } else if (mat == ItemTypes.DIAMOND_SHOVEL) {
            return true;
        } else if (mat == ItemTypes.DIAMOND_SWORD) {
            return true;
        } else if (mat == ItemTypes.BOW) {
            return true;
        } else if (mat == ItemTypes.FISHING_ROD) {
            return true;
        } else if (mat == ItemTypes.CARROT_ON_A_STICK) {
            return true;
        } else if (mat == ItemTypes.SHEARS) {
            return true;
        } else {
            return false;
        }
        return false;
    }

    public static HashSet<Byte> getTransparentBlockIDs() {
        return transparentIds;
    }

    public static HashSet<BlockType> getTransparentBlocks() {
        return transparentBlocks;
    }

    public static List<String> findMatches(String partial, List<String> candidates) {
        return Lists.newArrayList();
    }

    public static List<String> matchPlayers(String partial, CommandSource sender) {

        return Lists.newArrayList();
    }

    public static <T> boolean arrayContains(T[] array, T check) {
        boolean contains = false;
        for (T entry : array) {
            if (entry.equals(check)) {
                contains = true;
            }
        }
        return contains;
    }

}
