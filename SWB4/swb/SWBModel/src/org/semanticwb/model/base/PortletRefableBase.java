package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class PortletRefableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletRefableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.PortletRef> listPortletRef()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasPortletRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.PortletRef>(org.semanticwb.model.PortletRef.class, stit);
    }

    public void addPortletRef(org.semanticwb.model.PortletRef portletref)
    {
        addObjectProperty(vocabulary.hasPortletRef, portletref);
    }

    public void removeAllPortletRef()
    {
        getRDFResource().removeAll(vocabulary.hasPortletRef.getRDFProperty());
    }

    public PortletRef getPortletRef()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasPortletRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.PortletRef> it=new SemanticIterator<org.semanticwb.model.PortletRef>(PortletRef.class, stit);
         return it.next();
    }
}
