package com.l1near.basicrotor.registry;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.BasicRotor;
import com.l1near.basicrotor.blockentity.RotorBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public final class ModBlockEntities {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final BlockEntityType<RotorBlockEntity> ROTOR =
            Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(BasicRotor.MOD_ID, "rotor"),
                    new BlockEntityType<>(
                            RotorBlockEntity::new,
                            Set.of(ModBlocks.ROTOR)
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

    private ModBlockEntities() {
    }
}