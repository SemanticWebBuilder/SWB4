package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class RuleRefBase extends GenericObjectBase 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RuleRefBase(SemanticObject base)
    {
        super(base);
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

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        getSemanticObject().setLongProperty(vocabulary.status, status);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
