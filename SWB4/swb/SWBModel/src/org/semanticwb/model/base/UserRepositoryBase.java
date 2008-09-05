package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class UserRepositoryBase extends GenericObjectBase implements Descriptiveable,Traceable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public UserRepositoryBase(SemanticObject base)
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

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public User getUser(String id)
    {
        return (User)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.User),vocabulary.User);
    }

    public Iterator<User> listUsers()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.User.getOntClass());
        return new GenericIterator<User>(User.class, stit);
    }

    public User createUser(String id)
    {
        return (User)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.User), vocabulary.User);
    }

    public User createUser()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.User.getName());
        return createUser(""+id);
    } 

    public void removeUser(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.User));
    }

    public ObjectGroup getObjectGroup(String id)
    {
        return (ObjectGroup)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.ObjectGroup),vocabulary.ObjectGroup);
    }

    public Iterator<ObjectGroup> listObjectGroups()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.ObjectGroup.getOntClass());
        return new GenericIterator<ObjectGroup>(ObjectGroup.class, stit);
    }

    public ObjectGroup createObjectGroup(String id)
    {
        return (ObjectGroup)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.ObjectGroup), vocabulary.ObjectGroup);
    }

    public ObjectGroup createObjectGroup()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.ObjectGroup.getName());
        return createObjectGroup(""+id);
    } 

    public void removeObjectGroup(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.ObjectGroup));
    }

    public Role getRole(String id)
    {
        return (Role)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Role),vocabulary.Role);
    }

    public Iterator<Role> listRoles()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.Role.getOntClass());
        return new GenericIterator<Role>(Role.class, stit);
    }

    public Role createRole(String id)
    {
        return (Role)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.Role), vocabulary.Role);
    }

    public Role createRole()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.Role.getName());
        return createRole(""+id);
    } 

    public void removeRole(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.Role));
    }
}
