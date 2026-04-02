package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;

public class NBTHandlerVec3d extends NBTHandler<Vec3d> {
    public NBTHandlerVec3d(String nameIn, Vec3d defaultValIn) {
        super(nameIn, defaultValIn);
    }

    @Override
    protected Vec3d getTag(NBTTagCompound tag, String name) {
        NBTTagCompound domainTag = tag.getCompound(name);

        return new Vec3d(domainTag.getDouble("x"), domainTag.getDouble("y"), domainTag.getDouble("z"));
    }

    @Override
    protected void setTag(NBTTagCompound tag, String name, Vec3d value) {
        NBTTagCompound domainTag = new NBTTagCompound();
        domainTag.setDouble("x", value.x);
        domainTag.setDouble("y", value.y);
        domainTag.setDouble("z", value.z);
        tag.setTag(name, domainTag);
    }

}
