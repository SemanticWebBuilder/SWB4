package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class TemplateableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public TemplateableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.Template> listTemplate()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasTemplate.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Template>(org.semanticwb.model.Template.class, stit);
    }

    public void addTemplate(org.semanticwb.model.Template template)
    {
        addObjectProperty(vocabulary.hasTemplate, template);
    }

    public void removeAllTemplate()
    {
        removeProperty(vocabulary.hasTemplate);
    }

    public void removeTemplate(org.semanticwb.model.Template template)
    {
        removeObjectProperty(vocabulary.hasTemplate,template);
    }

    public Template getTemplate()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasTemplate.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Template> it=new SemanticIterator<org.semanticwb.model.Template>(Template.class, stit);
         return it.next();
    }
}
