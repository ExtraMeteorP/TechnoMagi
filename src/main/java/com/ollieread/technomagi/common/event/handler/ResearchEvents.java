package com.ollieread.technomagi.common.event.handler;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.common.knowledge.Energies;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class ResearchEvents
{

    @SubscribeEvent
    public void onCrafting(ItemCraftedEvent event)
    {
        if (!event.player.worldObj.isRemote) {
            List<String> researchList = TechnomagiApi.knowledge().getCraftingResearch(event.crafting);

            for (String research : researchList) {
                for (int i = 0; i < event.crafting.stackSize; i++) {
                    if (TechnomagiApi.knowledge().performResearch(event.player, TechnomagiApi.knowledge().getResearch(research))) {
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSmelting(ItemSmeltedEvent event)
    {
        if (!event.player.worldObj.isRemote) {
            TechnomagiApi.knowledge().performResearch(event.player, Energies.energyHeat.smelting);
            List<String> researchList = TechnomagiApi.knowledge().getSmeltingResearch(event.smelting);

            for (int i = 0; i < event.smelting.stackSize; i++) {
                for (String research : researchList) {
                    if (TechnomagiApi.knowledge().performResearch(event.player, TechnomagiApi.knowledge().getResearch(research))) {
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onMining(HarvestDropsEvent event)
    {
        if (event.harvester != null && !event.harvester.worldObj.isRemote) {
            if (event.block != null) {
                List<String> researchList = TechnomagiApi.knowledge().getMiningResearch(event.block, event.blockMetadata);

                for (String research : researchList) {
                    TechnomagiApi.knowledge().performResearch(event.harvester, TechnomagiApi.knowledge().getResearch(research));
                }
            }
        }
    }

    @SubscribeEvent
    public void onPickup(ItemPickupEvent event)
    {
        if (!event.player.worldObj.isRemote) {
            List<String> researchList = TechnomagiApi.knowledge().getPickupResearch(event.pickedUp.getEntityItem());

            for (int i = 0; i < event.pickedUp.getEntityItem().stackSize; i++) {
                for (String research : researchList) {
                    if (TechnomagiApi.knowledge().performResearch(event.player, TechnomagiApi.knowledge().getResearch(research))) {
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            World world = event.entityPlayer.worldObj;
            EntityPlayer player = event.entityPlayer;

            if (event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int x = event.target.blockX;
                int y = event.target.blockY;
                int z = event.target.blockZ;

                if (world.canMineBlock(player, x, y, z) && player.canPlayerEdit(x, y, z, event.target.sideHit, event.current)) {
                    Block block = world.getBlock(x, y, z);
                    Fluid fluid = FluidRegistry.lookupFluidForBlock(block);

                    if (block == Blocks.lava) {
                        TechnomagiApi.knowledge().performResearch(player, Energies.energyHeat.pickupLava);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.source.damageType.equals(DamageSource.inFire)) {
                if (event.entityLiving.isInsideOfMaterial(Material.lava)) {
                    TechnomagiApi.knowledge().performResearch(event.entityLiving, Energies.energyHeat.inLava);
                } else {
                    TechnomagiApi.knowledge().performResearch(event.entityLiving, Energies.energyHeat.inFire);
                }
            } else if (event.source.damageType.equals(DamageSource.onFire)) {
                TechnomagiApi.knowledge().performResearch(event.entityLiving, Energies.energyHeat.onFire);

                IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(event.entityLiving.getClass());

                if (descriptor != null) {
                    if (descriptor.isUndead()) {
                        if (event.entityLiving.getEquipmentInSlot(4) != null) {
                            if (event.entityLiving.worldObj.isDaytime() && event.entityLiving.worldObj.canBlockSeeTheSky(MathHelper.floor_double(event.entityLiving.posX), MathHelper.floor_double(event.entityLiving.posY), MathHelper.floor_double(event.entityLiving.posZ))) {

                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTreeGrow(SaplingGrowTreeEvent event)
    {
        if (!event.world.isRemote) {
            List<EntityLivingBase> entityList = event.world.getEntitiesWithinAABB(EntityLivingBase.class, getAABB(event.x, event.y, event.z));

            for (EntityLivingBase entity : entityList) {
                if (EntityHelper.canSeeBlock(entity, event.x, event.y, event.z)) {
                    TechnomagiApi.knowledge().performResearch(entity, Energies.energyLight.witnessTreeGrow);
                    TechnomagiApi.knowledge().performResearch(entity, Energies.energyLife.witnessTreeGrow);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.entityLiving instanceof EntityWitch) {
                EntityWitch witch = (EntityWitch) event.entityLiving;
                ItemStack heldItem = witch.getHeldItem();

                if (heldItem != null && heldItem.getItem() != null) {
                    if (heldItem.isItemEqual(new ItemStack(Items.potionitem, 1, (short) 16341))) {
                        int witchTimer = 2;

                        for (Field f : EntityWitch.class.getDeclaredFields()) {
                            f.setAccessible(true);

                            try {
                                if (f.getName().equals("witchAttackTimer")) {
                                    Field modfield = Field.class.getDeclaredField("modifiers");
                                    witchTimer = f.getInt(witch);
                                }
                            } catch (Exception e) {
                            }
                        }

                        if (witchTimer <= 1) {
                            TechnomagiApi.knowledge().performResearch(witch, Energies.energyLife.witnessWitchHeal);

                            List<EntityLivingBase> entityList = event.entityLiving.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getAABB(witch.posX, witch.posY, witch.posZ));

                            for (EntityLivingBase entity : entityList) {
                                if (entity != witch && EntityHelper.canSeeBlock(entity, (int) witch.posX, (int) witch.posY, (int) witch.posZ)) {
                                    TechnomagiApi.knowledge().performResearch(entity, Energies.energyLife.witnessWitchHeal);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.entityLiving instanceof EntityAnimal) {
                EntityAnimal animal = (EntityAnimal) event.entityLiving;

                if (animal.isChild()) {
                    List<EntityLivingBase> entityList = event.entityLiving.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getAABB(event.x, event.y, event.z));

                    for (EntityLivingBase entity : entityList) {
                        if (entity != animal && EntityHelper.canSeeBlock(entity, (int) event.x, (int) event.y, (int) event.z)) {
                            TechnomagiApi.knowledge().performResearch(entity, Energies.energyLife.witnessAnimalBirth);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;

        if (event.target instanceof EntityAnimal) {
            ItemStack heldItem = player.getHeldItem();

            if (heldItem != null && heldItem.getItem() != null && ((EntityAnimal) event.target).isBreedingItem(heldItem)) {
                TechnomagiApi.knowledge().performResearch(player, Energies.energyLife.breedAnimals);

                List<EntityLivingBase> entityList = event.entityLiving.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getAABB((int) event.target.posX, (int) event.target.posY, (int) event.target.posZ));

                for (EntityLivingBase entity : entityList) {
                    if (entity != player && EntityHelper.canSeeBlock(entity, (int) event.target.posX, (int) event.target.posY, (int) event.target.posZ)) {
                        // This would be witnessing animal breeding
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onUseBonemeail(BonemealEvent event)
    {
        EntityPlayer player = event.entityPlayer;

        if (player != null && !event.entityPlayer.worldObj.isRemote) {
            if (event.block instanceof IGrowable) {
                TechnomagiApi.knowledge().performResearch(player, Energies.energyLife.useBonemeal);

                List<EntityLivingBase> entityList = event.entityLiving.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getAABB(event.x, event.y, event.z));

                for (EntityLivingBase entity : entityList) {
                    if (entity != player && EntityHelper.canSeeBlock(entity, event.x, event.y, event.z)) {
                        // This would be witnessing bonemeal usage
                    }
                }
            }
        }
    }

    public static AxisAlignedBB getAABB(double x, double y, double z)
    {
        return AxisAlignedBB.getBoundingBox(x - 8, y - 8, z - 8, x + 8, y + 8, z + 8);
    }

}
