package com.ollieread.technomagi.client.gui.elements;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class GuiElementSection extends GuiElement
{

    public boolean background;
    protected Map<String, IGuiElement> elements = new HashMap<String, IGuiElement>();
    protected Map<String, IGuiElement> controls = new HashMap<String, IGuiElement>();
    protected boolean clickable = false;
    protected boolean horizontalScroll = false;
    protected boolean verticalScroll = false;
    public int horiztonalOffset = 0;
    public int verticalOffset = 0;

    public GuiElementSection(String name, IGuiElement parent, int x, int y, int w, int h)
    {
        this(name, parent, x, y, w, h, false);
    }

    public GuiElementSection(String name, IGuiElement parent, int x, int y, int w, int h, boolean background)
    {
        this.name = name;
        this.parent = parent;
        this.xOffset = x;
        this.yOffset = y;
        this.width = w;
        this.height = h;
        this.background = background;
    }

    public GuiElementSection setClickable()
    {
        clickable = true;

        return this;
    }

    public GuiElementSection setScrollHorizontal(boolean s)
    {
        this.horizontalScroll = s;

        return this;
    }

    public GuiElementSection setScrollVertical(boolean s)
    {
        this.verticalScroll = s;

        if (this.verticalScroll) {
            int w = width - (background ? 6 : 0);
            int h = height - (background ? 6 : 0);

            if (getInnerHeight() > h) {
                w -= 17;

                for (IGuiElement element : elements.values()) {
                    if (element.getWidth() > w) {
                        element.setWidth(w);
                    } else if (element.getMaxX() > w) {
                        element.setWidth(w - element.getMinX());
                    }
                }

                controls.put("down", new GuiElementButtonDirectional("down", this, this.width - (background ? 17 : 11), this.height - 10 - (background ? 3 : 0), 0));
                controls.put("up", new GuiElementButtonDirectional("up", this, this.width - (background ? 17 : 11), (background ? 6 : 3), 1));
            }
        }

        return this;
    }

    public int getInnerHeight()
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

    public int getInnerWidth()
    {
        int ih = 0;

        for (IGuiElement element : elements.values()) {
            int mx = element.getMaxX();

            if (mx > ih) {
                ih = mx;
            }
        }

        return ih;
    }

    public GuiElementSection addElement(IGuiElement element)
    {
        elements.put(element.getName(), element);

        return this;
    }

    @Override
    public void drawBackground(GuiBuilder builder, int xPadding, int yPadding)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            builder.drawSectionBackground(x, y, width, height);
            x += 3;
            y += 3;
        }

        if (this.verticalScroll) {
            int h = height - (background ? 6 : 0);
            int ih = getInnerHeight();

            if (ih > h) {
                if (verticalOffset == (ih - h)) {
                    ((GuiElementButtonDirectional) controls.get("down")).setInvisible();
                } else {
                    controls.get("down").drawBackground(builder, x, y);
                }

                if (verticalOffset == 0) {
                    ((GuiElementButtonDirectional) controls.get("up")).setInvisible();
                } else {
                    controls.get("up").drawBackground(builder, x, y);
                }

                int w = width - (background ? 17 : 11);
                int scale = builder.scaled.getScaleFactor();
                int sx = (builder.xOffset + x) * scale;
                int sw = w * scale;
                int sy = builder.mc.displayHeight - (((builder.yOffset + y) + h) * scale);
                int sh = h * scale;
                GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                GL11.glScissor(sx, sy, sw, sh);

                y -= verticalOffset;
            }
        }

        for (IGuiElement element : elements.values()) {
            element.drawBackground(builder, x, y);
        }
    }

    @Override
    public void draw(GuiBuilder builder, int xPadding, int yPadding)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            x += 3;
            y += 3;
        }

        if (this.verticalScroll) {
            int h = height - (background ? 6 : 0);
            int ih = getInnerHeight();

            if (ih > h) {
                y -= verticalOffset;
            }
        }

        for (IGuiElement element : elements.values()) {
            element.draw(builder, x, y);
        }

        if (this.verticalScroll) {
            int h = height - 6;
            int ih = getInnerHeight();

            if (ih > h) {
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                GL11.glPopAttrib();
            }
        }
    }

    @Override
    public boolean hover(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            x += 3;
            y += 3;
        }

        for (IGuiElement element : elements.values()) {
            if (element.hover(builder, x, y, xPosition, yPosition)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public IGuiElement clicked(GuiBuilder builder, int xPadding, int yPadding, int xPosition, int yPosition)
    {
        int x = xOffset + xPadding;
        int y = yOffset + yPadding;

        if (background) {
            x += 3;
            y += 3;
        }

        for (IGuiElement control : controls.values()) {
            IGuiElement link = control.clicked(builder, x - (background ? 3 : 0), y - (background ? 3 : 0), xPosition, yPosition);

            if (link != null) {
                return link;
            }
        }

        for (IGuiElement element : elements.values()) {
            IGuiElement link = element.clicked(builder, x - (background ? 3 : 0), y - (background ? 3 : 0), xPosition, yPosition);

            if (link != null) {
                return link;
            }
        }

        return null;
    }

}
