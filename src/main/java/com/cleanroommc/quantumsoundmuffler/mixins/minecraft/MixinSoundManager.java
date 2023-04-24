package com.cleanroommc.quantumsoundmuffler.mixins.minecraft;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.cleanroommc.quantumsoundmuffler.gui.PlaySoundButton;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;

@Mixin(value = net.minecraft.client.audio.SoundManager.class, priority = 900)
public abstract class MixinSoundManager implements ISoundLists {

    @Inject(method = "getNormalizedVolume", cancellable = true, at = @At(value = "RETURN"))
    private void qsm$getNormalizedVolume(ISound sound, SoundPoolEntry entry, SoundCategory category,
        CallbackInfoReturnable<Float> cir) {
        if (PlaySoundButton.isIsFromPSB()) return;

        ResourceLocation soundLocation = sound.getPositionedSoundLocation();
        if (isForbidden(soundLocation)) {
            return;
        }

        recentSounds.add(soundLocation);

        if (muffledSounds.containsKey(soundLocation)) {
            cir.setReturnValue(cir.getReturnValue() * muffledSounds.get(soundLocation));
        }
    }

    private static boolean isForbidden(ResourceLocation soundLocation) {
        for (String fs : forbiddenSounds) {
            if (soundLocation.toString()
                .contains(fs)) {
                return true;
            }
        }
        return false;
    }

}
