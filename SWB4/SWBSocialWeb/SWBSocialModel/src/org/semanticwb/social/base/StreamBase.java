package org.semanticwb.social.base;


   /**
   * Clase que contendra los streams que configurados para cada usuario 
   */
public abstract class StreamBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.social.Geolocable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.social.SocialRuleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Referensable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Trashable
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
   * Clase a Cambiar despues por "Relacional".Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. No se puso como identificador de las instancias de esta clase el id que maneja el usuario en la red social, ya que un identificador de una red social, puede ser el mismo para otra red social, pero obviamnete para otro usuario.Es por ello que se puso como AutoGenID esta clase y por ello se maneja por separado el id de un usuario en una determinada red social, esto en la propiedad snu_id.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");
   /**
   * Lista todos las instancias de SocialNetworkUser que esten asociados con un determinado Stream, se utiliza para las campañas.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetUserInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetUserInv");
   /**
   * Clase en la que se guardan datos que sirven para realizar una siguiente busqueda en una determinada red social y en un determinado stream.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetStreamSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetStreamSearch");
   /**
   * El stream puede tener varias instancias de la clase SocialNetStreamSearch, una por cada red social que tenga asignada.Si se elimina un Stream, se eliminan los objetos de esta clase (SocialNetStreamSerch) Asociados.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetStreamSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetStreamSearch");
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
   * Propiedad que indica si el stream va manejar conexiones abiertas en las cuentas de redes sociales que así lo permitan, en este momento solo twitter con su "Stream Api" lo maneja.
   */
    public static final org.semanticwb.platform.SemanticProperty social_keepAliveManager=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#keepAliveManager");
   /**
   * Frase a monitorear en un determinado stream, cada stream tiene sus propias frasea a monitorear.
   */
    public static final org.semanticwb.platform.SemanticProperty social_stream_phrase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#stream_phrase");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * PostIn asociados a un Stream. Si se elimina el Stream, se eliminan estos PostIn, ya que si no se hiciera, no se podrían ver desde ningún lado, ya que no tuvieran un Stream asociado.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInStreamInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInStreamInv");
   /**
   * Enviar al clasificador un determinado número de mensajes o todos los que puedan llegar por la red social de un solo golpe. Si se registra un número en este campo apareceran los mensajes mas rapidamente clasificados en la pestaña "Mensajes de entrada" de un Stream y de los socialTopic, sin embargo, esto puede hacer que se generen mayor cantidad de threads en el aplicativo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_blockofMsgToClassify=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#blockofMsgToClassify");
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
   * Número de resultados que se despliegan en las páginas de los reportes, ver si lo ocuparía o despues lo quito
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
       * Gets all org.semanticwb.social.Stream with a determined SocialNetUserInv
       * @param value SocialNetUserInv of the type org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetUserInv(org.semanticwb.social.SocialNetworkUser value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetUserInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetUserInv
       * @param value SocialNetUserInv of the type org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetUserInv(org.semanticwb.social.SocialNetworkUser value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetUserInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetStreamSearch
       * @param value SocialNetStreamSearch of the type org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetStreamSearch(org.semanticwb.social.SocialNetStreamSearch value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearch, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialNetStreamSearch
       * @param value SocialNetStreamSearch of the type org.semanticwb.social.SocialNetStreamSearch
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialNetStreamSearch(org.semanticwb.social.SocialNetStreamSearch value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearch,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined PostInStreamInv
       * @param value PostInStreamInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByPostInStreamInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInStreamInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined PostInStreamInv
       * @param value PostInStreamInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamByPostInStreamInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInStreamInv,value.getSemanticObject(),sclass));
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
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialRuleRef
       * @param value SocialRuleRef of the type org.semanticwb.social.SocialRuleRef
       * @param model Model of the org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialRuleRef(org.semanticwb.social.SocialRuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Stream with a determined SocialRuleRef
       * @param value SocialRuleRef of the type org.semanticwb.social.SocialRuleRef
       * @return Iterator with all the org.semanticwb.social.Stream
       */

        public static java.util.Iterator<org.semanticwb.social.Stream> listStreamBySocialRuleRef(org.semanticwb.social.SocialRuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialRuleRef,value.getSemanticObject(),sclass));
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
   * Gets all the org.semanticwb.social.SocialNetworkUser
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetworkUser
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> listSocialNetUserInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser>(getSemanticObject().listObjectProperties(social_hasSocialNetUserInv));
    }

   /**
   * Gets true if has a SocialNetUserInv
   * @param value org.semanticwb.social.SocialNetworkUser to verify
   * @return true if the org.semanticwb.social.SocialNetworkUser exists, false otherwise
   */
    public boolean hasSocialNetUserInv(org.semanticwb.social.SocialNetworkUser value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetUserInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialNetUserInv
   * @return a org.semanticwb.social.SocialNetworkUser
   */
    public org.semanticwb.social.SocialNetworkUser getSocialNetUserInv()
    {
         org.semanticwb.social.SocialNetworkUser ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetUserInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetworkUser)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.SocialNetStreamSearch
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetStreamSearch
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearches()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch>(getSemanticObject().listObjectProperties(social_hasSocialNetStreamSearch));
    }

   /**
   * Gets true if has a SocialNetStreamSearch
   * @param value org.semanticwb.social.SocialNetStreamSearch to verify
   * @return true if the org.semanticwb.social.SocialNetStreamSearch exists, false otherwise
   */
    public boolean hasSocialNetStreamSearch(org.semanticwb.social.SocialNetStreamSearch value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetStreamSearch,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialNetStreamSearch
   * @return a org.semanticwb.social.SocialNetStreamSearch
   */
    public org.semanticwb.social.SocialNetStreamSearch getSocialNetStreamSearch()
    {
         org.semanticwb.social.SocialNetStreamSearch ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetStreamSearch);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetStreamSearch)obj.createGenericInstance();
         }
         return ret;
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
* Gets the KeepAliveManager property
* @return boolean with the KeepAliveManager
*/
    public boolean isKeepAliveManager()
    {
        return getSemanticObject().getBooleanProperty(social_keepAliveManager);
    }

