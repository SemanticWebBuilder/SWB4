package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PortletRefBase extends GenericObjectBase implements Statusable,Priorityable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletRefBase(SemanticObject base)
    {
        super(base);
    }

    public void setPortlet(org.semanticwb.model.Portlet portlet)
    {
        getSemanticObject().addObjectProperty(vocabulary.portlet, portlet.getSemanticObject());
    }

    public void removePortlet()
    {
        getSemanticObject().removeProperty(vocabulary.portlet);
    }

    public Portlet getPortlet()
    {
         Portlet ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.portlet.getRDFProperty());
         GenericIterator<org.semanticwb.model.Portlet> it=new GenericIterator<org.semanticwb.model.Portlet>(Portlet.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        getSemanticObject().setLongProperty(vocabulary.status, status);
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.priority, priority);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
