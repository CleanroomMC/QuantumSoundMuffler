package com.cleanroommc.quantumsoundmuffler;


import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

@Mod(modid = QuantumSoundMuffler.ID,
        name = QuantumSoundMuffler.NAME,
        version = QuantumSoundMuffler.VERSION)
public class QuantumSoundMuffler {
    public static final String ID = "quantumsoundmuffler";
    public static final String NAME = "Quantum Sound Muffler";
    public static final String VERSION = "0.1";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    @Mod.Instance(ID)
    public static QuantumSoundMuffler instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ISoundLists.forbiddenSounds.addAll(Arrays.asList(QuantumSoundMufflerConfig.forbiddenSounds));
    }



    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ClientRegistry.registerKeyBinding(ClientEventHandler.configGuiKey);
    }

}
