package com.ollieread.technomagi.client.gui.archive;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.client.gui.GuiTMButton;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.util.PacketHelper;

public class GuiArchiveInfo extends GuiArchive
{

    protected ResourceLocation texture;

    public GuiArchiveInfo(EntityPlayer player, TileEntityArchive archive)
    {
        super(player, archive);
    }

    public void initGui()
    {
        super.initGui();

        texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive-info.png");

        buttonList.add(new GuiTMButton(1, guiLeft + 4, guiTop + 25, 100, 14, I18n.format("technomagi.archive.name.main")));
        buttonList.add(new GuiPaginationButton(2, guiLeft + 9, guiTop + 227, 1));
        buttonList.add(new GuiPaginationButton(3, guiLeft + 216, guiTop + 227, 0));
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

        String content = Information.getInformation("info", "story");

        fontRendererObj.drawString("Backstory", 9, 50, 5097727);

        drawStringPage(content, 9, 65, 215, 144, false);

        if (pages) {
            ((GuiPaginationButton) buttonList.get(2)).visible = true;
        } else {
            ((GuiPaginationButton) buttonList.get(2)).visible = false;
        }

        if (page > 0) {
            ((GuiPaginationButton) buttonList.get(1)).visible = true;
        } else {
            ((GuiPaginationButton) buttonList.get(1)).visible = false;
        }
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1) {
            archive.setType(0);
            archive.setSubType(0);
            archive.setPage(0);
            PacketHelper.setArchive(archive, 0, 0, 0);
        } else {
            if (button.id == 2) {
                page -= 1;
                archive.setPage(page);
                PacketHelper.setArchive(archive, type, subtype, page);
            } else if (button.id == 3) {
                page += 1;
                archive.setPage(page);
                PacketHelper.setArchive(archive, type, subtype, page);
            }
        }
    }

}
