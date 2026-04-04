package graphingcalculator3d.client.gui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SetupGui
{
    public static int centerVal(int negBound, int posBound, int size)
    {
        return negBound + (((posBound - negBound) - size) / 2);
    }
}
