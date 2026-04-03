package graphingcalculator3d.client.gui;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VisibleImage extends VisibleBase
{
    public static final int INVALID_VALUE = -10;

    private AbstractGui parentGui;

    private int tX = INVALID_VALUE, tY = INVALID_VALUE, panelWidth = INVALID_VALUE, panelHeight = INVALID_VALUE;
    private ResourceLocation image;

    public VisibleImage()
    {
        super();
        visible = true;
        width = INVALID_VALUE;
        height = INVALID_VALUE;
    }

    public VisibleImage(AbstractGui parent)
    {
        this();
        parentGui = parent;
    }

    public VisibleImage(int index)
    {
        this();
        setIndex(index);
    }

    public VisibleImage(ResourceLocation imageResource, int index)
    {
        this(index);
        image = imageResource;
    }

    public VisibleImage(ResourceLocation imageResource, int index, int widthHeight)
    {
        this(imageResource, index);
        panelWidth = widthHeight;
        panelHeight = widthHeight;
    }

    public VisibleImage(ResourceLocation imageResource, int index, int width, int height)
    {
        this(imageResource, index);
        panelWidth = width;
        panelHeight = height;
    }

    public VisibleImage(int x, int y)
    {
        this();
        xPos = x;
        yPos = y;
    }

    public VisibleImage(int x, int y, ResourceLocation imageResource)
    {
        this(x, y);
        image = imageResource;
    }

    public VisibleImage(int x, int y, ResourceLocation imageResource, boolean visibleIn)
    {
        this(x, y, imageResource);
        visible = visibleIn;
    }

    public VisibleImage(int x, int y, ResourceLocation imageResource, boolean visibleIn, int index)
    {
        this(x, y, imageResource, visibleIn);
        setIndex(index);
    }

    public VisibleImage(int x, int y, ResourceLocation imageResource, boolean visibleIn, int widthIn, int heightIn)
    {
        this(x, y, imageResource, visibleIn);
        panelWidth = widthIn;
        panelHeight = heightIn;
    }

    public VisibleImage(int x, int y, ResourceLocation imageResource, boolean visibleIn, int widthIn, int heightIn, int textureX, int textureY)
    {
        this(x, y, imageResource, visibleIn, widthIn, heightIn);
        tX = textureX;
        tY = textureY;
    }

    @Override
    public void draw()
    {
        if (!visible) { return; }
        if (image != null)
        {
            mc.getTextureManager().bindTexture(image);

            if (width == INVALID_VALUE)
            {
                width = 256;
                height = 256;
            }
            if (tX == INVALID_VALUE)
            {
                tX = 0;
                tY = 0;
            }
            if (panelWidth == INVALID_VALUE)
            {
                panelWidth = width;
                panelHeight = height;
            }

            AbstractGui.blit(xPos, yPos, 0, (float) tX, (float) tY, panelWidth, panelHeight, width, height);
        }
        else if (!errorLogged)
        {
            System.out.println("ERROR: Image is null in VisibleImage object: " + this.toString() + "  Draw failed.");
            errorLogged = true;
        }
    }

    public ResourceLocation getImage()
    {
        return image;
    }

    public int getTextureX()
    {
        return tX;
    }

    public int getTextureY()
    {
        return tY;
    }

    public AbstractGui getParent()
    {
        return parentGui;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    public int getPanelWidth()
    {
        return panelWidth;
    }

    @Override
    public int getPanelHeight()
    {
        return panelHeight;
    }

    public void setImage(ResourceLocation imageResource)
    {
        image = imageResource;
    }

    public void setTextureX(int textureX)
    {
        tX = textureX;
    }

    public void setTextureY(int textureY)
    {
        tY = textureY;
    }

    public void setParent(AbstractGui parent)
    {
        parentGui = parent;
    }

    public void setWidth(int wdth)
    {
        width = wdth;
    }

    public void setHeight(int hght)
    {
        height = hght;
    }

    @Override
    public VisibleImage indented(boolean indented)
    {
        return (VisibleImage) super.indented(indented);
    }

    @Override
    public VisibleImage breaksPage(boolean breaks)
    {
        return (VisibleImage) super.breaksPage(breaks);
    }
}
