package com.ollieread.technomagi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.ennds.item.IConstructModule;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConstruct extends Block
{

    public static final String[] names = new String[] { "default", "archive", "knowledgeReceptacle", "naniteReplicator" };

    public BlockConstruct(String name)
    {
        super(Material.iron);

        setBlockName(name);
        setBlockTextureName(name);
        setCreativeTab(TechnoMagi.tabTM);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (player.getHeldItem() != null) {
            ItemStack stack = player.getHeldItem();

            if (stack.getItem() instanceof IConstructModule) {
                Block module = ((IConstructModule) stack.getItem()).getModuleBlock(stack);

                if (module != null) {
                    world.setBlock(x, y, z, module);
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }

        return false;
    }

}
