package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Traceable extends GenericObject
{
    public Date getCreated();
    public void setCreated(Date created);
}
