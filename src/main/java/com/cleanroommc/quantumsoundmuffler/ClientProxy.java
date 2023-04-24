package com.cleanroommc.quantumsoundmuffler;

import java.util.Arrays;

import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    public void preInit(FMLPreInitializationEvent event) {
        QuantumSoundMufflerConfig.synchronizeConfiguration(event.getSuggestedConfigurationFile());
        QuantumSoundMuffler.LOG.info(Tags.VERSION + " initialized");
    }

    public void init(FMLInitializationEvent event) {
        ISoundLists.forbiddenSounds.addAll(Arrays.asList(QuantumSoundMufflerConfig.ForbiddenSounds));
    }
}
