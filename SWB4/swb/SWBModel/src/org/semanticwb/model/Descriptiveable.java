package org.semanticwb.model;

import org.semanticwb.model.GenericIterator;
import java.util.Date;
public interface Descriptiveable extends GenericObject
{
    public Date getCreated();
    public void setCreated(Date created);

    public void setModifiedBy(org.semanticwb.model.User user);

    public void removeModifiedBy();

    public User getModifiedBy();

    public void setCreator(org.semanticwb.model.User user);

    public void removeCreator();

    public User getCreator();
    public String getTitle();
    public void setTitle(String title);
    public String getDescription();
    public void setDescription(String description);
    public Date getUpdated();
    public void setUpdated(Date updated);
}
