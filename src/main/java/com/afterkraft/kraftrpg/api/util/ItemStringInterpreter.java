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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

import org.bukkit.CoalType;
import org.bukkit.DyeColor;
import org.bukkit.GrassSpecies;
import org.bukkit.Material;
import org.bukkit.SandstoneType;
import org.bukkit.SkullType;
import org.bukkit.TreeSpecies;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Coal;
import org.bukkit.material.Leaves;
import org.bukkit.material.LongGrass;
import org.bukkit.material.MaterialData;
import org.bukkit.material.MonsterEggs;
import org.bukkit.material.Sandstone;
import org.bukkit.material.SpawnEgg;
import org.bukkit.material.TexturedMaterial;
import org.bukkit.material.Tree;
import org.bukkit.material.WoodenStep;
import org.bukkit.material.Wool;

import net.milkbowl.vault.item.ItemInfo;
import net.milkbowl.vault.item.Items;

enum WallTypeEnum {
    COBBLESTONE,
    MOSSY_COBBLESTONE,
}

enum AnvilDamageEnum {
    UNDAMAGED,
    SLIGHTLY_DAMAGED,
    VERY_DAMAGED,
}

enum QuartzTypeEnum {
    NORMAL,
    CHISELED,
    PILLAR,
}

// From Step.class - missing new values
enum StepTypeEnum {
    STONE,
    SANDSTONE,
    WOOD,
    COBBLESTONE,
    BRICK,
    SMOOTH_BRICK,
    NETHER_BRICK,
    QUARTZ,
    SEAMLESS_STONE,
    SEAMLESS_SANDSTONE,
    ALTERNATE_WOOD,
    ALTERNATE_COBBLESTONE,
    ALTERNATE_BRICK,
    ALTERNATE_SMOOTH_BRICK,
    ALTERNATE_NETHER_BRICK,
    SEAMLESS_QUARTZ,
}

// From SmoothBrick.class - dumb names
enum StoneBrickType {
    NORMAL,
    CRACKED,
    MOSSY,
    CIRCLE,
}

interface StringInterpreter {
    /**
     * Interpret a data string into a Byte suitable for MaterialData.  The provided string must
     * first be made all uppercase before calling.
     *
     * @param s provided string
     *
     * @return byte of data
     */
    public byte interpret(String s);
}

/**
 * Interpreter of ItemStacks to Strings and Strings to ItemStacks. This is mainly used for
 * serialization purposes.
 */
@SuppressWarnings("rawtypes")
public final class ItemStringInterpreter {
    private static Map<Class<? extends MaterialData>, Class<? extends Enum>> materialData =
            new HashMap<Class<? extends MaterialData>, Class<? extends Enum>>();
    private static Map<Material, StringInterpreter> workarounds =
            new HashMap<Material, StringInterpreter>();

    private ItemStringInterpreter() {
        @SuppressWarnings("unused")
        String foo = "This file is licensed under the GNU LGPL, version 3 or any later version.";
    }

    static {
        materialData.put(Coal.class, CoalType.class);
        materialData.put(Leaves.class, TreeSpecies.class);
        materialData.put(LongGrass.class, GrassSpecies.class);
        materialData.put(Sandstone.class, SandstoneType.class);
        materialData.put(SpawnEgg.class, EntityType.class);
        materialData.put(Tree.class, TreeSpecies.class);
        materialData.put(WoodenStep.class, TreeSpecies.class);

        // Note that this is Silverfish blocks, not spawn eggs
        workarounds.put(Material.MONSTER_EGGS, new TexturedMaterialInterpreter(new MonsterEggs()));

        // Workaround: The texture names are pretty damn dumb here
        //workarounds.put(Material.SMOOTH_BRICK,
        //      new TexturedMaterialInterpreter(new SmoothBrick()));
        workarounds.put(Material.SMOOTH_BRICK,
                new EnumOrdinalMaterialInterpreter(StoneBrickType.values()));

        // Workaround: Step does not have the new materials in its texture list
        //workarounds.put(Material.STEP, new TexturedMaterialInterpreter(new Step()));
        workarounds.put(Material.STEP, new EnumOrdinalMaterialInterpreter(StepTypeEnum.values()));
        workarounds.put(Material.DOUBLE_STEP,
                new EnumOrdinalMaterialInterpreter(StepTypeEnum.values()));

        // Workaround: No MaterialData class for SKULL_ITEM
        workarounds.put(Material.SKULL_ITEM,
                new EnumOrdinalMaterialInterpreter(SkullType.values()));
        // Workaround: No MaterialData class for COBBLE_WALL
        workarounds.put(Material.COBBLE_WALL,
                new EnumOrdinalMaterialInterpreter(WallTypeEnum.values()));
        // Workaround: No MaterialData class for ANVIL
        workarounds.put(Material.ANVIL,
                new EnumOrdinalMaterialInterpreter(AnvilDamageEnum.values()));
        // Workaround: No MaterialData class for QUARTZ_BLOCK
        workarounds.put(Material.QUARTZ_BLOCK,
                new EnumOrdinalMaterialInterpreter(QuartzTypeEnum.values()));

        materialData.put(Wool.class, DyeColor.class);

        // Workaround: Dye.class does not accept DyeColor in constructor
        workarounds.put(Material.INK_SACK, new StringInterpreter() {
            @SuppressWarnings("deprecation")
            @Override
            public byte interpret(String s) {
                return DyeColor.valueOf(s).getDyeData();
            }
        });
        // These both should use Wool.class but don't
        StringInterpreter woolHandler = new StringInterpreter() {
            @SuppressWarnings("deprecation")
            @Override
            public byte interpret(String s) {
                return DyeColor.valueOf(s).getWoolData();
            }
        };
        workarounds.put(Material.STAINED_CLAY, woolHandler);
        workarounds.put(Material.STAINED_GLASS_PANE, woolHandler);
        workarounds.put(Material.STAINED_GLASS, woolHandler);
        workarounds.put(Material.CARPET, woolHandler);
    }

