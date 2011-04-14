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
        SemanticClass scls=null;
        if(this instanceof DataStore)
        {
            DataStore store=(DataStore)this;
            if(store.getDataStoreClass()!=null)
            {
                scls=store.getDataStoreClass().transformToSemanticClass();
            }
        }else if(this instanceof DataObjectItemAware)
        {
            DataObjectItemAware data=(DataObjectItemAware)this;
            if(data.getDataObjectClass()!=null)
            {
                scls=data.getDataObjectClass().transformToSemanticClass();
            }
        }
        return scls;
    }

    public SemanticProperty getItemSemanticProperty()
    {
        SemanticProperty sprop=null;
        if(this instanceof DataObjectItemAware)
        {
            DataObjectItemAware data=(DataObjectItemAware)this;
            if(data.getDataObjectProperty()!=null)
            {
                sprop=data.getDataObjectProperty().transformToSemanticProperty();
            }
        }
        return sprop;
    }
    
}
