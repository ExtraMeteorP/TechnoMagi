package com.ollieread.technomagi.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.inventory.ContainerStaff;
import com.ollieread.technomagi.item.ItemStaff;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiStaff extends GuiContainer
{

    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/staff.png");
    protected ItemStack staff;
    protected GuiTMButton specialiseButton;
    protected EntityPlayer player;

    public GuiStaff(EntityPlayer player, ItemStack staff)
    {
        super(new ContainerStaff(player, staff));

        this.player = player;
        this.staff = staff;

        xSize = 185;
        ySize = 162;
    }

    public void initGui()
    {
        super.initGui();

        specialiseButton = new GuiTMButton(1, this.guiLeft + 42, this.guiTop + 49, 101, 15, I18n.format("technomagi.activate.button"));
        this.buttonList.add(specialiseButton);

        if (ItemStaff.isComplete(staff)) {
            specialiseButton.enabled = true;
        } else {
            specialiseButton.enabled = false;
        }
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(I18n.format("technomagi.staff.gui"), 7, 9, 16777215);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1) {
            if (ItemStaff.isComplete(staff)) {
                player.openGui(TechnoMagi.instance, CommonProxy.GUI_SPECIALISATION, player.worldObj, 0, 0, 0);
            }
        }
    }

    public void updateScreen()
    {
        if (ItemStaff.isComplete(staff)) {
            specialiseButton.enabled = true;
        } else {
            specialiseButton.enabled = false;
        }
    }

}