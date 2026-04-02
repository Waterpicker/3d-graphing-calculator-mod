package graphingcalculator3d.common.util.config;

//import graphingcalculator3d.common.GraphingCalculator3D;
//import net.minecraftforge.common.config.Config;
//import net.minecraftforge.common.config.Config.Comment;
//import net.minecraftforge.common.config.Config.Name;
//import net.minecraftforge.common.config.Config.RangeDouble;
//import net.minecraftforge.common.config.Config.RangeInt;

//TODO: Reanble config with cloth config in 1.14
//@Config(modid = GraphingCalculator3D.MODID, name = "3D Graphing Calculator")
public class ConfigVars
{
//	@Name("Rendering")
//	@Comment
//	({
//		"Rendering-related configurations."
//	})
	public static Rendering RenderingConfigs = new Rendering();
	
//	@Name("Graphing")
//	@Comment
//	({
//		"Graphing-related configurations."
//	})
	public static Graphing GraphingConfigs = new Graphing();
	
	///////////////////

	public static class Rendering
	{
//		@Name("Render Graphs")
//		@Comment
//		({
//			"Define whether or not the graphs will render."
//		})
		public boolean render = true;
		
//		@Name("Maximum Graph Complexity")
//		@Comment
//		({
//			"Define the maximum number of tiles that the mod will try to render per calculator.",
//			"The number of tiles that compose a graph is equal to (domainASize * domainBSize * resolution * resolution).",
//			"I find that with a graph of 2,560,000 tiles, I get around 1 frame per second. More than that is probably accidental."
//		})
//		@RangeInt(min = 1)
		public int maxComplexity = 2560000;
		
//		@Name("Render Distance")
//		@Comment
//		({
//			"Define the maximum render distance of graphs.",
//			"Graphs beyond this distance will not be rendered."
//		})
		public int renderDistance = 100;
		
//		@Name("Z-Fight Correction")
//		@Comment
//		({
//			"Automatically account for z-fighting?",
//			"This results in a slight distortion of graph, up to about 0.06 blocks linear movement of vertices at default scaling. Noticable in spheres or connected shapes."
//		})
		public boolean zFightCorrection = false;
		
//		@Name("Alpha-Cutoff")
//		@Comment
//		({
//			"Define the minimum alpha value required to trigger fancy-transparancy rendering.",
//			"This setting applies only to \"auto\" mode. The others will be unaffected."
//		})
		public int alphaCutoff = 245;
		
//		@Name("Shade Graph")
//		@Comment
//		({
//			"Define whether or not lighting is dynamic based on height. (~No performance impact.)",
//			"With this off, the graphs will no longer have shadows or highlights."
//		})
		public boolean doLight = true;
		
//		@Name("Frames Between Sorting")
//		@Comment
//		({
//			"Define the number of frames between each sorting of transparent \"fancy\" vertices.",
//			"This sorting is what keeps transparent layers rendering in order (graph behind renders behind, not in front),",
//			"but impacts performance when called. \"fast\" rendering does not sort at all (settable in GUI)."
//		})
		public int framesPerSort = 60;
		
//		@Name("Light-Level Scaling")
//		@Comment
//		({
//			"Define the base light level of graphs.",
//			"Lower means that graphs will generally be darker, while higher means that they will be brighter."
//		})
//		@RangeInt(min = 0, max = 220)
		public int lightScale = 80;
		
//		@Name("Color Revolutions")
//		@Comment
//		({
//			"Define the number of times slope-colored graphs shift through the spectrum.",
//			"Higher values will mean more, smaller bands of color, while lower values will mean fewer, larger bands."
//		})
//		@RangeDouble(min = 0)
		public double colorScale = 0.83;
		
	}
	
	public static class Graphing
	{
//		@Name("GUI Decimal Places")
//		@Comment
//		({
//			"Number of decimal places to display in the GUI's fields."
//		})
//		@RangeInt(min = 0)
		public int decPlaces = 6;
		
//		@Name("A")
//		@Comment
//		({
//			"Sort of a scale factor for some systems.",
//			"As an example, the two poles in Bipolar Cylindrical coordinates are located at +/- a"
//		})
//		@RangeDouble
		public double a = 6;
		
//		@Name("B")
//		@Comment
//		({
//			"Similar to a. Applies to Conical coordinates."
//		})
//		@RangeDouble
		public double b = 2;
		
//		@Name("C")
//		@Comment
//		({
//			"Similar to b. Applies to Conical coordinates."
//		})
		public double c = 1;
		
//		@Name("Collision Checking Range")
//		@Comment
//		({
//			"Only checks for collisions within this radius of graph origin, or over any distance if 0 is inputted.",
//			"This is measured per-entity. In other words, a creeper standing on a collidable graph WON'T fall through as soon as you move past this limit.",
//			"The origin of a graph is equal to the position of the calculator + the translation field's values."
//		})
		public double collisionRange = 30;
		
//		@Name("Maximum Series Attempts")
//		@Comment
//		({
//			"Try at most this many times to calculate the result of a series (e.g: summation).",
//			"If the series requires more than this many iterations, it's assumed to require infinitely many iterations, and is given up as futile."
//		})
		public int maxSeries = 10000000;
	}
}
