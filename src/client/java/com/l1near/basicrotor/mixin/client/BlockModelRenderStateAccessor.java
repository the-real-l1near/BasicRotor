package com.l1near.basicrotor.mixin.client;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BlockModelRenderState.class)
public interface BlockModelRenderStateAccessor {

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Getters
    @Accessor("modelParts")
    @Nullable
    List<BlockStateModelPart> getModelParts();
    @Accessor("specialRenderer")
    @Nullable
    SpecialModelRenderer<?> getSpecialRenderer();
}