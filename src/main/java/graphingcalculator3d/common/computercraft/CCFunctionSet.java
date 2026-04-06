package graphingcalculator3d.common.computercraft;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;

/**
 * Legacy dynamic-dispatch helper, ported to CC:Tweaked 1.20.1.
 *
 * This is no longer required if you use annotation-based {@code @LuaFunction}
 * peripherals, but it is preserved here in case other classes still depend on it.
 */
public class CCFunctionSet {
    private final ArrayList<CCFunction> funcs = new ArrayList<>();

    public void addFunc(CCFunction func) {
        funcs.add(func);
    }

    @SuppressWarnings("unchecked")
    public <T> void addSetter(String name, Class<T> type, Consumer<T> setter) {
        addFunc(CCFunction.issueVoid(name, (comp, args) -> setter.accept((T) args[0])).requireArgs(type));
    }

    public <T> void addGetter(String name, Supplier<T> getter) {
        addFunc(CCFunction.execute(name, (comp, args) -> new Object[] { getter.get() }));
    }

    public <T> void addSetGet(String name, Class<T> type, Consumer<T> setter, Supplier<T> getter) {
        addSetter("set" + name, type, setter);
        addGetter("get" + name, getter);
    }

    @SuppressWarnings("unchecked")
    public <T> void addSetGetArray(String name, Class<T> arrayType, int minSize, Consumer<T> setter, Supplier<Map<Double, ?>> getter) {
        if (!arrayType.isArray()) {
            throw new IllegalArgumentException("Cannot addSetGetArray for non-array type.");
        }

        addSetter("set" + name, arrayType, value -> {
            Object[] array = (Object[]) value;
            if (array.length >= minSize) {
                setter.accept(value);
                return;
            }

            Object[] padded = (Object[]) Array.newInstance(arrayType.getComponentType(), minSize);
            System.arraycopy(array, 0, padded, 0, array.length);

            Map<Double, ?> existing = getter.get();
            for (int i = array.length; i < padded.length; i++) {
                padded[i] = existing.get((double) i + 1.0D);
            }

            setter.accept((T) padded);
        });

        addGetter("get" + name, getter);
    }

    public String[] getMethodNames() {
        String[] out = new String[funcs.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = funcs.get(i).name;
        }
        return out;
    }

    public MethodResult callMethod(IComputerAccess computer, ILuaContext context, int method, IArguments arguments)
        throws LuaException {
        CCFunction function = funcs.get(method);
        Object[] coerced = coerceArguments(function, arguments.getAll());
        function.verifyArgs(coerced);

        return switch (function.runType) {
            case EXECUTE -> context.executeMainThreadTask(() -> function.task.execute(computer, coerced));
            case ISSUE -> {
                context.issueMainThreadTask(() -> function.task.execute(computer, coerced));
                yield MethodResult.of();
            }
        };
    }

    private static Object[] coerceArguments(CCFunction function, Object[] rawArguments) throws LuaException {
        Object[] out = new Object[rawArguments.length];

        for (int i = 0; i < rawArguments.length; i++) {
            Object argument = rawArguments[i];
            Class<?> expected = function.reqArgs != null && i < function.reqArgs.length ? function.reqArgs[i] : null;

            if (expected != null && expected.isArray()) {
                out[i] = coerceArrayArgument(expected, argument);
                continue;
            }

            out[i] = coerceScalarArgument(expected, argument);
        }

        return out;
    }

    private static Object coerceScalarArgument(Class<?> expected, Object argument) {
        if (expected == null || argument == null) return argument;

        if ((expected == Double.class || expected == double.class) && argument instanceof Number number) {
            return number.doubleValue();
        }
        if ((expected == Integer.class || expected == int.class) && argument instanceof Number number) {
            return number.intValue();
        }
        if ((expected == Long.class || expected == long.class) && argument instanceof Number number) {
            return number.longValue();
        }
        if ((expected == Float.class || expected == float.class) && argument instanceof Number number) {
            return number.floatValue();
        }
        if ((expected == Short.class || expected == short.class) && argument instanceof Number number) {
            return number.shortValue();
        }
        if ((expected == Byte.class || expected == byte.class) && argument instanceof Number number) {
            return number.byteValue();
        }
        if (expected == String.class) {
            return String.valueOf(argument);
        }

        return argument;
    }

    private static Object coerceArrayArgument(Class<?> expectedArrayType, Object argument) throws LuaException {
        Class<?> component = expectedArrayType.getComponentType();

        if (argument == null) {
            return Array.newInstance(component, 0);
        }

        if (argument instanceof Map<?, ?> map) {
            return mapToArray(component, map);
        }

        if (argument.getClass().isArray()) {
            int length = Array.getLength(argument);
            Object out = Array.newInstance(component, length);
            for (int i = 0; i < length; i++) {
                Array.set(out, i, coerceScalarArgument(component, Array.get(argument, i)));
            }
            return out;
        }

        throw new LuaException("Invalid arguments: array/table was required, but " + argument.getClass().getSimpleName() + " was provided.");
    }

