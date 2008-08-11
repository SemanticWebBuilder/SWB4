package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class RuleRefableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public RuleRefableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public SemanticIterator<org.semanticwb.model.RuleRef> listRuleRef()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRuleRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, stit);
    }

    public void addRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        addObjectProperty(vocabulary.hasRuleRef, ruleref);
    }

    public void removeAllRuleRef()
    {
        removeProperty(vocabulary.hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        removeObjectProperty(vocabulary.hasRuleRef,ruleref);
    }

    public RuleRef getRuleRef()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRuleRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.RuleRef> it=new SemanticIterator<org.semanticwb.model.RuleRef>(RuleRef.class, stit);
         return it.next();
    }
}
