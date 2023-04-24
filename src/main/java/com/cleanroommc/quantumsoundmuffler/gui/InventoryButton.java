package com.cleanroommc.quantumsoundmuffler.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

import com.cleanroommc.quantumsoundmuffler.mixins.minecraft.IMixinGuiContainer;
import com.gtnewhorizons.modularui.api.drawable.UITexture;

@SuppressWarnings("ReferenceToMixin")
public class InventoryButton extends GuiButton {

    public InventoryButton(GuiContainer container) {
        super(9001, 0, 0, 11, 11, "Quantum Sound Muffler");

        IMixinGuiContainer mixRef = (IMixinGuiContainer) container;
        this.xPosition = mixRef.getGuiLeft() + mixRef.getXSize() / 2 - 8;
        this.yPosition = mixRef.getGuiTop() + 67;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width
                && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            if (k != 1) {
                this.drawCenteredString(
                    mc.fontRenderer,
                    this.displayString,
                    this.xPosition + 5,
                    this.yPosition + this.height,
                    0xffffff);
            }

            UITexture.fullImage(Textures.BUTTON_MUFFLED)
                .draw(this.xPosition, this.yPosition, this.width, this.height);
        }
    }
}
