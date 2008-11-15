package org.semanticwb.content;

import org.semanticwb.model.base.*;
import org.semanticwb.model.*;
import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Descriptiveable extends GenericObject
{
    public String getTitle();
    public void setTitle(String title);
    public String getDescription();
    public void setDescription(String description);
}
