package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class RuleRefBase extends ReferenceBase implements Activeable
{

    public RuleRefBase(SemanticObject base)
    {
        super(base);
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
             ret=(Rule)vocabulary.swb_Rule.newGenericInstance(obj);
         }
         return ret;
    }
}
