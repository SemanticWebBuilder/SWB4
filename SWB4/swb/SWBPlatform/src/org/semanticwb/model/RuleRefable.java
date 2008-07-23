package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface RuleRefable 
{
    public SemanticIterator<org.semanticwb.model.RuleRef> listRuleRef();
    public void addRuleRef(org.semanticwb.model.RuleRef ruleref);
    public void removeAllRuleRef();
    public RuleRef getRuleRef();
}
