package graphingcalculator3d.common.gameplay.tile;

import graphingcalculator3d.common.util.events.register.TileEntities;
import graphingcalculator3d.common.util.math.positionlib.Alt3d;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

public class TileGC6SphereV extends TileGCBase {
	public TileGC6SphereV(BlockPos pos, BlockState state) {
        super(TileEntities.GC_6_SPHERE_V, pos, state);
		domainA = GCNBT.GC_DOM_CIAN;
		domainB = GCNBT.GC_DOM_CIAN;
	}
	
	@Override
	public void genMesh()
	{
		varA = "u";
		varB = "w";
		super.genMesh();

		if (isErrored())
			return;
		
		Alt3d temp = new Alt3d();
		
		double u;
		double v;
		double w;
		for (int j = 0; j < vertexArray.length; j++)
		{
			for (int k = 0; k < vertexArray[j].length; k++)
			{
				u = vertexArray[j][k].x;
				v = vertexArray[j][k].y;
				w = vertexArray[j][k].z;
				
				double x = u / (sqr(u) + sqr(v) + sqr(w));
				double y = w / (sqr(u) + sqr(v) + sqr(w));
				double z = v / (sqr(u) + sqr(v) + sqr(w));
				
				temp.setTo(x, y, z, Alt3d.CARTESIAN);
				//vertexArray[j][k] = new Vec3d(x, y, z);
				//aggThresh(j, k);
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
