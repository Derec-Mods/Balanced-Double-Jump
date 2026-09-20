package net.derecsdoublejump;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public final class DoubleJump {
    public static final String FLAG = "hasDoubleJumped";

    private DoubleJump() {
    }

    public static void resetIfGrounded(Player player) {
        if (player.onGround()) {
            player.getPersistentData().putBoolean(FLAG, false);
        }
    }

    public static void tryJump(Player player) {
        if (!canJump(player)) {
            return;
        }

        player.setDeltaMovement(player.getDeltaMovement().x, 0.5, player.getDeltaMovement().z);
        player.hasImpulse = true;
        player.getPersistentData().putBoolean(FLAG, true);

        if (player.level().isClientSide) {
            return;
        }

        player.hurtMarked = true;
        applyHungerCost(player);
        playEffects(player);
    }

    private static boolean canJump(Player player) {
        if (player.onGround() || player.isInLava() || player.getAbilities().flying || player.isFallFlying()) {
            return false;
        }
        if (player.getPersistentData().getBoolean(FLAG) || player.isSpectator()) {
            return false;
        }
        return player.getFoodData().getFoodLevel() >= 7;
    }

    private static void applyHungerCost(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer) || serverPlayer.gameMode.getGameModeForPlayer() != GameType.SURVIVAL) {
            return;
        }

        int cost = player.level().getGameRules().getInt(DoubleJumpGameRules.JUMP_COST);
        FoodData food = player.getFoodData();
        if (food.getSaturationLevel() >= 1.0F) {
            food.setSaturation(Math.max(0.0F, food.getSaturationLevel() - cost));
        } else {
            food.setFoodLevel(Math.max(0, food.getFoodLevel() - cost));
        }
    }

    private static void playEffects(Player player) {
        Level level = player.level();
        if (level.getGameRules().getBoolean(DoubleJumpGameRules.DO_JUMP_SOUND)) {
            level.playSound(null, player.blockPosition(), SoundEvents.HORSE_JUMP, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        if (level instanceof ServerLevel serverLevel && level.getGameRules().getBoolean(DoubleJumpGameRules.DO_JUMP_PARTICLES)) {
            for (int i = 0; i < 8; i++) {
                double angle = (Math.PI * 2.0 / 8.0) * i;
                serverLevel.sendParticles(ParticleTypes.CRIT,
                        player.getX() + Math.cos(angle),
                        player.getY(),
                        player.getZ() + Math.sin(angle),
                        1, 0.0, 0.05, 0.0, 0.0);
            }
        }
    }
}
