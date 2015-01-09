package com.ollieread.technomagi.item.augments;

import java.util.ArrayList;
import java.util.List;

public class AugmentRegistry
{

    public static List<IAugment> augmentList = new ArrayList<IAugment>();

    public static int addAugment(IAugment augment)
    {
        if (!augmentList.contains(augment)) {
            augmentList.add(augment);

            return augmentList.indexOf(augment);
        }

        return 0;
    }

    public static IAugment getAugment(int id)
    {
        return augmentList.get(id);
    }

    public static enum AugmentType {
        Cerebral, Internal, External
    }

}
