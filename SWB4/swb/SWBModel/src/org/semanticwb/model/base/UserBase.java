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
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso. 
   */
public abstract class UserBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.CalendarRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Activeable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Roleable,org.semanticwb.model.Filterable,org.semanticwb.model.Expirable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Traceable
{
   
   /** Catalogo de paises. */
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
   
   /** Objeto que representa una relacion de favoritos entre un usuario y algun elemento del arbol de navegacion dentro de la administración de SWB. */
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
   /**
   * Un Filtro permite configurar un recurso para que se despliegue sólo en ciertas páginas dentro de un Sitio Web, es decir, restringe el acceso a ciertas funcionalidades a nivel de navegación.
   */
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
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of User for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.User
        */

        public static java.util.Iterator<org.semanticwb.model.User> listUsers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.User for all models
       * @return Iterator of org.semanticwb.model.User
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
       * Gets a org.semanticwb.model.User
       * @param id Identifier for org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.User
       * @return A org.semanticwb.model.User
       */
        public static org.semanticwb.model.User getUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.User)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.User
       * @param id Identifier for org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.User
       * @return A org.semanticwb.model.User
       */
        public static org.semanticwb.model.User createUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.User)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.User
       * @param id Identifier for org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.User
       */
        public static void removeUser(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.User
       * @param id Identifier for org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.User
       * @return true if the org.semanticwb.model.User exists, false otherwise
       */

        public static boolean hasUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUser(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.User with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_usrCountry, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_usrCountry,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined UserFavorite
       * @param value UserFavorite of the type org.semanticwb.model.UserFavorite
       * @param model Model of the org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByUserFavorite(org.semanticwb.model.UserFavorite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_usrFavorite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined UserFavorite
       * @param value UserFavorite of the type org.semanticwb.model.UserFavorite
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByUserFavorite(org.semanticwb.model.UserFavorite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_usrFavorite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined AdminFilter
       * @param value AdminFilter of the type org.semanticwb.model.AdminFilter
       * @param model Model of the org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByAdminFilter(org.semanticwb.model.AdminFilter value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAdminFilter, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined AdminFilter
       * @param value AdminFilter of the type org.semanticwb.model.AdminFilter
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByAdminFilter(org.semanticwb.model.AdminFilter value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAdminFilter,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.User with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.model.User
       */

        public static java.util.Iterator<org.semanticwb.model.User> listUserByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.User> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a UserBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the User
    */
    public UserBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property Country.
    * 
    * @param value Country to set
    */

    public void setCountry(org.semanticwb.model.Country value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_usrCountry, value.getSemanticObject());
        }else
        {
            removeCountry();
        }
    }
   
   /**
    * Remove the value for Country property.
    */

    public void removeCountry()
    {
        getSemanticObject().removeProperty(swb_usrCountry);
    }

   /**
    * Gets the Country.
    * 
    * @return a org.semanticwb.model.Country
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
 * Gets the LastName property.
 * 
 * @return String with the LastName
 */
    public String getLastName()
    {
        return getSemanticObject().getProperty(swb_usrLastName);
    }

/**
 * Sets the LastName property.
 * 
 * @param value long with the LastName
 */
    public void setLastName(String value)
    {
        getSemanticObject().setProperty(swb_usrLastName, value);
    }

/**
 * Gets the ExternalID property.
 * 
 * @return String with the ExternalID
 */
    public String getExternalID()
    {
        return getSemanticObject().getProperty(swb_externalID);
    }

/**
 * Sets the ExternalID property.
 * 
 * @param value long with the ExternalID
 */
    public void setExternalID(String value)
    {
        getSemanticObject().setProperty(swb_externalID, value);
    }

/**
 * Gets the Active property.
 * 
 * @return boolean with the Active
 */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
 * Sets the Active property.
 * 
 * @param value long with the Active
 */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

/**
 * Gets the Photo property.
 * 
 * @return String with the Photo
 */
    public String getPhoto()
    {
        return getSemanticObject().getProperty(swb_usrPhoto);
    }

/**
 * Sets the Photo property.
 * 
 * @param value long with the Photo
 */
    public void setPhoto(String value)
    {
        getSemanticObject().setProperty(swb_usrPhoto, value);
    }
   /**
   * Gets all the org.semanticwb.model.CalendarRef
   * @return A GenericIterator with all the org.semanticwb.model.CalendarRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

   /**
    * Gets true if has a CalendarRef.
    * 
    * @param value org.semanticwb.model.CalendarRef to verify
    * @return true if the org.semanticwb.model.CalendarRef exists, false otherwise
    */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
        }
        return ret;
    }
   
   /**
    * Adds a CalendarRef.
    * 
    * @param value org.semanticwb.model.CalendarRef to add
    */

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }
   
   /**
    * Removes all the CalendarRef.
    */

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }
   
   /**
    * Removes a CalendarRef.
    * 
    * @param value org.semanticwb.model.CalendarRef to remove
    */

    public void removeCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
    }

   /**
    * Gets the CalendarRef.
    * 
    * @return a org.semanticwb.model.CalendarRef
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
 * Gets the RequireConfirm property.
 * 
 * @return boolean with the RequireConfirm
 */
    public boolean isRequireConfirm()
    {
        return getSemanticObject().getBooleanProperty(swb_usrReqConfirm);
    }

