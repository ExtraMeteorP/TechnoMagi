package com.ollieread.technomagi.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.abstracts.BlockBasicContainer;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.ITileEntityMachine;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.tileentity.TileEntityConstruct;
import com.ollieread.technomagi.tileentity.TileEntityMachineAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityMachineAreaLight;
import com.ollieread.technomagi.tileentity.TileEntityMachineAssembler;
import com.ollieread.technomagi.tileentity.TileEntityMachineDisplacer;
import com.ollieread.technomagi.tileentity.TileEntityMachineFurnace;
import com.ollieread.technomagi.tileentity.TileEntityMachineInfuser;
import com.ollieread.technomagi.tileentity.TileEntityMachineProcessor;
import com.ollieread.technomagi.tileentity.TileEntityMachineReplicator;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineResearch;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachine extends BlockBasicContainer implements IBlockMulti
{

    private Random rand = new Random();
    public static String[] blockNames = new String[] { "construct", "archive", "assembler", "replicator", "analysis", "processor", "furnace", "embuer", "arealight", "displacer" };
    public static String[] textureNames = new String[] { "construct", "construct", "construct", "construct", "construct", "construct", "construct", "construct", "arealight", "displacer" };
    @SideOnly(Side.CLIENT)
    protected IIcon sideIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon[] blockIcons;

    public BlockMachine(String name)
    {
        super(Material.iron, name);

        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        setBlockTextureName("construct");
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcons = new IIcon[blockNames.length];

        for (int i = 0; i < blockNames.length; i++) {
            blockIcons[i] = register.registerIcon(Reference.MODID.toLowerCase() + ":" + textureNames[i]);
        }

        sideIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":genericSide");
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < blockNames.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (side != 0 && side != 1) {
            return sideIcon;
        }

        return blockIcons[meta];
    }

    public int damageDropped(int metadata)
    {
        return metadata;
    }

    public TileEntity createNewTileEntity(World world, int metadata)
    {
        switch (metadata) {
            case 0:
                return new TileEntityConstruct();
            case 1:
                return new TileEntityArchive();
            case 2:
                return new TileEntityMachineAssembler();
            case 3:
                return new TileEntityMachineReplicator();
            case 4:
                return new TileEntityMachineAnalysis();
            case 5:
                return new TileEntityMachineProcessor();
            case 6:
                return new TileEntityMachineFurnace();
            case 7:
                return new TileEntityMachineInfuser();
            case 8:
                return new TileEntityMachineAreaLight();
            case 9:
                return new TileEntityMachineDisplacer();
        }

        return null;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public String getName(int metadata)
    {
        return blockNames[metadata];
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tile = world.getTileEntity(x, y, z);
        ITileEntityMachine machine = null;

        if (tile instanceof ITileEntityMachine) {
            machine = (ITileEntityMachine) tile;
        }

        if (machine != null) {
            if (meta == 0) {
                if (machine != null && ((TileEntityConstruct) machine).isBuilding() && rand.nextInt(5) == 0) {
                    double px = x + 0.5D;
                    double py = y + 0.5D;
                    double pz = z + 0.5D;

                    double m = (rand.nextFloat() - 0.5D) * 0.5D;

                    EntityFX effect1 = new EntityPortalFX(world, px + 0.5D, py, pz, +1.0D + m, 0, 0);
                    effect1.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

                    EntityFX effect2 = new EntityPortalFX(world, px - 0.5D, py, pz, -1.0D + m, 0, 0);
                    effect2.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

                    EntityFX effect3 = new EntityPortalFX(world, px, py + 0.5D, pz, 0, 1.0D + m, 0);
                    effect3.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

                    EntityFX effect4 = new EntityPortalFX(world, px, py - 0.5D, pz, 0, 0 - 1.0D + m, 0);
                    effect4.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

                    EntityFX effect5 = new EntityPortalFX(world, px, py, pz + 0.5D, 0, 0, 1.0D + m);
                    effect5.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

                    EntityFX effect6 = new EntityPortalFX(world, px, py, pz - 0.5D, 0, 0, -1.0D + m);
                    effect6.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

                    Minecraft.getMinecraft().effectRenderer.addEffect(effect1);
                    Minecraft.getMinecraft().effectRenderer.addEffect(effect2);
                    Minecraft.getMinecraft().effectRenderer.addEffect(effect3);
                    Minecraft.getMinecraft().effectRenderer.addEffect(effect4);
                    Minecraft.getMinecraft().effectRenderer.addEffect(effect5);
                    Minecraft.getMinecraft().effectRenderer.addEffect(effect6);
                }
            } else if (meta == 3) {
                if (((TileEntityMachineResearch) machine).inProgress() && rand.nextInt(5) == 0) {
                    double d0 = (double) ((float) x + rand.nextFloat());
                    double d1 = (double) ((float) y + rand.nextFloat());
                    double d2 = (double) ((float) z + rand.nextFloat());
                    double d3 = 0.0D;
                    double d4 = 0.0D;
                    double d5 = 0.0D;
                    int i1 = rand.nextInt(2) * 2 - 1;
                    d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
                    d4 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
                    d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;

                    EntityFX effect = new EntityPortalFX(world, d0, d1, d2, d3, d4, d5);
                    effect.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);
                    Minecraft.getMinecraft().effectRenderer.addEffect(effect);
                }
            } else if (meta == 5) {
                if (machine != null && machine.isProcessing()) {
                    int l = world.getBlockMetadata(x, y, z);
                    float f = (float) x + 0.5F;
                    float f1 = (float) y + 0.6F + rand.nextFloat() * 6.0F / 16.0F;
                    float f2 = (float) z;
                    float f3 = 0.52F;
                    float f4 = rand.nextFloat() * 0.6F - 0.3F;

                    if (l == 4) {
                        world.spawnParticle("smoke", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                        world.spawnParticle("flame", (double) (f - f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                    } else if (l == 5) {
                        world.spawnParticle("smoke", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                        world.spawnParticle("flame", (double) (f + f3), (double) f1, (double) (f2 + f4), 0.0D, 0.0D, 0.0D);
                    } else if (l == 2) {
                        world.spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
                        world.spawnParticle("flame", (double) (f + f4), (double) f1, (double) (f2 - f3), 0.0D, 0.0D, 0.0D);
                    } else if (l == 3) {
                        world.spawnParticle("smoke", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
                        world.spawnParticle("flame", (double) (f + f4), (double) f1, (double) (f2 + f3), 0.0D, 0.0D, 0.0D);
                    }
                }
            } else if (meta == 6) {
                if (machine.getProgress() > 0) {
                    for (int l = x - 2; l <= x + 2; ++l) {
                        for (int i1 = z - 2; i1 <= z + 2; ++i1) {
                            if (l > x - 2 && l < x + 2 && i1 == z - 1) {
                                i1 = z + 2;
                            }

                            for (int j1 = y; j1 <= y + 1; ++j1) {
                                if (world.getBlock(l, j1, i1) == Blocks.blockFocusCharger) {
                                    if (!world.isAirBlock((l - x) / 2 + x, j1, (i1 - z) / 2 + z)) {
                                        break;
                                    }

                                    EntityFX effect = new EntityPortalFX(world, (double) x + 0.5D, (double) y + 1.0D, (double) z + 0.5D, (double) ((float) (l - x) + rand.nextFloat()) - 0.5D, (double) ((float) (j1 - y) - rand.nextFloat() - 0.5F), (double) ((float) (i1 - z) + rand.nextFloat()) - 0.5D);
                                    effect.setRBGColorF(228 / 255.0F, 69 / 255.0F, 152 / 255.0F);
                                    Minecraft.getMinecraft().effectRenderer.addEffect(effect);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
