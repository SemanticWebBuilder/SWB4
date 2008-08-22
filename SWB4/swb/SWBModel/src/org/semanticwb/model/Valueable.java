package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Valueable extends GenericObject
{
    public String getValue();
    public void setValue(String value);
}
