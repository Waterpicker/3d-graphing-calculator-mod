package graphingcalculator3d.common.util.nbthandler;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NBTHandlerIntArray extends NBTHandler<int[]> {
    public NBTHandlerIntArray(String name, int[] defaultVal) {
        super(name, defaultVal);
    }

    @Override
    protected void setTag(NBTTagCompound tag, String name, int[] value) {
        tag.setIntArray(name, value);
    }

    @Override
    protected int[] getTag(NBTTagCompound tag, String name) {
        return tag.getIntArray(name);
    }
}
