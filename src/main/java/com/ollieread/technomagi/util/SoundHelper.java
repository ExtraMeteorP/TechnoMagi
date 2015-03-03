package com.ollieread.technomagi.util;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.Technomagi;

public class SoundHelper
{

    public static void playSoundEffectAtPlayer(EntityPlayer player, String sound, Random rand)
    {
        player.worldObj.playSoundEffect(player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, Technomagi.MODID.toLowerCase() + ":" + sound, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
    }

}
