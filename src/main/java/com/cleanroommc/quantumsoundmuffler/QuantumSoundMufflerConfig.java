package com.cleanroommc.quantumsoundmuffler;


import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = QuantumSoundMuffler.ID)
public class QuantumSoundMufflerConfig {

    @Config.RequiresMcRestart
    @Config.Name("Forbidden Sounds")
    @Config.Comment("List of sounds to blacklist")
    public static String[] forbiddenSounds = {"ui.", "music.", "ambient."};

    @Config.RangeDouble(min = 0.0, max = 1.0)
    @Config.SlidingOption()
    @Config.Name("Default Mute Volume")
    @Config.Comment("Default volume set when mute button is pressed")
    public static float defaultMuteVolume = 0.0f;

    @Config.Name("Disable Anchors")
    public static boolean disableAnchors = false;

    @SubscribeEvent
    public void onConfigChangedEvent (ConfigChangedEvent.OnConfigChangedEvent event){
        if (event.getModID().equals(QuantumSoundMuffler.ID))
        {
            ConfigManager.sync(QuantumSoundMuffler.ID, Config.Type.INSTANCE);
        }
    }

}
