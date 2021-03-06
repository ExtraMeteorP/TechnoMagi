package com.ollieread.technomagi.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.entity.robot.EntityRobotCow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRobotCow extends RenderLiving
{
    private static final ResourceLocation cowTextures = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/robotCow.png");
    private static final String __OBFID = "CL_00000984";

    public RenderRobotCow(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    protected ResourceLocation getEntityTexture(EntityRobotCow par1EntityCow)
    {
        return cowTextures;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityRobotCow) par1Entity);
    }

}