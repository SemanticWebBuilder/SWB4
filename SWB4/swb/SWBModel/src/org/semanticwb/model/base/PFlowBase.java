package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class PFlowBase extends SemanticObject implements Descriptiveable,Statusable,Versionable,Deleteable,WebSiteable,Groupable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PFlowBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public boolean isDeleted()
    {
        return getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        setBooleanProperty(vocabulary.deleted, deleted);
    }

    public Date getCreated()
    {
        return getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        setDateProperty(vocabulary.created, created);
    }

    public String getTitle()
    {
        return getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        setProperty(vocabulary.title, title);
    }

    public SemanticIterator<org.semanticwb.model.VersionInfo> listVersionInfo()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.actualVersion.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.VersionInfo>(org.semanticwb.model.VersionInfo.class, stit);
    }

    public void addVersionInfo(org.semanticwb.model.VersionInfo versioninfo)
    {
        addObjectProperty(vocabulary.actualVersion, versioninfo);
    }

    public void removeAllVersionInfo()
    {
        getRDFResource().removeAll(vocabulary.actualVersion.getRDFProperty());
    }

    public VersionInfo getVersionInfo()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.actualVersion.getRDFProperty());
         SemanticIterator<org.semanticwb.model.VersionInfo> it=new SemanticIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.User> listUser()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.userCreated.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.User>(org.semanticwb.model.User.class, stit);
    }

    public void addUser(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userCreated, user);
    }

    public void removeAllUser()
    {
        getRDFResource().removeAll(vocabulary.userCreated.getRDFProperty());
    }

    public User getUser()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userCreated.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
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

    public String getDescription()
    {
        return getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        setProperty(vocabulary.description, description);
    }

    public SemanticIterator<org.semanticwb.model.ObjectGroup> listObjectGroup()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, stit);
    }

    public void addObjectGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        addObjectProperty(vocabulary.hasGroup, objectgroup);
    }

    public void removeAllObjectGroup()
    {
        getRDFResource().removeAll(vocabulary.hasGroup.getRDFProperty());
    }

    public ObjectGroup getObjectGroup()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasGroup.getRDFProperty());
         SemanticIterator<org.semanticwb.model.ObjectGroup> it=new SemanticIterator<org.semanticwb.model.ObjectGroup>(ObjectGroup.class, stit);
         return it.next();
    }
}
