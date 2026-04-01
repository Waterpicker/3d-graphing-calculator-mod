package graphingcalculator3d.common.gameplay.creativetabs;

import graphingcalculator3d.common.gameplay.blocks.GCBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TabGC extends ItemGroup
{
	public TabGC(String label)
	{
		super(label);
	}

    @Override
    public ItemStack createIcon() {
        return new ItemStack(GCBlocks.block_gc_cartesian);
	}
}
