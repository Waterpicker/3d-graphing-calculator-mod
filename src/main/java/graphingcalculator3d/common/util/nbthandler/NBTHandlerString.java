package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTHandlerString extends NBTHandler<String> {

	
	public NBTHandlerString(String nameIn, String defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected void setTag(NBTTagCompound tag, String name, String value) {
        tag.setString(name, value);
    }

    @Override
    protected String getTag(NBTTagCompound tag, String name) {
        return tag.getString(name);
    }
}
