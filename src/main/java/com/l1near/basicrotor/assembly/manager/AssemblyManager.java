package com.l1near.basicrotor.assembly.manager;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import net.minecraft.core.BlockPos;

import java.util.Collection;
import java.util.LinkedHashMap;

public class AssemblyManager {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final LinkedHashMap<BlockPos, LinkedAssembly> assemblies;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public AssemblyManager() {
        this.assemblies = new LinkedHashMap<>();
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Register
    public void register(
            BlockPos rotorPos,
            LinkedAssembly assembly
    ) {
        assemblies.put(rotorPos, assembly);
    }

    //Remove
    public void remove(BlockPos rotorPos) {
        assemblies.remove(rotorPos);
    }

    //Contains
    public boolean contains(BlockPos rotorPos) {
        return assemblies.containsKey(rotorPos);
    }

    //Get
    public LinkedAssembly get(BlockPos rotorPos) {
        return assemblies.get(rotorPos);
    }

    //Clear
    public void clear() {
        assemblies.clear();
    }

    //Assemblies
    public Collection<LinkedAssembly> getAssemblies() {
        return assemblies.values();
    }

}