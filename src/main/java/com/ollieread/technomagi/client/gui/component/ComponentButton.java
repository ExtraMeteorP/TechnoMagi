package com.ollieread.technomagi.client.gui.component;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.client.gui.GuiBuilder;

public class ComponentButton extends Component
{

    public static enum ButtonType {
        DEFAULT, TEXT, IMAGE, SCROLL_UP, SCROLL_DOWN, SCROLL_LEFT, SCROLL_RIGHT
    }

    protected ButtonType type;
    protected String text;
    protected ResourceLocation image;
    protected boolean hover = false;
    protected boolean active = false;
    protected boolean enabled = false;

    public ComponentButton(ButtonType type)
    {
        this(0, type);

        if (type.equals(ButtonType.SCROLL_UP) || type.equals(ButtonType.SCROLL_DOWN)) {
            this.width = 11;
            this.height = 7;
        } else if (type.equals(ButtonType.SCROLL_LEFT) || type.equals(ButtonType.SCROLL_RIGHT)) {
            this.width = 7;
            this.height = 11;
        }
    }

    public ComponentButton(int width, ButtonType type)
    {
        super(width, 20);

        this.type = type;

        if (type.equals(ButtonType.TEXT)) {
            this.height = 8;
        }
    }

    public ComponentButton setText(String text)
    {
        this.text = text;
        return this;
    }

    public ComponentButton setImage(ResourceLocation image)
    {
        this.image = image;

        return this;
    }

    public ComponentButton setHover(boolean hover)
    {
        this.hover = hover;
        return this;
    }

    public ComponentButton setActive(boolean active)
    {
        this.active = active;
        return this;
    }

    public ComponentButton setEnabled(boolean enabled)
    {
        this.enabled = enabled;
        return this;
    }

    @Override
    public boolean isClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        return enabled && isInsideRegion(mouseX, mouseY, x, y, x + getWidth(), y + getHeight());
    }

    @Override
    public void draw(int x, int y)
    {
        x += getOffsetX();
        y += getOffsetY();
        boolean flag = active || hover;

        if (type.equals(ButtonType.DEFAULT)) {
            builder.bindTexture();

            if (alignment != null && alignment.equals(ComponentAlignment.CENTER)) {
                x += (parent.getWidth() - getWidth()) / 2;
            }

            builder.drawButton(text, x, y, getWidth(), hover);
        } else if (type.equals(ButtonType.IMAGE)) {
            builder.bindTexture(image);
            builder.drawImage(x, y, 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        } else if (type.equals(ButtonType.TEXT)) {
            builder.drawString(text, x, y, !flag ? 16777215 : 2529246);
        } else {
            builder.bindTexture();

            if (type.equals(ButtonType.SCROLL_UP)) {
                builder.drawTextureArea(x, y, 176, 7, getWidth(), getHeight());
            } else if (type.equals(ButtonType.SCROLL_DOWN)) {
                builder.drawTextureArea(x, y, 176, 0, getWidth(), getHeight());
            } else if (type.equals(ButtonType.SCROLL_LEFT)) {
                builder.drawTextureArea(x, y, 194, 0, getWidth(), getHeight());
            } else if (type.equals(ButtonType.SCROLL_RIGHT)) {
                builder.drawTextureArea(x, y, 186, 0, getWidth(), getHeight());
            }
        }
    }

    public ButtonType getType()
    {
        return this.type;
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

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        if (enabled) {
            if (isHovered(x, y, mouseX, mouseY)) {
                setHover(true);
                if (tooltip != null) {
                    GuiBuilder.instance.drawHoveringText(tooltip, mouseX, mouseY);
                }
            } else {
                setHover(false);
            }
        }
    }

}
