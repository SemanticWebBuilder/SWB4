package org.semanticwb.social.base;


   /**
   * Clase a Cambiar despues por "Relacional", esta y todas sus hijas. Clase que comprende todos los tipos de Post que pueden ir siendo creados en la herramienta.. 
   */
public abstract class PostBase extends org.semanticwb.model.SWBClass implements org.semanticwb.social.PostDataable,org.semanticwb.model.Tagable,org.semanticwb.social.PostTextable,org.semanticwb.model.Traceable
{
   /**
   * Propiedad con valor entero que representa el tipo de Sentimientos que expresa el Post, estos se estan definiendo de esta manera: 0) Neutro 1) Positivo 2)Negativo, estos valores pueden ser mas y permanecer en un objeto tipo colección en lo futuro.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSentimentalType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSentimentalType");
   /**
   * Longitud de donde se envía el mensaje
   */
    public static final org.semanticwb.platform.SemanticProperty social_longitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#longitude");
    public static final org.semanticwb.platform.SemanticClass social_CountryState=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#CountryState");
   /**
   * Instancia de Estado Geográfico de un país de donde provenga el mensaje. Si es que la latitud y longitud del mismo proviene de un estado/país registrados en los catálogos Country/CountryState.
   */
    public static final org.semanticwb.platform.SemanticProperty social_geoStateMap=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#geoStateMap");
   /**
   * Valor que es resultado del algoritmo de intensidad, mediante este valor se puede determinar si la intencidad es alta, media o baja. Este valor solo lo guardo como referencia, sin embargo el realmente importante es el resultado, el cual se encuentra en la propiedad postIntensity. Evaluar si BORRO esta propiedad o la dejo ahi como pura referencia.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postIntensityValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postIntensityValue");
   /**
   * Latitude de donde se envía el mensaje
   */
    public static final org.semanticwb.platform.SemanticProperty social_latitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#latitude");
   /**
   * Valor que resulta del algoritmo de analisis sentimental, aqui se puede ver el porque se pone cierto valor a la propiedad PostSentimentalType. Este valor solo lo guardo como referencia, sin embargo el realmente importante es el resultado, el cual se encuentra en la propiedad PostSentimentalType. Evaluar si  BORRO esta propiedad o la dejo ahi como pura referencia.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSentimentalValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSentimentalValue");
   /**
   * Indica si un mensaje es prioritario o no. Esta propiedad aplica tanto si es un mensaje de entrada (PostIn) o de salida (PostOut).
   */
    public static final org.semanticwb.platform.SemanticProperty social_isPrioritary=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#isPrioritary");
   /**
   * Tipo de Intensidad. 2=Alta;1=Media;0=Baja;
   */
    public static final org.semanticwb.platform.SemanticProperty social_postIntesityType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postIntesityType");
   /**
   * Catalogo de temas de un modelo (Marca)
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialTopic");
    public static final org.semanticwb.platform.SemanticProperty social_socialTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#socialTopic");
   /**
   * Clase a Cambiar despues por "Relacional", esta y todas sus hijas. Clase que comprende todos los tipos de Post que pueden ir siendo creados en la herramienta..
   */
    public static final org.semanticwb.platform.SemanticClass social_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Post");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Post");

    public static class ClassMgr
    {
       /**
       * Returns a list of Post for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Post>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Post for all models
       * @return Iterator of org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Post>(it, true);
        }

        public static org.semanticwb.social.Post createPost(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Post.ClassMgr.createPost(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Post
       * @param id Identifier for org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Post
       * @return A org.semanticwb.social.Post
       */
        public static org.semanticwb.social.Post getPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Post)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Post
       * @param id Identifier for org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Post
       * @return A org.semanticwb.social.Post
       */
        public static org.semanticwb.social.Post createPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Post)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Post
       * @param id Identifier for org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Post
       */
        public static void removePost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Post
       * @param id Identifier for org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.Post
       * @return true if the org.semanticwb.social.Post exists, false otherwise
       */

        public static boolean hasPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPost(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @param model Model of the org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostBySocialTopic(org.semanticwb.social.SocialTopic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Post with a determined SocialTopic
       * @param value SocialTopic of the type org.semanticwb.social.SocialTopic
       * @return Iterator with all the org.semanticwb.social.Post
       */

        public static java.util.Iterator<org.semanticwb.social.Post> listPostBySocialTopic(org.semanticwb.social.SocialTopic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialTopic,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostBase.ClassMgr getPostClassMgr()
    {
        return new PostBase.ClassMgr();
    }

   /**
   * Constructs a PostBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Post
   */
    public PostBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Longitude property
* @return float with the Longitude
*/
    public float getLongitude()
    {
        return getSemanticObject().getFloatProperty(social_longitude);
    }

/**
* Sets the Longitude property
* @param value long with the Longitude
*/
    public void setLongitude(float value)
    {
        getSemanticObject().setFloatProperty(social_longitude, value);
    }
   /**
   * Sets the value for the property GeoStateMap
   * @param value GeoStateMap to set
   */

    public void setGeoStateMap(org.semanticwb.social.CountryState value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_geoStateMap, value.getSemanticObject());
        }else
        {
            removeGeoStateMap();
        }
    }
   /**
   * Remove the value for GeoStateMap property
   */

    public void removeGeoStateMap()
    {
        getSemanticObject().removeProperty(social_geoStateMap);
    }

   /**
   * Gets the GeoStateMap
   * @return a org.semanticwb.social.CountryState
   */
    public org.semanticwb.social.CountryState getGeoStateMap()
    {
         org.semanticwb.social.CountryState ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_geoStateMap);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.CountryState)obj.createGenericInstance();
         }
         return ret;
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
* Gets the Latitude property
* @return float with the Latitude
*/
    public float getLatitude()
    {
        return getSemanticObject().getFloatProperty(social_latitude);
    }

