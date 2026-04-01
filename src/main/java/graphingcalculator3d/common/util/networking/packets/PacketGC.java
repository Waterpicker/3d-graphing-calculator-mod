package graphingcalculator3d.common.util.networking.packets;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.math.expression.Expression;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketGC {
	public PacketGC() {}
	
	public int x, y, z;
	public Expression function;
	public String tex = "";
	public boolean crop = GCNBT.GC_CROP_TO_RANGE.defaultVal();
	public int[] rgba = GCNBT.GC_RGBA.defaultVal();
	public boolean colorSlope = GCNBT.GC_COLOR_SLOPE.defaultVal();
	public int tileCount = GCNBT.GC_TILE_COUNT.defaultVal();
	public double[] domainX = GCNBT.GC_DOMAIN_A.defaultVal();
	public double[] range = GCNBT.GC_RANGE.defaultVal();
	public double[] domainZ = GCNBT.GC_DOMAIN_B.defaultVal();
	public double[] scale = GCNBT.GC_SCALE.defaultVal();
	public double[] translation = GCNBT.GC_TRANSLATION.defaultVal();
	public double[] rotation = GCNBT.GC_ROTATION.defaultVal();
	public double resolution = GCNBT.GC_RESOLUTION.defaultVal();
	public double discThresh = GCNBT.GC_DISC_THRESH.defaultVal();
	public double aggDiscThresh = GCNBT.GC_AGG_DISC_THRESH.defaultVal();
	public boolean collision = GCNBT.GC_COLLISION.defaultVal();
	
	public PacketGC(TileGCBase tile) {
		x = tile.getPos().getX();
		y = tile.getPos().getY();
		z = tile.getPos().getZ();
		
		function = tile.getFunction();
		tex = tile.tex;
		crop = tile.cropToRange();
		rgba = tile.rgba;
		colorSlope = tile.colorSlope;
		tileCount = tile.tileCount;
		domainX = tile.domainA;
		range = tile.range;
		domainZ = tile.domainB;
		scale = tile.scale;
		translation = tile.translation;
		rotation = tile.rotation;
		resolution = tile.getResolution();
		discThresh = tile.getDiscThresh();
		aggDiscThresh = tile.getAggDiscThresh();
		collision = tile.collision;
	}

    public PacketGC(PacketBuffer buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();

        String string = buf.readString(32767);
        function = string.isEmpty() ? null : Expression.parseFromString(string);
        tex = buf.readString(32767);

        crop = buf.readBoolean();
        IntStream.range(0, 5).forEach(i -> rgba[i] = buf.readInt());
        colorSlope = buf.readBoolean();
        collision = buf.readBoolean();

        tileCount = buf.readInt();
        resolution = buf.readDouble();
        discThresh = buf.readDouble();
        aggDiscThresh = buf.readDouble();

        IntStream.range(0, 3).forEach(i -> scale[i] = buf.readDouble());
        IntStream.range(0, 3).forEach(i -> translation[i] = buf.readDouble());
        IntStream.range(0, 3).forEach(i -> rotation[i] = buf.readDouble());
        IntStream.range(0, 2).forEach(i -> domainX[i] = buf.readDouble());
        IntStream.range(0, 2).forEach(i -> range[i] = buf.readDouble());
        IntStream.range(0, 2).forEach(i -> domainZ[i] = buf.readDouble());
    }

	public void toBytes(PacketBuffer buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeString(function != null ? function.writeToString() : "");
        buf.writeString(tex != null ? tex : "");

        buf.writeBoolean(crop);

        buf.writeVarIntArray(rgba);
        buf.writeBoolean(colorSlope);
        buf.writeBoolean(collision);

        buf.writeInt(tileCount);
        buf.writeDouble(resolution);
        buf.writeDouble(discThresh);
        buf.writeDouble(aggDiscThresh);
        Arrays.stream(scale, 0, 3).forEach(buf::writeDouble);
        Arrays.stream(translation, 0, 3).forEach(buf::writeDouble);
        Arrays.stream(rotation, 0, 3).forEach(buf::writeDouble);
        Arrays.stream(domainX, 0, 2).forEach(buf::writeDouble);
        Arrays.stream(range, 0, 2).forEach(buf::writeDouble);
        Arrays.stream(domainZ, 0, 2).forEach(buf::writeDouble);
    }

	public static void handle(PacketGC message, Supplier<NetworkEvent.Context> ctx) {
        GraphingCalculator3D.proxy.handleGCPacket(message, ctx.get());
	}
}
