package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec3d;

public class NBTHandlerVec3d extends NBTHandler<Vec3d> {
    public NBTHandlerVec3d(String nameIn, Vec3d defaultValIn) {
        super(nameIn, defaultValIn);
    }

    @Override
    protected Vec3d getTag(CompoundNBT tag, String name) {
        CompoundNBT domainTag = tag.getCompound(name);

        if (!domainTag.contains("x", 6) || !domainTag.contains("y", 6) || !domainTag.contains("z", 6)) {
            return defaultVal();
        }

        return new Vec3d(domainTag.getDouble("x"), domainTag.getDouble("y"), domainTag.getDouble("z"));
    }

    @Override
    protected void setTag(CompoundNBT tag, String name, Vec3d value) {
        CompoundNBT domainTag = new CompoundNBT();
        domainTag.putDouble("x", value.x);
        domainTag.putDouble("y", value.y);
        domainTag.putDouble("z", value.z);
        tag.put(name, domainTag);
    }

}
