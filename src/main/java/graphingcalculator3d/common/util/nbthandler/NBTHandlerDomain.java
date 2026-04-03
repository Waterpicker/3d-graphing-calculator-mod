package graphingcalculator3d.common.util.nbthandler;

import graphingcalculator3d.common.util.arrays.Arrays;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTHandlerDomain extends NBTHandler<Domain> {
	public NBTHandlerDomain(String nameIn, Domain defaultValIn) {
		super(nameIn, defaultValIn);
	}

    @Override
    protected Domain getTag(CompoundNBT tag, String name) {
        CompoundNBT domainTag = tag.getCompound(name);

        if (!domainTag.contains("min", 6) || !domainTag.contains("max", 6)) { // double
            return defaultVal();
        }

        return new Domain(domainTag.getDouble("min"), domainTag.getDouble("max"));
    }

    @Override
    protected void setTag(CompoundNBT tag, String name, Domain value) {
        CompoundNBT domainTag = new CompoundNBT();
        domainTag.putDouble("min", value.min());
        domainTag.putDouble("max", value.max());

        tag.put(name, domainTag);
    }
}
