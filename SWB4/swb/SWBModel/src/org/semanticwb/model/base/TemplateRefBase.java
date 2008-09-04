package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class TemplateRefBase extends GenericObjectBase implements Activeable,Priorityable,Templateable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public TemplateRefBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public void setTemplate(org.semanticwb.model.Template template)
    {
        getSemanticObject().addObjectProperty(vocabulary.template, template.getSemanticObject());
    }

    public void removeTemplate()
    {
        getSemanticObject().removeProperty(vocabulary.template);
    }

    public Template getTemplate()
    {
         Template ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.template.getRDFProperty());
         GenericIterator<org.semanticwb.model.Template> it=new GenericIterator<org.semanticwb.model.Template>(Template.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.priority, priority);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
