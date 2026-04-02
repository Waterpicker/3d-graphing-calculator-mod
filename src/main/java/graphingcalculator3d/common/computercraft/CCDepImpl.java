//package graphingcalculator3d.common.computercraft;
//
//import dan200.computercraft.api.ComputerCraftAPI;
//import graphingcalculator3d.common.gameplay.tile.TileGCBase;
//import net.minecraft.tileentity.TileEntity;
//
//public class CCDepImpl implements CCDep
//{
//	@Override
//	public void preInit()
//	{
//		ComputerCraftAPI.registerPeripheralProvider((world, pos, side) ->
//		{
//			TileEntity ent = world.getTileEntity(pos);
//			if (ent instanceof TileGCBase)
//			{
//				return new GraphPeripheral((TileGCBase) ent);
//			}
//			return null;
//		});
//	}
//
//	@Override
//	public void init()
//	{
//	}
//
//	@Override
//	public void postInit()
//	{
//	}
//}
