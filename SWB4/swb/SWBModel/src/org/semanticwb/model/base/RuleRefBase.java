package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class RuleRefBase extends GenericObjectBase implements Activeable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RuleRefBase(SemanticObject base)
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

    public void setRule(org.semanticwb.model.Rule rule)
    {
        getSemanticObject().addObjectProperty(vocabulary.rule, rule.getSemanticObject());
    }

    public void removeRule()
    {
        getSemanticObject().removeProperty(vocabulary.rule);
    }

    public Rule getRule()
    {
         Rule ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.rule.getRDFProperty());
         GenericIterator<org.semanticwb.model.Rule> it=new GenericIterator<org.semanticwb.model.Rule>(Rule.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
