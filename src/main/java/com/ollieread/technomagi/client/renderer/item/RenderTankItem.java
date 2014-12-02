package com.ollieread.technomagi.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelTank;
import com.ollieread.technomagi.common.Reference;

public class RenderTankItem implements IItemRenderer
{

    private final ModelTank model;

    public RenderTankItem()
    {
        model = new ModelTank();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED || type == ItemRenderType.ENTITY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        ResourceLocation texture;

        switch (item.getItemDamage()) {
            case 1:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTankAdvanced.png"));
                break;
            case 2:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTankElite.png"));
                break;
            default:
                texture = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelTank.png"));
                break;
        }

        GL11.glPushMatrix();

        float scale = 0;

        if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON) || type.equals(ItemRenderType.EQUIPPED)) {
            scale = 1.2F;
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        } else if (type.equals(ItemRenderType.INVENTORY) || type.equals(ItemRenderType.ENTITY)) {
            scale = 1.0F;
        }

        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(270F, 0.0F, -1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -1.0F, 0.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (item.stackTagCompound != null) {
            FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 100);
            tank.readFromNBT(item.stackTagCompound);
            FluidStack fluidStack = tank.getFluid();

            if (fluidStack != null && fluidStack.getFluid() != null) {
                GL11.glDisable(GL11.GL_LIGHTING);

                GL11.glPushMatrix();
                GL11.glTranslatef(0, 1.0F, 0);

                int amount = tank.getFluidAmount();
                int capacity = tank.getCapacity();

                float scaleF = (0.7F / (FluidContainerRegistry.BUCKET_VOLUME * 100)) * (amount / (item.getItemDamage() * 5));

                if (scaleF < 0.1F) {
                    scaleF = 0.1F;
                }

                GL11.glScalef(scaleF, scaleF, scaleF);

                final Fluid fluid = fluidStack.getFluid();

                IIcon icon = fluid.getStillIcon();
                final int color;

                if (icon != null) {
                    Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                    color = fluid.getColor(fluidStack);
                } else {
                    Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                    icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("missingno");
                    color = 0xFFFFFFFF;
                }

                Tessellator t = Tessellator.instance;

                final double ySouthEast = 1;
                final double yNorthEast = 1;
                final double ySouthWest = 1;
                final double yNorthWest = 1;

                final double uMin = icon.getInterpolatedU(0.0);
                final double uMax = icon.getInterpolatedU(16.0);
                final double vMin = icon.getInterpolatedV(0.0);
                final double vMax = icon.getInterpolatedV(16.0);

                final double vHeight = vMax - vMin;

                final float r = (color >> 16 & 0xFF) / 255.0F;
                final float g = (color >> 8 & 0xFF) / 255.0F;
                final float b = (color & 0xFF) / 255.0F;

                // north side
                t.startDrawingQuads();
                t.setColorOpaque_F(r, g, b);
                t.addVertexWithUV(0.5, -0.5, -0.5, uMax, vMin); // bottom
                t.addVertexWithUV(-0.5, -0.5, -0.5, uMin, vMin); // bottom
                // top north/west
                t.addVertexWithUV(-0.5, -0.5 + yNorthWest, -0.5, uMin, vMin + (vHeight * yNorthWest));
                // top north/east
                t.addVertexWithUV(0.5, -0.5 + yNorthEast, -0.5, uMax, vMin + (vHeight * yNorthEast));

                // south side
                t.addVertexWithUV(0.5, -0.5, 0.5, uMin, vMin);
                // top south east
                t.addVertexWithUV(0.5, -0.5 + ySouthEast, 0.5, uMin, vMin + (vHeight * ySouthEast));
                // top south west
                t.addVertexWithUV(-0.5, -0.5 + ySouthWest, 0.5, uMax, vMin + (vHeight * ySouthWest));
                t.addVertexWithUV(-0.5, -0.5, 0.5, uMax, vMin);

                // east side
                t.addVertexWithUV(0.5, -0.5, -0.5, uMin, vMin);
                // top north/east
                t.addVertexWithUV(0.5, -0.5 + yNorthEast, -0.5, uMin, vMin + (vHeight * yNorthEast));
                // top south/east
                t.addVertexWithUV(0.5, -0.5 + ySouthEast, 0.5, uMax, vMin + (vHeight * ySouthEast));
                t.addVertexWithUV(0.5, -0.5, 0.5, uMax, vMin);

                // west side
                t.addVertexWithUV(-0.5, -0.5, 0.5, uMin, vMin);
                // top south/west
                t.addVertexWithUV(-0.5, -0.5 + ySouthWest, 0.5, uMin, vMin + (vHeight * ySouthWest));
                // top north/west
                t.addVertexWithUV(-0.5, -0.5 + yNorthWest, -0.5, uMax, vMin + (vHeight * yNorthWest));
                t.addVertexWithUV(-0.5, -0.5, -0.5, uMax, vMin);

                // top
                // south east
                t.addVertexWithUV(0.5, -0.5 + ySouthEast, 0.5, uMax, vMin);
                // north east
                t.addVertexWithUV(0.5, -0.5 + yNorthEast, -0.5, uMin, vMin);
                // north west
                t.addVertexWithUV(-0.5, -0.5 + yNorthWest, -0.5, uMin, vMax);
                // south west
                t.addVertexWithUV(-0.5, -0.5 + ySouthWest, 0.5, uMax, vMax);

                // bottom
                t.addVertexWithUV(0.5, -0.5, -0.5, uMax, vMin);
                t.addVertexWithUV(0.5, -0.5, 0.5, uMin, vMin);
                t.addVertexWithUV(-0.5, -0.5, 0.5, uMin, vMax);
                t.addVertexWithUV(-0.5, -0.5, -0.5, uMax, vMax);
                t.draw();

                GL11.glPopMatrix();

                GL11.glEnable(GL11.GL_LIGHTING);
            }
        }

        GL11.glPopMatrix();
    }

}
