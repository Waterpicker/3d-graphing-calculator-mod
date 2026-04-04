package graphingcalculator3d.client.gui;

import graphingcalculator3d.common.GraphingCalculator3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextButton extends Button
{
    private final Runnable onClick;
    public int defaultWidth = 120;
    public int defaultHeight = 10;
    protected static final ResourceLocation textButton = ResourceLocation.fromNamespaceAndPath(GraphingCalculator3D.MODID, "textures/gui/text_button.png");

    public TextButton(int x, int y, int widthIn, int heightIn, String buttonText, Runnable onClick)
    {
        super(Button.builder(Component.literal(buttonText), button -> {})
                .bounds(x, y, widthIn == 0 ? 120 : widthIn, heightIn == 0 ? 10 : heightIn));
        this.onClick = onClick;
    }

    public TextButton(int x, int y, int widthIn, int heightIn, String buttonText)
    {
        this(x, y, widthIn, heightIn, buttonText, null);
    }

    @Override
    public void onPress()
    {
        if (onClick != null)
        {
            onClick.run();
        }
    }

    private int getTextureY()
    {
        int i = 1;
        if (!this.active)
        {
            i = 0;
        }
        else if (this.isHoveredOrFocused())
        {
            i = 2;
        }

        return 46 + i * 20;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        if (!this.visible)
        {
            return;
        }

        Font fontRenderer = Minecraft.getInstance().font;
        int textureY = this.getTextureY();
        boolean hovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

        guiGraphics.blit(textButton, this.getX(), this.getY(), 0, 0.0F, (float) textureY, this.width / 2, this.height, 256, 256);
        guiGraphics.blit(textButton, this.getX() + this.width / 2, this.getY(), 0, 200.0F - this.width / 2, (float) textureY, this.width / 2, this.height, 256, 256);

        int color = 90909;
        if (this.packedFGColor != 0)
        {
            color = this.packedFGColor;
        }
        else if (!this.active)
        {
            color = 10526880;
        }
        else if (hovered)
        {
            color = 15132390;
        }

        guiGraphics.drawString(fontRenderer, this.getMessage(), this.getX(), this.getY(), color, false);
    }
}
