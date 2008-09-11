package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface XMLable extends GenericObject
{
    public String getXml();
    public void setXml(String xml);
}
