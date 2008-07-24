package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class CommunityBase extends SemanticObject implements Descriptiveable,Statusable,WebSiteable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public CommunityBase(com.hp.hpl.jena.rdf.model.Resource res)
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
