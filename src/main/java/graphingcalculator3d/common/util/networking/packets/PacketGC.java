package graphingcalculator3d.common.util.networking.packets;

import graphingcalculator3d.common.GraphingCalculator3D;
import graphingcalculator3d.common.gameplay.tile.TileGCBase;
import graphingcalculator3d.common.util.math.expression.Expression;
import graphingcalculator3d.common.util.nbthandler.Domain;
import graphingcalculator3d.common.util.nbthandler.GCNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketGC {
	
	public int x, y, z;
	public Expression function;
	public String tex = "";
	public boolean crop = GCNBT.GC_CROP_TO_RANGE.defaultVal();
	public int[] rgba = GCNBT.GC_RGBA.defaultVal();
	public boolean colorSlope = GCNBT.GC_COLOR_SLOPE.defaultVal();
	public int tileCount = GCNBT.GC_TILE_COUNT.defaultVal();
	public Domain domainX = GCNBT.GC_DOMAIN_A.defaultVal();
	public Domain range = GCNBT.GC_RANGE.defaultVal();
	public Domain domainZ = GCNBT.GC_DOMAIN_B.defaultVal();
	public Vector3d scale = GCNBT.GC_SCALE.defaultVal();
	public Vector3d translation = GCNBT.GC_TRANSLATION.defaultVal();
	public Vector3d rotation = GCNBT.GC_ROTATION.defaultVal();
	public double resolution = GCNBT.GC_RESOLUTION.defaultVal();
	public double discThresh = GCNBT.GC_DISC_THRESH.defaultVal();
	public double aggDiscThresh = GCNBT.GC_AGG_DISC_THRESH.defaultVal();
	public boolean collision = GCNBT.GC_COLLISION.defaultVal();
	
	public PacketGC(TileGCBase tile) {
		x = tile.getBlockPos().getX();
		y = tile.getBlockPos().getY();
		z = tile.getBlockPos().getZ();
		
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

        String string = buf.readUtf();
        function = string.isEmpty() ? null : Expression.parseFromString(string);
        tex = buf.readUtf();

        crop = buf.readBoolean();
        rgba = buf.readVarIntArray();
        colorSlope = buf.readBoolean();
        collision = buf.readBoolean();

        tileCount = buf.readInt();
        resolution = buf.readDouble();
        discThresh = buf.readDouble();
        aggDiscThresh = buf.readDouble();

        scale = readVec3(buf);
        translation = readVec3(buf);
        rotation = readVec3(buf);
        domainX = readDomain(buf);
        range = readDomain(buf);
        domainZ = readDomain(buf);
    }

    private Vector3d readVec3(PacketBuffer buffer) {
        return new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
    }

    private Domain readDomain(PacketBuffer buffer) {
        return new Domain(buffer.readDouble(), buffer.readDouble());
    }

    private void writeVec3(PacketBuffer buffer, Vector3d vec) {
        buffer.writeDouble(vec.x).writeDouble(vec.y).writeDouble(vec.z);
    }

    private void writeDomain(PacketBuffer buffer, Domain domain) {
        buffer.writeDouble(domain.min()).writeDouble(domain.max());
    }

	public void toBytes(PacketBuffer buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeUtf(function != null ? function.writeToString() : "");
        buf.writeUtf(tex != null ? tex : "");

        buf.writeBoolean(crop);

        buf.writeVarIntArray(rgba);
        buf.writeBoolean(colorSlope);
        buf.writeBoolean(collision);

        buf.writeInt(tileCount);
        buf.writeDouble(resolution);
        buf.writeDouble(discThresh);
        buf.writeDouble(aggDiscThresh);
        writeVec3(buf, scale);
        writeVec3(buf, translation);
        writeVec3(buf, rotation);
        writeDomain(buf, domainX);
        writeDomain(buf, range);
        writeDomain(buf, domainZ);
    }

	public static void handle(PacketGC message, Supplier<NetworkEvent.Context> ctx) {
        GraphingCalculator3D.proxy.handleGCPacket(message, ctx.get());
	}
}
