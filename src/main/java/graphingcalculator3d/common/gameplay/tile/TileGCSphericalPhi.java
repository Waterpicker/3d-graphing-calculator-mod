package graphingcalculator3d.common.gameplay.tile;

import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.positionlib.Alt3d;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.util.math.Vec3d;

public class TileGCSphericalPhi extends TileGCBase {
	public TileGCSphericalPhi() {
        super(TileEntities.GC_SPHERICAL_PHI);
		domainA = GCNBT.GC_DOM_R;
		domainB = GCNBT.GC_DOM_1_PI_POS;
	}
	
	@Override
	public void genMesh()
	{
		varA = "r";
		varB = "theta";
		super.genMesh();

		if (isErrored())
			return;
		
		Alt3d temp = new Alt3d();
		
		for (int j = 0; j < vertexArray.length; j++)
		{
			for (int k = 0; k < vertexArray[j].length; k++)
			{
				temp.setTo(vertexArray[j][k].x, vertexArray[j][k].y, (Math.PI / 2) - vertexArray[j][k].z, Alt3d.SPHERICAL);
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
