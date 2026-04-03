package graphingcalculator3d.common.gameplay.items;

import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.networking.GCPacketHandler;
import graphingcalculator3d.common.util.networking.packets.PacketGC;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static graphingcalculator3d.common.util.nbthandler.GCNBT.MEMORY_TIP;

public class ItemMemoryCard extends Item {
	public static final String WRITTEN = "Carrying Block Data.";
	public static final String EMPTY = "Empty";

    public ItemMemoryCard(Properties properties) {
        super(properties);
    }


    @Override
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Hand hand = context.getPlayer().getUsedItemHand();

		TileEntity tile = worldIn.getBlockEntity(pos);
		TileGCBase tileGC;
		ItemStack stack = player.getItemInHand(hand);
		CompoundNBT compound = stack.hasTag() ? stack.getTag() : new CompoundNBT();
		
		if (tile instanceof TileGCBase)
			tileGC = (TileGCBase) tile;
		else
			return ActionResultType.PASS;
		
		if (player.isShiftKeyDown()) {
			compound = tileGC.writeRelevant(compound);
			MEMORY_TIP.setValue(compound, WRITTEN);
			stack.setTag(compound);
			ITextComponent tempF = (tileGC.getFunction() == null) ? this.getDefaultInstance().getDisplayName() : new StringTextComponent(tileGC.getFunction().writeToString());
			stack.setHoverName(tempF);
		} else {
			tileGC.readRelevant(compound);
			if (worldIn.isClientSide)
				GCPacketHandler.GRAPH_SYNC.sendToServer(new PacketGC(tileGC));
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent(MEMORY_TIP.getValue(stack.getOrCreateTag())));
	}
}
