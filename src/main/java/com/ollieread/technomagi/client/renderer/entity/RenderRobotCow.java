package com.ollieread.technomagi.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.entity.passive.EntityRobotCow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRobotCow extends RenderLiving
{
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/robotCow.png");
    private static final String __OBFID = "CL_00000984";

    public RenderRobotCow(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    protected ResourceLocation getEntityTexture(EntityRobotCow par1EntityCow)
    {
        return texture;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called
     * unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityRobotCow) par1Entity);
    }
}