package com.l1near.basicrotor.client.render;

/*
---------------------------
Imports
---------------------------
*/
import com.l1near.basicrotor.block.RotorBlock;
import com.l1near.basicrotor.client.movement.ClientRotorMovementData;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.renderer.v1.Renderer;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableMesh;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.l1near.basicrotor.blockentity.RotorBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;
import com.l1near.basicrotor.client.BasicRotorClient;
import com.l1near.basicrotor.mixin.client.BlockModelRenderStateAccessor;
import java.util.List;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Minecraft;

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
    public int getViewDistance() {

        int renderDistanceChunks =
                Minecraft.getInstance()
                        .options
                        .getEffectiveRenderDistance();

        return renderDistanceChunks * 16;
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
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

        state.lightCoords =
                LightCoordsUtil.getLightCoords(
                        blockEntity.getLevel(),
                        blockEntity.getBlockPos()
                );

        state.facing =
                blockEntity
                        .getBlockState()
                        .getValue(RotorBlock.FACING);

        state.rotorBlockState =
                blockEntity.getBlockState();

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

                BlockPos virtualBlockPos =
                        getRotatedBlockPos(
                                blockEntity.getBlockPos(),
                                entry.getKey(),
                                state.facing,
                                state.rotation
                        );

                virtualBlock.lightCoords =
                        LightCoordsUtil.getLightCoords(
                                blockEntity.getLevel(),
                                virtualBlockPos
                        );

                virtualBlock.modelState.blockLightCoords =
                        virtualBlock.lightCoords;

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

        state.modelState.blockLightCoords =
                state.lightCoords;

        state.breakingModel =
                Minecraft.getInstance()
                        .getModelManager()
                        .getBlockStateModelSet()
                        .get(
                                blockEntity.getBlockState()
                        );

        state.breakingModelParts.clear();

        List<BlockStateModelPart> modelParts =
                ((BlockModelRenderStateAccessor) (Object) state.modelState)
                        .getModelParts();

        if (modelParts != null) {
            state.breakingModelParts.addAll(
                    modelParts
            );
        }
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
                0
        );

        if (state.breakProgress != null
                && state.breakingModel != null
                && state.rotorBlockState != null) {

            MutableMesh breakingMesh =
                    Renderer.get().mutableMesh();

            RandomSource random =
                    RandomSource.create(42L);

            state.breakingModel.emitQuads(
                    breakingMesh.emitter(),
                    BlockAndTintGetter.EMPTY,
                    state.blockPos,
                    state.rotorBlockState,
                    random,
                    layer -> false
            );

            submitNodeCollector.submitBreakingBlockModel(
                    poseStack,
                    state.breakingModelParts,
                    breakingMesh.immutableCopy(),
                    state.breakProgress.progress()
            );
        }
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
                        virtualBlock.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
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

    //Rotate Light Position
    private BlockPos getRotatedBlockPos(
            BlockPos originPos,
            BlockPos relativePos,
            Direction facing,
            float rotation
    ) {

        float radians =
                rotation
                        * ((float) Math.PI / 180.0F);

        double x =
                relativePos.getX();

        double y =
                relativePos.getY();

        double z =
                relativePos.getZ();

        double cos =
                Math.cos(radians);

        double sin =
                Math.sin(radians);

        double rotatedX = x;
        double rotatedY = y;
        double rotatedZ = z;

        switch (facing) {

            case NORTH -> {

                rotatedX =
                        x * cos
                                + y * sin;

                rotatedY =
                        -x * sin
                                + y * cos;
            }

            case SOUTH -> {

                rotatedX =
                        x * cos
                                - y * sin;

                rotatedY =
                        x * sin
                                + y * cos;
            }

            case WEST -> {

                rotatedY =
                        y * cos
                                + z * sin;

                rotatedZ =
                        -y * sin
                                + z * cos;
            }

            case EAST -> {

                rotatedY =
                        y * cos
                                - z * sin;

                rotatedZ =
                        y * sin
                                + z * cos;
            }

            case UP -> {

                rotatedX =
                        x * cos
                                + z * sin;

                rotatedZ =
                        -x * sin
                                + z * cos;
            }

            case DOWN -> {

                rotatedX =
                        x * cos
                                - z * sin;

                rotatedZ =
                        x * sin
                                + z * cos;
            }
        }

        return BlockPos.containing(
                originPos.getX()
                        + 0.5
                        + rotatedX,
                originPos.getY()
                        + 0.5
                        + rotatedY,
                originPos.getZ()
                        + 0.5
                        + rotatedZ
        );
    }
}