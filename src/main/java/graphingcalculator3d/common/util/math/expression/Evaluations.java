package graphingcalculator3d.common.util.math.expression;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

import graphingcalculator3d.common.util.math.Compare;
import graphingcalculator3d.common.util.math.expression.Expression.Evaluation;
import graphingcalculator3d.common.util.math.expression.Expression.Series;

public class Evaluations
{
	public static final Evaluation VAL = (double[] vals) -> { return vals[0]; };
	
	public static final Evaluation ADD = (double[] vals) -> { return vals[0] + vals[1]; };
	public static final Evaluation SUB = (double[] vals) -> { return vals[0] - vals[1]; };
	public static final Evaluation NEG = (double[] vals) -> { return -vals[0]; };
	public static final Evaluation MULT = (double[] vals) -> { return vals[0] * vals[1]; };
	public static final Evaluation TWICE = (double[] vals) -> { return vals[0] * 2; };
	public static final Evaluation HALF = (double[] vals) -> { return vals[0] * 0.5d; };
	public static final Evaluation DIVI = (double[] vals) -> { return vals[0] / vals[1]; };
	public static final Evaluation ONE_OVR = (double[] vals) -> { return 1 / vals[0]; };
	public static final Evaluation PERCENT = (double[] vals) -> { return vals[0] / 100; };
	public static final Evaluation SQR = (double[] vals) -> { return vals[0] * vals[0]; };
	public static final Evaluation SQR_PRSRV = (double[] vals) -> { return (vals[0] >= 0) ? vals[0] * vals[0] : -(vals[0] * vals[0]); };
	public static final Evaluation SQRT = (double[] vals) -> { return Math.sqrt(vals[0]); };
	public static final Evaluation SQRT_POS = (double[] vals) -> { return (vals[0] >= 0) ? Math.sqrt(vals[0]) : Math.sqrt(-vals[0]); };
	public static final Evaluation SQRT_PRSRV = (double[] vals) -> { return (vals[0] >= 0) ? Math.sqrt(vals[0]) : -Math.sqrt(-vals[0]); };
	public static final Evaluation PWR = (double[] vals) -> { return Math.pow(vals[0], vals[1]); };
	public static final Evaluation PWR_POS = (double[] vals) -> { return Math.pow(vals[0] < 0 ? -vals[0] : vals[0], vals[1]); }; 
	public static final Evaluation PWR_PRSRV = (double[] vals) -> { return vals[0] < 0 ? -Math.pow(-vals[0], vals[1]) : Math.pow(vals[0], vals[1]); };
	public static final Evaluation ROOT = (double[] vals) -> { return Math.pow(vals[1], 1 / vals[0]); };
	public static final Evaluation ROOT_POS = (double[] vals) -> { return Math.pow(vals[1] < 0 ? -vals[1] : vals[1], 1 / vals[0]); };
	public static final Evaluation ROOT_PRSRV = (double[] vals) -> { return vals[1] < 0 ? -Math.pow(-vals[1], 1 / vals[0]) : Math.pow(vals[1], 1 / vals[0]); };
	public static final Evaluation EXPONENT = (double[] vals) -> { return Math.getExponent(vals[0]); };
	public static final Evaluation REM = (double[] vals) -> { return vals[0] % vals[1]; };
	public static final Evaluation ABS_VAL = (double[] vals) -> { return (vals[0] >= 0) ? vals[0] : -vals[0]; };
	public static final Evaluation SIGNUM = (double[] vals) -> { return Math.signum(vals[0]); };
	public static final Evaluation SIN = (double[] vals) -> { return Math.sin(vals[0]); };
	public static final Evaluation COS = (double[] vals) -> { return Math.cos(vals[0]); };
	public static final Evaluation TAN = (double[] vals) -> { return Math.tan(vals[0]); };
	public static final Evaluation CSC = (double[] vals) -> { return 1 / Math.sin(vals[0]); };
	public static final Evaluation SEC = (double[] vals) -> { return 1 / Math.cos(vals[0]); };
	public static final Evaluation COT = (double[] vals) -> { return 1 / Math.tan(vals[0]); };
	public static final Evaluation SINH = (double[] vals) -> { return Math.sinh(vals[0]); };
	public static final Evaluation COSH = (double[] vals) -> { return Math.cosh(vals[0]); };
	public static final Evaluation TANH = (double[] vals) -> { return Math.tanh(vals[0]); };
	public static final Evaluation ASIN = (double[] vals) -> { return Math.asin(vals[0]); };
	public static final Evaluation ACOS = (double[] vals) -> { return Math.acos(vals[0]); };
	public static final Evaluation ATAN = (double[] vals) -> { return Math.atan(vals[0]); };
	public static final Evaluation LN = (double[] vals) -> { return Math.log(vals[0]); };
	public static final Evaluation LN_POS = (double[] vals) -> { return Math.log(vals[0] > 0 ? vals[0] : -vals[0]); };
	public static final Evaluation LN_PRSRV = (double[] vals) -> { return vals[0] > 0 ? Math.log(vals[0]) : -Math.log(-vals[0]); };
	public static final Evaluation LOG = (double[] vals) -> { return Math.log10(vals[0]); };
	public static final Evaluation LOG_POS = (double[] vals) -> { return Math.log10(vals[0] > 0 ? vals[0] : -vals[0]); };
	public static final Evaluation LOG_PRSRV = (double[] vals) -> { return vals[0] > 0 ? Math.log10(vals[0]) : -Math.log10(-vals[0]); };
	public static final Evaluation LOG1P = (double[] vals) -> { return Math.log1p(vals[0]); };
	public static final Evaluation INT = (double[] vals) -> { return Math.rint(vals[0]); };
	public static final Evaluation CEIL = (double[] vals) -> { return Math.ceil(vals[0]); };
	public static final Evaluation FLOOR = (double[] vals) -> { return Math.floor(vals[0]); };
	public static final Evaluation HYPOT = (double[] vals) -> { return Math.hypot(vals[0], vals[1]); };
	public static final Evaluation MAX = (double[] vals) -> { return Math.max(vals[0], vals[1]); };
	public static final Evaluation MIN = (double[] vals) -> { return Math.min(vals[0], vals[1]); };
	public static final Evaluation RAN_INT = (double[] vals) -> { int val = Math.round(Math.round(vals[0])); if (val == -0) val = 0; return (val >= 0) ? Compare.RNG.nextInt(val + 1) : -Compare.RNG.nextInt(-(val - 1)); };
	public static final Evaluation RAN_INT_BTWN = (double[] vals) -> { return Math.round(Math.round(Math.min(vals[0], vals[1]))) + Compare.RNG.nextInt(1 + Math.round(Math.round(Math.abs(vals[0] - vals[1])))); };
	public static final Evaluation RAN_DOUB = (double[] vals) -> { return Compare.RNG.nextDouble() * vals[0]; };
	public static final Evaluation RAN_DOUB_BTWN = (double[] vals) -> { return Math.min(vals[0], vals[1]) + (Compare.RNG.nextDouble() * Math.abs(vals[0] - vals[1])); };
	public static final Evaluation RAN_GAUS = (double[] vals) -> { return Compare.RNG.nextGaussian(); };
	
