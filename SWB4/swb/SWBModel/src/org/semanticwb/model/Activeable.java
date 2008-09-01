package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Activeable extends GenericObject
{
    public boolean isActive();
    public void setActive(boolean active);
}
