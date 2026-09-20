package net.derecsdoublejump;

import net.minecraftforge.fml.common.Mod;

@Mod(DerecsDoubleJumpMod.MODID)
public class DerecsDoubleJumpMod {
    public static final String MODID = "derecs_double_jump";

    public DerecsDoubleJumpMod() {
        DoubleJumpGameRules.bootstrap();
        DoubleJumpNetwork.register();
    }
}