	public static final Series ADD_SERIES = ADD::evaluate;
	public static final Series SUB_SERIES = SUB::evaluate;
	public static final Series MULT_SERIES = MULT::evaluate;
	public static final Series DIVI_SERIES = DIVI::evaluate;
	public static final Series PWR_SERIES = PWR::evaluate;
	public static final Series ROOT_SERIES = ROOT::evaluate;
	public static final Series REM_SERIES = REM::evaluate;
	public static final Series MAX_SERIES = MAX::evaluate;
	public static final Series MIN_SERIES = MIN::evaluate;
	public static final Series HYPOT_SERIES = HYPOT::evaluate;

	
	public static HashMap<String, Evaluation> evalMap = new HashMap<String, Evaluation>();
	public static HashMap<Evaluation, String> stringMap = new HashMap<Evaluation, String>();
	
	public static HashMap<Evaluation, EvalInfo> infoMap = new HashMap<Evaluation, EvalInfo>();
	public static Evaluation[] ordered;
	
	private static final Random ran = new Random();
	/////////////////////////////
	
	public static void loadMappings() throws IllegalArgumentException, IllegalAccessException
	{
		inf(0, VAL, "value", "A value defined by the text inputted via the text-field between the two arrows");
		
		inf(2, ADD, "+", "Addition of slot1 and slot2");
		inf(2, SUB, "-", "Subtraction of slot2 from slot1");
		inf(1, NEG, "-", "Opposite of slot1");
		inf(2, MULT, "*", "Multiplication of slot1 and slot2");
		inf(1, TWICE, "2", "Twice the value of slot1");
		inf(1, HALF, "1/2", "Half the value of slot1");
		inf(2, DIVI, "/", "Division of slot1 by slot2");
		inf(1, ONE_OVR, "1/", "Division of 1 by slot1");
		inf(1, PERCENT, "percent", "Slot1 percent");
		inf(1, SQR, "sqr", "Slot1 squared");
		inf(1, SQR_PRSRV, "preserved_sqr", "Slot1 squared, with the sign preserved");
		inf(1, SQRT, "sqrt", "The square root of slot1");
		inf(1, SQRT_POS, "positive_sqrt", "The \"positive\" square root of slot1, meaning that for a negative slot1, this operation produces the square root of the opposite of that slot1");
		inf(1, SQRT_PRSRV, "preserved_sqrt", "The \"preserved\" square root of slot1, meaning that for a negative slot1, this operation produces the opposite of the square root of the opposite of slot1");
		inf(2, PWR, "^", "Slot1 raised to the power of slot2");
		inf(2, PWR_POS, "^positive", "Slot1 raised to the power of slot2, \"positive\" version");
		inf(2, PWR_PRSRV, "^preserved", "Slot1 raised to the power of slot2, \"preserved\" version");
		inf(2, ROOT, "_root", "The nth root of slot2, where n is slot1");
		inf(2, ROOT_POS, "_positive_root", "The \"positive\" nth root of slot2, where n is slot1, meaning that for a negative slot2, this operation produces the nth root of the opposite of that slot2");
		inf(2, ROOT_PRSRV, "_preserved_root", "The \"preserved\" nth root of slot2, where n is slot1, meaning that for a negative slot2, this operation produces the opposite of the nth root of the opposite of slot2");
		inf(1, EXPONENT, "exponent", "The exponent used to display slot1 were it to be written in scientific notation");
		inf(2, REM, "remainder_from", "The remainder left from the division of slot1 by slot2");
		inf(1, ABS_VAL, "abs_value", "The absolute value of slot1");
		inf(1, SIGNUM, "signum", "The \"sign\" of slot1. Negatives yield -1, positives yield 1, 0 yields 0.");
		inf(1, SIN, "sin", "The sine of slot1");
		inf(1, COS, "cos", "The cosine of slot1");
		inf(1, TAN, "tan", "The tangent of slot1");
		inf(1, CSC, "csc", "The cosecant of slot1");
		inf(1, SEC, "sec", "The secant of slot1");
		inf(1, COT, "cot", "The cotangent of slot1");
		inf(1, SINH, "hyp_sin", "The hyperbolic sine of slot1");
		inf(1, COSH, "hyp_cos", "The hyperbolic cosine of slot1");
		inf(1, TANH, "hyp_tan", "The hyperbolic tangent of slot1");
		inf(1, LN, "nat_log", "The natural logarithm of slot1");
		inf(1, LN_POS, "positive_nat_log", "The \"positive\" natural logarithm of slot1");
		inf(1, LN_PRSRV, "preserved_nat_log", "The \"preserved\" natural logarithm of slot1");
		inf(1, LOG, "log10", "The base-10 logarithm of slot1");
		inf(1, LOG_POS, "positive_log10", "The \"positive\" base-10 logarithm of slot1");
		inf(1, LOG_PRSRV, "preserved_log10", "The \"preserved\" base-10 logarithm of slot1");
		inf(1, LOG1P, "ln(1+", "The natural logarithm of the value of 1 + slot1");
		inf(1, INT, "int_round", "The value of slot1 rounded to the nearest integer");
		inf(1, CEIL, "ceiling", "The value of slot1 rounded to the next-highest integer");
		inf(1, FLOOR, "floor", "The value of slot1 rounded to the next-lowest integer");
		inf(2, HYPOT, "hypoteneuse", "The square root of the sum of the squares of slot1 and slot2");
		inf(2, MAX, "greater_between", "The greater value between slot1 and slot2");
		inf(2, MIN, "lesser_between", "The lesser value between slot1 and slot2");
		inf(1, RAN_INT, "int_up_to", "A pseudorandom integer from the range 0-slot1");
		inf(2, RAN_INT_BTWN, "int_between", "A pseudorandom integer from the range slot1-slot2");
		inf(1, RAN_DOUB, "double_up_to", "A pseudorandom double from the range 0-slot1");
		inf(2, RAN_DOUB_BTWN, "double_between", "A pseudorandom double from the range slot1-slot2");
		inf(0, RAN_GAUS, "gaussian", "A pseudorandom Gaussian (\"normally\") distributed double value with mean 0.0 and standard deviation 1.0");
		
		inf(3, ADD_SERIES, "sum_i#->n#_of", "The series summation from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		inf(3, SUB_SERIES, "diff_i#->n#_of", "The series difference from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		inf(3, MULT_SERIES, "product_i#->n#_of", "The series product from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		inf(3, DIVI_SERIES, "quotient_i#->n#_of", "The series quotient from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		inf(3, PWR_SERIES, "pwr_i#->n#_of", "The series power from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		inf(3, ROOT_SERIES, "root_i#->n#_of", "The series root from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		inf(3, MAX_SERIES, "greater_i#->n#_of", "greater_between applied in a series from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		inf(3, MIN_SERIES, "less_i#->n#_of", "less_between applied in a series from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		inf(3, HYPOT_SERIES, "hypot_i#->n#_of", "hypoteneuse applied in a series from i=slot1 to n=slot2 of slot3. i and n can be used in value blocks.");
		
		Evaluation temp;
		Field[] fields = Evaluations.class.getFields();
		ordered = new Evaluation[fields.length];
		int i = 0;
		for (Field current : fields)
		{
			if (current.get(current) instanceof Evaluation)
			{
				temp = (Evaluation) current.get(current);
				evalMap.put(current.getName(), temp);
				stringMap.put(temp, current.getName());
				ordered[i] = temp;
			}
			i++;
		}
	}
	
	public static void inf(int slots, Evaluation eval, String forShow, int r, int g, int b, String tooltip)
	{
		infoMap.put(eval, new EvalInfo(slots, eval, forShow, r, g, b, tooltip));
	}
	
	public static void inf(int slots, Evaluation eval, String forShow, String tooltip)
	{
		inf(slots, eval, forShow, ran.nextInt(125), ran.nextInt(125), ran.nextInt(125), tooltip);
	}
}
