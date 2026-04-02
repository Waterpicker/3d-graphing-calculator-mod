package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTHandlerBoolean
{
	private final boolean defaultVal;
	private String name;
	
	public NBTHandlerBoolean(String nameIn, boolean defaultValIn)
	{
		name = nameIn;
		defaultVal = defaultValIn;
	}
	
	public boolean defaultVal()
	{
		return defaultVal;
	}
	
	public String getName()
	{
		return name;
	}
	
	public NBTTagCompound setValue(NBTTagCompound compound, boolean value)
	{
		compound.setBoolean(name, value);
		return compound;
	}
	
	public boolean getValue(NBTTagCompound compound)
	{
		return (compound.hasKey(name)) ? compound.getBoolean(name) : defaultVal;
	}
}
