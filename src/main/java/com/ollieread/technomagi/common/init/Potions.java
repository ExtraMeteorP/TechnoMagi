package com.ollieread.technomagi.common.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.potion.PotionTM;

public class Potions
{

    public static PotionTM potionHardness;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering potions");

        if (Potion.potionTypes.length < 256) {
            Potion[] potionTypes = null;

            for (Field f : Potion.class.getDeclaredFields()) {
                f.setAccessible(true);

                try {
                    if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                        Field modfield = Field.class.getDeclaredField("modifiers");
                        modfield.setAccessible(true);
                        modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                        potionTypes = (Potion[]) f.get(null);
                        final Potion[] newPotionType = new Potion[256];
                        System.arraycopy(potionTypes, 0, newPotionType, 0, potionTypes.length);
                        f.set(null, newPotionType);
                    }
                } catch (Exception e) {
                    System.err.println("Severe error, please report this to the mod author");
                    System.err.println(e);
                }
            }
        }

        // potionHardness = new PotionTM(30, "potionHardness", false, 0);
    }
}
