package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundNBT;

public class NBTHandlerString extends NBTHandler<String> {

	
	public NBTHandlerString(String nameIn, String defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected void setTag(CompoundNBT tag, String name, String value) {
        tag.putString(name, value);
    }

    @Override
    protected String getTag(CompoundNBT tag, String name) {
        return tag.getString(name);
    }
}
