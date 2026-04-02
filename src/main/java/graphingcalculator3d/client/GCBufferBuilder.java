package graphingcalculator3d.client;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Same as a normal BufferBuilder, but doesn't reset, and is used in the GCTessellator. 
 * @author SerpentDagger
 */
@OnlyIn(Dist.CLIENT)
public class GCBufferBuilder extends BufferBuilder
{
	public GCBufferBuilder(int bufferSizeIn)
	{
		super(bufferSizeIn);
	}
	
	@Override
	public void finishDrawing()
	{
		this.getByteBuffer().position(0);
		this.getByteBuffer().limit(this.getVertexCount() * this.getVertexFormat().getIntegerSize() * 4);
	}
	
	@Override
	public void reset()
	{
		//Your vile, resetting ways are no match for me! Bwahahaha! Suffer in your new-found efficiency!
	}
}
