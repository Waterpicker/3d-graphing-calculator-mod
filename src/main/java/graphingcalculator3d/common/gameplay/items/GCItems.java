package graphingcalculator3d.common.gameplay.items;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import graphingcalculator3d.common.GraphingCalculator3D;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class GCItems {
    private static final Item.Properties NORMAL_ITEM = new Item.Properties().group(GraphingCalculator3D.GC_TAB_MAIN).maxStackSize(64);
    private static final Item.Properties BLOCK_ITEM = new Item.Properties().group(GraphingCalculator3D.GC_TAB_MAIN).maxStackSize(1);

    public static final List<Item> ITEMS = new ArrayList<>();
	
	public static final ItemMemoryCard item_memory_card = registerNoStack("item_memory_card", ItemMemoryCard::new);

    public static <T extends Item> T registerNoStack(String name, Function<Item.Properties, T> function) {
        return register(name, function, NORMAL_ITEM);
    }

    public static ItemBlock registerBlock(String name, Block block) {
        return register(name, properties -> new ItemBlock(block, properties), BLOCK_ITEM);
    }

    public static <T extends Item> T register(String name, Function<Item.Properties, T> function, Item.Properties properties) {
        T item = function.apply(properties);
        item.setRegistryName(GraphingCalculator3D.id(name));
        ITEMS.add(item);
        return item;
    }

    public static void register(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        ITEMS.forEach(registry::register);
    }
}
