package com.l1near.basicrotor.registry;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.BasicRotor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;


public final class ModItems {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final Item ROTOR = Registry.register(
            BuiltInRegistries.ITEM,
            Identifier.fromNamespaceAndPath(BasicRotor.MOD_ID, "rotor"),
            new BlockItem(
                    ModBlocks.ROTOR,
                    new Item.Properties()
                            .setId(ResourceKey.create(
                                    Registries.ITEM,
                                    Identifier.fromNamespaceAndPath(BasicRotor.MOD_ID, "rotor")
                            ))
            )
    );

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    public static void init() {
    }

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    private ModItems() {
    }
}