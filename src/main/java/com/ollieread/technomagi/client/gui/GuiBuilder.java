package com.ollieread.technomagi.client.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.client.gui.component.Component.ComponentOrientation;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;

public class GuiBuilder extends Gui
{

    public static ResourceLocation texture = new ResourceLocation(Technomagi.MODID.toLowerCase(), "textures/gui/widgets.png");
    public static GuiBuilder instance = new GuiBuilder();

    public Window currentWindow;
    public Gui currentGui;
    public final Minecraft mc = Minecraft.getMinecraft();
    public int mouseX;
    public int mouseY;
    public float partialTicks;
    protected static RenderItem itemRender = new RenderItem();

    public GuiBuilder()
    {

    }

    public void setWindow(Window window)
    {
        this.currentWindow = window;
    }

    public void setGui(Gui gui)
    {
        this.currentGui = gui;
    }

    public void bindTexture()
    {
        bindTexture(texture);
    }

    public void bindTexture(ResourceLocation texture)
    {
        mc.getTextureManager().bindTexture(texture);
    }

    public int drawString(String string, int x, int y, int c)
    {
        return drawString(string, x, y, c, mc.fontRenderer);
    }

    public int drawString(String string, int x, int y, int c, FontRenderer font)
    {
        return font.drawString(string, x, y, c);
    }

    public void drawBackground(int x, int y, int width, int height)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(3, 17, 34, 255);
        tessellator.addVertex(x, (double) y + height, 0.0D);
        tessellator.addVertex((double) x + width, (double) y + height, 0.0D);
        tessellator.addVertex((double) x + width, y, 0.0D);
        tessellator.addVertex(x, y, 0.0D);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawItemStack(ItemStack stack, int x, int y, String tooltip)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1 / 1.0F, short2 / 1.0F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        FontRenderer font = GuiBuilder.instance.mc.fontRenderer;

        if (stack != null) {
            if (stack.getItem().getFontRenderer(stack) != null) {
                font = stack.getItem().getFontRenderer(stack);
            }
        }

        itemRender.renderItemAndEffectIntoGUI(font, GuiBuilder.instance.mc.getTextureManager(), stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, GuiBuilder.instance.mc.getTextureManager(), stack, x, y, tooltip);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

    public void drawHeading(String heading, int x, int y, int width, int height)
    {
        int w = width - 6;
        int h = 18;
        FontRenderer fontrenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(x, y, 0, 144, w / 2, h);
        drawTexturedModalRect(x + (w / 2), y, 256 - w / 2, 144, w / 2, h);
        drawString(fontrenderer, heading, x + 6, y + (h - 8) / 2, 16777215);
    }

    public void drawVerticalProgressBarBackground(int x, int y, int h)
    {
        int w = 5;
        h += 2;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(x, y, 176, 14, w, h / 2);
        drawTexturedModalRect(x, y + (h / 2), 176, 116 - (h / 2), w, h / 2);
    }

    public void drawVerticalProgressBarForeground(int x, int y, int h, ProgressType t, int p)
    {
        int w = 5;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (p > 0) {
            int[] uv = t.getUV(ComponentOrientation.VERTICAL);
            drawTexturedModalRect(x + 1, y + 1, uv[0], uv[1], 3, p);
        }
    }

    public void drawHorizontalProgressBarBackground(int x, int y, int w)
    {
        int h = 5;
        w += 2;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(x, y, 0, 232, w / 2, h);
        drawTexturedModalRect(x + (w / 2), y, 102 - (w / 2), 232, w / 2, h);
    }

