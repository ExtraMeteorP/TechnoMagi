package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityFlashstep extends AbilityActive
{
    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int distance;

    public ActiveAbilityFlashstep(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("force", 2);
        this.cost = Config.flashstepCost;
        this.distance = Config.flashstepDistance;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event, ItemStack stack)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (!interact.action.equals(Action.LEFT_CLICK_BLOCK) && interact.entityPlayer.isSprinting()) {
                Vec3 look = EntityHelper.getLookVector(interact.entityPlayer);
                Vec3 eye = EntityHelper.getEyeVector(interact.entityPlayer);

                Vec3 target = Vec3.createVectorHelper(look.xCoord, interact.entityPlayer.posY, look.zCoord);
                Vec3 dest = null;
                int max = distance * distance;
                int dmg = 0;
                Random rand = new Random();
                World world = interact.entityPlayer.worldObj;

                for (int i = 1; i <= max; i++) {
                    target.xCoord = (look.xCoord * i) + eye.xCoord;
                    target.zCoord = (look.zCoord * i) + eye.zCoord;

                    if (!world.isAirBlock(MathHelper.floor_double(target.xCoord), MathHelper.floor_double(target.yCoord), MathHelper.floor_double(target.zCoord)) || eye.squareDistanceTo(target) >= max || !world.isAirBlock(MathHelper.floor_double(target.xCoord), MathHelper.floor_double(target.yCoord + 1), MathHelper.floor_double(target.zCoord))) {
                        break;
                    } else {
                        dest = Vec3.createVectorHelper(target.xCoord, target.yCoord, target.zCoord);
                    }
                }

                if (dest != null && eye.squareDistanceTo(dest) < max && decreaseNanites(charon, cost)) {
                    // Random rand = new Random();
                    // interact.entityPlayer.worldObj.playSoundEffect(dest.xCoord
                    // + 0.5D, dest.yCoord + 0.5D, dest.zCoord + 0.5D,
                    // Reference.MODID.toLowerCase() + ":flashstep", 1.0F,
                    // rand.nextFloat() * 0.4F + 0.8F);

                    if (!world.isRemote) {
                        interact.entityPlayer.setPositionAndUpdate(dest.xCoord, dest.yCoord, dest.zCoord);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Map<String, Integer> getEnhancements()
    {
        return enhancements;
    }

    @Override
    public String[] getKnowledge()
    {
        return new String[] { "motion" };
    }

}
