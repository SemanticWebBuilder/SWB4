package org.semanticwb.social.base;


   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta. 
   */
public abstract class PostInBase extends org.semanticwb.social.Post implements org.semanticwb.social.PostTextable,org.semanticwb.social.PostDataable,org.semanticwb.model.Tagable
{
   /**
   * Plataforma (Sistema Operativo) desde donde se envió el mensaje de entrada.
   */
    public static final org.semanticwb.platform.SemanticClass social_DevicePlatform=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DevicePlatform");
   /**
   * Device Platform of the postIn
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInDevicePlatform=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInDevicePlatform");
   /**
   * Veces que ha sido re-enviado o compartido el post
   */
    public static final org.semanticwb.platform.SemanticProperty social_postShared=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postShared");
   /**
   * Tipo de dispositivo desde donde se envía el mensaje de entrada. Android Phone, Android Table, Ipad, Windows phone, etc
   */
    public static final org.semanticwb.platform.SemanticClass social_DeviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DeviceType");
   /**
   * Device type
   */
    public static final org.semanticwb.platform.SemanticProperty social_deviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#deviceType");
   /**
   * Tipo de PostIn. 1=Message;2=Photo;3:Video. Estan con variables estaticas en la clase PostIn.
   */
    public static final org.semanticwb.platform.SemanticProperty social_pi_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pi_type");
    public static final org.semanticwb.platform.SemanticClass social_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Country");
   /**
   * País del que proviene el PostIn y que se encuentra en el catálogo "Country".
   */
    public static final org.semanticwb.platform.SemanticProperty social_geoCountryObj=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#geoCountryObj");
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
   * Sentimiento de el mensaje (Post) basado en emoticones encontrados. (1=Positivo;2=Negativo;0=Neutro)
   */
    public static final org.semanticwb.platform.SemanticProperty social_postSentimentalEmoticonType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postSentimentalEmoticonType");
   /**
   * Lugar Geográfico de donde se envía el Post
   */
    public static final org.semanticwb.platform.SemanticProperty social_postPlace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postPlace");
   /**
   * Código de país de donde proviene el mensaje. No puse que esta propiedad tuviera como range un social:Country, porque tendría que agregar todos los países posibles para los PostIn en el catalogo de social:Country, Mejor voy a agregar en social:Country los paises que pudieran ser mas importantes y cuando se hagan reportes va a decir que de todos los paises que tengo ahi son un tanto por ciento y otro tanto de paises desconocidos, pero tendré su código de país, con ello podría despues agregar mas paises a social:Country.
   */
    public static final org.semanticwb.platform.SemanticProperty social_geoCountry=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#geoCountry");
   /**
   * Creación del PostIn. Este campo es un indice para poderlo tomar como ordenamiento via Sparql, por eso no se toma el de Descriptable. Esta propiedad nos indica cuando se creao el PostIn en el sistema, es muy diferente de cuando se creó en la red social, para eso tenemos el campo pi_createdInSocialNet.
   */
    public static final org.semanticwb.platform.SemanticProperty social_pi_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pi_created");
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
   * Fecha de creación del post en la red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_pi_createdInSocialNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pi_createdInSocialNet");
   /**
   * Url del PostIn en la red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_msg_url=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#msg_url");
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
       * Gets all org.semanticwb.social.PostIn with a determined PostInDevicePlatform
       * @param value PostInDevicePlatform of the type org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInDevicePlatform(org.semanticwb.social.DevicePlatform value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInDevicePlatform, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostInDevicePlatform
       * @param value PostInDevicePlatform of the type org.semanticwb.social.DevicePlatform
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostInDevicePlatform(org.semanticwb.social.DevicePlatform value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInDevicePlatform,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined DeviceType
       * @param value DeviceType of the type org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByDeviceType(org.semanticwb.social.DeviceType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_deviceType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined DeviceType
       * @param value DeviceType of the type org.semanticwb.social.DeviceType
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByDeviceType(org.semanticwb.social.DeviceType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_deviceType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined Msg_lang
       * @param value Msg_lang of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByMsg_lang(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_msg_lang, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined Msg_lang
       * @param value Msg_lang of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByMsg_lang(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_msg_lang,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined GeoCountryObj
       * @param value GeoCountryObj of the type org.semanticwb.social.Country
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByGeoCountryObj(org.semanticwb.social.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoCountryObj, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined GeoCountryObj
       * @param value GeoCountryObj of the type org.semanticwb.social.Country
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByGeoCountryObj(org.semanticwb.social.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoCountryObj,value.getSemanticObject(),sclass));
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
   * Sets the value for the property PostInDevicePlatform
   * @param value PostInDevicePlatform to set
   */

