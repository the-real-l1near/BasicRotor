package com.l1near.basicrotor.client.assembly;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedBlockData;
import net.minecraft.core.BlockPos;

import java.util.LinkedHashMap;

public class VirtualAssemblyData {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final BlockPos originPos;
    private final LinkedHashMap<BlockPos, LinkedBlockData> linkedBlocks;

    private float rotation;
    private boolean virtualized;
    private float renderRotation;
    private float previousRenderRotation;
    private float rotationStep;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public VirtualAssemblyData(BlockPos originPos) {

        this.originPos = originPos;
        this.linkedBlocks = new LinkedHashMap<>();

        this.rotation = 0.0F;
        this.virtualized = false;
        this.renderRotation = 0.0F;
        this.previousRenderRotation = 0.0F;
        this.rotationStep = 0.0F;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Blocks
    public void addBlock(
            BlockPos relativePos,
            LinkedBlockData blockData
    ) {
        linkedBlocks.put(relativePos, blockData);
    }

    //Render Tick
    public void tickRenderRotation() {

        previousRenderRotation =
                renderRotation;

        if (!virtualized) {
            renderRotation =
                    rotation;

            return;
        }

        renderRotation +=
                rotationStep;

        renderRotation %= 360.0F;

        if (renderRotation < 0.0F) {
            renderRotation += 360.0F;
        }
    }

    //Getters
    public BlockPos getOriginPos() {
        return originPos;
    }

    public LinkedHashMap<BlockPos, LinkedBlockData> getLinkedBlocks() {
        return linkedBlocks;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isVirtualized() {
        return virtualized;
    }

    public float getRenderRotation() {
        return renderRotation;
    }

    public float getPreviousRenderRotation() {
        return previousRenderRotation;
    }

    public float getRotationStep() {
        return rotationStep;
    }


    //Setters
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setVirtualized(boolean virtualized) {
        this.virtualized = virtualized;
    }

    public void setRenderRotation(float renderRotation) {
        this.renderRotation = renderRotation;
    }

    public void setPreviousRenderRotation(float previousRenderRotation) {
        this.previousRenderRotation = previousRenderRotation;
    }

    public void setRotationStep(float rotationStep) {
        this.rotationStep = rotationStep;
    }


}