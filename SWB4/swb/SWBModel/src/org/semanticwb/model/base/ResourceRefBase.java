package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class ResourceRefBase extends SemanticObject implements Resourceable,Priorityable,Statusable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public ResourceRefBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.Resource> listResource()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasResource.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Resource>(org.semanticwb.model.Resource.class, stit);
    }

    public void addResource(org.semanticwb.model.Resource resource)
    {
        addObjectProperty(vocabulary.hasResource, resource);
    }

    public void removeAllResource()
    {
        getRDFResource().removeAll(vocabulary.hasResource.getRDFProperty());
    }

    public Resource getResource()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasResource.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Resource> it=new SemanticIterator<org.semanticwb.model.Resource>(Resource.class, stit);
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
