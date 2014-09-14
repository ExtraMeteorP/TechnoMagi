package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerConstruct;
import com.ollieread.technomagi.tileentity.TileEntityConstruct;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiConstruct extends GuiContainer
{

    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/construct.png");
    protected TileEntityConstruct construct;
    protected GuiTMButton buildButton;
    protected EntityPlayer player;

    public GuiConstruct(InventoryPlayer inventory, TileEntityConstruct construct)
    {
        super(new ContainerConstruct(inventory, construct));

        this.construct = construct;

        xSize = 185;
        ySize = 162;
    }

    public void initGui()
    {
        super.initGui();

        buildButton = new GuiTMButton(1, this.guiLeft + 42, this.guiTop + 49, 101, 15, I18n.format("technomagi.build.button"));
        this.buttonList.add(buildButton);

        if (!construct.isBuilding() && construct.canBuild()) {
            buildButton.enabled = true;
        } else {
            buildButton.enabled = false;
        }
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.construct.gui"), 7, 9, 16777215);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1) {
            construct.setBuilding(true);
            button.enabled = false;
            TechnoMagi.proxy.getClientPlayer().closeScreen();
            PacketHelper.setBuilding(construct, true);
        }
    }

    public void updateScreen()
    {
        if (!construct.isBuilding() && construct.canBuild()) {
            buildButton.enabled = true;
        } else {
            buildButton.enabled = false;
        }
    }

}