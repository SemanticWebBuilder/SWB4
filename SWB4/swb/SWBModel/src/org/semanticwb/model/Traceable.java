package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Traceable extends GenericObject
{
    public Date getCreated();
    public void setCreated(Date created);

    public void setModifiedBy(org.semanticwb.model.User user);

    public void removeModifiedBy();

    public User getModifiedBy();
    public Date getUpdated();
    public void setUpdated(Date updated);

    public void setCreator(org.semanticwb.model.User user);

    public void removeCreator();

    public User getCreator();
}
