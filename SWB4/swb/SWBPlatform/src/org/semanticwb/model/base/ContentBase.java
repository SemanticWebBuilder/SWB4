package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class ContentBase extends SemanticObject implements Versionable,Priorityable,RoleRefable,Groupable,Deleteable,WebSiteable,Referensable,Localeable,RuleRefable,Calendarable,Descriptiveable,Statusable
{
    public ContentBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public SemanticIterator<org.semanticwb.model.RoleRef> listRoleRef()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRoleRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, stit);
    }
    public void addRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        addObjectProperty(Vocabulary.hasRoleRef, roleref);
    }
    public void removeAllRoleRef()
    {
        getRDFResource().removeAll(Vocabulary.hasRoleRef.getRDFProperty());
    }
    public RoleRef getRoleRef()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRoleRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.RoleRef> it=new SemanticIterator<org.semanticwb.model.RoleRef>(RoleRef.class, stit);
         return it.next();
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
    public SemanticIterator<org.semanticwb.model.Language> listLanguage()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.useLanguage.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Language>(org.semanticwb.model.Language.class, stit);
    }
    public void addLanguage(org.semanticwb.model.Language language)
    {
        addObjectProperty(Vocabulary.useLanguage, language);
    }
    public void removeAllLanguage()
    {
        getRDFResource().removeAll(Vocabulary.useLanguage.getRDFProperty());
    }
    public Language getLanguage()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.useLanguage.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Language> it=new SemanticIterator<org.semanticwb.model.Language>(Language.class, stit);
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
    public SemanticIterator<org.semanticwb.model.RuleRef> listRuleRef()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRuleRef.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, stit);
    }
    public void addRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        addObjectProperty(Vocabulary.hasRuleRef, ruleref);
    }
    public void removeAllRuleRef()
    {
        getRDFResource().removeAll(Vocabulary.hasRuleRef.getRDFProperty());
    }
    public RuleRef getRuleRef()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRuleRef.getRDFProperty());
         SemanticIterator<org.semanticwb.model.RuleRef> it=new SemanticIterator<org.semanticwb.model.RuleRef>(RuleRef.class, stit);
         return it.next();
    }
    public SemanticIterator<org.semanticwb.model.Calendar> listCalendar()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasCalendar.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, stit);
    }
    public void addCalendar(org.semanticwb.model.Calendar calendar)
    {
        addObjectProperty(Vocabulary.hasCalendar, calendar);
    }
    public void removeAllCalendar()
    {
        getRDFResource().removeAll(Vocabulary.hasCalendar.getRDFProperty());
    }
    public Calendar getCalendar()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasCalendar.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Calendar> it=new SemanticIterator<org.semanticwb.model.Calendar>(Calendar.class, stit);
         return it.next();
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
    public int getPriority()
    {
        return getIntProperty(Vocabulary.priority);
    }
    public void setPriority(int priority)
    {
        setLongProperty(Vocabulary.priority, priority);
    }
}
