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

import java.util.List;

public record AssemblySnapshotPayload(
        BlockPos rotorPos,
        List<BlockEntry> blocks
) implements CustomPacketPayload {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath(
                    BasicRotor.MOD_ID,
                    "assembly_snapshot"
            );

    public static final Type<AssemblySnapshotPayload> TYPE =
            new Type<>(ID);

    /*
    ---------------------------
    Codecs
    ---------------------------
    */

    public static final StreamCodec<RegistryFriendlyByteBuf, AssemblySnapshotPayload> CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    AssemblySnapshotPayload::rotorPos,

                    BlockEntry.CODEC.apply(ByteBufCodecs.list()),
                    AssemblySnapshotPayload::blocks,

                    AssemblySnapshotPayload::new
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

    public record BlockEntry(
            BlockPos relativePos,
            int blockStateId
    ) {

        public static final StreamCodec<RegistryFriendlyByteBuf, BlockEntry> CODEC =
                StreamCodec.composite(
                        BlockPos.STREAM_CODEC,
                        BlockEntry::relativePos,

                        ByteBufCodecs.INT,
                        BlockEntry::blockStateId,

                        BlockEntry::new
                );
    }
}