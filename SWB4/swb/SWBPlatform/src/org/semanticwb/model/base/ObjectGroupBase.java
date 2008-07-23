package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class ObjectGroupBase extends SemanticObject implements Groupable,Descriptiveable,WebSiteable
{
    public ObjectGroupBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public Date getCreated()
    {
        return getDateProperty(Vocabulary.created);
    }
    public void setCreated(Date created)
    {
        setDateProperty(Vocabulary.created, created);
    }
    public SemanticIterator<org.semanticwb.model.User> listUser()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.userModified.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.User>(org.semanticwb.model.User.class, stit);
    }
    public void addUser(org.semanticwb.model.User user)
    {
        addObjectProperty(Vocabulary.userModified, user);
    }
    public void removeAllUser()
    {
        getRDFResource().removeAll(Vocabulary.userModified.getRDFProperty());
    }
    public User getUser()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.userModified.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }
    public String getTitle()
    {
        return getProperty(Vocabulary.title);
    }
    public void setTitle(String title)
    {
        setProperty(Vocabulary.title, title);
    }
    public String getDescription()
    {
        return getProperty(Vocabulary.description);
    }
    public void setDescription(String description)
    {
        setProperty(Vocabulary.description, description);
    }
    public SemanticIterator<org.semanticwb.model.ObjectGroup> listObjectGroup()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasGroup.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, stit);
    }
    public void addObjectGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        addObjectProperty(Vocabulary.hasGroup, objectgroup);
    }
    public void removeAllObjectGroup()
    {
        getRDFResource().removeAll(Vocabulary.hasGroup.getRDFProperty());
    }
    public ObjectGroup getObjectGroup()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasGroup.getRDFProperty());
         SemanticIterator<org.semanticwb.model.ObjectGroup> it=new SemanticIterator<org.semanticwb.model.ObjectGroup>(ObjectGroup.class, stit);
         return it.next();
    }
    public Date getUpdated()
    {
        return getDateProperty(Vocabulary.updated);
    }
    public void setUpdated(Date updated)
    {
        setDateProperty(Vocabulary.updated, updated);
    }
}
