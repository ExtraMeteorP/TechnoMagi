package com.ollieread.technomagi.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.entity.robot.EntityRobotZombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRobotZombie extends RenderLiving
{
    private static final ResourceLocation cowTextures = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/robotZombie.png");
    private static final String __OBFID = "CL_00000984";

    public RenderRobotZombie(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    protected ResourceLocation getEntityTexture(EntityRobotZombie par1EntityCow)
    {
        return cowTextures;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityRobotZombie) par1Entity);
    }

}