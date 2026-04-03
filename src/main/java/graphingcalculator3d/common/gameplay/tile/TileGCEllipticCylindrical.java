package graphingcalculator3d.common.gameplay.tile;

import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.positionlib.Alt3d;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.util.math.vector.Vector3d;

public class TileGCEllipticCylindrical extends TileGCBase {
	public TileGCEllipticCylindrical() {
        super(TileEntities.GC_ELLIPTIC_CYLINDRICAL);
		domainA = GCNBT.GC_DOM_2_PI_POS;
		domainB = GCNBT.GC_DOM_2_PI_POS;
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
		
		double h;
		double mu;
		double nu;
		for (int j = 0; j < vertexArray.length; j++)
		{
			for (int k = 0; k < vertexArray[j].length; k++)
			{
				h = vertexArray[j][k].y;
				mu = vertexArray[j][k].x;
				nu = vertexArray[j][k].z;
				
				double x = a * Math.cosh(mu) * Math.cos(nu);
				double y = h;
				double z = a * Math.sinh(mu) * Math.sin(nu);
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
