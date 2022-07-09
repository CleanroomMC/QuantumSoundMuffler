package com.cleanroommc.quantumsoundmuffler.gui;

import com.cleanroommc.modularui.api.ModularUITextures;
import com.cleanroommc.modularui.api.drawable.AdaptableUITexture;
import com.cleanroommc.modularui.api.drawable.Text;
import com.cleanroommc.modularui.api.drawable.shapes.Rectangle;
import com.cleanroommc.modularui.api.math.Color;
import com.cleanroommc.modularui.api.screen.ModularWindow;
import com.cleanroommc.modularui.api.screen.UIBuildContext;
import com.cleanroommc.modularui.common.widget.*;
import com.cleanroommc.quantumsoundmuffler.QuantumSoundMuffler;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.cleanroommc.quantumsoundmuffler.utils.DataManger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ConfigGui implements ISoundLists
{
	private static State state = State.Recent;

	private static final AdaptableUITexture BACKGROUND = AdaptableUITexture.of("modularui:gui/background/background", 176, 166, 3);


	public static ModularWindow createConfigWindow(UIBuildContext buildContext)
	{
		ModularWindow.Builder builder = ModularWindow.builder(256, 176);

		// anchor sidebar
		AddAnchorMenu(builder);


		builder.setBackground(BACKGROUND)
			   .widget(new TextWidget(new Text(QuantumSoundMuffler.NAME).color(Color.WHITE.normal)
																		.shadow()).setBackground(ModularUITextures.ITEM_SLOT)
																				  .setPos(17, 5)
																				  .setSize(221, 15));

		ChangeableWidget soundListContainer = new ChangeableWidget(() -> CreateSoundSliders());
		soundListContainer.setPos(10, 25).setSize(236, 122);
		AddStateButton(builder, soundListContainer);
		builder.widget(soundListContainer);



		buildContext.addCloseListener(() ->
									  {
										  DataManger.saveData();
									  });

		return builder.build();
	}

	private static void AddStateButton(ModularWindow.Builder builder, ChangeableWidget soundListContainer)
	{
		final CycleButtonWidget btnToggleMode = new CycleButtonWidget();
		btnToggleMode.setForEnum(State.class, () -> state, val ->
					 {
						 state = val;
						 soundListContainer.notifyChangeServer();
					 })
					 .setLength(State.values().length)
					 .setTextureGetter(integer -> new Text(state.name()).color(Color.WHITE.normal)
																		.shadow())
					 .setPos(13, 154)
					 .setSize(52, 13);

		builder.widget(btnToggleMode);
	}

	private static void AddAnchorMenu(ModularWindow.Builder builder)
	{
		builder.widget(new ExpandTab().setNormalTexture(ModularUITextures.ICON_INFO.withFixedSize(14, 14, 3, 3))
									  .widget(new DrawableWidget().setDrawable(ModularUITextures.ICON_INFO)
																  .setSize(14, 14)
																  .setPos(3, 3))
									  .setExpandedSize(60, 160)
									  .setBackground(BACKGROUND)
									  .setSize(20, 20)
									  .setPos(258, 3)
									  .respectAreaInJei());
	}


	private static ListWidget CreateSoundSliders()
	{

		soundsList.clear();
		switch (state)
		{
			case All:
				soundsList.addAll(ForgeRegistries.SOUND_EVENTS.getKeys());
				forbiddenSounds.forEach(fs -> soundsList.removeIf(sl -> sl.toString().contains(fs)));
				break;
			case Recent:
				soundsList.addAll(recentSounds);
				break;
			case Muffled:
				soundsList.addAll(muffledSounds.keySet());
				break;
		}
		soundsList.clear();
		soundsList.addAll(recentSounds);


		ListWidget list = new ListWidget();
		list.setBackground(new Rectangle().setColor(Color.BLACK.normal)).fillParent();

		for (ResourceLocation sound : soundsList)
		{
			float vol;
			float maxVol = 1F;

			//TODO pull from anchor
			vol = muffledSounds.getOrDefault(sound, maxVol);

			MuffledSlider slider = new MuffledSlider(vol, sound, null);
			list.addChild(slider);
		}


		return list;
	}


	private enum State
	{
		All, Recent, Muffled
	}
}
