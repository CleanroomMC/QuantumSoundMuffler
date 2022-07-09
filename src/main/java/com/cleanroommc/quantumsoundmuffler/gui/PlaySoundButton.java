package com.cleanroommc.quantumsoundmuffler.gui;

import com.cleanroommc.modularui.common.widget.ButtonWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;


public class PlaySoundButton extends ButtonWidget
{
	private  static boolean isFromPSB = false;
	private ResourceLocation soundToPlay;
	public PlaySoundButton(ResourceLocation sound)
	{
		soundToPlay = sound;
	}

	public static boolean isIsFromPSB()
	{
		return isFromPSB;
	}

	@Override
	public ClickResult onClick(int buttonId, boolean doubleClick)
	{
		isFromPSB = true;
		if (soundToPlay != null){
			Minecraft.getMinecraft().player.playSound(new SoundEvent(soundToPlay), 1.0f, 1.0f);
		}
		isFromPSB = false;

		return ClickResult.ACCEPT;
	}
}
