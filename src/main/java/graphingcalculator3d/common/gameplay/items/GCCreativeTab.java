package graphingcalculator3d.common.gameplay.items;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.blocks.GCBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GCCreativeTab {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), GraphingCalculator3D.MODID);

    public static final CreativeModeTab GC_TAB_MAIN =
            CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.literal("3d Graphing Calculator"))
                    .icon(() -> GCItems.REGISTER.getEntries().stream().map(RegistryObject::get).map(Item::getDefaultInstance).toList().get(1))
                    .displayItems((p_270258_, p_259752_)
                            -> p_259752_.acceptAll(GCItems.REGISTER.getEntries().stream().map(RegistryObject::get).map(Item::getDefaultInstance).toList()))
                    .build();

    public static final RegistryObject<CreativeModeTab> GC_TAB = TABS.register("main", () -> GC_TAB_MAIN);

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
