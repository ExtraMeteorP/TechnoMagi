package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMachineArchive extends ModelBase
{
    ModelRenderer base;
    ModelRenderer stand;
    ModelRenderer top;
    ModelRenderer screenleft;
    ModelRenderer screenright;
    ModelRenderer screenmid;

    protected boolean owned = false;

    public ModelMachineArchive()
    {
        textureWidth = 128;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 16, 1, 16);
        base.setRotationPoint(-8F, 23F, -8F);
        base.setTextureSize(128, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        stand = new ModelRenderer(this, 0, 17);
        stand.addBox(0F, 0F, 0F, 14, 8, 14);
        stand.setRotationPoint(-7F, 15F, -7F);
        stand.setTextureSize(128, 64);
        stand.mirror = true;
        setRotation(stand, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 39);
        top.addBox(0F, 0F, 0F, 16, 7, 16);
        top.setRotationPoint(-8F, 8F, -8F);
        top.setTextureSize(128, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        screenleft = new ModelRenderer(this, 68, 0);
        screenleft.addBox(0F, 0F, 0F, 6, 6, 0);
        screenleft.setRotationPoint(-9F, -0.2F, 0F);
        screenleft.setTextureSize(128, 64);
        screenleft.mirror = true;
        setRotation(screenleft, -0.3270685F, -0.5728604F, 0.2230717F);
        screenright = new ModelRenderer(this, 68, 0);
        screenright.addBox(0F, 0F, 0F, 6, 6, 0);
        screenright.setRotationPoint(4F, 1F, 3F);
        screenright.setTextureSize(128, 64);
        screenright.mirror = false;
        setRotation(screenright, -0.3141593F, 0.6283185F, -0.2443461F);
        screenmid = new ModelRenderer(this, 67, 7);
        screenmid.addBox(0F, 0F, 0F, 8, 6, 0);
        screenmid.setRotationPoint(-4F, 1F, 3F);
        screenmid.setTextureSize(128, 64);
        screenmid.mirror = true;
        setRotation(screenmid, -0.3839724F, 0F, 0F);
        screenmid.mirror = false;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        stand.render(f5);
        top.render(f5);

        if (owned) {
            screenleft.render(f5);
            screenright.render(f5);
            screenmid.render(f5);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

    public void setOwnedStatus(boolean s)
    {
        owned = s;
    }

}
