package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Sortable extends GenericObject
{
    public int getIndex();
    public void setIndex(int index);
}
