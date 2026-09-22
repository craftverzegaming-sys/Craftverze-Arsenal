package eu.codexe.cataclysmarsenal.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.codexe.cataclysmarsenal.CataclysmArsenal;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ArsenalConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("cataclysmarsenal.json");

    public static boolean particles = true;
    public static boolean cameraEffects = true;
    public static boolean bedrockDestruction = false;
    public static boolean worldEffects = true;
    public static int maxEffectRadius = 18;
    public static int maxParticlesPerTick = 160;
    public static float damageMultiplier = 1.0f;
    public static float cooldownMultiplier = 1.0f;
    public static boolean debug = false;

    private ArsenalConfig() {
    }

    public static void load() {
        try {
            if (Files.exists(PATH)) {
                Data data = GSON.fromJson(Files.readString(PATH), Data.class);
                if (data != null) {
                    particles = data.particles;
                    cameraEffects = data.cameraEffects;
                    bedrockDestruction = data.bedrockDestruction;
                    worldEffects = data.worldEffects;
                    maxEffectRadius = Math.max(1, Math.min(20, data.maxEffectRadius));
                    maxParticlesPerTick = Math.max(0, Math.min(500, data.maxParticlesPerTick));
                    damageMultiplier = Math.max(0.1f, Math.min(5.0f, data.damageMultiplier));
                    cooldownMultiplier = Math.max(0.1f, Math.min(5.0f, data.cooldownMultiplier));
                    debug = data.debug;
                }
            } else {
                save();
            }
        } catch (IOException exception) {
            CataclysmArsenal.LOGGER.error("Unable to load configuration", exception);
        }
    }

    public static void save() {
        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, GSON.toJson(new Data()));
        } catch (IOException exception) {
            CataclysmArsenal.LOGGER.error("Unable to save configuration", exception);
        }
    }

    private static final class Data {
        boolean particles = ArsenalConfig.particles;
        boolean cameraEffects = ArsenalConfig.cameraEffects;
        boolean bedrockDestruction = ArsenalConfig.bedrockDestruction;
        boolean worldEffects = ArsenalConfig.worldEffects;
        int maxEffectRadius = ArsenalConfig.maxEffectRadius;
        int maxParticlesPerTick = ArsenalConfig.maxParticlesPerTick;
        float damageMultiplier = ArsenalConfig.damageMultiplier;
        float cooldownMultiplier = ArsenalConfig.cooldownMultiplier;
        boolean debug = ArsenalConfig.debug;
    }
}