package net.derecsdoublejump;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = DerecsDoubleJumpMod.MODID)
public final class DoubleJumpEvents {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        DoubleJump.resetIfGrounded(event.getEntity());
    }
}
