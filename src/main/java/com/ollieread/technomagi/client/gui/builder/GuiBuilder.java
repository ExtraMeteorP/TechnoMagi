package com.ollieread.technomagi.client.gui.builder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ollieread.technomagi.common.Reference;

public class GuiBuilder extends Gui
{
    public static ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/widgets.png");
    public static GuiBuilder instance = new GuiBuilder();

    public int width;
    public int height;
    public int xOffset;
    public int yOffset;
    public int mouseX;
    public int mouseY;
    public Minecraft mc = Minecraft.getMinecraft();
    protected int buttonIds = -1;
    protected Map<String, IGuiElement> elements = new HashMap<String, IGuiElement>();
    protected String heading;
    public ScaledResolution scaled;

    public void init(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void setOffset(int screenWidth, int screenHeight)
    {
        this.xOffset = (screenWidth - this.width) / 2;
        this.yOffset = (screenHeight - this.height) / 2;
    }

    public void setDimensions(int width, int height)
    {
        if (width > this.width) {
            this.xOffset -= (width - this.width) / 2;
            this.width = width;
        } else if (width < this.width) {
            this.xOffset += (this.width + width) / 2;
            this.width = width;
        }

        if (height > this.height) {
            this.yOffset -= (height - this.height) / 2;
            this.height = height;
        } else if (height < this.height) {
            this.yOffset += (this.height + height) / 2;
            this.height = height;
        }
    }

    public void setHeading(String heading)
    {
        this.heading = heading;
    }

    public void setMouse(int x, int y)
    {
        this.mouseX = x;
        this.mouseY = y;
    }

    public int getButtonId()
    {
        buttonIds++;
        return buttonIds;
    }

    public void addElement(IGuiElement element)
    {
        elements.put(element.getName(), element);
    }

    public void resetElements()
    {
        elements = new HashMap<String, IGuiElement>();
        buttonIds = -1;
    }

    public void drawElementBackgrounds()
    {
        this.scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        int x = 3;
        int y = 24;

        for (IGuiElement element : elements.values()) {
            element.drawBackground(x, y);
        }
    }

    public void drawElements()
    {
        drawHeading(heading);

        int x = 3;
        int y = 24;

        for (IGuiElement element : elements.values()) {
            element.draw(x, y);
        }
    }

    public void hover(int xPosition, int yPosition)
    {
        int x = xOffset + 3;
        int y = yOffset + 24;

        for (IGuiElement element : elements.values()) {
            if (element.hover(x, y, xPosition, yPosition)) {
                break;
            }
        }
    }

    public String clicked(int xPosition, int yPosition)
    {
        int x = xOffset + 3;
        int y = yOffset + 24;

        for (IGuiElement element : elements.values()) {
            String link = element.clicked(x, y, xPosition, yPosition);

            if (link != null && !link.isEmpty()) {
                return link;
            }
        }

        return null;
    }

    /**
     * Everything below here will draw the sections
     */

    public int drawString(String string, int x, int y, int c)
    {
        x += xOffset;
        y += yOffset;

        return mc.fontRenderer.drawString(string, x, y, c);
    }

    public void drawBackground()
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(3, 17, 34, 255);
        tessellator.addVertex((double) xOffset, (double) yOffset + height, 0.0D);
        tessellator.addVertex((double) xOffset + width, (double) yOffset + height, 0.0D);
        tessellator.addVertex((double) xOffset + width, (double) yOffset, 0.0D);
        tessellator.addVertex((double) xOffset, (double) yOffset, 0.0D);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void drawHeading(String heading)
    {
        int w = width - 6;
        int h = 18;
        FontRenderer fontrenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xOffset + 3, yOffset + 3, 0, 144, w / 2, h);
        drawTexturedModalRect(xOffset + 3 + (w / 2), yOffset + 3, 256 - w / 2, 144, w / 2, h);
        drawString(fontrenderer, heading, xOffset + 9, yOffset + 3 + (h - 8) / 2, 16777215);
    }

