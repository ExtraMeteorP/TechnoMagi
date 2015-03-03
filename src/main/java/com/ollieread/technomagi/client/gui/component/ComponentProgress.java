package com.ollieread.technomagi.client.gui.component;

import com.ollieread.technomagi.client.gui.GuiBuilder.ProgressType;

public class ComponentProgress extends Component
{

    protected ComponentOrientation orientation;
    protected ProgressType type;
    protected int percentage;

    public ComponentProgress(int length, ComponentOrientation orientation, ProgressType type, int percentage)
    {
        super(0, 0);

        this.orientation = orientation;
        this.type = type;
        this.percentage = percentage;

        if (orientation.equals(ComponentOrientation.VERTICAL)) {
            this.height = length;
            this.width = 5;
        } else {
            this.height = 5;
            this.width = length;
        }
    }

    @Override
    public void draw(int x, int y)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        builder.bindTexture();
        if (orientation.equals(ComponentOrientation.VERTICAL)) {
            builder.drawVerticalProgressBarBackground(x, y, this.getHeight());
            builder.drawVerticalProgressBarForeground(x, y, this.getHeight(), this.type, this.percentage);
        } else if (orientation.equals(ComponentOrientation.HORIZONTAL)) {
            builder.drawHorizontalProgressBarBackground(x, y, this.getWidth());
            builder.drawHorizontalProgressBarForeground(x, y, this.getWidth(), this.type, this.percentage);
        }
    }

    public ComponentProgress setPercentage(int percentage)
    {
        this.percentage = percentage;
        return this;
    }

}
