package com.ollieread.technomagi.client.gui.component;

import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class ComponentHorizontalList extends ComponentVerticalList
{
    protected boolean scrolling = false;
    protected int scrollOffset = 0;
    protected int innerWidth = 0;

    public ComponentHorizontalList(int width, int height)
    {
        super(width, height);
    }

    @Override
    public ComponentHorizontalList setBackground(boolean background)
    {
        this.background = background;
        return this;
    }

    @Override
    public void draw(int x, int y)
    {
        x += offsetX;
        y += offsetY;
        GuiBuilder builder = GuiBuilder.instance;

        if (background) {
            builder.drawSectionBackground(x, y, this.width, this.height);
        }

        if (scrolling) {
            ScaledResolution scaled = new ScaledResolution(builder.mc, builder.mc.displayWidth, builder.mc.displayHeight);
            int scale = scaled.getScaleFactor();
            int w = width - 11 + (paddingX * 2);
            int h = height + (paddingY * 2);
            int sx = (x + paddingX) * scale;
            int sw = w * scale;
            int sy = GuiBuilder.instance.mc.displayHeight - (((y + paddingY) + h) * scale);
            int sh = h * scale;
            GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(sx, sy, sw, sh);

            x -= scrollOffset;
        }

        for (Component component : components.values()) {
            component.draw(x + this.paddingX, y + this.paddingY);

            x += component.getWidth() + this.paddingX;
        }

        if (scrolling) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopAttrib();
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        x += offsetX + this.paddingX;
        y += offsetY;
        int cut = x;

        if (scrolling) {
            cut -= scrollOffset;
        }

        for (Component component : components.values()) {
            if (x >= cut) {
                if (component.mouseClicked(x, y + this.paddingY, mouseX, mouseY, clickedButton)) {
                    return true;
                }
            }

            x += component.getWidth() + this.paddingX;
        }

        return false;
    }

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        x += offsetX + this.paddingX;
        y += offsetY;
        int cut = x;

        if (scrolling) {
            cut -= scrollOffset;
        }

        for (Component component : components.values()) {
            if (x >= cut) {
                component.mouseHovered(x, y + this.paddingY, mouseX, mouseY);
            }

            x += component.getWidth() + this.paddingX;
        }
    }

}
