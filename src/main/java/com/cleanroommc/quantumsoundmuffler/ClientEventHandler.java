package com.cleanroommc.quantumsoundmuffler;


import com.cleanroommc.modularui.api.UIInfos;
import com.cleanroommc.quantumsoundmuffler.gui.ConfigGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(modid = QuantumSoundMuffler.ID, value = Side.CLIENT)
public class ClientEventHandler {

    public static final KeyBinding configGuiKey = new KeyBinding("key.open_muffler", KeyConflictContext.IN_GAME, Keyboard.KEY_K, "key.categories.quantumsoundmuffler");


    private static long time = 0;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (configGuiKey.isPressed() || configGuiKey.isKeyDown()) {
            long t = Minecraft.getSystemTime();
            if (t - time > 50) {
                UIInfos.openClientUI(Minecraft.getMinecraft().player, ConfigGui::createConfigWindow);
            }
            time = t;
        }
    }

}
