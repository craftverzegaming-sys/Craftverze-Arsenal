package eu.codexe.cataclysmarsenal.client;

import eu.codexe.cataclysmarsenal.CataclysmArsenal;
import eu.codexe.cataclysmarsenal.client.animation.AnimationManager;
import eu.codexe.cataclysmarsenal.client.render.ArsenalRenderers;
import eu.codexe.cataclysmarsenal.network.ArsenalNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class CataclysmArsenalClient implements ClientModInitializer {
    public static KeyBinding inspectKey;
    public static KeyBinding abilityKey;
    public static KeyBinding reloadKey;

    @Override
    public void onInitializeClient() {
        inspectKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cataclysmarsenal.inspect",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                "category.cataclysmarsenal"
        ));

        abilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cataclysmarsenal.ability",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.cataclysmarsenal"
        ));

        reloadKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cataclysmarsenal.reload",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.cataclysmarsenal"
        ));

        ArsenalNetworking.registerClient();
        ArsenalRenderers.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (inspectKey.wasPressed()) {
                AnimationManager.inspect();
            }
            while (abilityKey.wasPressed()) {
                ArsenalNetworking.sendAbility();
            }
            while (reloadKey.wasPressed()) {
                ArsenalNetworking.sendReload();
            }
            AnimationManager.tick();
        });

        CataclysmArsenal.LOGGER.info("Cataclysm Arsenal client initialized");
    }
}