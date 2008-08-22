package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Priorityable extends GenericObject
{
    public int getPriority();
    public void setPriority(int priority);
}
