package org.semanticwb.process.model;

import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;


public class ItemAware extends org.semanticwb.process.model.base.ItemAwareBase 
{
    public ItemAware(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static SemanticClass getSemanticClass(ItemAware item)
    {
        if(item instanceof DataStore)
        {
            DataStore store=((DataStore)item);

            SemanticObject obj=store.getDataStoreClass();
            if(obj!=null)
            {
                return obj.transformToSemanticClass();
            }
        }else
        {
            //TODO:
        }
        return null;
    }
}
