package com.cleanroommc.quantumsoundmuffler.mixin;

import com.cleanroommc.quantumsoundmuffler.QuantumSoundMuffler;
import com.cleanroommc.quantumsoundmuffler.QuantumSoundMufflerConfig;
import com.cleanroommc.quantumsoundmuffler.interfaces.ISoundLists;
import com.cleanroommc.quantumsoundmuffler.utils.Anchor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundManager.class)
public abstract class MixinSound implements ISoundLists
{

	@Inject(method = "getClampedVolume", at = @At("RETURN"), cancellable = true)
	private void getClampedVolume(ISound sound, CallbackInfoReturnable<Float> cir)
	{
		ResourceLocation soundLocation = sound.getSoundLocation();

		if (isForbidden(soundLocation))
		{
			return;
		}

		recentSounds.add(soundLocation);

		if (muffledSounds.containsKey(soundLocation))
		{
			cir.setReturnValue(cir.getReturnValue() * muffledSounds.get(soundLocation));
			return;
		}

		if (!QuantumSoundMufflerConfig.disableAnchors)
		{
			Anchor anchor = Anchor.getAnchor(sound);
			if (anchor != null)
			{
				cir.setReturnValue(cir.getReturnValue() * anchor.getMuffledSounds().get(soundLocation));
				return;
			}
		}

	}

	private static boolean isForbidden(ResourceLocation soundLocation)
	{
		for (String fs : forbiddenSounds)
		{
			if (soundLocation.toString().contains(fs))
			{
				return true;
			}
		}
		return false;
	}

}
