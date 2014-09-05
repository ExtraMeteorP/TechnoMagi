package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.util.SoundHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityHarvestIII extends AbilityActive
{

    public ActiveAbilityHarvestIII(String name)
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
            EntityPlayer player = interact.entityPlayer;

            if (interact.action.equals(Action.RIGHT_CLICK_BLOCK)) {
                int x = interact.x;
                int y = interact.y;
                int z = interact.z;
                int meta = player.worldObj.getBlockMetadata(x, y, z);
                Block block = player.worldObj.getBlock(x, y, z);

                if (block != null && block.getHarvestLevel(meta) <= 3 && (block.getHarvestTool(meta).equals("pickaxe") || block.getHarvestTool(meta).equals("shovel"))) {
                    if (decreaseNanites(charon, 9)) {
                        if (!player.worldObj.isRemote) {
                            player.worldObj.func_147480_a(x, y, z, true);
                            SoundHelper.playSoundEffectAtPlayer(player, "cast", new Random());
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

}
