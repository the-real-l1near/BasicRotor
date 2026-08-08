package com.l1near.basicrotor.network.payload;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.BasicRotor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record AssemblyRemovePayload(
        BlockPos rotorPos
) implements CustomPacketPayload {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath(
                    BasicRotor.MOD_ID,
                    "assembly_remove"
            );

    public static final Type<AssemblyRemovePayload> TYPE =
            new Type<>(ID);

    /*
    ---------------------------
    Codecs
    ---------------------------
    */

    public static final StreamCodec<RegistryFriendlyByteBuf, AssemblyRemovePayload> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    AssemblyRemovePayload::rotorPos,
                    AssemblyRemovePayload::new
            );

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}