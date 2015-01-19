package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.inventory.abstracts.ContainerBuilder;
import com.ollieread.technomagi.item.ItemPersonalInterface;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncPersonalInterfaceLink;

public class ContainerPersonalInterface extends ContainerBuilder
{

    private ItemStack personalInterface;
    private EntityPlayer player;

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
        PacketHandler.INSTANCE.sendToServer(new MessageSyncPersonalInterfaceLink(link));
    }

    public String getLink()
    {
        return ItemPersonalInterface.getLink(personalInterface);
    }

}
