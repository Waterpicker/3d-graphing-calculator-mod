package graphingcalculator3d.common.gameplay.items;

import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.networking.GCPacketHandler;
import graphingcalculator3d.common.util.networking.packets.PacketGC;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static graphingcalculator3d.common.util.nbthandler.GCNBT.MEMORY_TIP;

public class ItemMemoryCard extends net.minecraft.world.item.Item {
	public static final String WRITTEN = "Carrying Block Data.";
	public static final String EMPTY = "Empty";

    public ItemMemoryCard(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        InteractionHand hand = context.getPlayer().getUsedItemHand();

		BlockEntity tile = worldIn.getBlockEntity(pos);
		TileGCBase tileGC;
		ItemStack stack = player.getItemInHand(hand);
        CompoundTag compound = stack.hasTag() ? stack.getTag() : new CompoundTag();
		
		if (tile instanceof TileGCBase)
			tileGC = (TileGCBase) tile;
		else
			return InteractionResult.FAIL;
		
		if (player.isShiftKeyDown()) {
			compound = tileGC.writeRelevant(compound);
			MEMORY_TIP.setValue(compound, WRITTEN);
			stack.setTag(compound);
			Component tempF = (tileGC.getFunction() == null) ? this.getDefaultInstance().getDisplayName() : Component.literal(tileGC.getFunction().writeToString());
			stack.setHoverName(tempF);
		} else {
			tileGC.readRelevant(compound);
			if (worldIn.isClientSide)
				GCPacketHandler.GRAPH_SYNC.sendToServer(new PacketGC(tileGC));
		}
		return InteractionResult.SUCCESS;
	}

    @Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.literal(MEMORY_TIP.getValue(stack.getOrCreateTag())));
	}
}
