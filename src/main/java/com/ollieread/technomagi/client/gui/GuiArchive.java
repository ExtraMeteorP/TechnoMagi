package com.ollieread.technomagi.client.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerArchive;
import com.ollieread.technomagi.tileentity.TileEntityArchive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchive extends GuiContainer
{

    protected int mouseX;
    protected int mouseY;
    protected int xOffset;
    protected int yOffset;
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive.png");
    protected TileEntityArchive archive;
    protected int selected;
    protected int knowledgeOffset = 0;
    protected HashMap<Integer, String> buttonKnowledge = new HashMap<Integer, String>();

    public GuiArchive(InventoryPlayer playerInventory, TileEntityArchive tile)
    {
        super(new ContainerArchive(playerInventory, tile));

        archive = tile;
        xSize = 176;
        ySize = 223;
    }

    public void initGui()
    {
        super.initGui();

        this.buttonList.clear();

        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(TechnoMagi.proxy.getClientPlayer());

        int c = 0;

        List<String> knowledge = playerKnowledge.getResearchedKnowledge();
        Set<String> progress = playerKnowledge.getResearchingKnowledge();

        for (Iterator<String> i = progress.iterator(); i.hasNext();) {
            String k = i.next();

            if (!knowledge.contains(k)) {
                knowledge.add(k);
            }
        }

        List<String> knowledgeList;

        if (knowledge.size() < (knowledgeOffset + 4)) {
            knowledgeList = knowledge.subList(knowledgeOffset, knowledge.size());
        } else {
            knowledgeList = knowledge.subList(knowledgeOffset, knowledgeOffset + 4);
        }

        int y = 36;
        int buttonId = 2;

        for (Iterator<String> i = knowledgeList.iterator(); i.hasNext();) {
            String name = i.next();
            IKnowledge knowledgeItem = ResearchRegistry.getKnowledge(name);

            if (knowledgeItem != null) {
                this.buttonKnowledge.put(buttonId, name);
                this.buttonList.add(new GuiKnowledgeButton(buttonId, this.guiLeft + 157, this.guiTop + y, I18n.format("technomagi.specialise.button"), knowledgeItem.getIcon()));
                buttonId++;

                y += 22;
            }
        }
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
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id >= 2) {
            selected = button.id;

            System.out.println(buttonKnowledge.get(button.id));
        }
    }

}