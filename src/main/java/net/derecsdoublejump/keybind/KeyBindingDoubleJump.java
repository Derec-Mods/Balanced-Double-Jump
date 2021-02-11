
package net.derecsdoublejump.keybind;

import org.lwjgl.input.Keyboard;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

import net.derecsdoublejump.ElementsDerecsDoubleJumpMod;
import net.derecsdoublejump.DerecsDoubleJumpMod;

@ElementsDerecsDoubleJumpMod.ModElement.Tag
public class KeyBindingDoubleJump extends ElementsDerecsDoubleJumpMod.ModElement {
	private KeyBinding keys;
	public KeyBindingDoubleJump(ElementsDerecsDoubleJumpMod instance) {
		super(instance, 1);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		elements.addNetworkMessage(KeyBindingPressedMessageHandler.class, KeyBindingPressedMessage.class, Side.SERVER);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void init(FMLInitializationEvent event) {
		keys = new KeyBinding("key.double_jump", Keyboard.KEY_SPACE, "key.categories.misc");
		ClientRegistry.registerKeyBinding(keys);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Minecraft.getMinecraft().currentScreen == null) {
			if (org.lwjgl.input.Keyboard.isKeyDown(keys.getKeyCode())) {
				DerecsDoubleJumpMod.PACKET_HANDLER.sendToServer(new KeyBindingPressedMessage());
				pressAction(Minecraft.getMinecraft().player);
			}
		}
	}
	public static class KeyBindingPressedMessageHandler implements IMessageHandler<KeyBindingPressedMessage, IMessage> {
		@Override
		public IMessage onMessage(KeyBindingPressedMessage message, MessageContext context) {
			EntityPlayerMP entity = context.getServerHandler().player;
			entity.getServerWorld().addScheduledTask(() -> {
				pressAction(entity);
			});
			return null;
		}
	}

	public static class KeyBindingPressedMessage implements IMessage {
		@Override
		public void toBytes(io.netty.buffer.ByteBuf buf) {
		}

		@Override
		public void fromBytes(io.netty.buffer.ByteBuf buf) {
		}
	}
	private static void pressAction(EntityPlayer entity) {
		World world = entity.world;
		int x = (int) entity.posX;
		int y = (int) entity.posY;
		int z = (int) entity.posZ;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
		{
			java.util.HashMap<String, Object> $_dependencies = new java.util.HashMap<>();
			$_dependencies.put("entity", entity);
			ProcedureCheckPlayerInAir.executeProcedure($_dependencies);
		}
	}
}
