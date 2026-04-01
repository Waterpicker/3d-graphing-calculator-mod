package graphingcalculator3d.common.gameplay.blocks;

import java.util.List;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.items.GCItems;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockGC extends Block {
	public final TileEntityType<? extends TileGCBase> TEGC;
	
	public BlockGC(TileEntityType<? extends TileGCBase> tileClass, Properties properties) {
		super(properties);
		TEGC = tileClass;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(BlockToolTips.graphingCalculator3D);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		return TEGC.create();
	}


    @Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.getHeldItem(hand).getItem() == GCItems.item_memory_card) return false;

        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileGCBase) {
            GraphingCalculator3D.proxy.openGuiGC(worldIn, (TileGCBase) tile);
            return true;
        } else {
            return true;
        }
    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.SOLID;
	}
}
