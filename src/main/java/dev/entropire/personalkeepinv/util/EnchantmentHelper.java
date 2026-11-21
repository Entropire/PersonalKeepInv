package dev.entropire.personalkeepinv.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class EnchantmentHelper {
        private static final Enchantment VANISHING_CURSE = findVanishingCurse();

    private EnchantmentHelper() {
    }

    public static boolean hasCurseOfVanishing(ItemStack item) {
        return VANISHING_CURSE != null && item.containsEnchantment(VANISHING_CURSE);
    }

    private static Enchantment findVanishingCurse() {
        try {
            Object value = Enchantment.class.getField("VANISHING_CURSE").get(null);
            return value instanceof Enchantment ? (Enchantment) value : null;
        } catch (ReflectiveOperationException | LinkageError e) {
            return null;
        }
    }
}
