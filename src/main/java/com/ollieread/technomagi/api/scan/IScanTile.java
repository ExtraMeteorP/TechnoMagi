package com.ollieread.technomagi.api.scan;

import com.ollieread.technomagi.api.knowledge.research.IResearcher;
import com.ollieread.technomagi.api.scan.ScanHandler.ScanRepresentation;

public interface IScanTile extends IResearcher
{

    public ScanRepresentation getRepresentation();

}
