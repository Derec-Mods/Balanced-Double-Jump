package net.derecsdoublejump.world;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.minecraft.world.GameRules;

import net.derecsdoublejump.DerecsDoubleJumpModElements;

import java.lang.reflect.Method;

@DerecsDoubleJumpModElements.ModElement.Tag
public class DoJumpSoundGameRule extends DerecsDoubleJumpModElements.ModElement {
	public static final GameRules.RuleKey<GameRules.BooleanValue> gamerule = GameRules.register("doJumpSound", GameRules.Category.MISC, create(true));
	public DoJumpSoundGameRule(DerecsDoubleJumpModElements instance) {
		super(instance, 5);
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
