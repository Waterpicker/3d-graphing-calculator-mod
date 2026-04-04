package graphingcalculator3d.proxy;


import graphingcalculator3d.client.FastTESRGC;
import graphingcalculator3d.client.gui.GuiGC;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.expression.Expression;
import graphingcalculator3d.common.util.nbthandler.Domain;
import graphingcalculator3d.common.util.networking.packets.PacketGC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3d;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {
	Minecraft mc = Minecraft.getInstance();


    @Override
	public void preInit() {
        BlockEntityRenderers.register(TileEntities.GC_CARTESIAN.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_SPHERICAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_SPHERICAL_THETA.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_SPHERICAL_PHI.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_CYLINDRICAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_CYLINDRICAL_H.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_CYLINDRICAL_THETA.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PARABOLIC_CYLINDRICAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PARABOLIC_CYLINDRICAL_SIGMA.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PARABOLIC_CYLINDRICAL_TAU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PARABOLIC.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PARABOLIC_TAU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PARABOLIC_PHI.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_BIPOLAR_CYLINDRICAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_BIPOLAR_CYLINDRICAL_SIGMA.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_BIPOLAR_CYLINDRICAL_TAU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_OBLATE_SPHEROIDAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_OBLATE_SPHEROIDAL_NU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_OBLATE_SPHEROIDAL_PHI.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PROLATE_SPHEROIDAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PROLATE_SPHEROIDAL_NU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_PROLATE_SPHEROIDAL_PHI.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_TOROIDAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_TOROIDAL_PHI.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_TOROIDAL_TAU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_CONICAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_CONICAL_MU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_CONICAL_NU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_6_SPHERE.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_6_SPHERE_V.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_6_SPHERE_W.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_ELLIPTIC_CYLINDRICAL.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_ELLIPTIC_CYLINDRICAL_MU.get(), FastTESRGC::new);
        BlockEntityRenderers.register(TileEntities.GC_ELLIPTIC_CYLINDRICAL_NU.get(), FastTESRGC::new);
    }

	@Override
	public void init()
	{
		
	}

    @Override
	public void sayToClient(String text, Level world) {
		if (world.isClientSide) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(text));
        }
	}

	@Override
	public void openGuiGC(Level worldIn, TileGCBase tile)
	{
		if (worldIn.isClientSide)
			mc.setScreen(new GuiGC(tile));
	}

    @Override
	public void handleGCPacket(PacketGC message, NetworkEvent.Context ctx) {
		if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT)
		{
			ClientLevel world = Minecraft.getInstance().level;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			BlockPos pos = new BlockPos(x, y, z);
			
			if (world.isLoaded(pos))
			{
				if (world.getBlockEntity(pos) instanceof TileGCBase)
				{
					ctx.enqueueWork(() ->
					{
						TileGCBase tile = (TileGCBase) world.getBlockEntity(pos);
						
						Expression function = message.function;
						String tex = message.tex;
						boolean crop = message.crop;
						int[] rgba = message.rgba;
						boolean colorSlope = message.colorSlope;
						int tileCount = message.tileCount;
						Domain domainX = message.domainX;
						Domain range = message.range;
						Domain domainZ = message.domainZ;
						Vector3d scale = message.scale;
                        Vector3d translation = message.translation;
                        Vector3d rotation = message.rotation;
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
						
						tile.setChanged();
					});
				}
			}
            ctx.setPacketHandled(true);
        } else if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
			ServerLevel world = ctx.getSender().getLevel();
			int x = message.x;
			int y = message.y;
			int z = message.z;
			BlockPos pos = new BlockPos(x, y, z);
			
			if (world.isLoaded(pos))
			{
				if (world.getBlockEntity(pos) instanceof TileGCBase)
				{
					ctx.enqueueWork(() ->
					{
						TileGCBase tile = (TileGCBase) world.getBlockEntity(pos);
						
						Expression function = message.function;
						String tex = message.tex;
						boolean crop = message.crop;
						int[] rgba = message.rgba;
						boolean colorSlope = message.colorSlope;
						int tileCount = message.tileCount;
						Domain domainX = message.domainX;
						Domain range = message.range;
						Domain domainZ = message.domainZ;
						Vector3d scale = message.scale;
                        Vector3d translation = message.translation;
                        Vector3d rotation = message.rotation;
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
						
						tile.setChanged();
					});
				}
			}
            ctx.setPacketHandled(true);
        }
	}

	@Override
	public void deleteVertexData(TileGCBase te) {
		BlockEntityRenderer<TileGCBase> tesr = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(te);
		if (tesr != null && te.hasLevel() && te.getLevel().isClientSide)
			((FastTESRGC) tesr).deleteVertexData(te);
	}
}
