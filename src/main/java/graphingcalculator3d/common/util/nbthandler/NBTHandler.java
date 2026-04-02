package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundNBT;

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

    public CompoundNBT setValue(CompoundNBT compound, T value) {
        setTag(compound, name, value);
        return compound;
    }

    abstract protected void setTag(CompoundNBT tag, String name, T value);
    abstract protected T getTag(CompoundNBT tag, String name);

    public T getValue(CompoundNBT compound) {
        return (compound.contains(name)) ? getTag(compound, name) : defaultVal;
    }
}
