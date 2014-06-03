package com.ollieread.technomagi.ability;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.technomagi.api.ability.AbilityActive;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityFire extends AbilityActive
{

    public ActiveAbilityFire(String name)
    {
        super(name, new ResourceLocation(Reference.MODID.toLowerCase(), "textures/ability/1.png"));
    }

    @Override
    public boolean canUse(PlayerKnowledge charon, Event event)
    {
        return true;
    }

    @Override
    public boolean isAvailable(PlayerKnowledge charon)
    {
        return false;
    }

    @Override
    public void use(PlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (interact.action.equals(Action.RIGHT_CLICK_BLOCK) && charon.decreaseNanites(5)) {
                if (!interact.entityLiving.worldObj.isRemote) {
                    int x = interact.x;
                    int y = interact.y;
                    int z = interact.z;

                    if (interact.face == 0) {
                        --y;
                    }

                    if (interact.face == 1) {
                        ++y;
                    }

                    if (interact.face == 2) {
                        --z;
                    }

                    if (interact.face == 3) {
                        ++z;
                    }

                    if (interact.face == 4) {
                        --x;
                    }

                    if (interact.face == 5) {
                        ++x;
                    }

                    if (!interact.entityPlayer.canPlayerEdit(x, y, z, interact.face, null)) {
                        return;
                    } else {
                        if (interact.entityPlayer.worldObj.isAirBlock(x, y, z)) {
                            Random rand = new Random();
                            interact.entityPlayer.worldObj.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                            interact.entityPlayer.worldObj.setBlock(x, y, z, Blocks.fire);
                        }
                    }
                }
            }
        } else if (event instanceof EntityInteractEvent) {
            EntityInteractEvent interact = (EntityInteractEvent) event;

            if (interact.target instanceof EntityPlayer && !interact.entityPlayer.canAttackPlayer((EntityPlayer) interact.target)) {
                return;
            }

            if (interact.target instanceof EntityLiving && charon.decreaseNanites(8)) {
                if (!interact.entityLiving.worldObj.isRemote) {
                    EntityLiving target = (EntityLiving) interact.target;
                }
            }
        }
    }

}
