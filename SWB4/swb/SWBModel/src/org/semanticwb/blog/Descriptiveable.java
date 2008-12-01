package org.semanticwb.blog;

import org.semanticwb.model.base.*;
import org.semanticwb.model.*;
import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Descriptiveable extends GenericObject
{
    public String getDescription();
    public void setDescription(String description);
    public String getName();
    public void setName(String name);
}
