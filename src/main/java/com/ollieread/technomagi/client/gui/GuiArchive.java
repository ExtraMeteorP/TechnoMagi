package com.ollieread.technomagi.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchive extends GuiScreen
{

    protected int xSize = 143;
    protected int ySize = 209;
    protected int mouseX;
    protected int mouseY;
    protected int xOffset;
    protected int yOffset;
    private static final ResourceLocation texture1 = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/archive.png");

    public void initGui()
    {
        this.buttonList.clear();
        this.xOffset = (this.width - this.xSize) / 2;
        this.yOffset = (this.height - this.ySize) / 2;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.mouseX = par1;
        this.mouseY = par2;
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture1);
        this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, this.xSize, this.ySize);

        int k2 = (this.width - this.xSize) / 2; // X asis on GUI
        int l2 = (this.height - this.ySize) / 2; // Y asis on GUI

        this.fontRendererObj.drawString(I18n.format("technomagi.archive.gui"), this.xOffset + 7, this.yOffset + 9, 16777215);

        List<IKnowledge> knowledge = ResearchRegistry.getKnowledge();

        ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(TechnoMagi.proxy.getClientPlayer());

        int x = 10;

        for (Iterator<IKnowledge> i = knowledge.iterator(); i.hasNext();) {
            IKnowledge k = i.next();

            // draw progress
            this.mc.getTextureManager().bindTexture(texture1);
            this.drawTexturedModalRect(this.xOffset + 30, this.yOffset + 20 + x + 10, 0, 212, 102, 5);

            int p = 0;

            if (charon.hasKnowledge(k.getName())) {
                p = 100;
                this.drawTexturedModalRect(this.xOffset + 31, this.yOffset + 20 + x + 11, 102, 213, 100, 5);
            } else {
                p = charon.getKnowledgeProgress(k.getName());
                this.drawTexturedModalRect(this.xOffset + 31, this.yOffset + 20 + x + 11, 102, 213, p, 5);
            }

            // draw name
            this.fontRendererObj.drawString(k.getLocalisedName(), this.xOffset + 30, this.yOffset + 20 + x, 16777215);

            // draw icon
            this.mc.getTextureManager().bindTexture(k.getIcon());
            this.func_146110_a(this.xOffset + 7, this.yOffset + 20 + x, 0, 0, 16, 16, 16, 16);

            if (mouseX >= this.xOffset + 30 && mouseX <= this.xOffset + 142) {
                if (mouseY >= this.yOffset + 20 + x + 10 && mouseY <= this.yOffset + 25 + x + 10) {
                    List text = new ArrayList();
                    text.add("Progress: " + p + " / 100");

                    this.drawHoveringText(text, mouseX, mouseY, this.fontRendererObj);
                }
            }

            x += 25;
        }

        super.drawScreen(par1, par2, par3);
    }

    private void drawGui()
    {

    }

    protected void actionPerformed(GuiButton button)
    {
        ((GuiListButton) button).selected = true;
    }

    public void onGuiClosed()
    {

    }

}