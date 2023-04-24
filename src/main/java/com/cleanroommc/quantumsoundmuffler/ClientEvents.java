package com.cleanroommc.quantumsoundmuffler;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.cleanroommc.quantumsoundmuffler.gui.ConfigGui;
import com.cleanroommc.quantumsoundmuffler.gui.InventoryButton;
import com.gtnewhorizons.modularui.api.UIInfos;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientEvents {

    @SuppressWarnings("unchecked")
    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiInventory) {
            event.buttonList.add(new InventoryButton(((GuiContainer) event.gui)));
        }
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void guiPostAction(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.gui instanceof GuiInventory) {
            if (event.button.id == 9001) {
                UIInfos.openClientUI(event.gui.mc.thePlayer, ConfigGui::createConfigWindow);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onEnityJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayerSP) {
            DataManager.loadData();
        }
    }
}
