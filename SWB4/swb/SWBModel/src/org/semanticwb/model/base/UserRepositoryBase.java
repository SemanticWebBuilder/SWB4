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
        getSemanticObject().setObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.modifiedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
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
        return getSemanticObject().getProperty(vocabulary.title, null, lang);
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
        getSemanticObject().setObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.creator);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
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
        return getSemanticObject().getProperty(vocabulary.description, null, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.description, description, lang);
    }

    public Permission getPermission(String id)
    {
        return (Permission)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Permission),vocabulary.swb_Permission);
    }

    public Iterator<Permission> listPermissions()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Permission.getOntClass());
        return new GenericIterator<Permission>(Permission.class, stit, true);
    }

    public Permission createPermission(String id)
    {
        return (Permission)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Permission), vocabulary.swb_Permission);
    }

    public Permission createPermission()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_Permission.getName());
        return createPermission(""+id);
    } 

    public void removePermission(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Permission));
    }
    public boolean hasPermission(String id)
    {
        return (getPermission(id)!=null);
    }

    public User getUser(String id)
    {
        return (User)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_User),vocabulary.swb_User);
    }

    public Iterator<User> listUsers()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_User.getOntClass());
        return new GenericIterator<User>(User.class, stit, true);
    }

    public User createUser(String id)
    {
        return (User)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_User), vocabulary.swb_User);
    }

    public User createUser()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_User.getName());
        return createUser(""+id);
    } 

    public void removeUser(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_User));
    }
    public boolean hasUser(String id)
    {
        return (getUser(id)!=null);
    }

    public ObjectGroup getObjectGroup(String id)
    {
        return (ObjectGroup)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_ObjectGroup),vocabulary.swb_ObjectGroup);
    }

    public Iterator<ObjectGroup> listObjectGroups()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_ObjectGroup.getOntClass());
        return new GenericIterator<ObjectGroup>(ObjectGroup.class, stit, true);
    }

    public ObjectGroup createObjectGroup(String id)
    {
        return (ObjectGroup)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_ObjectGroup), vocabulary.swb_ObjectGroup);
    }

    public ObjectGroup createObjectGroup()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_ObjectGroup.getName());
        return createObjectGroup(""+id);
    } 

    public void removeObjectGroup(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_ObjectGroup));
    }
    public boolean hasObjectGroup(String id)
    {
        return (getObjectGroup(id)!=null);
    }

    public Role getRole(String id)
    {
        return (Role)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Role),vocabulary.swb_Role);
    }

    public Iterator<Role> listRoles()
    {
        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary.swb_Role.getOntClass());
        return new GenericIterator<Role>(Role.class, stit, true);
    }

    public Role createRole(String id)
    {
        return (Role)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary.swb_Role), vocabulary.swb_Role);
    }

    public Role createRole()
    {
        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+vocabulary.swb_Role.getName());
        return createRole(""+id);
    } 

    public void removeRole(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary.swb_Role));
    }
    public boolean hasRole(String id)
    {
        return (getRole(id)!=null);
    }
}
