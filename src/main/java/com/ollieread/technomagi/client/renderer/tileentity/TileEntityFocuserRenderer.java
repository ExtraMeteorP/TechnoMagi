package com.ollieread.technomagi.client.renderer.tileentity;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelMachineConstruct;
import com.ollieread.technomagi.client.model.ModelMachineFocuser;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityFocuser;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class TileEntityFocuserRenderer extends TileEntitySpecialRenderer
{

    private final ModelMachineConstruct construct;
    private final ModelMachineFocuser focuser;
    private static Field rollField = ReflectionHelper.findField(EntityRenderer.class, "camRoll", "field_78495_O");
    private static Field prevRollField = ReflectionHelper.findField(EntityRenderer.class, "prevCamRoll", "field_78505_P");

    public TileEntityFocuserRenderer()
    {
        construct = new ModelMachineConstruct();
        focuser = new ModelMachineFocuser();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks)
    {
        TileEntityFocuser machine = (TileEntityFocuser) te;

        int side = machine.getBlockMetadata();
        Tessellator tessellator = Tessellator.instance;
        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = Blocks.blockFocuser.getLightValue(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        int l = te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureFocuser = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelFocuser.png"));

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        switch (side) {
            case 2:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                break;
        }

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureFocuser);

        focuser.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

}
