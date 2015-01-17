package com.ollieread.technomagi.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.Reference;

public class GuiBuilder extends Gui
{
    public static ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/widgets.png");
    public static int width;
    public static int height;
    public static int xOffset;
    public static int yOffset;
    public static Minecraft mc = Minecraft.getMinecraft();

    public void configure(int width, int height, int xOffset, int yOffset)
    {
        GuiBuilder.width = width;
        GuiBuilder.height = height;
        GuiBuilder.xOffset = xOffset;
        GuiBuilder.yOffset = yOffset;
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
        drawString(fontrenderer, heading, xOffset + 6, yOffset + 3 + (h - 8) / 2, 16777215);
    }

    public void drawProgressBar(int x, int y, int h, int t, int p)
    {
        int w = 5;
        mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xOffset + x, yOffset + y, 176, 14, w, h / 2);
        drawTexturedModalRect(xOffset + x, yOffset + y + (h / 2), 176, 116 - (h / 2), w, h / 2);

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

}
