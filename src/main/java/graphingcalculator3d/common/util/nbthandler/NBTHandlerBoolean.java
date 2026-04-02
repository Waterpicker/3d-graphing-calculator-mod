package graphingcalculator3d.common.util.nbthandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTHandlerBoolean extends NBTHandler<Boolean>
{
	public NBTHandlerBoolean(String nameIn, boolean defaultValIn) {
        super(nameIn, defaultValIn);
	}

    @Override
    protected void setTag(CompoundNBT tag, String name, Boolean value) {
        tag.putBoolean(name, value);
	}

    @Override
    protected Boolean getTag(CompoundNBT tag, String name) {
        return tag.getBoolean(name);
    }
}
