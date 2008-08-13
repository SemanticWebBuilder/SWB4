package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class PortletRefBase extends SemanticObject implements Priorityable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletRefBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public void setPortlet(org.semanticwb.model.Portlet portlet)
    {
        addObjectProperty(vocabulary.portlet, portlet);
    }

    public void removePortlet()
    {
        removeProperty(vocabulary.portlet);
    }

    public Portlet getPortlet()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.portlet.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Portlet> it=new SemanticIterator<org.semanticwb.model.Portlet>(Portlet.class, stit);
         return it.next();
    }

    public int getStatus()
    {
        return getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        setLongProperty(vocabulary.status, status);
    }

    public int getPriority()
    {
        return getIntProperty(vocabulary.priority);
    }

    public void setPriority(int priority)
    {
        setLongProperty(vocabulary.priority, priority);
    }
}
