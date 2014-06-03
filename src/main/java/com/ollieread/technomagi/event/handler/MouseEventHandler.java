package com.ollieread.technomagi.event.handler;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageAbility;
import com.ollieread.technomagi.network.message.MessageSyncKnowledge;
import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.MouseEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.server.FMLServerHandler;

public class MouseEventHandler {
	
	@SubscribeEvent
	public void onMouseEvent(MouseEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(player.isSneaking() && event.dwheel != 0) {
			PlayerKnowledge charon = PlayerKnowledge.get(player);

			if(event.dwheel > 0) {
				PacketHandler.INSTANCE.sendToServer(new MessageAbility(1));
			} else {
				PacketHandler.INSTANCE.sendToServer(new MessageAbility(2));
			}
			event.setCanceled(true);
		}
	}

}
