package net.derecsdoublejump.world;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.minecraft.world.GameRules;

import net.derecsdoublejump.DerecsDoubleJumpModElements;

import java.lang.reflect.Method;

@DerecsDoubleJumpModElements.ModElement.Tag
public class DoJumpParticlesGameRule extends DerecsDoubleJumpModElements.ModElement {
	public static final GameRules.RuleKey<GameRules.BooleanValue> gamerule = GameRules.register("doJumpParticles", GameRules.Category.MISC,
			create(true));
	public DoJumpParticlesGameRule(DerecsDoubleJumpModElements instance) {
		super(instance, 4);
	}

	public static GameRules.RuleType<GameRules.BooleanValue> create(boolean defaultValue) {
		try {
			Method createGameruleMethod = ObfuscationReflectionHelper.findMethod(GameRules.BooleanValue.class, "func_223568_b", boolean.class);
			createGameruleMethod.setAccessible(true);
			return (GameRules.RuleType<GameRules.BooleanValue>) createGameruleMethod.invoke(null, defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
