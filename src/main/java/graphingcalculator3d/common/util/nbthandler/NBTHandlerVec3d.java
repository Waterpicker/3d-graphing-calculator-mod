package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.nbt.CompoundTag;
import org.joml.Vector3d;

public class NBTHandlerVec3d extends NBTHandler<Vector3d> {
    public NBTHandlerVec3d(String nameIn, Vector3d defaultValIn) {
        super(nameIn, defaultValIn);
    }

    @Override
    protected Vector3d getTag(CompoundTag tag, String name) {
        CompoundTag domainTag = tag.getCompound(name);

        if (!domainTag.contains("x", 6) || !domainTag.contains("y", 6) || !domainTag.contains("z", 6)) {
            return defaultVal();
        }

        return new Vector3d(domainTag.getDouble("x"), domainTag.getDouble("y"), domainTag.getDouble("z"));
    }

    @Override
    protected void setTag(CompoundTag tag, String name, Vector3d value) {
        CompoundTag domainTag = new CompoundTag();
        domainTag.putDouble("x", value.x);
        domainTag.putDouble("y", value.y);
        domainTag.putDouble("z", value.z);
        tag.put(name, domainTag);
    }

}
