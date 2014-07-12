package com.ollieread.technomagi.ability;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityFlashstep extends AbilityActive
{

    public ActiveAbilityFlashstep(String name)
    {
        super(name, Reference.MODID.toLowerCase());
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

            if (!interact.action.equals(Action.LEFT_CLICK_BLOCK) && interact.entityPlayer.isSprinting()) {
                Vec3 look = PlayerHelper.getLookVector(interact.entityPlayer);
                Vec3 eye = PlayerHelper.getEyeVector(interact.entityPlayer);

                Vec3 target = Vec3.createVectorHelper(look.xCoord, look.yCoord, look.zCoord);
                Vec3 dest = null;
                int max = 10 * 10;
                int dmg = 0;

                for (int i = 0; i <= max; i++) {
                    target.xCoord = (look.xCoord * i) + eye.xCoord;
                    target.yCoord = (look.yCoord * i) + eye.yCoord;
                    target.zCoord = (look.zCoord * i) + eye.zCoord;

                    Block block = interact.entityPlayer.worldObj.getBlock(MathHelper.floor_double(target.xCoord), MathHelper.floor_double(target.yCoord), MathHelper.floor_double(target.zCoord));

                    if (!block.equals(Blocks.air) || eye.squareDistanceTo(target) >= max) {
                        break;
                    } else {
                        dest = Vec3.createVectorHelper(target.xCoord, target.yCoord, target.zCoord);
                    }
                }

                if (dest != null && eye.squareDistanceTo(dest) < max && decreaseNanites(charon, 8)) {
                    // Random rand = new Random();
                    // interact.entityPlayer.worldObj.playSoundEffect(dest.xCoord
                    // + 0.5D, dest.yCoord + 0.5D, dest.zCoord + 0.5D,
                    // Reference.MODID.toLowerCase() + ":flashstep", 1.0F,
                    // rand.nextFloat() * 0.4F + 0.8F);

                    interact.entityPlayer.setPositionAndUpdate(dest.xCoord, dest.yCoord, dest.zCoord);

                    return true;
                }
            }
        }

        return false;
    }

}
