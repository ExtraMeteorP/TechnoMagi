package com.ollieread.technomagi.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSpinningCube extends ModelBase
{

    private ModelRenderer cube;
    private ModelRenderer glass = new ModelRenderer(this, "glass");
    private static final String __OBFID = "CL_00000871";

    public ModelSpinningCube(float p_i1170_1_, boolean p_i1170_2_)
    {
        this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
        this.cube = new ModelRenderer(this, "cube");
        this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
        GL11.glPushMatrix();
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);

        GL11.glRotatef(p_78088_3_, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.8F, 0.0F);
        GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
        this.glass.render(p_78088_7_);
        float f6 = 0.875F;
        GL11.glScalef(f6, f6, f6);
        GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
        GL11.glRotatef(p_78088_3_, 0.0F, 1.0F, 0.0F);
        this.glass.render(p_78088_7_);
        GL11.glPopMatrix();
    }
}
