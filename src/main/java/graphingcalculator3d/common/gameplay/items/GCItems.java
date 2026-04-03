package graphingcalculator3d.common.gameplay.items;

import graphingcalculator3d.common.GraphingCalculator3D;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

public class GCItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, GraphingCalculator3D.MODID);

    private static final Item.Properties NORMAL_ITEM = new Item.Properties().tab(GraphingCalculator3D.GC_TAB_MAIN).stacksTo(64);
    private static final Item.Properties BLOCK_ITEM = new Item.Properties().tab(GraphingCalculator3D.GC_TAB_MAIN).stacksTo(1);

	public static final RegistryObject<ItemMemoryCard> item_memory_card = registerNoStack("item_memory_card", ItemMemoryCard::new);

    public static <T extends Item> RegistryObject<T> registerNoStack(String name, Function<Item.Properties, T> function) {
        return register(name, function, NORMAL_ITEM);
    }

    public static RegistryObject<BlockItem> registerBlock(String name, RegistryObject<Block> block) {
        return register(name, properties -> new BlockItem(block.get(), properties), BLOCK_ITEM);
    }

    public static <T extends Item> RegistryObject<T> register(String name, Function<Item.Properties, T> function, Item.Properties properties) {
        return REGISTER.register(name, () -> function.apply(properties));
    }

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }
}
