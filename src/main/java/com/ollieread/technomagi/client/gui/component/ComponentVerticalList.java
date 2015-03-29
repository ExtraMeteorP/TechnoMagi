package com.ollieread.technomagi.client.gui.component;

import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.component.ComponentButton.ButtonType;

public class ComponentVerticalList extends ComponentCollection
{
    protected boolean scrolling = false;
    protected int scrollOffset = 0;

    protected boolean background;
    public ComponentButton buttonUp = new ComponentButton(ButtonType.SCROLL_UP);
    public ComponentButton buttonDown = new ComponentButton(ButtonType.SCROLL_DOWN);

    public ComponentVerticalList(int width, int height)
    {
        this(width, height, false);
    }

    public ComponentVerticalList(int width, int height, boolean scrolling)
    {
        super(width, height);

        this.scrolling = scrolling;
        int x = (width - buttonUp.getWidth()) - (paddingX * 2) - 4;
        int y = (height - buttonUp.getHeight()) - (paddingY * 2) - 4;
        buttonUp.setEnabled(true).setOffset(x, (paddingY * 2) + 4).setParent(this);
        buttonDown.setEnabled(true).setActive(true).setOffset(x, y).setParent(this);
    }

    public ComponentVerticalList setBackground(boolean background)
    {
        this.background = background;
        return this;
    }

    @Override
    public Component setClickHandler(IClickHandler handler)
    {
        buttonUp.setClickHandler(handler);
        buttonDown.setClickHandler(handler);
        return this;
    }

    public void setScroll(int scroll)
    {
        this.scrollOffset = scroll;
        int i = getInnerHeight() - height;

        if (scrollOffset == 0) {
            buttonDown.setActive(true);
            buttonUp.setActive(false);
        }
    }

    public void scrollUp()
    {
        if (scrollOffset > 0) {
            if (scrollOffset <= 15) {
                scrollOffset = 0;
                buttonUp.setActive(false);
            } else {
                scrollOffset -= 15;
            }
        }

        int i = getInnerHeight() - height;

        if (scrollOffset < i) {
            buttonDown.setActive(true);
        }
    }

    public void scrollDown()
    {
        int i = getInnerHeight() - height;

        if (scrollOffset < i) {
            if (scrollOffset >= (i - 15)) {
                scrollOffset = i;
                buttonDown.setActive(false);
            } else {
                scrollOffset += 15;
            }
        }

        if (scrollOffset > 0) {
            buttonUp.setActive(true);
        }
    }

    @Override
    public int getWidth()
    {
        return scrolling ? width - 15 : width;
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

        int nx = x;
        int ny = y;

        if (scrolling) {
            ScaledResolution scaled = new ScaledResolution(builder.mc, builder.mc.displayWidth, builder.mc.displayHeight);
            int scale = scaled.getScaleFactor();
            int w = getWidth() - (paddingX * 2);
            int h = height - (paddingY * 2);
            int sx = (x + paddingX) * scale;
            int sw = w * scale;
            int sy = GuiBuilder.instance.mc.displayHeight - (((y + paddingY) + h) * scale);
            int sh = h * scale;
            GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(sx, sy, sw, sh);

            ny -= scrollOffset;
        }

        for (Component component : components.values()) {
            component.draw(nx + this.paddingX, ny + this.paddingY);

            ny += component.getHeight() + this.paddingY;
        }

        if (scrolling) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopAttrib();

            buttonUp.draw(x, y);
            buttonDown.draw(x, y);
        }
    }

    private int getInnerHeight()
    {
        int ih = paddingY;

        for (Component component : components.values()) {
            ih += paddingY + component.getHeight();
        }

        return ih + paddingY;
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        x += this.offsetX;
        y += this.offsetY;
        int cut = y;

        if (scrolling) {
            cut -= scrollOffset;
        }

        int nx = x;
        int ny = y + this.paddingY;

        for (Component component : components.values()) {
            if (ny >= cut) {
                if (component.mouseClicked(nx + this.paddingX, ny, mouseX, mouseY, clickedButton)) {
                    return true;
                }
            }

            ny += component.getHeight() + this.paddingY;
        }

        buttonUp.mouseClicked(x, y, mouseX, mouseY, clickedButton);
        buttonDown.mouseClicked(x, y, mouseX, mouseY, clickedButton);

        return false;
    }

    @Override
    public void mouseScrolled(int x, int y, int mouseX, int mouseY, int dwheel)
    {
        x += this.offsetX;
        y += this.offsetY;

        if (isHovered(x, y, mouseX, mouseY)) {
            scroll(dwheel);
        }
    }

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        x += this.offsetX;
        y += this.offsetY;
        int cut = y;

        if (scrolling) {
            cut -= scrollOffset;
        }

        int nx = x;
        int ny = y - scrollOffset + this.paddingY;

        for (Component component : components.values()) {
            if (ny >= cut) {
                component.mouseHovered(x + this.paddingX, ny, mouseX, mouseY);
            }

            ny += component.getHeight() + this.paddingY;
        }
    }

    @Override
    public void scroll(int dwheel)
    {
        if (dwheel < 0) {
            scrollDown();
        } else if (dwheel > 0) {
            scrollUp();
        }
    }

}
