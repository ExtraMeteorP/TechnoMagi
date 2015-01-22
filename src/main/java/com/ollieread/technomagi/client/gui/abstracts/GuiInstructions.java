package com.ollieread.technomagi.client.gui.abstracts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.IGuiInstructions;
import com.ollieread.technomagi.client.gui.elements.IGuiElement;
import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;

public abstract class GuiInstructions implements IGuiInstructions
{

    protected static Map<String, Integer> scroll = new HashMap<String, Integer>();
    protected static IGuiElement linkElement = null;
    protected static String linkName = "";
    protected static List<String> linkMetadata = new ArrayList<String>();

    @Override
    public void clicked(GuiBuilder builder, ContainerBuilder container, IGuiElement element)
    {
        if (element != null) {
            this.linkElement = element;

            String linkDirty = element.getLink();
            String[] linkParsed = StringUtils.split(linkDirty, ':');
            linkMetadata = new ArrayList<String>();

            if (linkParsed.length > 0) {
                linkName = linkParsed[0];

                if (linkParsed.length > 1) {
                    String[] metadata = new String[linkParsed.length - 1];
                    for (int i = 1; i <= metadata.length; i++) {
                        metadata[i - 1] = linkParsed[i];
                    }

                    linkMetadata = Arrays.asList(metadata);
                }
            }
        }
    }

    /**
     * Below are the helper methods
     */

    protected String getMetadata(int i)
    {
        for (String metadata : linkMetadata) {
            if (linkMetadata.indexOf(metadata) == i) {
                return metadata;
            }
        }

        return null;
    }

    protected void increaseScroll(String name, Integer amount)
    {
        int current = getScroll(name);

        setScroll(name, current + amount);
    }

    protected void decreaseScroll(String name, Integer amount)
    {
        int current = getScroll(name);

        setScroll(name, current - amount);
    }

    protected void setScroll(String name, Integer amount)
    {
        scroll.put(name, amount);
    }

    protected Integer getScroll(String name)
    {
        if (scroll.containsKey(name)) {
            return scroll.get(name);
        }

        return 0;
    }

}
