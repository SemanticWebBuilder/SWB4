package org.semanticwb.process.model;

import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;

public class ItemAware extends org.semanticwb.process.model.base.ItemAwareBase 
{
    public ItemAware(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Regresa la SemanticClass relacionada al objeto ItemAware
     * @return
     */
    public SemanticClass getItemSemanticClass()
    {
        SemanticObject obj=getDataObjectClass();
        SemanticClass scls=null;
        if(obj!=null){
            scls=obj.transformToSemanticClass();
        }
        return scls;
    }
    
}
