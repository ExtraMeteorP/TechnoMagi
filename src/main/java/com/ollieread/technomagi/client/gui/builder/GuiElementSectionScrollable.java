package com.ollieread.technomagi.client.gui.builder;

import org.lwjgl.opengl.GL11;

public class GuiElementSectionScrollable extends GuiElementSection
{

    protected int innerHeight;
    public int scrollOffset = 0;

    public GuiElementSectionScrollable(String name, IGuiElement parent, int x, int y, int w, int h)
    {
        super(name, parent, x, y, w, h, true);
    }

    protected int getInnerHeight()
    {
        int ih = 0;

        for (IGuiElement element : elements.values()) {
            int mh = element.getMaxY();

            if (mh > ih) {
                ih = mh;
            }
        }

        return ih;
    }

    public int scrollUp()
    {
        scrollOffset -= 15;

        if (scrollOffset < 0) {
            scrollOffset = 0;
        }

        GuiBuilder.instance.setScroll(getName(), scrollOffset);

        return scrollOffset;
    }

    public int scrollDown()
    {
        scrollOffset += 15;

        if (scrollOffset > (innerHeight - (height - 6))) {
            scrollOffset = (innerHeight - (height - 6));
        }

        GuiBuilder.instance.setScroll(getName(), scrollOffset);

        return scrollOffset;
    }

    @Override
    public void drawBackground(int xPadding, int yPadding)
    {
        this.innerHeight = getInnerHeight();

        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            GuiBuilder.instance.drawSectionBackground(x, y, width, height);
            x += 3;
            y += 3;
        }

        if (innerHeight > (height - 9)) {
            GuiElementButtonDirectional buttonDown = new GuiElementButtonDirectional("buttonScrollableSectionDown", this, width - 17, height - 15, 0);
            GuiElementButtonDirectional buttonUp = new GuiElementButtonDirectional("buttonScrollableSectionUp", this, width - 17, 1, 1);

            if (scrollOffset == 0) {
                buttonUp.setInvisible();
            } else if (scrollOffset == (innerHeight - (height - 6))) {
                buttonDown.setInvisible();
            }

            this.addElement(buttonDown);
            this.addElement(buttonUp);

            buttonDown.drawBackground(x, y);
            buttonUp.drawBackground(x, y);

            this.width -= 17;

            int scale = GuiBuilder.instance.scaled.getScaleFactor();
            int sx = (GuiBuilder.instance.xOffset + x) * scale;
            int sw = (width - 6) * scale;
            int sy = GuiBuilder.instance.mc.displayHeight - (((GuiBuilder.instance.yOffset + y) + (height - 6)) * scale);
            int sh = (height - 6) * scale;
            GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(sx, sy, sw, sh);
        }

        for (IGuiElement element : elements.values()) {
            if (!element.getName().equals("buttonScrollableSectionDown") && !element.getName().equals("buttonScrollableSectionUp")) {
                element.drawBackground(x, y - scrollOffset);
            }
        }
    }

    @Override
    public void draw(int xPadding, int yPadding)
    {
        super.draw(xPadding, yPadding - scrollOffset);

        if (innerHeight > (height - 6)) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopAttrib();
        }
    }

}
