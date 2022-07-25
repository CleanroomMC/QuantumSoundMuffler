package com.cleanroommc.quantumsoundmuffler;


import com.cleanroommc.modularui.api.KeyBindAPI;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

@Mod(modid = QuantumSoundMuffler.ID, name = QuantumSoundMuffler.NAME, version = QuantumSoundMuffler.VERSION, clientSideOnly = true, dependencies = "required-after:modularui@[1.0.6,);")
public class QuantumSoundMuffler
{
	public static final String ID = "quantumsoundmuffler";
	public static final String NAME = "Quantum Sound Muffler";
	public static final String VERSION = "0.1";

	public static final Logger LOGGER = LogManager.getLogger(ID);

	@Mod.Instance(ID)
	public static QuantumSoundMuffler instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		ISoundLists.forbiddenSounds.addAll(Arrays.asList(QuantumSoundMufflerConfig.forbiddenSounds));
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		ClientRegistry.registerKeyBinding(ClientEventHandler.configGuiKey);
		KeyBindAPI.forceCheckKeyBind(ClientEventHandler.configGuiKey);
	}
}
