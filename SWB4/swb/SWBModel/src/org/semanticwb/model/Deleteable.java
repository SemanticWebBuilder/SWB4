package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Deleteable 
{
    public boolean isDeleted();
    public void setDeleted(boolean deleted);
}
