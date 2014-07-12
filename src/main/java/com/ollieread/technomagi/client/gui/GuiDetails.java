package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ollieread.ennds.extended.ExtendedNanites;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDetails extends GuiScreen
{

    protected int xSize = 108;
    protected int ySize = 142;
    protected int xOffset;
    protected int yOffset;
    protected ExtendedNanites nanites;
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/details.png");

    public void initGui()
    {
        this.buttonList.clear();
        this.xOffset = (this.width - this.xSize) / 2;
        this.yOffset = (this.height - this.ySize) / 2;
        this.nanites = ExtendedNanites.get(this.mc.thePlayer);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.xOffset, this.yOffset, 0, 0, this.xSize, this.ySize);

        this.fontRendererObj.drawString(I18n.format("technomagi.details.gui"), this.xOffset + 7, this.yOffset + 9, 16777215);

        int n = nanites.getNanites();
        int d = nanites.getData();

        this.mc.getTextureManager().bindTexture(texture);

        this.drawTexturedModalRect(xOffset + 4, yOffset + 111, 108, 0, n, 3);
        this.drawTexturedModalRect(xOffset + 4, yOffset + 131, 108, 3, d, 3);

        this.fontRendererObj.drawString(I18n.format("technomagi.nanites"), xOffset + 4, yOffset + 100, 16777215);
        this.fontRendererObj.drawString(I18n.format("technomagi.data"), xOffset + 4, yOffset + 120, 16777215);

        this.fontRendererObj.drawString(n + "%", (xOffset + 105) - this.fontRendererObj.getStringWidth(n + "%"), yOffset + 100, 4118771);
        this.fontRendererObj.drawString(d + "%", (xOffset + 105) - this.fontRendererObj.getStringWidth(d + "%"), yOffset + 120, 15944766);

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