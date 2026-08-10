package com.l1near.basicrotor.registry;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.BasicRotor;
import com.l1near.basicrotor.item.AssemblyWrenchItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;


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

    public static final Item ASSEMBLY_WRENCH = Registry.register(
            BuiltInRegistries.ITEM,
            Identifier.fromNamespaceAndPath(
                    BasicRotor.MOD_ID,
                    "assembly_wrench"
            ),
            new AssemblyWrenchItem(
                    new Item.Properties()
                            .setId(
                                    ResourceKey.create(
                                            Registries.ITEM,
                                            Identifier.fromNamespaceAndPath(
                                                    BasicRotor.MOD_ID,
                                                    "assembly_wrench"
                                            )
                                    )
                            )
            )
    );
    /*
    ---------------------------
    Methods
    ---------------------------
    */

    public static void init() {

        CreativeModeTabEvents
                .modifyOutputEvent(
                        CreativeModeTabs.REDSTONE_BLOCKS
                )
                .register(
                        output -> {

                            output.accept(
                                    ROTOR
                            );

                            output.accept(
                                    ASSEMBLY_WRENCH
                            );
                        }
                );
    }

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    private ModItems() {
    }
}