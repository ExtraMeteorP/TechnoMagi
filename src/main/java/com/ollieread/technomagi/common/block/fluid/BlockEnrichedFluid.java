package com.ollieread.technomagi.common.block.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.init.Fluids;
import com.ollieread.technomagi.common.misc.PotionTechnomagi;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnrichedFluid extends BlockFluidFinite
{

    protected String name;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;

    public BlockEnrichedFluid(String name)
    {
        super(Fluids.enriched, Material.water);

        this.name = name;
        this.setBlockTextureName(getTexturePath(name));
        this.setCreativeTab(TechnomagiTabs.blocks);
    }

    public String getTexturePath(String name)
    {
        return Technomagi.MODID.toLowerCase() + ":" + name;
    }

    @Override
    public String getUnlocalizedName()
    {
        return "tile.technomagi." + name;
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        flowingIcon = register.registerIcon(getTexturePath("fluid/enriched_fluid_flowing"));
        stillIcon = register.registerIcon(getTexturePath("fluid/enriched_fluid_still"));

        Fluids.enriched.setIcons(flowingIcon, stillIcon);
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return side <= 1 ? stillIcon : flowingIcon;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase) entity;

            if (!entityLiving.isPotionActive(PotionTechnomagi.naniteRegeneration)) {
                entityLiving.addPotionEffect(new PotionEffect(PotionTechnomagi.naniteRegeneration.id, 200, 2));
            }

            if (!entityLiving.isPotionActive(Potion.waterBreathing)) {
                entityLiving.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 1));
            }
        }
    }

}
