package com.ollieread.technomagi.item.nanites;

import java.util.ArrayList;
import java.util.List;

public class NaniteRegistry
{

    public static List<INanites> nanitesList = new ArrayList<INanites>();

    public static int addNanites(INanites nanites)
    {
        if (!nanitesList.contains(nanites)) {
            nanitesList.add(nanites);

            return nanitesList.indexOf(nanites);
        }

        return 0;
    }

    public static INanites getNanites(int id)
    {
        return nanitesList.get(id);
    }

}
