package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Localeable extends GenericObject
{
    public String getLanguage();
    public void setLanguage(String language);
}