    public void setPostInDevicePlatform(org.semanticwb.social.DevicePlatform value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postInDevicePlatform, value.getSemanticObject());
        }else
        {
            removePostInDevicePlatform();
        }
    }
   /**
   * Remove the value for PostInDevicePlatform property
   */

    public void removePostInDevicePlatform()
    {
        getSemanticObject().removeProperty(social_postInDevicePlatform);
    }

   /**
   * Gets the PostInDevicePlatform
   * @return a org.semanticwb.social.DevicePlatform
   */
    public org.semanticwb.social.DevicePlatform getPostInDevicePlatform()
    {
         org.semanticwb.social.DevicePlatform ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postInDevicePlatform);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.DevicePlatform)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property DeviceType
   * @param value DeviceType to set
   */

    public void setDeviceType(org.semanticwb.social.DeviceType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_deviceType, value.getSemanticObject());
        }else
        {
            removeDeviceType();
        }
    }
   /**
   * Remove the value for DeviceType property
   */

    public void removeDeviceType()
    {
        getSemanticObject().removeProperty(social_deviceType);
    }

   /**
   * Gets the DeviceType
   * @return a org.semanticwb.social.DeviceType
   */
    public org.semanticwb.social.DeviceType getDeviceType()
    {
         org.semanticwb.social.DeviceType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_deviceType);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.DeviceType)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Pi_type property
* @return int with the Pi_type
*/
    public int getPi_type()
    {
        return getSemanticObject().getIntProperty(social_pi_type);
    }

/**
* Sets the Pi_type property
* @param value long with the Pi_type
*/
    public void setPi_type(int value)
    {
        getSemanticObject().setIntProperty(social_pi_type, value);
    }
   /**
   * Sets the value for the property GeoCountryObj
   * @param value GeoCountryObj to set
   */

    public void setGeoCountryObj(org.semanticwb.social.Country value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_geoCountryObj, value.getSemanticObject());
        }else
        {
            removeGeoCountryObj();
        }
    }
   /**
   * Remove the value for GeoCountryObj property
   */

    public void removeGeoCountryObj()
    {
        getSemanticObject().removeProperty(social_geoCountryObj);
    }

   /**
   * Gets the GeoCountryObj
   * @return a org.semanticwb.social.Country
   */
    public org.semanticwb.social.Country getGeoCountryObj()
    {
         org.semanticwb.social.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_geoCountryObj);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Country)obj.createGenericInstance();
         }
         return ret;
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
* Gets the GeoCountry property
* @return String with the GeoCountry
*/
    public String getGeoCountry()
    {
        return getSemanticObject().getProperty(social_geoCountry);
    }

/**
* Sets the GeoCountry property
* @param value long with the GeoCountry
*/
    public void setGeoCountry(String value)
    {
        getSemanticObject().setProperty(social_geoCountry, value);
    }

/**
* Gets the Pi_created property
* @return java.util.Date with the Pi_created
*/
    public java.util.Date getPi_created()
    {
        return getSemanticObject().getDateProperty(social_pi_created);
    }

/**
* Sets the Pi_created property
* @param value long with the Pi_created
*/
    public void setPi_created(java.util.Date value)
    {
        getSemanticObject().setDateProperty(social_pi_created, value);
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
* Gets the Pi_createdInSocialNet property
* @return java.util.Date with the Pi_createdInSocialNet
*/
    public java.util.Date getPi_createdInSocialNet()
    {
        return getSemanticObject().getDateProperty(social_pi_createdInSocialNet);
    }

/**
* Sets the Pi_createdInSocialNet property
* @param value long with the Pi_createdInSocialNet
*/
    public void setPi_createdInSocialNet(java.util.Date value)
    {
        getSemanticObject().setDateProperty(social_pi_createdInSocialNet, value);
    }

/**
* Gets the Msg_url property
* @return String with the Msg_url
*/
    public String getMsg_url()
    {
        return getSemanticObject().getProperty(social_msg_url);
    }

/**
* Sets the Msg_url property
* @param value long with the Msg_url
*/
    public void setMsg_url(String value)
    {
        getSemanticObject().setProperty(social_msg_url, value);
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
