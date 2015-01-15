package com.ollieread.technomagi.asm;

import java.util.Map;

import com.ollieread.technomagi.asm.transformers.BlockTransformer;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({ "com.ollieread.technomagi.asm" })
@MCVersion(value = "1.7.10")
public class TechnoMagiCoreLoadingPlugin implements IFMLLoadingPlugin
{

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] { BlockTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass()
    {
        return TechnoMagiCore.class.getName();
    }

    @Override
    public String getSetupClass()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getAccessTransformerClass()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
