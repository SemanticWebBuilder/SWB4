package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class ResourceTypeBase extends SemanticObject implements Groupable,WebSiteable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public ResourceTypeBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.ObjectGroup> listObjectGroup()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, stit);
    }

    public void addObjectGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        addObjectProperty(vocabulary.hasGroup, objectgroup);
    }

    public void removeAllObjectGroup()
    {
        getRDFResource().removeAll(vocabulary.hasGroup.getRDFProperty());
    }

    public ObjectGroup getObjectGroup()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
         SemanticIterator<org.semanticwb.model.ObjectGroup> it=new SemanticIterator<org.semanticwb.model.ObjectGroup>(ObjectGroup.class, stit);
         return it.next();
    }
}
