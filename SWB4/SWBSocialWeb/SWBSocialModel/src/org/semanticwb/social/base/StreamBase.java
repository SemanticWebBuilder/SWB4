package org.semanticwb.social.base;


   /**
   * Clase que contendra los streams que configurados para cada usuario 
   */
public abstract class StreamBase extends org.semanticwb.model.SWBClass implements org.semanticwb.social.Geolocable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Redes sociales asociadas al stream. En estas redes sociales se escuchara la frase asociada a un stream.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasStream_socialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasStream_socialNetwork");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con sentimiento negativo
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterSentimentalNegative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterSentimentalNegative");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con intensidad baja
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterIntensityLow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterIntensityLow");
   /**
   * Lapso de tiempo en que se busca la información. Ej. Cada x tiempo
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_PoolTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_PoolTime");
   /**
   * Frase a monitorear en un determinado stream, cada stream tiene sus propias frasea a monitorear.
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_phrase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_phrase");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
    public static final org.semanticwb.platform.SemanticProperty social_hasPostIntInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostIntInv");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con intensidad alta
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterIntensityHigh=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterIntensityHigh");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con sentimiento neutro
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterSentimentalNeutral=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterSentimentalNeutral");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con sentimiento positivo
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterSentimentalPositive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterSentimentalPositive");
   /**
   * Propiedad que indica si en el stream se desea aceptar que entren los mensajes que sean clasificados con intensidad media
   */
    public static final org.semanticwb.platform.SemanticProperty social_filterIntensityMedium=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#filterIntensityMedium");
   /**
   * Número de resultados que se despliegan en las páginas de los reportes
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_resultPagnum=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_resultPagnum");
   /**
   * Valor númerico minimo de klout que se desea filtrar para un stream. Los usuarios que tengan este klout o más y que hablen en las redes sociales configuradas para el stream, seran tomados sus mensajes para ser guardados en el sistema.
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_KloutValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_KloutValue");
   /**
   * Clase que contendra los streams que configurados para cada usuario
   */
    public static final org.semanticwb.platform.SemanticClass social_Stream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Stream");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Stream");

    public static class ClassMgr
    {
       /**
       * Returns a list of Stream for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreams(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Stream for all models
       * @return Iterator of org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreams()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream>(it, true);
        }

        public static org.semanticwb.social.Stream createStream(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Stream.ClassMgr.createStream(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Stream
       * @param id Identifier for org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.Stream
       * @return A org.semanticwb.social.Stream
       */
        public static org.semanticwb.social.Stream getStream(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Stream)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Stream
       * @param id Identifier for org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.Stream
       * @return A org.semanticwb.social.Stream
       */
        public static org.semanticwb.social.Stream createStream(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Stream)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Stream
       * @param id Identifier for org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.Stream
       */
        public static void removeStream(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Stream
       * @param id Identifier for org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.Stream
       * @return true if the org.semanticwb.social.Stream exists, false otherwise
       */

        public static boolean hasStream(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStream(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasStream_socialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasStream_socialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined PostIntInv
       * @param value PostIntInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByPostIntInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostIntInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined PostIntInv
       * @param value PostIntInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByPostIntInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostIntInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StreamBase.ClassMgr getStreamClassMgr()
    {
        return new StreamBase.ClassMgr();
    }

   /**
   * Constructs a StreamBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Stream
   */
    public StreamBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the GeoRadio property
* @return float with the GeoRadio
*/
    public float getGeoRadio()
    {
        return getSemanticObject().getFloatProperty(social_geoRadio);
    }

/**
* Sets the GeoRadio property
* @param value long with the GeoRadio
*/
    public void setGeoRadio(float value)
    {
        getSemanticObject().setFloatProperty(social_geoRadio, value);
    }
   /**
   * Gets all the org.semanticwb.social.SocialNetwork
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetwork
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> listSocialNetworks()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(getSemanticObject().listObjectProperties(social_hasStream_socialNetwork));
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
           ret=getSemanticObject().hasObjectProperty(social_hasStream_socialNetwork,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SocialNetwork
   * @param value org.semanticwb.social.SocialNetwork to add
   */

    public void addSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        getSemanticObject().addObjectProperty(social_hasStream_socialNetwork, value.getSemanticObject());
    }
   /**
   * Removes all the SocialNetwork
   */

    public void removeAllSocialNetwork()
    {
        getSemanticObject().removeProperty(social_hasStream_socialNetwork);
    }
   /**
   * Removes a SocialNetwork
   * @param value org.semanticwb.social.SocialNetwork to remove
   */

    public void removeSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        getSemanticObject().removeObjectProperty(social_hasStream_socialNetwork,value.getSemanticObject());
    }

   /**
   * Gets the SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasStream_socialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
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
* Gets the GeoCenterLongitude property
* @return float with the GeoCenterLongitude
*/
    public float getGeoCenterLongitude()
    {
        return getSemanticObject().getFloatProperty(social_geoCenterLongitude);
    }

/**
* Sets the GeoCenterLongitude property
* @param value long with the GeoCenterLongitude
*/
    public void setGeoCenterLongitude(float value)
    {
        getSemanticObject().setFloatProperty(social_geoCenterLongitude, value);
    }

/**
* Gets the FilterSentimentalNegative property
* @return boolean with the FilterSentimentalNegative
*/
    public boolean isFilterSentimentalNegative()
    {
        return getSemanticObject().getBooleanProperty(social_filterSentimentalNegative);
    }

/**
* Sets the FilterSentimentalNegative property
* @param value long with the FilterSentimentalNegative
*/
    public void setFilterSentimentalNegative(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterSentimentalNegative, value);
    }

/**
* Gets the FilterIntensityLow property
* @return boolean with the FilterIntensityLow
*/
    public boolean isFilterIntensityLow()
    {
        return getSemanticObject().getBooleanProperty(social_filterIntensityLow);
    }

/**
* Sets the FilterIntensityLow property
* @param value long with the FilterIntensityLow
*/
    public void setFilterIntensityLow(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterIntensityLow, value);
    }

