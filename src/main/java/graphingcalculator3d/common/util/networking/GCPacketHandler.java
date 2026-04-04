package graphingcalculator3d.common.util.networking;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.util.networking.packets.PacketGC;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class GCPacketHandler {
    private static final String PROTOCOL = "0";

    public static final SimpleChannel GRAPH_SYNC = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(GraphingCalculator3D.MODID, "gc_3d_graph_sync"),
            () -> PROTOCOL,
            PROTOCOL::equals, PROTOCOL::equals
    );
	
	///////////////////////
	
	static int dsc = 0;
	public static void registerPackets() {
        GRAPH_SYNC.registerMessage(dsc++, PacketGC.class, PacketGC::toBytes, PacketGC::new, PacketGC::handle);

//		GRAPH_SYNC.registerMessage(dsc++, PacketGC.class, , Side.CLIENT);
//		GRAPH_SYNC.registerMessage(dsc++, PacketGC.class, , Side.SERVER);
	}
}
