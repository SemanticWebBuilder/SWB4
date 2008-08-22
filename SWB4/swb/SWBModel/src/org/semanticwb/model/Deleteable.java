package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Deleteable extends GenericObject
{
    public boolean isDeleted();
    public void setDeleted(boolean deleted);
}
