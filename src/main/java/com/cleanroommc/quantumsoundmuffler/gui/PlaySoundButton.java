package com.cleanroommc.quantumsoundmuffler.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.modularui.common.widget.ButtonWidget;

public class PlaySoundButton extends ButtonWidget {

    private static boolean isFromPSB = false;
    private ResourceLocation soundToPlay;

    public PlaySoundButton(ResourceLocation sound) {
        soundToPlay = sound;
    }

    public static boolean isIsFromPSB() {
        return isFromPSB;
    }

    @Override
    public ClickResult onClick(int buttonId, boolean doubleClick) {
        isFromPSB = true;
        if (soundToPlay != null) {
            Minecraft.getMinecraft().thePlayer.playSound(soundToPlay.getResourcePath(), 1.0f, 1.0f);
        }
        isFromPSB = false;

        return ClickResult.ACCEPT;
    }
}
