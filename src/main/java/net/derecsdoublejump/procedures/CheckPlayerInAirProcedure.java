package net.derecsdoublejump.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.world.GameType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.FoodStats;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.Minecraft;

import net.derecsdoublejump.world.JumpCostGameRule;
import net.derecsdoublejump.world.DoJumpSoundGameRule;
import net.derecsdoublejump.world.DoJumpParticlesGameRule;
import net.derecsdoublejump.DerecsDoubleJumpModElements;
import net.derecsdoublejump.DerecsDoubleJumpMod;

import java.util.Map;

@DerecsDoubleJumpModElements.ModElement.Tag
public class CheckPlayerInAirProcedure extends DerecsDoubleJumpModElements.ModElement {
	public CheckPlayerInAirProcedure(DerecsDoubleJumpModElements instance) {
		super(instance, 1);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				DerecsDoubleJumpMod.LOGGER.warn("Failed to load dependency entity for procedure CheckPlayerInAir!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				DerecsDoubleJumpMod.LOGGER.warn("Failed to load dependency x for procedure CheckPlayerInAir!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				DerecsDoubleJumpMod.LOGGER.warn("Failed to load dependency y for procedure CheckPlayerInAir!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				DerecsDoubleJumpMod.LOGGER.warn("Failed to load dependency z for procedure CheckPlayerInAir!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				DerecsDoubleJumpMod.LOGGER.warn("Failed to load dependency world for procedure CheckPlayerInAir!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double xRadius = 0;
		double loop = 0;
		double zRadius = 0;
		double particleAmount = 0;
		if (((!(entity.isOnGround())) && ((!(entity.isInLava())) && (!(entity.isInLava()))))) {
			if ((!(entity.getPersistentData().getBoolean("hasDoubleJumped")))) {
				if ((!(new Object() {
					public boolean checkGamemode(Entity _ent) {
						if (_ent instanceof ServerPlayerEntity) {
							return ((ServerPlayerEntity) _ent).interactionManager.getGameType() == GameType.SPECTATOR;
						} else if (_ent instanceof PlayerEntity && _ent.world.isRemote()) {
							NetworkPlayerInfo _npi = Minecraft.getInstance().getConnection()
									.getPlayerInfo(((ClientPlayerEntity) _ent).getGameProfile().getId());
							return _npi != null && _npi.getGameType() == GameType.SPECTATOR;
						}
						return false;
					}
				}.checkGamemode(entity)))) {
					if ((((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).getFoodStats().getFoodLevel() : 0) >= 7)) {
						entity.setMotion((entity.getMotion().getX()), 0.5, (entity.getMotion().getZ()));
						if (((world instanceof World) ? ((World) world).getGameRules().getBoolean(DoJumpParticlesGameRule.gamerule) : false)) {
							loop = (double) 0;
							particleAmount = (double) 8;
							xRadius = (double) 1;
							zRadius = (double) 1;
							while (((loop) < (particleAmount))) {
								world.addParticle(ParticleTypes.CRIT,
										((x + 0.5) + (Math.cos((((Math.PI * 2) / (particleAmount)) * (loop))) * (xRadius))), y,
										((z + 0.5) + (Math.sin((((Math.PI * 2) / (particleAmount)) * (loop))) * (zRadius))), 0, 0.05, 0);
								loop = (double) ((loop) + 1);
							}
						}
						if (((world instanceof World) ? ((World) world).getGameRules().getBoolean(DoJumpSoundGameRule.gamerule) : false)) {
							if (world instanceof World && !world.isRemote()) {
								((World) world)
										.playSound(null, new BlockPos((int) x, (int) y, (int) z),
												(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
														.getValue(new ResourceLocation("entity.horse.jump")),
												SoundCategory.NEUTRAL, (float) 1, (float) 1);
							} else {
								((World) world).playSound(x, y, z,
										(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
												.getValue(new ResourceLocation("entity.horse.jump")),
										SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
							}
						}
						entity.getPersistentData().putBoolean("hasDoubleJumped", (true));
						if (((new Object() {
							public boolean checkGamemode(Entity _ent) {
								if (_ent instanceof ServerPlayerEntity) {
									return ((ServerPlayerEntity) _ent).interactionManager.getGameType() == GameType.SURVIVAL;
								} else if (_ent instanceof PlayerEntity && _ent.world.isRemote()) {
									NetworkPlayerInfo _npi = Minecraft.getInstance().getConnection()
											.getPlayerInfo(((ClientPlayerEntity) _ent).getGameProfile().getId());
									return _npi != null && _npi.getGameType() == GameType.SURVIVAL;
								}
								return false;
							}
						}.checkGamemode(entity)) || (new Object() {
							public boolean checkGamemode(Entity _ent) {
								if (_ent instanceof ServerPlayerEntity) {
									return ((ServerPlayerEntity) _ent).interactionManager.getGameType() == GameType.SURVIVAL;
								} else if (_ent instanceof PlayerEntity && _ent.world.isRemote()) {
									NetworkPlayerInfo _npi = Minecraft.getInstance().getConnection()
											.getPlayerInfo(((ClientPlayerEntity) _ent).getGameProfile().getId());
									return _npi != null && _npi.getGameType() == GameType.SURVIVAL;
								}
								return false;
							}
						}.checkGamemode(entity)))) {
							if ((((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).getFoodStats().getSaturationLevel() : 0) >= 1)) {
								if (entity instanceof PlayerEntity) {
									ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, ((PlayerEntity) entity).getFoodStats(),
											(float) (((entity instanceof PlayerEntity)
													? ((PlayerEntity) entity).getFoodStats().getSaturationLevel()
													: 0)
													- ((world instanceof World)
															? ((World) world).getGameRules().getInt(JumpCostGameRule.gamerule)
															: 0)),
											"field_75125_b");
								}
							} else {
								if (entity instanceof PlayerEntity)
									((PlayerEntity) entity).getFoodStats().setFoodLevel((int) (((entity instanceof PlayerEntity)
											? ((PlayerEntity) entity).getFoodStats().getFoodLevel()
											: 0)
											- ((world instanceof World) ? ((World) world).getGameRules().getInt(JumpCostGameRule.gamerule) : 0)));
							}
						}
					}
				}
			}
		}
	}
}
