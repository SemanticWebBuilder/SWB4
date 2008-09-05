package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class CampBase extends GenericObjectBase implements Activeable,Deleteable,Ruleable,Descriptiveable,Traceable,Calendarable,Roleable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public CampBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.created, created);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.deleted, deleted);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.modifiedBy);
         return (User)vocabulary.User.newGenericInstance(obj);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.title, title, lang);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.creator);
         return (User)vocabulary.User.newGenericInstance(obj);
    }

    public GenericIterator<org.semanticwb.model.Calendar> listCalendars()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, stit);
    }

    public void addCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasCalendar, calendar.getSemanticObject());
    }

    public void removeAllCalendar()
    {
        getSemanticObject().removeProperty(vocabulary.hasCalendar);
    }

    public void removeCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasCalendar,calendar.getSemanticObject());
    }

    public Calendar getCalendar()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasCalendar);
         return (Calendar)vocabulary.Calendar.newGenericInstance(obj);
    }

    public GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRole.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, stit);
    }

    public void addRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasRole, role.getSemanticObject());
    }

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(vocabulary.hasRole);
    }

    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasRole,role.getSemanticObject());
    }

    public Role getRole()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasRole);
         return (Role)vocabulary.Role.newGenericInstance(obj);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.description, description, lang);
    }

    public GenericIterator<org.semanticwb.model.Rule> listRules()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRule.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.Rule>(org.semanticwb.model.Rule.class, stit);
    }

    public void addRule(org.semanticwb.model.Rule rule)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasRule, rule.getSemanticObject());
    }

    public void removeAllRule()
    {
        getSemanticObject().removeProperty(vocabulary.hasRule);
    }

    public void removeRule(org.semanticwb.model.Rule rule)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasRule,rule.getSemanticObject());
    }

    public Rule getRule()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasRule);
         return (Rule)vocabulary.Rule.newGenericInstance(obj);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
