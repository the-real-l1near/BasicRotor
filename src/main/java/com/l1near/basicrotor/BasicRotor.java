package com.l1near.basicrotor;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.registry.ModBlockEntities;
import com.l1near.basicrotor.registry.ModBlocks;
import com.l1near.basicrotor.registry.ModItems;
import net.fabricmc.api.ModInitializer;

public class BasicRotor implements ModInitializer {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final String MOD_ID = "basicrotor";

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
    }
}