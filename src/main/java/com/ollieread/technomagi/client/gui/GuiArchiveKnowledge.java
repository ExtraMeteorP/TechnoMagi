package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerArchive;
import com.ollieread.technomagi.tileentity.TileEntityArchive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchiveKnowledge extends GuiContainer
{

    protected int mouseX;
    protected int mouseY;
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive.png");
    protected TileEntityArchive archive;
    protected Map<Integer, IKnowledge> knowledgeButtons = new HashMap<Integer, IKnowledge>();
    protected int selected = 0;
    protected EntityPlayer player;
    protected List tabList = new ArrayList();

    protected GuiScrollableText scrollableText;

    protected int section = 0; // 0 for knowledge, 1 for abilities

    public GuiArchiveKnowledge(EntityPlayer player, TileEntityArchive tile)
    {
        super(new ContainerArchive(player.inventory, tile));
        this.player = player;

        archive = tile;
        xSize = 238;
        ySize = 248;
    }

    public void initGui()
    {
        super.initGui();

        int x = 11;
        int y = 29;
        int z = 0;
        int id = 5;

        List<IKnowledge> knowledgeList = ResearchRegistry.getKnowledge();

        for (Iterator<IKnowledge> i = knowledgeList.iterator(); i.hasNext();) {
            IKnowledge knowledge = i.next();

            buttonList.add(new GuiTMIconButton(id, guiLeft + x + (z * 20), guiTop + y, knowledge.getName(), knowledge.getIcon(), 16, 16));
            knowledgeButtons.put(id, knowledge);

            z++;
            id++;

            if ((z % 11) == 0) {
                y += 20;
                z = 0;
            }
        }

        /*
         * tabList.add(new GuiTMTab(1, guiLeft - 26, guiTop + 30));
         * tabList.add(new GuiTMTab(2, guiLeft - 26, guiTop + 60));
         * tabList.add(new GuiTMTab(3, guiLeft - 26, guiTop + 90));
         */

        scrollableText = new GuiScrollableText(this.guiLeft + 9, this.guiTop + 14, this.guiLeft + 9, this.guiTop + 118, 220, 116, 0, 0, "", buttonList, 1, 2, fontRendererObj);
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.archive.gui"), 9, 9, 16777215);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        this.mouseX = par1;
        this.mouseY = par2;
        super.drawScreen(par1, par2, par3);

        for (int k = 0; k < this.tabList.size(); ++k) {
            ((GuiTMTab) this.tabList.get(k)).drawButton(this.mc, par1, par2);
        }

        if (selected > 4) {
            IKnowledge knowledge = knowledgeButtons.get(selected);
            ExtendedPlayerKnowledge extendedKnowledge = ExtendedPlayerKnowledge.get(player);

            if (knowledge != null && extendedKnowledge != null) {
                boolean hasKnowledge = extendedKnowledge.hasKnowledge(knowledge.getName());

                if (hasKnowledge) {
                    this.fontRendererObj.drawString(EnumChatFormatting.GREEN + knowledge.getLocalisedName(), this.guiLeft + 9, this.guiTop + 98, 16777215);
                } else {
                    this.fontRendererObj.drawString(EnumChatFormatting.RED + knowledge.getLocalisedName(), this.guiLeft + 9, this.guiTop + 98, 16777215);
                }

                int progress = extendedKnowledge.getKnowledgeProgress(knowledge.getName());

                this.fontRendererObj.drawString(progress + "%", guiLeft + xSize - 9 - this.fontRendererObj.getStringWidth(progress + "%"), this.guiTop + 98, 4118771);
                this.mc.getTextureManager().bindTexture(texture);

                this.drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 110, 0, 248, 102, 5);
                String paragraphs = Information.getInformation("knowledge", knowledge.getName());

                if (hasKnowledge) {
                    this.drawTexturedModalRect(this.guiLeft + 10, this.guiTop + 111, 0, 262, 100, 3);
                    scrollableText.setFontRenderer(fontRendererObj);
                } else {
                    if (progress > 0) {
                        this.drawTexturedModalRect(this.guiLeft + 10, this.guiTop + 111, 0, 262, progress, 3);
                    }
                    scrollableText.setFontRenderer(this.mc.standardGalacticFontRenderer);
                }

                scrollableText.setString(paragraphs);
                scrollableText.drawScreen(par1, par2, par3);
            }

            int z = (int) selected - 5;
            int x = (int) 11 + ((z % 11) * 20);
            int y = (int) 29;

            if (z > 11 || z % 11 == 0) {
                y += Math.floor(z / 11) * 20;
            }

            this.mc.getTextureManager().bindTexture(texture);
            this.drawTexturedModalRect(this.guiLeft + x - 1, this.guiTop + y - 1, 238, 0, 18, 18);
        }

        for (Iterator i = buttonList.iterator(); i.hasNext();) {
            GuiButton button = (GuiButton) i.next();

            if (!(button instanceof GuiTMIconButton)) {
                continue;
            }

            GuiTMIconButton iconButton = (GuiTMIconButton) button;

            if (mouseX >= iconButton.xPosition && mouseX <= (iconButton.xPosition + iconButton.width)) {
                if (mouseY >= iconButton.yPosition && mouseY <= (iconButton.yPosition + iconButton.height)) {
                    IKnowledge knowledge = knowledgeButtons.get(iconButton.id);
                    ExtendedPlayerKnowledge extendedKnowledge = ExtendedPlayerKnowledge.get(player);

                    if (knowledge != null && extendedKnowledge != null) {
                        List text = new ArrayList();

                        if (extendedKnowledge.hasKnowledge(knowledge.getName())) {
                            text.add(EnumChatFormatting.GREEN + knowledge.getLocalisedName());
                        } else {
                            text.add(EnumChatFormatting.RED + knowledge.getLocalisedName());
                        }

                        text.add(extendedKnowledge.getKnowledgeProgress(knowledge.getName()) + "%");

                        this.drawHoveringText(text, mouseX, mouseY, this.fontRendererObj);
                    }
                }
            }
        }
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1) {
            scrollableText.scrollUp(10);
        } else if (button.id == 2) {
            scrollableText.scrollDown(10);
        } else if (button.id > 4) {
            selected = button.id;
        }
    }

}