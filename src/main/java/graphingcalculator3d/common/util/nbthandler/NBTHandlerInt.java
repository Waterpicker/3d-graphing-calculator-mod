package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundTag;

public class NBTHandlerInt extends NBTHandler<Integer> {
	public NBTHandlerInt(String nameIn, int defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected Integer getTag(CompoundTag tag, String name) {
        return tag.getInt(name);
    }

    @Override
    protected void setTag(CompoundTag tag, String name, Integer value) {
        tag.putInt(name, value);
    }
}
