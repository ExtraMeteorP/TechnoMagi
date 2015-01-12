package com.ollieread.technomagi.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ollieread.technomagi.TechnoMagi;

public class Information
{

    private static Map<String, Map<String, List<String>>> data = new LinkedHashMap<String, Map<String, List<String>>>();
    private static Gson gson = new Gson();

    public static void load(String name)
    {
        load(Reference.MODID.toLowerCase(), name);
    }

    public static void load(String modid, String name)
    {
        try {
            String location = "/assets/" + modid + "/information/" + name + ".json";
            Type jsonType = new TypeToken<Map<String, List<String>>>()
            {
            }.getType();
            InputStream resource = TechnoMagi.class.getResourceAsStream(location);
            Map<String, List<String>> json = gson.fromJson(IOUtils.toString(resource), jsonType);

            if (data.containsKey(name)) {
                data.get(name).putAll(json);
            } else {
                data.put(name, json);
            }
        } catch (IOException e) {
            TechnoMagi.logger.warn("Unable to load information for: " + name);
            e.printStackTrace();
        } catch (Exception e) {
            TechnoMagi.logger.warn("Unable to load information for: " + name);
            e.printStackTrace();
        }
    }

    public static Map<String, List<String>> getInformation(String type)
    {
        if (data.containsKey(type)) {
            return data.get(type);
        }

        return new LinkedHashMap<String, List<String>>();
    }

    public static String[] getInformation(String type, String name)
    {
        if (data.containsKey(type)) {
            Map<String, List<String>> information = data.get(type);

            if (information.containsKey(name)) {
                Object info = information.get(name);

                if (info instanceof String) {
                    return new String[] { (String) info };
                } else if (info instanceof List<?>) {
                    List<String> list = (List<String>) info;

                    String[] infoList = new String[list.size()];
                    int i = 0;

                    for (Iterator<String> p = list.iterator(); p.hasNext();) {
                        String item = p.next();

                        if (item != null && !item.isEmpty()) {
                            infoList[i] = item;
                        }

                        i++;
                    }

                    return infoList;
                }
            }
        }

        return new String[] {};
    }

    public static String getInformationParagraphs(String type, String name)
    {
        if (data.containsKey(type)) {
            Map<String, List<String>> information = data.get(type);

            if (information.containsKey(name)) {
                Object info = information.get(name);

                if (info instanceof String) {
                    return (String) info;
                } else if (info instanceof List<?>) {
                    List<String> list = (List<String>) info;

                    String paragraph = null;

                    for (Iterator<String> p = list.iterator(); p.hasNext();) {
                        if (paragraph == null) {
                            paragraph = p.next();
                        } else {
                            paragraph += "\n\n" + p.next();
                        }
                    }

                    return paragraph;
                }
            }
        }

        return "";
    }
}
