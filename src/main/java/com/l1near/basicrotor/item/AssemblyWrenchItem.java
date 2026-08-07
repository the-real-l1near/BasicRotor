package com.l1near.basicrotor.item;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.manager.LinkSessionManager;
import com.l1near.basicrotor.assembly.session.LinkSession;
import com.l1near.basicrotor.registry.ModBlocks;
import net.minecraft.ChatFormatting;
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

        boolean finishMode =
                player.isShiftKeyDown();

        BlockState blockState =
                level.getBlockState(blockPos);

        if (finishMode) {

            if (blockState.getBlock() != ModBlocks.ROTOR) {

                player.sendSystemMessage(
                        Component.translatable("message.basicrotor.finish_only_rotor")
                                .withStyle(ChatFormatting.RED)
                );

                return InteractionResult.SUCCESS;
            }

            LinkSession session =
                    LINK_SESSION_MANAGER.getSession(player);

            if (session.getSelectedRotorPos() == null) {

                player.sendSystemMessage(
                        Component.translatable("message.basicrotor.no_rotor_selected")
                                .withStyle(ChatFormatting.RED)
                );

                return InteractionResult.SUCCESS;
            }

            if (session.getSelectedBlocks().isEmpty()) {

                player.sendSystemMessage(
                        Component.translatable("message.basicrotor.no_blocks_selected")
                                .withStyle(ChatFormatting.RED)
                );

                return InteractionResult.SUCCESS;
            }

            player.sendSystemMessage(
                    Component.translatable("message.basicrotor.finish_mode")
                            .withStyle(ChatFormatting.GREEN)
            );

            return InteractionResult.SUCCESS;
        }

        if (blockState.getBlock() == ModBlocks.ROTOR) {

            LinkSession session =
                    LINK_SESSION_MANAGER.getSession(player);

            BlockPos currentRotorPos =
                    session.getSelectedRotorPos();

            if (currentRotorPos == null) {

                session.setSelectedRotorPos(blockPos);

                player.sendSystemMessage(
                        Component.translatable("message.basicrotor.rotor_selected")
                                .withStyle(ChatFormatting.GREEN)
                );

                return InteractionResult.SUCCESS;
            }

            if (currentRotorPos.equals(blockPos)) {

                player.sendSystemMessage(
                        Component.translatable("message.basicrotor.rotor_already_selected")
                                .withStyle(ChatFormatting.RED)
                );

                return InteractionResult.SUCCESS;
            }

            session.clear();

            session.setSelectedRotorPos(blockPos);

            player.sendSystemMessage(
                    Component.translatable("message.basicrotor.rotor_changed")
                            .withStyle(ChatFormatting.GREEN)
            );

            return InteractionResult.SUCCESS;
        }
        else {

            LinkSession session =
                    LINK_SESSION_MANAGER.getSession(player);

            if (session.getSelectedRotorPos() == null) {

                player.sendSystemMessage(
                        Component.translatable("message.basicrotor.select_rotor_first")
                                .withStyle(ChatFormatting.RED)
                );

                return InteractionResult.SUCCESS;
            }
            if (session.contains(blockPos)) {

                player.sendSystemMessage(
                        Component.translatable("message.basicrotor.block_already_added")
                                .withStyle(ChatFormatting.RED)
                );

                return InteractionResult.SUCCESS;
            }
            session.addBlock(blockPos);

            String blockName =
                    blockState.getBlock()
                            .getName()
                            .getString();

            player.sendSystemMessage(
                    Component.translatable(
                            "message.basicrotor.block_selected",
                            blockState.getBlock()
                                    .getName()
                                    .copy()
                                    .withStyle(ChatFormatting.GREEN)
                    )
            );
        }


        return InteractionResult.SUCCESS;
    }
}