package com.ollieread.technomagi.common.event.handler;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.EntityTechnomagi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.specialisation.Specialisation;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class InitialisationEvents
{

    // public static List<InterfaceCreation> interfaceCreations = new
    // LinkedList<InterfaceCreation>();

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer) {
            if (PlayerTechnomagi.get((EntityPlayer) event.entity) == null) {
                PlayerTechnomagi.register((EntityPlayer) event.entity);
            } else {
                PlayerTechnomagi technomage = PlayerTechnomagi.get((EntityPlayer) event.entity);

                if (technomage.hasSpecialised()) {
                    Specialisation spec = technomage.getSpecialisation();
                    spec.applyAttributes(((EntityPlayer) event.entity), ((EntityPlayer) event.entity).getAttributeMap());
                }

                technomage.sync();
            }
        } else {
            List<Class> allowedEntities = TechnomagiApi.entity().getNaniteEntities();

            for (Class entity : allowedEntities) {
                if (entity.isInstance(event.entity) && EntityTechnomagi.get((EntityLivingBase) event.entity) == null) {
                    EntityTechnomagi.register((EntityLivingBase) event.entity);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            PlayerTechnomagi.saveProxyData((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onClone(Clone event)
    {
        if (!event.entity.worldObj.isRemote && event.original instanceof EntityPlayer && !event.wasDeath) {
            PlayerTechnomagi technomage = PlayerTechnomagi.get(event.entityPlayer);

            if (technomage != null && technomage.hasSpecialised()) {
                technomage.copyFrom(PlayerTechnomagi.get(event.original));
                Specialisation spec = technomage.getSpecialisation();
                spec.applyAttributes(((EntityPlayer) event.entity), ((EntityPlayer) event.entity).getAttributeMap());
            }

            technomage.sync();
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            PlayerTechnomagi.loadProxyData((EntityPlayer) event.entity);
            PlayerTechnomagi technomage = PlayerTechnomagi.get((EntityPlayer) event.entity);

            if (technomage != null && technomage.hasSpecialised()) {
                Specialisation spec = technomage.getSpecialisation();
                spec.applyAttributes(((EntityPlayer) event.entity), ((EntityPlayer) event.entity).getAttributeMap());
            }

            technomage.sync();
        }
    }

}
