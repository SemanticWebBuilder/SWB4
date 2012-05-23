package org.semanticwb.social.base;


   /**
   * En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Cuando un post que llegue por el listener sea tomado como base para crear un nuevo post por la organización, se cree que debe copiarse la información de dicho post de esta clase hacia la clase PostListenerContainerBase. 
   */
public abstract class PostListenerContainerBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Cuenta de Red Social a la que pertenece el post que llega por el Listener.
   */
    public static final org.semanticwb.platform.SemanticProperty social_plc_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#plc_SocialNetwork");
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * Referencia al Post que es creado por un post que llega por el Listener.
   */
    public static final org.semanticwb.platform.SemanticProperty social_plc_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#plc_Post");
   /**
   * En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Cuando un post que llegue por el listener sea tomado como base para crear un nuevo post por la organización, se cree que debe copiarse la información de dicho post de esta clase hacia la clase PostListenerContainerBase.
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
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Plc_SocialNetwork
       * @param value Plc_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostListenerContainer
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByPlc_SocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_plc_SocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Plc_SocialNetwork
       * @param value Plc_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByPlc_SocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_plc_SocialNetwork,value.getSemanticObject(),sclass));
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
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_plc_Post, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainer with a determined Plc_Post
       * @param value Plc_Post of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostListenerContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainer> listPostListenerContainerByPlc_Post(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_plc_Post,value.getSemanticObject(),sclass));
            return it;
        }
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
   * Sets the value for the property Plc_SocialNetwork
   * @param value Plc_SocialNetwork to set
   */

    public void setPlc_SocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_plc_SocialNetwork, value.getSemanticObject());
        }else
        {
            removePlc_SocialNetwork();
        }
    }
   /**
   * Remove the value for Plc_SocialNetwork property
   */

    public void removePlc_SocialNetwork()
    {
        getSemanticObject().removeProperty(social_plc_SocialNetwork);
    }

   /**
   * Gets the Plc_SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getPlc_SocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_plc_SocialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Plc_Post
   * @param value Plc_Post to set
   */

    public void setPlc_Post(org.semanticwb.social.PostIn value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_plc_Post, value.getSemanticObject());
        }else
        {
            removePlc_Post();
        }
    }
   /**
   * Remove the value for Plc_Post property
   */

    public void removePlc_Post()
    {
        getSemanticObject().removeProperty(social_plc_Post);
    }

   /**
   * Gets the Plc_Post
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPlc_Post()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_plc_Post);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }
}
