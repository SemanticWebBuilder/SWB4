package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class RuleableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RuleableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.Rule> listRule()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRule.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Rule>(org.semanticwb.model.Rule.class, stit);
    }

    public void addRule(org.semanticwb.model.Rule rule)
    {
        addObjectProperty(vocabulary.hasRule, rule);
    }

    public void removeAllRule()
    {
        removeProperty(vocabulary.hasRule);
    }

    public void removeRule(org.semanticwb.model.Rule rule)
    {
        removeObjectProperty(vocabulary.hasRule,rule);
    }

    public Rule getRule()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRule.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Rule> it=new SemanticIterator<org.semanticwb.model.Rule>(Rule.class, stit);
         return it.next();
    }
}
