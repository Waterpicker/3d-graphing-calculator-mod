package graphingcalculator3d.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VisibleBase
{
	public static int indent = 20;
	
	protected int xPos, yPos, width, height, splitPage, splitY;
	protected boolean visible, indented = false, breaksPage = false, errorLogged = false;
	protected int index;
	protected final Minecraft mc;
	
	//////////////////////////////Inheritables
	
	public void draw() {}
	public void load() {}
	public int getPanelWidth() { return width; }
	public int getPanelHeight() { return height; }
	
	//////////////////////////////
	
	public VisibleBase()
	{
		mc = Minecraft.getInstance();
	}
	
	//////////////////////////////Getters and setters
	
	/////////Getters
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	///////////Setters
	public void setX(int x)
	{
		xPos = x;
	}
	
	public void setY(int y)
	{
		yPos = y;
	}
	
	public void setVisible(boolean visibleIn)
	{
		visible = visibleIn;
	}
	
	public void setIndex(int ind)
	{
		index = ind;
	}
	
	public VisibleBase indented(boolean indented)
	{
		this.indented = indented;
		return this;
	}
	
	public VisibleBase breaksPage(boolean breaks)
	{
		breaksPage = breaks;
		return this;
	}
	
	//////////////////////////////
}