    private static Object mapToArray(Class<?> component, Map<?, ?> map) throws LuaException {
        int maxIndex = 0;
        for (Object key : map.keySet()) {
            if (!(key instanceof Number number)) {
                throw new LuaException("The supplied array contains non-numerical keys.");
            }

            double rawIndex = number.doubleValue();
            int index = (int) rawIndex;
            if (index <= 0 || rawIndex != index) {
                throw new LuaException("The supplied array contains non-integral or non-positive keys.");
            }
            maxIndex = Math.max(maxIndex, index);
        }

        Object out = Array.newInstance(component, maxIndex);
        for (int i = 1; i <= maxIndex; i++) {
            if (!map.containsKey((double) i) && !map.containsKey(i) && !map.containsKey((long) i)) {
                throw new LuaException("Indices in the array must not be sparsely populated.");
            }
        }

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            int index = ((Number) entry.getKey()).intValue() - 1;
            Array.set(out, index, coerceScalarArgument(component, entry.getValue()));
        }

        return out;
    }

    private static <T> LinkedHashMap<Double, T> arrayToMap(T[] array) {
        LinkedHashMap<Double, T> map = new LinkedHashMap<>();
        for (int i = 0; i < array.length; i++) {
            map.put((double) i + 1.0D, array[i]);
        }
        return map;
    }

    public static class CCFunction {
        public final String name;
        public final CCFunc task;
        public final CCRunType runType;
        private Class<?>[] reqArgs = null;

        public CCFunction(String name, CCFunc task) {
            this(name, task, CCRunType.EXECUTE);
        }

        public CCFunction(String name, CCFunc task, CCRunType runType) {
            this.name = name;
            this.task = task;
            this.runType = runType;
        }

        public static CCFunction issue(String name, CCFunc task) {
            return new CCFunction(name, task, CCRunType.ISSUE);
        }

        public static CCFunction issueVoid(String name, CCVoidFunc task) {
            return new CCFunction(name, (comp, args) -> {
                task.execute(comp, args);
                return null;
            }, CCRunType.ISSUE);
        }

        public static CCFunction execute(String name, CCFunc task) {
            return new CCFunction(name, task);
        }

        public static CCFunction executeVoid(String name, CCVoidFunc task) {
            return new CCFunction(name, (comp, args) -> {
                task.execute(comp, args);
                return null;
            });
        }

        public static Consumer<Double[]> of(Consumer<double[]> in) {
            return array -> {
                double[] converted = new double[array.length];
                for (int i = 0; i < converted.length; i++) {
                    converted[i] = array[i] != null ? array[i] : 0.0D;
                }
                in.accept(converted);
            };
        }

        public static Consumer<Double[]> ofInt(Consumer<int[]> in) {
            return array -> {
                int[] converted = new int[array.length];
                for (int i = 0; i < converted.length; i++) {
                    converted[i] = array[i] != null ? array[i].intValue() : 0;
                }
                in.accept(converted);
            };
        }

        public static Supplier<Map<Double, ?>> of(Supplier<double[]> in) {
            return () -> {
                double[] source = in.get();
                Double[] boxed = new Double[source.length];
                for (int i = 0; i < boxed.length; i++) {
                    boxed[i] = source[i];
                }
                return arrayToMap(boxed);
            };
        }

        public static Supplier<Map<Double, ?>> ofInt(Supplier<int[]> in) {
            return () -> {
                int[] source = in.get();
                Double[] boxed = new Double[source.length];
                for (int i = 0; i < boxed.length; i++) {
                    boxed[i] = (double) source[i];
                }
                return arrayToMap(boxed);
            };
        }

        public CCFunction requireArgs(Class<?>... types) {
            reqArgs = types;
            return this;
        }

        public boolean verifyArgs(Object[] args) throws LuaException {
            if (reqArgs == null) return true;
            if (args == null) throw new LuaException("Invalid arguments: This function requires arguments.");
            if (args.length < reqArgs.length) {
                throw new LuaException("Invalid arguments: expected at least " + reqArgs.length + " argument(s), got " + args.length + ".");
            }

            for (int i = 0; i < reqArgs.length; i++) {
                Object argument = args[i];
                if (argument == null) {
                    throw new LuaException("Invalid arguments: argument " + (i + 1) + " was missing.");
                }

                Class<?> expected = wrap(reqArgs[i]);
                Class<?> actual = wrap(argument.getClass());
                if (!expected.isAssignableFrom(actual)) {
                    throw new LuaException(
                        "Invalid arguments: " + expected.getSimpleName() + " was required, but " + actual.getSimpleName() + " was provided."
                    );
                }
            }

            return true;
        }

        private static Class<?> wrap(Class<?> type) {
            if (!type.isPrimitive()) return type;
            if (type == int.class) return Integer.class;
            if (type == long.class) return Long.class;
            if (type == double.class) return Double.class;
            if (type == float.class) return Float.class;
            if (type == short.class) return Short.class;
            if (type == byte.class) return Byte.class;
            if (type == boolean.class) return Boolean.class;
            if (type == char.class) return Character.class;
            return type;
        }
    }

    @FunctionalInterface
    public interface CCFunc {
        Object[] execute(IComputerAccess computer, Object[] arguments);
    }

    @FunctionalInterface
    public interface CCVoidFunc {
        void execute(IComputerAccess computer, Object[] arguments);
    }

    public enum CCRunType {
        ISSUE,
        EXECUTE
    }
}
