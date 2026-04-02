package graphingcalculator3d.common.gameplay.tile;

import java.text.NumberFormat;
import java.util.List;

import graphingcalculator3d.common.util.nbthandler.Domain;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import org.apache.commons.lang3.ArrayUtils;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.util.arrays.Arrays;
import graphingcalculator3d.common.util.config.ConfigVars;
import graphingcalculator3d.common.util.events.Event;
import graphingcalculator3d.common.util.events.GCEvents;
import graphingcalculator3d.common.util.events.TriggerOn;
import graphingcalculator3d.common.util.math.Compare;
import graphingcalculator3d.common.util.math.expression.Expression;
import graphingcalculator3d.common.util.math.expression.Expression.InfiniteCalculationsException;
import graphingcalculator3d.common.util.math.positionlib.Alt3d;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;

public class TileGCBase extends TileEntity {
	public static int RENDER_DISTANCE_SQ = ConfigVars.RenderingConfigs.renderDistance * ConfigVars.RenderingConfigs.renderDistance;
	private static int ALPHA_CUTOFF = ConfigVars.RenderingConfigs.alphaCutoff;
	private static double COLLISION_RANGE_SQ = ConfigVars.GraphingConfigs.collisionRange * ConfigVars.GraphingConfigs.collisionRange;
	
	private Expression function;
	private Vec3d[] mesh;
	public Vec3d[][] vertexArray;
	public boolean[][] disconnects;
	private AxisAlignedBB[] collisionArray = new AxisAlignedBB[] {};
	public boolean collision = GCNBT.GC_COLLISION.defaultVal();
	private boolean errored = false;
	public boolean renderReady = false;
	public boolean regenGraph = false;
	public String tex = GCNBT.GC_TEXTURE.defaultVal();
	private String errorMessage = "";
	private String erroredFunction = "";
	private String functionText = "";
	public int[] rgba = GCNBT.GC_RGBA.defaultVal();
	public double highestSlope;
	public double[][] slope;
	public boolean colorSlope = GCNBT.GC_COLOR_SLOPE.defaultVal();
	public double lowestF;
	public double highestF;
	public int tileCount = GCNBT.GC_TILE_COUNT.defaultVal();
	private double resolution = GCNBT.GC_RESOLUTION.defaultVal();
	public Vec3d scale = GCNBT.GC_SCALE.defaultVal();
	public Vec3d translation = GCNBT.GC_TRANSLATION.defaultVal();
	public Vec3d rotation = GCNBT.GC_ROTATION.defaultVal();
	private double discThresh = GCNBT.GC_DISC_THRESH.defaultVal();
	private double aggDiscThresh = GCNBT.GC_AGG_DISC_THRESH.defaultVal();
	private boolean cropToRange = GCNBT.GC_CROP_TO_RANGE.defaultVal();
	public Domain domainA;
	public Domain domainB;
	public Domain range = GCNBT.GC_RANGE.defaultVal();
	
	protected static double a = ConfigVars.GraphingConfigs.a;
	protected static double b = ConfigVars.GraphingConfigs.b;
	protected static double c = ConfigVars.GraphingConfigs.c;
	private static double gold_r = 1.61803398874989484820;
	private static double plank_c = 6.626068E-34;
	private static double avog_c = 6.0221515E23;
	private static double grav_c = 6.67300E-11;
	private static double boltz_c = 1.380650E23;
	
	public String varA;
	public String varB;
	
	public int renderID = -1;
	public GCPreviousState prevState;
	/////////////////////////////////////////////////////////////////////////////

    public TileGCBase(RegistryObject<TileEntityType<TileGCBase>> tileEntityType) {
        this(tileEntityType.get());
    }

