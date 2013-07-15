package org.semanticwb.social.base;


   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta. 
   */
public abstract class PostInBase extends org.semanticwb.social.Post implements org.semanticwb.model.Traceable,org.semanticwb.social.PostTextable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Tagable,org.semanticwb.social.PostDataable
{
   /**
   * Clase a Cambiar despues por "Relacional".Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. No se puso como identificador de las instancias de esta clase el id que maneja el usuario en la red social, ya que un identificador de una red social, puede ser el mismo para otra red social, pero obviamnete para otro usuario.Es por ello que se puso como AutoGenID esta clase y por ello se maneja por separado el id de un usuario en una determinada red social, esto en la propiedad snu_id.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");
   /**
   * Usuario creador del Post que llega por el Listener (PostIn). TODO: Al borrar un PostIn, no borro al SocialNetworkUser porque este podría obviamente tenear mas PostIn asociados. Tengo que despues revisar para que si ya un usuario no tiene PostIn(Porque x algun modo se hayan borrado) borre al SocialNetworkUser, ya que de no ser así se quedaran como basura en la BD, haciendo que esta cresca muchisimo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInSocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInSocialNetworkUser");
   /**
   * Identificador del mensaje que llega en la red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_socialNetMsgId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#socialNetMsgId");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Red Social de la cual es procedente el mensaje que llega por el Listener.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInSocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInSocialNetwork");
   /**
   * Lugar Geográfico de donde se envía el Post
   */
    public static final org.semanticwb.platform.SemanticProperty social_postPlace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postPlace");
   /**
   * Sentimiento de el mensaje (Post) basado en emoticones encontrados. (1=Positivo;2=Negativo;0=Neutro)
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSentimentalEmoticonType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSentimentalEmoticonType");
   /**
   * Veces que ha sido re-enviado o compartido el post
   */
    public static final org.semanticwb.platform.SemanticProperty social_postShared=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postShared");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * Inversa que nos dice que PostOut han sido creados a manera de respuesta de esta instancia de PostIn.
   */
    public static final org.semanticwb.platform.SemanticProperty social_haspostOutResponseInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#haspostOutResponseInv");
   /**
   * Dispositivo desde el que se ha enviado el post
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSource");
   /**
   * Clase que contendra los streams que configurados para cada usuario
   */
    public static final org.semanticwb.platform.SemanticClass social_Stream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Stream");
   /**
   * Stream por el que llega el mensaje de entrada
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInStream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInStream");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostIn for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostIns(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostIn for all models
       * @return Iterator of org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostIns()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return A org.semanticwb.social.PostIn
       */
        public static org.semanticwb.social.PostIn getPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostIn)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return A org.semanticwb.social.PostIn
       */
        public static org.semanticwb.social.PostIn createPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostIn)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       */
        public static void removePostIn(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return true if the org.semanticwb.social.PostIn exists, false otherwise
       */

        public static boolean hasPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostIn(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInSocialNetworkUser
       * @param value PostInSocialNetworkUser of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetworkUser,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInSocialNetwork
       * @param value PostInSocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInSocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostOutResponseInv
       * @param value PostOutResponseInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostOutResponseInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspostOutResponseInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostOutResponseInv
       * @param value PostOutResponseInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostOutResponseInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspostOutResponseInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInStream
       * @param value PostInStream of the type org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInStream(org.semanticwb.social.Stream value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInStream, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInStream
       * @param value PostInStream of the type org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInStream(org.semanticwb.social.Stream value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInStream,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostInBase.ClassMgr getPostInClassMgr()
    {
        return new PostInBase.ClassMgr();
    }

   /**
   * Constructs a PostInBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostIn
   */
    public PostInBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property PostInSocialNetworkUser
   * @param value PostInSocialNetworkUser to set
   */

    public void setPostInSocialNetworkUser(org.semanticwb.social.SocialNetworkUser value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postInSocialNetworkUser, value.getSemanticObject());
        }else
        {
            removePostInSocialNetworkUser();
        }
    }
   /**
   * Remove the value for PostInSocialNetworkUser property
   */

    public void removePostInSocialNetworkUser()
    {
        getSemanticObject().removeProperty(social_postInSocialNetworkUser);
    }

   /**
   * Gets the PostInSocialNetworkUser
   * @return a org.semanticwb.social.SocialNetworkUser
   */
    public org.semanticwb.social.SocialNetworkUser getPostInSocialNetworkUser()
    {
         org.semanticwb.social.SocialNetworkUser ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postInSocialNetworkUser);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetworkUser)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the SocialNetMsgId property
* @return String with the SocialNetMsgId
*/
    public String getSocialNetMsgId()
    {
        return getSemanticObject().getProperty(social_socialNetMsgId);
    }

/**
* Sets the SocialNetMsgId property
* @param value long with the SocialNetMsgId
*/
    public void setSocialNetMsgId(String value)
    {
        getSemanticObject().setProperty(social_socialNetMsgId, value);
    }
   /**
   * Sets the value for the property PostInSocialNetwork
   * @param value PostInSocialNetwork to set
   */

    public void setPostInSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postInSocialNetwork, value.getSemanticObject());
        }else
        {
            removePostInSocialNetwork();
        }
    }
   /**
   * Remove the value for PostInSocialNetwork property
   */

    public void removePostInSocialNetwork()
    {
        getSemanticObject().removeProperty(social_postInSocialNetwork);
    }

   /**
   * Gets the PostInSocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getPostInSocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postInSocialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PostPlace property
* @return String with the PostPlace
*/
    public String getPostPlace()
    {
        return getSemanticObject().getProperty(social_postPlace);
    }

