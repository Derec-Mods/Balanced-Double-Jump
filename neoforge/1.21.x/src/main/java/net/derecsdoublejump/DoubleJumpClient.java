package net.derecsdoublejump;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

public final class DoubleJumpClient {
    public static final KeyMapping DOUBLE_JUMP_KEY = new KeyMapping(
            "key.derecs_double_jump.double_jump",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            "key.categories.movement"
    );

    private DoubleJumpClient() {
    }

    @EventBusSubscriber(modid = DerecsDoubleJumpMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class ModEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(DOUBLE_JUMP_KEY);
        }
    }

    @EventBusSubscriber(modid = DerecsDoubleJumpMod.MODID, value = Dist.CLIENT)
    public static final class GameEvents {
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen != null) {
                return;
            }
            LocalPlayer player = minecraft.player;
            while (DOUBLE_JUMP_KEY.consumeClick()) {
                if (player != null) {
                    DoubleJump.tryJump(player);
                    PacketDistributor.sendToServer(new DoubleJumpNetwork.DoubleJumpPayload());
                }
            }
        }
    }
}
