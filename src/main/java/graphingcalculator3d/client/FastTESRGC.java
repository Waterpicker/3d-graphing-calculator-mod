package graphingcalculator3d.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.config.ConfigVars;
import graphingcalculator3d.common.util.events.GCEvents;
import graphingcalculator3d.common.util.events.TriggerOn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3d;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class FastTESRGC implements BlockEntityRenderer<TileGCBase> {
    private static boolean doRender = ConfigVars.RenderingConfigs.render;
    private static int lightScale = ConfigVars.RenderingConfigs.lightScale;
    private static int colorScale = (int) (360 * ConfigVars.RenderingConfigs.colorScale);

    private final Minecraft mc = Minecraft.getInstance();

    private Vector3d[][] vArray;
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
    private double uMin, uMax, vMin, vMax, stepU, stepV, u, v, u2, v2;
    private Vector3d vec;

    private static final Map<ResourceLocation, RenderType> OPAQUE_TYPES = new HashMap<>();
    private static final Map<ResourceLocation, RenderType> TRANSLUCENT_TYPES = new HashMap<>();

    public FastTESRGC(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(TileGCBase te, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (!doRender || te == null)
            return;
        if (te.isErrored())
            return;
        if (!te.renderReady)
            return;
        if (!te.hasLevel())
            return;

        BlockPos pos = te.getBlockPos();
        if (te.getLevel() == null || te.getLevel().hasNeighborSignal(pos))
            return;

        var renderInfo = mc.gameRenderer.getMainCamera();
        Vec3 cameraPos = renderInfo.getPosition();

        if (pos.distToCenterSqr(cameraPos.subtract(te.translation.x, te.translation.y, te.translation.z)) >= TileGCBase.RENDER_DISTANCE_SQ) {
            return;
        }

        vArray = te.getVertexArray();
        if (vArray == null || vArray.length < 2 || vArray[0] == null || vArray[0].length < 2)
            return;

        disconnects = te.disconnects;
        slope = te.slope;
        highestSlope = te.highestSlope;
        rgba = te.rgba;
        r = rgba[0];
        g = rgba[1];
        b = rgba[2];
        a = rgba[3];

        tex = toDirectTexture(ResourceLocation.parse(te.tex));

        tileCount = Math.max(1, te.tileCount);
        lowestF = te.lowestF;
        highestF = te.highestF;
        doLight = highestF - lowestF > 0.01D && ConfigVars.RenderingConfigs.doLight;
        doSlope = te.colorSlope;
        transparent = isTransparent(te);
        lightmap = 200;
        difY = highestF - lowestF;
        lRatio = 1.0D;

        uMin = 0.0D;
        uMax = 1.0D;
        vMin = 0.0D;
        vMax = 1.0D;

        stepU = (uMax - uMin) / tileCount;
        stepV = (vMax - vMin) / tileCount;

        u = uMin;
        v = vMin;
        u2 = uMin;
        v2 = vMin;

        if (te.regenGraph)
            te.regenGraph = false;

        RenderType type = transparent
                ? TRANSLUCENT_TYPES.computeIfAbsent(tex, RenderType::entityTranslucent)
                : OPAQUE_TYPES.computeIfAbsent(tex, RenderType::entityCutoutNoCull);

        var builder = bufferIn.getBuffer(type);

        matrixStack.pushPose();
        renderGraph(builder, matrixStack, combinedOverlayIn);
        matrixStack.popPose();
    }

    private static ResourceLocation toDirectTexture(ResourceLocation rl)
    {
        String namespace = rl.getNamespace();
        String path = rl.getPath();

        if (path.startsWith("textures/") && path.endsWith(".png"))
            return rl;

        if (!path.startsWith("textures/"))
            path = "textures/" + path;

        if (!path.endsWith(".png"))
            path = path + ".png";

        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    private boolean isTransparent(TileGCBase te)
    {
        if (rgba == null || rgba.length < 5)
            return a < 255;

        switch (rgba[4])
        {
            case 1:
                return false;
            case 2:
                return true;
            case 0:
            default:
                return a < 255;
        }
    }

    private void renderGraph(VertexConsumer buffer, PoseStack matrixStack, int combinedOverlayIn)
    {
        for (int j = 0; j + 1 < vArray.length; j++)
        {
            u = (u + stepU >= uMax - 1.0E-9D) ? uMin : u + stepU;
            u2 = u + stepU;

            for (int k = 0; k + 1 < vArray[j].length; k++)
            {
                v = (v + stepV >= vMax - 1.0E-9D) ? vMin : v + stepV;
                v2 = v + stepV;

                if (disconnects != null && disconnects[j][k])
                    continue;

                vec = vArray[j][k];
                vert(buffer, matrixStack, vec, (float) u, (float) v, j, k, combinedOverlayIn);

                vec = vArray[j + 1][k];
                vert(buffer, matrixStack, vec, (float) u2, (float) v, j, k, combinedOverlayIn);

                vec = vArray[j + 1][k + 1];
                vert(buffer, matrixStack, vec, (float) u2, (float) v2, j, k, combinedOverlayIn);

                vec = vArray[j][k + 1];
                vert(buffer, matrixStack, vec, (float) u, (float) v2, j, k, combinedOverlayIn);
            }

            v = vMin;
        }
    }

    private void vert(VertexConsumer buffer, PoseStack matrixStack, Vector3d vec, float u, float v, int j, int k, int combinedOverlayIn)
    {
        if (vec == null)
            return;

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

            buffer.vertex(matrixStack.last().pose(), (float) vec.x, (float) vec.y, (float) vec.z)
                    .color(col2.r, col2.g, col2.b, a)
                    .uv(u, v)
                    .overlayCoords(combinedOverlayIn)
                    .uv2(lightScale, lightmap)
                    .normal(matrixStack.last().normal(), 0.0F, 1.0F, 0.0F)
                    .endVertex();
        }
        else
        {
            buffer.vertex(matrixStack.last().pose(), (float) vec.x, (float) vec.y, (float) vec.z)
                    .color(r, g, b, a)
                    .uv(u, v)
                    .overlayCoords(combinedOverlayIn)
                    .uv2(lightScale, lightmap)
                    .normal(matrixStack.last().normal(), 0.0F, 1.0F, 0.0F)
                    .endVertex();
        }
    }

    private Col shiftHue(Col in, double hue)
    {
        Col out = new Col();
        double cosA = Math.cos(hue * Math.PI / 180.0D);
        double sinA = Math.sin(hue * Math.PI / 180.0D);

        double[][] matrix =
                {
                        {
                                cosA + (1.0D - cosA) / 3.0D,
                                (1.0D / 3.0D) * (1.0D - cosA) - sqrtf(1.0D / 3.0D) * sinA,
                                (1.0D / 3.0D) * (1.0D - cosA) + sqrtf(1.0D / 3.0D) * sinA
                        },
                        {
                                (1.0D / 3.0D) * (1.0D - cosA) + sqrtf(1.0D / 3.0D) * sinA,
                                cosA + (1.0D / 3.0D) * (1.0D - cosA),
                                (1.0D / 3.0D) * (1.0D - cosA) - sqrtf(1.0D / 3.0D) * sinA
                        },
                        {
                                (1.0D / 3.0D) * (1.0D - cosA) - sqrtf(1.0D / 3.0D) * sinA,
                                (1.0D / 3.0D) * (1.0D - cosA) + sqrtf(1.0D / 3.0D) * sinA,
                                cosA + (1.0D / 3.0D) * (1.0D - cosA)
                        }
                };

        out.r = clamp(in.r * matrix[0][0] + in.g * matrix[0][1] + in.b * matrix[0][2]);
        out.g = clamp(in.r * matrix[1][0] + in.g * matrix[1][1] + in.b * matrix[1][2]);
        out.b = clamp(in.r * matrix[2][0] + in.g * matrix[2][1] + in.b * matrix[2][2]);

        return out;
    }

    private static class Col
    {
        public int r;
        public int g;
        public int b;

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

    public void deleteVertexData(TileGCBase te)
    {
    }

    public void deleteAllVertexData()
    {
    }

    @TriggerOn(GCEvents.GC_CONFIG)
    public void updateConfigs()
    {
        doRender = ConfigVars.RenderingConfigs.render;
        lightScale = ConfigVars.RenderingConfigs.lightScale;
        colorScale = (int) (360 * ConfigVars.RenderingConfigs.colorScale);
    }
}