package eu.codexe.cataclysmarsenal;

import eu.codexe.cataclysmarsenal.command.ArsenalCommand;
import eu.codexe.cataclysmarsenal.config.ArsenalConfig;
import eu.codexe.cataclysmarsenal.item.ArsenalItems;
import eu.codexe.cataclysmarsenal.network.ArsenalNetworking;
import eu.codexe.cataclysmarsenal.registry.ArsenalRegistries;
import eu.codexe.cataclysmarsenal.system.CombatSystem;
import eu.codexe.cataclysmarsenal.system.CooldownSystem;
import eu.codexe.cataclysmarsenal.system.ProjectileSystem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CataclysmArsenal implements ModInitializer {
    public static final String MOD_ID = "cataclysmarsenal";
    public static final String AUTHOR = "Craftverze";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ItemGroup CUSTOM_WEAPONS = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(MOD_ID, "custom_weapons"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.cataclysmarsenal.custom_weapons"))
                    .icon(() -> new ItemStack(ArsenalItems.CATACLYSM_CORE))
                    .build()
    );

    @Override
    public void onInitialize() {
        ArsenalConfig.load();
        ArsenalRegistries.register();
        ArsenalItems.register();
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_WEAPONS).register(entries -> ArsenalItems.addToGroup(entries));
        ArsenalNetworking.registerServer();
        ArsenalCommand.register();
        CombatSystem.register();
        ProjectileSystem.register();
        CooldownSystem.register();

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            CooldownSystem.tick();
            ProjectileSystem.tick(server);
            CombatSystem.tick(server);
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            CooldownSystem.clear();
            ProjectileSystem.clear();
            CombatSystem.clear();
        });

        LOGGER.info("Cataclysm Arsenal initialized by {}", AUTHOR);
    }
}