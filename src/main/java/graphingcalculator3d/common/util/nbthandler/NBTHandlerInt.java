package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundNBT;

public class NBTHandlerInt extends NBTHandler<Integer> {
	public NBTHandlerInt(String nameIn, int defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected Integer getTag(CompoundNBT tag, String name) {
        return tag.getInt(name);
    }

    @Override
    protected void setTag(CompoundNBT tag, String name, Integer value) {
        tag.putInt(name, value);
    }
}
