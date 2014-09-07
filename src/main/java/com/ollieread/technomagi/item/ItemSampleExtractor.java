package com.ollieread.technomagi.item;

import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
        Set<Class> entities = ResearchRegistry.getMonitorableEntities();

        if (entities.contains(entity.getClass())) {
            sample = new ItemStack(Items.itemSampleVile, 1, 1);
            ItemSampleVile.setEntity(sample, entity.getClass());
            ItemStack vileStack = new ItemStack(Items.itemSampleVile, 1, 0);
            vileStack.stackTagCompound = new NBTTagCompound();

            boolean flag = false;

            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);

                if (slot != null && slot.isItemEqual(vileStack)) {
                    if (ItemSampleVile.getEntity(slot) == null) {
                        slot.stackSize--;

                        if (slot.stackSize <= 0) {
                            slot = null;
                        }

                        player.inventory.setInventorySlotContents(i, slot);
                        flag = true;
                        break;
                    }
                }
            }

            if (flag && entity.attackEntityFrom(DamageSource.generic, 2)) {
                stack.setItemDamage(stack.getItemDamage() + 1);
                EntityItem item = player.entityDropItem(sample, 0);
                item.delayBeforeCanPickup = 0;

                return true;
            }
        }

        return false;
    }
}
