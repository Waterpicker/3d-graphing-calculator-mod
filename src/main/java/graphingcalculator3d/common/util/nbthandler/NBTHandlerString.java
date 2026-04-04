package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundTag;

public class NBTHandlerString extends NBTHandler<String> {

	
	public NBTHandlerString(String nameIn, String defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected void setTag(CompoundTag tag, String name, String value) {
        tag.putString(name, value);
    }

    @Override
    protected String getTag(CompoundTag tag, String name) {
        return tag.getString(name);
    }
}
