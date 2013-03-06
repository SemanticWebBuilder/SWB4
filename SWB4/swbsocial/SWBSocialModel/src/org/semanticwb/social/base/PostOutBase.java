package org.semanticwb.social.base;


   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. 
   */
public abstract class PostOutBase extends org.semanticwb.social.Post implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable
{
   /**
   * Clase a Cambiar despues por "Relacional".  Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales.Si se eliminan un post que han sifo tomados como base(PostIn), se debe de eliminar la instancia asociada de esta clase (en la propiedad plcb_Post).
   */
    public static final org.semanticwb.platform.SemanticClass social_PostListenerContainerBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainerBase");
   /**
   * En esta propiedad se guarda el post que llegó por el listener y que sirvió de base para que se creara un post nuevo desde SSMCC.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postListenerBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postListenerBase");
   /**
   * Clase a Cambiar despues por "Relacional". Clase que contiene todos los post que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostContainer");
   /**
   * Propiedad inversa que me regresa los postContainers con los cuales tiene referencia un objeto(instancia) PostOut, recordemos que se crea una instancia de PostContainer por una red social, de modo que si se envía un postOut a varias redes sociales(lo cual puede pasar) pues se deverían de crear varios postContainers (Si es que no hay ya creados) por cada una de las estas redes sociales a las cuales se envía el PostOut.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostContainer_PostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostContainer_PostInv");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOut for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOuts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOut for all models
       * @return Iterator of org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOuts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostOut
       * @param id Identifier for org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOut
       * @return A org.semanticwb.social.PostOut
       */
        public static org.semanticwb.social.PostOut getPostOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOut)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOut
       * @param id Identifier for org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOut
       * @return A org.semanticwb.social.PostOut
       */
        public static org.semanticwb.social.PostOut createPostOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOut)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOut
       * @param id Identifier for org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOut
       */
        public static void removePostOut(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOut
       * @param id Identifier for org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOut
       * @return true if the org.semanticwb.social.PostOut exists, false otherwise
       */

        public static boolean hasPostOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOut(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostListenerBase
       * @param value PostListenerBase of the type org.semanticwb.social.PostListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostListenerBase(org.semanticwb.social.PostListenerContainerBase value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postListenerBase, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostListenerBase
       * @param value PostListenerBase of the type org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostListenerBase(org.semanticwb.social.PostListenerContainerBase value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postListenerBase,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostContainer_PostInv(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostContainer_PostInv(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutBase.ClassMgr getPostOutClassMgr()
    {
        return new PostOutBase.ClassMgr();
    }

   /**
   * Constructs a PostOutBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOut
   */
    public PostOutBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property PostListenerBase
   * @param value PostListenerBase to set
   */

    public void setPostListenerBase(org.semanticwb.social.PostListenerContainerBase value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postListenerBase, value.getSemanticObject());
        }else
        {
            removePostListenerBase();
        }
    }
   /**
   * Remove the value for PostListenerBase property
   */

    public void removePostListenerBase()
    {
        getSemanticObject().removeProperty(social_postListenerBase);
    }

   /**
   * Gets the PostListenerBase
   * @return a org.semanticwb.social.PostListenerContainerBase
   */
    public org.semanticwb.social.PostListenerContainerBase getPostListenerBase()
    {
         org.semanticwb.social.PostListenerContainerBase ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postListenerBase);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostListenerContainerBase)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.social.PostContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> listPostContainer_PostInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer>(getSemanticObject().listObjectProperties(social_hasPostContainer_PostInv));
    }

   /**
   * Gets true if has a PostContainer_PostInv
   * @param value org.semanticwb.social.PostContainer to verify
   * @return true if the org.semanticwb.social.PostContainer exists, false otherwise
   */
    public boolean hasPostContainer_PostInv(org.semanticwb.social.PostContainer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostContainer_PostInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostContainer_PostInv
   * @return a org.semanticwb.social.PostContainer
   */
    public org.semanticwb.social.PostContainer getPostContainer_PostInv()
    {
         org.semanticwb.social.PostContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostContainer_PostInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostContainer)obj.createGenericInstance();
         }
         return ret;
    }
}
