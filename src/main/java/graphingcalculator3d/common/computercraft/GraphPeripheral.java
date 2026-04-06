package graphingcalculator3d.common.computercraft;

import java.util.LinkedHashMap;
import java.util.Map;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.math.expression.Expression;
import graphingcalculator3d.common.util.nbthandler.Domain;
import graphingcalculator3d.common.util.networking.GCPacketHandler;
import graphingcalculator3d.common.util.networking.packets.PacketGC;
import net.minecraftforge.network.PacketDistributor;
import org.joml.Vector3d;

public record GraphPeripheral(TileGCBase graph) implements IPeripheral {

    @Override
    public String getType() {
        return "Graph";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof GraphPeripheral peripheral && peripheral.graph == graph;
    }

    @Override
    public Object getTarget() {
        return graph;
    }

    @LuaFunction(value = "refresh", mainThread = true)
    public final void refresh() {
        graph.setErrored(false);
        graph.genMesh();
        syncGraph();
    }

    @LuaFunction(value = "setDomainA", mainThread = true)
    public final void setDomainA(IArguments arguments) throws LuaException {
        graph.setDomainA(readDomain(arguments, 0));
    }

    @LuaFunction(value = "getDomainA", mainThread = true)
    public final Map<Integer, Double> getDomainA() {
        return toDoubleMap(graph.getDomainA());
    }

    @LuaFunction(value = "setDomainB", mainThread = true)
    public final void setDomainB(IArguments arguments) throws LuaException {
        graph.setDomainB(readDomain(arguments, 0));
    }

    @LuaFunction(value = "getDomainB", mainThread = true)
    public final Map<Integer, Double> getDomainB() {
        return toDoubleMap(graph.getDomainB());
    }

    @LuaFunction(value = "setRange", mainThread = true)
    public final void setRange(IArguments arguments) throws LuaException {
        graph.setRange(readDomain(arguments, 0));
    }

    @LuaFunction(value = "getRange", mainThread = true)
    public final Map<Integer, Double> getRange() {
        return toDoubleMap(graph.getRange());
    }

    @LuaFunction(value = "setErrored", mainThread = true)
    public final void setErrored(boolean errored) {
        graph.setErrored(errored);
    }

    @LuaFunction(value = "getErrored", mainThread = true)
    public final boolean getErrored() {
        return graph.isErrored();
    }

    @LuaFunction(value = "setFunction", mainThread = true)
    public final void setFunction(String text) {
        graph.setFunction(Expression.parseFromString(text));
        graph.setFunctionText(text);
    }

    @LuaFunction(value = "getFunction", mainThread = true)
    public final String getFunction() {
        return graph.getFunctionText();
    }

    @LuaFunction(value = "setRGBA", mainThread = true)
    public final void setRGBA(IArguments arguments) throws LuaException {
        graph.rgba = readIntArray(arguments, 0, 5);
    }

    @LuaFunction(value = "getRGBA", mainThread = true)
    public final Map<Integer, Integer> getRGBA() {
        return toIntMap(graph.rgba);
    }

    @LuaFunction(value = "setCollision", mainThread = true)
    public final void setCollision(boolean value) {
        graph.collision = value;
    }

    @LuaFunction(value = "getCollision", mainThread = true)
    public final boolean getCollision() {
        return graph.collision;
    }

    @LuaFunction(value = "setColorSlope", mainThread = true)
    public final void setColorSlope(boolean value) {
        graph.colorSlope = value;
    }

    @LuaFunction(value = "getColorSlope", mainThread = true)
    public final boolean getColorSlope() {
        return graph.colorSlope;
    }

    @LuaFunction(value = "setTexture", mainThread = true)
    public final void setTexture(String texture) {
        graph.tex = texture;
    }

    @LuaFunction(value = "getTexture", mainThread = true)
    public final String getTexture() {
        return graph.tex;
    }

    @LuaFunction(value = "setTileCount", mainThread = true)
    public final void setTileCount(int tileCount) {
        graph.tileCount = tileCount;
    }

    @LuaFunction(value = "getTileCount", mainThread = true)
    public final int getTileCount() {
        return graph.tileCount;
    }

    @LuaFunction(value = "setResolution", mainThread = true)
    public final void setResolution(double resolution) {
        graph.setResolution(resolution);
    }

    @LuaFunction(value = "getResolution", mainThread = true)
    public final double getResolution() {
        return graph.getResolution();
    }

    @LuaFunction(value = "setScale", mainThread = true)
    public final void setScale(IArguments arguments) throws LuaException {
        graph.scale = readVector3d(arguments, 0);
    }

    @LuaFunction(value = "getScale", mainThread = true)
    public final Map<Integer, Double> getScale() {
        return toDoubleMap(graph.scale);
    }

    @LuaFunction(value = "setTranslation", mainThread = true)
    public final void setTranslation(IArguments arguments) throws LuaException {
        graph.translation = readVector3d(arguments, 0);
    }

    @LuaFunction(value = "getTranslation", mainThread = true)
    public final Map<Integer, Double> getTranslation() {
        return toDoubleMap(graph.translation);
    }

    @LuaFunction(value = "setRotation", mainThread = true)
    public final void setRotation(IArguments arguments) throws LuaException {
        graph.rotation = readVector3d(arguments, 0);
    }

    @LuaFunction(value = "getRotation", mainThread = true)
    public final Map<Integer, Double> getRotation() {
        return toDoubleMap(graph.rotation);
    }

