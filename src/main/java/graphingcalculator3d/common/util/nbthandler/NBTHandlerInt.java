package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.NBTTagCompound;

public class NBTHandlerInt extends NBTHandler<Integer> {
	public NBTHandlerInt(String nameIn, int defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected Integer getTag(NBTTagCompound tag, String name) {
        return tag.getInt(name);
    }

    @Override
    protected void setTag(NBTTagCompound tag, String name, Integer value) {
        tag.setInt(name, value);
    }
}
