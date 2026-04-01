package graphingcalculator3d.common.gameplay.items;

import java.util.ArrayList;

import graphingcalculator3d.common.GraphingCalculator3D;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.ObjectHolder;

@net.minecraftforge.registries.ObjectHolder(GraphingCalculator3D.MODID)
public class GCItems
{
	public static ArrayList<Item> gcItemList = new ArrayList<Item>();
	
	/////////////////////////////////////////////////////////////
	
	public static final ItemMemoryCard item_memory_card = null;
}
