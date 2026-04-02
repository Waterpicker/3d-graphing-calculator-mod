package graphingcalculator3d.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Section
{
	public final int x, y, width, height, right, down;
	
	public Section(int xIn, int yIn, int widthIn, int heightIn)
	{
		x = xIn;
		y = yIn;
		width = widthIn;
		height = heightIn;
		right = x + width;
		down = y + height;
	}
	
	public boolean isPointWithinIncl(double pointX, double pointY)
	{
		return pointX >= x && pointX <= right && pointY >= y && pointY <= down;
	}
	
	public boolean isPointWithinExcl(int pointX, int pointY)
	{
		return pointX > x && pointX < right && pointY > y && pointY < down;
	}
	
	public void drawSection(float r, float g, float b)
	{
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("graphingcalculator3d:textures/blocks/block_mesh_flat.png"));
		r /= 255;
		g /= 255;
		b /= 255;
		GlStateManager.color3f(r, g, b);
		Gui.drawScaledCustomSizeModalRect(x, y, 0, 0, 5, 5, width, height, width, height);
	}
	
	public void drawSection(float r, float g, float b, float a)
	{
		Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("graphingcalculator3d:textures/blocks/block_mesh_flat.png"));
		r /= 255;
		g /= 255;
		b /= 255;
		a /= 255;
		GlStateManager.enableAlphaTest();
		GlStateManager.color4f(r, g, b, a);
		Gui.drawScaledCustomSizeModalRect(x, y, 0, 0, 5, 5, width, height, width, height);
		GlStateManager.disableAlphaTest();
	}
	
	public Section resize(int newWidth, int newHeight)
	{
		return new Section(x, y, newWidth, newHeight);
	}
}
