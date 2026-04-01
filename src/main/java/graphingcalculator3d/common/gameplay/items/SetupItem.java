package graphingcalculator3d.common.gameplay.items;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;

import java.util.function.Function;

public class SetupItem
{
public static final ItemGroup DEFAULT_TAB = GraphingCalculator3D.GC_TAB_MAIN;
	
	public static Item basicItem(Item item, String name)
	{
		setNameAndTab(item, name);
		item.setMaxStackSize(64);
		generateDefaultJsons(name);
		
		return item;
	}
	
	public static Item basicItem(String name)
	{
		Item item = new Item();
		return basicItem(item, name);
	}
	
	public static Item basicNoStackItem(Function<Item.Properties, Item> item, String name) {
        Item.Properties properties = new Item.Properties().group(DEFAULT_TAB).maxStackSize(1);

        v

		setNameAndTab(item, name);
		item.setMaxStackSize(1);
		generateDefaultJsons(name);
		
		return item;
	}
	
	public static ItemBlock basicItemBlock(Block block)
	{
		ItemBlock itemBlock = new ItemBlock(block);
		setName(itemBlock);
		
		itemBlock.setMaxStackSize(64);
		return itemBlock;
	}
	
	public static Item stoneSword(Item item, String name)
	{
		setNameAndTab(item, name);
		item.setMaxStackSize(1).setMaxDamage(300);
		return item;
	}
	
	public static Item damageableNoStackItem(Item item, String name, int maxDamage)
	{
		basicNoStackItem(item, name);
		item.setMaxDamage(maxDamage);
		
		return item;
	}
	
	public static Item setName(Item item, String name)
	{
		return item.setRegistryName(name).setUnlocalizedName(item.getRegistryName().toString());
	}
	
	public static ItemBlock setName(ItemBlock item)
	{
		item.setRegistryName(item.getBlock().getRegistryName()).setUnlocalizedName(item.getRegistryName().toString());
		return item;
	}
	
	public static void generateDefaultJsons(String name)
	{
		Utilities.generateJson(name, "models\\item", "item_simple", name, null, null);
	}
	
	public static Item setNameAndTab(Item item, String name)
	{
		item.add(DEFAULT_TAB);
		return setName(item, name);
	}
}
