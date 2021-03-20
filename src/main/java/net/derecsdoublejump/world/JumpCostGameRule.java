package net.derecsdoublejump.world;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.minecraft.world.GameRules;

import net.derecsdoublejump.DerecsDoubleJumpModElements;

import java.lang.reflect.Method;

@DerecsDoubleJumpModElements.ModElement.Tag
public class JumpCostGameRule extends DerecsDoubleJumpModElements.ModElement {
	public static final GameRules.RuleKey<GameRules.IntegerValue> gamerule = GameRules.register("jumpCost", GameRules.Category.PLAYER, create(1));
	public JumpCostGameRule(DerecsDoubleJumpModElements instance) {
		super(instance, 3);
	}

	public static GameRules.RuleType<GameRules.IntegerValue> create(int defaultValue) {
		try {
			Method createGameruleMethod = ObfuscationReflectionHelper.findMethod(GameRules.IntegerValue.class, "func_223564_a", int.class);
			createGameruleMethod.setAccessible(true);
			return (GameRules.RuleType<GameRules.IntegerValue>) createGameruleMethod.invoke(null, defaultValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
