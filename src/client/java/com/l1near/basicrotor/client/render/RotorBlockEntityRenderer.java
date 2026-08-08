package com.l1near.basicrotor.client.render;

/*
---------------------------
Imports
---------------------------
*/
import com.l1near.basicrotor.block.RotorBlock;
import com.l1near.basicrotor.client.movement.ClientRotorMovementData;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.l1near.basicrotor.blockentity.RotorBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;
import com.l1near.basicrotor.client.BasicRotorClient;

public class RotorBlockEntityRenderer implements BlockEntityRenderer<RotorBlockEntity, RotorBlockEntityRenderState> {

    /*
    ---------------------------
               Fields
    ---------------------------
    */
    private final BlockModelResolver blockModelResolver;

    /*
    ---------------------------
               Constructors
    ---------------------------
    */

    public RotorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();}



    /*
    ---------------------------
    Methods
    ---------------------------
    */

    @Override
    public RotorBlockEntityRenderState createRenderState() {
        return new RotorBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(
            RotorBlockEntity blockEntity,
            RotorBlockEntityRenderState state,
            float partialTicks,
            Vec3 cameraPosition,
            ModelFeatureRenderer.CrumblingOverlay breakProgress
    ) {
        BlockEntityRenderer.super.extractRenderState(
                blockEntity,
                state,
                partialTicks,
                cameraPosition,
                breakProgress
        );
        state.lightCoords = LightCoordsUtil.getLightCoords(
                blockEntity.getLevel(),
                blockEntity.getBlockPos()
        );

        state.facing = blockEntity
                .getBlockState()
                .getValue(RotorBlock.FACING);

        state.virtualAssembly = null;

        if (blockEntity.getLevel() != null) {

            state.virtualAssembly =
                    BasicRotorClient
                            .getAssemblyManager()
                            .get(
                                    blockEntity.getLevel().dimension(),
                                    blockEntity.getBlockPos()
                            );
        }

        ClientRotorMovementData rotorMovement = null;

        if (blockEntity.getLevel() != null) {

            rotorMovement =
                    BasicRotorClient
                            .getRotorMovementManager()
                            .get(
                                    blockEntity.getLevel().dimension(),
                                    blockEntity.getBlockPos()
                            );
        }

        if (rotorMovement != null) {

            state.rotation =
                    interpolateRotation(
                            rotorMovement.getPreviousRenderRotation(),
                            rotorMovement.getRenderRotation(),
                            partialTicks
                    );
        }
        else {

            state.rotation =
                    blockEntity.getRotation(
                            partialTicks
                    );
        }

        state.virtualBlocks.clear();



        if (state.virtualAssembly != null) {

            for (var entry
                    : state.virtualAssembly.getLinkedBlocks().entrySet()) {

                VirtualBlockRenderState virtualBlock =
                        new VirtualBlockRenderState(
                                entry.getKey()
                        );

                blockModelResolver.update(
                        virtualBlock.modelState,
                        entry.getValue().getBlockState(),
                        BlockDisplayContext.create()
                );

                virtualBlock.modelState.blockLightCoords =
                        state.lightCoords;

                state.virtualBlocks.add(
                        virtualBlock
                );
            }
        }

        blockModelResolver.update(
                state.modelState,
                blockEntity.getBlockState(),
                BlockDisplayContext.create()
        );
        state.modelState.blockLightCoords = state.lightCoords;
    }
    //Submit
    @Override
    public void submit(
            RotorBlockEntityRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera
    ) {
        poseStack.pushPose();

        poseStack.translate(0.5, 0.5, 0.5);

        applySpinRotation(
                state.facing,
                state.rotation,
                poseStack
        );

        applyFacingRotation(
                state.facing,
                poseStack
        );

        poseStack.translate(-0.5, -0.5, -0.5);

        state.modelState.submit(
                poseStack,
                submitNodeCollector,
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                -1
        );
        poseStack.popPose();

        if (state.virtualAssembly != null
                && state.virtualAssembly.isVirtualized()) {

            poseStack.pushPose();

            poseStack.translate(
                    0.5,
                    0.5,
                    0.5
            );

            applySpinRotation(
                    state.facing,
                    state.rotation,
                    poseStack
            );

            poseStack.translate(
                    -0.5,
                    -0.5,
                    -0.5
            );

            for (VirtualBlockRenderState virtualBlock
                    : state.virtualBlocks) {

                poseStack.pushPose();

                poseStack.translate(
                        virtualBlock.relativePos.getX(),
                        virtualBlock.relativePos.getY(),
                        virtualBlock.relativePos.getZ()
                );

                virtualBlock.modelState.submit(
                        poseStack,
                        submitNodeCollector,
                        state.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        -1
                );

                poseStack.popPose();
            }

            poseStack.popPose();
        }
    }
    private void applyFacingRotation(
            Direction facing,
            PoseStack poseStack
    ) {
        switch (facing) {

            case SOUTH ->
                    poseStack.mulPose(
                            Axis.YP.rotationDegrees(180)
                    );

            case EAST ->
                    poseStack.mulPose(
                            Axis.YP.rotationDegrees(90)
                    );

            case WEST ->
                    poseStack.mulPose(
                            Axis.YP.rotationDegrees(-90)
                    );

            case UP ->
                    poseStack.mulPose(
                            Axis.XP.rotationDegrees(-90)
                    );

            case DOWN ->
                    poseStack.mulPose(
                            Axis.XP.rotationDegrees(90)
                    );

            case NORTH -> {
                // default
            }
        }
    }
    private void applySpinRotation(
            Direction facing,
            float rotation,
            PoseStack poseStack
    ) {
        switch (facing) {

            case NORTH ->
                    poseStack.mulPose(
                            Axis.ZP.rotationDegrees(-rotation)
                    );

            case SOUTH ->
                    poseStack.mulPose(
                            Axis.ZP.rotationDegrees(rotation)
                    );

            case WEST ->
                    poseStack.mulPose(
                            Axis.XP.rotationDegrees(-rotation)
                    );

            case EAST ->
                    poseStack.mulPose(
                            Axis.XP.rotationDegrees(rotation)
                    );

            case UP ->
                    poseStack.mulPose(
                            Axis.YP.rotationDegrees(rotation)
                    );


            case DOWN ->
                    poseStack.mulPose(
                            Axis.YP.rotationDegrees(-rotation)
                    );
        }
    }

    //Helpers
    private float interpolateRotation(
            float lastRotation,
            float rotation,
            float partialTicks
    ) {

        return Mth.rotLerp(
                partialTicks,
                lastRotation,
                rotation
        );
    }
}