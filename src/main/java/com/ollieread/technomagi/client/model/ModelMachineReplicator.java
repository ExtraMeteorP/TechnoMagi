package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import com.ollieread.technomagi.tileentity.TileEntityMachineReplicator;

public class ModelMachineReplicator extends ModelBase
{
    // fields
    ModelRenderer nanites;
    ModelRenderer con1;
    ModelRenderer con2;
    ModelRenderer sample1;
    ModelRenderer sample2;
    ModelRenderer sample3;
    ModelRenderer sample4;

    protected TileEntityMachineReplicator replicator = null;

    public ModelMachineReplicator()
    {
        textureWidth = 128;
        textureHeight = 64;

        nanites = new ModelRenderer(this, 0, 0);
        nanites.addBox(0F, 0F, 0F, 3, 2, 8);
        nanites.setRotationPoint(3F, 11F, -4F);
        nanites.setTextureSize(128, 64);
        nanites.mirror = true;
        setRotation(nanites, 0F, 0F, 0F);
        con1 = new ModelRenderer(this, 23, 0);
        con1.addBox(0F, 0F, 0F, 1, 1, 1);
        con1.setRotationPoint(4F, 12F, 4F);
        con1.setTextureSize(128, 64);
        con1.mirror = true;
        setRotation(con1, 0F, 0F, 0F);
        con2 = new ModelRenderer(this, 23, 0);
        con2.addBox(0F, 0F, 0F, 1, 1, 1);
        con2.setRotationPoint(4F, 12F, -5F);
        con2.setTextureSize(128, 64);
        con2.mirror = true;
        setRotation(con2, 0F, 0F, 0F);
        sample3 = new ModelRenderer(this, 28, 0);
        sample3.addBox(0F, 0F, 0F, 1, 5, 1);
        sample3.setRotationPoint(-5F, 8F, 1F);
        sample3.setTextureSize(128, 64);
        sample3.mirror = true;
        setRotation(sample3, 0F, 0F, 0F);
        sample1 = new ModelRenderer(this, 28, 0);
        sample1.addBox(0F, 0F, 0F, 1, 5, 1);
        sample1.setRotationPoint(-5F, 8F, -5F);
        sample1.setTextureSize(128, 64);
        sample1.mirror = true;
        setRotation(sample1, 0F, 0F, 0F);
        sample2 = new ModelRenderer(this, 28, 0);
        sample2.addBox(0F, 0F, 0F, 1, 5, 1);
        sample2.setRotationPoint(-5F, 8F, -2F);
        sample2.setTextureSize(128, 64);
        sample2.mirror = true;
        setRotation(sample2, 0F, 0F, 0F);
        sample4 = new ModelRenderer(this, 28, 0);
        sample4.addBox(0F, 0F, 0F, 1, 5, 1);
        sample4.setRotationPoint(-5F, 8F, 4F);
        sample4.setTextureSize(128, 64);
        sample4.mirror = true;
        setRotation(sample4, 0F, 0F, 0F);
    }

    public void setTileEntity(TileEntityMachineReplicator tile)
    {
        replicator = tile;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        nanites.render(f5);
        con1.render(f5);
        con2.render(f5);
        sample1.render(f5);
        sample2.render(f5);
        sample3.render(f5);
        sample4.render(f5);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int sample)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        nanites.render(f5);
        con1.render(f5);
        con2.render(f5);

        sample1.offsetY = 0.3F;
        sample2.offsetY = 0.3F;
        sample3.offsetY = 0.3F;
        sample4.offsetY = 0.3F;

        if (sample > 0) {
            if (sample < 25) {
                sample1.offsetY -= (sample / (25 / 3)) / 10F;
            } else if (sample < 50) {
                sample2.offsetY -= ((sample - 25) / (25 / 3)) / 10F;
                sample1.offsetY = 0;
            } else if (sample < 75) {
                sample3.offsetY -= ((sample - 50) / (25 / 3)) / 10F;
                sample1.offsetY = 0;
                sample2.offsetY = 0;
            } else if (sample < 100) {
                sample4.offsetY -= ((sample - 75) / (25 / 3)) / 10F;
                sample1.offsetY = 0;
                sample2.offsetY = 0;
                sample3.offsetY = 0;
            } else {
                sample1.offsetY = 0;
                sample2.offsetY = 0;
                sample3.offsetY = 0;
                sample4.offsetY = 0;
            }
        }

        sample1.render(f5);
        sample2.render(f5);
        sample3.render(f5);
        sample4.render(f5);
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

}
