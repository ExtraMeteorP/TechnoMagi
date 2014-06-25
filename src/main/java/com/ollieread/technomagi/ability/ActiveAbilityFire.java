package com.ollieread.technomagi.ability;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.technomagi.api.ability.AbilityActive;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityFire extends AbilityActive
{

    public ActiveAbilityFire(String name)
    {
        super(name);
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, Event event)
    {
        return true;
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (interact.action.equals(Action.RIGHT_CLICK_BLOCK)) {
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
                    return false;
                } else {
                    Block block = interact.entityPlayer.worldObj.getBlock(x, y, z);
                    if (interact.entityPlayer.worldObj.isAirBlock(x, y, z)) {
                        if (decreaseNanites(charon, 4)) {
                            if (!interact.entityPlayer.worldObj.isRemote) {
                                Random rand = new Random();
                                interact.entityPlayer.worldObj.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, Reference.MODID.toLowerCase() + ":cast", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                                interact.entityPlayer.worldObj.setBlock(x, y, z, Blocks.fire);
                            }

                            interact.useBlock = Event.Result.DENY;

                            return true;
                        }
                    } else if (block.isEqualTo(block, Blocks.fire)) {
                        if (decreaseNanites(charon, 6)) {
                            if (!interact.entityPlayer.worldObj.isRemote) {
                                interact.entityPlayer.worldObj.setBlockToAir(x, y, z);
                            }

                            interact.useBlock = Event.Result.DENY;

                            return true;
                        }
                    }
                }
            }
        } else if (event instanceof EntityInteractEvent) {
            EntityInteractEvent interact = (EntityInteractEvent) event;

            if (interact.target instanceof EntityPlayer && !interact.entityPlayer.canAttackPlayer((EntityPlayer) interact.target)) {
                return false;
            }

            if (interact.target instanceof EntityLiving && decreaseNanites(charon, 8)) {
                EntityLiving target = (EntityLiving) interact.target;

                if (!interact.entityPlayer.worldObj.isRemote) {
                    if (target.isBurning()) {
                        target.extinguish();
                    } else {
                        target.setFire(5);
                    }
                }

                return true;
            }
        }

        return false;
    }

}
