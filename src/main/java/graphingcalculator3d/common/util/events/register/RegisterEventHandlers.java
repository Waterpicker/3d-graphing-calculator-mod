package graphingcalculator3d.common.util.events.register;

import graphingcalculator3d.common.gameplay.blocks.GCBlocks;
import graphingcalculator3d.common.gameplay.items.GCItems;
import net.minecraftforge.eventbus.api.IEventBus;

public class RegisterEventHandlers {
    public static void register(IEventBus bus) {
        TileEntities.register(bus);
        GCBlocks.register(bus);
        GCItems.register(bus);
    }
}
