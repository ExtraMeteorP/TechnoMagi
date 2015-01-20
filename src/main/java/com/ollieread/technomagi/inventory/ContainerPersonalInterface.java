package com.ollieread.technomagi.inventory;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.StringUtils;

import scala.actors.threadpool.Arrays;

import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;
import com.ollieread.technomagi.item.ItemPersonalInterface;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSetPersonalInterfaceLink;
import com.ollieread.technomagi.network.message.MessageSetPersonalInterfaceSyncing;

public class ContainerPersonalInterface extends ContainerBuilder
{

    public ItemStack personalInterface;
    public EntityPlayer player;

    public ContainerPersonalInterface(EntityPlayer player, ItemStack personalInterface)
    {
        super(196, 250);

        this.player = player;
        this.personalInterface = personalInterface;
    }

    public ItemStack getPersonalInterface()
    {
        return personalInterface;
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return ItemPersonalInterface.isPlayer(personalInterface, player);
    }

    public void setLink(String link)
    {
        ItemPersonalInterface.setLink(personalInterface, link);
        PacketHandler.INSTANCE.sendToServer(new MessageSetPersonalInterfaceLink(link));

        List<String> linkParts = null;

        if (link != null && !link.isEmpty()) {
            linkParts = Arrays.asList(StringUtils.split(link, '/'));

            for (String part : linkParts) {
                if (part.equals("syncButton")) {
                    ItemPersonalInterface.setSyncing(personalInterface, !ItemPersonalInterface.getSyncing(personalInterface));
                    PacketHandler.INSTANCE.sendToServer(new MessageSetPersonalInterfaceSyncing());
                }
            }
        }
    }

    public String getLink()
    {
        return ItemPersonalInterface.getLink(personalInterface);
    }

}
