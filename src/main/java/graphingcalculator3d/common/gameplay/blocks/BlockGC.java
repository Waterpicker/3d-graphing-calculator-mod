package graphingcalculator3d.common.gameplay.blocks;

import java.util.List;
import java.util.function.Supplier;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.items.GCItems;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
//import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;

public class BlockGC<T extends TileGCBase> extends Block {
	public final Supplier<RegistryObject<TileEntityType<T>>> TEGC;

    public BlockGC(Supplier<RegistryObject<TileEntityType<T>>> type, Properties properties) {
        super(properties);
		TEGC = type;
	}

    @Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(BlockToolTips.graphingCalculator3D);
	}

    @Override
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TEGC.get().get().create();
	}

    @Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hitResult) {
		if (playerIn.getHeldItem(hand).getItem() == GCItems.item_memory_card.get()) return ActionResultType.FAIL;

        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileGCBase) {
            GraphingCalculator3D.proxy.openGuiGC(worldIn, (TileGCBase) tile);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.SUCCESS;
        }
    }

//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.SOLID;
//	}
}
