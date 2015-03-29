package com.ollieread.technomagi.common.misc;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialTechnomagi extends Material
{

    private boolean canBurn;
    private boolean replaceable;
    private boolean isTranslucent;
    private boolean requiresNoTool = true;
    private int mobilityFlag;
    private boolean isAdventureModeExempt;

    public MaterialTechnomagi(MapColor color)
    {
        super(color);
    }

    @Override
    public boolean isLiquid()
    {
        return false;
    }

    @Override
    public boolean isSolid()
    {
        return true;
    }

    @Override
    public boolean getCanBlockGrass()
    {
        return true;
    }

    @Override
    public boolean blocksMovement()
    {
        return true;
    }

    private Material setTranslucent()
    {
        this.isTranslucent = true;
        return this;
    }

    @Override
    protected Material setRequiresTool()
    {
        this.requiresNoTool = false;
        return this;
    }

    @Override
    protected Material setBurning()
    {
        this.canBurn = true;
        return this;
    }

    @Override
    public boolean getCanBurn()
    {
        return this.canBurn;
    }

    @Override
    public Material setReplaceable()
    {
        this.replaceable = true;
        return this;
    }

    @Override
    public boolean isReplaceable()
    {
        return this.replaceable;
    }

    @Override
    public boolean isOpaque()
    {
        return this.isTranslucent ? false : this.blocksMovement();
    }

    @Override
    public boolean isToolNotRequired()
    {
        return this.requiresNoTool;
    }

    @Override
    public int getMaterialMobility()
    {
        return this.mobilityFlag;
    }

    @Override
    protected Material setNoPushMobility()
    {
        this.mobilityFlag = 1;
        return this;
    }

    @Override
    protected Material setImmovableMobility()
    {
        this.mobilityFlag = 2;
        return this;
    }

    @Override
    protected Material setAdventureModeExempt()
    {
        this.isAdventureModeExempt = true;
        return this;
    }

    @Override
    public boolean isAdventureModeExempt()
    {
        return this.isAdventureModeExempt;
    }

}