    public void drawVerticalProgressBarBackground(int x, int y, int h, int p)
    {
        int w = 5;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xOffset + x, yOffset + y, 176, 14, w, h / 2);
        drawTexturedModalRect(xOffset + x, yOffset + y + (h / 2), 176, 116 - (h / 2), w, h / 2);
    }

    public void drawVerticalProgressBarForeground(int x, int y, int h, int t, int p)
    {
        int w = 5;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (p > 0) {
            switch (t) {
                case 0:
                    drawTexturedModalRect(xOffset + x + 1, yOffset + y + h - (p + 1), 181, 115 - p, 3, p);
                    break;
                case 1:
                    drawTexturedModalRect(xOffset + x + 1, yOffset + y + h - (p + 1), 184, 115 - p, 3, p);
                    break;
                case 2:
                    drawTexturedModalRect(xOffset + x + 1, yOffset + y + h - (p + 1), 187, 115 - p, 3, p);
                    break;
            }
        }
    }

    public void drawHorizontalProgressBarBackground(int x, int y, int w, int p)
    {
        int h = 5;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xOffset + x, yOffset + y, 74, 90, w / 2, h);
        drawTexturedModalRect(xOffset + x + (w / 2), yOffset + y, 176 - (w / 2), 90, w / 2, h);
    }

    public void drawHorizontalProgressBarForeground(int x, int y, int w, int t, int p)
    {
        int h = 5;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (p > 0) {
            switch (t) {
                case 0:
                    drawTexturedModalRect(xOffset + x + 1, yOffset + y + 1, 75, 95, p, 3);
                    break;
                case 1:
                    drawTexturedModalRect(xOffset + x + 1, yOffset + y + 1, 75, 98, p, 3);
                    break;
                case 2:
                    drawTexturedModalRect(xOffset + x + 1, yOffset + y + 1, 75, 101, p, 3);
                    break;
            }
        }
    }

    public void drawButton(String text, int x, int y, int w, boolean hover)
    {
        FontRenderer fontrenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        int yh = !hover ? 162 : 182;
        drawTexturedModalRect(xOffset + x, yOffset + y, 0, yh, w / 2, 20);
        drawTexturedModalRect(xOffset + x + (w / 2), yOffset + y, 256 - w / 2, yh, w / 2, 20);
        int l = !hover ? 16777215 : 2529246;
        drawCenteredString(fontrenderer, text, xOffset + x + (w / 2), yOffset + y + (12) / 2, l);
    }

    public void drawTab(int x, int y, int t, boolean selected)
    {
        int w = 0;
        int h = 0;
        int xo = 0;
        int yo = 202;

        switch (t) {
            case 0:
                w = 28;
                h = 30;
                y -= h;
                if (selected) {
                    xo += w;
                }
                break;
            case 1:
                w = 28;
                h = 30;
                xo = 56;
                if (selected) {
                    xo += w;
                }
                break;
            case 2:
                w = 30;
                h = 28;
                xo = 112;
                if (selected) {
                    xo += w;
                }
                break;
            case 3:
                w = 30;
                h = 28;
                xo = 172;
                x -= w;
                if (selected) {
                    xo += w;
                }
                break;
        }

        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xOffset + x, yOffset + y, xo, yo, w, h);
    }

    public void drawTab(int x, int y, int t, ResourceLocation image, boolean selected)
    {
        int w = 0;
        int h = 0;
        int xo = 0;
        int yo = 202;
        int ix = 0;
        int iy = 0;

        switch (t) {
            case 0:
                w = 28;
                h = 30;
                y -= h;
                ix = 6;
                iy = 7;
                if (selected) {
                    xo += w;
                }
                break;
            case 1:
                w = 28;
                h = 30;
                xo = 56;
                ix = 6;
                iy = 7;
                if (selected) {
                    xo += w;
                }
                break;
            case 2:
                w = 30;
                h = 28;
                xo = 112;
                ix = 7;
                iy = 6;
                if (selected) {
                    xo += w;
                }
                break;
            case 3:
                w = 30;
                h = 28;
                xo = 172;
                x -= w;
                ix = 7;
                iy = 6;
                if (selected) {
                    xo += w;
                }
                break;
        }

        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xOffset + x, yOffset + y, xo, yo, w, h);
        mc.getMinecraft().getTextureManager().bindTexture(image);
        drawImage(x + ix, y + iy, 0, 0, 16, 16, 16, 16);
    }

    public void drawSectionBackground(int x, int y, int w, int h)
    {
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xOffset + x, yOffset + y, 0, 144, w / 2, 3);
        drawTexturedModalRect(xOffset + x + (w / 2), yOffset + y, 256 - w / 2, 144, w / 2, 3);

        drawStretchedRect(xOffset + x, yOffset + y + 3, 0, 147, w, h - 6);

        drawTexturedModalRect(xOffset + x, yOffset + y + h - 3, 0, 159, w / 2, 3);
        drawTexturedModalRect(xOffset + x + (w / 2), yOffset + y + h - 3, 256 - w / 2, 159, w / 2, 3);
    }

    public void drawTextureArea(int x, int y, int u, int v, int w, int h)
    {
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xOffset + x, yOffset + y, u, v, w, h);
    }

    public void drawStretchedRect(int x, int y, int u, int v, int w, int h)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + h), (double) zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + 1) * f1));
        tessellator.addVertexWithUV((double) (x + w), (double) (y + h), (double) zLevel, (double) ((float) (u + w) * f), (double) ((float) (v + 1) * f1));
        tessellator.addVertexWithUV((double) (x + w), (double) (y + 0), (double) zLevel, (double) ((float) (u + w) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
    }

    public void drawImage(int x, int y, float u, float v, int w, int h, float ow, float oh)
    {
        float f4 = 1.0F / ow;
        float f5 = 1.0F / oh;
        x += xOffset;
        y += yOffset;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) x, (double) (y + h), 0.0D, (double) (u * f4), (double) ((v + (float) h) * f5));
        tessellator.addVertexWithUV((double) (x + w), (double) (y + h), 0.0D, (double) ((u + (float) w) * f4), (double) ((v + (float) h) * f5));
        tessellator.addVertexWithUV((double) (x + w), (double) y, 0.0D, (double) ((u + (float) w) * f4), (double) (v * f5));
        tessellator.addVertexWithUV((double) x, (double) y, 0.0D, (double) (u * f4), (double) (v * f5));
        tessellator.draw();
    }

    public void drawHoveringText(List list, int x, int y)
    {
        if (!list.isEmpty()) {
            int w = this.width;
            int h = this.height;
            x -= xOffset;
            y -= yOffset;

            FontRenderer font = mc.fontRenderer;
            RenderItem itemRender = new RenderItem();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                int l = font.getStringWidth(s);

                if (l > k) {
                    k = l;
                }
            }

            int j2 = x + 12;
            int k2 = y - 12;
            int i1 = 8;

            if (list.size() > 1) {
                i1 += 2 + (list.size() - 1) * 10;
            }

            if (j2 + k > w) {
                j2 -= 28 + k;
            }

            if (k2 + i1 + 6 > h) {
                k2 = h - i1 - 6;
            }

            zLevel = 300.0F;
            itemRender.zLevel = 300.0F;
            int j1 = -267386864;
            drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < list.size(); ++i2) {
                String s1 = (String) list.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);

                if (i2 == 0) {
                    k2 += 2;
                }

                k2 += 10;
            }

            zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }

    public void drawEntity(int x, int y, int scale, EntityLivingBase entity)
    {
        x += xOffset;
        y += yOffset;
        float yaw = x - this.mouseX;
        float pitch = y - this.mouseY;
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, 50.0F);
        GL11.glScalef((float) (-scale), (float) scale, (float) scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan((double) (pitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float) Math.atan((double) (yaw / 40.0F)) * 20.0F;
        entity.rotationYaw = (float) Math.atan((double) (yaw / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float) Math.atan((double) (pitch / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

}
