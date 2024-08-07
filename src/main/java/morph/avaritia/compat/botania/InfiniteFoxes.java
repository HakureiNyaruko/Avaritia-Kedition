package morph.avaritia.compat.botania;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class InfiniteFoxes {

	@SideOnly(Side.CLIENT)
	private static FakeTailEntity fakeEntity;
	@SideOnly(Side.CLIENT)
	private static Object tailPartInfo;
	@SideOnly(Side.CLIENT)
	private static Object earPartInfo;
	@SideOnly(Side.CLIENT)
	private static Object foxTailRender;
	@SideOnly(Side.CLIENT)
	private static Object foxEarsRender;
	@SideOnly(Side.CLIENT)
	private static Method m_RenderPart_render;



	public static void floof(){
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			grabReflections();
			MinecraftForge.EVENT_BUS.register(new InfiniteFoxes());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public static void grabReflections() {
		try {
			Class c_PartType = Class.forName("kihira.tails.common.PartsData$PartType");
			Class c_PartInfo = Class.forName("kihira.tails.common.PartInfo");

			//Setup Info
			Constructor constr = c_PartInfo.getConstructor(boolean.class, int.class, int.class, int.class, int[].class, c_PartType, ResourceLocation.class);
			tailPartInfo = constr.newInstance(true, 0, 2, 0, new int[]{-5480951, -6594259, -5197647}, Enum.valueOf(c_PartType, "TAIL"), null);
			earPartInfo = constr.newInstance(true, 0, 0, 0, new int[]{-5480951, 0xFF000000, -5197647}, Enum.valueOf(c_PartType, "EARS"), null);

			//Grab renderparts + render method
			Class c_RenderPart = Class.forName("kihira.tails.client.render.RenderPart");
			Class c_PartRegistry = Class.forName("kihira.tails.client.PartRegistry");
			Method m_getRenderPart = c_PartRegistry.getMethod("getRenderPart", c_PartType, int.class);
			foxTailRender = m_getRenderPart.invoke(null, Enum.valueOf(c_PartType, "TAIL"),0);
			foxEarsRender = m_getRenderPart.invoke(null, Enum.valueOf(c_PartType, "EARS"),0);

			m_RenderPart_render = c_RenderPart.getMethod("render", EntityLivingBase.class, c_PartInfo, double.class, double.class, double.class, float.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void renderInfinitatoFluff(float partialTicks) {
		if (fakeEntity == null) {
			fakeEntity = new FakeTailEntity(Minecraft.getMinecraft().world);
		}

		if (m_RenderPart_render != null) {
			try {
				GlStateManager.scale(2,2,2);
				GlStateManager.translate(0,.25f,.5f);
				m_RenderPart_render.invoke(foxTailRender, fakeEntity, tailPartInfo, 0,0,0, partialTicks);
				GlStateManager.translate(0,.25f,-.75f);
				GlStateManager.scale(2,2,2);
				m_RenderPart_render.invoke(foxEarsRender, fakeEntity, earPartInfo, 0,0,0, partialTicks);
			} catch (Exception e) {}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onWorldUnload(WorldEvent.Unload e) {
		if (fakeEntity != null) {
			fakeEntity.setDead();
			fakeEntity = null;
		}
	}

	@SideOnly(Side.CLIENT)
	public static class FakeTailEntity extends EntityLiving {

		public FakeTailEntity(World world) {
			super(world);
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
			return nbt;
		}
		@Override
		public void readFromNBT(NBTTagCompound nbt) {}
	}
}