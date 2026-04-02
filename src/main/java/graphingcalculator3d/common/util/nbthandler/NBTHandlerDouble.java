package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTHandlerDouble extends NBTHandler<Double> {

	public NBTHandlerDouble(String nameIn, double defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected void setTag(CompoundNBT tag, String name, Double value) {
        tag.putDouble(name, value);
    }

    @Override
    protected Double getTag(CompoundNBT tag, String name) {
        return tag.getDouble(name);
    }

}
