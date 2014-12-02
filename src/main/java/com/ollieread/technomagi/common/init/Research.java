package com.ollieread.technomagi.common.init;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ollieread.ennds.EnndsRegistry;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;

public class Research
{

    public static Map<String, List<String>> json = new HashMap<String, List<String>>();
    public static Map<ItemStack, String> itemToResearchMapping = new HashMap<ItemStack, String>();
    private static Gson gson = new Gson();

    public static void init()
    {
        try {
            String location = "/assets/" + Reference.MODID.toLowerCase() + "/information/research.json";
            Type jsonType = new TypeToken<Map<String, List<String>>>()
            {
            }.getType();
            InputStream resource = TechnoMagi.class.getResourceAsStream(location);
            json = gson.fromJson(IOUtils.toString(resource), jsonType);

            for (Iterator<List<String>> i = json.values().iterator(); i.hasNext();) {
                List<String> e = i.next();

                if (e != null && e.size() > 0) {
                    for (int x = 0; x < e.size(); x++) {
                        if (e.get(x) != null && !e.get(x).isEmpty()) {
                            EnndsRegistry.registerEvent(e.get(x));
                        }
                    }
                }
            }
        } catch (IOException e) {
            TechnoMagi.logger.warn("Unable to load research");
            e.printStackTrace();
        } catch (Exception e) {
            TechnoMagi.logger.warn("Unable to load research");
            e.printStackTrace();
        }

        itemToResearchMapping.put(new ItemStack(Items.cooked_porkchop), "eatPorkchop");
        itemToResearchMapping.put(new ItemStack(Items.cooked_beef), "eatBeef");
        itemToResearchMapping.put(new ItemStack(Items.cooked_chicken), "eatChicken");
        itemToResearchMapping.put(new ItemStack(Items.cooked_fished), "eatFish");
        itemToResearchMapping.put(new ItemStack(Items.apple), "eatApple");
        itemToResearchMapping.put(new ItemStack(Items.golden_apple), "eatGoldenApple");
        itemToResearchMapping.put(new ItemStack(Items.golden_carrot), "eatGoldenCarrot");
        itemToResearchMapping.put(new ItemStack(Items.ender_pearl), "useEnderpearl");
        itemToResearchMapping.put(new ItemStack(Items.egg), "useEgg");
        itemToResearchMapping.put(new ItemStack(Items.snowball), "useSnowball");
    }
}
