package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Ruleable 
{
    public SemanticIterator<org.semanticwb.model.Rule> listRule();
    public void addRule(org.semanticwb.model.Rule rule);
    public void removeAllRule();
    public Rule getRule();
}
