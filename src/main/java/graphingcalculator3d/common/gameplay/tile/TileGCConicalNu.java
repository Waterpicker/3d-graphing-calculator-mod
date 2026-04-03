package graphingcalculator3d.common.gameplay.tile;

import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.positionlib.Alt3d;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.util.math.vector.Vector3d;

public class TileGCConicalNu extends TileGCBase {
	public TileGCConicalNu() {
        super(TileEntities.GC_CONICAL_NU);
		domainA = GCNBT.GC_DOMAIN_A.defaultVal();
		domainB = GCNBT.GC_DOMAIN_B.defaultVal();
	}
	
	@Override
	public void genMesh() {
		varA = "r";
		varB = "mu";
		super.genMesh();

		if (isErrored())
			return;
		
		Alt3d temp = new Alt3d();
		
		double r;
		double mu;
		double nu;
		for (int j = 0; j < vertexArray.length; j++)
		{
			for (int k = 0; k < vertexArray[j].length; k++)
			{
				r = vertexArray[j][k].x;
				mu = vertexArray[j][k].z;
				nu = vertexArray[j][k].y;
				
				double x = (r * mu * nu) / (b * c);
				double y = (r / c) * Math.sqrt(   ( (sqr(mu)-sqr(c))*(sqr(nu)-sqr(c)) )  /  (sqr(c) - sqr(b))   );
				double z = (r / b) * Math.sqrt(   ( (sqr(mu)-sqr(b))*(sqr(nu)-sqr(b)) )  /  (sqr(b) - sqr(c))   );
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