    @LuaFunction(value = "setDiscThresh", mainThread = true)
    public final void setDiscThresh(double discThresh) {
        graph.setDiscThresh(discThresh);
    }

    @LuaFunction(value = "getDiscThresh", mainThread = true)
    public final double getDiscThresh() {
        return graph.getDiscThresh();
    }

    @LuaFunction(value = "setAggDiscThresh", mainThread = true)
    public final void setAggDiscThresh(double aggDiscThresh) {
        graph.setAggDiscThresh(aggDiscThresh);
    }

    @LuaFunction(value = "getAggDiscThresh", mainThread = true)
    public final double getAggDiscThresh() {
        return graph.getAggDiscThresh();
    }

    @LuaFunction(value = "setCropToRange", mainThread = true)
    public final void setCropToRange(boolean cropToRange) {
        graph.cropToRange(cropToRange);
    }

    @LuaFunction(value = "getCropToRange", mainThread = true)
    public final boolean getCropToRange() {
        return graph.cropToRange();
    }

    private void syncGraph() {
        GCPacketHandler.GRAPH_SYNC.send(PacketDistributor.DIMENSION.with(() -> graph.getLevel().dimension()), new PacketGC(graph));
    }

    private static Vector3d readVector3d(IArguments arguments, int index) throws LuaException {
        Map<?, ?> table = readNumericTable(arguments, index);

        double x, y, z;

        if (getSequential(table, 0) instanceof Number numberX) {
            x = numberX.doubleValue();
        } else {
            throw new LuaException("X must be numeric.");
        }

        if (getSequential(table, 1) instanceof Number numberY) {
            y = numberY.doubleValue();
        } else {
            throw new LuaException("Y must be numeric.");
        }

        if (getSequential(table, 2) instanceof Number numberZ) {
            z = numberZ.doubleValue();
        } else {
            throw new LuaException("Z must be numeric.");
        }

        return new Vector3d(x, y, z);
    }

    private static Domain readDomain(IArguments arguments, int index) throws LuaException {
        Map<?, ?> table = readNumericTable(arguments, index);

        double min, max;

        if (getSequential(table, 0) instanceof Number minNumber) {
            min = minNumber.doubleValue();
        } else {
            throw new LuaException("Min must be numeric.");
        }

        if (getSequential(table, 1) instanceof Number maxNumber) {
            max = maxNumber.doubleValue();
        } else {
            throw new LuaException("Max must be numeric.");
        }

        return new Domain(min, max);
    }

    private static double[] readDoubleArray(IArguments arguments, int index, int minSize) throws LuaException {
        Map<?, ?> table = readNumericTable(arguments, index);
        int maxIndex = maxDenseIndex(table);
        int size = Math.max(minSize, maxIndex);
        double[] out = new double[size];

        for (int i = 1; i <= maxIndex; i++) {
            Object value = getSequential(table, i);
            if (!(value instanceof Number number)) {
                throw new LuaException("Array entry " + i + " must be numeric.");
            }
            out[i - 1] = number.doubleValue();
        }

        return out;
    }

    private static int[] readIntArray(IArguments arguments, int index, int minSize) throws LuaException {
        Map<?, ?> table = readNumericTable(arguments, index);
        int maxIndex = maxDenseIndex(table);
        int size = Math.max(minSize, maxIndex);
        int[] out = new int[size];

        for (int i = 1; i <= maxIndex; i++) {
            Object value = getSequential(table, i);
            if (!(value instanceof Number number)) {
                throw new LuaException("Array entry " + i + " must be numeric.");
            }
            out[i - 1] = number.intValue();
        }

        return out;
    }

    private static Map<?, ?> readNumericTable(IArguments arguments, int index) throws LuaException {
        Object value = arguments.get(index);
        if (!(value instanceof Map<?, ?> table)) {
            throw new LuaException("Expected table argument at index " + (index + 1) + ".");
        }
        return table;
    }

    private static int maxDenseIndex(Map<?, ?> table) throws LuaException {
        int maxIndex = 0;
        for (Object key : table.keySet()) {
            if (!(key instanceof Number number)) {
                throw new LuaException("The supplied array contains non-numerical keys.");
            }

            double raw = number.doubleValue();
            int asInt = (int) raw;
            if (asInt <= 0 || raw != asInt) {
                throw new LuaException("The supplied array contains non-integral or non-positive keys.");
            }
            maxIndex = Math.max(maxIndex, asInt);
        }

        for (int i = 1; i <= maxIndex; i++) {
            if (getSequential(table, i) == null) {
                throw new LuaException("Indices in the array must not be sparsely populated.");
            }
        }

        return maxIndex;
    }

    private static Object getSequential(Map<?, ?> table, int index) {
        Object value = table.get((double) index);
        if (value != null) return value;
        value = table.get(index);
        if (value != null) return value;
        return table.get((long) index);
    }

    private static Map<Integer, Double> toDoubleMap(Domain values) {
        var out = new LinkedHashMap<Integer, Double>();
        out.put(0, values.min());
        out.put(1, values.max());
        return out;
    }

    private static Map<Integer, Double> toDoubleMap(Vector3d values) {
        var out = new LinkedHashMap<Integer, Double>();
        out.put(0, values.x());
        out.put(1, values.y());
        out.put(2, values.z());

        return out;
    }

    private static Map<Integer, Integer> toIntMap(int[] values) {
        LinkedHashMap<Integer, Integer> out = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i++) {
            out.put(i + 1, values[i]);
        }
        return out;
    }
}
