package com.ollieread.technomagi.common.event.handler;

import java.util.List;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.crafting.CraftingHandler;
import com.ollieread.technomagi.api.electromagnetic.EnergyHandler;
import com.ollieread.technomagi.api.electromagnetic.IElectromagneticActionItem;
import com.ollieread.technomagi.api.electromagnetic.EnergyHandler.EnergyType;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeBlock;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeEntity;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeItem;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.common.knowledge.energies.ElectromagneticPockets;
import com.ollieread.technomagi.common.knowledge.energies.EnergyHeat;
import com.ollieread.technomagi.common.knowledge.energies.EnergyLife;
import com.ollieread.technomagi.common.knowledge.energies.EnergyLight;
import com.ollieread.technomagi.common.knowledge.energies.EnergyVoid;
import com.ollieread.technomagi.common.misc.DamageSourceTechnomagi;
import com.ollieread.technomagi.common.misc.PotionTechnomagi;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ElectromagneticPocketEvents
{

    @SubscribeEvent
    public void onExposeEntity(ExposeEntity event)
    {
        if (event.type.equals(EnergyHandler.EnergyType.LIFE)) {
            if ((event.entity.getMaxHealth() - event.entity.getHealth()) < event.amount) {
                event.amount = event.entity.getMaxHealth() - event.entity.getHealth();
            }

            if (event.negative) {
                if (event.entity.isEntityUndead()) {
                    event.entity.heal(event.amount);
                    event.setResult(Result.ALLOW);
                } else if (event.entity.attackEntityFrom(DamageSourceTechnomagi.lifeforceDamage, event.amount)) {
                    event.setResult(Result.ALLOW);
                }
            } else {
                event.entity.heal(event.amount);
                event.setResult(Result.ALLOW);
            }
        } else if (event.type.equals(EnergyHandler.EnergyType.LIGHT)) {
            if (event.entity instanceof EntityPlayer) {
                event.setResult(Result.ALLOW);
            }

            if (event.negative) {
                if (event.entity instanceof EntityZombie || event.entity instanceof EntitySkeleton) {
                    if (event.entity.worldObj.isDaytime() && event.entity.isBurning()) {
                        event.entity.extinguish();
                        event.setResult(Result.ALLOW);
                    }
                }
            } else {
                if (event.entity instanceof EntityZombie || event.entity instanceof EntitySkeleton) {
                    event.entity.setFire((int) event.amount);
                    event.setResult(Result.ALLOW);
                }
            }
        } else if (event.type.equals(EnergyHandler.EnergyType.VOID)) {
            if (event.negative) {
                event.entity.addPotionEffect(new PotionEffect(PotionTechnomagi.voidSickness.id, (int) (60 * event.amount), (int) event.amount));
                event.setResult(Result.ALLOW);
            }
        } else if (event.type.equals(EnergyHandler.EnergyType.HEAT)) {
            if (event.negative) {
                if (event.entity.attackEntityFrom(DamageSourceTechnomagi.frostDamage, event.amount)) {
                    event.setResult(Result.ALLOW);
                }
            } else {
                if (event.entity.attackEntityFrom(DamageSource.onFire, event.amount)) {
                    event.setResult(Result.ALLOW);
                }
            }
        }

        if (event.getResult().equals(Result.ALLOW)) {
            if (event.entity instanceof EntityPlayer) {
                TechnomagiApi.knowledge().performResearch(event.entity, ElectromagneticPockets.exposedToPocket);

                if (event.type.equals(EnergyHandler.EnergyType.LIFE)) {
                    TechnomagiApi.knowledge().performResearch(event.entity, ElectromagneticPockets.exposedToLifePocket);
                } else if (event.type.equals(EnergyHandler.EnergyType.LIGHT)) {
                    TechnomagiApi.knowledge().performResearch(event.entity, ElectromagneticPockets.exposedToLightPocket);
                } else if (event.type.equals(EnergyHandler.EnergyType.VOID)) {
                    TechnomagiApi.knowledge().performResearch(event.entity, ElectromagneticPockets.exposedToVoidPocket);
                } else if (event.type.equals(EnergyHandler.EnergyType.HEAT)) {
                    TechnomagiApi.knowledge().performResearch(event.entity, ElectromagneticPockets.exposedToHeatPocket);
                }
            } else {
                List<EntityPlayer> players = getPlayers(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, 8);

                for (EntityPlayer player : players) {
                    if (player.canEntityBeSeen(event.entity)) {
                        if (event.type.equals(EnergyHandler.EnergyType.LIFE)) {
                            TechnomagiApi.knowledge().performResearch(player, EnergyLife.witnessEntityExposure);
                        } else if (event.type.equals(EnergyHandler.EnergyType.LIGHT)) {
                            TechnomagiApi.knowledge().performResearch(player, EnergyLight.witnessEntityExposure);
                        } else if (event.type.equals(EnergyHandler.EnergyType.VOID)) {
                            TechnomagiApi.knowledge().performResearch(player, EnergyVoid.witnessEntityExposure);
                        } else if (event.type.equals(EnergyHandler.EnergyType.HEAT)) {
                            TechnomagiApi.knowledge().performResearch(player, EnergyHeat.witnessEntityExposure);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onExposeItem(ExposeItem event)
    {
        if (event.item != null) {
            ItemStack input = event.item.getEntityItem();

            if (input != null) {
                IElectromagneticActionItem recipe = CraftingHandler.electromagnetic.findMatchingAction(event.type, event.negative, input);

                if (recipe != null) {
                    ItemStack result = recipe.getOutput(event.type, event.negative);

                    if (recipe.perform(event.type, event.negative, event.item.worldObj, event.item, event.pocket.xCoord, event.pocket.yCoord, event.pocket.zCoord)) {
                        if (result != null) {
                            input.stackSize--;
                            ItemStackHelper.spawnInWorld(result, event.item.worldObj, event.item.posX, event.item.posY, event.item.posZ);
                        }

                        event.setResult(Result.ALLOW);

                        List<EntityPlayer> players = getPlayers(event.item.worldObj, event.item.posX, event.item.posY, event.item.posZ, 8);

                        for (EntityPlayer player : players) {
                            if (player.canEntityBeSeen(event.item)) {
                                if (input.isItemEqual(ItemStackHelper.itemSubtype(Items.resource, "etherium", 1))) {
                                    TechnomagiApi.knowledge().performResearch(player, Resources.etherium.etheriumExposed);
                                }

                                if (event.type.equals(EnergyHandler.EnergyType.HEAT)) {
                                    TechnomagiApi.knowledge().performResearch(player, EnergyHeat.witnessItemExposure);

                                    if (input.isItemEqual(ItemStackHelper.itemSubtype(Items.resource, "etherium", 1))) {
                                        TechnomagiApi.knowledge().performResearch(player, EnergyHeat.etheriumExposed);
                                    }
                                } else if (event.type.equals(EnergyHandler.EnergyType.LIGHT)) {
                                    TechnomagiApi.knowledge().performResearch(player, EnergyLight.witnessItemExposure);

                                    if (input.isItemEqual(ItemStackHelper.itemSubtype(Items.resource, "etherium", 1))) {
                                        TechnomagiApi.knowledge().performResearch(player, EnergyLight.etheriumExposed);
                                    }
                                } else if (event.type.equals(EnergyHandler.EnergyType.LIFE)) {
                                    TechnomagiApi.knowledge().performResearch(player, EnergyLife.witnessItemExposure);

                                    if (input.isItemEqual(ItemStackHelper.itemSubtype(Items.resource, "etherium", 1))) {
                                        TechnomagiApi.knowledge().performResearch(player, EnergyLife.etheriumExposed);
                                    }
                                } else if (event.type.equals(EnergyHandler.EnergyType.VOID)) {
                                    TechnomagiApi.knowledge().performResearch(player, EnergyVoid.witnessItemExposure);

                                    if (input.isItemEqual(ItemStackHelper.itemSubtype(Items.resource, "etherium", 1))) {
                                        TechnomagiApi.knowledge().performResearch(player, EnergyVoid.etheriumExposed);
                                    }
                                }
                            }
                        }

                        if (input.stackSize <= 0) {
                            event.item.setDead();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onExposeBlock(ExposeBlock event)
    {

    }

    public static List<EntityPlayer> getPlayers(World world, double posX, double posY, double posZ, int range)
    {
        return world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));
    }

}