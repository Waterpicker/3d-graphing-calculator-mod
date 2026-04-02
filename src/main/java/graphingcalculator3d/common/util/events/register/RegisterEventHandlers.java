package graphingcalculator3d.common.util.events.register;

import graphingcalculator3d.common.gameplay.blocks.GCBlocks;
import graphingcalculator3d.common.gameplay.items.GCItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Consumer;

public class RegisterEventHandlers {
    public static void register(IEventBus bus) {
        bus.<RegistryEvent.Register<Block>, Block>addGenericListener(Block.class, GCBlocks::register);
        bus.<RegistryEvent.Register<Item>, Item>addGenericListener(Item.class, GCItems::register);
        bus.addGenericListener(TileEntityType.class, TileEntities::register);
    }
}
