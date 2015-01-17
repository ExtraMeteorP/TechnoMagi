package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.tileentity.abstracts.Generator;
import com.ollieread.technomagi.util.PowerHelper;

public class TileEntityGeneratorSolar extends Generator
{

    public int field_145926_a;
    public float field_145933_i;
    public float field_145931_j;
    public float field_145932_k;
    public float field_145929_l;
    public float field_145930_m;
    public float field_145927_n;
    public float field_145928_o;
    public float field_145925_p;
    public float field_145924_q;
    private static Random field_145923_r = new Random();

    public TileEntityGeneratorSolar()
    {
        super(Config.generatorLightPowerMax, Config.generatorLightPowerOutput, Config.generatorLightGeneration, Config.generatorLightMaxTicks);
    }

    @Override
    public boolean canGenerate()
    {
        return worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) && worldObj.isDaytime();
    }

    public void updateEntity()
    {
        super.updateEntity();

        if (!worldObj.isRemote) {
            PowerHelper.pushToAdjacent(this, ForgeDirection.DOWN);
        }

        this.field_145927_n = this.field_145930_m;
        this.field_145925_p = this.field_145928_o;
        this.field_145924_q += 0.02F;
        this.field_145930_m -= 0.1F;

        while (this.field_145928_o >= (float) Math.PI) {
            this.field_145928_o -= ((float) Math.PI * 2F);
        }

        while (this.field_145928_o < -(float) Math.PI) {
            this.field_145928_o += ((float) Math.PI * 2F);
        }

        while (this.field_145924_q >= (float) Math.PI) {
            this.field_145924_q -= ((float) Math.PI * 2F);
        }

        while (this.field_145924_q < -(float) Math.PI) {
            this.field_145924_q += ((float) Math.PI * 2F);
        }

        float f2;

        for (f2 = this.field_145924_q - this.field_145928_o; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
            ;
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        this.field_145928_o += f2 * 0.4F;

        if (this.field_145930_m < 0.0F) {
            this.field_145930_m = 0.0F;
        }

        if (this.field_145930_m > 1.0F) {
            this.field_145930_m = 1.0F;
        }

        ++this.field_145926_a;
        this.field_145931_j = this.field_145933_i;
        float f = (this.field_145932_k - this.field_145933_i) * 0.4F;
        float f3 = 0.2F;

        if (f < -f3) {
            f = -f3;
        }

        if (f > f3) {
            f = f3;
        }

        this.field_145929_l += (f - this.field_145929_l) * 0.9F;
        this.field_145933_i += this.field_145929_l;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.DOWN);
    }

}
