package com.afterkraft.kraftrpg.api.util;

import java.util.HashSet;

import org.bukkit.Material;

/**
 * @author gabizou
 */
public class Utilities {

    private static HashSet<Byte> transparentIds;
    private static HashSet<Material> transparentBlocks;

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

    public static HashSet<Byte> getTransparentBlockIDs() {
        return transparentIds;
    }

    public static HashSet<Material> getTransparentBlocks() {
        return transparentBlocks;
    }
}
