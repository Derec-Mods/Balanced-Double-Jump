package net.derecsdoublejump;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(DerecsDoubleJumpMod.MODID)
public class DerecsDoubleJumpMod {
    public static final String MODID = "derecs_double_jump";

    public DerecsDoubleJumpMod(IEventBus modEventBus) {
        DoubleJumpGameRules.bootstrap();
        modEventBus.addListener(DoubleJumpNetwork::register);
    }
}
