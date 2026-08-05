package com.l1near.basicrotor.client;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.client.render.RotorBlockEntityRenderer;
import com.l1near.basicrotor.registry.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class BasicRotorClient implements ClientModInitializer {

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    @Override
    public void onInitializeClient() {

        BlockEntityRenderers.register(
                ModBlockEntities.ROTOR,
                RotorBlockEntityRenderer::new
        );

    }
}