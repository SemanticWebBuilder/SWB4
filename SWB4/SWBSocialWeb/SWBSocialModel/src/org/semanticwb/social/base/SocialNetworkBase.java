package org.semanticwb.social.base;


   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales. 
   */
public abstract class SocialNetworkBase extends org.semanticwb.model.SWBClass implements org.semanticwb.social.Secreteable,org.semanticwb.social.Listenerable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Clase a Cambiar despues por "Relacional".  En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Cuando un post que llegue por el listener sea tomado como base para crear un nuevo post por la organización, se cree que debe copiarse la información de dicho post de esta clase hacia la clase PostListenerContainerBase.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostListenerContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainer");
   /**
   * Con esta propiedad se puede obtener cuales son los objetos de tipo "PostListenerContainer" que tiene una determinada red social, de esta manera se pudiera saber cuales son los post que han sido recibidos desde una determinada cuenta de una red social, siendo agrupados por año y mes.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostListenerContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostListenerContainer");
   /**
   * Bandera que indica si la red social se encuentra antenticada o no, esta propiedad se maneja desde el sistema. Si la red social regresa que la fecha de expiración de la autenticación ha concluido, se pone en false, para que despues se autentique nuevamente la cuenta de manera manual desde el modulo de cuentas de redes sociales.
   */
    public static final org.semanticwb.platform.SemanticProperty social_sn_authenticated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#sn_authenticated");
   /**
   * Fecha siguiente para busqueda en una red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_nextDatetoSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#nextDatetoSearch");
   /**
   * Clase a Cambiar despues por "Relacional". Clase que contiene todos los post que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostContainer");
   /**
   * Con esta propiedad se puede obtener cuales son los objetos de tipo "PostContainer" que tiene una determinada red social, de esta manera se pudiera saber cuales son los post que han sido enviados a una determinada cuenta de una red social, siendo agrupados por año y mes.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostContainer");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * Esta propiedad inversa me puede decir cuales son los mensajes que han llegado por el Listener por una determinada red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInSocialNetworkInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInSocialNetworkInv");
   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto Post (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese post para c/red social. En el ID de este objeto se colocara el id de ese post en esa red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPost");
   /**
   * Con esta inversa, Cuando se elimine una red social, se eliminaran todos los objetos de tipo SocialPost que este asociados a la misma.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialPostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialPostInv");
   /**
   * Tiempo en que se ira a buscar información a una determinada red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_PoolTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#PoolTime");
   /**
   * Código de pais que servira como filtro, es decir, solo se guardaran los mensajes que se hayan generado en el pais que pertenesca al mismo código de pais que se capture en esta propiedad, esto podría ser para cada instancia (cuenta) de cada red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_countryCodetoSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#countryCodetoSearch");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialNetwork for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialNetwork for all models
       * @return Iterator of org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(it, true);
        }

        public static org.semanticwb.social.SocialNetwork createSocialNetwork(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialNetwork.ClassMgr.createSocialNetwork(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return A org.semanticwb.social.SocialNetwork
       */
        public static org.semanticwb.social.SocialNetwork getSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetwork)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return A org.semanticwb.social.SocialNetwork
       */
        public static org.semanticwb.social.SocialNetwork createSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetwork)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       */
        public static void removeSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return true if the org.semanticwb.social.SocialNetwork exists, false otherwise
       */

        public static boolean hasSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialNetwork(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostListenerContainer
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostListenerContainer(org.semanticwb.social.PostListenerContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostListenerContainer
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostListenerContainer(org.semanticwb.social.PostListenerContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostContainer(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostContainer(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostInSocialNetworkInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.SocialPost
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialPostInv(org.semanticwb.social.SocialPost value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.SocialPost
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialPostInv(org.semanticwb.social.SocialPost value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialNetworkBase.ClassMgr getSocialNetworkClassMgr()
    {
        return new SocialNetworkBase.ClassMgr();
    }

   /**
   * Constructs a SocialNetworkBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialNetwork
   */
    public SocialNetworkBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.social.PostListenerContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostListenerContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer>(getSemanticObject().listObjectProperties(social_hasPostListenerContainer));
    }

   /**
   * Gets true if has a PostListenerContainer
   * @param value org.semanticwb.social.PostListenerContainer to verify
   * @return true if the org.semanticwb.social.PostListenerContainer exists, false otherwise
   */
    public boolean hasPostListenerContainer(org.semanticwb.social.PostListenerContainer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostListenerContainer,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PostListenerContainer
   * @param value org.semanticwb.social.PostListenerContainer to add
   */

    public void addPostListenerContainer(org.semanticwb.social.PostListenerContainer value)
    {
        getSemanticObject().addObjectProperty(social_hasPostListenerContainer, value.getSemanticObject());
    }
   /**
   * Removes all the PostListenerContainer
   */

    public void removeAllPostListenerContainer()
    {
        getSemanticObject().removeProperty(social_hasPostListenerContainer);
    }
   /**
   * Removes a PostListenerContainer
   * @param value org.semanticwb.social.PostListenerContainer to remove
   */

    public void removePostListenerContainer(org.semanticwb.social.PostListenerContainer value)
    {
        getSemanticObject().removeObjectProperty(social_hasPostListenerContainer,value.getSemanticObject());
    }

   /**
   * Gets the PostListenerContainer
   * @return a org.semanticwb.social.PostListenerContainer
   */
    public org.semanticwb.social.PostListenerContainer getPostListenerContainer()
    {
         org.semanticwb.social.PostListenerContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostListenerContainer);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostListenerContainer)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Sn_authenticated property
* @return boolean with the Sn_authenticated
*/
    public boolean isSn_authenticated()
    {
        return getSemanticObject().getBooleanProperty(social_sn_authenticated);
    }

/**
* Sets the Sn_authenticated property
* @param value long with the Sn_authenticated
*/
    public void setSn_authenticated(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_sn_authenticated, value);
    }

/**
* Gets the NextDatetoSearch property
* @return String with the NextDatetoSearch
*/
    public String getNextDatetoSearch()
    {
        return getSemanticObject().getProperty(social_nextDatetoSearch);
    }

/**
* Sets the NextDatetoSearch property
* @param value long with the NextDatetoSearch
*/
    public void setNextDatetoSearch(String value)
    {
        getSemanticObject().setProperty(social_nextDatetoSearch, value);
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
   * Gets all the org.semanticwb.social.PostContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> listPostContainers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer>(getSemanticObject().listObjectProperties(social_hasPostContainer));
    }

   /**
   * Gets true if has a PostContainer
   * @param value org.semanticwb.social.PostContainer to verify
   * @return true if the org.semanticwb.social.PostContainer exists, false otherwise
   */
    public boolean hasPostContainer(org.semanticwb.social.PostContainer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostContainer,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PostContainer
   * @param value org.semanticwb.social.PostContainer to add
   */

    public void addPostContainer(org.semanticwb.social.PostContainer value)
    {
        getSemanticObject().addObjectProperty(social_hasPostContainer, value.getSemanticObject());
    }
   /**
   * Removes all the PostContainer
   */

    public void removeAllPostContainer()
    {
        getSemanticObject().removeProperty(social_hasPostContainer);
    }
   /**
   * Removes a PostContainer
   * @param value org.semanticwb.social.PostContainer to remove
   */

    public void removePostContainer(org.semanticwb.social.PostContainer value)
    {
        getSemanticObject().removeObjectProperty(social_hasPostContainer,value.getSemanticObject());
    }

   /**
   * Gets the PostContainer
   * @return a org.semanticwb.social.PostContainer
   */
    public org.semanticwb.social.PostContainer getPostContainer()
    {
         org.semanticwb.social.PostContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostContainer);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostContainer)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInSocialNetworkInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInSocialNetworkInv));
    }

   /**
   * Gets true if has a PostInSocialNetworkInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInSocialNetworkInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInSocialNetworkInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInSocialNetworkInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInSocialNetworkInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.SocialPost
   * @return A GenericIterator with all the org.semanticwb.social.SocialPost
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPost> listSocialPostInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPost>(getSemanticObject().listObjectProperties(social_hasSocialPostInv));
    }

   /**
   * Gets true if has a SocialPostInv
   * @param value org.semanticwb.social.SocialPost to verify
   * @return true if the org.semanticwb.social.SocialPost exists, false otherwise
   */
    public boolean hasSocialPostInv(org.semanticwb.social.SocialPost value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialPostInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialPostInv
   * @return a org.semanticwb.social.SocialPost
   */
    public org.semanticwb.social.SocialPost getSocialPostInv()
    {
         org.semanticwb.social.SocialPost ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialPostInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialPost)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PoolTime property
* @return int with the PoolTime
*/
    public int getPoolTime()
    {
        return getSemanticObject().getIntProperty(social_PoolTime);
    }

/**
* Sets the PoolTime property
* @param value long with the PoolTime
*/
    public void setPoolTime(int value)
    {
        getSemanticObject().setIntProperty(social_PoolTime, value);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

/**
* Gets the CountryCodetoSearch property
* @return String with the CountryCodetoSearch
*/
    public String getCountryCodetoSearch()
    {
        return getSemanticObject().getProperty(social_countryCodetoSearch);
    }

/**
* Sets the CountryCodetoSearch property
* @param value long with the CountryCodetoSearch
*/
    public void setCountryCodetoSearch(String value)
    {
        getSemanticObject().setProperty(social_countryCodetoSearch, value);
    }

/**
* Gets the AppKey property
* @return String with the AppKey
*/
    public String getAppKey()
    {
        return getSemanticObject().getProperty(social_appKey);
    }

/**
* Sets the AppKey property
* @param value long with the AppKey
*/
    public void setAppKey(String value)
    {
        getSemanticObject().setProperty(social_appKey, value);
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
* Gets the SecretKey property
* @return String with the SecretKey
*/
    public String getSecretKey()
    {
        return getSemanticObject().getProperty(social_secretKey);
    }

/**
* Sets the SecretKey property
* @param value long with the SecretKey
*/
    public void setSecretKey(String value)
    {
        getSemanticObject().setProperty(social_secretKey, value);
    }
}
