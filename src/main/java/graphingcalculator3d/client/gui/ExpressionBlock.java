package graphingcalculator3d.client.gui;

import graphingcalculator3d.common.util.math.expression.EvalInfo;
import graphingcalculator3d.common.util.math.expression.Evaluations;
import graphingcalculator3d.common.util.math.expression.Expression;
import graphingcalculator3d.common.util.math.expression.Expression.Evaluation;
import graphingcalculator3d.common.util.math.expression.Expression.Series;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExpressionBlock extends Button
{
	public static int defaultSlotSize;
	
	public Section[] slots;
	public Section whole;
	public boolean dragged = false;
	public boolean slotted = false;
	public boolean moved = false;
	public boolean onPage;
	public int page;
	public Expression expression;
	public ExpressionBlock[] innerExpressions;
	private Minecraft mc;
	private FontRenderer fontRenderer;
	private final GuiGC parent;
	private BlockSpawner spawner;
	public final int r, g, b;
	public int gennedID;
		
	public ExpressionBlock(int id, int x, int y, String evalText, int slots, Evaluation eval, GuiGC parent, int r, int g, int b)
	{
		super(id, x, y, Minecraft.getInstance().fontRenderer.getStringWidth(evalText) + (slots * Minecraft.getInstance().fontRenderer.FONT_HEIGHT) + 4
				+ (slots * 2), evalText, a -> {});
		this.parent = parent;
		mc = Minecraft.getInstance();
		fontRenderer = mc.fontRenderer;
		defaultSlotSize = fontRenderer.FONT_HEIGHT;
		expression = new Expression(eval, slots);
		innerExpressions = new ExpressionBlock[slots];
		this.slots = new Section[slots];
		arrange(x, y);
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public ExpressionBlock(int id, int x, int y, String evalText, int slots, Evaluation eval, GuiGC parent, int[] rgb)
	{
		this(id, x, y, evalText, slots, eval, parent, rgb[0], rgb[1], rgb[2]);
	}
	
	public boolean setSlot(int slotNum, Expression expressionIn)
	{
		if (slots.length > slotNum && slotNum >= 0 && expression.slots.length > slotNum)
		{
			expression.slots[slotNum] = expressionIn;
			return true;
		}
		return false;
	}
	
	public boolean slotFull(int slotNum)
	{
		if (innerExpressions.length > slotNum && slotNum >= 0)
			return innerExpressions[slotNum] != null;
		return false;
	}
	
	public ExpressionBlock getBlock(int slotNum)
	{
		if (innerExpressions.length > slotNum && slotNum >= 0)
			return innerExpressions[slotNum];
		else
			return null;
	}
	
	public ExpressionBlock duplicate(int id)
	{
		return blockFromExpression(id, this.getExpression(), parent);
	}
	
	public boolean setBlock(int slotNum, ExpressionBlock block)
	{
		if (slots.length > slotNum && slotNum >= 0)
		{
			if (innerExpressions[slotNum] != null)
				innerExpressions[slotNum].slotted = false;
			innerExpressions[slotNum] = block;
			if (block != null)
			{
				block.slotted = true;
				if (expression.evaluation instanceof Series)
				{
					if (slotNum == 0)
						block.expression.setName("i");
					else if (slotNum == 1)
						block.expression.setName("n");
				}
			}
			return true;
		}
		return false;
	}
	
	public Expression getExpression()
	{
		if (expression.isValue && !expression.isVariable)
			expression.vals[0] = Double.parseDouble(expression.getValueValue());
		for (int i = 0; i < slots.length; i++)
		{
			if (innerExpressions[i] != null)
				innerExpressions[i].addExpression(expression, i);
		}
		return expression;
	}
	
	public Expression addExpression(Expression base, int slot)
	{
		base.slots[slot] = expression;
		if (expression.isValue && !expression.isVariable)
			expression.vals[0] = Double.parseDouble(expression.getValueValue());
		for (int i = 0; i < slots.length; i++)
		{
			if (innerExpressions[i] != null)
				innerExpressions[i].addExpression(expression, i);
		}
		return base;
	}

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
	{
		if (!this.visible)
			return;
		if (dragged)
		{
			arrange(mouseX - 1, mouseY - 1);
		}
		else
			arrange(x, y);
		whole.drawSection(r, g, b);
		for (int i = 0; i < slots.length; i++)
		{
			if (slots[i].isPointWithinIncl(mouseX, mouseY))
				slots[i].drawSection(2 * r, 2 * g, 2 * b);
			else
				slots[i].drawSection(r / 2, g / 2, b / 2);
		}
		if (slots.length == 0 || slots.length == 1 || slots.length == 3)
			this.drawString(mc.fontRenderer, getMessage(), x + 2, y + 2, 0xFFFFFF);
		else if (slots.length == 2)
			this.drawString(mc.fontRenderer, getMessage(), x + 4 + slots[0].width, y + 2, 0xFFFFFF);
		for (int i = 0; i < innerExpressions.length; i++)
		{
			if (innerExpressions[i] != null)
				innerExpressions[i].render(mouseX, mouseY, partialTicks);
		}
		checkSlots();
	}

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int middle)
	{
		boolean hovered = whole.isPointWithinIncl(mouseX, mouseY) && this.active && this.visible;
		if (!hovered)
			return false;
		boolean subSlot = this.slotNotWhole(mouseX, mouseY);
		if (subSlot)
			return false;
	
		if (dragged)
		{
			if (!parent.spawnSection.isPointWithinIncl(mouseX, mouseY))
			{
				parent.slottingReqAt(mouseX, mouseY);
				parent.blockDropped();
			}
		}
		else if (!parent.dragging)
		{
			parent.setDragging(this);
			slotted = false;
			if (spawner != null)
			{
				if (!moved && x == spawner.x && y == spawner.y)
				{
					moved = true;
					spawner.spawn(parent.id++);
				}
			}
		}
		
		parent.updateBlockStrings();
		return hovered && !subSlot;
	}
	
	public void arrangeSections()
	{
		whole = new Section(x, y, calcWidth(), calcHeight());
		if (slots.length == 1)
		{
			slots[0] = new Section(x + width - calcSlotWidth(0) - 2, y + 2, calcSlotWidth(0), calcSlotHeight(0));
		}
		else if (slots.length == 2)
		{
			slots[0] = new Section(x + 2, y + 2, calcSlotWidth(0), calcSlotHeight(0));
			slots[1] = new Section(x + width - calcSlotWidth(1) - 2, y + 2, calcSlotWidth(1), calcSlotHeight(1));
		}
		else if (slots.length == 3)
		{
			int offset = x + width;
			int w0 = calcSlotWidth(0);
			int w1 = calcSlotWidth(1);
			int w2 = calcSlotWidth(2);
			int h0 = calcSlotHeight(0);
			int h1 = calcSlotHeight(1);
			int h2 = calcSlotHeight(2);
			slots[0] = new Section(offset - w0 - w1 - w2 - 6, y + 2, w0, h0);
			slots[1] = new Section(offset - w1 - w2 - 4, y + 2, w1, h1);
			slots[2] = new Section(offset - w2 - 2, y + 2, w2, h2);
		}
	}
	
	public void arrange(int x, int y)
	{
		this.x = x;
		this.y = y;
		arrangeSections();
		for (int i = 0; i < innerExpressions.length; i++)
		{
			if (innerExpressions[i] != null)
				innerExpressions[i].arrange(slots[i].x, slots[i].y);
		}
	}
	
	public int calcWidth()
	{
		int string = fontRenderer.getStringWidth(getMessage());
		int slot = 0;
		for (int i = 0; i < slots.length; i++)
		{
			slot += calcSlotWidth(i);
		}
		width = string + slot + (2 * slots.length) + 4; 
		return width;
	}
	
	public int calcSlotWidth(int slot)
	{
		if (innerExpressions.length > slot)
			return innerExpressions[slot] == null ? defaultSlotSize : innerExpressions[slot].calcWidth();
		else
			return 0;
	}
	
	public int calcHeight()
	{
		int slot = fontRenderer.FONT_HEIGHT;
		for (int i = 0; i < slots.length; i++)
		{
			slot = calcSlotHeight(i) > slot ? calcSlotHeight(i) : slot;
		}
		height = slot + 4;
		return height;
	}
	
	public int calcSlotHeight(int slot)
	{
		if (innerExpressions.length > slot)
			return innerExpressions[slot] == null ? defaultSlotSize : innerExpressions[slot].calcHeight();
		else
			return 0;
	}
	
	public boolean wholeNotSlot(double x, double y)
	{
		boolean b = false;
		b = whole.isPointWithinIncl(x, y);
		for (Section sec : slots)
		{
			if (sec.isPointWithinIncl(x, y))
				b = false;
		}
		return b;
	}
	
	public boolean slotNotWhole(double x, double y)
	{
		boolean b = false;
		for (Section sec : slots)
		{
			if (sec.isPointWithinIncl(x, y))
				b = true;
		}
		return b;
	}
	
	public boolean whole(int x, int y)
	{
		return whole.isPointWithinIncl(x, y);
	}
	
	public int getSlotNumAt(double x, double y)
	{
		for (int i = 0; i < slots.length; i++)
		{
			if (slots[i].isPointWithinIncl(x, y))
				return i;
		}
		return 0;
	}
	
	public void checkSlots()
	{
		for (int i = 0; i < innerExpressions.length; i++)
		{
			if (innerExpressions[i] != null && !innerExpressions[i].slotted)
			{
				setBlock(i, null);
			}
		}
	}
	
	public void setSpawner(BlockSpawner spawnerIn)
	{
		spawner = spawnerIn;
	}
	
	public int blockCount()
	{
		int c = 1;
		for (int i = 0; i < innerExpressions.length; i++)
		{
			c += innerExpressions[i] == null ? 0 : innerExpressions[i].blockCount();
		}
		return c;
	}
	
	//////////////////////
	public static ExpressionBlock blockFromExpression(int id, Expression expr, GuiGC parent)
	{
		EvalInfo inf = Evaluations.infoMap.get(expr.evaluation);
		ExpressionBlock block = new ExpressionBlock(id, 0, 0, inf.string, inf.slots, expr.evaluation, parent, inf.rgb);
		block.expression = Expression.cloneExpression(expr);
		int id2 = id;
		for (int i = 0; i < expr.slots.length; i++)
		{
			id2 = addBlockToBlockFromExpr(id2, block, expr.slots[i], i, parent);
		}
		if (expr.isValue)
		{
			if (expr.evaluation == Evaluations.VAL)
				block.setMessage(block.expression.getValueValue());
			else
				block.expression.setValueValue(block.getMessage(), true);
		}
		block.gennedID = id2;
		parent.button(block);
		return block;
	}
	
	private static int addBlockToBlockFromExpr(int prevId, ExpressionBlock block, Expression expr, int slot, GuiGC parent)
	{
		if (expr != null)
		{
			EvalInfo inf = Evaluations.infoMap.get(expr.evaluation);
			ExpressionBlock block2 = new ExpressionBlock(prevId + 1, 0, 0, inf.string, inf.slots, expr.evaluation, parent, inf.rgb);
			block2.expression = Expression.cloneExpression(expr);
			block.innerExpressions[slot] = block2;
			block2.slotted = true;
			int id3 = prevId + 1;
			for (int i = 0; i < expr.slots.length; i++)
			{
				id3 = addBlockToBlockFromExpr(id3, block2, expr.slots[i], i, parent);
			}
			if (expr.isValue)
			{
				if (expr.evaluation == Evaluations.VAL)
					block2.setMessage(block2.expression.getValueValue());
				else
					block2.expression.setValueValue(block2.getMessage(), true);
			}
			parent.button(block2);
			return id3;
		}
		return 0;
	}

	public void delete(boolean unslot)
	{
		this.active = false;
		this.onPage = false;
		this.visible = false;
		this.dragged = false;
		this.moved = false;
		if (unslot)
			this.slotted = false;
		for (int i = 0; i < innerExpressions.length; i++)
		{
			if (innerExpressions[i] != null)
				innerExpressions[i].delete(false);
		}
	}
	
	public void unDelete()
	{
		this.active = true;
		this.onPage = true;
		this.visible = true;
		
		for (int i = 0; i < innerExpressions.length; i++)
		{
			if (innerExpressions[i] != null)
				innerExpressions[i].unDelete();
		}
	}
	
	public void setMoved(boolean movedIn)
	{
		moved = movedIn;
		for (int i = 0; i < innerExpressions.length; i++)
		{
			if (innerExpressions[i] != null)
				innerExpressions[i].setMoved(movedIn);
		}
	}
	
	public void updateStringVars()
	{
		if (innerExpressions.length > 0)
		{
			String str = Evaluations.infoMap.get(expression.evaluation).string;
			
			if (innerExpressions[0] != null)
				str = str.replaceAll("i#", this.innerExpressions[0].expression.getName());
			if (innerExpressions.length > 1)
			{
				if (innerExpressions[1] != null)
					str = str.replaceAll("n#", this.innerExpressions[1].expression.getName());
				if (innerExpressions.length > 2)
				{
					if (innerExpressions[2] != null)
						str = str.replaceAll("v2", this.innerExpressions[2].expression.getName());
				}
			}
			setMessage(str);
		}
	}
}
