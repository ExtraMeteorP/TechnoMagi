package com.ollieread.technomagi.api.specialisation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.api.TechnomagiApi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Specialisation
{
    protected String name;
    protected ResourceLocation icon;
    protected Map<IAttribute, AttributeModifier> attributes = new HashMap<IAttribute, AttributeModifier>();

    public Specialisation(String name, ResourceLocation icon)
    {
        this.name = name;
        this.icon = icon;
    }

    public String getName()
    {
        return name;
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getIcon()
    {
        return icon;
    }

    public String getUnlocalisedName()
    {
        return TechnomagiApi.PREFIX_SPECIALISATION + name;
    }

    public Specialisation addAttribute(IAttribute attribute, String name, double amount, int operation)
    {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(name), this.getName(), amount, operation);
        attributes.put(attribute, attributemodifier);
        return this;
    }

    public void applyAttributes(EntityPlayer player, BaseAttributeMap map)
    {
        for (Entry<IAttribute, AttributeModifier> entry : attributes.entrySet()) {
            IAttributeInstance attribute = map.getAttributeInstance(entry.getKey());

            if (attribute != null) {
                AttributeModifier modifier = entry.getValue();
                attribute.removeModifier(modifier);
                attribute.applyModifier(modifier);
            }
        }
    }

    public int modifyDamage(DamageSource damage, int amount)
    {
        return amount;
    }

}
