package com.ollieread.technomagi.client.gui;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ollieread.ennds.ISpecialisation;
import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.ability.IAbility;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDiagnosis extends GuiScreen
{

    protected int xSize = 140;
    protected int ySize = 130;
    protected int xOffset;
    protected int yOffset;
    protected ExtendedPlayerKnowledge charon;
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/diagnosis.png");
    protected Map<String, Integer> knowledgeProgress;
    protected int progressX = 0;
    protected int progressY = 0;
    protected int mouseX;
    protected int mouseY;

    public void initGui()
    {
        this.buttonList.clear();
        this.xOffset = (this.width - this.xSize) / 2;
        this.yOffset = (this.height - this.ySize) / 2;
        this.charon = ExtendedPlayerKnowledge.get(this.mc.thePlayer);
        this.knowledgeProgress = charon.nanites.getResearchingKnowledge();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.mouseX = par1 - xOffset;
        this.mouseY = par2 - yOffset;
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, this.xSize, this.ySize);

        this.fontRendererObj.drawString(I18n.format("technomagi.diagnosis.gui"), this.xOffset + 7, this.yOffset + 9, 16777215);

        ISpecialisation specialisation = charon.getSpecialisation();

        if (specialisation != null) {
            ResourceLocation icon = specialisation.getIcon();

            this.mc.getTextureManager().bindTexture(texture);

            progressX = 132;
            progressY = 126;
            int data = charon.nanites.getData();

            drawTexturedModalRect(xOffset + progressX, yOffset + (progressY - data), 140, (progressY - data), 4, data);

            drawKnowledgeProgress();

            drawCooldownProgress();

            this.mc.getTextureManager().bindTexture(icon);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_146110_a(xOffset + 13, yOffset + 28, 0, 0, 32, 32, 32, 32);
            this.func_147046_a(xOffset + 28, yOffset + 120, 30, (float) (xOffset + 28) - par1, (float) (yOffset + 120 - 50) - par2, this.mc.thePlayer);
        }

        super.drawScreen(par1, par2, par3);
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    private void drawKnowledgeProgress()
    {
        for (Iterator<Entry<String, Integer>> i = knowledgeProgress.entrySet().iterator(); i.hasNext();) {
            Entry<String, Integer> entry = i.next();
            int p = entry.getValue();

            if (mouseX >= progressX && mouseX <= progressX + 4) {
                if (mouseY >= (progressY - p) && mouseY <= progressY) {
                    progressY -= p;
                    drawTexturedModalRect(xOffset + progressX, yOffset + progressY, 144, progressY, 4, p);
                    drawTexturedModalRect(xOffset + progressX, yOffset + progressY, 140, 26, 4, 1);
                    drawHoveringKnowledgeProgress(entry.getKey(), p, xOffset + progressX + 4, yOffset + progressY + (p / 2), fontRendererObj);
                    continue;
                }
            }

            progressY -= p;
            drawTexturedModalRect(xOffset + progressX, yOffset + progressY, 110, 26, 4, 1);
        }
    }

    private void drawCooldownProgress()
    {
        int x = 0;
        int coolY = 25 + yOffset;
        for (Iterator<Entry<String, Integer>> i = charon.abilities.getCooldowns().entrySet().iterator(); i.hasNext();) {
            if (x == 5) {
                break;
            }

            Entry<String, Integer> entry = i.next();
            String abilityName = entry.getKey();
            Integer cooldown = entry.getValue();
            IAbility ability = AbilityRegistry.getAbility(abilityName);

            if (ability != null) {
                this.mc.getTextureManager().bindTexture(texture);
                this.drawTexturedModalRect(56 + xOffset, coolY, 148, 0, 71, 22);
                this.mc.getTextureManager().bindTexture(ability.getIcon());
                this.func_146110_a(60 + xOffset, coolY + 3, 0, 0, 16, 16, 16, 16);
                this.fontRendererObj.drawString(StringUtils.ticksToElapsedTime(cooldown), xOffset + 88, coolY + 7, 16777215);
                this.mc.getTextureManager().bindTexture(texture);
                coolY += 25;
            }

            x++;
        }
    }

    private void drawHoveringKnowledgeProgress(String knowledge, int p, int x, int y, FontRenderer font)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        int k = 36;
        y += 4;

        int j2 = x + 12;
        int k2 = y - 12;
        int i1 = 16;

        if (j2 + k > this.width) {
            j2 -= 28 + k;
        }

        if (k2 + i1 + 6 > this.height) {
            k2 = this.height - i1 - 6;
        }

        this.zLevel = 300.0F;
        itemRender.zLevel = 300.0F;
        int j1 = -267386864;
        this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
        this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
        this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
        this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
        this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
        int k1 = 1347420415;
        int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
        this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
        this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
        this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
        this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

        font.drawStringWithShadow(p + "%", j2, k2 + 4, -1);

        ResourceLocation icon = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/knowledge/unknown.png");

        if (charon.getResearchingKnowledge().contains(knowledge)) {
            IKnowledge ik = ResearchRegistry.getKnowledge(knowledge);
            icon = ik.getIcon();
        }

        this.mc.getTextureManager().bindTexture(icon);
        this.func_146110_a(j2 + font.getStringWidth(p + "%") + 2, k2, 0, 0, 16, 16, 16, 16);
        this.mc.getTextureManager().bindTexture(texture);

        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
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

    public static void func_147046_a(int par1, int par2, int par3, float par4, float par5, EntityLivingBase p_147046_5_)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) par1, (float) par2, 50.0F);
        GL11.glScalef((float) (-par3), (float) par3, (float) par3);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = p_147046_5_.renderYawOffset;
        float f3 = p_147046_5_.rotationYaw;
        float f4 = p_147046_5_.rotationPitch;
        float f5 = p_147046_5_.prevRotationYawHead;
        float f6 = p_147046_5_.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan((double) (par5 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_147046_5_.renderYawOffset = (float) Math.atan((double) (par4 / 40.0F)) * 20.0F;
        p_147046_5_.rotationYaw = (float) Math.atan((double) (par4 / 40.0F)) * 40.0F;
        p_147046_5_.rotationPitch = -((float) Math.atan((double) (par5 / 40.0F))) * 20.0F;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GL11.glTranslatef(0.0F, p_147046_5_.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        p_147046_5_.renderYawOffset = f2;
        p_147046_5_.rotationYaw = f3;
        p_147046_5_.rotationPitch = f4;
        p_147046_5_.prevRotationYawHead = f5;
        p_147046_5_.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

}