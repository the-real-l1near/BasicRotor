package com.l1near.basicrotor.client.render;

/*
---------------------------
Imports
---------------------------
*/
import com.l1near.basicrotor.block.RotorBlock;
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
        state.rotation = blockEntity.getRotation(partialTicks);
        state.facing = blockEntity
                .getBlockState()
                .getValue(RotorBlock.FACING);

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

// spin trước
        applySpinRotation(
                state.facing,
                state.rotation,
                poseStack
        );

// xoay hướng block sau
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

            // Nhìn từ NORTH vào block
            case NORTH ->
                    poseStack.mulPose(
                            Axis.ZP.rotationDegrees(-rotation)
                    );

            // Nhìn từ SOUTH vào block
            case SOUTH ->
                    poseStack.mulPose(
                            Axis.ZP.rotationDegrees(rotation)
                    );


            // Nhìn từ WEST vào block
            case WEST ->
                    poseStack.mulPose(
                            Axis.XP.rotationDegrees(-rotation)
                    );

            // Nhìn từ EAST vào block
            case EAST ->
                    poseStack.mulPose(
                            Axis.XP.rotationDegrees(rotation)
                    );


            // Nhìn từ trên xuống
            case UP ->
                    poseStack.mulPose(
                            Axis.YP.rotationDegrees(rotation)
                    );

            // Nhìn từ dưới lên
            case DOWN ->
                    poseStack.mulPose(
                            Axis.YP.rotationDegrees(-rotation)
                    );
        }
    }
}