package org.semanticwb.social.base;


   /**
   * Clase a Cambiar despues por "Relacional".  En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Cuando un post que llegue por el listener sea tomado como base para crear un nuevo post por la organización, se cree que debe copiarse la información de dicho post de esta clase hacia la clase PostListenerContainerBase.Se almacenan por mes y año, de esta manera sera mucho mas rapido hacer las busquedas sobre las instancias de esta clase. 
   */
public abstract class PostListenerContainerBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Mes en el cual es creada una instancia de PostListenerContainer,esta propiedad servira mucho  para buscar los postIn de manera optima, ya que pudieran ser muchos en un corto tiempo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_plc_month=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#plc_month");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * Referencia a los Post que llegan por el Listener y que son referenciados desde esta para que puedan ser mas facilmente encontrados, debido a que esta clase (PostListenerContainer) tiene una propiedad de año y mes en los cuales pueden ser buscados dichos post.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPlc_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPlc_Post");
   /**
   * Año en el cual es creada una instancia de PostListenerContainer, esta propiedad servira mucho  para buscar los postIn de manera optima, ya que pudieran ser muchos en un corto tiempo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_plc_year=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#plc_year");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Cuenta de Red Social a la que pertenece el grupo de postIn que llega por el Listener y que son agrupados por mes y año.
   */
    public static final org.semanticwb.platform.SemanticProperty social_plc_SocialNetworkInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#plc_SocialNetworkInv");
   /**
   * Clase a Cambiar despues por "Relacional".  En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Cuando un post que llegue por el listener sea tomado como base para crear un nuevo post por la organización, se cree que debe copiarse la información de dicho post de esta clase hacia la clase PostListenerContainerBase.Se almacenan por mes y año, de esta manera sera mucho mas rapido hacer las busquedas sobre las instancias de esta clase.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostListenerContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainer");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainer");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostListenerContainer for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostListenerContainer for all models
       * @return Iterator of org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer>(it, true);
        }

        public static org.semanticwb.social.PostListenerContainer createPostListenerContainer(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PostListenerContainer.ClassMgr.createPostListenerContainer(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PostListenerContainer
       * @param id Identifier for org.semanticwb.social.PostListenerContainer
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       * @return A org.semanticwb.social.PostListenerContainer
       */
        public static org.semanticwb.social.PostListenerContainer getPostListenerContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostListenerContainer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostListenerContainer
       * @param id Identifier for org.semanticwb.social.PostListenerContainer
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       * @return A org.semanticwb.social.PostListenerContainer
       */
        public static org.semanticwb.social.PostListenerContainer createPostListenerContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostListenerContainer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostListenerContainer
       * @param id Identifier for org.semanticwb.social.PostListenerContainer
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       */
        public static void removePostListenerContainer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostListenerContainer
       * @param id Identifier for org.semanticwb.social.PostListenerContainer
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       * @return true if the org.semanticwb.social.PostListenerContainer exists, false otherwise
       */

        public static boolean hasPostListenerContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostListenerContainer(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Plc_Post
       * @param value Plc_Post of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByPlc_Post(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPlc_Post, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Plc_Post
       * @param value Plc_Post of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByPlc_Post(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPlc_Post,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Plc_SocialNetworkInv
       * @param value Plc_SocialNetworkInv of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByPlc_SocialNetworkInv(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_plc_SocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Plc_SocialNetworkInv
       * @param value Plc_SocialNetworkInv of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByPlc_SocialNetworkInv(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_plc_SocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostListenerContainerBase.ClassMgr getPostListenerContainerClassMgr()
    {
        return new PostListenerContainerBase.ClassMgr();
    }

   /**
   * Constructs a PostListenerContainerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostListenerContainer
   */
    public PostListenerContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
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
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
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
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
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

/**
* Gets the Plc_month property
* @return String with the Plc_month
*/
    public String getPlc_month()
    {
        return getSemanticObject().getProperty(social_plc_month);
    }

/**
* Sets the Plc_month property
* @param value long with the Plc_month
*/
    public void setPlc_month(String value)
    {
        getSemanticObject().setProperty(social_plc_month, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPlc_Posts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPlc_Post));
    }

   /**
   * Gets true if has a Plc_Post
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPlc_Post(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPlc_Post,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Plc_Post
   * @param value org.semanticwb.social.PostIn to add
   */

    public void addPlc_Post(org.semanticwb.social.PostIn value)
    {
        getSemanticObject().addObjectProperty(social_hasPlc_Post, value.getSemanticObject());
    }
   /**
   * Removes all the Plc_Post
   */

    public void removeAllPlc_Post()
    {
        getSemanticObject().removeProperty(social_hasPlc_Post);
    }
   /**
   * Removes a Plc_Post
   * @param value org.semanticwb.social.PostIn to remove
   */

    public void removePlc_Post(org.semanticwb.social.PostIn value)
    {
        getSemanticObject().removeObjectProperty(social_hasPlc_Post,value.getSemanticObject());
    }

   /**
   * Gets the Plc_Post
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPlc_Post()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPlc_Post);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Plc_year property
* @return String with the Plc_year
*/
    public String getPlc_year()
    {
        return getSemanticObject().getProperty(social_plc_year);
    }

/**
* Sets the Plc_year property
* @param value long with the Plc_year
*/
    public void setPlc_year(String value)
    {
        getSemanticObject().setProperty(social_plc_year, value);
    }
   /**
   * Sets the value for the property Creator
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
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
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
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
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
   /**
   * Sets the value for the property Plc_SocialNetworkInv
   * @param value Plc_SocialNetworkInv to set
   */

    public void setPlc_SocialNetworkInv(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_plc_SocialNetworkInv, value.getSemanticObject());
        }else
        {
            removePlc_SocialNetworkInv();
        }
    }
   /**
   * Remove the value for Plc_SocialNetworkInv property
   */

    public void removePlc_SocialNetworkInv()
    {
        getSemanticObject().removeProperty(social_plc_SocialNetworkInv);
    }

   /**
   * Gets the Plc_SocialNetworkInv
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getPlc_SocialNetworkInv()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_plc_SocialNetworkInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
}