/**
* Gets the PoolTime property
* @return int with the PoolTime
*/
    public int getPoolTime()
    {
        return getSemanticObject().getIntProperty(social_stream_PoolTime);
    }

/**
* Sets the PoolTime property
* @param value long with the PoolTime
*/
    public void setPoolTime(int value)
    {
        getSemanticObject().setIntProperty(social_stream_PoolTime, value);
    }

/**
* Gets the Phrase property
* @return String with the Phrase
*/
    public String getPhrase()
    {
        return getSemanticObject().getProperty(social_stream_phrase);
    }

/**
* Sets the Phrase property
* @param value long with the Phrase
*/
    public void setPhrase(String value)
    {
        getSemanticObject().setProperty(social_stream_phrase, value);
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
* Gets the GeoCenterLatitude property
* @return float with the GeoCenterLatitude
*/
    public float getGeoCenterLatitude()
    {
        return getSemanticObject().getFloatProperty(social_geoCenterLatitude);
    }

/**
* Sets the GeoCenterLatitude property
* @param value long with the GeoCenterLatitude
*/
    public void setGeoCenterLatitude(float value)
    {
        getSemanticObject().setFloatProperty(social_geoCenterLatitude, value);
    }

/**
* Gets the GeoDistanceUnit property
* @return String with the GeoDistanceUnit
*/
    public String getGeoDistanceUnit()
    {
        return getSemanticObject().getProperty(social_geoDistanceUnit);
    }

/**
* Sets the GeoDistanceUnit property
* @param value long with the GeoDistanceUnit
*/
    public void setGeoDistanceUnit(String value)
    {
        getSemanticObject().setProperty(social_geoDistanceUnit, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostIntInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostIntInv));
    }

   /**
   * Gets true if has a PostIntInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostIntInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostIntInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostIntInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostIntInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostIntInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the FilterIntensityHigh property
* @return boolean with the FilterIntensityHigh
*/
    public boolean isFilterIntensityHigh()
    {
        return getSemanticObject().getBooleanProperty(social_filterIntensityHigh);
    }

/**
* Sets the FilterIntensityHigh property
* @param value long with the FilterIntensityHigh
*/
    public void setFilterIntensityHigh(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterIntensityHigh, value);
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
* Gets the FilterSentimentalNeutral property
* @return boolean with the FilterSentimentalNeutral
*/
    public boolean isFilterSentimentalNeutral()
    {
        return getSemanticObject().getBooleanProperty(social_filterSentimentalNeutral);
    }

/**
* Sets the FilterSentimentalNeutral property
* @param value long with the FilterSentimentalNeutral
*/
    public void setFilterSentimentalNeutral(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterSentimentalNeutral, value);
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
* Gets the FilterSentimentalPositive property
* @return boolean with the FilterSentimentalPositive
*/
    public boolean isFilterSentimentalPositive()
    {
        return getSemanticObject().getBooleanProperty(social_filterSentimentalPositive);
    }

/**
* Sets the FilterSentimentalPositive property
* @param value long with the FilterSentimentalPositive
*/
    public void setFilterSentimentalPositive(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterSentimentalPositive, value);
    }

/**
* Gets the FilterIntensityMedium property
* @return boolean with the FilterIntensityMedium
*/
    public boolean isFilterIntensityMedium()
    {
        return getSemanticObject().getBooleanProperty(social_filterIntensityMedium);
    }

/**
* Sets the FilterIntensityMedium property
* @param value long with the FilterIntensityMedium
*/
    public void setFilterIntensityMedium(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_filterIntensityMedium, value);
    }

/**
* Gets the Stream_resultPagnum property
* @return int with the Stream_resultPagnum
*/
    public int getStream_resultPagnum()
    {
        return getSemanticObject().getIntProperty(social_stream_resultPagnum);
    }

/**
* Sets the Stream_resultPagnum property
* @param value long with the Stream_resultPagnum
*/
    public void setStream_resultPagnum(int value)
    {
        getSemanticObject().setIntProperty(social_stream_resultPagnum, value);
    }

/**
* Gets the Stream_KloutValue property
* @return int with the Stream_KloutValue
*/
    public int getStream_KloutValue()
    {
        return getSemanticObject().getIntProperty(social_stream_KloutValue);
    }

/**
* Sets the Stream_KloutValue property
* @param value long with the Stream_KloutValue
*/
    public void setStream_KloutValue(int value)
    {
        getSemanticObject().setIntProperty(social_stream_KloutValue, value);
    }
}
