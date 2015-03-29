package com.ollieread.technomagi.client.renderers.tiles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.models.ModelBattery;
import com.ollieread.technomagi.common.block.energy.tile.TileBattery;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.util.ResourceHelper;

public class TileBatteryRenderer extends TileEntitySpecialRenderer
{

    private final ModelBattery battery;

    public TileBatteryRenderer()
    {
        battery = new ModelBattery();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks)
    {
        TileBattery machine = (TileBattery) te;

        Tessellator tessellator = Tessellator.instance;
        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = Blocks.battery.getLightValue(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        int l = te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, l1, l2);
        ResourceLocation textureBattery = null;

        if (machine.getBlockMetadata() == 0) {
            textureBattery = ResourceHelper.texture("models/battery.png");
        } else {
            textureBattery = ResourceHelper.texture("models/battery_nanite_iron.png");
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureBattery);

        battery.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
