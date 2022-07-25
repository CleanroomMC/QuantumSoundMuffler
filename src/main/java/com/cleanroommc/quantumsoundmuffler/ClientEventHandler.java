package com.cleanroommc.quantumsoundmuffler;


import com.cleanroommc.modularui.api.UIInfos;
import com.cleanroommc.quantumsoundmuffler.gui.ConfigGui;
import com.cleanroommc.quantumsoundmuffler.utils.DataManger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = QuantumSoundMuffler.ID, value = Side.CLIENT)
public class ClientEventHandler
{

    public static final KeyBinding configGuiKey = new KeyBinding("key.open_muffler", KeyConflictContext.UNIVERSAL, KeyModifier.CONTROL, Keyboard.KEY_HOME, "key.categories.quantumsoundmuffler");

    private static GuiScreen previousScreen;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        handleInput(null);
    }

    @SubscribeEvent
    public static void onGuiKeyInput(GuiScreenEvent.KeyboardInputEvent.Post event)
    {
        if (!(event.getGui() instanceof GuiContainer))
            return;
        if (handleInput((GuiContainer) event.getGui()))
        {
            event.setCanceled(true);
        }
    }

    private static boolean handleInput(@Nullable GuiContainer container)
    {
        if (container != null && container.isFocused())
        {
            return false;
        }

        if (configGuiKey.isPressed())
        {
            if (ConfigGui.wasOpened)
            {
                Minecraft.getMinecraft().displayGuiScreen(previousScreen);
                previousScreen = null;
            } else
            {
                previousScreen = Minecraft.getMinecraft().currentScreen;
                UIInfos.openClientUI(Minecraft.getMinecraft().player, ConfigGui::createConfigWindow);
            }

            return true;
        }

        return false;
    }


    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        DataManger.loadData();
    }
}
