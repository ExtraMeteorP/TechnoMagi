package com.ollieread.technomagi.common.misc;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;
import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.util.PacketHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionTechnomagi extends Potion
{

    public static final PotionTechnomagi voidSickness = new PotionTechnomagi("potion.technomage.voidSickness", true, 8171462).setTexture("textures/abilities/void.png");
    public static final PotionTechnomagi freezing = new PotionTechnomagi("potion.technomage.freezing", true, 8171462).setTexture("textures/abilities/freezing.png");
    public static final PotionTechnomagi burning = new PotionTechnomagi("potion.technomage.burning", true, 81711462).setTexture("texutres/abilities/burning.png");
    public static final PotionTechnomagi draining = new PotionTechnomagi("potion.technomage.draining", true, 81711462).setTexture("textures/abilities/draining.png");
    public static final PotionTechnomagi flight = new PotionTechnomagi("potion.technomage.flight", true, 8171462).setTexture("textures/abilities/flight.png");
    public static final PotionTechnomagi naniteRegeneration = new PotionTechnomagi("potion.technomage.naniteRegeneration", true, 8171462).setTexture("textures/abilities/naniteRegeneration.png");
    public static final PotionTechnomagi naniteDegeneration = new PotionTechnomagi("potion.technomage.naniteDegeneration", true, 8171462).setTexture("textures/abilities/naniteDegeneration.png");
    public static final PotionTechnomagi exposure = new PotionTechnomagi("potion.technomage.exposure", true, 8171462).setTexture("textures/abilities/exposure.png");
    public static final PotionTechnomagi shortcircuit = new PotionTechnomagi("potion.technomage.shortcircuit", true, 8171462).setTexture("texture/abilities/shortcircuit.png");
    public static final PotionTechnomagi phased = new PotionTechnomagi("potion.technomage.phased", true, 8171462).setTexture("textures/abilities/phase_jump");

    private final Map modifiers = Maps.newHashMap();
    private int statusIconIndex = -1;
    private boolean renderInv = true;
    private ResourceLocation texture = new ResourceLocation(Technomagi.MODID.toLowerCase(), "textures/gui/potions.png");

    public PotionTechnomagi(String name, boolean isBad, int colour)
    {
        this(name, isBad, colour, true);
    }

    public PotionTechnomagi(String name, boolean isBad, int colour, boolean inv)
    {
        super(nextPotionId(), isBad, colour);

        setPotionName(name);
        renderInv = inv;
    }

    public PotionTechnomagi setTexture(String texture)
    {
        setTexture(new ResourceLocation(Technomagi.MODID.toLowerCase(), texture));
        return this;
    }

    protected PotionTechnomagi setTexture(ResourceLocation texture)
    {
        this.texture = texture;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        return this.statusIconIndex;
    }

    public PotionTechnomagi addModifier(IAttribute attribute, double amount, int operation)
    {
        AttributeModifier attributemodifier = new AttributeModifier(this.getName(), amount, operation);
        this.modifiers.put(attribute, attributemodifier);
        return this;
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entity, BaseAttributeMap map, int amplifier)
    {
        if (id == flight.id) {
            if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.allowFlying) {
                ((EntityPlayer) entity).capabilities.allowFlying = true;
                ((EntityPlayer) entity).capabilities.isFlying = true;
                PacketHelper.syncPlayerCapabilities((EntityPlayer) entity);
            }
        }

        Iterator iterator = modifiers.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance iattributeinstance = map.getAttributeInstance((IAttribute) entry.getKey());

            if (iattributeinstance != null) {
                AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();
                iattributeinstance.removeModifier(attributemodifier);
                iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), this.getName() + " " + amplifier, func_111183_a(amplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, BaseAttributeMap map, int amplifier)
    {
        if (id == flight.id) {
            if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode && ((EntityPlayer) entity).capabilities.allowFlying) {
                ((EntityPlayer) entity).capabilities.allowFlying = false;
                ((EntityPlayer) entity).capabilities.isFlying = false;
                PacketHelper.syncPlayerCapabilities((EntityPlayer) entity);
            }
        }

        super.removeAttributesModifiersFromEntity(entity, map, amplifier);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int effect)
    {
        if (id == voidSickness.id) {
            if (entity.getHealth() > 1.0F) {
                entity.attackEntityFrom(DamageSourceTechnomagi.voidDamage, 1.0F);
            }
        } else if (id == naniteRegeneration.id) {
            if (entity instanceof EntityPlayer) {
                PlayerTechnomagi technomage = PlayerTechnomagi.get((EntityPlayer) entity);

                if (technomage != null) {
                    technomage.nanites().increaseNanites(Math.max(20 << effect, 0));
                }
            }
        } else if (id == naniteDegeneration.id) {
            if (entity instanceof EntityPlayer) {
                PlayerTechnomagi technomage = PlayerTechnomagi.get((EntityPlayer) entity);

                if (technomage != null) {
                    technomage.nanites().decreaseNanites(Math.max(20 << effect, 0));
                }
            }
        } else if (id == freezing.id) {
            if (entity.getHealth() > 0.5F) {
                entity.attackEntityFrom(DamageSourceTechnomagi.frostDamage, 6 << effect);
            }
        } else if (id == burning.id) {
            if (entity.getHealth() > 0.5F) {
                entity.attackEntityFrom(DamageSource.onFire, 6 << effect);
            }
        } else if (id == draining.id) {
            if (entity.getHealth() > 0.5F) {
                entity.attackEntityFrom(DamageSourceTechnomagi.lifeforceDamage, 6 << effect);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int modifier)
    {
        int k;

        if (id == voidSickness.id) {
            k = 25 >> modifier;
            return k > 0 ? duration % k == 0 : true;
        } else if (id == freezing.id || id == burning.id) {
            k = 50 >> modifier;
            return k > 0 ? duration % k == 0 : true;
        } else if (id == naniteRegeneration.id || id == naniteDegeneration.id) {
            k = 50 >> modifier;
            return k > 0 ? duration % k == 0 : true;
        }

        return false;
    }

    @Override
    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
    {
        mc.getTextureManager().bindTexture(texture);

        mc.currentScreen.func_146110_a(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }

    public static int nextPotionId()
    {
        for (int i = 0; i < Potion.potionTypes.length; i++) {
            if (Potion.potionTypes[i] == null) {
                return i;
            }
        }

        return -1;
    }

}
