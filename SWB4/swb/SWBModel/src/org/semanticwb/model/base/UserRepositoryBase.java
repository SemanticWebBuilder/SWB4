package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class UserRepositoryBase extends SemanticObject implements Descriptiveable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public UserRepositoryBase(com.hp.hpl.jena.rdf.model.Resource res)
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

    public Date getUpdated()
    {
        return getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        setDateProperty(vocabulary.updated, updated);
    }
    public Role getRole(String id)
    {
        return (Role)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.Role),vocabulary.Role);
    }

    public Iterator<Role> listRoles()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.Role.getOntClass());
        return new SemanticIterator<Role>(Role.class, stit);
    }

    public Role createRole(String id)
    {
        return (Role)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.Role), vocabulary.Role);
    }

    public Role createRole()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.Role.getName());
        return createRole(""+id);
    } 

    public void removeRole(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.Role));
    }
    public ObjectGroup getObjectGroup(String id)
    {
        return (ObjectGroup)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.ObjectGroup),vocabulary.ObjectGroup);
    }

    public Iterator<ObjectGroup> listObjectGroups()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.ObjectGroup.getOntClass());
        return new SemanticIterator<ObjectGroup>(ObjectGroup.class, stit);
    }

    public ObjectGroup createObjectGroup(String id)
    {
        return (ObjectGroup)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.ObjectGroup), vocabulary.ObjectGroup);
    }

    public ObjectGroup createObjectGroup()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.ObjectGroup.getName());
        return createObjectGroup(""+id);
    } 

    public void removeObjectGroup(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.ObjectGroup));
    }
    public User getUser(String id)
    {
        return (User)getModel().getSemanticObject(getModel().getObjectUri(id,vocabulary.User),vocabulary.User);
    }

    public Iterator<User> listUsers()
    {
        Property rdf=getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getModel().getRDFModel().listStatements(null, rdf, vocabulary.User.getOntClass());
        return new SemanticIterator<User>(User.class, stit);
    }

    public User createUser(String id)
    {
        return (User)getModel().createSemanticObject(getModel().getObjectUri(id, vocabulary.User), vocabulary.User);
    }

    public User createUser()
    {
        long id=SWBInstance.getCounterValue(getModel().getName()+"/"+vocabulary.User.getName());
        return createUser(""+id);
    } 

    public void removeUser(String id)
    {
        getModel().removeSemanticObject(getModel().getObjectUri(id,vocabulary.User));
    }
}
