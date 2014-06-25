package com.ollieread.technomagi.event.handler;

import java.lang.reflect.Field;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Post;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderEventHandler
{

    public Field manager;

    public RenderEventHandler(Field f)
    {
        manager = f;
    }

    @SubscribeEvent
    public void onPostRender(Post event)
    {
        if (event.entity instanceof EntityOtherPlayerMP) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            RenderManager renderManager;
            try {
                renderManager = (RenderManager) manager.get(event.renderer);
                double d3 = event.entity.getDistanceSqToEntity(renderManager.livingPlayer);
                String s = charon.getSpecialisation().getLocalisedName();
                double x = event.entity.lastTickPosX + ((event.entity.posX - renderManager.renderPosX) - event.entity.lastTickPosX);
                double y = event.entity.lastTickPosY + ((event.entity.posY - renderManager.renderPosY) - event.entity.lastTickPosY);
                double z = event.entity.lastTickPosZ + ((event.entity.posZ - renderManager.renderPosZ) - event.entity.lastTickPosZ);

                if (d3 <= (double) (64 * 64)) {
                    FontRenderer fontrenderer = renderManager.getFontRenderer();
                    float f = 1.6F;
                    float f1 = 0.016666668F * f;
                    GL11.glPushMatrix();
                    GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                    GL11.glTranslatef((float) x + 0.0F, (float) y + player.height + 0.8F, (float) z);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                    GL11.glScalef(-f1, -f1, f1);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDepthMask(false);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    Tessellator tessellator = Tessellator.instance;
                    byte b0 = 0;

                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    int j = fontrenderer.getStringWidth(s) / 2;
                    tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.addVertex((double) (-j - 1), (double) (-1 + b0), 0.0D);
                    tessellator.addVertex((double) (-j - 1), (double) (8 + b0), 0.0D);
                    tessellator.addVertex((double) (j + 1), (double) (8 + b0), 0.0D);
                    tessellator.addVertex((double) (j + 1), (double) (-1 + b0), 0.0D);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, b0, 553648127);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDepthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, b0, -1);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    /*
     * @SubscribeEvent public void onArmourRender(SetArmorModel event) {
     * event.setCanceled(true); }
     * 
     * @SubscribeEvent public void onPreRenderPlayer(Pre event) {
     * event.setCanceled(true);
     * 
     * RenderRobotCow r = new RenderRobotCow(new ModelRobotCow(), 0.5F); }
     */

}
