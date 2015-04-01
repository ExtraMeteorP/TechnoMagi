package com.ollieread.technomagi.common.block.fluid;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.init.Fluids;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;
import com.ollieread.technomagi.util.BlockHelper;

public class BlockAmnioticFluid extends BlockFluidFinite
{

    protected String name;

    public BlockAmnioticFluid(String name)
    {
        super(Fluids.amniotic, Material.water);

        this.name = name;
        this.setBlockTextureName(getTexturePath(name));
        this.setCreativeTab(TechnomagiTabs.blocks);
        this.setTickRandomly(true);
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
        super.registerBlockIcons(register);

        Fluids.amniotic.setIcons(this.blockIcon);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase) entity;
            if (world.rand.nextInt(50) == 0 && !entityLiving.isPotionActive(Potion.heal)) {
                entityLiving.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 1));
            }

            if (!entityLiving.isPotionActive(Potion.waterBreathing)) {
                entityLiving.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 1));
            }
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        int x2 = x + rand.nextInt(8);
        int y2 = y + rand.nextInt(2);
        int z2 = z + rand.nextInt(8);

        BlockHelper.applyBonemeal(world, x2, y2, z2);

        super.updateTick(world, x, y, z, rand);
    }

}
