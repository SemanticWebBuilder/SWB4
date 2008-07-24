package org.semanticwb.model;

import org.semanticwb.platform.SemanticIterator;
import java.util.Date;
public interface Descriptiveable 
{
    public Date getCreated();
    public void setCreated(Date created);
    public SemanticIterator<org.semanticwb.model.User> listUser();
    public void addUser(org.semanticwb.model.User user);
    public void removeAllUser();
    public User getUser();
    public String getTitle();
    public void setTitle(String title);
    public String getDescription();
    public void setDescription(String description);
    public Date getUpdated();
    public void setUpdated(Date updated);
}
