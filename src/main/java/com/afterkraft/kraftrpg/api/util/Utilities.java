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
package com.afterkraft.kraftrpg.api.util;

import java.util.*;
import java.util.regex.Pattern;

import net.milkbowl.vault.item.ItemInfo;
import net.milkbowl.vault.item.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;


public class Utilities {

    public static Pattern uuidRegex;
    public static Pattern locationRegex;

    private static HashSet<Byte> transparentIds;
    private static HashSet<Material> transparentBlocks;

    private static Map<String, Enchantment> enchantmentMap;

    static {
        // Use Bukkit's Material#isTransParent()
        transparentBlocks = new HashSet<Material>();
        for (Material mat : Material.values()) {
            if (mat.isTransparent()) {
                transparentBlocks.add(mat);
            }
        }

        // Use Bukkit's Material#isTransParent()
        transparentIds = new HashSet<Byte>();
        for (Material mat : Material.values()) {
            if (mat.isTransparent()) {
                transparentIds.add((byte) mat.getId());
            }
        }

        uuidRegex = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", Pattern.CASE_INSENSITIVE);
        locationRegex = Pattern.compile("~?-?[0-9]*(\\.[0-9]+)?");

        for (Enchantment ench : Enchantment.values()) {
            enchantmentMap.put(ench.toString().toUpperCase(), ench);
        }
    }

    public static ItemStack loadItem(ConfigurationSection section) {
        ItemStack item = ItemStringInterpreter.valueOf(section.getString("item"));
        ItemMeta meta = item.getItemMeta();

        if (section.get("name") != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', section.getString("name")));
        }

        if (section.get("lore") != null) {
            List<String> lore = new ArrayList<String>();
            for (String str : section.getStringList("lore")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', str));
            }
            meta.setLore(lore);
        }

        if (section.getConfigurationSection("enchantments") != null) {
            ConfigurationSection enchSection = section.getConfigurationSection("enchantments");
            if (item.getType() == Material.ENCHANTED_BOOK) {
                EnchantmentStorageMeta esMeta = (EnchantmentStorageMeta) meta;
                for (String enchantment : enchSection.getKeys(false)) {
                    Enchantment ench = enchantmentMap.get(enchantment.toUpperCase());
                    esMeta.addStoredEnchant(ench, enchSection.getInt(enchantment), true);
                }
            } else {
                for (String enchantment : enchSection.getKeys(false)) {
                    Enchantment ench = enchantmentMap.get(enchantment.toUpperCase());
                    meta.addEnchant(ench, enchSection.getInt(enchantment), true);
                }
            }
        }

        if (item.getType() == Material.WRITTEN_BOOK) {
            if (section.get("pages") != null) {
                List<String> pages = new ArrayList<String>();
                for (String page : section.getStringList("pages")) {
                    pages.add(ChatColor.translateAlternateColorCodes('&', page));
                }
                ((BookMeta) meta).setPages(pages);
            }
        }



        return null;
    }

    public static HashSet<Byte> getTransparentBlockIDs() {
        return transparentIds;
    }

    public static HashSet<Material> getTransparentBlocks() {
        return transparentBlocks;
    }

    public static List<String> findMatches(String partial, List<String> candidates) {
        if (partial == null || partial.isEmpty()) return candidates;

        List<String> ret = new ArrayList<String>();
        // This is a Bukkit method!
        StringUtil.copyPartialMatches(partial, candidates, ret);
        return ret;
    }

    public static List<String> matchPlayers(String partial, CommandSender sender) {
        Player senderPlayer = sender instanceof Player ? (Player) sender : null;

        ArrayList<String> matchedPlayers = new ArrayList<String>();
        for (Player player : sender.getServer().getOnlinePlayers()) {
            String name = player.getName();
            if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, partial)) {
                matchedPlayers.add(name);
            }
        }

        Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }
}
