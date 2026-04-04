package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundTag;

public class NBTHandlerIntArray extends NBTHandler<int[]> {
    public NBTHandlerIntArray(String name, int[] defaultVal) {
        super(name, defaultVal);
    }

    @Override
    protected void setTag(CompoundTag tag, String name, int[] value) {
        tag.putIntArray(name, value);
    }

    @Override
    protected int[] getTag(CompoundTag tag, String name) {
        return tag.getIntArray(name);
    }
}
