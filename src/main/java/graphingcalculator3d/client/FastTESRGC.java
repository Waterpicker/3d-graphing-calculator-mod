package graphingcalculator3d.client;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.gameplay.tile.TileGCBase.GCPreviousState;
import graphingcalculator3d.common.util.arrays.Arrays;
import graphingcalculator3d.common.util.config.ConfigVars;
import graphingcalculator3d.common.util.events.Event;
import graphingcalculator3d.common.util.events.GCEvents;
import graphingcalculator3d.common.util.events.TriggerOn;
import graphingcalculator3d.common.util.math.Compare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

/**
 * @author SerpentDagger
 */
@OnlyIn(Dist.CLIENT)
public class FastTESRGC extends TileEntityRenderer<TileGCBase>
{
	private static boolean doRender = ConfigVars.RenderingConfigs.render;
	private static int lightScale = ConfigVars.RenderingConfigs.lightScale;
	private static int colorScale = (int) (360 * ConfigVars.RenderingConfigs.colorScale);
	
	private Vec3d[][] vArray;
	private boolean[][] disconnects;
	private double[][] slope;
	private double highestSlope;
	private int[] rgba;
	private int r, g, b, a;
	private ResourceLocation tex;
	private int tileCount;
	private double lowestF, highestF;
	private boolean doLight, doSlope, transparent;
	private int lightmap;
	private double difY;
	private double lRatio;
	private double sRatio;
	private TextureAtlasSprite sprite;
	private double uMin, uMax, vMin, vMax, stepU, stepV, u, v, u2, v2;
	private Minecraft mc = Minecraft.getInstance();
	private Vec3d vec;
	
	private int renderID = -1;
	private int renderIndex = 0;
	private int frameMax = ConfigVars.RenderingConfigs.framesPerSort;
	private int frameIndex = 0;
	private GCBufferBuilder[] buff = new GCBufferBuilder[1];
	private TileGCBase[] orderedRender = new TileGCBase[] {};
	private boolean[] sorted = new boolean[] {};
	
	public FastTESRGC() {
		super();
		MinecraftForge.EVENT_BUS.register(this);
		Event.register(this);
	}

