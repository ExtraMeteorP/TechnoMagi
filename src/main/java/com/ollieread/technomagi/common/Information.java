package com.ollieread.technomagi.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ollieread.technomagi.TechnoMagi;

public class Information
{

    private static Map<String, Map<String, List<String>>> data = new HashMap<String, Map<String, List<String>>>();
    private static Gson gson = new Gson();

    public static void load(String name)
    {
        try {
            String location = "/assets/technomagi/information/" + name + ".json";
            Type jsonType = new TypeToken<Map<String, List<String>>>()
            {
            }.getType();
            Map<String, List<String>> json = gson.fromJson(new FileReader(location), jsonType);
            data.put(name, json);
        } catch (IOException e) {
            TechnoMagi.logger.warn("Unable to load information for: " + name);
        }
    }

    public static List<String> getInformation(String type, String name)
    {
        if (data.containsKey(type)) {
            Map<String, List<String>> information = data.get(type);

            if (information.containsKey(name)) {
                return information.get(name);
            }
        }

        return null;
    }

}
