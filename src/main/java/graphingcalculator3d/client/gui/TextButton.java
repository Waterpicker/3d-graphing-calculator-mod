package graphingcalculator3d.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import graphingcalculator3d.common.GraphingCalculator3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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

    public TextButton(int x, int y, int widthIn, int heightIn, String buttonText, Runnable onClick) {
        super(x, y, widthIn == 0 ? 120 : widthIn, heightIn == 0 ? 10 : heightIn, Component.literal(buttonText), button -> {}, (a) -> Component.empty());
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

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (!this.visible) {
            return;
        }

        Font fontRenderer = Minecraft.getInstance().font;
        Minecraft.getInstance().getTextureManager().bindForSetup(textButton);
        RenderSystem.setShaderColor( 1.0F, 1.0F, 1.0F, 1.0F);

        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
        int hoverState = this.getTextureY();

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        RenderSystem.blendFunc(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        blit(poseStack, this.getX(), this.getY(), 0, 46 + hoverState * 20, this.width / 2, this.height);
        blit(poseStack, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, 46 + hoverState * 20, this.width / 2, this.height);

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

        fontRenderer.draw(poseStack, this.getMessage(), this.getX(), this.getY(), color);
    }

    private int getTextureY() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }

        return 46 + i * 20;
    }
}