    @Override
    public void renderTileEntityFast(TileGCBase te, double x, double y, double z, float partialTicks, int destroyStage, BufferBuilder buffer) {
		if (!doRender) {
			return;
		}

		if (te.isErrored())
			return;
		if (!te.renderReady)
			return;
		if (!te.hasWorld())
			return;
		BlockPos pos = te.getPos();
		
		renderID = te.renderID;
		vArray = te.getVertexArray();
		disconnects = te.disconnects;
		slope = te.slope;
		highestSlope = te.highestSlope;
		rgba = te.rgba;
		r = rgba[0];
		g = rgba[1];
		b = rgba[2];
		a = rgba[3];
		tex = new ResourceLocation(te.tex);
		tileCount = te.tileCount;
		lowestF = te.lowestF;
		highestF = te.highestF;
		doLight = highestF - lowestF > 0.01 && ConfigVars.RenderingConfigs.doLight;
		doSlope = te.colorSlope;
		transparent = te.shouldRenderInPass(1);
		lightmap = 200;
		difY = highestF - lowestF;
		lRatio = 1;
		
		sprite = mc.getTextureMap().getSprite(tex);
		
		uMin = sprite.getMinU();
		uMax = sprite.getMaxU();
		vMin = sprite.getMinV();
		vMax = sprite.getMaxV();
		
		stepU = (uMax - uMin) / tileCount;
		stepV = (vMax - vMin) / tileCount;
		
		u = uMin;
		v = vMin;
		u2 = uMin;
		v2 = vMin;
		
		if (te.regenGraph)
		{
			deleteVertexData(te);
			generateGraph(te);
			te.regenGraph = false;
			indexUp();
		}
		else if (orderedRender.length > 0 || !transparent)
		{
			
			Minecraft.getInstance().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.disableCull();
			GlStateManager.color3f(1f, 1f, 1f);
			GlStateManager.enableDepthTest();
			
			if (Minecraft.isAmbientOcclusionEnabled())
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
			else
				GlStateManager.shadeModel(GL11.GL_FLAT);
			
			if (!transparent)
			{
				if (!te.getWorld().isBlockPowered(pos))
				{
					GlStateManager.translated(pos.getX() - TileEntityRendererDispatcher.staticPlayerX,
							pos.getY() - TileEntityRendererDispatcher.staticPlayerY, pos.getZ() - TileEntityRendererDispatcher.staticPlayerZ);
					GlStateManager.callList(renderID);
					GlStateManager.translated(-pos.getX() + TileEntityRendererDispatcher.staticPlayerX,
							-pos.getY() + TileEntityRendererDispatcher.staticPlayerY, -pos.getZ() + TileEntityRendererDispatcher.staticPlayerZ);
				}
			}
			else
			{
				if (orderedRender.length > renderIndex && orderedRender[renderIndex] != null)
				{
					pos = orderedRender[renderIndex].getPos();
					Vec3d eyePos = Minecraft.getInstance().player.getEyePosition(partialTicks);
					if (ArrayUtils.contains(orderedRender, te)
							&& orderedRender[renderIndex].getWorld().isBlockLoaded(pos))
					{
						if (pos.distanceSq(eyePos.x, eyePos.y, eyePos.z) < TileGCBase.RENDER_DISTANCE_SQ)
						{
							if (!orderedRender[renderIndex].getWorld().isBlockPowered(pos))
							{
								GlStateManager.translated(pos.getX() - TileEntityRendererDispatcher.staticPlayerX,
										pos.getY() - TileEntityRendererDispatcher.staticPlayerY, pos.getZ() - TileEntityRendererDispatcher.staticPlayerZ);
								GCTessellator tessellator = GCTessellator.getInstance();
								GCBufferBuilder gcbuffer = buff[orderedRender[renderIndex].renderID];
								if (!sorted[renderIndex])
								{
									Vec3d cameraPos = ActiveRenderInfo.getCameraPosition();
									Vec3d translation = Minecraft.getInstance().player.getEyePosition(partialTicks);
									translation = new Vec3d(cameraPos.x + translation.x - pos.getX(), cameraPos.y + translation.y - pos.getY(),
											cameraPos.z + translation.z - pos.getZ());
									gcbuffer.setTranslation(translation.x, translation.y, translation.z);
									gcbuffer.sortVertexData((float) cameraPos.x, (float) cameraPos.y, (float) cameraPos.z);
									sorted[renderIndex] = true;
								}
								tessellator.buffer = gcbuffer;
								tessellator.draw();
								GlStateManager.translated(-pos.getX() + TileEntityRendererDispatcher.staticPlayerX,
										-pos.getY() + TileEntityRendererDispatcher.staticPlayerY, -pos.getZ() + TileEntityRendererDispatcher.staticPlayerZ);
							}
						}
						indexUp();
					}
				}
			}
			
			RenderHelper.enableStandardItemLighting();
		}
	}
	
	private void renderGraph(BufferBuilder buffer)
	{
		for (int j = 0; j + 1 < vArray.length; j++)
		{
			u = (u + stepU >= uMax - 0.000000001) ? uMin : u + stepU;
			u2 = u + stepU;
			for (int k = 0; k + 1 < vArray[j].length; k++)
			{
				v = (v + stepV >= vMax - 0.000000001) ? vMin : v + stepV;
				v2 = v + stepV;
				
				if (disconnects[j][k])
					continue;
				
				vec = vArray[j][k];
				vert(buffer, vec, u, v, j, k);
				
				vec = vArray[j + 1][k];
				vert(buffer, vec, u2, v, j, k);
				
				vec = vArray[j + 1][k + 1];
				vert(buffer, vec, u2, v2, j, k);
				
				vec = vArray[j][k + 1];
				vert(buffer, vec, u, v2, j, k);
			}
			v = vMin;
		}
		Vec3d cameraPos = Minecraft.getInstance().player.getEyePosition(1);
		buffer.sortVertexData((float) cameraPos.x, (float) cameraPos.y, (float) cameraPos.z);
	}
	
