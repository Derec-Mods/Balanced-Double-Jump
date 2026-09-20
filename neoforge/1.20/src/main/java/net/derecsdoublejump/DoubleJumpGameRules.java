package net.derecsdoublejump;

import net.minecraft.world.level.GameRules;

public final class DoubleJumpGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> DO_JUMP_SOUND =
            GameRules.register("doJumpSound", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> DO_JUMP_PARTICLES =
            GameRules.register("doJumpParticles", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.IntegerValue> JUMP_COST =
            GameRules.register("jumpCost", GameRules.Category.PLAYER, GameRules.IntegerValue.create(1));

    private DoubleJumpGameRules() {
    }

    public static void bootstrap() {
        // Forces class loading so the gamerules are registered.
    }
}
