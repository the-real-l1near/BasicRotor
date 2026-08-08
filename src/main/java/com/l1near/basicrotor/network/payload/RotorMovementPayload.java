package com.l1near.basicrotor.network.payload;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.BasicRotor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record RotorMovementPayload(
        BlockPos rotorPos,
        float rotation,
        float rotationStep,
        boolean moving,
        boolean virtualized
) implements CustomPacketPayload {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath(
                    BasicRotor.MOD_ID,
                    "rotor_movement"
            );

    public static final Type<RotorMovementPayload> TYPE =
            new Type<>(ID);

    /*
    ---------------------------
    Codecs
    ---------------------------
    */

    public static final StreamCodec<RegistryFriendlyByteBuf, RotorMovementPayload> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    RotorMovementPayload::rotorPos,

                    ByteBufCodecs.FLOAT,
                    RotorMovementPayload::rotation,

                    ByteBufCodecs.FLOAT,
                    RotorMovementPayload::rotationStep,

                    ByteBufCodecs.BOOL,
                    RotorMovementPayload::moving,

                    ByteBufCodecs.BOOL,
                    RotorMovementPayload::virtualized,

                    RotorMovementPayload::new
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