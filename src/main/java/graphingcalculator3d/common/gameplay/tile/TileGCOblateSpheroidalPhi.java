package graphingcalculator3d.common.gameplay.tile;

import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.positionlib.Alt3d;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

public class TileGCOblateSpheroidalPhi extends TileGCBase
{
	public TileGCOblateSpheroidalPhi(BlockPos pos, BlockState state) {
        super(TileEntities.GC_OBLATE_SPHEROIDAL_PHI, pos, state);
        domainA = GCNBT.GC_DOM_R;
		domainB = GCNBT.GC_DOM_1_PI_POS;
	}
	
	@Override
	public void genMesh()
	{
		varA = "mu";
		varB = "nu";
		super.genMesh();

		if (isErrored())
			return;
		
		Alt3d temp = new Alt3d();
		
		double mu;
		double nu;
		double phi;
		for (int j = 0; j < vertexArray.length; j++)
		{
			for (int k = 0; k < vertexArray[j].length; k++)
			{
				mu = vertexArray[j][k].x;
				nu = vertexArray[j][k].z;
				phi = vertexArray[j][k].y;
				
				double x = a * Math.cosh(mu) * Math.cos(nu) * Math.cos(phi);
				double y = a * Math.sinh(mu) * Math.sin(nu);
				double z = a * Math.cosh(mu) * Math.cos(nu) * Math.sin(phi);
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
