package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PortletRefBase extends ReferenceBase implements Priorityable,Activeable,Deleteable
{

    public PortletRefBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.deleted, deleted);
    }

    public void setPortlet(org.semanticwb.model.Portlet portlet)
    {
        getSemanticObject().setObjectProperty(vocabulary.portlet, portlet.getSemanticObject());
    }

    public void removePortlet()
    {
        getSemanticObject().removeProperty(vocabulary.portlet);
    }

    public Portlet getPortlet()
    {
         Portlet ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.portlet);
         if(obj!=null)
         {
             ret=(Portlet)vocabulary.swb_Portlet.newGenericInstance(obj);
         }
         return ret;
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.priority, priority);
    }
}