/**
* Sets the KeepAliveManager property
* @param value long with the KeepAliveManager
*/
    public void setKeepAliveManager(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_keepAliveManager, value);
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
* Gets the Deleted property
* @return boolean with the Deleted
*/
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

/**
* Sets the Deleted property
* @param value long with the Deleted
*/
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
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
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInStreamInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInStreamInv));
    }

   /**
   * Gets true if has a PostInStreamInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInStreamInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInStreamInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInStreamInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInStreamInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInStreamInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
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
* Gets the BlockofMsgToClassify property
* @return int with the BlockofMsgToClassify
*/
    public int getBlockofMsgToClassify()
    {
        return getSemanticObject().getIntProperty(social_blockofMsgToClassify);
    }

/**
* Sets the BlockofMsgToClassify property
* @param value long with the BlockofMsgToClassify
*/
    public void setBlockofMsgToClassify(int value)
    {
        getSemanticObject().setIntProperty(social_blockofMsgToClassify, value);
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
   * Gets all the org.semanticwb.social.SocialRuleRef
   * @return A GenericIterator with all the org.semanticwb.social.SocialRuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef> listSocialRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef>(getSemanticObject().listObjectProperties(social_hasSocialRuleRef));
    }

   /**
   * Gets true if has a SocialRuleRef
   * @param value org.semanticwb.social.SocialRuleRef to verify
   * @return true if the org.semanticwb.social.SocialRuleRef exists, false otherwise
   */
    public boolean hasSocialRuleRef(org.semanticwb.social.SocialRuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialRuleRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SocialRuleRef
   * @param value org.semanticwb.social.SocialRuleRef to add
   */

    public void addSocialRuleRef(org.semanticwb.social.SocialRuleRef value)
    {
        getSemanticObject().addObjectProperty(social_hasSocialRuleRef, value.getSemanticObject());
    }
   /**
   * Removes all the SocialRuleRef
   */

    public void removeAllSocialRuleRef()
    {
        getSemanticObject().removeProperty(social_hasSocialRuleRef);
    }
   /**
   * Removes a SocialRuleRef
   * @param value org.semanticwb.social.SocialRuleRef to remove
   */

    public void removeSocialRuleRef(org.semanticwb.social.SocialRuleRef value)
    {
        getSemanticObject().removeObjectProperty(social_hasSocialRuleRef,value.getSemanticObject());
    }

   /**
   * Gets the SocialRuleRef
   * @return a org.semanticwb.social.SocialRuleRef
   */
    public org.semanticwb.social.SocialRuleRef getSocialRuleRef()
    {
         org.semanticwb.social.SocialRuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialRuleRef)obj.createGenericInstance();
         }
         return ret;
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

/**
* Gets the GeoLanguage property
* @return String with the GeoLanguage
*/
    public String getGeoLanguage()
    {
        return getSemanticObject().getProperty(social_geoLanguage);
    }

/**
* Sets the GeoLanguage property
* @param value long with the GeoLanguage
*/
    public void setGeoLanguage(String value)
    {
        getSemanticObject().setProperty(social_geoLanguage, value);
    }

   /**
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
