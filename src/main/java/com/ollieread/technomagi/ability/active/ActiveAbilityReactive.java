package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityReactiveCrafting;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityReactive extends AbilityActive
{

    public int cost = 0;
    protected Map<String, Integer> enhancements;

    public ActiveAbilityReactive(String name, int cost)
    {
        super(name, Reference.MODID.toLowerCase());

        this.cost = cost;
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("endo", 1);
        this.enhancements.put("exo", 1);
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
                Item item = Item.getItemFromBlock(block);

                if (item != null) {
                    ItemStack stack = new ItemStack(block, 1, meta);

                    if (stack != null) {
                        ItemStack recipe = FurnaceRecipes.smelting().getSmeltingResult(stack);

                        if (recipe != null) {
                            if (recipe.getItem() != null) {
                                Block result = Block.getBlockFromItem(recipe.getItem());

                                if (result != null) {
                                    if (charon.nanites.decreaseNanites(cost)) {
                                        if (!interact.world.isRemote) {
                                            interact.world.setBlock(x, y, z, Blocks.blockReactiveCrafting);
                                            TileEntityReactiveCrafting reactive = (TileEntityReactiveCrafting) player.worldObj.getTileEntity(x, y, z);

                                            if (reactive != null) {
                                                reactive.setResult(recipe, 100, new ItemStack(block, 1, meta));
                                            }
                                        }

                                        return true;
                                    }

                                    return true;
                                }
                            }
                        }
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
        return new String[] { "endothermic", "exothermic" };
    }

}
