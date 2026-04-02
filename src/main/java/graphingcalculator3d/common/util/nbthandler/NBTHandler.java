package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.NBTTagCompound;

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

    public NBTTagCompound setValue(NBTTagCompound compound, T value) {
        setTag(compound, name, value);
        return compound;
    }

    abstract protected void setTag(NBTTagCompound tag, String name, T value);
    abstract protected T getTag(NBTTagCompound tag, String name);

    public T getValue(NBTTagCompound compound) {
        return (compound.hasKey(name)) ? getTag(compound, name) : defaultVal;
    }
}
