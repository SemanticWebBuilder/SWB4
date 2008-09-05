package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface RuleRefable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs();

    public void addRuleRef(org.semanticwb.model.RuleRef ruleref);

    public void removeAllRuleRef();

    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref);

    public RuleRef getRuleRef();
}