    @SuppressWarnings("deprecation")
    public static ItemStack valueOf(String itemString) throws IllegalArgumentException {
        itemString = itemString.toUpperCase();

        // Try Vault first
        ItemInfo info = Items.itemByString(itemString);
        if (info != null) {
            return info.toStack();
        }

        String[] split = itemString.split(":");
        checkArgument(split.length <= 2,
                "Unable to parse item string - too many colons (maximum 1). "
                        + "Please correct the format and reload the config. Input: "
                        + itemString);
        Material mat = getMaterial(split[0]);
        if (mat == null) {
            return null;
        }

        if (split.length == 1) {
            return new ItemStack(mat);
        }
        String dataString = split[1];
        short data;
        try {
            data = Short.parseShort(dataString);
            return new ItemStack(mat, 1, data);
        } catch (NumberFormatException ignored) {
            // do nothing
        }

        if (materialData.containsKey(mat.getData())) {
            Class<? extends MaterialData> matClass = mat.getData();
            Class<? extends Enum> enumClass = materialData.get(mat.getData());
            Enum enumValue;
            try {
                enumValue = (Enum) enumClass.getMethod("valueOf", String.class)
                        .invoke(null, dataString);
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException("Unable to parse item string - "
                        + dataString + " is not a valid member of "
                        + enumClass.getSimpleName() + ". Input: " + itemString, e.getCause());
            } catch (Exception rethrow) {
                throw new RuntimeException("Unexpected exception when parsing item string. Input: "
                        + itemString, rethrow);
            }
            MaterialData matData;
            try {
                matData = matClass.getConstructor(enumClass).newInstance(enumValue);
            } catch (Exception rethrow) {
                throw new RuntimeException("Unexpected exception when parsing item string. Input: "
                        + itemString, rethrow);
            }
            return new ItemStack(mat, 1, (short) matData.getData());
        }
        if (workarounds.containsKey(mat)) {
            StringInterpreter interpreter = workarounds.get(mat);
            return new ItemStack(mat, 1, interpreter.interpret(dataString));
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private static Material getMaterial(String s) {
        Material m = null;
        try {
            m = Material.matchMaterial(s);
        } catch (Exception ignored) {
            // do nothing
        }
        if (m != null) {
            return m;
        }
        try {
            m = Material.getMaterial(Integer.parseInt(s));
        } catch (Exception ignored) {
            // do nothing
        }
        if (m != null) {
            return m;
        }
        return null;
    }
}

class TexturedMaterialInterpreter implements StringInterpreter {
    private TexturedMaterial referenceInstance;

    public TexturedMaterialInterpreter(TexturedMaterial instance) {
        this.referenceInstance = instance;
    }

    @SuppressWarnings("deprecation")
    @Override
    public byte interpret(String s) {
        this.referenceInstance.setMaterial(Material.valueOf(s));
        return this.referenceInstance.getData();
    }
}

class EnumOrdinalMaterialInterpreter implements StringInterpreter {
    private Enum<?>[] values;

    public EnumOrdinalMaterialInterpreter(Enum<?>[] values) {
        this.values = values;
    }

    @Override
    public byte interpret(String s) {
        for (Enum<?> e : this.values) {
            if (s.equalsIgnoreCase(e.toString())) {
                return (byte) e.ordinal();
            }
        }
        return 0;
    }
}
