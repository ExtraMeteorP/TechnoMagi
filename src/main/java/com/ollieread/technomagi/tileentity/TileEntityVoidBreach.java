package com.ollieread.technomagi.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import scala.actors.threadpool.Arrays;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;

public class TileEntityVoidBreach extends TileEntityTM
{

    protected int stage = 0;
    protected int ticks = 0;
    protected int maxTicks = 0;
    protected int spreadChance = 1;
    protected int stageChance = 1;
    protected int spreadStage = 0;
    protected Random rand = new Random();
    protected List<ItemStack> nonReplaceable;

    public TileEntityVoidBreach()
    {
        maxTicks = Config.voidBreachMaxTicks;
        spreadChance = Config.voidBreachSpreadChance;
        stageChance = Config.voidBreachStageChance;
        spreadStage = Config.voidBreachStageSpread;
        nonReplaceable = Arrays.asList(Config.voidBreachImmune);
    }

    protected boolean canReplace(Block block, int meta)
    {
        return !nonReplaceable.contains(new ItemStack(Item.getItemFromBlock(block), 1, meta));
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            ticks++;

            if (ticks >= maxTicks) {
                ticks = 0;

                if (stage < 9) {
                    if (rand.nextInt(stageChance) == 0) {
                        stage++;
                        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, stage, 2);
                        sync();
                    }
                } else {
                    spread();
                }
            }
        }
    }

    private void spread()
    {
        if (stage >= spreadStage) {
            if (rand.nextInt(spreadChance) == 0) {
                ForgeDirection direction = ForgeDirection.getOrientation(rand.nextInt(6));

                int x = xCoord + direction.offsetX;
                int y = yCoord + direction.offsetY;
                int z = zCoord + direction.offsetZ;

                if (canReplace(worldObj.getBlock(x, y, z), worldObj.getBlockMetadata(x, y, z))) {
                    worldObj.setBlock(x, y, z, Blocks.blockVoidBreach, 0, 2);
                }
            }
        }
    }

    public int getStage()
    {
        return stage;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        stage = compound.getInteger("Stage");
        ticks = compound.getInteger("Ticks");
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Stage", stage);
        compound.setInteger("Ticks", ticks);
    }

}
