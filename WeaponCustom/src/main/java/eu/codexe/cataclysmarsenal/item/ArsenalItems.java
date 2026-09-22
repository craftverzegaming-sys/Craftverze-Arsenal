package eu.codexe.cataclysmarsenal.item;

import eu.codexe.cataclysmarsenal.CataclysmArsenal;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ArsenalItems {
    public static final Item CATACLYSM_CORE = register("cataclysm_core", new Item(new Item.Settings()));

    private ArsenalItems() {
    }

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(CataclysmArsenal.MOD_ID, id), item);
    }

    public static void register() {
        // Items are registered via static initializers above
    }

    public static void addToGroup(ItemGroupEvents.ModifyEntries entries) {
        entries.add(new ItemStack(CATACLYSM_CORE));
    }
}