package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class WebSiteBase extends SemanticObject implements Statusable,Deleteable,Descriptiveable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebSiteBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public Date getCreated()
    {
        return getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        setDateProperty(vocabulary.created, created);
    }

    public boolean isDeleted()
    {
        return getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        setBooleanProperty(vocabulary.deleted, deleted);
    }

    public SemanticIterator<org.semanticwb.model.User> listUser()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.userModified.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.User>(org.semanticwb.model.User.class, stit);
    }

    public void addUser(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userModified, user);
    }

    public void removeAllUser()
    {
        getRDFResource().removeAll(vocabulary.userModified.getRDFProperty());
    }

    public User getUser()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userModified.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.HomePage> listHomePage()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasHome.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.HomePage>(org.semanticwb.model.HomePage.class, stit);
    }

    public void addHomePage(org.semanticwb.model.HomePage homepage)
    {
        addObjectProperty(vocabulary.hasHome, homepage);
    }

    public void removeAllHomePage()
    {
        getRDFResource().removeAll(vocabulary.hasHome.getRDFProperty());
    }

    public HomePage getHomePage()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasHome.getRDFProperty());
         SemanticIterator<org.semanticwb.model.HomePage> it=new SemanticIterator<org.semanticwb.model.HomePage>(HomePage.class, stit);
         return it.next();
    }

    public String getTitle()
    {
        return getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        setProperty(vocabulary.title, title);
    }

    public String getDescription()
    {
        return getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        setProperty(vocabulary.description, description);
    }

    public int getStatus()
    {
        return getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        setLongProperty(vocabulary.status, status);
    }

    public Date getUpdated()
    {
        return getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        setDateProperty(vocabulary.updated, updated);
    }
}
