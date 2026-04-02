package graphingcalculator3d.common.util.nbthandler;

import graphingcalculator3d.common.util.arrays.Arrays;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTHandlerDomain extends NBTHandler<Domain> {
	public NBTHandlerDomain(String nameIn, Domain defaultValIn) {
		super(nameIn, defaultValIn);
	}

    @Override
    protected Domain getTag(NBTTagCompound tag, String name) {
        NBTTagCompound domainTag = tag.getCompound(name);

        return new Domain(domainTag.getDouble("min"), domainTag.getDouble("max"));
    }

    @Override
    protected void setTag(NBTTagCompound tag, String name, Domain value) {
        NBTTagCompound domainTag = new NBTTagCompound();
        domainTag.setDouble("min", value.min());
        domainTag.setDouble("max", value.max());

        tag.setTag(name, domainTag);
    }
}
