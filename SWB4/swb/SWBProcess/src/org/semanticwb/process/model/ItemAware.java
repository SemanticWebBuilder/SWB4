package org.semanticwb.process.model;

import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

public class ItemAware extends org.semanticwb.process.model.base.ItemAwareBase 
{
    public ItemAware(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public SemanticClass getItemSemanticClass()
    {
        SemanticClass scls=getDataObjectClass().transformToSemanticClass();
        return scls;
    }
    
}
