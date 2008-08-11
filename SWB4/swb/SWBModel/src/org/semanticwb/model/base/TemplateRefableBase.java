package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class TemplateRefableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public TemplateRefableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.TemplateRef> listTemplateRef()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasTemplateRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.TemplateRef>(org.semanticwb.model.TemplateRef.class, stit);
    }

    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        addObjectProperty(vocabulary.hasTemplateRef, templateref);
    }

    public void removeAllTemplateRef()
    {
        removeProperty(vocabulary.hasTemplateRef);
    }

    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        removeObjectProperty(vocabulary.hasTemplateRef,templateref);
    }

    public TemplateRef getTemplateRef()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasTemplateRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.TemplateRef> it=new SemanticIterator<org.semanticwb.model.TemplateRef>(TemplateRef.class, stit);
         return it.next();
    }
}
