package com.cleanroommc.quantumsoundmuffler.gui;

import com.cleanroommc.modularui.api.ModularUITextures;
import com.cleanroommc.modularui.api.drawable.AdaptableUITexture;
import com.cleanroommc.modularui.api.drawable.Text;
import com.cleanroommc.modularui.api.drawable.UITexture;
import com.cleanroommc.modularui.api.math.Alignment;
import com.cleanroommc.modularui.api.math.Color;
import com.cleanroommc.modularui.common.widget.*;
import com.cleanroommc.quantumsoundmuffler.QuantumSoundMuffler;
import com.cleanroommc.quantumsoundmuffler.QuantumSoundMufflerConfig;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.cleanroommc.quantumsoundmuffler.utils.Anchor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class MuffledSlider extends MultiChildWidget implements ISoundLists
{
	/**
	 * TODO
	 * 		Hide slider until click
	 * 		thing
	 */

	public static boolean showSlider = false;
	private final ResourceLocation sound;
	private float sliderValue;
	private final SliderWidget sliderVol;

	private Text labelText;
	private final DynamicTextWidget txtLabel;
	private final CycleButtonWidget btnToggleSound;
	private final ButtonWidget btnPlaySound;

	private final AdaptableUITexture sliderBackground;

	public MuffledSlider(float sliderValueIn, ResourceLocation soundIn, Anchor anchor)
	{
		this.sliderValue = sliderValueIn;
		sound = soundIn;

		sliderBackground = AdaptableUITexture.of("quantumsoundmuffler", "gui/slider/background.png", 205, 13, 1);
		sliderVol = createSlider();
		btnToggleSound = createToggleButton();
		btnPlaySound = createPlayButton();

		txtLabel = TextWidget.dynamicText(() -> getLabel());
		txtLabel.setTextAlignment(Alignment.Center)
				.setSize(sliderVol.getSize().width - 6, sliderVol.getSize().height)
				.setPos(6, 2);

		this.addChild(sliderVol);
		this.addChild(txtLabel);
		this.addChild(btnToggleSound);
		this.addChild(btnPlaySound);
	}

	private Text getLabel()
	{
		String txt;
		int color;
		if (isMuffled())
		{
			if (sliderVol.isHovering())
			{
				txt = String.format("Volume: %d", (int) (sliderValue * 100));
			} else
			{
				txt = String.format("%s", getSoundName());
			}
			color = Color.CYAN.normal;
		} else
		{
			txt = String.format("%s", getSoundName());
			color = Color.WHITE.normal;
		}
		return new Text(txt).color(color).shadow();
	}

	private SliderWidget createSlider()
	{
		final SliderWidget sliderVol;
		sliderVol = new SliderWidget();
		sliderVol.setBounds(0.0f, 1.0f)
				 .setGetter(() -> this.sliderValue)
				 .setSetter(val ->
							{
								setSliderValue(val);
								//sliderVol.setBackground(sliderBackground.getSubArea(0, 0, val, 1));
							})
				 .setBackground(sliderBackground.getSubArea(0, 0, 1, 1))
				 .setSize(205, 13)
				 .setPos(2, 2)
				 .setTicker(widget ->
							{
								widget.setEnabled(isMuffled() && txtLabel.isRightBelowMouse());
							});
		sliderVol.setValue(this.sliderValue, false);
		return sliderVol;
	}

	private CycleButtonWidget createToggleButton()
	{
		final CycleButtonWidget btnToggleSound;
		btnToggleSound = new CycleButtonWidget();
		btnToggleSound.setTextureGetter(state ->
										{
											switch (state)
											{
												case 0:
													return UITexture.fullImage("quantumsoundmuffler", "gui/button/muffled.png");
												case 1:
													return UITexture.fullImage("quantumsoundmuffler", "gui/button/unmuffled.png");
												default:
													return ModularUITextures.BASE_BUTTON;
											}
										})
					  .setLength(2)
					  .setGetter(() -> isMuffled() ? 1 : 0)
					  .setSetter(value ->
								 {
									 if (value == 0)
									 {
										 muffledSounds.remove(sound);
									 } else if (value == 1)
									 {
										 setSliderValue(QuantumSoundMufflerConfig.defaultMuteVolume);
										 muffledSounds.put(sound, this.sliderValue);
									 }
								 })
					  .setPos(sliderVol.getSize().width + 5, 3)
					  .setSize(11, 11);
		return btnToggleSound;
	}

	private ButtonWidget createPlayButton()
	{
		final ButtonWidget btnPlaySound;
		btnPlaySound = new PlaySoundButton(sound);
		btnPlaySound.setBackground(UITexture.fullImage("quantumsoundmuffler", "gui/button/play.png"))
					.setPos(btnToggleSound.getPos().x + 12, btnToggleSound.getPos().y)
					.setSize(11, 11);
		return btnPlaySound;
	}


	private void setSliderValue(float value)
	{
		this.sliderValue = MathHelper.clamp(value, 0.0f, 0.9f);
		updateVolume();
	}

	private String getSoundName()
	{
		return String.format("%s:%s", sound.getNamespace(), sound.getPath().length() > 25 ? sound.getPath()
																								 .substring(0, 25) : sound.getPath());
	}

	private void updateVolume()
	{
		muffledSounds.replace(this.sound, this.sliderValue);
	}

	public boolean isMuffled()
	{
		return muffledSounds.containsKey(sound);
	}
}
