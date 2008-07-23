package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class RuleRefBase extends SemanticObject implements Ruleable,Statusable
{
    public RuleRefBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public int getStatus()
    {
        return getIntProperty(Vocabulary.status);
    }
    public void setStatus(int status)
    {
        setLongProperty(Vocabulary.status, status);
    }
    public SemanticIterator<org.semanticwb.model.Rule> listRule()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRule.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Rule>(org.semanticwb.model.Rule.class, stit);
    }
    public void addRule(org.semanticwb.model.Rule rule)
    {
        addObjectProperty(Vocabulary.hasRule, rule);
    }
    public void removeAllRule()
    {
        getRDFResource().removeAll(Vocabulary.hasRule.getRDFProperty());
    }
    public Rule getRule()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRule.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Rule> it=new SemanticIterator<org.semanticwb.model.Rule>(Rule.class, stit);
         return it.next();
    }
}
