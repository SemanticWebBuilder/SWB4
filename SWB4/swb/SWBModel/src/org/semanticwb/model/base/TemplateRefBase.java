package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class TemplateRefBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public TemplateRefBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public void setTemplate(org.semanticwb.model.Template template)
    {
        addObjectProperty(vocabulary.template, template);
    }

    public void removeTemplate()
    {
        removeProperty(vocabulary.template);
    }

    public Template getTemplate()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.template.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Template> it=new SemanticIterator<org.semanticwb.model.Template>(Template.class, stit);
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
