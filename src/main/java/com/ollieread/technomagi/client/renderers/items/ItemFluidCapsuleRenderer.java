package com.ollieread.technomagi.client.renderers.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.item.ItemFluidCapsule;
import com.ollieread.technomagi.util.RenderHelper;

public class ItemFluidCapsuleRenderer implements IItemRenderer
{

    @Override
    public boolean handleRenderType(ItemStack stack, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper)
    {
        return type.equals(IItemRenderer.ItemRenderType.ENTITY);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... params)
    {
        IIcon icon = ((ItemFluidCapsule) stack.getItem()).getIconFromDamage(0);
        IIcon iconMask = ((ItemFluidCapsule) stack.getItem()).itemOverlay;
        IIcon iconMaskGas = ((ItemFluidCapsule) stack.getItem()).itemOverlayGas;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(3008);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/atlas/items.png"));

        if (!type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
            ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
        } else {
            RenderHelper.renderIcon(icon, 4.0D);
        }

        if (stack.stackTagCompound != null) {
            FluidStack fluidStack = ((ItemFluidCapsule) stack.getItem()).getFluid(stack);

            if (fluidStack != null) {
                Fluid fluid = fluidStack.getFluid();

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/atlas/items.png"));

                if (fluid.isGaseous() || fluid.getDensity() < 0) {
                    renderMask(iconMaskGas, fluid.getIcon(), type);
                } else {
                    renderMask(iconMask, fluid.getIcon(), type);
                }
            }
        }

        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glPopMatrix();
    }

    public static void renderMask(IIcon maskIcon, IIcon fluidIcon, ItemRenderType type)
    {
        if ((maskIcon == null) || (fluidIcon == null)) {
            return;
        }

        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);

        GL11.glDisable(2884);
        Tessellator localTessellator = Tessellator.instance;
        localTessellator.startDrawingQuads();
        localTessellator.setNormal(0.0F, 0.0F, 1.0F);

        if (type.equals(ItemRenderType.EQUIPPED_FIRST_PERSON) || type.equals(ItemRenderType.EQUIPPED)) {
            GL11.glTranslatef(0F, 1F, -0.0625F);
            GL11.glRotatef(180, 1, 0, 0);
        }

        if (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
            Tessellator.instance.addVertexWithUV(0.0D, 16.0D, 10.0D, maskIcon.getMinU(), maskIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(16.0D, 16.0D, 10.0D, maskIcon.getMaxU(), maskIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(16.0D, 0.0D, 10.0D, maskIcon.getMaxU(), maskIcon.getMinV());
            Tessellator.instance.addVertexWithUV(0.0D, 0.0D, 10.0D, maskIcon.getMinU(), maskIcon.getMinV());
        } else {
            Tessellator.instance.addVertexWithUV(0.0D, 1.0D, 0.001D, maskIcon.getMinU(), maskIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(1.0D, 1.0D, 0.001D, maskIcon.getMaxU(), maskIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(1.0D, 0.0D, 0.001D, maskIcon.getMaxU(), maskIcon.getMinV());
            Tessellator.instance.addVertexWithUV(0.0D, 0.0D, 0.001D, maskIcon.getMinU(), maskIcon.getMinV());
        }
        localTessellator.draw();

        localTessellator.startDrawingQuads();
        localTessellator.setNormal(0.0F, 0.0F, -1.0F);
        if (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
            Tessellator.instance.addVertexWithUV(0.0D, 16.0D, -0.0635D, maskIcon.getMinU(), maskIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(16.0D, 16.0D, -0.0635D, maskIcon.getMaxU(), maskIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(16.0D, 0.0D, -0.0635D, maskIcon.getMaxU(), maskIcon.getMinV());
            Tessellator.instance.addVertexWithUV(0.0D, 0.0D, -0.0635D, maskIcon.getMinU(), maskIcon.getMinV());
        } else {
            Tessellator.instance.addVertexWithUV(0.0D, 1.0D, -0.0635D, maskIcon.getMinU(), maskIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(1.0D, 1.0D, -0.0635D, maskIcon.getMaxU(), maskIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(1.0D, 0.0D, -0.0635D, maskIcon.getMaxU(), maskIcon.getMinV());
            Tessellator.instance.addVertexWithUV(0.0D, 0.0D, -0.0635D, maskIcon.getMinU(), maskIcon.getMinV());
        }
        localTessellator.draw();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/atlas/blocks.png"));

        GL11.glDepthFunc(514);
        GL11.glDepthMask(false);

        localTessellator.startDrawingQuads();
        localTessellator.setNormal(0.0F, 0.0F, 1.0F);
        if (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
            Tessellator.instance.addVertexWithUV(0.0D, 16.0D, 10.0D, fluidIcon.getMinU(), fluidIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(16.0D, 16.0D, 10.0D, fluidIcon.getMaxU(), fluidIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(16.0D, 0.0D, 10.0D, fluidIcon.getMaxU(), fluidIcon.getMinV());
            Tessellator.instance.addVertexWithUV(0.0D, 0.0D, 10.0D, fluidIcon.getMinU(), fluidIcon.getMinV());
        } else {
            Tessellator.instance.addVertexWithUV(0.0D, 1.0D, 0.001D, fluidIcon.getMinU(), fluidIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(1.0D, 1.0D, 0.001D, fluidIcon.getMaxU(), fluidIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(1.0D, 0.0D, 0.001D, fluidIcon.getMaxU(), fluidIcon.getMinV());
            Tessellator.instance.addVertexWithUV(0.0D, 0.0D, 0.001D, fluidIcon.getMinU(), fluidIcon.getMinV());
        }
        localTessellator.draw();

        localTessellator.startDrawingQuads();
        localTessellator.setNormal(0.0F, 0.0F, -1.0F);
        if (type.equals(IItemRenderer.ItemRenderType.INVENTORY)) {
            Tessellator.instance.addVertexWithUV(0.0D, 16.0D, -0.0635D, fluidIcon.getMinU(), fluidIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(16.0D, 16.0D, -0.0635D, fluidIcon.getMaxU(), fluidIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(16.0D, 0.0D, -0.0635D, fluidIcon.getMaxU(), fluidIcon.getMinV());
            Tessellator.instance.addVertexWithUV(0.0D, 0.0D, -0.0635D, fluidIcon.getMinU(), fluidIcon.getMinV());
        } else {
            Tessellator.instance.addVertexWithUV(0.0D, 1.0D, -0.0635D, fluidIcon.getMinU(), fluidIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(1.0D, 1.0D, -0.0635D, fluidIcon.getMaxU(), fluidIcon.getMaxV());
            Tessellator.instance.addVertexWithUV(1.0D, 0.0D, -0.0635D, fluidIcon.getMaxU(), fluidIcon.getMinV());
            Tessellator.instance.addVertexWithUV(0.0D, 0.0D, -0.0635D, fluidIcon.getMinU(), fluidIcon.getMinV());
        }
        localTessellator.draw();

        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glDepthFunc(515);
        GL11.glEnable(2884);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
