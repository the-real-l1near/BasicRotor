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

public record AssemblyRequestPayload(
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
                    "assembly_request"
            );

    public static final Type<AssemblyRequestPayload> TYPE =
            new Type<>(ID);

    /*
    ---------------------------
    Codecs
    ---------------------------
    */

    public static final StreamCodec<RegistryFriendlyByteBuf, AssemblyRequestPayload> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    AssemblyRequestPayload::rotorPos,
                    AssemblyRequestPayload::new
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