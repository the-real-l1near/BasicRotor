package com.l1near.basicrotor.item;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.manager.LinkSessionManager;
import com.l1near.basicrotor.assembly.session.LinkSession;
import com.l1near.basicrotor.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AssemblyWrenchItem extends Item {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private static final LinkSessionManager LINK_SESSION_MANAGER =
            new LinkSessionManager();

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
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        } if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        BlockState blockState =
                level.getBlockState(blockPos);
        if (blockState.getBlock() == ModBlocks.ROTOR) {
            player.sendSystemMessage(
                    Component.literal("Rotor selected.")
            );
        }
        else {
            player.sendSystemMessage(
                    Component.literal("Not a rotor.")
            );
        }

        LinkSession session =
                LINK_SESSION_MANAGER.getSession(player);

        session.setSelectedRotorPos(blockPos);


        return InteractionResult.SUCCESS;
    }
}