package morph.avaritia.item;

import codechicken.lib.item.ItemMultiType;
import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.util.TransformUtils;
import morph.avaritia.api.IHaloRenderItem;
import morph.avaritia.api.registration.IModelRegister;
import morph.avaritia.client.render.item.HaloRenderItem;
import morph.avaritia.init.AvaritiaTextures;
import morph.avaritia.util.TextUtils;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by covers1624 on 11/04/2017.
 */
public class ItemSingularity extends ItemMultiType implements IHaloRenderItem, IModelRegister {

    public ItemSingularity(CreativeTabs tab, String registryName) {
        super(tab, registryName);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        int meta = stack.getItemDamage();
        if (meta == 10) {
            //tooltip.add(EnumChatFormatting.DARK_GRAY +""+ EnumChatFormatting.ITALIC + StatCollector.translateToLocal("tooltip.claybalance.desc"));
            tooltip.add(TextUtils.makeFabulous(I18n.translateToLocal("tooltip.claybalance.desc")));
        }
    }

    @Override
    @SideOnly (Side.CLIENT)
    public boolean shouldDrawHalo(ItemStack stack) {
        return true;
    }

    @Override
    @SideOnly (Side.CLIENT)
    public TextureAtlasSprite getHaloTexture(ItemStack stack) {
        return AvaritiaTextures.HALO;
    }

    @Override
    @SideOnly (Side.CLIENT)
    public int getHaloSize(ItemStack stack) {
        return 4;
    }

    @Override
    @SideOnly (Side.CLIENT)
    public int getHaloColour(ItemStack stack) {
        return 0xFF000000;
    }

    @Override
    @SideOnly (Side.CLIENT)
    public boolean shouldDrawPulse(ItemStack stack) {
        return false;
    }

    @Override
    @SideOnly (Side.CLIENT)
    public void registerModels() {
        ModelResourceLocation location = new ModelResourceLocation("avaritia:singularity", "type=singularity");
        ModelLoader.registerItemVariants(this, location);
        IBakedModel wrappedModel = new HaloRenderItem(TransformUtils.DEFAULT_ITEM, modelRegistry -> modelRegistry.getObject(location));
        ModelRegistryHelper.register(location, wrappedModel);
        ModelLoader.setCustomMeshDefinition(this, stack -> location);
    }
}
