package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class PFlowBase extends SemanticObject implements Descriptiveable,Statusable,Versionable,Deleteable,WebSiteable,Groupable
{
    public PFlowBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public boolean isDeleted()
    {
        return getBooleanProperty(Vocabulary.deleted);
    }
    public void setDeleted(boolean deleted)
    {
        setBooleanProperty(Vocabulary.deleted, deleted);
    }
    public Date getCreated()
    {
        return getDateProperty(Vocabulary.created);
    }
    public void setCreated(Date created)
    {
        setDateProperty(Vocabulary.created, created);
    }
    public String getTitle()
    {
        return getProperty(Vocabulary.title);
    }
    public void setTitle(String title)
    {
        setProperty(Vocabulary.title, title);
    }
    public SemanticIterator<org.semanticwb.model.VersionInfo> listVersionInfo()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.actualVersion.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.VersionInfo>(org.semanticwb.model.VersionInfo.class, stit);
    }
    public void addVersionInfo(org.semanticwb.model.VersionInfo versioninfo)
    {
        addObjectProperty(Vocabulary.actualVersion, versioninfo);
    }
    public void removeAllVersionInfo()
    {
        getRDFResource().removeAll(Vocabulary.actualVersion.getRDFProperty());
    }
    public VersionInfo getVersionInfo()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.actualVersion.getRDFProperty());
         SemanticIterator<org.semanticwb.model.VersionInfo> it=new SemanticIterator<org.semanticwb.model.VersionInfo>(VersionInfo.class, stit);
         return it.next();
    }
    public SemanticIterator<org.semanticwb.model.User> listUser()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.userCreated.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.User>(org.semanticwb.model.User.class, stit);
    }
    public void addUser(org.semanticwb.model.User user)
    {
        addObjectProperty(Vocabulary.userCreated, user);
    }
    public void removeAllUser()
    {
        getRDFResource().removeAll(Vocabulary.userCreated.getRDFProperty());
    }
    public User getUser()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.userCreated.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
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
}
