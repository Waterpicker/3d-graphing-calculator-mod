package graphingcalculator3d.common.util.math;

import graphingcalculator3d.common.util.math.expression.Expression.Evaluation;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

public class Compare
{
	public static final Random RNG = new Random();
	public static boolean withinRangeOf(double check, double checkAgainst, double range)
	{
		return (check < checkAgainst + range && check > checkAgainst - range);
	}
	
	public static boolean greaterThanByAtLeast(double check, double checkAgainst, double range)
	{
		return (check - checkAgainst > range);
	}
	
	public static boolean lessThanByAtLeast(double check, double checkAgainst, double range)
	{
		return (checkAgainst - check > range);
	}
	
	public static double wrappedRemainder(double num, double divide)
	{
		if (num >= divide)
		{
			while (num > divide)
			{
				num -= divide;
			}
			return num;
		}
		else
		{
			while (num < 0)
			{
				num += divide;
			}
			return num;
		}
	}
	
	public static boolean isEven(int val)
	{
		return (val % 2) == 0;
	}
	
	public static int digitsInBaseTen(int number)
	{
		int dig = 1;
		while (number >= 10)
		{
			number /= 10;
			dig++;
		}
		return dig;
	}
	
	public static boolean distanceBetweenGreaterThan(double p1, double p2, double dist)
	{
		return Math.abs(p1 - p2) > dist;
	}
	
	public static boolean distanceBetweenGreaterThanDistanceBetween(double p1, double p2, double p3, double p4)
	{
		return Math.abs(p1 - p2) > Math.abs(p3 - p4);
	}
	
	public static boolean distanceBetweenGreaterThanDistanceBetween(Vector3d v1, Vector3d v2, Vector3d v3, Vector3d v4)
	{
		return v1.distanceToSqr(v2) > v3.distanceToSqr(v4);
	}
	
	public static boolean distanceBetweenGreaterThan(Vector3d p1, Vector3d p2, double dist)
	{
		return p1.distanceToSqr(p2) > dist * dist;
	}
	
	public static boolean sameVec(Vector3d v1, Vector3d v2)
	{
		return v1.x == v2.x && v1.y == v2.y && v1.z == v2.z;
	}
	
	public static double average(double... values)
	{
		double total = 0;
		for (Double val : values)
		{
			total += val;
		}
		if (values.length > 0)
			total /= values.length;
		return total;
	}
	
	public static Vector3d average(Vector3d... values)
	{
		double totalX = 0;
		double totalY = 0;
		double totalZ = 0;
		int nulls = 0;
		for (Vector3d vec : values)
		{
			if (vec != null)
			{
				totalX += vec.x;
				totalY += vec.y;
				totalZ += vec.z;
			}
			else
				nulls++;
		}
		if (values.length > 0)
		{
			totalX /= (values.length - nulls);
			totalY /= (values.length - nulls);
			totalZ /= (values.length - nulls);
		}
		return new Vector3d(totalX, totalY, totalZ);
	}
	
	public static double[] computeOver(double[] vals, Evaluation computation)
	{
		double[] out = new double[vals.length];
		for (int i = 0; i < vals.length; i++)
		{
			out[i] = computation.evaluate(new double[] { vals[i] });
		}
		return out;
	}
	
	public static double highestSlopeBetween(Vector3d... inputs)
	{
        Vector3d h = inputs[0];
        Vector3d l = inputs[0];
		for (Vector3d vec : inputs)
		{
			if (vec.y > h.y)
				h = vec;
			if (vec.y < l.y)
				l = vec;
		}
		double rise = h.y - l.y;
		double run = Math.hypot(h.x - l.x, h.z - l.z);
		return rise / run;
	}
}
