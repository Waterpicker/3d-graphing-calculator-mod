package graphingcalculator3d.proxy;


import graphingcalculator3d.client.FastTESRGC;
import graphingcalculator3d.client.gui.GuiGC;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.expression.Expression;
import graphingcalculator3d.common.util.nbthandler.Domain;
import graphingcalculator3d.common.util.networking.packets.PacketGC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

@OnlyIn(Dist.CLIENT)
public class ClientProxy implements IProxy {
	Minecraft mc = Minecraft.getInstance();
	TextureAtlasSprite sprite;

	@Override
	public void preInit() {
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_CARTESIAN.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_SPHERICAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_SPHERICAL_THETA.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_SPHERICAL_PHI.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_CYLINDRICAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_CYLINDRICAL_H.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_CYLINDRICAL_THETA.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PARABOLIC_CYLINDRICAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PARABOLIC_CYLINDRICAL_SIGMA.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PARABOLIC_CYLINDRICAL_TAU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PARABOLIC.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PARABOLIC_TAU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PARABOLIC_PHI.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_BIPOLAR_CYLINDRICAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_BIPOLAR_CYLINDRICAL_SIGMA.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_BIPOLAR_CYLINDRICAL_TAU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_OBLATE_SPHEROIDAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_OBLATE_SPHEROIDAL_NU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_OBLATE_SPHEROIDAL_PHI.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PROLATE_SPHEROIDAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PROLATE_SPHEROIDAL_NU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_PROLATE_SPHEROIDAL_PHI.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_TOROIDAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_TOROIDAL_PHI.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_TOROIDAL_TAU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_CONICAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_CONICAL_MU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_CONICAL_NU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_6_SPHERE.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_6_SPHERE_V.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_6_SPHERE_W.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_ELLIPTIC_CYLINDRICAL.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_ELLIPTIC_CYLINDRICAL_MU.get(), FastTESRGC::new);
        ClientRegistry.bindTileEntityRenderer(TileEntities.GC_ELLIPTIC_CYLINDRICAL_NU.get(), FastTESRGC::new);
    }

	@Override
	public void init()
	{
		
	}

    @Override
	public void sayToClient(String text, World world)
	{
		if (world.isRemote) { Minecraft.getInstance().player.sendMessage(new StringTextComponent(text)); }
	}

	@Override
	public void openGuiGC(World worldIn, TileGCBase tile)
	{
		if (worldIn.isRemote)
			mc.displayGuiScreen(new GuiGC(tile));
	}

	@Override
	public double[] getUV(ResourceLocation tex)
	{
		mc.getTextureManager().bindTexture(tex);
		sprite = mc.getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(tex);
		
		double uMin = sprite.getMinU();
		double uMax = sprite.getMaxU();
		double vMin = sprite.getMinV();
		double vMax = sprite.getMaxV();

        return new double[] { uMin, uMax, vMin, vMax };
	}

	@Override
	public void handleGCPacket(PacketGC message, NetworkEvent.Context ctx) {
		if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT)
		{
			ClientWorld world = Minecraft.getInstance().world;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			BlockPos pos = new BlockPos(x, y, z);
			
			if (world.isBlockLoaded(pos))
			{
				if (world.getTileEntity(pos) instanceof TileGCBase)
				{
					ctx.enqueueWork(() ->
					{
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
				}
			}
            ctx.setPacketHandled(true);
        } else if (ctx.getDirection() == NetworkDirection.PLAY_TO_SERVER) {
			ServerWorld world = ctx.getSender().getServerWorld();
			int x = message.x;
			int y = message.y;
			int z = message.z;
			BlockPos pos = new BlockPos(x, y, z);
			
			if (world.isBlockLoaded(pos))
			{
				if (world.getTileEntity(pos) instanceof TileGCBase)
				{
					ctx.enqueueWork(() ->
					{
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
				}
			}
            ctx.setPacketHandled(true);
        }
	}

	@Override
	public void deleteVertexData(TileGCBase te) {
		TileEntityRenderer<TileGCBase> tesr = TileEntityRendererDispatcher.instance.getRenderer(te);
		if (tesr != null && te.hasWorld() && te.getWorld().isRemote)
			((FastTESRGC) tesr).deleteVertexData(te);
	}
}
