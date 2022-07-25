package com.cleanroommc.quantumsoundmuffler.gui;

import com.cleanroommc.modularui.api.ModularUITextures;
import com.cleanroommc.modularui.api.drawable.AdaptableUITexture;
import com.cleanroommc.modularui.api.drawable.Text;
import com.cleanroommc.modularui.api.drawable.UITexture;
import com.cleanroommc.modularui.api.drawable.shapes.Rectangle;
import com.cleanroommc.modularui.api.math.Alignment;
import com.cleanroommc.modularui.api.math.Color;
import com.cleanroommc.modularui.api.math.CrossAxisAlignment;
import com.cleanroommc.modularui.api.math.MainAxisAlignment;
import com.cleanroommc.modularui.api.screen.ModularWindow;
import com.cleanroommc.modularui.api.screen.UIBuildContext;
import com.cleanroommc.modularui.common.widget.*;
import com.cleanroommc.quantumsoundmuffler.QuantumSoundMuffler;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.cleanroommc.quantumsoundmuffler.utils.Anchor;
import com.cleanroommc.quantumsoundmuffler.utils.DataManger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ConfigGui implements ISoundLists
{
	private static State state = State.Recent;

	private static ChangeableWidget soundListContainer;
	public static boolean wasOpened = false;

	public static ModularWindow createConfigWindow(UIBuildContext buildContext)
	{
		buildContext.setShowJei(false);
		buildContext.addCloseListener(() -> {
			DataManger.saveData();
			wasOpened = false;
		});

		ModularWindow.Builder builder = ModularWindow.builder(256, 176);

		builder.setBackground(ModularUITextures.VANILLA_BACKGROUND)
			   .widget(new TextWidget(new Text(QuantumSoundMuffler.NAME).color(Color.WHITE.normal)
																		.shadow()).setBackground(ModularUITextures.ITEM_SLOT)
																				  .setPos(17, 5)
																				  .setSize(221, 15));

		builder.widget(CreateSoundSliders().setPos(10, 25)
										   .setSize(236, 122)
										   .setBackground(new Rectangle().setColor(Color.BLACK.normal)));

		wasOpened = true;

		return builder.build();
	}

	private static void AddStateButton(ModularWindow.Builder builder)
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
					 .setSize(52, 13)
					 .setBackground(ModularUITextures.BASE_BUTTON);


		builder.widget(btnToggleMode);
	}

	private static void AddAnchorMenu(ModularWindow.Builder builder)
	{
		builder.widget(new ExpandTab().setNormalTexture(UITexture.fullImage("quantumsoundmuffler", "gui/button/anchor.png"))
									  .widget(new ListWidget().addChild(new TextWidget("Anchors"))
															  .addChild(new TextWidget("Test"))
															  .fillParent())
									  .setExpandedSize(60, 160)
									  .setSize(16, 16)
									  .setPos(258, 3)
									  .setBackground(new Rectangle().setColor(Color.GREY.normal))
									  .respectAreaInJei());
	}

	private static ListWidget CreateAnchorButtons()
	{
		final ListWidget widget = new ListWidget();
		for (Anchor anchor : anchorList)
		{

		}

		return widget;
	}


	private static ListWidget CreateSoundSliders()
	{

		soundsList.clear();
		switch (state)
		{
			case All:
				soundsList.addAll(ForgeRegistries.SOUND_EVENTS.getKeys());
				break;
			case Recent:
				soundsList.addAll(recentSounds);
			case Muffled:
				soundsList.addAll(muffledSounds.keySet());
				break;
		}

		forbiddenSounds.forEach(fs -> soundsList.removeIf(sl -> sl.toString().contains(fs)));

		ListWidget list = new ListWidget();

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
