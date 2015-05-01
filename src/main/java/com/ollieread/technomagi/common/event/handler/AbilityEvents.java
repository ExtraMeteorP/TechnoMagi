package com.ollieread.technomagi.common.event.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.IAbilityItem;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.event.AbilityCastEvent;
import com.ollieread.technomagi.client.gui.GuiTechnomagi;
import com.ollieread.technomagi.util.PlayerHelper;
import com.ollieread.technomagi.util.SoundHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AbilityEvents
{

    @SubscribeEvent
    public void interact(PlayerInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;

        if (PlayerHelper.hasSpecialised(player)) {
            PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(player);

            if (!technomage.abilities().isCasting()) {
                ItemStack heldStack = player.getHeldItem();

                if (heldStack != null && heldStack.getItem() instanceof IAbilityItem && ((IAbilityItem) heldStack.getItem()).canCast(heldStack)) {
                    World world = player.worldObj;
                    MovingObjectPosition mouse = PlayerHelper.getMovingObjectPosition(technomage.getPlayer().worldObj, technomage.getPlayer(), true);
                    AbilityPayload payload = PlayerHelper.getAbilityPayload(technomage.getPlayer().worldObj, technomage.getPlayer(), event.x, event.y, event.z, event.face);

                    if (payload != null) {
                        technomage.abilities().startCasting(heldStack, payload);
                    }
                }
            } else {
                AbilityPayload payload = PlayerHelper.getAbilityPayload(technomage.getPlayer().worldObj, technomage.getPlayer(), event.x, event.y, event.z, event.face);
                AbilityPayload currentPayload = technomage.abilities().getAbilityPayload();

                if (payload != null && currentPayload != null && !currentPayload.equals(payload)) {
                    technomage.abilities().stopCasting(false);
                }

                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void entityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;

        if (PlayerHelper.hasSpecialised(player)) {
            PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(player);

            if (!technomage.abilities().isCasting()) {
                ItemStack heldStack = player.getHeldItem();

                if (heldStack != null && heldStack.getItem() instanceof IAbilityItem && ((IAbilityItem) heldStack.getItem()).canCast(heldStack)) {
                    World world = player.worldObj;
                    MovingObjectPosition mouse = PlayerHelper.getMovingObjectPosition(technomage.getPlayer().worldObj, technomage.getPlayer(), true);
                    AbilityPayload payload = null;

                    if (mouse != null) {
                        if (mouse.typeOfHit.equals(MovingObjectType.ENTITY)) {
                            payload = new AbilityPayload(0, null, mouse.entityHit, mouse.blockX, mouse.blockY, mouse.blockZ, mouse.sideHit);
                            technomage.abilities().startCasting(heldStack, payload);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void stoppedCasting(AbilityCastEvent event)
    {
        if (event.entityPlayer.worldObj.isRemote) {
            GuiTechnomagi.overlay.updateContent();
        }
    }

    @SubscribeEvent
    public void stoppedCasting(AbilityCastEvent.Stop event)
    {
        if (event.complete) {
            SoundHelper.playSoundEffectAtPlayer(event.entityPlayer, "cast", event.entityPlayer.worldObj.rand);
        } else {
            SoundHelper.playSoundEffectAtPlayer(event.entityPlayer, "fail", event.entityPlayer.worldObj.rand);
        }
    }

}
