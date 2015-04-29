package com.ollieread.technomagi.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntitySample;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.PlayerHelper;

public class ItemSampleExtractor extends ItemBase
{

    public ItemSampleExtractor(String name)
    {
        super(name);

        this.setMaxDamage(100);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null || !movingobjectposition.typeOfHit.equals(MovingObjectType.ENTITY)) {
            this.createSampleFor(stack, player, player);
        }

        return stack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        return this.createSampleFor(stack, player, entity);
    }

    protected boolean createSampleFor(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        Class<? extends EntityLivingBase> entityClass = entity.getClass();

        if (entity instanceof EntityPlayer) {
            entityClass = EntityPlayer.class;
        }

        IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entityClass);

        if (descriptor != null && descriptor instanceof IEntitySample) {
            int damage = ((IEntitySample) descriptor).getExtractorDamage();
            ItemStack sample = PlayerHelper.getInventoryItem(player, ItemStackHelper.sample(null));

            if (sample != null) {
                sample.stackSize--;
                ItemStack newStack = ItemStackHelper.sample(entityClass);
                PlayerHelper.giveInventoryItem(player, newStack);
                entity.attackEntityFrom(DamageSource.generic, 2F);
                stack.damageItem(damage, player);

                return true;
            }
        }

        return false;
    }

}
