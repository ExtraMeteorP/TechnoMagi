package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.util.SoundHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityHarvest extends AbilityActive
{

    protected Map<String, Integer> enhancements;
    protected int cost;

    public ActiveAbilityHarvest(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("force", 1);
        this.cost = Config.harvestCost;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event, ItemStack stack)
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

                if (block != null && block.getHarvestLevel(meta) <= ((IStaff) stack.getItem()).getEnhancement(stack, "force")) {
                    String tool = block.getHarvestTool(meta);

                    if (decreaseNanites(charon, cost)) {
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

    @Override
    public Map<String, Integer> getEnhancements()
    {
        return enhancements;
    }

    @Override
    public String[] getKnowledge()
    {
        return new String[] { "density", "force" };
    }
}
