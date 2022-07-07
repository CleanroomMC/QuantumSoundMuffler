package com.cleanroommc.quantumsoundmuffler.mixin;

import com.cleanroommc.quantumsoundmuffler.QuantumSoundMuffler;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundManager.class)
public abstract class MixinSound implements ISoundLists {

    @Inject(method = "getClampedVolume", at = @At("RETURN"), cancellable = true)
    private void getClampedVolume(ISound soundIn, CallbackInfoReturnable<Float> cir) {
        Sound sound = soundIn.getSound();

        recentSounds.add(soundIn.getSoundLocation());

        //cir.setReturnValue(0.0f);
    }

}
