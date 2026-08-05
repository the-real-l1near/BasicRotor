package com.l1near.basicrotor.assembly;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.core.BlockPos;

import java.util.Collection;
import java.util.LinkedHashMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class LinkedAssembly {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final BlockPos originPos;

    private final LinkedHashMap<BlockPos, LinkedBlockData> linkedBlocks;

    private AssemblyState state;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public LinkedAssembly(BlockPos originPos) {
        this.originPos = originPos;
        this.linkedBlocks = new LinkedHashMap<>();
        this.state = AssemblyState.STOPPED;
    }

    /*
---------------------------
Methods
---------------------------
*/

    //Add
    public void addBlock(
            BlockPos relativePos,
            LinkedBlockData blockData
    ) {
        linkedBlocks.put(relativePos, blockData);
    }

    //Remove
    public void removeBlock(BlockPos relativePos) {
        linkedBlocks.remove(relativePos);
    }

    //Contains
    public boolean contains(BlockPos relativePos) {
        return linkedBlocks.containsKey(relativePos);
    }

    //Entries
    public Set<Map.Entry<BlockPos, LinkedBlockData>> getEntries() {
        return linkedBlocks.entrySet();
    }

    //Blocks
    public Collection<LinkedBlockData> getBlocks() {
        return linkedBlocks.values();
    }

    //Getters
    public BlockPos getOriginPos() {
        return originPos;
    }

    public LinkedHashMap<BlockPos, LinkedBlockData> getLinkedBlocks() {
        return linkedBlocks;
    }

    public AssemblyState getState() {
        return state;
    }

    //Setters
    public void setState(AssemblyState state) {
        this.state = state;
    }

}