package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PortletRefBase extends Reference implements Deleteable,Activeable,Priorityable
{

    public PortletRefBase(SemanticObject base)
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

    public void setPortlet(org.semanticwb.model.Portlet portlet)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_portlet, portlet.getSemanticObject());
    }

    public void removePortlet()
    {
        getSemanticObject().removeProperty(vocabulary.swb_portlet);
    }

    public Portlet getPortlet()
    {
         Portlet ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_portlet);
         if(obj!=null)
         {
             ret=(Portlet)vocabulary.swb_Portlet.newGenericInstance(obj);
         }
         return ret;
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_priority, priority);
    }
}
