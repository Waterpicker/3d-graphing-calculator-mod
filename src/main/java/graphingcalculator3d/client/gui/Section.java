package graphingcalculator3d.client.gui;

import net.minecraft.client.gui.AbstractGui;
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
        drawSection(r, g, b, 255);
    }

    public void drawSection(float r, float g, float b, float a)
    {
        int ri = Math.max(0, Math.min(255, Math.round(r)));
        int gi = Math.max(0, Math.min(255, Math.round(g)));
        int bi = Math.max(0, Math.min(255, Math.round(b)));
        int ai = Math.max(0, Math.min(255, Math.round(a)));
        int color = (ai << 24) | (ri << 16) | (gi << 8) | bi;
        AbstractGui.fill(x, y, right, down, color);
    }

    public Section resize(int newWidth, int newHeight)
    {
        return new Section(x, y, newWidth, newHeight);
    }
}
