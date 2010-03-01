/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;


// TODO: Auto-generated Javadoc
/**
 * The Class UserGroupBase.
 */
public abstract class UserGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant swb_UserGroupable. */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupable");
    
    /** The Constant swb_hasGroupedUser. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasGroupedUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasGroupedUser");
    
    /** The Constant swb_UserGroup. */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    
    /** The Constant swb_usrgrpParent. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrgrpParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrgrpParent");
    
    /** The Constant swb_hasUsrGrpChild. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasUsrGrpChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUsrGrpChild");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List user groups.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(it, true);
        }

        /**
         * List user groups.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(it, true);
        }

        /**
         * Gets the user group.
         * 
         * @param id the id
         * @param model the model
         * @return the user group
         */
        public static org.semanticwb.model.UserGroup getUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the user group.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. user group
         */
        public static org.semanticwb.model.UserGroup createUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the user group.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for user group.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasUserGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserGroup(id, model)!=null);
        }

        /**
         * List user group by user.
         * 
         * @param hasgroupeduser the hasgroupeduser
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByUser(org.semanticwb.model.UserGroupable hasgroupeduser,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasGroupedUser, hasgroupeduser.getSemanticObject()));
            return it;
        }

        /**
         * List user group by user.
         * 
         * @param hasgroupeduser the hasgroupeduser
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByUser(org.semanticwb.model.UserGroupable hasgroupeduser)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(hasgroupeduser.getSemanticObject().getModel().listSubjects(swb_hasGroupedUser,hasgroupeduser.getSemanticObject()));
            return it;
        }

        /**
         * List user group by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List user group by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List user group by parent.
         * 
         * @param usrgrpparent the usrgrpparent
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByParent(org.semanticwb.model.UserGroup usrgrpparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_usrgrpParent, usrgrpparent.getSemanticObject()));
            return it;
        }

        /**
         * List user group by parent.
         * 
         * @param usrgrpparent the usrgrpparent
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByParent(org.semanticwb.model.UserGroup usrgrpparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(usrgrpparent.getSemanticObject().getModel().listSubjects(swb_usrgrpParent,usrgrpparent.getSemanticObject()));
            return it;
        }

        /**
         * List user group by child.
         * 
         * @param hasusrgrpchild the hasusrgrpchild
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByChild(org.semanticwb.model.UserGroup hasusrgrpchild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUsrGrpChild, hasusrgrpchild.getSemanticObject()));
            return it;
        }

        /**
         * List user group by child.
         * 
         * @param hasusrgrpchild the hasusrgrpchild
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByChild(org.semanticwb.model.UserGroup hasusrgrpchild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(hasusrgrpchild.getSemanticObject().getModel().listSubjects(swb_hasUsrGrpChild,hasusrgrpchild.getSemanticObject()));
            return it;
        }

        /**
         * List user group by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List user group by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.UserGroup> listUserGroupByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new user group base.
     * 
     * @param base the base
     */
    public UserGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List users.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupable> listUsers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupable>(getSemanticObject().listObjectProperties(swb_hasGroupedUser));
    }

    /**
     * Checks for user.
     * 
     * @param usergroupable the usergroupable
     * @return true, if successful
     */
    public boolean hasUser(org.semanticwb.model.UserGroupable usergroupable)
    {
        boolean ret=false;
        if(usergroupable!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasGroupedUser,usergroupable.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public org.semanticwb.model.UserGroupable getUser()
    {
         org.semanticwb.model.UserGroupable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasGroupedUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupable)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
     */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /**
     * Sets the parent.
     * 
     * @param value the new parent
     */
    public void setParent(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().setObjectProperty(swb_usrgrpParent, value.getSemanticObject());
    }

    /**
     * Removes the parent.
     */
    public void removeParent()
    {
        getSemanticObject().removeProperty(swb_usrgrpParent);
    }

    /**
     * Gets the parent.
     * 
     * @return the parent
     */
    public org.semanticwb.model.UserGroup getParent()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_usrgrpParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * List childs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(getSemanticObject().listObjectProperties(swb_hasUsrGrpChild));
    }

    /**
     * Checks for child.
     * 
     * @param usergroup the usergroup
     * @return true, if successful
     */
    public boolean hasChild(org.semanticwb.model.UserGroup usergroup)
    {
        boolean ret=false;
        if(usergroup!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUsrGrpChild,usergroup.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the child.
     * 
     * @return the child
     */
    public org.semanticwb.model.UserGroup getChild()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUsrGrpChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
     */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UndeleteableBase#isUndeleteable()
     */
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UndeleteableBase#setUndeleteable(boolean)
     */
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

    /**
     * Gets the user repository.
     * 
     * @return the user repository
     */
    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return (org.semanticwb.model.UserRepository)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
