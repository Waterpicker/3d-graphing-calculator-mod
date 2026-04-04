package graphingcalculator3d.common.gameplay.blocks;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.items.GCItems;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class BlockGC<T extends TileGCBase> extends Block implements EntityBlock {
	public final Supplier<RegistryObject<BlockEntityType<T>>> TEGC;

    public BlockGC(Supplier<RegistryObject<BlockEntityType<T>>> type, Properties properties) {
        super(properties);
		TEGC = type;
	}

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(BlockToolTips.graphingCalculator3D);
	}

//    @Override
//    public int getHarvestLevel(BlockState state) {
//        return 1;
//    }
//
//    @Nullable
//    @Override
//    public ToolType getHarvestTool(BlockState state) {
//        return ToolType.PICKAXE;
//    }
//
//    @Override
//	public boolean hasTileEntity(BlockState state)
//	{
//		return true;
//	}


    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TEGC.get().get().create(pos, state);
	}

    @Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand, BlockHitResult hitResult) {
		if (playerIn.getItemInHand(hand).getItem() == GCItems.item_memory_card.get()) return InteractionResult.FAIL;
        var tile = worldIn.getBlockEntity(pos);
        if (tile instanceof TileGCBase base) {
            GraphingCalculator3D.proxy.openGuiGC(worldIn, base);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.SUCCESS;
        }
    }

//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.SOLID;
//	}
}
