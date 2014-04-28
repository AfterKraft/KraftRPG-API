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

import java.util.HashSet;

import org.bukkit.Material;


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
