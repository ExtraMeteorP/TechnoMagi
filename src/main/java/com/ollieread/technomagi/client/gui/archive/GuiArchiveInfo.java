package com.ollieread.technomagi.client.gui.archive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.client.gui.GuiTMButton;
import com.ollieread.technomagi.client.gui.GuiTMTextButton;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchiveInfo extends GuiArchive
{

    protected ResourceLocation texture;
    protected List infoButtons = new ArrayList();
    protected String info = "";
    protected Map<String, List<String>> infoList = new LinkedHashMap<String, List<String>>();
    protected Map<Integer, String> infoMapping = new HashMap<Integer, String>();
    protected int prevPage = 0;

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

        infoList = Information.getInformation("info");
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
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        fontRendererObj.drawString(I18n.format("technomagi.archive.name.main"), 9, 9, 16777215);
        infoButtons.clear();
        infoMapping.clear();

        if (subtype == 0) {
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.main");
            drawMainLayer();
        } else if (subtype == 1) {
            ((GuiButton) buttonList.get(0)).displayString = I18n.format("technomagi.archive.name.info");
            drawInfoLayer();
        }
    }

    protected void drawMainLayer()
    {
        fontRendererObj.drawString(I18n.format("technomagi.archive.name.info"), 9, 50, 5097727);

        if (infoList != null && infoList.size() > 0) {
            int start = 8 * page;
            int end = start + 8;

            ArrayList keyList = new ArrayList();
            keyList.addAll(infoList.keySet());

            List<String> sublist = keyList.subList(8 * page, end > infoList.size() ? infoList.size() : end);

            int x = 9;
            int y = 65;
            int id = 4;

            for (Iterator<String> i = sublist.iterator(); i.hasNext();) {
                String key = i.next();

                GuiTMTextButton button = new GuiTMTextButton(id, x, y, guiLeft, guiTop, I18n.format("info." + key));
                infoButtons.add(button);
                infoMapping.put(id, key);
                button.drawButton(Minecraft.getMinecraft(), x, y);

                y += 14;
                id++;
            }

            if (end < infoList.size()) {
                ((GuiPaginationButton) buttonList.get(2)).visible = true;
            } else {
                ((GuiPaginationButton) buttonList.get(2)).visible = false;
            }

            if (start > 0) {
                ((GuiPaginationButton) buttonList.get(1)).visible = true;
            } else {
                ((GuiPaginationButton) buttonList.get(1)).visible = false;
            }
        }
    }

    protected void drawInfoLayer()
    {
        String content = Information.getInformation("info", "" + info);

        fontRendererObj.drawString(I18n.format("info." + info), 9, 50, 5097727);

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
            if (subtype == 1) {
                subtype = 0;
                page = prevPage;
                archive.setSubType(0);
                archive.setPage(prevPage);
                PacketHelper.setArchive(archive, type, subtype, page);
            } else {
                archive.setType(0);
                archive.setSubType(0);
                archive.setPage(0);
                PacketHelper.setArchive(archive, 0, 0, 0);
            }
        } else {
            if (button.id == 2) {
                page -= 1;
                archive.setPage(page);
                PacketHelper.setArchive(archive, type, subtype, page);
            } else if (button.id == 3) {
                page += 1;
                archive.setPage(page);
                PacketHelper.setArchive(archive, type, subtype, page);
            } else if (button.id > 3) {
                int i = button.id;

                if (infoMapping.containsKey(i)) {
                    subtype = 1;
                    info = infoMapping.get(i);
                    prevPage = page;
                    page = 0;
                    archive.setSubType(1);
                    archive.setPage(0);
                    PacketHelper.setArchive(archive, type, subtype, page);
                }
            }
        }
    }

    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);

        if (p_73864_3_ == 0) {
            for (int l = 0; l < this.infoButtons.size(); ++l) {
                GuiTMTextButton guibutton = (GuiTMTextButton) this.infoButtons.get(l);

                if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_)) {
                    guibutton.func_146113_a(this.mc.getSoundHandler());
                    actionPerformed(guibutton);
                }
            }
        }
    }

}
