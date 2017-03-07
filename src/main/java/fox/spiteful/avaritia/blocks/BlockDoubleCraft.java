package fox.spiteful.avaritia.blocks;

import javax.annotation.Nullable;

import fox.spiteful.avaritia.Avaritia;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDoubleCraft extends BlockBase {

	public BlockDoubleCraft() {
		super(Material.WOOD, "compressed_workbench", 4.0F, -1.0F);
		setSoundType(SoundType.WOOD);
		setCreativeTab(Avaritia.tab);

	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		else {
			player.openGui(Avaritia.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}

}