    public void drawHorizontalProgressBarForeground(int x, int y, int w, ProgressType t, int p)
    {
        int h = 5;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (p > 0) {
            int[] uv = t.getUV(ComponentOrientation.HORIZONTAL);
            drawTexturedModalRect(x + 1, y + 1, uv[0], uv[1], p, 3);
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
        drawTexturedModalRect(x, y, 0, yh, w / 2, 20);
        drawTexturedModalRect(x + (w / 2), y, 256 - w / 2, yh, w / 2, 20);
        int l = !hover ? 16777215 : 2529246;
        drawCenteredString(fontrenderer, text, x + (w / 2), y + (12) / 2, l);
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
        drawTexturedModalRect(x, y, xo, yo, w, h);
    }

    public void drawTab(int x, int y, TabLocation location, ResourceLocation image, boolean selected)
    {
        int w = 28;
        int h = 28;

        int[] uv = location.getUV(selected);

        bindTexture();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(x, y, uv[0], uv[1], w, h);
        bindTexture(image);
        drawImage(x + 6, y + 6, 0, 0, 16, 16, 16, 16);
        bindTexture();
    }

    public void drawSectionBackground(int x, int y, int w, int h)
    {
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(x, y, 0, 144, w / 2, 3);
        drawTexturedModalRect(x + (w / 2), y, 256 - w / 2, 144, w / 2, 3);

        drawStretchedRect(x, y + 3, 0, 147, w, h - 6);

        drawTexturedModalRect(x, y + h - 3, 0, 159, w / 2, 3);
        drawTexturedModalRect(x + (w / 2), y + h - 3, 256 - w / 2, 159, w / 2, 3);
    }

    public void drawTextureArea(int x, int y, int u, int v, int w, int h)
    {
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(x, y, u, v, w, h);
    }

    public void drawTextureArea(int x, int y, int u, int v, int w, int h, ResourceLocation texture)
    {
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(x, y, u, v, w, h);
    }

    public void drawStretchedRect(int x, int y, int u, int v, int w, int h)
    {
        drawStretchedRect(x, y, u, v, w, 1, w, h);
    }

    public void drawStretchedRect(int x, int y, int u, int v, int ow, int oh, int w, int h)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + h, zLevel, (u + 0) * f, (v + oh) * f1);
        tessellator.addVertexWithUV(x + w, y + h, zLevel, (u + ow) * f, (v + oh) * f1);
        tessellator.addVertexWithUV(x + w, y + 0, zLevel, (u + ow) * f, (v + 0) * f1);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, (u + 0) * f, (v + 0) * f1);
        tessellator.draw();
    }

    public void drawImage(int x, int y, float u, float v, int w, int h, float ow, float oh)
    {
        float f4 = 1.0F / ow;
        float f5 = 1.0F / oh;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + h, 0.0D, u * f4, (v + h) * f5);
        tessellator.addVertexWithUV(x + w, y + h, 0.0D, (u + w) * f4, (v + h) * f5);
        tessellator.addVertexWithUV(x + w, y, 0.0D, (u + w) * f4, v * f5);
        tessellator.addVertexWithUV(x, y, 0.0D, u * f4, v * f5);
        tessellator.draw();
    }

    public void drawHoveringText(List list, int x, int y)
    {
        if (!list.isEmpty()) {
            int w = mc.displayWidth;
            int h = mc.displayHeight;

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
        float yaw = x - this.mc.mouseHelper.deltaX;
        float pitch = y - this.mc.mouseHelper.deltaY;
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 50.0F);
        GL11.glScalef((-scale), scale, scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
        entity.rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
        entity.rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
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

    public static enum ProgressType {
        NANITES(181, 15, 0, 237),
        DATA(184, 15, 0, 240),
        ENERGY(187, 15, 0, 243);

        protected int veritcalU;
        protected int veritcalV;
        protected int horizontalU;
        protected int horizontalV;

        ProgressType(int vu, int vv, int hu, int hv)
        {
            this.veritcalU = vu;
            this.veritcalV = vv;
            this.horizontalU = hu;
            this.horizontalV = hv;
        }

        public int[] getUV(ComponentOrientation o)
        {
            if (o.equals(ComponentOrientation.HORIZONTAL)) {
                return new int[] { this.horizontalU, this.horizontalV };
            } else if (o.equals(ComponentOrientation.VERTICAL)) {
                return new int[] { this.veritcalU, this.veritcalV };
            }

            return new int[] { 0, 0 };
        }
    }

    public static enum TabLocation {
        LEFT(172, 202, 202, 202),
        RIGHT(114, 202, 144, 202),
        TOP(0, 202, 28, 202),
        BOTTOM(56, 204, 84, 204);

        protected int normalU;
        protected int normalV;
        protected int hoverU;
        protected int hoverV;

        TabLocation(int nu, int nv, int hu, int hv)
        {
            this.normalU = nu;
            this.normalV = nv;
            this.hoverU = hu;
            this.hoverV = hv;
        }

        public int[] getUV(boolean hover)
        {
            if (hover) {
                return new int[] { this.hoverU, this.hoverV };
            } else {
                return new int[] { this.normalU, this.normalV };
            }
        }
    }

}
