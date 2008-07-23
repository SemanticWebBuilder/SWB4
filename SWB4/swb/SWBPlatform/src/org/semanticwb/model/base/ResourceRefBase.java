package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class ResourceRefBase extends SemanticObject implements Resourceable,Priorityable,Statusable
{
    public ResourceRefBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public SemanticIterator<org.semanticwb.model.Resource> listResource()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasResource.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Resource>(org.semanticwb.model.Resource.class, stit);
    }
    public void addResource(org.semanticwb.model.Resource resource)
    {
        addObjectProperty(Vocabulary.hasResource, resource);
    }
    public void removeAllResource()
    {
        getRDFResource().removeAll(Vocabulary.hasResource.getRDFProperty());
    }
    public Resource getResource()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasResource.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Resource> it=new SemanticIterator<org.semanticwb.model.Resource>(Resource.class, stit);
         return it.next();
    }
    public int getStatus()
    {
        return getIntProperty(Vocabulary.status);
    }
    public void setStatus(int status)
    {
        setLongProperty(Vocabulary.status, status);
    }
    public int getPriority()
    {
        return getIntProperty(Vocabulary.priority);
    }
    public void setPriority(int priority)
    {
        setLongProperty(Vocabulary.priority, priority);
    }
}