	public TileGCBase(TileEntityType<TileGCBase> tileEntityType) {
		super(tileEntityType);
		Event.register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	///////////////////////////////////// NBT
	
	@Override
	public CompoundNBT getUpdateTag()
	{
        CompoundNBT nbtTagCompound = new CompoundNBT();
		write(nbtTagCompound);
		return nbtTagCompound;
	}
	
	@Override
	public void handleUpdateTag(CompoundNBT tag)
	{
		this.read(tag);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT parentNBTTagCompound)
	{
		super.write(parentNBTTagCompound);
		
		return writeRelevant(parentNBTTagCompound);
	}
	
	@Override
	public void read(CompoundNBT parentNBTTagCompound)
	{
		super.read(parentNBTTagCompound);
		
		readRelevant(parentNBTTagCompound);
	}
	
	public CompoundNBT writeRelevant(CompoundNBT parentNBTTagCompound)
	{
		
		if (function != null)
			GCNBT.GC_FUNCTION.setValue(parentNBTTagCompound, function.writeToString());
		else
			GCNBT.GC_FUNCTION.setValue(parentNBTTagCompound, GCNBT.GC_FUNCTION.defaultVal());
		GCNBT.GC_TEXTURE.setValue(parentNBTTagCompound, tex);
		
		GCNBT.GC_CROP_TO_RANGE.setValue(parentNBTTagCompound, cropToRange);
		GCNBT.GC_COLOR_SLOPE.setValue(parentNBTTagCompound, colorSlope);
		GCNBT.GC_COLLISION.setValue(parentNBTTagCompound, collision);
		
		GCNBT.GC_RGBA.setValue(parentNBTTagCompound, rgba);
		
		GCNBT.GC_TILE_COUNT.setValue(parentNBTTagCompound, tileCount);
		GCNBT.GC_RESOLUTION.setValue(parentNBTTagCompound, resolution);
		GCNBT.GC_DISC_THRESH.setValue(parentNBTTagCompound, discThresh);
		GCNBT.GC_AGG_DISC_THRESH.setValue(parentNBTTagCompound, aggDiscThresh);
		
		GCNBT.GC_SCALE.setValue(parentNBTTagCompound, scale);
		GCNBT.GC_TRANSLATION.setValue(parentNBTTagCompound, translation);
		GCNBT.GC_DOMAIN_A.setValue(parentNBTTagCompound, domainA);
		GCNBT.GC_RANGE.setValue(parentNBTTagCompound, range);
		GCNBT.GC_DOMAIN_B.setValue(parentNBTTagCompound, domainB);
		GCNBT.GC_ROTATION.setValue(parentNBTTagCompound, rotation);
		
		return parentNBTTagCompound;
	}

	public void readRelevant(CompoundNBT parentNBTTagCompound)
	{
		setErrored(false);
		renderReady = false;
		
		try
		{
			setFunction(Expression.parseFromString(GCNBT.GC_FUNCTION.getValue(parentNBTTagCompound)));
		}
		catch (NumberFormatException e)
		{
			setErrored(true, "Invalid number formatting.");
			setErroredFunction(GCNBT.GC_FUNCTION.getValue(parentNBTTagCompound));
			e.printStackTrace();
		}
		
		tex = GCNBT.GC_TEXTURE.getValue(parentNBTTagCompound);
		
		setResolution(GCNBT.GC_RESOLUTION.getValue(parentNBTTagCompound));
		tileCount = GCNBT.GC_TILE_COUNT.getValue(parentNBTTagCompound);
		
		domainA = GCNBT.GC_DOMAIN_A.getValue(parentNBTTagCompound);
		range = GCNBT.GC_RANGE.getValue(parentNBTTagCompound);
		domainB = GCNBT.GC_DOMAIN_B.getValue(parentNBTTagCompound);
		
		scale = GCNBT.GC_SCALE.getValue(parentNBTTagCompound);
		translation = GCNBT.GC_TRANSLATION.getValue(parentNBTTagCompound);
		rotation = GCNBT.GC_ROTATION.getValue(parentNBTTagCompound);
		
		rgba = GCNBT.GC_RGBA.getValue(parentNBTTagCompound);
		if (rgba.length == 4)
		{
			rgba = Arrays.copyOf(rgba, 5);
			rgba[4] = 0;
		}
		colorSlope = GCNBT.GC_COLOR_SLOPE.getValue(parentNBTTagCompound);
		
		cropToRange(GCNBT.GC_CROP_TO_RANGE.getValue(parentNBTTagCompound));
		setDiscThresh(GCNBT.GC_DISC_THRESH.getValue(parentNBTTagCompound));
		setAggDiscThresh(GCNBT.GC_AGG_DISC_THRESH.getValue(parentNBTTagCompound));
		
		collision = GCNBT.GC_COLLISION.getValue(parentNBTTagCompound);
		
		if (this.hasWorld())
			if (this.world.isRemote)
				genMesh();
	}
	
	////////////////////////////////////// FastTESR
	
	@Override
	public boolean hasFastRenderer()
	{
		return true;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return Double.POSITIVE_INFINITY;
	}


//    TODO
//	@Override
//	public boolean shouldRenderInPass(int pass)
//	{
//		if (errored)
//			return false;
//		else if (rgba[4] == 0)
//			return (pass == 0 && rgba[3] >= ALPHA_CUTOFF) || (pass == 1 && rgba[3] < ALPHA_CUTOFF);
//		else if (rgba[4] == 1)
//			return pass == 0;
//		else if (rgba[4] == 2)
//			return pass == 1;
//		else
//		{
//			this.setErrored(true, "The fifth (5th) value in the RGBA text field must be either 0, 1, or 2.");
//			return false;
//		}
//	}

    @Override
    public void remove() {
        GraphingCalculator3D.proxy.deleteVertexData(this);
		super.remove();
	}
	
	@Override
	public void onChunkUnloaded() {
		GraphingCalculator3D.proxy.deleteVertexData(this);
	}
	
	@Override
	public void onLoad()
	{
		if (this.hasWorld() && !this.world.isRemote && !this.renderReady && this.collision)
			genMesh();
	}
	
	////////// Generating
	
	public void genMesh()
	{
		if (!ConfigVars.RenderingConfigs.render)
		{
			setErrored(true, "Graph rendering is disabled in the config.");
			return;
		}
		if (sqr(resolution) * (domainA.max() - domainA.min()) * (domainB.max() - domainB.min()) > ConfigVars.RenderingConfigs.maxComplexity)
		{
			NumberFormat f = NumberFormat.getInstance();
			setErrored(true, "Graph complexity is above the " + f.format(ConfigVars.RenderingConfigs.maxComplexity)
					+ "-tile limit defined in the config file." + " Feel free to change that limit, but things will get laggy.");
			return;
		}
		renderReady = false;
		if (this.isErrored())
			return;
		if (function == null)
		{
			setErrored(true, "Invalid function");
			System.out.println(errorMessage);
			return;
		}
		
		try
		{
			double space = 1 / resolution;
			mesh = new Vec3d[(int) (((domainA.length()) * (domainB.length())) * (resolution * resolution))];
			vertexArray = new Vec3d[(int) ((domainA.length()) * resolution)][(int) ((domainB.length()) * resolution)];
			disconnects = new boolean[vertexArray.length + 2][vertexArray[0].length + 2];
			slope = new double[vertexArray.length + 2][vertexArray[0].length + 2];
			int i = 0;
			int j = 0;
			int k = 0;
			lowestF = 0;
			highestF = 0;
			
			function.setVarsToDoub("e", Math.E);
			function.setVarsToDoub("pi", Math.PI);
			function.setVarsToDoub("a", a);
			function.setVarsToDoub("b", b);
			function.setVarsToDoub("c", c);
			function.setVarsToDoub("gold_r", gold_r);
			function.setVarsToDoub("plank_c", plank_c);
			function.setVarsToDoub("grav_c", grav_c);
			function.setVarsToDoub("boltz_c", boltz_c);
			function.setVarsToDoub("avog_c", avog_c);
			
			double f00 = 0, f10 = 0, f01 = 0, f11 = 0, s10 = 0, s01 = 0, s11 = 0;
			
			for (double x = domainA.min(); x < domainA.max(); x += space) {
				for (double z = domainB.min(); z < domainB.max(); z += space) {
					///// Position/Discontinuity/AddingPointsInGeneral
					function.setVarsToDoub(varA, x);
					function.setVarsToDoub(varB, z);
					Vec3d vertex = new Vec3d(x, function.eval(), z);
					
					if (i < mesh.length)
						mesh[i] = vertex;
					else
						mesh = ArrayUtils.add(mesh, vertex);
					
					if (mesh[i].y > range.max())
						if (cropToRange)
							mesh[i] = new Vec3d(mesh[i].x, range.max(), mesh[i].z);
						else
							disconnects[j][k] = true;
					if (mesh[i].y < range.min())
						if (cropToRange)
							mesh[i] = new Vec3d(mesh[i].x, range.min(), mesh[i].z);
						else
							disconnects[j][k] = true;
						
					if (vertexArray.length > j)
						if (vertexArray[j].length > k)
							vertexArray[j][k] = mesh[i];
						else
							vertexArray[j] = ArrayUtils.add(vertexArray[j], mesh[i]);
					else
					{
						vertexArray = ArrayUtils.add(vertexArray, new Vec3d[(int) ((domainB.length()) * resolution)]);
						if (vertexArray[j].length > k)
							vertexArray[j][k] = mesh[i];
						else
							vertexArray[j] = ArrayUtils.add(vertexArray[j], mesh[i]);
					}
					
					// Discontinuity detection
					f00 = vertexArray[j][k].y;
					
					if (vertexArray[j][k].x == Double.NaN || vertexArray[j][k].y == Double.NaN || vertexArray[j][k].z == Double.NaN)
						disconnects[j][k] = true;
					if (k > 0)
					{
						f01 = vertexArray[j][k - 1].y;
						s01 = (f00 - f01) / (vertexArray[j][k].z - vertexArray[j][k - 1].z);
						
						if (Math.abs(f00 - f01) >= Math.abs(range.length()))
							disconnects[j][k - 1] = true;
						if (discThresh >= 0 && Math.abs(s01) > Math.abs(discThresh))
							disconnects[j][k - 1] = true;
					}
					if (j > 0)
					{
						f10 = vertexArray[j - 1][k].y;
						s10 = (f00 - f10) / (vertexArray[j][k].x - vertexArray[j - 1][k].x);
						if (Math.abs(f00 - f10) >= Math.abs(range.length()))
							disconnects[j - 1][k] = true;
						if (discThresh >= 0 && Math.abs(s10) > Math.abs(discThresh))
							disconnects[j - 1][k] = true;
					}
					if (k > 0 && j > 0)
					{
						f11 = vertexArray[j - 1][k - 1].y;
						s11 = (f00 - f11) / (Math
								.sqrt(sqr(vertexArray[j][k].x - vertexArray[j - 1][k - 1].x) + sqr(vertexArray[j][k].z - vertexArray[j - 1][k - 1].z)));
						
						if (Math.abs(f00 - f11) >= Math.abs(range.length()))
							disconnects[j - 1][k - 1] = true;
						if (discThresh >= 0 && Math.abs(s11) > Math.abs(discThresh))
							disconnects[j - 1][k - 1] = true;
					}
					
					// Slope recording
					if (k == 0 && j == 0)
					{
						highestSlope = 0;
					}
					else if (k > 0 && j > 0)
					{
						double tSlope = Compare.highestSlopeBetween(vertexArray[j][k], vertexArray[j - 1][k], vertexArray[j][k - 1],
								vertexArray[j - 1][k - 1]);
						tSlope = Math.abs(tSlope);
						if (tSlope > highestSlope && !disconnects[j - 1][k - 1] && !disconnects[j - 1][k] && !disconnects[j - 1][k - 1])
							highestSlope = tSlope;
						slope[j - 1][k - 1] = tSlope;
					}
					
					///// Mitigate Z-Fighting
					if (ConfigVars.RenderingConfigs.zFightCorrection)
					{
						double scooch = ((k * 0.02) / (vertexArray[j].length + k));
						vertexArray[j][k] = new Vec3d(vertexArray[j][k].x + scooch, vertexArray[j][k].y + scooch, vertexArray[j][k].z + scooch);
					}
					i++;
					k++;
				}
				k = 0;
				j++;
			}
		}
		catch (NullPointerException e)
		{
			setErrored(true, "NullPointerException while generating mesh. See log for stacktrace.");
			e.printStackTrace();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			setErrored(true, "Evaluation requires more arguments than have been supplied.");
			e.printStackTrace();
		}
		catch (InfiniteCalculationsException e)
		{
			NumberFormat f = NumberFormat.getInstance();
			setErrored(true, "Evaluation of this sum has exceeded the maximum number of iterations (" + f.format(ConfigVars.GraphingConfigs.maxSeries)
					+ "), and so has been considered impossible to finish. This threshold can be" + " changed in the config.");
		}
	}
	
	protected void postMesh()
	{
		Vec3d v1, v2;
		double tX = aggDiscThresh * scale.x;
		double tY = aggDiscThresh * scale.y;
		double tZ = aggDiscThresh * scale.z;
		
		///////////////////////////////////////////////// Aggressive discontinuity detection.
		if (aggDiscThresh != 0)
		{
			for (int j = 0; j < vertexArray.length; j++)
			{
				for (int k = 0; k < vertexArray[j].length; k++)
				{
					v1 = vertexArray[j][k];
					if (k > 0)
					{
						v2 = vertexArray[j][k - 1];
						if (Compare.distanceBetweenGreaterThan(v1.x, v2.x, tX) || Compare.distanceBetweenGreaterThan(v1.y, v2.y, tY)
								|| Compare.distanceBetweenGreaterThan(v1.z, v2.z, tZ))
							disconnects[j][k - 1] = true;
					}
					if (j > 0)
					{
						v2 = vertexArray[j - 1][k];
						if (Compare.distanceBetweenGreaterThan(v1.x, v2.x, tX) || Compare.distanceBetweenGreaterThan(v1.y, v2.y, tY)
								|| Compare.distanceBetweenGreaterThan(v1.z, v2.z, tZ))
							disconnects[j - 1][k] = true;
					}
					if (j > 0 && k > 0)
					{
						v2 = vertexArray[j - 1][k - 1];
						if (Compare.distanceBetweenGreaterThan(v1.x, v2.x, tX) || Compare.distanceBetweenGreaterThan(v1.y, v2.y, tY)
								|| Compare.distanceBetweenGreaterThan(v1.z, v2.z, tZ))
							disconnects[j - 1][k - 1] = true;
					}
				}
			}
		}
		////////////////////////////////////// Collision
		
		if (collision && vertexArray.length > 0 && vertexArray[0].length > 0)
		{
			collisionArray = new AxisAlignedBB[vertexArray.length * vertexArray[0].length];
			int c = 0;
			for (int j = 0; j + 1 < vertexArray.length; j++)
			{
				for (int k = 0; k + 1 < vertexArray[j].length; k++)
				{
					if (disconnects[j][k])
						continue;
					
					v1 = vertexArray[j][k];
					v2 = vertexArray[j + 1][k + 1];
					if (v1 == null || v2 == null)
						continue;
					
					collisionArray[c] = new AxisAlignedBB(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z).offset(this.pos);
					c++;
				}
			}
		}
		
		renderReady = true;
		regenGraph = true;
	}
	
	protected void aggThresh(int j, int k)
	{
		if (aggDiscThresh == 0 || errored)
			return;
		Vec3d v1 = vertexArray[j][k];
		Vec3d v2;
		if (k > 0)
		{
			v2 = vertexArray[j][k - 1];
			if (Compare.distanceBetweenGreaterThan(v1, v2, aggDiscThresh))
				disconnects[j][k - 1] = true;
		}
		if (j > 0)
		{
			v2 = vertexArray[j - 1][k];
			if (Compare.distanceBetweenGreaterThan(v1, v2, aggDiscThresh))
				disconnects[j - 1][k] = true;
		}
	}
	
	protected void scaleTrans(Vec3d vec)
	{
		vec = new Vec3d(
                (vec.x * scale.x) + translation.x + 0.5,
                (vec.y * scale.y) + translation.y + 0.5,
				(vec.z * scale.z) + translation.z + 0.5);
	}
	
	protected void height(int j, int k)
	{
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
	
	protected void scaleTrans(Alt3d alt)
	{
		alt
                .rotateAroundX(rotation.x)
                .rotateAroundY(rotation.y)
                .rotateAroundZ(rotation.z)
                .mult(
                        scale.x,
                        scale.y,
                        scale.z, Alt3d.CARTESIAN)
				.add(
                        translation.x + 0.5,
                        translation.y + 0.5,
                        translation.z + 0.5,
                        Alt3d.CARTESIAN);
	}
	
	protected double scaleTrans(double y)
	{
		return (y * scale.y) + translation.y + 0.5;
	}
	
	////////
	
	protected double sqr(double val)
	{
		return val * val;
	}
	
	////////////////////////////////////// Collision
	
	@SubscribeEvent
	public void collisionEvent(GetCollisionBoxesEvent event) {
        Entity ent = event.getEntity();
        if (!this.isRemoved()) {
            if (collision && ent != null && event.getWorld() != null && pos != null
                    && (COLLISION_RANGE_SQ == 0
                    || pos.distanceSq(ent.posX - translation.x, ent.posY - translation.y, ent.posZ - translation.z, false) < COLLISION_RANGE_SQ)
                    && !((World) event.getWorld()).isBlockPowered(pos))
                if (addCollisionAABBs(event.getAabb(), event.getCollisionBoxesList())) {
                    ent.collided = true;
                }
        } else
            GraphingCalculator3D.proxy.deleteVertexData(this);
    }
	
	public boolean addCollisionAABBs(AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes)
	{
		if (entityBox == null)
			return false;
		boolean hit = false;
        for (AxisAlignedBB axisAlignedBB : collisionArray) {
            if (axisAlignedBB != null && entityBox.intersects(axisAlignedBB)) {
                collidingBoxes.add(axisAlignedBB);
                hit = true;
            }
        }
		return hit;
	}
	
	///////////////////////////////////// Non-default Getters & Setters
	
	public void setErrored(boolean errored, String message) {
		this.setErrored(errored);
		this.setErrorMessage(message);
		this.setErroredFunction(this.getFunctionText());
		System.out.println(errorMessage);
	}
	
	public void setErrored(boolean errored, String message, String erroredFunction) {
		this.setErrored(errored, message);
		this.setErroredFunction(erroredFunction);
	}
	
	public int getArraySize()
	{
		return vertexArray.length * vertexArray[0].length;
	}
	
	@TriggerOn(GCEvents.GC_CONFIG)
	public void updateConfigs() {
		a = ConfigVars.GraphingConfigs.a;
		b = ConfigVars.GraphingConfigs.b;
		c = ConfigVars.GraphingConfigs.c;
		RENDER_DISTANCE_SQ = ConfigVars.RenderingConfigs.renderDistance * ConfigVars.RenderingConfigs.renderDistance;
		ALPHA_CUTOFF = ConfigVars.RenderingConfigs.alphaCutoff;
		COLLISION_RANGE_SQ = ConfigVars.GraphingConfigs.collisionRange * ConfigVars.GraphingConfigs.collisionRange;
		if (this.hasWorld())
			if (this.getWorld().isRemote)
				genMesh();
	}
	
	///////////////////////////////////// Default Getters & Setters
	
	/**
	 * @return the function
	 */
	public Expression getFunction()
	{
		return function;
	}
	
	/**
	 * @param function
	 *           the function to set
	 */
	public void setFunction(Expression function)
	{
		this.function = function;
		if (this.function != null)
			this.function.prepareSeries();
	}
	
	/**
	 * @return the mesh
	 */
	public Vec3d[] getMesh()
	{
		return mesh;
	}
	
	/**
	 * @param mesh
	 *           the mesh to set
	 */
	public void setMesh(Vec3d[] mesh)
	{
		this.mesh = mesh;
	}
	
	/**
	 * @return the errored
	 */
	public boolean isErrored()
	{
		return errored;
	}
	
	/**
	 * @param errored
	 *           the errored to set
	 */
	public void setErrored(boolean errored)
	{
		this.errored = errored;
	}
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	/**
	 * @param errorMessage
	 *           the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @return the vertexArray
	 */
	public Vec3d[][] getVertexArray()
	{
		return vertexArray;
	}
	
	/**
	 * @param vertexArray
	 *           the vertexArray to set
	 */
	public void setVertexArray(Vec3d[][] vertexArray)
	{
		this.vertexArray = vertexArray;
	}
	
	/**
	 * @return the resolution
	 */
	public double getResolution()
	{
		return resolution;
	}
	
	/**
	 * @param d
	 *           the resolution to set
	 */
	public void setResolution(double d)
	{
		this.resolution = d;
	}
	
	/**
	 * @return the domainA
	 */
	public Domain getDomainA() {
		return domainA;
	}
	
	/**
	 * @param domainA
	 *           the domainA to set
	 */
	public void setDomainA(Domain domainA) {
		this.domainA = domainA;
	}
	
	/**
	 * @return the domainB
	 */
	public Domain getDomainB() {
		return domainB;
	}
	
	/**
	 * @param domainB
	 *           the domainB to set
	 */
	public void setDomainB(Domain domainB) {
		this.domainB = domainB;
	}
	
	/**
	 * @return the range
	 */
	public Domain getRange() {
		return range;
	}
	
	/**
	 * @param range
	 *           the range to set
	 */
	public void setRange(Domain range) {
		this.range = range;
	}

    /**
	 * @return the erroredFunction
	 */
	public String getErroredFunction()
	{
		return erroredFunction;
	}
	
	/**
	 * @param erroredFunction
	 *           the erroredFunction to set
	 */
	public void setErroredFunction(String erroredFunction)
	{
		this.erroredFunction = erroredFunction;
	}
	
	/**
	 * @return the functionText
	 */
	public String getFunctionText()
	{
		return functionText;
	}
	
	/**
	 * @param functionText
	 *           the functionText to set
	 */
	public void setFunctionText(String functionText)
	{
		this.functionText = functionText;
	}
	
	/**
	 * @return the discThresh
	 */
	public double getDiscThresh()
	{
		return discThresh;
	}
	
	/**
	 * @param discThresh
	 *           the discThresh to set
	 */
	public void setDiscThresh(double discThresh)
	{
		this.discThresh = discThresh;
	}
	
	/**
	 * @return cropToRange
	 */
	public boolean cropToRange()
	{
		return cropToRange;
	}
	
	/**
	 * @param cropToRange
	 */
	public void cropToRange(boolean cropToRange)
	{
		this.cropToRange = cropToRange;
	}
	
	/**
	 * @return aggDiscThresh
	 */
	public double getAggDiscThresh()
	{
		return aggDiscThresh;
	}
	
	/**
	 * @param aggDiscThresh aggDiscThresh to set
	 */
	public void setAggDiscThresh(double aggDiscThresh)
	{
		this.aggDiscThresh = aggDiscThresh;
	}
	
	public enum GCPreviousState
	{
		TESSELLATOR, GL_CALL_LIST;
	}
}
