package com.l1near.basicrotor.registry;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.BasicRotor;
import com.l1near.basicrotor.block.RotorBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

public final class ModBlocks {

    public static final Block ROTOR = Registry.register(
            BuiltInRegistries.BLOCK,
            Identifier.fromNamespaceAndPath(BasicRotor.MOD_ID, "rotor"),
            new RotorBlock(
                    Block.Properties.of()
                            .setId(ResourceKey.create(
                                    Registries.BLOCK,
                                    Identifier.fromNamespaceAndPath(BasicRotor.MOD_ID, "rotor")
                            ))
                            .strength(3.0F)
                            .noOcclusion()
            )
    );
    private ModBlocks() {
    }

    public static void init() {
    }

}