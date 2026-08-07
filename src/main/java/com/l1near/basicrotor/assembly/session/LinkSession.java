package com.l1near.basicrotor.assembly.session;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.core.BlockPos;

import java.util.LinkedHashSet;

public class LinkSession {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private BlockPos selectedRotorPos;

    private final LinkedHashSet<BlockPos> selectedBlocks;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public LinkSession() {
        this.selectedBlocks = new LinkedHashSet<>();
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Add
    public void addBlock(BlockPos blockPos) {
        selectedBlocks.add(blockPos);
    }

    //Remove
    public void removeBlock(BlockPos blockPos) {
        selectedBlocks.remove(blockPos);
    }

    //Contains
    public boolean contains(BlockPos blockPos) {
        return selectedBlocks.contains(blockPos);
    }

    //Clear
    public void clear() {
        selectedBlocks.clear();
    }

    //Getters
    public BlockPos getSelectedRotorPos() {
        return selectedRotorPos;
    }

    public LinkedHashSet<BlockPos> getSelectedBlocks() {
        return selectedBlocks;
    }

    //Setters
    public void setSelectedRotorPos(BlockPos selectedRotorPos) {
        this.selectedRotorPos = selectedRotorPos;
    }

}