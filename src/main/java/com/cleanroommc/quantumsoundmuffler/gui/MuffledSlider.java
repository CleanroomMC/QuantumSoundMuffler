package com.cleanroommc.quantumsoundmuffler.gui;

import com.cleanroommc.modularui.api.ModularUITextures;
import com.cleanroommc.modularui.api.drawable.Text;
import com.cleanroommc.modularui.api.drawable.UITexture;
import com.cleanroommc.modularui.api.math.Alignment;
import com.cleanroommc.modularui.api.math.Color;
import com.cleanroommc.modularui.common.widget.*;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.cleanroommc.quantumsoundmuffler.utils.Anchor;
import net.minecraft.util.ResourceLocation;

public class MuffledSlider extends MultiChildWidget implements ISoundLists
{


	public static ResourceLocation tickSound;
	public static boolean showSlider = false;
	private final ResourceLocation sound;
	private float value;
	private final SliderWidget sliderVol;
	private final TextWidget txtLabel;
	private final ButtonWidget btnToggleSound;
	private final ButtonWidget btnPlaySound;


	public MuffledSlider(float sliderValue, ResourceLocation soundIn, Anchor anchor)
	{
		value = sliderValue;
		sound = soundIn;

		sliderVol = new SliderWidget();
		sliderVol.setGetter(() -> this.value)
				 .setSetter(val -> this.value = val)
				 .setBackground(UITexture.fullImage("quantumsoundmuffler", "gui/slider_background.png"))
				 .setSize(205, 13)
				 .setPos(2, 2);

		String label = String.format("%s:%s",
									 sound.getNamespace(),
									 sound.getPath().length() > 25 ? sound.getPath().substring(0, 25) : sound.getPath());
		txtLabel = new TextWidget(new Text(label).color(Color.WHITE.normal).shadow());

		btnToggleSound = new ButtonWidget();
		btnToggleSound.setBackground(UITexture.fullImage("quantumsoundmuffler", "gui/btn_toggle.png"))
						   .setPos(sliderVol.getSize().width + 5 , 4)
						   .setSize(11, 11);

		btnPlaySound = new ButtonWidget();
		btnPlaySound.setBackground(UITexture.fullImage("quantumsoundmuffler", "gui/btn_play.png"))
						 .setPos(btnToggleSound.getPos().x + 12, 4)
						 .setSize(11, 11);

		txtLabel.setTextAlignment(Alignment.CenterLeft)
				.setSize(sliderVol.getSize().width - 6, sliderVol.getSize().height)
				.setPos(6, 2);

		this.addChild(sliderVol);
		this.addChild(txtLabel);
		this.addChild(btnToggleSound);
		this.addChild(btnPlaySound);
	}

}
