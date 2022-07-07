package com.cleanroommc.quantumsoundmuffler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = QuantumSoundMuffler.MODID, value = Side.CLIENT)
public class ClientProxy {


    public void preInit() {

    }

    public void postInit() {
    }

}
