package com.ollieread.technomagi.client.gui.component;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.content.element.Element;
import com.ollieread.technomagi.client.gui.content.element.ElementParagraph;
import com.ollieread.technomagi.client.gui.content.element.ElementRecipe;

public class ComponentCollection extends Component
{

    protected Map<String, Component> components = new LinkedHashMap<String, Component>();

    public ComponentCollection(int width, int height)
    {
        super(width, height);
    }

    public void clearComponents()
    {
        components.clear();
    }

    public void removeComponent(String name)
    {
        if (components.containsKey(name)) {
            components.remove(name);
        }
    }

    public Component getComponent(String name)
    {
        if (components.containsKey(name)) {
            return components.get(name);
        }

        return null;
    }

    public Collection<Component> getComponents()
    {
        return components.values();
    }

    public Component addComponent(String name, Component component)
    {
        components.put(name, component);
        component.setName(name);
        component.setParent(this);

        return component;
    }

    public ComponentText addComponentText(String name, String text)
    {
        return (ComponentText) addComponent(name, new ComponentText(getWidth() - (paddingX * 2), GuiBuilder.instance.mc.fontRenderer).setContent(text));
    }

    public void addElements(List<Element> elements)
    {
        for (Element element : elements) {
            if (element instanceof ElementParagraph) {
                ElementParagraph paragraph = (ElementParagraph) element;
                addComponentText(element.name, paragraph.text).setPadding(paragraph.paddingX, paragraph.paddingY + 6).setOffset(paragraph.offsetX, paragraph.offsetY).setAlignment(paragraph.alignment).setParent(this);
            } else if (element instanceof ElementRecipe) {
                ElementRecipe recipe = (ElementRecipe) element;

                if (recipe.output != null) {
                    ComponentRecipe component = new ComponentRecipe(recipe.type);
                    component.setOutput(recipe.output);
                    addComponent(element.name, component).setPadding(recipe.paddingX, recipe.paddingY + 6).setOffset(recipe.offsetX, recipe.offsetY).setAlignment(recipe.alignment).setParent(this);
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int clickedButton)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        for (Component component : components.values()) {
            if (component.mouseClicked(x, y, mouseX, mouseY, clickedButton)) {
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

        for (Component component : components.values()) {
            component.mouseHovered(x, y, mouseX, mouseY);
        }
    }

    @Override
    public void mouseScrolled(int x, int y, int mouseX, int mouseY, int dwheel)
    {
        x += offsetX + paddingX;
        y += offsetY + paddingY;

        for (Component component : components.values()) {
            component.mouseScrolled(x, y, mouseX, mouseY, dwheel);
        }
    }

}
