package com.ollieread.technomagi.item;

import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSampleExtractor extends ItemTM
{

    public ItemSampleExtractor(String name)
    {
        super(name);
        setMaxStackSize(1);
        setMaxDamage(50);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        ItemStack sample = null;
        player.swingItem();

        if (!entity.worldObj.isRemote) {
            Map<Integer, Integer> entities = ResearchRegistry.getMonitorableEntities();
            int id = EntityList.getEntityID(entity);

            if (entities.containsKey(id)) {
                sample = new ItemStack(Items.itemSampleVile, 1, id);

                if (entity.attackEntityFrom(DamageSource.generic, 2)) {
                    player.setItemInUse(new ItemStack(Items.itemSampleVile, 1, 0), 1);
                    EntityItem item = player.entityDropItem(sample, 0);
                    item.delayBeforeCanPickup = 0;

                    return true;
                }
            }
        }

        return false;
    }
}
