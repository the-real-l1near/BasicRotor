package com.l1near.basicrotor.assembly.persistence;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.BasicRotor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.assembly.manager.AssemblyManager;

import net.minecraft.server.level.ServerLevel;

public class AssemblySavedData extends SavedData {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final Codec<AssemblySavedData> CODEC =
            RecordCodecBuilder.create(
                    instance ->
                            instance.group(
                                    SavedAssemblyData.CODEC
                                            .listOf()
                                            .fieldOf("assemblies")
                                            .forGetter(
                                                    AssemblySavedData::getAssemblies
                                            )
                            ).apply(
                                    instance,
                                    AssemblySavedData::new
                            )
            );

    public static final SavedDataType<AssemblySavedData> TYPE =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath(
                            BasicRotor.MOD_ID,
                            "assemblies"
                    ),
                    AssemblySavedData::new,
                    CODEC,
                    null
            );

    private final List<SavedAssemblyData> assemblies;

    //Update From Manager
    public void updateFromManager(
            AssemblyManager assemblyManager
    ) {

        assemblies.clear();

        for (LinkedAssembly assembly
                : assemblyManager.getAssemblies()) {

            assemblies.add(
                    SavedAssemblyData.fromAssembly(
                            assembly
                    )
            );
        }

        setDirty();
    }

    //Load Into Manager
    public void loadIntoManager(
            AssemblyManager assemblyManager
    ) {

        assemblyManager.clear();

        for (SavedAssemblyData savedAssembly
                : assemblies) {

            LinkedAssembly assembly =
                    savedAssembly.toAssembly();

            assemblyManager.register(
                    assembly.getOriginPos(),
                    assembly
            );
        }
    }

    //Get
    public static AssemblySavedData get(
            ServerLevel level
    ) {

        return level
                .getDataStorage()
                .computeIfAbsent(
                        TYPE
                );
    }

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public AssemblySavedData() {
        this.assemblies = new ArrayList<>();
    }

    private AssemblySavedData(
            List<SavedAssemblyData> assemblies
    ) {
        this.assemblies =
                new ArrayList<>(
                        assemblies
                );
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Getters
    public List<SavedAssemblyData> getAssemblies() {
        return assemblies;
    }
}