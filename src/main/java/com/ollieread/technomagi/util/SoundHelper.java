package com.ollieread.technomagi.util;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.common.Reference;

public class SoundHelper
{

    public static void playSoundEffectAtPlayer(EntityPlayer player, String sound, Random rand)
    {
        player.worldObj.playSoundEffect((double) player.posX + 0.5D, (double) player.posY + 0.5D, (double) player.posZ + 0.5D, Reference.MODID.toLowerCase() + ":" + sound, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
    }

}
