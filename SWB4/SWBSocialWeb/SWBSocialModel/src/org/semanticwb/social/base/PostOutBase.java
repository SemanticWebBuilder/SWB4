package org.semanticwb.social.base;


   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer. 
   */
public abstract class PostOutBase extends org.semanticwb.social.Post implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable
{
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * PostIn que sirvió como origen de esta instancia de PostOut
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInOrigen=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInOrigen");
   /**
   * Propiedad que indica si el mensaje de salida (PostOut) se origina de un mensaje que estamos solamente compartiendo de otro mensaje que llego desde PostIn, es decir, desde la red social Twitter, sería como darle a un mensaje de otra persona "retweet" y desde facebook, sería como darle a un mensaje de otra persona "Share".
   */
    public static final org.semanticwb.platform.SemanticProperty social_isMsgShared=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#isMsgShared");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Redes Sociales a las que se envía el post de salida (PostOut)
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetwork");
   /**
   * Clase a Cambiar despues por "Relacional". Clase que contiene TODOS los post de salida (PostOut) que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas. De acuerdo al PostOut, puede buscar si este existe en la clase PostListenerContainerBase, si si existe, quiere decir que es un PostOut que se origino mediante la contestación de un PostIn, si no existe en esa clase, es que es un PostOut que se origino como nuevo, sin que haya sido contestación de un PostIn.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutContainer");
   /**
   * Propiedad inversa que me regresa los postContainers con los cuales tiene referencia un objeto(instancia) PostOut, recordemos que se crea una instancia de PostContainer por una red social, de modo que si se envía un postOut a varias redes sociales(lo cual puede pasar) pues se deverían de crear varios postContainers (Si es que no hay ya creados) por cada una de estas redes sociales a las cuales se envía el PostOut.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostContainer_PostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostContainer_PostInv");
   /**
   * Instancia de un recurso asociado a un flujo de publicación.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowInstance");
    public static final org.semanticwb.platform.SemanticProperty social_pflowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pflowInstance");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
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
       * Gets all org.semanticwb.social.PostOut with a determined PostInOrigen
       * @param value PostInOrigen of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostInOrigen(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInOrigen, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostInOrigen
       * @param value PostInOrigen of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostInOrigen(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInOrigen,value.getSemanticObject(),sclass));
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
       * Gets all org.semanticwb.social.PostOut with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostContainer_PostInv(org.semanticwb.social.PostOutContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostContainer_PostInv(org.semanticwb.social.PostOutContainer value)
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
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPflowInstance(org.semanticwb.social.SocialPFlowInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_pflowInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PflowInstance
       * @param value PflowInstance of the type org.semanticwb.social.SocialPFlowInstance
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPflowInstance(org.semanticwb.social.SocialPFlowInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_pflowInstance,value.getSemanticObject(),sclass));
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
   * Sets the value for the property PostInOrigen
   * @param value PostInOrigen to set
   */

    public void setPostInOrigen(org.semanticwb.social.PostIn value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postInOrigen, value.getSemanticObject());
        }else
        {
            removePostInOrigen();
        }
    }
   /**
   * Remove the value for PostInOrigen property
   */

    public void removePostInOrigen()
    {
        getSemanticObject().removeProperty(social_postInOrigen);
    }

   /**
   * Gets the PostInOrigen
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInOrigen()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postInOrigen);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the IsMsgShared property
* @return boolean with the IsMsgShared
*/
    public boolean isIsMsgShared()
    {
        return getSemanticObject().getBooleanProperty(social_isMsgShared);
    }

/**
* Sets the IsMsgShared property
* @param value long with the IsMsgShared
*/
    public void setIsMsgShared(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_isMsgShared, value);
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
   * Gets all the org.semanticwb.social.SocialNetwork
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetwork
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> listSocialNetworks()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(getSemanticObject().listObjectProperties(social_hasSocialNetwork));
    }

   /**
   * Gets true if has a SocialNetwork
   * @param value org.semanticwb.social.SocialNetwork to verify
   * @return true if the org.semanticwb.social.SocialNetwork exists, false otherwise
   */
    public boolean hasSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetwork,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SocialNetwork
   * @param value org.semanticwb.social.SocialNetwork to add
   */

    public void addSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        getSemanticObject().addObjectProperty(social_hasSocialNetwork, value.getSemanticObject());
    }
   /**
   * Removes all the SocialNetwork
   */

    public void removeAllSocialNetwork()
    {
        getSemanticObject().removeProperty(social_hasSocialNetwork);
    }
   /**
   * Removes a SocialNetwork
   * @param value org.semanticwb.social.SocialNetwork to remove
   */

    public void removeSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        getSemanticObject().removeObjectProperty(social_hasSocialNetwork,value.getSemanticObject());
    }

   /**
   * Gets the SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.PostOutContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostOutContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> listPostContainer_PostInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer>(getSemanticObject().listObjectProperties(social_hasPostContainer_PostInv));
    }

   /**
   * Gets true if has a PostContainer_PostInv
   * @param value org.semanticwb.social.PostOutContainer to verify
   * @return true if the org.semanticwb.social.PostOutContainer exists, false otherwise
   */
    public boolean hasPostContainer_PostInv(org.semanticwb.social.PostOutContainer value)
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
   * @return a org.semanticwb.social.PostOutContainer
   */
    public org.semanticwb.social.PostOutContainer getPostContainer_PostInv()
    {
         org.semanticwb.social.PostOutContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostContainer_PostInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOutContainer)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property PflowInstance
   * @param value PflowInstance to set
   */

    public void setPflowInstance(org.semanticwb.social.SocialPFlowInstance value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_pflowInstance, value.getSemanticObject());
        }else
        {
            removePflowInstance();
        }
    }
   /**
   * Remove the value for PflowInstance property
   */

    public void removePflowInstance()
    {
        getSemanticObject().removeProperty(social_pflowInstance);
    }

   /**
   * Gets the PflowInstance
   * @return a org.semanticwb.social.SocialPFlowInstance
   */
    public org.semanticwb.social.SocialPFlowInstance getPflowInstance()
    {
         org.semanticwb.social.SocialPFlowInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_pflowInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialPFlowInstance)obj.createGenericInstance();
         }
         return ret;
    }
}
