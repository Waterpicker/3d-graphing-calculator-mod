package graphingcalculator3d.proxy;

import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.math.expression.Expression;
import graphingcalculator3d.common.util.nbthandler.Domain;
import graphingcalculator3d.common.util.networking.GCPacketHandler;
import graphingcalculator3d.common.util.networking.packets.PacketGC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerProxy implements IProxy
{
	//////////////////////////////Initialization
	@Override
	public void preInit()
	{
		
	}

	@Override
	public void init()
	{
		
	}

    @Override
	public void handleGCPacket(PacketGC message, NetworkEvent.Context ctx) {
		if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
			ServerWorld world = ctx.getSender().getServerWorld();
			int x = message.x;
			int y = message.y;
			int z = message.z;
			BlockPos pos = new BlockPos(x, y, z);
			
			if (world.isBlockLoaded(pos)) {
				if (world.getTileEntity(pos) instanceof TileGCBase) {
					ctx.enqueueWork(() -> {
						TileGCBase tile = (TileGCBase) world.getTileEntity(pos);
						
						Expression function = message.function;
						String tex = message.tex;
						boolean crop = message.crop;
						int[] rgba = message.rgba;
						boolean colorSlope = message.colorSlope;
						int tileCount = message.tileCount;
						Domain domainX = message.domainX;
						Domain range = message.range;
						Domain domainZ = message.domainZ;
						Vec3d scale = message.scale;
						Vec3d translation = message.translation;
						Vec3d rotation = message.rotation;
						double resolution = message.resolution;
						double discThresh = message.discThresh;
						double aggDiscThresh = message.aggDiscThresh;
						boolean collision = message.collision;
						
						tile.setErrored(false);
						tile.renderReady = false;
						
						tile.setFunction(function);
						tile.tex = tex;
						
						tile.setResolution(resolution);
						tile.tileCount = tileCount;
						
						tile.domainA = domainX;
						tile.range = range;
						tile.domainB = domainZ;
						
						tile.scale = scale;
						tile.translation = translation;
						tile.rotation = rotation;
						
						tile.rgba = rgba;
						tile.colorSlope = colorSlope;
						
						tile.cropToRange(crop);
						tile.setDiscThresh(discThresh);
						tile.setAggDiscThresh(aggDiscThresh);
						
						tile.collision = collision;
						
						tile.genMesh();
						
						tile.markDirty();
					});

                    GCPacketHandler.GRAPH_SYNC.send(PacketDistributor.DIMENSION.with(() -> world.getDimension().getType()), message);
				}
			}

            ctx.setPacketHandled(true);
		}
	}
	
	////////////////////////////Client-only methods
	
	@Override
	public void sayToClient(String text, World world) {}

	@Override
	public double[] getUV(ResourceLocation tex)
	{
		return null;
	}

	@Override
	public void openGuiGC(World worldIn, TileGCBase tile) {}

	@Override
	public void deleteVertexData(TileGCBase te) {}

}
