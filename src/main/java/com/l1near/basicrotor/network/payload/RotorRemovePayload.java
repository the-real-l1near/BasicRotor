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

public record RotorRemovePayload(
        BlockPos rotorPos
) implements CustomPacketPayload {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final Type<RotorRemovePayload> TYPE =
            new Type<>(
                    Identifier.fromNamespaceAndPath(
                            BasicRotor.MOD_ID,
                            "rotor_remove"
                    )
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, RotorRemovePayload> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    RotorRemovePayload::rotorPos,
                    RotorRemovePayload::new
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