/**
 * Sets the RequireConfirm property.
 * 
 * @param value long with the RequireConfirm
 */
    public void setRequireConfirm(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_usrReqConfirm, value);
    }

/**
 * Gets the Created property.
 * 
 * @return java.util.Date with the Created
 */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
 * Sets the Created property.
 * 
 * @param value long with the Created
 */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }
   
   /**
    * Sets the value for the property ModifiedBy.
    * 
    * @param value ModifiedBy to set
    */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   
   /**
    * Remove the value for ModifiedBy property.
    */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
    * Gets the ModifiedBy.
    * 
    * @return a org.semanticwb.model.User
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
 * Gets the SecondLastName property.
 * 
 * @return String with the SecondLastName
 */
    public String getSecondLastName()
    {
        return getSemanticObject().getProperty(swb_usrSecondLastName);
    }

/**
 * Sets the SecondLastName property.
 * 
 * @param value long with the SecondLastName
 */
    public void setSecondLastName(String value)
    {
        getSemanticObject().setProperty(swb_usrSecondLastName, value);
    }

/**
 * Gets the Expiration property.
 * 
 * @return java.util.Date with the Expiration
 */
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

/**
 * Sets the Expiration property.
 * 
 * @param value long with the Expiration
 */
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }
   /**
   * Gets all the org.semanticwb.model.UserGroup
   * @return A GenericIterator with all the org.semanticwb.model.UserGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listUserGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup>(getSemanticObject().listObjectProperties(swb_hasUserGroup));
    }

   /**
    * Gets true if has a UserGroup.
    * 
    * @param value org.semanticwb.model.UserGroup to verify
    * @return true if the org.semanticwb.model.UserGroup exists, false otherwise
    */
    public boolean hasUserGroup(org.semanticwb.model.UserGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroup,value.getSemanticObject());
        }
        return ret;
    }
   
   /**
    * Adds a UserGroup.
    * 
    * @param value org.semanticwb.model.UserGroup to add
    */

    public void addUserGroup(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroup, value.getSemanticObject());
    }
   
   /**
    * Removes all the UserGroup.
    */

    public void removeAllUserGroup()
    {
        getSemanticObject().removeProperty(swb_hasUserGroup);
    }
   
   /**
    * Removes a UserGroup.
    * 
    * @param value org.semanticwb.model.UserGroup to remove
    */

    public void removeUserGroup(org.semanticwb.model.UserGroup value)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroup,value.getSemanticObject());
    }

   /**
    * Gets the UserGroup.
    * 
    * @return a org.semanticwb.model.UserGroup
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
    * Sets the value for the property UserFavorite.
    * 
    * @param value UserFavorite to set
    */

    public void setUserFavorite(org.semanticwb.model.UserFavorite value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_usrFavorite, value.getSemanticObject());
        }else
        {
            removeUserFavorite();
        }
    }
   
   /**
    * Remove the value for UserFavorite property.
    */

    public void removeUserFavorite()
    {
        getSemanticObject().removeProperty(swb_usrFavorite);
    }

   /**
    * Gets the UserFavorite.
    * 
    * @return a org.semanticwb.model.UserFavorite
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
 * Gets the Email property.
 * 
 * @return String with the Email
 */
    public String getEmail()
    {
        return getSemanticObject().getProperty(swb_usrEmail);
    }

