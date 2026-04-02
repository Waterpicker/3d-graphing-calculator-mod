//package graphingcalculator3d.common.computercraft;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.function.Consumer;
//import java.util.function.Supplier;
//
//import dan200.computercraft.api.lua.ILuaContext;
//import dan200.computercraft.api.lua.LuaException;
//import dan200.computercraft.api.peripheral.IComputerAccess;
//
//public class CCFunctionSet
//{
//	private final ArrayList<CCFunction> funcs = new ArrayList<CCFunction>();
//
//	public void addFunc(CCFunction func)
//	{
//		funcs.add(func);
//	}
//
//	@SuppressWarnings("unchecked")
//	public <T> void addSetter(String name, Class<T> type, Consumer<T> setter)
//	{
//		addFunc(CCFunction.issueVoid(name, (comp, args) -> setter.accept((T) args[0])).requireArgs(type));
//	}
//
//	public <T> void addGetter(String name, Supplier<T> getter)
//	{
//		addFunc(CCFunction.execute(name, (comp, args) -> new Object[] { getter.get() }));
//	}
//
//	public <T> void addSetGet(String name, Class<T> type, Consumer<T> setter, Supplier<T> getter)
//	{
//		addSetter("set" + name, type, setter);
//		addGetter("get" + name, getter);
//	}
//
//	@SuppressWarnings("unchecked")
//	public <T> void addSetGetArray(String name, Class<T> arrayType, int minSize, Consumer<T> setter, Supplier<HashMap<Double, ?>> getter)
//	{
//		if (!arrayType.isArray())
//			throw new IllegalArgumentException("Cannot addSetGetArray for non-array type.");
//		addSetter("set" + name, arrayType, (t) ->
//		{
//			Object[] arrT = (Object[]) t;
//			if (arrT.length < minSize)
//			{
//				Object[] set = (Object[]) Array.newInstance(arrayType.getComponentType(), minSize);
//				for (int i = 0; i < arrT.length; i++)
//					set[i] = arrT[i];
//				HashMap<Double, T> get = (HashMap<Double, T>) getter.get();
//				for (int i = arrT.length; i < set.length; i++)
//					set[i] = get.get((Double) (double) i + 1);
//				setter.accept((T) set);
//			}
//			else
//				setter.accept(t);
//		});
//		addGetter("get" + name, getter);
//	}
//
//	public String[] getMethodNames()
//	{
//		String[] out = new String[funcs.size()];
//		for (int i = 0; i < out.length; i++)
//			out[i] = funcs.get(i).name;
//		return out;
//	}
//
//	@SuppressWarnings("unchecked")
//	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException
//	{
//		CCFunction f = funcs.get(method);
//		Object[] args = new Object[arguments.length];
//		for (int i = 0; i < args.length; i++)
//		{
//			if (arguments[i] instanceof HashMap && f.reqArgs != null && f.reqArgs.length > i && f.reqArgs[i].isArray())
//			{
//				HashMap<?, ?> hm = (HashMap<?, ?>) arguments[i];
//				if (hm.size() > 0)
//				{
//					AtomicBoolean thrK = new AtomicBoolean(false);
//					AtomicBoolean thrV = new AtomicBoolean(false);
//					Class<?> compCl = f.reqArgs[i].getComponentType();
//					hm.forEach((key, val) ->
//					{
//						if (key.getClass() != Double.class || ((Double) key) - 1 < 0)
//							thrK.set(true);
//						if (!compCl.isAssignableFrom(val.getClass()))
//							thrV.set(true);
//					});
//					if (thrK.get())
//						throw new LuaException("The supplied array contains non-numerical or negative keys.");
//					if (thrV.get())
//						throw new LuaException("The supplied array contains values of illegal type.");
//
//					args[i] = hashToArray(compCl, (HashMap<Double, ?>) hm);
//				}
//				else
//					args[i] = Array.newInstance(f.reqArgs[i].getClass().getComponentType(), 0);
//			}
//			else
//			{
//				args[i] = arguments[i];
//			}
//		}
//		f.verifyArgs(args);
//		switch(f.runType)
//		{
//			case EXECUTE:
//				return context.executeMainThreadTask(() -> f.task.execute(computer, args));
//			case ISSUE:
//				context.issueMainThreadTask(() -> f.task.execute(computer, args));
//				return null;
//			default:
//				return null;
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	private static <T> T[] hashToArray(Class<T> compType, HashMap<Double, ?> map) throws LuaException
//	{
//		T[] out = (T[]) Array.newInstance(compType, map.size());
//
//		AtomicBoolean thrK = new AtomicBoolean(false);
//		map.forEach((key, val) ->
//		{
//			final int index = ((int) (double) (Double) key) - 1;
//			if (index < 0 || index >= out.length)
//				thrK.set(true);
//			else
//				out[index] = (T) val;
//		});
//
//		if (thrK.get())
//			throw new LuaException("Indicies in the array must not be sparsely populated.");
//
//		return out;
//	}
//
//	private static <T> HashMap<Double, T> arrayToHash(T[] array)
//	{
//		HashMap<Double, T> map = new HashMap<Double, T>();
//		for (int i = 0; i < array.length; i++)
//			map.put((double) i + 1, array[i]);
//
//		return map;
//	}
//
//	////////////////////////////////////
//
//	public static class CCFunction
//	{
//		public final String name;
//		public final CCFunc task;
//		public final CCRunType runType;
//		private Class<?>[] reqArgs = null;
//
//		///////////////////
//
//		public CCFunction(String name, CCFunc task)
//		{
//			this(name, task, CCRunType.EXECUTE);
//		}
//
//		public CCFunction(String name, CCFunc task, CCRunType runType)
//		{
//			this.name = name;
//			this.task = task;
//			this.runType = runType;
//		}
//
//		//////////////////
//
//		public static CCFunction issue(String name, CCFunc task)
//		{
//			return new CCFunction(name, task, CCRunType.ISSUE);
//		}
//
//		public static CCFunction issueVoid(String name, CCVoidFunc task)
//		{
//			return new CCFunction(name, (comp, args) -> { task.execute(comp, args); return null; }, CCRunType.ISSUE);
//		}
//
//		public static CCFunction execute(String name, CCFunc task)
//		{
//			return new CCFunction(name, task);
//		}
//
//		public static CCFunction executeVoid(String name, CCVoidFunc task)
//		{
//			return new CCFunction(name, (comp, args) -> { task.execute(comp, args); return null; });
//		}
//
//		public static Consumer<Double[]> of(Consumer<double[]> in)
//		{
//			return (DA) ->
//			{
//				double[] dA = new double[DA.length];
//				for (int i = 0; i < dA.length; i++)
//					dA[i] = DA[i] != null ? DA[i] : 0;
//
//				in.accept(dA);
//			};
//		}
//
//		public static Consumer<Double[]> ofInt(Consumer<int[]> in)
//		{
//			return (DA) ->
//			{
//				int[] iA = new int[DA.length];
//				for (int i = 0; i < iA.length; i++)
//					iA[i] = DA[i] != null ? Math.round(Math.round(DA[i])) : 0;
//
//				in.accept(iA);
//			};
//		}
//
//		public static Supplier<HashMap<Double, ?>> of(Supplier<double[]> in)
//		{
//			return () ->
//			{
//				double[] dA = in.get();
//				Double[] DA = new Double[dA.length];
//				for (int i = 0; i < DA.length; i++)
//					DA[i] = dA[i];
//
//				return arrayToHash(DA);
//			};
//		}
//
//		public static Supplier<HashMap<Double, ?>> ofInt(Supplier<int[]> in)
//		{
//			return () ->
//			{
//				int[] iA = in.get();
//				Double[] DA = new Double[iA.length];
//				for (int i = 0; i < DA.length; i++)
//					DA[i] = (double) iA[i];
//
//				return arrayToHash(DA);
//			};
//		}
//
//		///////////////////
//
//		public CCFunction requireArgs(Class<?>... types)
//		{
//			reqArgs = types;
//			return this;
//		}
//
//		public boolean verifyArgs(Object[] args) throws LuaException
//		{
//			if (reqArgs == null)
//				return true;
//			if (args == null)
//				throw new LuaException("Invalid arguments: This function requires arguments.");
//			for (int i = 0; i < reqArgs.length; i++)
//			{
//				if (!reqArgs[i].isAssignableFrom(args[i].getClass()))
//					throw new LuaException("Invalid arguments: " + reqArgs[i].getSimpleName() + " was required, but " + args[i].getClass().getSimpleName() + " was provided.");
//			}
//			return true;
//		}
//	}
//
//	@FunctionalInterface
//	public static interface CCFunc
//	{
//		public Object[] execute(IComputerAccess computer, Object[] arguments);
//	}
//
//	@FunctionalInterface
//	public static interface CCVoidFunc
//	{
//		public void execute(IComputerAccess computer, Object[] arguments);
//	}
//
//	public static enum CCRunType
//	{
//		ISSUE, EXECUTE;
//	}
//}
