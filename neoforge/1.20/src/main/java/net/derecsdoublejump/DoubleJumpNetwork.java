package net.derecsdoublejump;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public final class DoubleJumpNetwork {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(DerecsDoubleJumpMod.MODID, "main"))
            .networkProtocolVersion(() -> PROTOCOL)
            .clientAcceptedVersions(PROTOCOL::equals)
            .serverAcceptedVersions(PROTOCOL::equals)
            .simpleChannel();

    private DoubleJumpNetwork() {
    }

    public static void register() {
        CHANNEL.messageBuilder(DoubleJumpPacket.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(DoubleJumpPacket::encode)
                .decoder(DoubleJumpPacket::decode)
                .consumerMainThread(DoubleJumpPacket::handle)
                .add();
    }

    public static void sendToServer() {
        CHANNEL.sendToServer(new DoubleJumpPacket());
    }

    public static final class DoubleJumpPacket {
        public static void encode(DoubleJumpPacket packet, FriendlyByteBuf buf) {
        }

        public static DoubleJumpPacket decode(FriendlyByteBuf buf) {
            return new DoubleJumpPacket();
        }

        public static void handle(DoubleJumpPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            ServerPlayer player = context.getSender();
            if (player != null) {
                DoubleJump.tryJump(player);
            }
            context.setPacketHandled(true);
        }
    }
}
