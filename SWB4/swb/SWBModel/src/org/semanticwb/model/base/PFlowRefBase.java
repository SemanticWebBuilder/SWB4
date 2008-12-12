package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PFlowRefBase extends Reference implements Activeable,Deleteable
{

    public PFlowRefBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_deleted, deleted);
    }

    public void setPflow(org.semanticwb.model.PFlow pflow)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_pflow, pflow.getSemanticObject());
    }

    public void removePflow()
    {
        getSemanticObject().removeProperty(vocabulary.swb_pflow);
    }

    public PFlow getPflow()
    {
         PFlow ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_pflow);
         if(obj!=null)
         {
             ret=(PFlow)vocabulary.swb_PFlow.newGenericInstance(obj);
         }
         return ret;
    }
}