/**
* Sets the Latitude property
* @param value long with the Latitude
*/
    public void setLatitude(float value)
    {
        getSemanticObject().setFloatProperty(social_latitude, value);
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
* Gets the IsPrioritary property
* @return boolean with the IsPrioritary
*/
    public boolean isIsPrioritary()
    {
        return getSemanticObject().getBooleanProperty(social_isPrioritary);
    }

/**
* Sets the IsPrioritary property
* @param value long with the IsPrioritary
*/
    public void setIsPrioritary(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_isPrioritary, value);
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
* Gets the Tags property
* @return String with the Tags
*/
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

/**
* Sets the Tags property
* @param value long with the Tags
*/
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public String getTags(String lang)
    {
        return getSemanticObject().getProperty(swb_tags, null, lang);
    }

    public String getDisplayTags(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_tags, lang);
    }

    public void setTags(String tags, String lang)
    {
        getSemanticObject().setProperty(swb_tags, tags, lang);
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
* Gets the Msg_Text property
* @return String with the Msg_Text
*/
    public String getMsg_Text()
    {
        return getSemanticObject().getProperty(social_msg_Text);
    }

/**
* Sets the Msg_Text property
* @param value long with the Msg_Text
*/
    public void setMsg_Text(String value)
    {
        getSemanticObject().setProperty(social_msg_Text, value);
    }
   /**
   * Sets the value for the property SocialTopic
   * @param value SocialTopic to set
   */

    public void setSocialTopic(org.semanticwb.social.SocialTopic value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_socialTopic, value.getSemanticObject());
        }else
        {
            removeSocialTopic();
        }
    }
   /**
   * Remove the value for SocialTopic property
   */

    public void removeSocialTopic()
    {
        getSemanticObject().removeProperty(social_socialTopic);
    }

   /**
   * Gets the SocialTopic
   * @return a org.semanticwb.social.SocialTopic
   */
    public org.semanticwb.social.SocialTopic getSocialTopic()
    {
         org.semanticwb.social.SocialTopic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_socialTopic);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialTopic)obj.createGenericInstance();
         }
         return ret;
    }
}
