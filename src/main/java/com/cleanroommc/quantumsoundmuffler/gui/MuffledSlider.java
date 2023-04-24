package com.cleanroommc.quantumsoundmuffler.gui;

import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import com.cleanroommc.quantumsoundmuffler.QuantumSoundMufflerConfig;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.drawable.AdaptableUITexture;
import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.api.drawable.Text;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.drawable.shapes.Rectangle;
import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.api.math.Color;
import com.gtnewhorizons.modularui.common.widget.*;

public class MuffledSlider extends MultiChildWidget implements ISoundLists {

    private final ResourceLocation sound;
    private float sliderValue;
    private boolean isMuffled = false;
    private final SliderWidget sliderVol;
    private final DynamicTextWidget txtLabel;

    private static final AdaptableUITexture sliderBackground = AdaptableUITexture
        .of(Textures.SLIDER_BACKGROUND, 205, 13, 1);

    public MuffledSlider(float sliderValueIn, ResourceLocation soundIn) {
        this.sliderValue = sliderValueIn;
        sound = soundIn;
        isMuffled = muffledSounds.containsKey(sound);

        this.setBackground(new Rectangle().setColor(Color.GRAY.normal));

        sliderVol = createSlider();
        CycleButtonWidget btnToggleSound = createToggleButton();
        ButtonWidget btnPlaySound = createPlayButton(btnToggleSound);

        txtLabel = TextWidget.dynamicText(this::getLabel);
        txtLabel.setSynced(false)
            .setTextAlignment(Alignment.CenterLeft)
            .setSize(sliderVol.getSize().width - 6, sliderVol.getSize().height);
        // .setPos(6, 2);

        this.addChild(sliderVol);
        this.addChild(txtLabel);
        this.addChild(btnToggleSound);
        this.addChild(btnPlaySound);
    }

    private Text getLabel() {
        String txt;
        int color;
        Alignment alignment = Alignment.CenterLeft;
        if (isMuffled) {
            if (sliderVol.isHovering()) {
                txt = String.format("Volume: %d", (int) (sliderValue * 100));
                alignment = Alignment.Center;
            } else {
                txt = String.format("%s", getSoundName());
            }
            color = Color.CYAN.normal;
        } else {
            txt = String.format("%s", getSoundName());
            color = Color.WHITE.normal;
        }
        return new Text(txt).alignment(alignment)
            .color(color)
            .shadow();
    }

    private SliderWidget createSlider() {
        SliderWidget sliderVol = new SliderWidget();

        sliderVol.setSynced(false, false);

        sliderVol.setBounds(QuantumSoundMufflerConfig.MinimumVolume, 0.9f)
            .setGetter(() -> this.sliderValue)
            .setSetter(val -> {
                this.sliderValue = MathHelper.clamp_float(val, QuantumSoundMufflerConfig.MinimumVolume, 0.9f);
                muffledSounds.replace(this.sound, this.sliderValue);
            })
            .setSize(205, 13)
            .setTicker(widget -> { widget.setEnabled(isMuffled && txtLabel.isRightBelowMouse()); })
            .setBackground(
                () -> new IDrawable[] {
                    isMuffled && txtLabel.isRightBelowMouse() ? sliderBackground.getSubArea(0, 0, sliderValue / 0.9f, 1)
                        .withFixedSize(205 * (sliderValue / 0.9f), 13) : (IDrawable) null });

        return sliderVol;
    }

    private CycleButtonWidget createToggleButton() {
        final CycleButtonWidget btnToggleSound = new CycleButtonWidget();

        btnToggleSound.setTextureGetter(state -> switch (state) {
            case 0 -> UITexture.fullImage(Textures.BUTTON_MUFFLED);
            case 1 -> UITexture.fullImage(Textures.BUTTON_UNMUFFLED);
            default -> ModularUITextures.BASE_BUTTON;
        })
            .setLength(2)
            .setGetter(() -> isMuffled ? 1 : 0)
            .setSetter(value -> {
                isMuffled = !isMuffled;
                if (value == 0) {
                    this.setBackground((IDrawable) null);
                    muffledSounds.remove(sound);
                } else if (value == 1) {
                    sliderVol.setValue(QuantumSoundMufflerConfig.DefaultMuteVolume, false);
                    muffledSounds.put(sound, this.sliderValue);
                }
            })
            .setPos(sliderVol.getSize().width + 5, 3)
            .setSize(11, 11);
        return btnToggleSound;
    }

    private ButtonWidget createPlayButton(CycleButtonWidget btnToggleSound) {
        final ButtonWidget btnPlaySound = new PlaySoundButton(sound);
        btnPlaySound.setBackground(UITexture.fullImage(Textures.BUTTON_PLAY))
            .setPos(btnToggleSound.getPos().x + 12, btnToggleSound.getPos().y)
            .setSize(11, 11);
        return btnPlaySound;
    }

    private String getSoundName() {
        return String.format(
            "%s:%s",
            sound.getResourceDomain(),
            sound.getResourcePath()
                .length() > 25 ? sound.getResourcePath()
                    .substring(0, 25) : sound.getResourcePath());
    }
}
