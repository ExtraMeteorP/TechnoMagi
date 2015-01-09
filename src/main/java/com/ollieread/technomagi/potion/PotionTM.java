package com.ollieread.technomagi.potion;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.EntityLivingBase;
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
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncPlayerCapabilities;
import com.ollieread.technomagi.util.DamageSourceTM;
import com.ollieread.technomagi.util.PotionHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionTM extends Potion
{

    public static final PotionTM passify = new PotionPassify();
    public static final PotionTM anger = new PotionTM("potion.anger", true, 8171462).setIconIndex(1, 0);
    public static final PotionTM voidSickness = new PotionTM("potion.voidSickness", true, 8171462).setIconIndex(2, 0);
    public static final PotionTM antiGravity = new PotionTM("potion.antiGravity", true, 8171462).setIconIndex(0, 0);

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

    protected PotionTM setIconIndex(int p_76399_1_, int p_76399_2_)
    {
        statusIconIndex = p_76399_1_ + p_76399_2_ * 8;
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
        if (id == antiGravity.id) {
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
        if (id == antiGravity.id) {
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
        }
    }

    public boolean isReady(int duration, int modifier)
    {
        int k;

        if (id == voidSickness.id) {
            k = 25 >> modifier;
            return k > 0 ? duration % k == 0 : true;
        } else if (id == antiGravity.id) {
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
        int l = getStatusIconIndex();

        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, l % 8 * 18, l / 8 * 18, 18, 18);
    }

}
