package com.ollieread.technomagi.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import com.ollieread.technomagi.client.model.ModelMachineConstruct;

public class RenderConstructItem implements IItemRenderer
{

    protected ModelMachineConstruct model;

    public RenderConstructItem()
    {
        model = new ModelMachineConstruct();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.INVENTORY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.ENTITY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        IItemRenderer itemRenderer = null;

        switch (item.getItemDamage()) {
            case 0:
                itemRenderer = new RenderArchiveItem();
                break;
            case 1:
                itemRenderer = new RenderReplicatorItem();
                break;
            case 2:
                itemRenderer = new RenderCraftingItem();
                break;
            case 3:
                itemRenderer = new RenderAnalysisItem();
                break;
            case 4:
                itemRenderer = new RenderSeparatorItem();
                break;
            case 5:
                itemRenderer = new RenderFurnaceItem();
                break;
            case 6:
                itemRenderer = new RenderFocuserItem();
                break;
        }

        if (itemRenderer != null) {
            itemRenderer.renderItem(type, item, data);
        }
    }

}
