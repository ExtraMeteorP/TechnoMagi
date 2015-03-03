package com.ollieread.technomagi.client.gui.component;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.client.gui.GuiBuilder.TabLocation;

public class ComponentTab extends Component
{

    protected TabLocation location;
    protected ResourceLocation image;
    protected boolean active = false;

    public ComponentTab(TabLocation location)
    {
        super(28, 28);

        this.location = location;
    }

    public ComponentTab setImage(ResourceLocation image)
    {
        this.image = image;

        return this;
    }

    public ComponentTab setActive(boolean active)
    {
        this.active = active;
        return this;
    }

    @Override
    public void draw(int x, int y)
    {
        x += getOffsetX();
        y += getOffsetY();

        builder.drawTab(x, y, this.location, image, this.active);
    }

    @Override
    public boolean isClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        return isInsideRegion(mouseX, mouseY, x, y, x + getWidth(), y + getHeight());
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        if (isClicked(x, y, mouseX, mouseY, clickedButton)) {
            if (getHandler() != null) {
                getHandler().onClick(this);
                return true;
            }
        }

        return false;
    }

}
