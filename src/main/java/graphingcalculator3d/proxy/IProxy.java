package graphingcalculator3d.proxy;

import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.networking.packets.PacketGC;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkEvent;

public interface IProxy
{
	public void preInit();
	public void init();

    public void handleGCPacket(PacketGC message, NetworkEvent.Context ctx);
	
	public void sayToClient(String text, Level world);

    public void openGuiGC(Level worldIn, TileGCBase tile);
	public void deleteVertexData(TileGCBase te);

    default void onConstruct(IEventBus bus) {}
}
