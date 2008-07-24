package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class TemplateRefBase extends SemanticObject implements Templateable,Priorityable,Statusable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public TemplateRefBase(com.hp.hpl.jena.rdf.model.Resource res)
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
        getRDFResource().removeAll(vocabulary.hasTemplate.getRDFProperty());
    }

    public Template getTemplate()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasTemplate.getRDFProperty());
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
