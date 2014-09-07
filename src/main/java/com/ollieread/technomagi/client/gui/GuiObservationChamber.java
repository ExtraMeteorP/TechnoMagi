package com.ollieread.technomagi.client.gui;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerObservation;
import com.ollieread.technomagi.tileentity.TileEntityObservationChamber;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiObservationChamber extends GuiEnergyContainer
{

    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/observation.png");
    protected TileEntityObservationChamber chamber;
    protected GuiTMButton analyseButton;

    public GuiObservationChamber(InventoryPlayer playerInventory, TileEntityObservationChamber tile)
    {
        super(new ContainerObservation(playerInventory, tile));

        chamber = tile;

        xSize = 175;
        ySize = 192;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.observation.gui"), 11, 9, 16777215);

        int progress = chamber.getProgress();
        int data = chamber.getData();
        int health = chamber.getHealth();

        this.fontRendererObj.drawString(I18n.format("technomagi.progress"), 67, 59, 16777215);
        this.fontRendererObj.drawString(I18n.format("technomagi.data"), 67, 79, 16777215);

        this.fontRendererObj.drawString(progress + "%", 167 - this.fontRendererObj.getStringWidth(progress + "%"), 59, 4118771);
        this.fontRendererObj.drawString(data + "%", 167 - this.fontRendererObj.getStringWidth(data + "%"), 79, 15944766);

        this.fontRendererObj.drawString("" + health, 119 - this.fontRendererObj.getStringWidth("" + health), 32, 16777215);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int progress = chamber.getProgress();
        int data = chamber.getData();

        if (progress > 0) {
            this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 70, 0, 192, progress, 3);
        }

        if (data > 0) {
            this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 90, 0, 195, data, 3);
        }

        this.drawTexturedModalRect(this.guiLeft + 121, this.guiTop + 32, 0, 198, 7, 7);

        EntityLiving entity = chamber.getEntityLiving();

        if (entity != null) {
            int scale = 30;

            if (entity instanceof EntityEnderman) {
                scale = 20;
            } else if (!(entity instanceof EntityCreeper)) {
                scale = 27;
            }
            this.func_147046_a(guiLeft + 33, guiTop + 90, scale, (float) (guiLeft + 33) - var2, (float) (guiTop + 90 - 50) - var3, entity);
        }

        this.drawPowerLayer(chamber, this.guiLeft + xSize, this.guiTop);
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