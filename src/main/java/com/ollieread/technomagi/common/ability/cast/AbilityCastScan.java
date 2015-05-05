package com.ollieread.technomagi.common.ability.cast;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.ability.AbilityCast;
import com.ollieread.technomagi.api.ability.AbilityHandler.AbilityType;
import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityResult;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.util.BlockHelper;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.PlayerHelper;

public class AbilityCastScan extends AbilityCast
{

    public final static int maxDuration = 50;

    public AbilityCastScan(String name, ResourceLocation icon)
    {
        super(name, icon);
    }

    @Override
    public boolean isAvailable(PlayerTechnomagi technomage)
    {
        return true;
    }

    @Override
    public boolean canUse(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
        ItemStack stack = null;
        boolean flag = false;

        if (payload.target.equals(AbilityUseTarget.BLOCK)) {
            World world = technomage.getPlayer().worldObj;
            flag = !TechnomagiApi.scan().getScanMapping(BlockHelper.getBlockRepresentation(world.getBlock(payload.blockX, payload.blockY, payload.blockZ), world.getBlockMetadata(payload.blockX, payload.blockY, payload.blockZ))).isEmpty();
        } else if (payload.target.equals(AbilityUseTarget.ENTITY_ITEM)) {
            stack = ((EntityItem) payload.targetEntity).getEntityItem();
            flag = !TechnomagiApi.scan().getScanMapping(ItemStackHelper.getItemStackRepresentation(stack)).isEmpty();
        }

        return flag;
    }

    @Override
    public void use(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
        World world = technomage.getPlayer().worldObj;
        MovingObjectPosition mouse = PlayerHelper.getMovingObjectPosition(technomage.getPlayer().worldObj, technomage.getPlayer(), true);
        AbilityPayload payloadCompare = null;
        boolean matches = false;

        if (mouse.typeOfHit.equals(MovingObjectType.BLOCK)) {
            Block block = world.getBlock(mouse.blockX, mouse.blockY, mouse.blockZ);
            int metadata = world.getBlockMetadata(mouse.blockX, mouse.blockY, mouse.blockZ);
            payloadCompare = new AbilityPayload(0, block, null, mouse.blockX, mouse.blockY, mouse.blockZ, mouse.sideHit);
            Block block2 = world.getBlock(payload.blockX, payload.blockY, payload.blockZ);
            int metadata2 = world.getBlockMetadata(payload.blockX, payload.blockY, payload.blockZ);

            matches = payload.target.equals(payloadCompare.target) && payload.blockX == payloadCompare.blockX && payload.blockY == payloadCompare.blockY && payload.blockZ == payloadCompare.blockZ && block == block2 && metadata == metadata2;
        } else if (mouse.typeOfHit.equals(MovingObjectType.ENTITY)) {
            payloadCompare = new AbilityPayload(0, null, mouse.entityHit, mouse.blockX, mouse.blockY, mouse.blockZ, mouse.sideHit);

            matches = payload.target.equals(payloadCompare.target) && payload.targetEntity.getEntityId() == payloadCompare.targetEntity.getEntityId();
        } else {
            payloadCompare = new AbilityPayload(0, null, null, mouse.blockX, mouse.blockY, mouse.blockZ, mouse.sideHit);
        }

        if (matches) {
            List<String> mapping = null;

            if (payload.target.equals(AbilityUseTarget.BLOCK)) {
                Block block = world.getBlock(payload.blockX, payload.blockY, payload.blockZ);
                int metadata = world.getBlockMetadata(payload.blockX, payload.blockY, payload.blockZ);
                mapping = TechnomagiApi.scan().getScanMapping(BlockHelper.getBlockRepresentation(block, metadata));
            } else if (payload.target.equals(AbilityUseTarget.ENTITY_ITEM)) {
                mapping = TechnomagiApi.scan().getScanMapping(ItemStackHelper.getItemStackRepresentation(((EntityItem) payload.targetEntity).getEntityItem()));
            }

            if (mapping != null && !mapping.isEmpty()) {
                for (String name : mapping) {
                    IResearch research = TechnomagiApi.knowledge().getResearch(name);

                    if (research != null && technomage.knowledge().canResearch(research)) {
                        if (payload.duration < maxDuration) {
                            payload.setResult(AbilityResult.CONTINUE);
                        } else if (technomage.nanites().decreaseNanites(0)) {
                            if (TechnomagiApi.knowledge().performResearch(technomage.getPlayer(), research)) {
                                payload.setResult(AbilityResult.COMPLETE);
                            } else {
                                payload.setResult(AbilityResult.HALT);
                            }
                        }
                        return;
                    }
                }
            }
        }

        payload.setResult(AbilityResult.HALT);
        return;
    }

    @Override
    public void stoppedUsing(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
        technomage.abilities().removeAbilityData("Scanning");
    }

    @Override
    public AbilityType getType(int abilityMode)
    {
        return null;
    }

    @Override
    public int getMaxFocus(int abilityMode)
    {
        return maxDuration;
    }

    @Override
    public int getCooldown(int abilityMode)
    {
        return 20;
    }

}
