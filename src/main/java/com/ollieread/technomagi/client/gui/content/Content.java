package com.ollieread.technomagi.client.gui.content;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Content
{

    protected Map<String, Section> sections = new LinkedHashMap<String, Section>();

    public void addSection(Section section)
    {
        sections.put(section.getName(), section);
    }

    public Section getSection(String name)
    {
        if (sections.containsKey(name)) {
            return sections.get(name);
        }

        return null;
    }

    public Collection<Section> getSections()
    {
        return sections.values();
    }

}
