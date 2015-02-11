package com.ollieread.technomagi.common.ability.cast;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.ability.AbilityCast;
import com.ollieread.technomagi.api.ability.AbilityHandler.AbilityType;
import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.scan.ScanHandler;

public class AbilityCastScan extends AbilityCast
{

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

        if (payload.target.equals(AbilityUseTarget.BLOCK)) {
            World world = technomage.getPlayer().worldObj;
            stack = new ItemStack(world.getBlock(payload.blockX, payload.blockY, payload.blockZ), 1, world.getBlockMetadata(payload.blockX, payload.blockY, payload.blockZ));
        } else if (payload.target.equals(AbilityUseTarget.ENTITY_ITEM)) {
            stack = ((EntityItem) payload.targetEntity).getEntityItem();
        }

        if (stack != null) {
            return !TechnomagiApi.scan().getItemStackScanMapping(ScanHandler.getItemStackRepresentation(stack)).isEmpty();
        }

        return false;
    }

    @Override
    public void use(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {

    }

    @Override
    public void stoppedUsing(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {

    }

    @Override
    public AbilityType getType(int abilityMode)
    {
        return null;
    }

}
