package graphingcalculator3d.client.gui;

import graphingcalculator3d.common.util.math.expression.Expression.Evaluation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class BlockSpawner extends Button
{
    public final Evaluation eval;
    public final int slotNum;
    public final String evalText;
    public final GuiGC parent;
    public final int r, g, b;
    public int page;
    public boolean onPage = false;

    public BlockSpawner(int id, int x, int y, String evalText, int slots, Evaluation eval, GuiGC parent, int r, int g, int b)
    {
        super(Button.builder(Component.literal(evalText), button -> {})
                .bounds(x, y,
                        Minecraft.getInstance().font.width(evalText) + (2 * Minecraft.getInstance().font.lineHeight) + 4,
                        Minecraft.getInstance().font.lineHeight + 2));
        this.eval = eval;
        slotNum = slots;
        this.evalText = evalText;
        this.parent = parent;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public BlockSpawner setPage(int pageIn)
    {
        page = pageIn;
        return this;
    }

    public BlockSpawner setOnPage(boolean isOnPage)
    {
        onPage = isOnPage;
        return this;
    }

    public ExpressionBlock spawn(int id)
    {
        ExpressionBlock block = new ExpressionBlock(id, getX(), getY(), evalText, slotNum, eval, parent, r, g, b);
        block.visible = true;
        parent.button(block);
        block.setSpawner(this);
        block.onPage = onPage;
        block.page = page;
        return block;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
    }
}
