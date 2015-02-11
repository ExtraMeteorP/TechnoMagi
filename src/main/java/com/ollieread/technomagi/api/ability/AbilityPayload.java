package com.ollieread.technomagi.api.ability;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidBlock;

/**
 * This is the payload set to the ability. It holds information regarding the
 * target, focus and various bits of information.
 * 
 * TODO Explore the possibility of having target updated as the cast goes on,
 * while storing previous target.
 * 
 * @author ollieread
 *
 */
public class AbilityPayload
{

    /**
     * Denotes the type of target for the cast. Some abilities require targets
     * of a specific type and others will do slightly different things dependant
     * on the type of target.
     * 
     * @author ollieread
     *
     */
    public static enum AbilityUseTarget {
        AIR, BLOCK, LIQUID, ENTITY, ENTITY_ITEM, ENTITY_LIVING, PLAYER
    }

    /**
     * Denotes the result of the current cast action.
     * 
     * @author ollieread
     *
     */
    public static enum AbilityResult {
        CONTINUE, HALT, COMPLETE
    }

    /**
     * The current duration of the cast.
     */
    public int duration;
    /**
     * The type of target for this cast.
     * 
     * @see AbilityUseTarget
     */
    public AbilityUseTarget target;
    /**
     * The result of the current cast action. Defaults to
     * {@link AbilityResult#CONTINUE}, must specify otherwise.
     * 
     * @see AbilityResult
     */
    public AbilityResult result;
    /**
     * The entity, if the target is {@link AbilityUseTarget#ENTITY}.
     */
    public Entity targetEntity;
    /**
     * The living entity, if the target is
     * {@link AbilityUseTarget#ENTITY_LIVING}.
     */
    public EntityLiving targetEntityLiving;
    /**
     * The player entity, if the target is {@link AbilityUseTarget#PLAYER}.
     */
    public EntityPlayer targetPlayer;
    /**
     * The coordinates of the block, if the target is
     * {@link AbilityUseTarget#BLOCK}.
     */
    public int blockX;
    public int blockY;
    public int blockZ;
    public int sideHit;
    /**
     * The item that is allowing the player to cast.
     */
    public ItemStack abilityItem;

    public AbilityPayload()
    {

    }

    public AbilityPayload(int duration, Block targetBlock, Entity targetEntity, int x, int y, int z, int side)
    {
        this.duration = duration;
        this.targetEntity = targetEntity;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.sideHit = side;
        this.result = AbilityResult.CONTINUE;

        if (this.targetEntity != null) {
            if (this.targetEntity instanceof EntityPlayer) {
                this.targetPlayer = (EntityPlayer) this.targetEntity;
                this.target = AbilityUseTarget.PLAYER;
            } else if (this.targetEntity instanceof EntityLiving) {
                this.targetEntityLiving = (EntityLiving) this.targetEntity;
                this.target = AbilityUseTarget.ENTITY_LIVING;
            } else if (this.targetEntity instanceof EntityItem) {
                this.target = AbilityUseTarget.ENTITY_ITEM;
            } else {
                this.target = AbilityUseTarget.ENTITY;
            }
        } else {
            if (targetBlock == null) {
                this.target = AbilityUseTarget.AIR;
            } else {
                if (targetBlock instanceof BlockLiquid || targetBlock instanceof IFluidBlock) {
                    this.target = AbilityUseTarget.LIQUID;
                } else {
                    this.target = AbilityUseTarget.BLOCK;
                }
            }
        }
    }

    public void setAbilityItem(ItemStack stack)
    {
        this.abilityItem = stack;
    }

    public void setResult(AbilityResult result)
    {
        this.result = result;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Duration", duration);
        compound.setInteger("Target", target.ordinal());

        if (targetPlayer != null) {
            compound.setString("Player", targetPlayer.getCommandSenderName());
        } else if (targetEntityLiving != null) {
            compound.setInteger("EntityLiving", targetEntityLiving.getEntityId());
        } else if (targetEntity != null) {
            compound.setInteger("Entity", targetEntity.getEntityId());
        }

        compound.setInteger("BlockX", blockX);
        compound.setInteger("BlockY", blockY);
        compound.setInteger("BlockZ", blockZ);
        compound.setInteger("SideHit", sideHit);

        if (abilityItem != null) {
            NBTTagCompound itemCompound = new NBTTagCompound();
            abilityItem.writeToNBT(itemCompound);
            compound.setTag("AbilityItem", itemCompound);
        }
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        duration = compound.getInteger("Duration");
        target = AbilityUseTarget.values()[compound.getInteger("Target")];

        if (compound.hasKey("Player")) {
            targetPlayer = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(compound.getString("Player"));
            targetEntity = targetPlayer;
        } else if (compound.hasKey("EntityLiving")) {
            targetEntityLiving = (EntityLiving) Minecraft.getMinecraft().theWorld.getEntityByID(compound.getInteger("EntityLiving"));
            targetEntity = targetEntityLiving;
        } else if (compound.hasKey("Entity")) {
            targetEntity = Minecraft.getMinecraft().theWorld.getEntityByID(compound.getInteger("Entity"));
        }

        blockX = compound.getInteger("BlockX");
        blockY = compound.getInteger("BlockY");
        blockZ = compound.getInteger("BlockZ");
        sideHit = compound.getInteger("SideHit");

        if (compound.hasKey("AbilityItem")) {
            abilityItem = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("AbilityItem"));
        }
    }

}
