package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class RuleRefBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RuleRefBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public void setRule(org.semanticwb.model.Rule rule)
    {
        addObjectProperty(vocabulary.rule, rule);
    }

    public void removeRule()
    {
        removeProperty(vocabulary.rule);
    }

    public Rule getRule()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.rule.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Rule> it=new SemanticIterator<org.semanticwb.model.Rule>(Rule.class, stit);
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
}
