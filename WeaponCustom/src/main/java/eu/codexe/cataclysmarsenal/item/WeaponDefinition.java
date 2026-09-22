package eu.codexe.cataclysmarsenal.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.ColorHelper;

public record WeaponDefinition(
        String id,
        String displayKey,
        boolean knife,
        int damage,
        int ammoCapacity,
        int reloadTicks,
        int cooldownTicks,
        int abilityCooldownTicks,
        float projectileSpeed,
        float spread,
        int color,
        AbilityType ability,
        RegistryEntry<StatusEffect> effect,
        int durability
) {
    public int colorWithAlpha(int alpha) {
        return ColorHelper.Argb.getArgb(alpha, (color >> 16) & 255, (color >> 8) & 255, color & 255);
    }
}