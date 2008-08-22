package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Statusable extends GenericObject
{
    public int getStatus();
    public void setStatus(int status);
}
