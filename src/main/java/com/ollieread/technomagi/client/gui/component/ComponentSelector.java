package com.ollieread.technomagi.client.gui.component;


public class ComponentSelector extends ComponentButton
{

    protected ComponentSize size;

    public ComponentSelector(ComponentSize size)
    {
        super(0, ButtonType.IMAGE);

        if (!size.equals(ComponentSize.LARGE)) {
            this.width = 18;
            this.height = 18;
        } else {
            this.width = 34;
            this.height = 34;
        }

        this.size = size;
    }

    @Override
    public void draw(int x, int y)
    {
        x += getOffsetX();
        y += getOffsetY();

        builder.bindTexture();
        builder.drawStretchedRect(x, y, 0, 110, 18, 18, width, height);
        builder.bindTexture(image);
        builder.drawImage(x + 1, y + 1, 0, 0, getWidth() - 2, getHeight() - 2, getWidth() - 2, getHeight() - 2);

        if (active || hover) {
            int u = 0;
            int v = 128;
            int w = 16;
            int h = 16;
            x += 1;
            y += 1;

            if (size.equals(ComponentSize.LARGE)) {
                u = 74;
                v = 104;
                w = 30;
                h = 30;
                x += 1;
                y += 1;
            }

            builder.bindTexture();
            builder.drawTextureArea(x, y, u, v, w, h);
        }
    }

    @Override
    public boolean isClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        return isInsideRegion(mouseX, mouseY, x, y, x + getWidth(), y + getHeight());
    }

}
