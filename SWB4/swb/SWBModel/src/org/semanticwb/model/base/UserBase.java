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
 * The Class UserBase.
 */
public abstract class UserBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.FilterableClass,org.semanticwb.model.Expirable,org.semanticwb.model.Roleable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Referensable,org.semanticwb.model.Filterable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable
{
    
    /** The Constant swb_Country. */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
    
    /** The Constant swb_usrCountry. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrCountry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrCountry");
    
    /** The Constant swb_usrLastName. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrLastName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLastName");
    
    /** The Constant swb_externalID. */
    public static final org.semanticwb.platform.SemanticProperty swb_externalID=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#externalID");
    
    /** The Constant swb_usrPhoto. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPhoto");
    
    /** The Constant swb_usrReqConfirm. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrReqConfirm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrReqConfirm");
    
    /** The Constant swb_usrSecondLastName. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrSecondLastName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecondLastName");
    
    /** The Constant swb_UserFavorite. */
    public static final org.semanticwb.platform.SemanticClass swb_UserFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFavorite");
    
    /** The Constant swb_usrFavorite. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrFavorite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrFavorite");
    
    /** The Constant swb_usrEmail. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrEmail");
    
    /** The Constant swb_usrFirstName. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrFirstName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrFirstName");
    
    /** The Constant swb_usrLanguage. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrLanguage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLanguage");
    
    /** The Constant swb_hasUserType. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasUserType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserType");
    
    /** The Constant swb_usrPasswordChanged. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrPasswordChanged=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPasswordChanged");
    
    /** The Constant swb_AdminFilter. */
    public static final org.semanticwb.platform.SemanticClass swb_AdminFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminFilter");
    
