package com.ollieread.technomagi.event.handler;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.KeyBindings;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler
{

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeyBindings.tmGui.isPressed()) {
            Minecraft client = FMLClientHandler.instance().getClient();
            EntityPlayer player = client.thePlayer;
            PlayerKnowledge charon = PlayerKnowledge.get(player);

            int id = CommonProxy.GUI_TECHNOMAGI;

            player.openGui(TechnoMagi.instance, id, client.theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }

}
