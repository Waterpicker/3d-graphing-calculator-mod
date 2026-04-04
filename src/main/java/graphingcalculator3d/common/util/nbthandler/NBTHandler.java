package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundTag;

public abstract class NBTHandler<T> {
    private final T defaultVal;
    private final String name;

    public NBTHandler(String nameIn, T defaultValIn) {
        name = nameIn;
        defaultVal = defaultValIn;
    }

    public String getName()
    {
        return name;
    }

    public T defaultVal()
    {
        return defaultVal;
    }

    public CompoundTag setValue(CompoundTag compound, T value) {
        setTag(compound, name, value);
        return compound;
    }

    abstract protected void setTag(CompoundTag tag, String name, T value);
    abstract protected T getTag(CompoundTag tag, String name);

    public T getValue(CompoundTag compound) {
        return (compound.contains(name)) ? getTag(compound, name) : defaultVal;
    }
}
