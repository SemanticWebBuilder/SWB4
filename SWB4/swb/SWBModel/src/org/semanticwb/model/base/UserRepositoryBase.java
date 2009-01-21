package org.semanticwb.model.base;


public class UserRepositoryBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorites=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorites");
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");


    public static org.semanticwb.model.UserRepository createUserRepository(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.UserRepository)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swb_UserRepository), swb_UserRepository);
    }

    public UserRepositoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.model.UserFavorites getUserFavorites(String id)
    {
        return (org.semanticwb.model.UserFavorites)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_UserFavorites),swb_UserFavorites);
    }

    public java.util.Iterator<org.semanticwb.model.UserFavorites> listUserFavoritess()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_UserFavorites.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFavorites>(org.semanticwb.model.UserFavorites.class, stit, true);
    }

    public org.semanticwb.model.UserFavorites createUserFavorites(String id)
    {
        return (org.semanticwb.model.UserFavorites)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_UserFavorites), swb_UserFavorites);
    }

    public org.semanticwb.model.UserFavorites createUserFavorites()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_UserFavorites.getName());
        return createUserFavorites(""+id);
    } 

    public void removeUserFavorites(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_UserFavorites));
    }
    public boolean hasUserFavorites(String id)
    {
        return (getUserFavorites(id)!=null);
    }

    public org.semanticwb.model.UserGroup getUserGroup(String id)
    {
        return (org.semanticwb.model.UserGroup)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_UserGroup),swb_UserGroup);
    }

    public java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_UserGroup.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(org.semanticwb.model.UserGroup.class, stit, true);
    }

    public org.semanticwb.model.UserGroup createUserGroup(String id)
    {
        return (org.semanticwb.model.UserGroup)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_UserGroup), swb_UserGroup);
    }

    public void removeUserGroup(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_UserGroup));
    }
    public boolean hasUserGroup(String id)
    {
        return (getUserGroup(id)!=null);
    }

    public org.semanticwb.model.User getUser(String id)
    {
        return (org.semanticwb.model.User)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_User),swb_User);
    }

    public java.util.Iterator<org.semanticwb.model.User> listUsers()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_User.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(org.semanticwb.model.User.class, stit, true);
    }

    public org.semanticwb.model.User createUser(String id)
    {
        return (org.semanticwb.model.User)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_User), swb_User);
    }

    public org.semanticwb.model.User createUser()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_User.getName());
        return createUser(""+id);
    } 

    public void removeUser(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_User));
    }
    public boolean hasUser(String id)
    {
        return (getUser(id)!=null);
    }

    public org.semanticwb.model.Role getRole(String id)
    {
        return (org.semanticwb.model.Role)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,swb_Role),swb_Role);
    }

    public java.util.Iterator<org.semanticwb.model.Role> listRoles()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, swb_Role.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(org.semanticwb.model.Role.class, stit, true);
    }

    public org.semanticwb.model.Role createRole(String id)
    {
        return (org.semanticwb.model.Role)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, swb_Role), swb_Role);
    }

    public org.semanticwb.model.Role createRole()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+swb_Role.getName());
        return createRole(""+id);
    } 

    public void removeRole(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,swb_Role));
    }
    public boolean hasRole(String id)
    {
        return (getRole(id)!=null);
    }
}
