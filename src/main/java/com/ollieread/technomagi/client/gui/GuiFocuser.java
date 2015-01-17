package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerFocuser;
import com.ollieread.technomagi.tileentity.TileEntityMachineInfuser;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFocuser extends GuiEnergyContainer
{

    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/furnace.png");
    protected TileEntityMachineInfuser focuser;

    public GuiFocuser(InventoryPlayer playerInventory, TileEntityMachineInfuser tile)
    {
        super(new ContainerFocuser(playerInventory, tile));

        focuser = tile;

        xSize = 175;
        ySize = 145;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.focuser.gui"), 7, 9, 16777215);

        int progress = focuser.getProgress();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int progress = focuser.getProgress(100);

        if (progress > 0) {
            this.drawTexturedModalRect(this.guiLeft + 38, this.guiTop + 35, 0, 176, progress, 3);
        }

        this.drawPowerLayer(focuser, this.guiLeft + xSize, this.guiTop);
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        this.mouseX = par1;
        this.mouseY = par2;
        super.drawScreen(par1, par2, par3);
        int progress = focuser.getProgress(100);

        if (mouseX >= this.guiLeft + 38 && mouseX <= this.guiLeft + 138) {
            if (mouseY >= this.guiTop + 35 && mouseY <= this.guiTop + 38) {
                List text = new ArrayList();
                text.add("Progress: " + progress + "%");

                this.drawHoveringText(text, mouseX, mouseY, this.fontRendererObj);
            }
        }
    }

}