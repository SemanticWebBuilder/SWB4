package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class PortletableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.Portlet> listPortlet()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasPortlet.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Portlet>(org.semanticwb.model.Portlet.class, stit);
    }

    public void addPortlet(org.semanticwb.model.Portlet portlet)
    {
        addObjectProperty(vocabulary.hasPortlet, portlet);
    }

    public void removeAllPortlet()
    {
        getRDFResource().removeAll(vocabulary.hasPortlet.getRDFProperty());
    }

    public Portlet getPortlet()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasPortlet.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Portlet> it=new SemanticIterator<org.semanticwb.model.Portlet>(Portlet.class, stit);
         return it.next();
    }
}
