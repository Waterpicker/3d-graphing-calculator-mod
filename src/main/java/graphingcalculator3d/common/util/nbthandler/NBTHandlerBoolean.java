package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundTag;

public class NBTHandlerBoolean extends NBTHandler<Boolean> {
	public NBTHandlerBoolean(String nameIn, boolean defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected void setTag(CompoundTag tag, String name, Boolean value) {
        tag.putBoolean(name, value);
	}

    @Override
    protected Boolean getTag(CompoundTag tag, String name) {
        return tag.getBoolean(name);
    }
}
