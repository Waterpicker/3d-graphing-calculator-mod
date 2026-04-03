package graphingcalculator3d.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import graphingcalculator3d.common.GraphingCalculator3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextButton extends Button
{
    private final Runnable onClick;
    public int defaultWidth = 120;
    public int defaultHeight = 10;
    protected static final ResourceLocation textButton = new ResourceLocation(GraphingCalculator3D.MODID + ":textures/gui/text_button.png");

    public TextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Runnable onClick)
    {
        super(x, y, widthIn == 0 ? 120 : widthIn, heightIn == 0 ? 10 : heightIn, buttonText, button -> {});
        this.onClick = onClick;
    }

    public TextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        this(buttonId, x, y, widthIn, heightIn, buttonText, null);
    }

    @Override
    public void onPress()
    {
        if (onClick != null)
        {
            onClick.run();
        }
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks)
    {
        if (!this.visible)
        {
            return;
        }

        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        Minecraft.getInstance().getTextureManager().bindTexture(textButton);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        int hoverState = this.getYImage(this.isHovered);

        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        this.blit(this.x, this.y, 0, 46 + hoverState * 20, this.width / 2, this.height);
        this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + hoverState * 20, this.width / 2, this.height);

        int color = 90909;
        if (this.packedFGColor != 0)
        {
            color = this.packedFGColor;
        }
        else if (!this.active)
        {
            color = 10526880;
        }
        else if (this.isHovered)
        {
            color = 15132390;
        }

        fontRenderer.drawString(this.getMessage(), this.x, this.y, color);
    }
}