/**
 * Sets the Email property.
 * 
 * @param value long with the Email
 */
    public void setEmail(String value)
    {
        getSemanticObject().setProperty(swb_usrEmail, value);
    }

/**
 * Gets the FirstName property.
 * 
 * @return String with the FirstName
 */
    public String getFirstName()
    {
        return getSemanticObject().getProperty(swb_usrFirstName);
    }

/**
 * Sets the FirstName property.
 * 
 * @param value long with the FirstName
 */
    public void setFirstName(String value)
    {
        getSemanticObject().setProperty(swb_usrFirstName, value);
    }

/**
 * Gets the Updated property.
 * 
 * @return java.util.Date with the Updated
 */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
 * Sets the Updated property.
 * 
 * @param value long with the Updated
 */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
 * Gets the Language property.
 * 
 * @return String with the Language
 */
    public String getLanguage()
    {
        return getSemanticObject().getProperty(swb_usrLanguage);
    }

/**
 * Sets the Language property.
 * 
 * @param value long with the Language
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
     * @param value the value
     */
    public void addUserType(String value)
    {
        getSemanticObject().addLiteralProperty(swb_hasUserType, new org.semanticwb.platform.SemanticLiteral(value));
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
     * @param value the value
     */
    public void removeUserType(String value)
    {
        getSemanticObject().removeProperty(swb_hasUserType,value);
    }

/**
 * Gets the PasswordChanged property.
 * 
 * @return java.util.Date with the PasswordChanged
 */
    public java.util.Date getPasswordChanged()
    {
        return getSemanticObject().getDateProperty(swb_usrPasswordChanged);
    }

/**
 * Sets the PasswordChanged property.
 * 
 * @param value long with the PasswordChanged
 */
    public void setPasswordChanged(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_usrPasswordChanged, value);
    }
   /**
   * Gets all the org.semanticwb.model.AdminFilter
   * @return A GenericIterator with all the org.semanticwb.model.AdminFilter
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminFilter> listAdminFilters()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AdminFilter>(getSemanticObject().listObjectProperties(swb_hasAdminFilter));
    }

   /**
    * Gets true if has a AdminFilter.
    * 
    * @param value org.semanticwb.model.AdminFilter to verify
    * @return true if the org.semanticwb.model.AdminFilter exists, false otherwise
    */
    public boolean hasAdminFilter(org.semanticwb.model.AdminFilter value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasAdminFilter,value.getSemanticObject());
        }
        return ret;
    }
   
   /**
    * Adds a AdminFilter.
    * 
    * @param value org.semanticwb.model.AdminFilter to add
    */

    public void addAdminFilter(org.semanticwb.model.AdminFilter value)
    {
        getSemanticObject().addObjectProperty(swb_hasAdminFilter, value.getSemanticObject());
    }
   
   /**
    * Removes all the AdminFilter.
    */

    public void removeAllAdminFilter()
    {
        getSemanticObject().removeProperty(swb_hasAdminFilter);
    }
   
   /**
    * Removes a AdminFilter.
    * 
    * @param value org.semanticwb.model.AdminFilter to remove
    */

    public void removeAdminFilter(org.semanticwb.model.AdminFilter value)
    {
        getSemanticObject().removeObjectProperty(swb_hasAdminFilter,value.getSemanticObject());
    }

   /**
    * Gets the AdminFilter.
    * 
    * @return a org.semanticwb.model.AdminFilter
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
 * Gets the LastLogin property.
 * 
 * @return java.util.Date with the LastLogin
 */
    public java.util.Date getLastLogin()
    {
        return getSemanticObject().getDateProperty(swb_usrLastLogin);
    }

