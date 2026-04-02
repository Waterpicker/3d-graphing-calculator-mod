package graphingcalculator3d.common.gameplay.tile;

import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.positionlib.Alt3d;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.util.math.Vec3d;

public class TileGCEllipticCylindricalNu extends TileGCBase
{
	public TileGCEllipticCylindricalNu() {
        super(TileEntities.GC_ELLIPTIC_CYLINDRICAL_NU);
		domainA = GCNBT.GC_DOM_2_PI_POS;
		domainB = GCNBT.GC_DOMAIN_B.defaultVal();
	}
	
	@Override
	public void genMesh()
	{
		varA = "nu";
		varB = "h";
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
				h = vertexArray[j][k].z;
				mu = vertexArray[j][k].x;
				nu = vertexArray[j][k].y;
				
				double x = a * Math.cosh(mu) * Math.cos(nu);
				double y = h;
				double z = a * Math.sinh(mu) * Math.sin(nu);
				temp.setTo(x, y, z, Alt3d.CARTESIAN);
				scaleTrans(temp);
				vertexArray[j][k] = new Vec3d(temp.getX(), temp.getY(), temp.getZ());
			
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
