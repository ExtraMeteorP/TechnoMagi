package com.ollieread.technomagi.common.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class AchievementTechnomagi extends Achievement
{

    public AchievementTechnomagi(String name, int column, int row, ItemStack stack, Achievement achievement)
    {
        super("achievement.technomagi." + name, name, column, row, stack, achievement);
    }

}
