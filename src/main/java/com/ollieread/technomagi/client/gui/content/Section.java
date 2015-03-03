package com.ollieread.technomagi.client.gui.content;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ollieread.technomagi.client.gui.content.element.Element;

public class Section
{

    public static enum ElementType {
        PARAGRAPH, IMAGE, RECIPE_VANILLA, RECIPE_TECHNOMAGI
    }

    protected String name;
    protected Map<Integer, List<Element>> pages = new LinkedHashMap<Integer, List<Element>>();

    public Section(String name, int pageCount)
    {
        this.name = name;

        for (int i = 0; i < pageCount; i++) {
            pages.put(Integer.valueOf(i), new LinkedList<Element>());
        }
    }

    public Section(String name, Map<Integer, List<Element>> pageMap)
    {
        this.name = name;
        this.pages = pageMap;
    }

    public String getName()
    {
        return name;
    }

    public List<Element> getElements(int page)
    {
        ArrayList<Element> elements = new ArrayList<Element>();

        if (pages.containsKey(page)) {
            for (Element element : pages.get(page)) {
                elements.add(element);
            }
        }

        return elements;
    }

}