/**
 * Sets the LastLogin property.
 * 
 * @param value long with the LastLogin
 */
    public void setLastLogin(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_usrLastLogin, value);
    }
   
   /**
    * Sets the value for the property Creator.
    * 
    * @param value Creator to set
    */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   
   /**
    * Remove the value for Creator property.
    */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
    * Gets the Creator.
    * 
    * @return a org.semanticwb.model.User
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
 * Gets the Password property.
 * 
 * @return String with the Password
 */
    public String getPassword()
    {
        //Override this method in User object
        return getSemanticObject().getProperty(swb_usrPassword,false);
    }

/**
 * Sets the Password property.
 * 
 * @param value long with the Password
 */
    public void setPassword(String value)
    {
        //Override this method in User object
        getSemanticObject().setProperty(swb_usrPassword, value,false);
    }

/**
 * Gets the Login property.
 * 
 * @return String with the Login
 */
    public String getLogin()
    {
        return getSemanticObject().getProperty(swb_usrLogin);
    }

/**
 * Sets the Login property.
 * 
 * @param value long with the Login
 */
    public void setLogin(String value)
    {
        getSemanticObject().setProperty(swb_usrLogin, value);
    }
   /**
   * Gets all the org.semanticwb.model.Role
   * @return A GenericIterator with all the org.semanticwb.model.Role
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Role>(getSemanticObject().listObjectProperties(swb_hasRole));
    }

   /**
    * Gets true if has a Role.
    * 
    * @param value org.semanticwb.model.Role to verify
    * @return true if the org.semanticwb.model.Role exists, false otherwise
    */
    public boolean hasRole(org.semanticwb.model.Role value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRole,value.getSemanticObject());
        }
        return ret;
    }
   
   /**
    * Adds a Role.
    * 
    * @param value org.semanticwb.model.Role to add
    */

    public void addRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().addObjectProperty(swb_hasRole, value.getSemanticObject());
    }
   
   /**
    * Removes all the Role.
    */

    public void removeAllRole()
    {
        getSemanticObject().removeProperty(swb_hasRole);
    }
   
   /**
    * Removes a Role.
    * 
    * @param value org.semanticwb.model.Role to remove
    */

    public void removeRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRole,value.getSemanticObject());
    }

   /**
    * Gets the Role.
    * 
    * @return a org.semanticwb.model.Role
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
 * Gets the SecurityQuestion property.
 * 
 * @return int with the SecurityQuestion
 */
    public int getSecurityQuestion()
    {
        return getSemanticObject().getIntProperty(swb_usrSecurityQuestion);
    }

/**
 * Sets the SecurityQuestion property.
 * 
 * @param value long with the SecurityQuestion
 */
    public void setSecurityQuestion(int value)
    {
        getSemanticObject().setIntProperty(swb_usrSecurityQuestion, value);
    }

/**
 * Gets the SecurityAnswer property.
 * 
 * @return String with the SecurityAnswer
 */
    public String getSecurityAnswer()
    {
        return getSemanticObject().getProperty(swb_usrSecurityAnswer);
    }

/**
 * Sets the SecurityAnswer property.
 * 
 * @param value long with the SecurityAnswer
 */
    public void setSecurityAnswer(String value)
    {
        getSemanticObject().setProperty(swb_usrSecurityAnswer, value);
    }

   /**
    * Gets the UserRepository.
    * 
    * @return a instance of org.semanticwb.model.UserRepository
    */
    public org.semanticwb.model.UserRepository getUserRepository()
    {
        return (org.semanticwb.model.UserRepository)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
