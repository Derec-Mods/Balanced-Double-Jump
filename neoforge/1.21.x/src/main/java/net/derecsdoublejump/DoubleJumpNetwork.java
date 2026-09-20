package net.derecsdoublejump;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class DoubleJumpNetwork {
    private DoubleJumpNetwork() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(DoubleJumpPayload.TYPE, DoubleJumpPayload.STREAM_CODEC, DoubleJumpNetwork::handle);
    }

    private static void handle(DoubleJumpPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                DoubleJump.tryJump(player);
            }
        });
    }

    public record DoubleJumpPayload() implements CustomPacketPayload {
        public static final Type<DoubleJumpPayload> TYPE =
                new Type<>(ResourceLocation.fromNamespaceAndPath(DerecsDoubleJumpMod.MODID, "double_jump"));
        public static final StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, DoubleJumpPayload> STREAM_CODEC =
                StreamCodec.unit(new DoubleJumpPayload());

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
