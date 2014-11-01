package com.ollieread.technomagi.tileentity;

public class TileEntityGeneratorLife extends TileEntityGenerator
{

    public TileEntityGeneratorLife()
    {
        super(3200, 10, 0);
    }

    @Override
    public void updateEntity()
    {
        return;
        /*
         * if (!worldObj.isRemote) { boolean flag =
         * (energy.getMaxEnergyStored(null) > energy.getEnergyStored(null)) &&
         * energyGeneration > 0 && energyTicks > 0;
         * 
         * if (flag) {
         * 
         * for (int i = 0; i < 4; ++i) { int x = xCoord +
         * worldObj.rand.nextInt(3) - 1; int y = yCoord +
         * worldObj.rand.nextInt(5) - 3; int z = zCoord +
         * worldObj.rand.nextInt(3) - 1; Block block = worldObj.getBlock(x, y +
         * 1, z);
         * 
         * if (block instanceof BlockGrass) { energyGeneration = 4;
         * worldObj.setBlock(x, y, z, Blocks.dirt); break; } else if (block
         * instanceof BlockLeaves) { energyGeneration = 6;
         * worldObj.setBlockToAir(x, y, z); break; } else if (block instanceof
         * BlockTallGrass) { energyGeneration = 8; worldObj.setBlockToAir(x, y,
         * z); break; } else if (block instanceof BlockFlower) {
         * energyGeneration = 4; worldObj.setBlockToAir(x, y, z); break; } }
         * 
         * if (energyGeneration > 0) {
         * energy.increaseEnergyStored(energyGeneration);
         * 
         * sync(); }
         * 
         * energyGeneration = 0; } }
         */
    }

    @Override
    public boolean canGenerate()
    {
        return true;
    }

}
