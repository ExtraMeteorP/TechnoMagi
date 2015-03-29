package com.ollieread.technomagi.client.gui.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.window.WindowKnowledge;

public class ComponentKnowledge extends Component
{

    protected boolean active;
    protected ResourceLocation image;
    protected boolean hover = false;
    protected boolean highlight = false;

    public ComponentKnowledge()
    {
        super(30, 26);
    }

    public boolean isActive()
    {
        return active;
    }

    public ComponentKnowledge setActive(boolean active)
    {
        this.active = active;
        return this;
    }

    public ComponentKnowledge setImage(ResourceLocation image)
    {
        this.image = image;

        return this;
    }

    public ComponentKnowledge setHover(boolean hover)
    {
        this.hover = hover;
        return this;
    }

    public ComponentKnowledge setHighlight(boolean highlight)
    {
        this.highlight = highlight;
        return this;
    }

    @Override
    public void draw(int x, int y)
    {
        x += offsetX;
        y += offsetY;

        boolean flag = highlight ? highlight : hover;

        if (flag) {
            if (active) {
                builder.drawTextureArea(x, y, 226, 26, getWidth(), getHeight());
            } else {
                builder.drawTextureArea(x, y, 226, 0, getWidth(), getHeight());
            }
            builder.drawTextureArea(x - 3, y - 3, 207, 62, 36, 32);
            builder.bindTexture(this.image);
            builder.drawImage(x + 7, y + 5, 0, 0, 16, 16, 16, 16);

            if (highlight) {
                this.setHighlight(false);
            }
        } else {
            if (active) {
                builder.drawTextureArea(x, y, 197, 37, 28, 24);
            } else {
                builder.drawTextureArea(x, y, 197, 11, 28, 24);
            }

            builder.bindTexture(this.image);
            builder.drawImage(x + 6, y + 4, 0, 0, 16, 16, 16, 16);
        }
    }

    @Override
    public void mouseHovered(int x, int y, int mouseX, int mouseY)
    {
        x += offsetX;
        y += offsetY;

        if (isHovered(x, y, mouseX, mouseY)) {
            setHover(true);
            if (tooltip != null) {
                GuiBuilder.instance.drawHoveringText(tooltip, mouseX, mouseY);
            }

            Knowledge knowledge = TechnomagiApi.getKnowledge(name);
            List<String> prerequisites = new ArrayList<String>();

            if (knowledge != null) {
                String prerequisite = knowledge.getPrerequisite();

                while (prerequisite != null) {
                    prerequisites.add(prerequisite);

                    knowledge = TechnomagiApi.getKnowledge(prerequisite);

                    if (knowledge != null) {
                        prerequisite = knowledge.getPrerequisite();
                    } else {
                        break;
                    }
                }

                if (prerequisites.size() > 0) {
                    if (builder.currentWindow instanceof WindowKnowledge) {
                        ((WindowKnowledge) builder.currentWindow).highlightPath(prerequisites);
                    }
                }
            }

        } else {
            setHover(false);
        }
    }

    @Override
    public boolean isClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        x += offsetX;
        y += offsetY;

        return active && isInsideRegion(mouseX, mouseY, x, y, x + getWidth(), y + getHeight());
    }

}
