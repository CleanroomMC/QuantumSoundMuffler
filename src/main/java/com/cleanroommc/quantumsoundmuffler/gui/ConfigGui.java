package com.cleanroommc.quantumsoundmuffler.gui;

import com.cleanroommc.modularui.ModularUI;
import com.cleanroommc.modularui.api.ModularUITextures;
import com.cleanroommc.modularui.api.drawable.AdaptableUITexture;
import com.cleanroommc.modularui.api.drawable.Text;
import com.cleanroommc.modularui.api.drawable.shapes.Rectangle;
import com.cleanroommc.modularui.api.math.Alignment;
import com.cleanroommc.modularui.api.math.Color;
import com.cleanroommc.modularui.api.screen.ModularWindow;
import com.cleanroommc.modularui.api.screen.UIBuildContext;
import com.cleanroommc.modularui.common.widget.*;
import com.cleanroommc.quantumsoundmuffler.QuantumSoundMuffler;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import net.minecraft.util.ResourceLocation;

public class ConfigGui implements ISoundLists {

    private static final AdaptableUITexture BACKGROUND = AdaptableUITexture.of("modularui:gui/background/background", 176, 166, 3);
    public static ModularWindow createConfigWindow(UIBuildContext buildContext) {
        ModularWindow.Builder builder = ModularWindow.builder(256, 176);

        // anchor sidebar
        AddAnchorMenu(builder);


        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND)
                .widget(new TextWidget(new Text(QuantumSoundMuffler.NAME).color(Color.WHITE.normal).shadow())
                        .setBackground(ModularUITextures.ITEM_SLOT)
                        .setPos(17, 5)
                        .setSize(221, 15)
                );

        AddSoundSliders(builder);

        return builder.build();
    }

    private static void AddAnchorMenu(ModularWindow.Builder builder) {
        builder.widget(new ExpandTab()
                .setNormalTexture(ModularUITextures.ICON_INFO.withFixedSize(14, 14, 3, 3))
                .widget(new DrawableWidget()
                        .setDrawable(ModularUITextures.ICON_INFO)
                        .setSize(14, 14)
                        .setPos(3, 3))
                .setExpandedSize(60, 160)
                .setBackground(BACKGROUND)
                .setSize(20, 20)
                .setPos(258, 3)
                .respectAreaInJei());
    }


    private static void AddSoundSliders(ModularWindow.Builder builder){

        soundsList.clear();
        soundsList.addAll(muffledSounds.keySet());
        soundsList.addAll(recentSounds);

//        if (soundsList.isEmpty()){
//            return;
//        }

        ListWidget list = new ListWidget();
        list.setBackground(new Rectangle().setColor(Color.BLACK.normal))
                .setPos(10, 25)
                .setSize(236, 130);

        for (ResourceLocation sound: soundsList){
            float vol;
            float maxVol = 1F;

            vol = muffledSounds.getOrDefault(sound, maxVol);


            QuantumSoundMuffler.LOGGER.info(String.format("%s: %f", sound.toString(), vol));
            MuffledSlider slider = new MuffledSlider(vol, sound, null);
            list.addChild(slider);
        }


        builder.widget(list);
    }

}