	private void generateGraph(TileGCBase te)
	{
		deleteVertexData(te);
		if (!transparent)
		{
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			
			renderID = GLAllocation.generateDisplayLists(1);
			te.renderID = renderID;
			GlStateManager.pushMatrix();
			GlStateManager.newList(renderID, 4864);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
			renderGraph(buffer);
			tessellator.draw();
			GlStateManager.endList();
			GlStateManager.popMatrix();
			
			te.prevState = GCPreviousState.GL_CALL_LIST;
		}
		else
		{
			GCTessellator tessellator = new GCTessellator(te.getArraySize());
			GCBufferBuilder buffer = tessellator.getBuffer();
			
			for (int i = 0; i < buff.length; i++)
			{
				if (buff[i] == null)
				{
					renderID = i;
					break;
				}
				else if (i == buff.length - 1)
				{
					buff = (GCBufferBuilder[]) Arrays.copyOf(buff, buff.length + 1);
					renderID = buff.length - 1;
					break;
				}
			}
			te.renderID = renderID;
			
			GlStateManager.pushMatrix();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
			renderGraph(buffer);
			tessellator.draw();
			buff[renderID] = buffer;
			GlStateManager.popMatrix();
			
			te.prevState = GCPreviousState.TESSELLATOR;
			if (!ArrayUtils.contains(orderedRender, te))
			{
				BlockPos temp = te.getPos();
				for (int i = 0; i < orderedRender.length; i++)
				{
					if (orderedRender[i].getPos().distanceSq(temp) < 0.5 * 0.5)
					{
						deleteVertexData(orderedRender[i]);
						break;
					}
				}
				orderedRender = (TileGCBase[]) Arrays.copyOf(orderedRender, orderedRender.length + 1);
				orderedRender[orderedRender.length - 1] = te;
			}
			sorted = new boolean[orderedRender.length];
		}
	}
	
	private void vert(BufferBuilder buffer, Vec3d vec, double u, double v, int j, int k)
	{
		if (doLight)
		{
			lRatio = (vec.y - lowestF) / difY;
			lightmap = (int) (200 * lRatio);
		}
		if (doSlope)
		{
			sRatio = highestSlope > 0 ? slope[j][k] / highestSlope : 0;
			Col col = new Col(r, g, b);
			Col col2 = shiftHue(col, colorScale * sRatio);
			buffer.pos(vec.x, vec.y, vec.z).color(col2.r, col2.g, col2.b, a).tex(u, v).lightmap(lightScale, lightmap).endVertex();
		}
		else
		{
			buffer.pos(vec.x, vec.y, vec.z).color(r, g, b, a).tex(u, v).lightmap(lightScale, lightmap).endVertex();
		}
	}
	
	@Override
	public boolean isGlobalRenderer(TileGCBase te)
	{
		return true;
	}
	
	private Col shiftHue(Col in, double hue)
	{
		Col out = new Col();
		double cosA = Math.cos(hue * Math.PI / 180);
		double sinA = Math.sin(hue * Math.PI / 180);
		
		double[][] matrix =
		{
				{ cosA + (1.0f - cosA) / 3.0f, 1.0f / 3.0f * (1.0f - cosA) - sqrtf(1.0f / 3.0f) * sinA,
						1.0f / 3.0f * (1.0f - cosA) + sqrtf(1.0f / 3.0f) * sinA },
				{ 1.0f / 3.0f * (1.0f - cosA) + sqrtf(1.0f / 3.0f) * sinA, cosA + 1.0f / 3.0f * (1.0f - cosA),
						1.0f / 3.0f * (1.0f - cosA) - sqrtf(1.0f / 3.0f) * sinA },
				{ 1.0f / 3.0f * (1.0f - cosA) - sqrtf(1.0f / 3.0f) * sinA, 1.0f / 3.0f * (1.0f - cosA) + sqrtf(1.0f / 3.0f) * sinA,
						cosA + 1.0f / 3.0f * (1.0f - cosA) } };
		out.r = clamp(in.r * matrix[0][0] + in.g * matrix[0][1] + in.b * matrix[0][2]);
		out.g = clamp(in.r * matrix[1][0] + in.g * matrix[1][1] + in.b * matrix[1][2]);
		out.b = clamp(in.r * matrix[2][0] + in.g * matrix[2][1] + in.b * matrix[2][2]);
		return out;
	}
	
