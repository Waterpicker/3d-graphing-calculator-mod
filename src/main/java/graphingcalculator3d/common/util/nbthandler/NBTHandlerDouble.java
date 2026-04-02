package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTHandlerDouble
{
	private final double defaultVal;
	private String name;
	
	public NBTHandlerDouble(String nameIn, double defaultValIn)
	{
		name = nameIn;
		defaultVal = defaultValIn;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double defaultVal()
	{
		return defaultVal;
	}
	
	public NBTTagCompound setValue(NBTTagCompound compound, double value)
	{
		compound.setDouble(name, value);
		return compound;
	}
	
	public double getValue(NBTTagCompound compound)
	{
		return (compound.hasKey(name)) ? compound.getDouble(name) : defaultVal;
	}
}
