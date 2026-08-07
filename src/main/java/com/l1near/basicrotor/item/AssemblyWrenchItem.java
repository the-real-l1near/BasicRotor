package com.l1near.basicrotor.item;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class AssemblyWrenchItem extends Item {

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public AssemblyWrenchItem(Properties properties) {
        super(properties);
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            player.sendSystemMessage(
                    Component.literal("Assembly Wrench used.")
            );
        }
        return super.use(level, player, hand);
    }
}