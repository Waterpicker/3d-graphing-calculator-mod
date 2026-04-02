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

    public int defaultWidth = 120;
	public int defaultHeight = 10;
	protected static final ResourceLocation textButton = new ResourceLocation(GraphingCalculator3D.MODID + ":textures/gui/text_button.png");
	
	/**
	 * @param buttonId
	 * @param x
	 * @param y
	 * @param widthIn
	 * @param heightIn
	 * @param buttonText
	 */
	public TextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Runnable onClick) {
		super(buttonId, x, y, widthIn, buttonText, button -> onClick.run());
        if (widthIn == 0) { width = defaultWidth; }
		if (heightIn == 0) { height = defaultHeight; }
	}

    public TextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this(buttonId, x, y, widthIn, heightIn, buttonText, null);
    }

//    @Override
//    public void onClick(double p_194829_1_, double p_194829_3_) {
//        if(onClick != null) onClick.run();
//    }

    @Override
	public void render(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
            Minecraft.getInstance().getTextureManager().bindTexture(textButton);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.active = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getYImage(this.active);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.blit(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
            this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
//            this.mouseDragged(mc, mouseX, mouseY);
            int j = 90909;

            if (packedFGColor != 0)
            {
                j = packedFGColor;
            }
            else
            if (!this.active)
            {
                j = 10526880;
            }
            else if (this.active)
            {
                j = 15132390;
            }

            fontrenderer.drawString(this.getMessage(), x, y, j);
        }
    }

	
}
