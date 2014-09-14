package com.ollieread.technomagi.client.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;

public class GuiScrollableText extends GuiScreen
{
    protected int offsetX;
    protected int offsetY;
    protected int offsetStringX;
    protected int offsetStringY;
    protected int width;
    protected int height;
    protected int innerHeight;
    protected int innerWidth;
    protected int paddingX;
    protected int paddingY;
    protected String text;
    protected int guiScale;
    protected static int scrollY = 0;

    protected GuiScrollButton buttonDown;
    protected GuiScrollButton buttonUp;

    public GuiScrollableText(int x, int y, int sx, int sy, int w, int h, int px, int py, String t, List buttons, int ui, int di, FontRenderer fontRenderer)
    {
        mc = Minecraft.getMinecraft();
        fontRendererObj = fontRenderer;
        offsetStringX = sx;
        offsetStringY = sy;
        offsetX = x;
        offsetY = y;
        width = w;
        height = h;
        paddingX = px;
        paddingY = py;
        text = t;

        buttons.add(buttonDown = new GuiScrollButton(di, offsetStringX + width - 15, offsetStringY + height - 9, 11, 7, 0));
        buttons.add(buttonUp = new GuiScrollButton(ui, offsetStringX + width - 15, offsetStringY, 11, 7, 1));
    }

    public void setString(String t)
    {
        text = t;

        init();
    }

    private void init()
    {
        innerWidth = width - (paddingX * 2);
        innerHeight = calcInnerHeight();
        guiScale = 1;
        int scale = mc.gameSettings.guiScale;

        if (scale == 0) {
            scale = 1000;
        }

        while (guiScale < scale && mc.displayWidth / (guiScale + 1) >= 320 && mc.displayHeight / (guiScale + 1) >= 240) {
            guiScale++;
        }

        if (innerHeight > height) {
            innerWidth -= 19;
        }

        if (scrollY > 0) {
            buttonUp.enabled = true;
            buttonUp.visible = true;
        } else {
            buttonUp.enabled = false;
            buttonUp.visible = false;
        }

        if (scrollY < (innerHeight - height)) {
            buttonDown.enabled = true;
            buttonDown.visible = true;
        } else {
            buttonDown.enabled = false;
            buttonDown.visible = false;
        }
    }

    private String trimStringNewline(String string)
    {
        while (string != null && string.endsWith("\n")) {
            string = string.substring(0, string.length() - 1);
        }

        return string;
    }

    private String bidiReorder(String s)
    {
        try {
            Bidi bidi = new Bidi((new ArabicShaping(8)).shape(s), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (ArabicShapingException arabicshapingexception) {
            return s;
        }
    }

    protected int calcInnerHeight()
    {
        String s = trimStringNewline(text);
        List l = fontRendererObj.listFormattedStringToWidth(s, innerWidth);
        int h = paddingY * 2;

        for (Iterator<String> i = l.iterator(); i.hasNext();) {
            String s1 = (String) i.next();

            if (s1.indexOf("\n\n") > -1) {
                h += (fontRendererObj.FONT_HEIGHT * 2);
            }

            h += fontRendererObj.FONT_HEIGHT;
        }

        return h + fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int x = offsetX * guiScale;
        int y = offsetY * guiScale;
        int w = width * guiScale;
        int h = height * guiScale;
        GL11.glScissor(x, y, w, h);

        renderSplitText();

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopAttrib();

        super.drawScreen(par1, par2, par3);

    }

    protected void renderSplitText()
    {
        String s = trimStringNewline(text);
        List l = fontRendererObj.listFormattedStringToWidth(s, innerWidth);
        String s1 = "";
        int y = offsetStringY;

        for (Iterator iterator = l.iterator(); iterator.hasNext(); y += fontRendererObj.FONT_HEIGHT) {
            if ((y - scrollY) > (offsetStringY + paddingY + height)) {
                break;
            } else {
                if (s1.isEmpty()) {
                    s1 = (String) iterator.next();
                } else {
                    s1 += "\n" + (String) iterator.next();
                }
            }
        }

        fontRendererObj.drawSplitString(s1, offsetStringX + paddingX, offsetStringY + paddingY - scrollY, innerWidth, 5097727);
    }

    public void scrollDown(int i)
    {
        if (scrollY < (innerHeight - height)) {
            scrollY += i;
        }
    }

    public void scrollUp(int i)
    {
        if (scrollY > 0) {
            if ((scrollY - i) < 0) {
                scrollY = 0;
            } else {
                scrollY -= i;
            }
        }
    }

    public void setFontRenderer(FontRenderer fontRenderer)
    {
        fontRendererObj = fontRenderer;
    }

}
