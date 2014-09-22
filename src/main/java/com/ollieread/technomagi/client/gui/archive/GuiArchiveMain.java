package com.ollieread.technomagi.client.gui.archive;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.client.gui.GuiTMButton;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchiveMain extends GuiArchive
{

    protected ResourceLocation texture;

    public GuiArchiveMain(EntityPlayer player, TileEntityArchive archive)
    {
        super(player, archive);
    }

    public void initGui()
    {
        super.initGui();

        texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-main.png");

        buttonList.add(new GuiTMButton(1, guiLeft + 41, guiTop + 40, 150, 20, I18n.format("technomagi.archive.name.info")));
        buttonList.add(new GuiTMButton(2, guiLeft + 41, guiTop + 66, 150, 20, I18n.format("technomagi.archive.name.knowledge")));
        buttonList.add(new GuiTMButton(3, guiLeft + 41, guiTop + 92, 150, 20, I18n.format("technomagi.archive.name.abilities")));
        buttonList.add(new GuiTMButton(4, guiLeft + 41, guiTop + 118, 150, 20, I18n.format("technomagi.archive.name.recipes")));
        buttonList.add(new GuiTMButton(5, guiLeft + 41, guiTop + 144, 150, 20, I18n.format("technomagi.archive.name.construct")));
        buttonList.add(new GuiTMButton(6, guiLeft + 41, guiTop + 170, 150, 20, I18n.format("technomagi.archive.name.reactive")));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    protected void drawGuiContainerForegroundLayer(int var1, int var2)
    {
        fontRendererObj.drawString(I18n.format("technomagi.archive.name.main"), 9, 9, 16777215);
    }

    protected void actionPerformed(GuiButton button)
    {
        archive.setType(button.id);
        archive.setSubType(0);
        archive.setPage(0);
        PacketHelper.setArchive(archive, button.id, 0, 0);
    }

}
