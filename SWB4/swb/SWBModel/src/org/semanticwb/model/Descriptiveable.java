package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Descriptiveable 
{
    public Date getCreated();
    public void setCreated(Date created);

    public void setUserModified(org.semanticwb.model.User user);

    public void removeUserModified();

    public User getUserModified();
    public String getTitle();
    public void setTitle(String title);
    public String getDescription();
    public void setDescription(String description);

    public void setUserCreated(org.semanticwb.model.User user);

    public void removeUserCreated();

    public User getUserCreated();
    public Date getUpdated();
    public void setUpdated(Date updated);
}
