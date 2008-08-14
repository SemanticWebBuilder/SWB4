package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class CampBase extends SemanticObject implements Statusable,Deleteable,Ruleable,Descriptiveable,Calendarable,Roleable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public CampBase(com.hp.hpl.jena.rdf.model.Resource res)
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

    public void setUserModified(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userModified, user);
    }

    public void removeUserModified()
    {
        removeProperty(vocabulary.userModified);
    }

    public User getUserModified()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userModified.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public SemanticIterator<org.semanticwb.model.Calendar> listCalendar()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, stit);
    }

    public void addCalendar(org.semanticwb.model.Calendar calendar)
    {
        addObjectProperty(vocabulary.hasCalendar, calendar);
    }

    public void removeAllCalendar()
    {
        removeProperty(vocabulary.hasCalendar);
    }

    public void removeCalendar(org.semanticwb.model.Calendar calendar)
    {
        removeObjectProperty(vocabulary.hasCalendar,calendar);
    }

    public Calendar getCalendar()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Calendar> it=new SemanticIterator<org.semanticwb.model.Calendar>(Calendar.class, stit);
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

    public SemanticIterator<org.semanticwb.model.Role> listRole()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRole.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, stit);
    }

    public void addRole(org.semanticwb.model.Role role)
    {
        addObjectProperty(vocabulary.hasRole, role);
    }

    public void removeAllRole()
    {
        removeProperty(vocabulary.hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        removeObjectProperty(vocabulary.hasRole,role);
    }

    public Role getRole()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRole.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Role> it=new SemanticIterator<org.semanticwb.model.Role>(Role.class, stit);
         return it.next();
    }

    public String getDescription()
    {
        return getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        setProperty(vocabulary.description, description);
    }

    public void setUserCreated(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userCreated, user);
    }

    public void removeUserCreated()
    {
        removeProperty(vocabulary.userCreated);
    }

    public User getUserCreated()
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

    public SemanticIterator<org.semanticwb.model.Rule> listRule()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRule.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Rule>(org.semanticwb.model.Rule.class, stit);
    }

    public void addRule(org.semanticwb.model.Rule rule)
    {
        addObjectProperty(vocabulary.hasRule, rule);
    }

    public void removeAllRule()
    {
        removeProperty(vocabulary.hasRule);
    }

    public void removeRule(org.semanticwb.model.Rule rule)
    {
        removeObjectProperty(vocabulary.hasRule,rule);
    }

    public Rule getRule()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasRule.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Rule> it=new SemanticIterator<org.semanticwb.model.Rule>(Rule.class, stit);
         return it.next();
    }
}
