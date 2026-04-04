package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundTag;

public class NBTHandlerDomain extends NBTHandler<Domain> {
	public NBTHandlerDomain(String nameIn, Domain defaultValIn) {
		super(nameIn, defaultValIn);
	}

    @Override
    protected Domain getTag(CompoundTag tag, String name) {
        CompoundTag domainTag = tag.getCompound(name);

        if (!domainTag.contains("min", 6) || !domainTag.contains("max", 6)) { // double
            return defaultVal();
        }

        return new Domain(domainTag.getDouble("min"), domainTag.getDouble("max"));
    }

    @Override
    protected void setTag(CompoundTag tag, String name, Domain value) {
        CompoundTag domainTag = new CompoundTag();
        domainTag.putDouble("min", value.min());
        domainTag.putDouble("max", value.max());

        tag.put(name, domainTag);
    }
}
