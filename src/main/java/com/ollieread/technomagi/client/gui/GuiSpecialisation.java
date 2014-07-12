package com.ollieread.technomagi.client.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.ennds.EnndsRegistry;
import com.ollieread.ennds.ISpecialisation;
import com.ollieread.ennds.common.PacketHelper;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSpecialisation extends GuiScreen
{

    public static String choice;
    private float xSize_lo;
    private float ySize_lo;
    protected int xSize = 225;
    protected int ySize = 209;
    protected int xOffset;
    protected int yOffset;
    protected HashMap<Integer, String> buttonSpecialisations = new HashMap<Integer, String>();
    protected int contentWidth = 205;
    protected int guiScale;
    protected int selected;

    protected GuiButton buttonSpecialise;

    protected GuiScrollableText scrollableText;

    private static final ResourceLocation iconLocation = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/specialisation.png");

    public void initGui()
    {
        this.buttonList.clear();
        this.xOffset = (this.width - this.xSize) / 2;
        this.yOffset = (this.height - this.ySize) / 2;

        this.buttonList.add(this.buttonSpecialise = new GuiTMButton(1, this.xOffset + 53, this.yOffset + 185, 120, 20, I18n.format("technomagi.specialise.button")));
        guiScale = this.mc.gameSettings.guiScale;

        Collection<ISpecialisation> specialisations = EnndsRegistry.getSpecialisations();
        int buttonId = 4;

        for (Iterator<ISpecialisation> i = specialisations.iterator(); i.hasNext();) {
            ISpecialisation spec = i.next();
            this.buttonSpecialisations.put(buttonId, spec.getName());
            this.buttonList.add(new GuiSpecialiseButton(buttonId, this.xOffset + 57 + ((buttonId - 4) * 40), this.yOffset + 23, I18n.format("technomagi.specialise.button"), spec.getIcon()));
            buttonId++;
        }

        scrollableText = new GuiScrollableText(xOffset + 7, yOffset + 28, xOffset + 7, yOffset + 72, 211, 109, 5, 5, "", buttonList, 2, 3, this.fontRendererObj);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(iconLocation);
        this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, this.xSize, this.ySize);
        if (selected >= 4) {
            this.drawTexturedModalRect(this.xOffset + 57 + ((selected - 4) * 40) + 1, this.yOffset + 24, 225, 0, 30, 30);
        }
        int i1;

        String s = "technomagi.specialise.gui";

        this.fontRendererObj.drawString(I18n.format(s), this.xOffset + 8, this.yOffset + 8, 16777215);

        if (choice != null) {
            String spec = "specialisation." + choice;
            this.fontRendererObj.drawString(I18n.format(spec), this.xOffset + 13, this.yOffset + 63, 16777215);
        }

        this.xSize_lo = (float) par1;
        this.ySize_lo = (float) par2;

        if (choice != null) {
            String paragraphs = Information.getInformation("specialisations", choice);
            scrollableText.setString(paragraphs);
            scrollableText.drawScreen(par1, par2, par3);
        }

        super.drawScreen(par1, par2, par3);
    }

    private void drawGui()
    {

    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1) {
            if (choice != null) {
                ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(this.mc.thePlayer);
                charon.setSpecialisation(choice);
                PacketHelper.specialisationPacket(choice);

                scrollableText.scrollY = 0;
                this.mc.displayGuiScreen(null);
            }
        } else if (button.id == 2) {
            scrollableText.scrollUp(10);
        } else if (button.id == 3) {
            scrollableText.scrollDown(10);
        } else {
            selected = button.id;
            String specName = this.buttonSpecialisations.get(button.id);

            if (specName != null) {
                this.choice = specName;
                scrollableText.scrollY = 0;
            }
        }
    }

    public void onGuiClosed()
    {
        this.choice = null;
    }

}