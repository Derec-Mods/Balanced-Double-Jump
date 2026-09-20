package net.derecsdoublejump;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

    @Mod.EventBusSubscriber(modid = DerecsDoubleJumpMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class ModEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(DOUBLE_JUMP_KEY);
        }
    }

    @Mod.EventBusSubscriber(modid = DerecsDoubleJumpMod.MODID, value = Dist.CLIENT)
    public static final class GameEvents {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen != null) {
                return;
            }
            LocalPlayer player = minecraft.player;
            while (DOUBLE_JUMP_KEY.consumeClick()) {
                if (player != null) {
                    DoubleJump.tryJump(player);
                    DoubleJumpNetwork.sendToServer();
                }
            }
        }
    }
}
