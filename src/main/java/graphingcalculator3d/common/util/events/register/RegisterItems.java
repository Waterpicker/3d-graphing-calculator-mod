package graphingcalculator3d.common.util.events.register;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.blocks.GCBlocks;
import graphingcalculator3d.common.gameplay.items.GCItems;
import graphingcalculator3d.common.gameplay.items.ItemMemoryCard;
import graphingcalculator3d.common.gameplay.items.SetupItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisterItems
{
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		event.getRegistry().registerAll
		(
				SetupItem.basicNoStackItem(properties -> new ItemMemoryCard(properties), "item_memory_card")
		);
		
		for (Block current : GCBlocks.gcBlockList)
		{
			event.getRegistry().register(SetupItem.basicItemBlock(current));
		}
		
		for (Item current : event.getRegistry().getValues())
		{
			if (current.getRegistryName().getNamespace().equals(GraphingCalculator3D.MODID)) {
				GCItems.gcItemList.add(current);
			}
		}
		
		System.out.println("GraphingCalculator3D items registered.");
	}
}
