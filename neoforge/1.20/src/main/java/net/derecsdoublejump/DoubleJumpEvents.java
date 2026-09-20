package net.derecsdoublejump;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DerecsDoubleJumpMod.MODID)
public final class DoubleJumpEvents {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            DoubleJump.resetIfGrounded(event.player);
        }
    }
}
