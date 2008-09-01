package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Hiddenable extends GenericObject
{
    public boolean isHidden();
    public void setHidden(boolean hidden);
}
