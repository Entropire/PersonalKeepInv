package dev.entropire.personalkeepinv.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.World;

public final class GameRuleHelper {
    private static final Object KEEP_INVENTORY_RULE;
    private static final Method GET_TYPED;
    private static final Method SET_TYPED;

    private static final Method GET_STRING;
    private static final Method SET_STRING;

    static {
        Object rule = staticField("org.bukkit.GameRules", "KEEP_INVENTORY");
        if (rule == null) {
            rule = staticField("org.bukkit.GameRule", "KEEP_INVENTORY");
        }

        Method getTyped = null;
        Method setTyped = null;
        if (rule != null) {
            for (Method method : World.class.getMethods()) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 0 || !params[0].isInstance(rule)) {
                    continue;
                }
                if (method.getName().equals("getGameRuleValue") && params.length == 1) {
                    getTyped = method;
                } else if (method.getName().equals("setGameRule") && params.length == 2) {
                    setTyped = method;
                }
            }
        }

        boolean typedAvailable = rule != null && getTyped != null && setTyped != null;
        KEEP_INVENTORY_RULE = typedAvailable ? rule : null;
        GET_TYPED = typedAvailable ? getTyped : null;
        SET_TYPED = typedAvailable ? setTyped : null;

        // 4. Always look up the string API too, as the fallback
        GET_STRING = method(World.class, "getGameRuleValue", String.class);
        SET_STRING = method(World.class, "setGameRuleValue", String.class, String.class);

        if (!typedAvailable && (GET_STRING == null || SET_STRING == null)) {
            throw new IllegalStateException("No usable gamerule API found on this server version");
        }
    }

    private GameRuleHelper() {
    }

    public static boolean getKeepInventory(World world) {
        try {
            if (GET_TYPED != null) {
                return Boolean.TRUE.equals(GET_TYPED.invoke(world, KEEP_INVENTORY_RULE));
            }
            return Boolean.parseBoolean(String.valueOf(GET_STRING.invoke(world, "keepInventory")));
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Could not read the keepInventory gamerule", e);
        }
    }

    public static void setKeepInventory(World world, boolean value) {
        try {
            if (SET_TYPED != null) {
                SET_TYPED.invoke(world, KEEP_INVENTORY_RULE, Boolean.valueOf(value));
            } else {
                SET_STRING.invoke(world, "keepInventory", String.valueOf(value));
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Could not change the keepInventory gamerule", e);
        }
    }

    private static Object staticField(String className, String fieldName) {
        try {
            Field field = Class.forName(className).getField(fieldName);
            return field.get(null);
        } catch (ReflectiveOperationException | LinkageError e) {
            return null;
        }
    }

    private static Method method(Class<?> owner, String name, Class<?>... params) {
        try {
            return owner.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}