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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;


public class Utilities {

    private static HashSet<Byte> transparentIds;
    private static HashSet<Material> transparentBlocks;
    public static Pattern uuidRegex;
    public static Pattern locationRegex;

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

    public static List<String> matchPlayers(String partial, Player sender) {
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
