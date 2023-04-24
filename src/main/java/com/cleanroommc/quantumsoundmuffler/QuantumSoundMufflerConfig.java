package com.cleanroommc.quantumsoundmuffler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class QuantumSoundMufflerConfig {

    public static String[] ForbiddenSounds = { "ui.", "music.", "ambient." };
    public static float DefaultMuteVolume = 0.0f;
    public static float MinimumVolume = 0.1F;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        ForbiddenSounds = configuration.getStringList(
            "Forbidden Sounds",
            Configuration.CATEGORY_GENERAL,
            ForbiddenSounds,
            "List of sounds to blacklist");
        DefaultMuteVolume = configuration.getFloat(
            "Default Mute Volume",
            Configuration.CATEGORY_GENERAL,
            DefaultMuteVolume,
            0,
            1,
            "Default volume set when mute button is pressed");
        MinimumVolume = configuration.getFloat(
            "Default Mute Volume",
            Configuration.CATEGORY_GENERAL,
            DefaultMuteVolume,
            0,
            1,
            "Default volume set when mute button is pressed");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
