package com.l1near.basicrotor.client.data;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record ClientRotorKey(
        ResourceKey<Level> dimension,
        BlockPos rotorPos
) {

}