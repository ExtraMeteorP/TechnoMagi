package com.ollieread.technomagi.client.gui.content.element;

import org.apache.commons.lang3.StringUtils;

public class ElementParagraph extends Element
{

    public String text;

    public ElementParagraph(String name, String text)
    {
        super(name);
        this.text = StringUtils.trimToEmpty(text).replaceAll("[\u0000-\u001f]", "");
    }

}