/**
* Sets the PostPlace property
* @param value long with the PostPlace
*/
    public void setPostPlace(String value)
    {
        getSemanticObject().setProperty(social_postPlace, value);
    }

/**
* Gets the PostSentimentalEmoticonType property
* @return int with the PostSentimentalEmoticonType
*/
    public int getPostSentimentalEmoticonType()
    {
        return getSemanticObject().getIntProperty(social_postSentimentalEmoticonType);
    }

/**
* Sets the PostSentimentalEmoticonType property
* @param value long with the PostSentimentalEmoticonType
*/
    public void setPostSentimentalEmoticonType(int value)
    {
        getSemanticObject().setIntProperty(social_postSentimentalEmoticonType, value);
    }

/**
* Gets the PostShared property
* @return int with the PostShared
*/
    public int getPostShared()
    {
        return getSemanticObject().getIntProperty(social_postShared);
    }

/**
* Sets the PostShared property
* @param value long with the PostShared
*/
    public void setPostShared(int value)
    {
        getSemanticObject().setIntProperty(social_postShared, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostOut
   * @return A GenericIterator with all the org.semanticwb.social.PostOut
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> listpostOutResponseInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(getSemanticObject().listObjectProperties(social_haspostOutResponseInv));
    }

   /**
   * Gets true if has a postOutResponseInv
   * @param value org.semanticwb.social.PostOut to verify
   * @return true if the org.semanticwb.social.PostOut exists, false otherwise
   */
    public boolean haspostOutResponseInv(org.semanticwb.social.PostOut value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_haspostOutResponseInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the postOutResponseInv
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getpostOutResponseInv()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_haspostOutResponseInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PostSource property
* @return String with the PostSource
*/
    public String getPostSource()
    {
        return getSemanticObject().getProperty(social_postSource);
    }

/**
* Sets the PostSource property
* @param value long with the PostSource
*/
    public void setPostSource(String value)
    {
        getSemanticObject().setProperty(social_postSource, value);
    }
   /**
   * Sets the value for the property PostInStream
   * @param value PostInStream to set
   */

    public void setPostInStream(org.semanticwb.social.Stream value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postInStream, value.getSemanticObject());
        }else
        {
            removePostInStream();
        }
    }
   /**
   * Remove the value for PostInStream property
   */

    public void removePostInStream()
    {
        getSemanticObject().removeProperty(social_postInStream);
    }

   /**
   * Gets the PostInStream
   * @return a org.semanticwb.social.Stream
   */
    public org.semanticwb.social.Stream getPostInStream()
    {
         org.semanticwb.social.Stream ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postInStream);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Stream)obj.createGenericInstance();
         }
         return ret;
    }
}
