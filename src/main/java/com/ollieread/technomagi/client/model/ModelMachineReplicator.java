package com.ollieread.technomagi.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;

public class ModelMachineReplicator extends ModelBase
{
    // fields
    ModelRenderer base;
    ModelRenderer stand;
    ModelRenderer top;
    ModelRenderer resultstand;
    ModelRenderer samplestand;
    ModelRenderer sampleedge;
    ModelRenderer sample1;
    ModelRenderer sample2;
    ModelRenderer sample3;
    ModelRenderer sample4;

    TileEntityNaniteReplicator replicator = null;

    public ModelMachineReplicator()
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
        resultstand = new ModelRenderer(this, 65, 0);
        resultstand.addBox(0F, 0F, 0F, 14, 1, 3);
        resultstand.setRotationPoint(-7F, 7F, -7F);
        resultstand.setTextureSize(128, 64);
        resultstand.mirror = true;
        setRotation(resultstand, 0F, 0F, 0F);
        samplestand = new ModelRenderer(this, 65, 5);
        samplestand.addBox(0F, 0F, 0F, 14, 3, 3);
        samplestand.setRotationPoint(-7F, 5F, 4F);
        samplestand.setTextureSize(128, 64);
        samplestand.mirror = true;
        setRotation(samplestand, 0F, 0F, 0F);
        sampleedge = new ModelRenderer(this, 67, 12);
        sampleedge.addBox(0F, 0F, 0F, 14, 4, 2);
        sampleedge.setRotationPoint(-7F, 5F, 4F);
        sampleedge.setTextureSize(128, 64);
        sampleedge.mirror = true;
        setRotation(sampleedge, -0.4089647F, 0F, 0F);
        sample1 = new ModelRenderer(this, 56, 17);
        sample1.addBox(0F, 0F, 0F, 1, 4, 1);
        sample1.setRotationPoint(-5F, 1F, 5F);
        sample1.setTextureSize(128, 64);
        sample1.mirror = true;
        setRotation(sample1, 0F, 0F, 0F);
        sample2 = new ModelRenderer(this, 56, 17);
        sample2.addBox(0F, 0F, 0F, 1, 4, 1);
        sample2.setRotationPoint(-2F, 1F, 5F);
        sample2.setTextureSize(128, 64);
        sample2.mirror = true;
        setRotation(sample2, 0F, 0F, 0F);
        sample3 = new ModelRenderer(this, 56, 17);
        sample3.addBox(0F, 0F, 0F, 1, 4, 1);
        sample3.setRotationPoint(1F, 1F, 5F);
        sample3.setTextureSize(128, 64);
        sample3.mirror = true;
        setRotation(sample3, 0F, 0F, 0F);
        sample4 = new ModelRenderer(this, 56, 17);
        sample4.addBox(0F, 0F, 0F, 1, 4, 1);
        sample4.setRotationPoint(4F, 1F, 5F);
        sample4.setTextureSize(128, 64);
        sample4.mirror = true;
        setRotation(sample4, 0F, 0F, 0F);
    }

    public void setTileEntity(TileEntityNaniteReplicator tile)
    {
        replicator = tile;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        stand.render(f5);
        top.render(f5);
        resultstand.render(f5);
        samplestand.render(f5);
        sampleedge.render(f5);

        /*
         * if (replicator != null) { int sample = replicator.getSample();
         * System.out.println(sample);
         * 
         * if (sample < 75) { sample4.offsetY -= 3.0F; }
         * 
         * if (sample < 50) { sample3.offsetY -= 3.0F; }
         * 
         * if (sample < 25) { sample2.offsetY -= 3.0F; }
         * 
         * if (sample == 0) { sample1.offsetY -= 3.0F; } }
         */

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
