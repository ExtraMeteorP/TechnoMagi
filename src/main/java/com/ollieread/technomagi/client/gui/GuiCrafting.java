package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerCrafting;
import com.ollieread.technomagi.tileentity.TileEntityCrafting;
import com.ollieread.technomagi.util.PacketHelper;

public class GuiCrafting extends GuiEnergyContainer
{

    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/crafting.png");
    protected TileEntityCrafting crafter;
    protected GuiTMButton craftingButton;

    public GuiCrafting(InventoryPlayer playerInventory, TileEntityCrafting tile)
    {
        super(new ContainerCrafting(playerInventory, tile));

        crafter = tile;

        xSize = 176;
        ySize = 176;
    }

    public void initGui()
    {
        super.initGui();

        craftingButton = new GuiTMButton(1, this.guiLeft + 96, this.guiTop + 62, 70, 15, I18n.format("technomagi.craft.button"));
        this.buttonList.add(craftingButton);

        if (!crafter.canCraft() || crafter.isCrafting()) {
            craftingButton.enabled = false;
        } else if (crafter.canCraft() && !crafter.isCrafting()) {
            craftingButton.enabled = true;
        }
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.crafter.gui"), 7, 9, 16777215);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int progress = crafter.getProgress();

        if (progress > 0) {
            this.drawTexturedModalRect(this.guiLeft + 121, this.guiTop + 51, 176, 0, progress, 3);
        }

        this.drawPowerLayer(crafter, this.guiLeft + xSize, this.guiTop);
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1 && crafter.canCraft()) {
            crafter.setCrafting(true);
            button.enabled = false;
            PacketHelper.setCrafting(crafter, true);
        }
    }

    public void updateScreen()
    {
        if (!crafter.canCraft() || crafter.isCrafting()) {
            craftingButton.enabled = false;
        } else if (crafter.canCraft() && !crafter.isCrafting()) {
            craftingButton.enabled = true;
        }
    }

}
