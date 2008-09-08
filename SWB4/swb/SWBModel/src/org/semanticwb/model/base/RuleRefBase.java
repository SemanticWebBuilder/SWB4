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
        getSemanticObject().setObjectProperty(vocabulary.rule, rule.getSemanticObject());
    }

    public void removeRule()
    {
        getSemanticObject().removeProperty(vocabulary.rule);
    }

    public Rule getRule()
    {
         Rule ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.rule);
         if(obj!=null)
         {
             ret=(Rule)vocabulary.Rule.newGenericInstance(obj);
         }
         return ret;
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
