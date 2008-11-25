package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Iconable extends GenericObject
{
    public String getIconClass();
    public void setIconClass(String iconClass);
}
