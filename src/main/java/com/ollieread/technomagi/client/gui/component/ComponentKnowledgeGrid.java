package com.ollieread.technomagi.client.gui.component;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.util.ResourceHelper;

public class ComponentKnowledgeGrid extends ComponentCollection
{

    protected float sensitivity = 0.25F;
    protected float angle = 16.5F;
    protected ResourceLocation background;
    protected int xo = 0;
    protected int yo = 0;
    protected int bx = 0;
    protected int by = 0;

    public ComponentKnowledgeGrid(int width, int height)
    {
        super(width, height);
    }

    public ComponentKnowledgeGrid setBackground(ResourceLocation background)
    {
        this.background = background;

        return this;
    }

    public void resetCoords()
    {
        this.xo = 0;
        this.yo = 0;
        this.bx = 0;
        this.by = 0;
    }

    @Override
    public void draw(int x, int y)
    {
        ScaledResolution scaled = new ScaledResolution(builder.mc, builder.mc.displayWidth, builder.mc.displayHeight);

        int scale = scaled.getScaleFactor();
        int w = getWidth();
        int h = getHeight();
        int sx = x * scale;
        int sw = w * scale;
        int sy = GuiBuilder.instance.mc.displayHeight - ((y + h) * scale);
        int sh = h * scale;

        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(sx, sy, sw, sh);

        int cx = x + (this.getWidth() / 2);
        int cy = y + (this.getHeight() / 2);

        // if (this.isInsideRegion(builder.instance.mouseX,
        // builder.instance.mouseY, x, y, x + this.getWidth(), y +
        // this.getHeight())) {
        xo = (cx - builder.instance.mouseX) / 2;
        yo = (cy - builder.instance.mouseY) / 2;
        bx = (cx - builder.instance.mouseX) / 3;
        by = (cy - builder.instance.mouseY) / 3;
        // }
        if (xo > 75) {
            xo = 75;
        } else if (xo < -75) {
            xo = -75;
        }

        if (yo > 75) {
            yo = 75;
        } else if (yo < -75) {
            yo = -75;
        }

        if (background != null) {
            builder.bindTexture(background);
            if (bx > 50) {
                bx = 50;
            } else if (bx < -50) {
                bx = -50;
            }

            if (by > 50) {
                by = 50;
            } else if (by < -50) {
                by = -50;
            }
            builder.drawImage(x + bx - 50, y + by - 50, 0, 0, 350, 350, 350, 350);
        }

        x += xo;
        y += yo;
        cx += xo;
        cy += yo;

        for (Component component : components.values()) {
            component.draw(cx - 15, cy - 13);
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopAttrib();
        builder.bindTexture(ResourceHelper.texture("gui/knowledge_overlay.png"));
        builder.drawImage(x - 1 - xo, y - 1 - yo, 0, 0, 245, 210, 245, 210);
    }

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        x += offsetX;
        y += offsetY;

        int cx = x + (this.getWidth() / 2);
        int cy = y + (this.getHeight() / 2);
        int xo = (cx - builder.instance.mouseX) / 2;
        int yo = (cy - builder.instance.mouseY) / 2;
        x += xo;
        y += yo;
        cx += xo;
        cy += yo;

        for (Component component : components.values()) {
            component.mouseHovered(cx - 15, cy - 13, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        x += offsetX;
        y += offsetY;

        int cx = x + (this.getWidth() / 2);
        int cy = y + (this.getHeight() / 2);
        int xo = (cx - builder.instance.mouseX) / 2;
        int yo = (cy - builder.instance.mouseY) / 2;
        x += xo;
        y += yo;
        cx += xo;
        cy += yo;

        for (Component component : components.values()) {
            if (component.mouseClicked(cx - 15, cy - 13, mouseX, mouseY, clickedButton)) {
                return true;
            }
        }

        return false;
    }

}
