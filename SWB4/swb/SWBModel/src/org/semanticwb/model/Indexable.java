package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Indexable extends GenericObject
{
    public boolean isIndexable();
    public void setIndexable(boolean indexable);
}
