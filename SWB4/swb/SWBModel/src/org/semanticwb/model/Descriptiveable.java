package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Descriptiveable extends GenericObject
{
    public String getTitle();
    public void setTitle(String title);
    public String getTitle(String lang);
    public void setTitle(String title, String lang);
    public String getDescription();
    public void setDescription(String description);
    public String getDescription(String lang);
    public void setDescription(String description, String lang);
}
