package com.ollieread.technomagi.potion;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;
import com.ollieread.ennds.extended.ExtendedNanites;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncPlayerCapabilities;
import com.ollieread.technomagi.util.DamageSourceTM;
import com.ollieread.technomagi.util.PlayerHelper;
import com.ollieread.technomagi.util.PotionHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionTM extends Potion
{

    public static final PotionTM anger = new PotionAnger();
    public static final PotionTM passify = new PotionPassify().addModifier(SharedMonsterAttributes.attackDamage, 5.0D, 0);
    public static final PotionTM voidSickness = new PotionTM("potion.tm.voidSickness", true, 8171462).setTexture("textures/abilities/void.png");
    public static final PotionTM flight = new PotionTM("potion.tm.flight", true, 8171462).setTexture("textures/abilities/flight.png");
    public static final PotionTM naniteRegeneration = new PotionTM("potion.tm.naniteRegeneration", true, 8171462).setTexture("textures/abilities/naniteRegeneration.png");
    public static final PotionTM naniteDegeneration = new PotionTM("potion.tm.naniteDegeneration", true, 8171462).setTexture("textures/abilities/naniteDegeneration.png");

    private final Map modifiers = Maps.newHashMap();
    private int statusIconIndex = -1;
    private boolean renderInv = true;
    private ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/potions.png");

    public PotionTM(String name, boolean isBad, int colour)
    {
        this(name, isBad, colour, true);
    }

    public PotionTM(String name, boolean isBad, int colour, boolean inv)
    {
        super(PotionHelper.nextPotionId(), isBad, colour);

        setPotionName(name);
        renderInv = inv;
    }

    public PotionTM setTexture(String texture)
    {
        setTexture(new ResourceLocation(Reference.MODID.toLowerCase(), texture));
        return this;
    }

    protected PotionTM setTexture(ResourceLocation texture)
    {
        this.texture = texture;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        return this.statusIconIndex;
    }

    public PotionTM addModifier(IAttribute attribute, double amount, int operation)
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
                PacketHandler.INSTANCE.sendTo(new MessageSyncPlayerCapabilities(((EntityPlayer) entity).capabilities), (EntityPlayerMP) entity);
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
                PacketHandler.INSTANCE.sendTo(new MessageSyncPlayerCapabilities(((EntityPlayer) entity).capabilities), (EntityPlayerMP) entity);
            }
        }

        super.removeAttributesModifiersFromEntity(entity, map, amplifier);
    }

    public void performEffect(EntityLivingBase entity, int effect)
    {
        if (id == voidSickness.id) {
            if (entity.getHealth() > 1.0F) {
                entity.attackEntityFrom(DamageSourceTM.voidDamage, 1.0F);
            }
        } else if (id == naniteRegeneration.id) {
            if (entity instanceof EntityPlayer) {
                ExtendedNanites nanites = PlayerHelper.getPlayerNanites((EntityPlayer) entity);

                if (nanites.getNanites() < 100) {
                    nanites.setNanites(nanites.getNanites() + 5);
                }
            }
        } else if (id == naniteDegeneration.id) {
            if (entity instanceof EntityPlayer) {
                ExtendedNanites nanites = PlayerHelper.getPlayerNanites((EntityPlayer) entity);

                if (nanites.getNanites() > 0) {
                    nanites.setNanites(nanites.getNanites() - 5);
                }
            }
        }
    }

    public boolean isReady(int duration, int modifier)
    {
        int k;

        if (id == voidSickness.id) {
            k = 25 >> modifier;
            return k > 0 ? duration % k == 0 : true;
        } else if (id == naniteRegeneration.id) {
            k = 50 >> modifier;
            return k > 0 ? duration % k == 0 : true;
        } else if (id == naniteDegeneration.id) {
            k = 50 >> modifier;
            return k > 0 ? duration % k == 0 : true;
        } else if (id == flight.id) {
            return true;
        }

        return false;
    }

    public boolean shouldRenderInvText(PotionEffect effect)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
    {
        mc.getTextureManager().bindTexture(texture);

        mc.currentScreen.func_146110_a(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }

}
