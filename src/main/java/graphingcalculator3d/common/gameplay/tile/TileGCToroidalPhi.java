package graphingcalculator3d.common.gameplay.tile;

import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.positionlib.Alt3d;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

public class TileGCToroidalPhi extends TileGCBase {
	public TileGCToroidalPhi(BlockPos pos, BlockState state) {
        super(TileEntities.GC_TOROIDAL_PHI, pos, state);
		domainA = GCNBT.GC_DOM_2_PI_POS_EXCL;
		domainB = GCNBT.GC_DOM_CIAN;
	}
	
	@Override
	public void genMesh()
	{
		varA = "sigma";
		varB = "tau";
		super.genMesh();

		if (isErrored())
			return;
		
		Alt3d temp = new Alt3d();
		
		double sigma;
		double tau;
		double phi;
		for (int j = 0; j < vertexArray.length; j++)
		{
			for (int k = 0; k < vertexArray[j].length; k++)
			{
				sigma = vertexArray[j][k].x;
				tau = vertexArray[j][k].z;
				phi = vertexArray[j][k].y;
				
				double x = a * Math.cos(phi) * (Math.sinh(tau) / (Math.cosh(tau) - Math.cos(sigma)));
				double y = a * (Math.sin(sigma) / (Math.cosh(tau) - Math.cos(sigma)));
				double z = a * Math.sin(phi) * (Math.sinh(tau) / (Math.cosh(tau) - Math.cos(sigma)));
				temp.setTo(x, y, z, Alt3d.CARTESIAN);
				scaleTrans(temp);
				vertexArray[j][k] = new Vector3d(temp.getX(), temp.getY(), temp.getZ());
			
				if (j == 0 && k == 0)
				{
					highestF = vertexArray[j][k].y;
					lowestF = vertexArray[j][k].y;
				}
				
				if (vertexArray[j][k].y > highestF)
					highestF = vertexArray[j][k].y;
				if (vertexArray[j][k].y < lowestF)
					lowestF = vertexArray[j][k].y;
			}
		}
		
		postMesh();
	}
}