    /** The Constant swb_hasAdminFilter. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasAdminFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasAdminFilter");
    
    /** The Constant swb_usrLastLogin. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrLastLogin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLastLogin");
    
    /** The Constant swb_usrPassword. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrPassword=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPassword");
    
    /** The Constant swb_usrLogin. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrLogin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLogin");
    
    /** The Constant swb_usrSecurityQuestion. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrSecurityQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecurityQuestion");
    
    /** The Constant swb_usrSecurityAnswer. */
    public static final org.semanticwb.platform.SemanticProperty swb_usrSecurityAnswer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecurityAnswer");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List users.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUsers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(it, true);
        }

        /**
         * List users.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUsers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(it, true);
        }

        /**
         * Creates the user.
         * 
         * @param model the model
         * @return the org.semanticwb.model. user
         */
        public static org.semanticwb.model.User createUser(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.User.ClassMgr.createUser(String.valueOf(id), model);
        }

        /**
         * Gets the user.
         * 
         * @param id the id
         * @param model the model
         * @return the user
         */
        public static org.semanticwb.model.User getUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.User)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the user.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. user
         */
        public static org.semanticwb.model.User createUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.User)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the user.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeUser(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for user.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUser(id, model)!=null);
        }

        /**
         * List user by country.
         * 
         * @param usrcountry the usrcountry
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByCountry(org.semanticwb.model.Country usrcountry,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_usrCountry, usrcountry.getSemanticObject()));
            return it;
        }

        /**
         * List user by country.
         * 
         * @param usrcountry the usrcountry
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByCountry(org.semanticwb.model.Country usrcountry)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(usrcountry.getSemanticObject().getModel().listSubjects(swb_usrCountry,usrcountry.getSemanticObject()));
            return it;
        }

        /**
         * List user by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef, hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List user by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef,hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List user by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List user by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List user by user group.
         * 
         * @param hasusergroup the hasusergroup
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByUserGroup(org.semanticwb.model.UserGroup hasusergroup,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUserGroup, hasusergroup.getSemanticObject()));
            return it;
        }

        /**
         * List user by user group.
         * 
         * @param hasusergroup the hasusergroup
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByUserGroup(org.semanticwb.model.UserGroup hasusergroup)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(hasusergroup.getSemanticObject().getModel().listSubjects(swb_hasUserGroup,hasusergroup.getSemanticObject()));
            return it;
        }

        /**
         * List user by user favorite.
         * 
         * @param usrfavorite the usrfavorite
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByUserFavorite(org.semanticwb.model.UserFavorite usrfavorite,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_usrFavorite, usrfavorite.getSemanticObject()));
            return it;
        }

        /**
         * List user by user favorite.
         * 
         * @param usrfavorite the usrfavorite
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByUserFavorite(org.semanticwb.model.UserFavorite usrfavorite)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(usrfavorite.getSemanticObject().getModel().listSubjects(swb_usrFavorite,usrfavorite.getSemanticObject()));
            return it;
        }

        /**
         * List user by admin filter.
         * 
         * @param hasadminfilter the hasadminfilter
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByAdminFilter(org.semanticwb.model.AdminFilter hasadminfilter,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasAdminFilter, hasadminfilter.getSemanticObject()));
            return it;
        }

        /**
         * List user by admin filter.
         * 
         * @param hasadminfilter the hasadminfilter
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByAdminFilter(org.semanticwb.model.AdminFilter hasadminfilter)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(hasadminfilter.getSemanticObject().getModel().listSubjects(swb_hasAdminFilter,hasadminfilter.getSemanticObject()));
            return it;
        }

        /**
         * List user by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List user by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        /**
         * List user by role.
         * 
         * @param hasrole the hasrole
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByRole(org.semanticwb.model.Role hasrole,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRole, hasrole.getSemanticObject()));
            return it;
        }

        /**
         * List user by role.
         * 
         * @param hasrole the hasrole
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.User> listUserByRole(org.semanticwb.model.Role hasrole)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(hasrole.getSemanticObject().getModel().listSubjects(swb_hasRole,hasrole.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new user base.
     * 
     * @param base the base
     */
    public UserBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the country.
     * 
     * @param value the new country
     */
    public void setCountry(org.semanticwb.model.Country value)
    {
        getSemanticObject().setObjectProperty(swb_usrCountry, value.getSemanticObject());
    }

    /**
     * Removes the country.
     */
    public void removeCountry()
    {
        getSemanticObject().removeProperty(swb_usrCountry);
    }

    /**
     * Gets the country.
     * 
     * @return the country
     */
    public org.semanticwb.model.Country getCountry()
    {
         org.semanticwb.model.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_usrCountry);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Country)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the last name.
     * 
     * @return the last name
     */
    public String getLastName()
    {
        return getSemanticObject().getProperty(swb_usrLastName);
    }

    /**
     * Sets the last name.
     * 
     * @param value the new last name
     */
    public void setLastName(String value)
    {
        getSemanticObject().setProperty(swb_usrLastName, value);
    }

    /**
     * Gets the external id.
     * 
     * @return the external id
     */
    public String getExternalID()
    {
        return getSemanticObject().getProperty(swb_externalID);
    }

    /**
     * Sets the external id.
     * 
     * @param value the new external id
     */
    public void setExternalID(String value)
    {
        getSemanticObject().setProperty(swb_externalID, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#isActive()
     */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#setActive(boolean)
     */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    /**
     * Gets the photo.
     * 
     * @return the photo
     */
    public String getPhoto()
    {
        return getSemanticObject().getProperty(swb_usrPhoto);
    }

    /**
     * Sets the photo.
     * 
     * @param value the new photo
     */
    public void setPhoto(String value)
    {
        getSemanticObject().setProperty(swb_usrPhoto, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#listCalendarRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#hasCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        boolean ret=false;
        if(calendarref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,calendarref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#addCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#removeAllCalendarRef()
     */
    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#removeCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public void removeCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,calendarref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#getCalendarRef()
     */
    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Checks if is require confirm.
     * 
     * @return true, if is require confirm
     */
    public boolean isRequireConfirm()
    {
        return getSemanticObject().getBooleanProperty(swb_usrReqConfirm);
    }

    /**
     * Sets the require confirm.
     * 
     * @param value the new require confirm
     */
    public void setRequireConfirm(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_usrReqConfirm, value);
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

    /**
     * Gets the second last name.
     * 
     * @return the second last name
     */
    public String getSecondLastName()
    {
        return getSemanticObject().getProperty(swb_usrSecondLastName);
    }

    /**
     * Sets the second last name.
     * 
     * @param value the new second last name
     */
    public void setSecondLastName(String value)
    {
        getSemanticObject().setProperty(swb_usrSecondLastName, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ExpirableBase#getExpiration()
     */
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ExpirableBase#setExpiration(java.util.Date)
     */
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupableBase#listUserGroups()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(getSemanticObject().listObjectProperties(swb_hasUserGroup));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupableBase#hasUserGroup(org.semanticwb.model.UserGroup)
     */
    public boolean hasUserGroup(org.semanticwb.model.UserGroup usergroup)
    {
        boolean ret=false;
        if(usergroup!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroup,usergroup.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupableBase#addUserGroup(org.semanticwb.model.UserGroup)
     */
    public void addUserGroup(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroup, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupableBase#removeAllUserGroup()
     */
    public void removeAllUserGroup()
    {
        getSemanticObject().removeProperty(swb_hasUserGroup);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupableBase#removeUserGroup(org.semanticwb.model.UserGroup)
     */
    public void removeUserGroup(org.semanticwb.model.UserGroup usergroup)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroup,usergroup.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupableBase#getUserGroup()
     */
    public org.semanticwb.model.UserGroup getUserGroup()
    {
         org.semanticwb.model.UserGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroup)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the user favorite.
     * 
     * @param value the new user favorite
     */
    public void setUserFavorite(org.semanticwb.model.UserFavorite value)
    {
        getSemanticObject().setObjectProperty(swb_usrFavorite, value.getSemanticObject());
    }

    /**
     * Removes the user favorite.
     */
    public void removeUserFavorite()
    {
        getSemanticObject().removeProperty(swb_usrFavorite);
    }

    /**
     * Gets the user favorite.
     * 
     * @return the user favorite
     */
    public org.semanticwb.model.UserFavorite getUserFavorite()
    {
         org.semanticwb.model.UserFavorite ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_usrFavorite);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserFavorite)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the email.
     * 
     * @return the email
     */
    public String getEmail()
    {
        return getSemanticObject().getProperty(swb_usrEmail);
    }

    /**
     * Sets the email.
     * 
     * @param value the new email
     */
    public void setEmail(String value)
    {
        getSemanticObject().setProperty(swb_usrEmail, value);
    }

    /**
     * Gets the first name.
     * 
     * @return the first name
     */
    public String getFirstName()
    {
        return getSemanticObject().getProperty(swb_usrFirstName);
    }

    /**
     * Sets the first name.
     * 
     * @param value the new first name
     */
    public void setFirstName(String value)
    {
        getSemanticObject().setProperty(swb_usrFirstName, value);
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
     * Gets the language.
     * 
     * @return the language
     */
    public String getLanguage()
    {
        return getSemanticObject().getProperty(swb_usrLanguage);
    }

    /**
     * Sets the language.
     * 
     * @param value the new language
     */
    public void setLanguage(String value)
    {
        getSemanticObject().setProperty(swb_usrLanguage, value);
    }

    /**
     * List user types.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<String> listUserTypes()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swb_hasUserType);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    /**
     * Adds the user type.
     * 
     * @param usertype the usertype
     */
    public void addUserType(String usertype)
    {
        getSemanticObject().setProperty(swb_hasUserType, usertype);
    }

    /**
     * Removes the all user type.
     */
    public void removeAllUserType()
    {
        getSemanticObject().removeProperty(swb_hasUserType);
    }

    /**
     * Removes the user type.
     * 
     * @param usertype the usertype
     */
    public void removeUserType(String usertype)
    {
        getSemanticObject().removeProperty(swb_hasUserType,usertype);
    }

    /**
     * Gets the password changed.
     * 
     * @return the password changed
     */
    public java.util.Date getPasswordChanged()
    {
        return getSemanticObject().getDateProperty(swb_usrPasswordChanged);
    }

    /**
     * Sets the password changed.
     * 
     * @param value the new password changed
     */
    public void setPasswordChanged(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_usrPasswordChanged, value);
    }

    /**
     * List admin filters.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminFilter> listAdminFilters()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminFilter>(getSemanticObject().listObjectProperties(swb_hasAdminFilter));
    }

    /**
     * Checks for admin filter.
     * 
     * @param adminfilter the adminfilter
     * @return true, if successful
     */
    public boolean hasAdminFilter(org.semanticwb.model.AdminFilter adminfilter)
    {
        boolean ret=false;
        if(adminfilter!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasAdminFilter,adminfilter.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the admin filter.
     * 
     * @param value the value
     */
    public void addAdminFilter(org.semanticwb.model.AdminFilter value)
    {
        getSemanticObject().addObjectProperty(swb_hasAdminFilter, value.getSemanticObject());
    }

    /**
     * Removes the all admin filter.
     */
    public void removeAllAdminFilter()
    {
        getSemanticObject().removeProperty(swb_hasAdminFilter);
    }

    /**
     * Removes the admin filter.
     * 
     * @param adminfilter the adminfilter
     */
    public void removeAdminFilter(org.semanticwb.model.AdminFilter adminfilter)
    {
        getSemanticObject().removeObjectProperty(swb_hasAdminFilter,adminfilter.getSemanticObject());
    }

    /**
     * Gets the admin filter.
     * 
     * @return the admin filter
     */
    public org.semanticwb.model.AdminFilter getAdminFilter()
    {
         org.semanticwb.model.AdminFilter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasAdminFilter);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.AdminFilter)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the last login.
     * 
     * @return the last login
     */
    public java.util.Date getLastLogin()
    {
        return getSemanticObject().getDateProperty(swb_usrLastLogin);
    }

    /**
     * Sets the last login.
     * 
     * @param value the new last login
     */
    public void setLastLogin(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_usrLastLogin, value);
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

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword()
    {
        //Override this method in User object
        return getSemanticObject().getProperty(swb_usrPassword,false);
    }

    /**
     * Sets the password.
     * 
     * @param value the new password
     */
    public void setPassword(String value)
    {
        //Override this method in User object
        getSemanticObject().setProperty(swb_usrPassword, value,false);
    }

    /**
     * Gets the login.
     * 
     * @return the login
     */
    public String getLogin()
    {
        return getSemanticObject().getProperty(swb_usrLogin);
    }

    /**
     * Sets the login.
     * 
     * @param value the new login
     */
    public void setLogin(String value)
    {
        getSemanticObject().setProperty(swb_usrLogin, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#listRoles()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(getSemanticObject().listObjectProperties(swb_hasRole));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#hasRole(org.semanticwb.model.Role)
     */
    public boolean hasRole(org.semanticwb.model.Role role)
    {
        boolean ret=false;
        if(role!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRole,role.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#addRole(org.semanticwb.model.Role)
     */
    public void addRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().addObjectProperty(swb_hasRole, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#removeAllRole()
     */
    public void removeAllRole()
    {
        getSemanticObject().removeProperty(swb_hasRole);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#removeRole(org.semanticwb.model.Role)
     */
    public void removeRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().removeObjectProperty(swb_hasRole,role.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleableBase#getRole()
     */
    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the security question.
     * 
     * @return the security question
     */
    public int getSecurityQuestion()
    {
        return getSemanticObject().getIntProperty(swb_usrSecurityQuestion);
    }

    /**
     * Sets the security question.
     * 
     * @param value the new security question
     */
    public void setSecurityQuestion(int value)
    {
        getSemanticObject().setIntProperty(swb_usrSecurityQuestion, value);
    }

    /**
     * Gets the security answer.
     * 
     * @return the security answer
     */
    public String getSecurityAnswer()
    {
        return getSemanticObject().getProperty(swb_usrSecurityAnswer);
    }

    /**
     * Sets the security answer.
     * 
     * @param value the new security answer
     */
    public void setSecurityAnswer(String value)
    {
        getSemanticObject().setProperty(swb_usrSecurityAnswer, value);
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
