//package graphingcalculator3d.common.computercraft;
//
//import dan200.computercraft.api.lua.ILuaContext;
//import dan200.computercraft.api.lua.LuaException;
//import dan200.computercraft.api.peripheral.IComputerAccess;
//import dan200.computercraft.api.peripheral.IPeripheral;
//import graphingcalculator3d.common.computercraft.CCFunctionSet.CCFunction;
//import graphingcalculator3d.common.gameplay.tile.TileGCBase;
//import graphingcalculator3d.common.util.math.expression.Expression;
//import graphingcalculator3d.common.util.networking.GCPacketHandler;
//import graphingcalculator3d.common.util.networking.packets.PacketGC;
//
//public class GraphPeripheral implements IPeripheral
//{
//	private final TileGCBase graph;
//	private final CCFunctionSet functions;
//
//	public GraphPeripheral(TileGCBase graph)
//	{
//		this.graph = graph;
//		functions = getFunctions(graph);
//	}
//
//	private static CCFunctionSet getFunctions(TileGCBase graph)
//	{
//		CCFunctionSet functions = new CCFunctionSet();
//
//		functions.addFunc(CCFunction.issueVoid("refresh", (comp, args) ->
//		{
//			graph.setErrored(false);
//			graph.genMesh();
//			GCPacketHandler.GRAPH_SYNC.sendToDimension(new PacketGC(graph), graph.getWorld().provider.getDimension());
//		}));
//		functions.addSetGetArray("DomainA", Double[].class, 2, CCFunction.of(graph::setDomainA), CCFunction.of(graph::getDomainA));
//		functions.addSetGetArray("DomainB", Double[].class, 2, CCFunction.of(graph::setDomainB), CCFunction.of(graph::getDomainB));
//		functions.addSetGetArray("Range", Double[].class, 2, CCFunction.of(graph::setRange), CCFunction.of(graph::getRange));
//		functions.addSetGet("Errored", Boolean.class, graph::setErrored, graph::isErrored);
//		functions.addSetGet("Function", String.class, (txt) ->
//		{
//			graph.setFunction(Expression.parseFromString(txt));
//			graph.setFunctionText(txt);
//		}, graph::getFunctionText);
//		functions.addSetGetArray("RGBA", Double[].class, 5, CCFunction.ofInt((ar) -> { graph.rgba = ar; }), CCFunction.ofInt(() -> graph.rgba));
//		functions.addSetGet("Collision", Boolean.class, (b) -> { graph.collision = b; }, () -> graph.collision);
//		functions.addSetGet("ColorSlope", Boolean.class, (b) -> { graph.colorSlope = b; }, () -> graph.colorSlope);
//		functions.addSetGet("Texture", String.class, (tx) -> { graph.tex = tx; }, () -> graph.tex);
//		functions.addSetGet("TileCount", Integer.class, (in) -> { graph.tileCount = in; }, () -> graph.tileCount);
//		functions.addSetGet("Resolution", Double.class, graph::setResolution, graph::getResolution);
//		functions.addSetGetArray("Scale", Double[].class, 3, CCFunction.of((ar) -> { graph.scale = ar; }), CCFunction.of(() -> graph.scale));
//		functions.addSetGetArray("Translation", Double[].class, 3, CCFunction.of((ar) -> { graph.translation = ar; }), CCFunction.of(() -> graph.translation));
//		functions.addSetGetArray("Rotation", Double[].class, 3, CCFunction.of((ar) -> { graph.rotation = ar; }), CCFunction.of(() -> graph.rotation));
//		functions.addSetGet("DiscThresh", Double.class, graph::setDiscThresh, graph::getDiscThresh);
//		functions.addSetGet("AggDiscThresh", Double.class, graph::setAggDiscThresh, graph::getAggDiscThresh);
//		functions.addSetGet("CropToRange", Boolean.class, graph::cropToRange, graph::cropToRange);
//
//		return functions;
//	}
//
//	@Override
//	public String getType()
//	{
//		return "Graph";
//	}
//
//	@Override
//	public String[] getMethodNames()
//	{
//		return functions.getMethodNames();
//	}
//
//	@Override
//	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException
//	{
//		return functions.callMethod(computer, context, method, arguments);
//	}
//
//	@Override
//	public Object getTarget()
//	{
//		return graph;
//	}
//
//	@Override
//	public boolean equals(IPeripheral other)
//	{
//		return other.getTarget().equals(this.getTarget());
//	}
//
//}
