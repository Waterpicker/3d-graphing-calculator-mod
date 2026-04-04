package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundTag;

public class NBTHandlerDouble extends NBTHandler<Double> {

	public NBTHandlerDouble(String nameIn, double defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected void setTag(CompoundTag tag, String name, Double value) {
        tag.putDouble(name, value);
    }

    @Override
    protected Double getTag(CompoundTag tag, String name) {
        return tag.getDouble(name);
    }

}
