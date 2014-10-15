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
package com.afterkraft.kraftrpg.api.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableSet;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.StringUtil;

import com.afterkraft.kraftrpg.api.handler.ServerInternals;


public class Utilities {

    public static final Pattern uuidRegex = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", Pattern.CASE_INSENSITIVE);
    private static final Pattern timePattern = Pattern.compile("(\\d+)(\\w)");
    public static Pattern locationRegex = Pattern.compile("~?-?[0-9]*(\\.[0-9]+)?");
    private static HashSet<Byte> transparentIds;
    private static HashSet<Material> transparentBlocks;
    private static Set<String> onlyItemKey = ImmutableSet.of("item");

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

    }

    @SuppressWarnings("unchecked")
    public static ItemStack loadItem(Object root) {
        ConfigurationSection section;
        if (root == null) return null;
        if (root instanceof ItemStack) return (ItemStack) root;
        if (root instanceof ConfigurationSection) {
            section = (ConfigurationSection) root;
        } else if (root instanceof Map) {
            MemoryConfiguration _section = new MemoryConfiguration();
            _section.addDefaults((Map<String, Object>) root);
            section = _section;
        } else {
            return null;
        }

        ItemStack item = ItemStringInterpreter.valueOf(section.getString("item"));
        if (item == null) return null;

        // No addtl data? Return now
        if (section.getKeys(true).equals(onlyItemKey)) {
            return item;
        }

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
            ConfigurationSection enchantSection = section.getConfigurationSection("enchantments");
            if (meta instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta esMeta = (EnchantmentStorageMeta) meta;
                for (String enchantStr : enchantSection.getKeys(false)) {
                    Enchantment enchant = Enchantment.getByName(enchantStr);
                    esMeta.addStoredEnchant(enchant, enchantSection.getInt(enchantStr), true);
                }
            } else {
                for (String enchantStr : enchantSection.getKeys(false)) {
                    Enchantment enchant = Enchantment.getByName(enchantStr);
                    meta.addEnchant(enchant, enchantSection.getInt(enchantStr), true);
                }
            }
        }

        if (meta instanceof BookMeta) {
            if (section.get("pages") != null) {
                List<String> pages = new ArrayList<String>();
                for (String page : section.getStringList("pages")) {
                    pages.add(ChatColor.translateAlternateColorCodes('&', page));
                }
                ((BookMeta) meta).setPages(pages);
            }
        }

        if (meta instanceof LeatherArmorMeta) {
            Object obj = section.get("color");
            if (obj instanceof String) {
                ((LeatherArmorMeta) meta).setColor(parseColor((String) obj));
            } else if (obj instanceof Color) {
                ((LeatherArmorMeta) meta).setColor((Color) obj);
            }
        }

        if (meta instanceof PotionMeta) {
            List<?> effects = section.getList("effects");
            if (effects != null) {
                for (Object obj : effects) {
                    PotionEffect effect = loadEffect(obj);
                    if (effect != null) {
                        ((PotionMeta) meta).addCustomEffect(effect, true);
                    }
                }
            }
        }

        if (meta instanceof SkullMeta) {
            String uuid = section.getString("uuid");
            if (uuid != null) {
                try {
                    UUID u = UUID.fromString(uuid);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(u);
                    // FIXME
                    ((SkullMeta) meta).setOwner(offlinePlayer.getName());
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    public static Color parseColor(String str) {
        String[] split = str.split(":");
        int r, g, b;
        try {
            r = Integer.parseInt(split[0]);
            g = Integer.parseInt(split[1]);
            b = Integer.parseInt(split[2]);
            return Color.fromRGB(r, g, b);
        } catch (NumberFormatException e) {
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static PotionEffect loadEffect(Object root) {
        if (root instanceof PotionEffect) return (PotionEffect) root;
        if (root instanceof ConfigurationSection) {
            ConfigurationSection section = (ConfigurationSection) root;

            PotionEffectType type = PotionEffectType.getByName(section.getString("type"));
            if (type == null) {
                type = ServerInternals.getInterface().getAlternatePotionEffectNames().get(section.getString("type").toLowerCase());
            }
            if (type == null) return null;

            int strength = section.getInt("level", 0);

            int ticks = section.getInt("ticks", -1);
            if (ticks != -1) {
                return new PotionEffect(type, ticks, strength);
            }

            String time = section.getString("time");
            if (time == null) return null;

            Matcher matcher = timePattern.matcher(time);
            if (!matcher.matches()) return null;

            int quant = Integer.parseInt(matcher.group(1));
            char unit = matcher.group(2).charAt(0);
            switch (unit) {
                // fallthrough
                case 'd':
                    quant *= 24;
                case 'h':
                    quant *= 60;
                case 'm':
                    quant *= 60;
                case 's':
                    quant *= 20;
                case 't':
                    break;
                default:
                    return null;
            }
            return new PotionEffect(type, quant, strength);
        }
        return null;
    }

    public static boolean isStandardWeapon(Material mat) {
        switch (mat) {
            case IRON_AXE:
            case IRON_HOE:
            case IRON_PICKAXE:
            case IRON_SPADE:
            case IRON_SWORD:
            case STONE_AXE:
            case STONE_HOE:
            case STONE_PICKAXE:
            case STONE_SPADE:
            case STONE_SWORD:
            case GOLD_AXE:
            case GOLD_HOE:
            case GOLD_PICKAXE:
            case GOLD_SPADE:
            case GOLD_SWORD:
            case WOOD_AXE:
            case WOOD_HOE:
            case WOOD_PICKAXE:
            case WOOD_SPADE:
            case WOOD_SWORD:
            case DIAMOND_AXE:
            case DIAMOND_HOE:
            case DIAMOND_PICKAXE:
            case DIAMOND_SPADE:
            case DIAMOND_SWORD:
            case BOW:
            case FISHING_ROD:
            case CARROT_STICK:
            case SHEARS:
                return true;
            default:
                return false;
        }
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

    public static String minMaxString(double at0, double max, ChatColor color) {
        return String.format("%3$s%1$.1f%4$s-%3$s%2$.1f%5$s", at0, max, color.toString(), ChatColor.WHITE.toString(), ChatColor.RESET.toString());
    }
}
