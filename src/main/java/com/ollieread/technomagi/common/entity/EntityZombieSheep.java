package com.ollieread.technomagi.common.entity;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class EntityZombieSheep extends EntityZombie
{

    public EntityZombieSheep(World world)
    {
        super(world);
    }

    @Override
    protected String getLivingSound()
    {
        return "mob.sheep.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound()
    {
        return "mob.sheep.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound()
    {
        return "mob.sheep.hurt";
    }

    @Override
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        int j = this.rand.nextInt(2 + p_70628_2_);
        int k;

        for (k = 0; k < j; ++k) {
            this.dropItem(Items.rotten_flesh, 1);
        }
    }

    @Override
    public boolean interact(EntityPlayer p_70085_1_)
    {
        return false;
    }

    @Override
    protected void dropRareDrop(int p_70600_1_)
    {
        // this.dropItem(Items.gold_ingot, 1);
    }

    /**
     * Makes entity wear random armor based on difficulty
     */
    @Override
    protected void addRandomArmor()
    {
        // this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
    {
        super.onSpawnWithEgg(p_110161_1_);
        this.setVillager(false);
        return p_110161_1_;
    }

}