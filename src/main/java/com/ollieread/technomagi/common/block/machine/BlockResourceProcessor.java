package com.ollieread.technomagi.common.block.machine;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessor;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorBasic;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorElectric;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorNanite;
import com.ollieread.technomagi.util.ResourceHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockResourceProcessor extends BlockContainerSubtypes
{

    protected IInventory inventory;
    @SideOnly(Side.CLIENT)
    protected IIcon basicIcons[];
    @SideOnly(Side.CLIENT)
    protected IIcon electricIcons[];
    @SideOnly(Side.CLIENT)
    protected IIcon naniteIcons[];

    public BlockResourceProcessor(String name)
    {
        super(name, new String[] { "basic", "electric", "nanite" }, Material.rock);

        this.setHardness(3.5F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        if (metadata == 0) {
            return new TileResourceProcessorBasic();
        } else if (metadata == 1) {
            return new TileResourceProcessorElectric();
        } else if (metadata == 2) {
            return new TileResourceProcessorNanite();
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.basicIcons = new IIcon[4];
        this.basicIcons[0] = register.registerIcon(ResourceHelper.icon("machine/basic/processor_front"));
        this.basicIcons[1] = register.registerIcon(ResourceHelper.icon("machine/basic/generator_off"));
        this.basicIcons[2] = register.registerIcon(ResourceHelper.icon("machine/basic/generator_on"));
        this.basicIcons[3] = register.registerIcon(ResourceHelper.icon("machine/basic/generic_side"));

        this.electricIcons = new IIcon[4];
        this.electricIcons[0] = register.registerIcon(ResourceHelper.icon("machine/electric/processor_front"));
        this.electricIcons[1] = register.registerIcon(ResourceHelper.icon("machine/electric/processor_side"));
        this.electricIcons[2] = register.registerIcon(ResourceHelper.icon("machine/electric/generic_side"));
        this.electricIcons[3] = register.registerIcon(ResourceHelper.icon("machine/electric/generic_back"));

        this.naniteIcons = new IIcon[4];
        this.naniteIcons[0] = register.registerIcon(ResourceHelper.icon("machine/nanite/processor_front"));
        this.naniteIcons[1] = register.registerIcon(ResourceHelper.icon("machine/nanite/processor_side"));
        this.naniteIcons[2] = register.registerIcon(ResourceHelper.icon("machine/nanite/generic_side"));
        this.naniteIcons[3] = register.registerIcon(ResourceHelper.icon("machine/nanite/generic_back"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata == 0) {
            if (side == 0 || side == 1) {
                return this.basicIcons[3];
            } else if (side == 4) {
                return this.basicIcons[0];
            } else if (side == 5) {
                return this.basicIcons[3];
            }

            return this.basicIcons[1];
        } else if (metadata == 1) {
            if (side == 0 || side == 1) {
                return this.electricIcons[2];
            } else if (side == 4) {
                return this.electricIcons[0];
            } else if (side == 5) {
                return this.electricIcons[3];
            }

            return this.electricIcons[1];
        } else {
            if (side == 0 || side == 1) {
                return this.naniteIcons[2];
            } else if (side == 4) {
                return this.naniteIcons[0];
            } else if (side == 5) {
                return this.naniteIcons[3];
            }

            return this.naniteIcons[1];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata == 0) {
            if (side == 0 || side == 1) {
                return this.basicIcons[3];
            } else {
                TileResourceProcessor tile = (TileResourceProcessor) world.getTileEntity(x, y, z);

                if (tile != null && tile.getDirection() != null) {
                    if (tile.getDirection().ordinal() == side) {
                        return this.basicIcons[0];
                    } else if (tile.getDirection().getOpposite().ordinal() == side) {
                        return this.basicIcons[3];
                    } else {
                        if (tile.isProcessing()) {
                            return this.basicIcons[2];
                        }
                    }
                }
            }

            return this.basicIcons[1];
        } else if (metadata == 1) {
            if (side == 0 || side == 1) {
                return this.electricIcons[2];
            } else {
                TileResourceProcessor tile = (TileResourceProcessor) world.getTileEntity(x, y, z);

                if (tile != null && tile.getDirection() != null) {
                    if (tile.getDirection().ordinal() == side) {
                        return this.electricIcons[0];
                    } else if (tile.getDirection().getOpposite().ordinal() == side) {
                        return this.electricIcons[3];
                    }
                }
            }

            return this.electricIcons[1];
        } else {
            if (side == 0 || side == 1) {
                return this.naniteIcons[2];
            } else {
                TileResourceProcessor tile = (TileResourceProcessor) world.getTileEntity(x, y, z);

                if (tile != null && tile.getDirection() != null) {
                    if (tile.getDirection().ordinal() == side) {
                        return this.naniteIcons[0];
                    } else if (tile.getDirection().getOpposite().ordinal() == side) {
                        return this.naniteIcons[3];
                    }
                }
            }

            return this.naniteIcons[1];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        TileResourceProcessor processor = (TileResourceProcessor) world.getTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);

        if (processor.isProcessing()) {
            int l = processor.getDirection().ordinal();
            float f = x + 0.5F;
            float f1 = y + 0.35F + rand.nextFloat() * 6.0F / 16.0F;
            float f2 = z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.6F - 0.3F;

            if (metadata == 0) {
                if (l == 4) {
                    world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);

                    world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                } else if (l == 5) {
                    world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);

                    world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                } else if (l == 2) {
                    world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);

                    world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                } else if (l == 3) {
                    world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);

                    world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                }
            } else {
                if (l == 4) {
                    world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                } else if (l == 5) {
                    world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                } else if (l == 2) {
                    world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                } else if (l == 3) {
                    world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                    world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
