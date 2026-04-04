package graphingcalculator3d.common.gameplay.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class GCCreativeTab {
    public static final CreativeModeTab GC_TAB_MAIN = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).displayItems((p_270258_, p_259752_) -> p_259752_.acceptAll(GCItems.REGISTER.getEntries().stream().map(RegistryObject::get).map(Item::getDefaultInstance).toList())).build();

    public static void register(IEventBus bus) {}
}
