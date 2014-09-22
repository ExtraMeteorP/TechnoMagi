package com.ollieread.technomagi.client.gui.archive;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.tileentity.TileEntityArchive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiArchiveReactive extends GuiArchive
{

    public GuiArchiveReactive(EntityPlayer player, TileEntityArchive archive)
    {
        super(player, archive);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        // TODO Auto-generated method stub

    }

}