package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class CampBase extends SemanticObject implements Descriptiveable,Statusable,Deleteable,Roleable,Ruleable,WebSiteable,Calendarable
{
    public CampBase(com.hp.hpl.jena.rdf.model.Resource res)
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
    public SemanticIterator<org.semanticwb.model.Role> listRole()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRole.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, stit);
    }
    public void addRole(org.semanticwb.model.Role role)
    {
        addObjectProperty(Vocabulary.hasRole, role);
    }
    public void removeAllRole()
    {
        getRDFResource().removeAll(Vocabulary.hasRole.getRDFProperty());
    }
    public Role getRole()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRole.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Role> it=new SemanticIterator<org.semanticwb.model.Role>(Role.class, stit);
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
    public SemanticIterator<org.semanticwb.model.Rule> listRule()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRule.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.Rule>(org.semanticwb.model.Rule.class, stit);
    }
    public void addRule(org.semanticwb.model.Rule rule)
    {
        addObjectProperty(Vocabulary.hasRule, rule);
    }
    public void removeAllRule()
    {
        getRDFResource().removeAll(Vocabulary.hasRule.getRDFProperty());
    }
    public Rule getRule()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasRule.getRDFProperty());
         SemanticIterator<org.semanticwb.model.Rule> it=new SemanticIterator<org.semanticwb.model.Rule>(Rule.class, stit);
         return it.next();
    }
}