	private class Col
	{
		public int r, g, b;
		
		public Col()
		{
		}
		
		public Col(int rIn, int gIn, int bIn)
		{
			r = rIn;
			g = gIn;
			b = bIn;
		}
	}
	
	private int clamp(double d)
	{
		int v = (int) d;
		if (v < 0)
			return 0;
		if (v > 255)
			return 255;
		return v;
	}
	
	private double sqrtf(double v)
	{
		return Math.sqrt(v);
	}
	
	public void sort()
	{
		EntityPlayerSP player = Minecraft.getInstance().player;
		if (player == null)
			return;
		Vec3d eyePos = player.getEyePosition(1);
		for (int i = 1; i < orderedRender.length; i++)
		{
			TileGCBase te1 = orderedRender[i];
			TileGCBase te2 = orderedRender[i - 1];
			Vec3d pos1 = new Vec3d(te1.getPos()).add(te1.translation.x, te1.translation.y, te1.translation.z);
			Vec3d pos2 = new Vec3d(te2.getPos()).add(te2.translation.x, te2.translation.y, te2.translation.z);
			if (Compare.distanceBetweenGreaterThanDistanceBetween(pos1, eyePos, pos2, eyePos))
			{
				orderedRender[i] = orderedRender[i - 1];
				orderedRender[i - 1] = te1;
			}
		}
		
		sorted = new boolean[orderedRender.length];
	}
	
	public void deleteVertexData(TileGCBase te) {
		if (te == null)
			return;
		// System.out.println(te.renderID);
		if (te.renderID >= 0) {
			switch (te.prevState)
			{
				case GL_CALL_LIST:
					GLAllocation.deleteDisplayLists(te.renderID);
					te.renderID = -1;
					break;
				case TESSELLATOR:
					buff[te.renderID] = null;
					te.renderID = -1;
					break;
			}
		}
		renderIndex = 0;
		orderedRender = ArrayUtils.removeAllOccurences(orderedRender, te);
	}
	
	public void deleteAllVertexData()
	{
        for (TileGCBase tileGCBase : orderedRender) {
            deleteVertexData(tileGCBase);
        }
		orderedRender = new TileGCBase[] {};
	}
	
	private void indexUp()
	{
		renderIndex++;
		if (renderIndex >= orderedRender.length)
		{
			renderIndex = 0;
		}
	}
	
	@SubscribeEvent
	public void frameUp(RenderTickEvent event) {
		if (event.phase == Phase.START)
			return;
		frameIndex++;
		if (frameIndex >= frameMax)
		{
			frameIndex = 0;
			renderIndex = 0;
			sort();
		}
	}
	
	@SubscribeEvent
	public void unload(Unload event)
	{
		deleteAllVertexData();
	}
	
	@SubscribeEvent
	public void load(Load event)
	{
		deleteAllVertexData();
	}
	
	@TriggerOn(GCEvents.GC_CONFIG)
	public void updateConfigs()
	{
		doRender = ConfigVars.RenderingConfigs.render;
		frameMax = ConfigVars.RenderingConfigs.framesPerSort;
		lightScale = ConfigVars.RenderingConfigs.lightScale;
		colorScale = (int) (360 * ConfigVars.RenderingConfigs.colorScale);
	}
}