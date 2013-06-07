package org.semanticwb.social.base;


   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta. 
   */
public abstract class PostInBase extends org.semanticwb.social.Post implements org.semanticwb.social.PostDataable,org.semanticwb.model.Traceable,org.semanticwb.social.PostTextable,org.semanticwb.model.Tagable
{
   /**
   * Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. No se puso como identificador de las instancias de esta clase el id que maneja el usuario en la red social, ya que un identificador de una red social, puede ser el mismo para otra red social, pero obviamnete para otro usuario.Es por ello que se puso como AutoGenID esta clase y por ello se maneja por separado el id de un usuario en una determinada red social, esto en la propiedad snu_id.
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
   * Propiedad con valor entero que representa el tipo de Sentimientos que expresa el Post, estos se estan definiendo de esta manera: 0) Neutro 1) Positivo 2)Negativo, estos valores pueden ser mas y permanecer en un objeto tipo colección en lo futuro.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSentimentalType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSentimentalType");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Red Social de la cual es procedente el mensaje que llega por el Listener.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInSocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInSocialNetwork");
   /**
   * Veces que ha sido re-enviado el post
   */
    public static final org.semanticwb.platform.SemanticProperty social_postRetweets=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postRetweets");
   /**
   * Lugar Geográfico de donde se envía el Post
   */
    public static final org.semanticwb.platform.SemanticProperty social_postPlace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postPlace");
   /**
   * Sentimiento de el mensaje (Post) basado en emoticones encontrados. (1=Positivo;2=Negativo;0=Neutro)
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSentimentalEmoticonType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSentimentalEmoticonType");
   /**
   * Valor que es resultado del algoritmo de intensidad, mediante este valor se puede determinar si la intencidad es alta, media o baja
   */
    public static final org.semanticwb.platform.SemanticProperty social_postIntensityValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postIntensityValue");
   /**
   * Valor que resulta del algoritmo de analisis sentimental, aqui se puede ver el porque se pone cierto valor a la propiedad PostSentimentalType
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSentimentalValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSentimentalValue");
   /**
   * Dispositivo desde el que se ha enviado el post
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSource");
   /**
   * Tipo de Intensidad. 2=Alta;1=Media;0=Baja;
   */
    public static final org.semanticwb.platform.SemanticProperty social_postIntesityType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postIntesityType");
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
* Gets the PostSentimentalType property
* @return int with the PostSentimentalType
*/
    public int getPostSentimentalType()
    {
        return getSemanticObject().getIntProperty(social_postSentimentalType);
    }

/**
* Sets the PostSentimentalType property
* @param value long with the PostSentimentalType
*/
    public void setPostSentimentalType(int value)
    {
        getSemanticObject().setIntProperty(social_postSentimentalType, value);
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
* Gets the PostRetweets property
* @return int with the PostRetweets
*/
    public int getPostRetweets()
    {
        return getSemanticObject().getIntProperty(social_postRetweets);
    }

/**
* Sets the PostRetweets property
* @param value long with the PostRetweets
*/
    public void setPostRetweets(int value)
    {
        getSemanticObject().setIntProperty(social_postRetweets, value);
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
* Gets the PostIntensityValue property
* @return float with the PostIntensityValue
*/
    public float getPostIntensityValue()
    {
        return getSemanticObject().getFloatProperty(social_postIntensityValue);
    }

/**
* Sets the PostIntensityValue property
* @param value long with the PostIntensityValue
*/
    public void setPostIntensityValue(float value)
    {
        getSemanticObject().setFloatProperty(social_postIntensityValue, value);
    }

/**
* Gets the PostSentimentalValue property
* @return float with the PostSentimentalValue
*/
    public float getPostSentimentalValue()
    {
        return getSemanticObject().getFloatProperty(social_postSentimentalValue);
    }

/**
* Sets the PostSentimentalValue property
* @param value long with the PostSentimentalValue
*/
    public void setPostSentimentalValue(float value)
    {
        getSemanticObject().setFloatProperty(social_postSentimentalValue, value);
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
* Gets the PostIntesityType property
* @return int with the PostIntesityType
*/
    public int getPostIntesityType()
    {
        return getSemanticObject().getIntProperty(social_postIntesityType);
    }

/**
* Sets the PostIntesityType property
* @param value long with the PostIntesityType
*/
    public void setPostIntesityType(int value)
    {
        getSemanticObject().setIntProperty(social_postIntesityType, value);
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
