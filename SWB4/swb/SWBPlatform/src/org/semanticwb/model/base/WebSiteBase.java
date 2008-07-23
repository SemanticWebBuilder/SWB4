package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class WebSiteBase extends SemanticObject implements Statusable,Deleteable,Descriptiveable
{
    public WebSiteBase(com.hp.hpl.jena.rdf.model.Resource res)
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
    public boolean isDeleted()
    {
        return getBooleanProperty(Vocabulary.deleted);
    }
    public void setDeleted(boolean deleted)
    {
        setBooleanProperty(Vocabulary.deleted, deleted);
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
    public SemanticIterator<org.semanticwb.model.HomePage> listHomePage()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasHome.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.HomePage>(org.semanticwb.model.HomePage.class, stit);
    }
    public void addHomePage(org.semanticwb.model.HomePage homepage)
    {
        addObjectProperty(Vocabulary.hasHome, homepage);
    }
    public void removeAllHomePage()
    {
        getRDFResource().removeAll(Vocabulary.hasHome.getRDFProperty());
    }
    public HomePage getHomePage()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasHome.getRDFProperty());
         SemanticIterator<org.semanticwb.model.HomePage> it=new SemanticIterator<org.semanticwb.model.HomePage>(HomePage.class, stit);
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
    public int getStatus()
    {
        return getIntProperty(Vocabulary.status);
    }
    public void setStatus(int status)
    {
        setLongProperty(Vocabulary.status, status);
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
