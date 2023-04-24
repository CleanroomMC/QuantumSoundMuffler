package com.cleanroommc.quantumsoundmuffler.gui;

import com.cleanroommc.quantumsoundmuffler.DataManager;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.drawable.shapes.Rectangle;
import com.gtnewhorizons.modularui.api.math.Color;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.ListWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;

public class ConfigGui implements ISoundLists {

    private static State state = State.Recent;

    public static boolean wasOpened = false;

    public static ModularWindow createConfigWindow(UIBuildContext buildContext) {
        buildContext.setShowNEI(false);
        buildContext.addCloseListener(() -> {
            DataManager.saveData();
            wasOpened = false;
        });

        ModularWindow.Builder builder = ModularWindow.builder(256, 176);

        builder.setBackground(ModularUITextures.VANILLA_BACKGROUND)
            .widget(
                new TextWidget(
                    new Text("Quantum Sound Muffler").color(Color.WHITE.normal)
                        .shadow()).setBackground(ModularUITextures.ITEM_SLOT)
                            .setPos(17, 5)
                            .setSize(221, 15));

        builder.widget(
            CreateSoundSliders().setPos(10, 25)
                .setSize(236, 122)
                .setBackground(new Rectangle().setColor(Color.BLACK.normal)));

        wasOpened = true;

        return builder.build();
    }

    private static ListWidget CreateSoundSliders() {

        soundsList.clear();
        switch (state) {
            // no All for now
            // case All:
            // soundsList.addAll(ForgeRegistries.SOUND_EVENTS.getKeys());
            // break;
            case Recent:
                soundsList.addAll(recentSounds);
            case Muffled:
                soundsList.addAll(muffledSounds.keySet());
                break;
        }

        forbiddenSounds.forEach(
            fs -> soundsList.removeIf(
                sl -> sl.toString()
                    .contains(fs)));

        ListWidget list = new ListWidget();

        soundsList.stream()
            .filter(muffledSounds::containsKey)
            .map(sound -> new MuffledSlider(muffledSounds.get(sound), sound))
            .forEachOrdered(list::addChild);

        soundsList.stream()
            .filter(key -> !muffledSounds.containsKey(key))
            .map(sound -> new MuffledSlider(1f, sound))
            .forEachOrdered(list::addChild);

        return list;
    }

    private enum State {
        Recent,
        Muffled
    }
}
