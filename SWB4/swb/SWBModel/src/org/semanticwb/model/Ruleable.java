package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Ruleable extends GenericObject
{

    public GenericIterator<org.semanticwb.model.Rule> listRule();

    public void addRule(org.semanticwb.model.Rule rule);

    public void removeAllRule();

    public void removeRule(org.semanticwb.model.Rule rule);

    public Rule getRule();
}
