package org.semanticwb.social.base;


   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer. 
   */
public abstract class PostOutBase extends org.semanticwb.social.Post implements org.semanticwb.model.CalendarRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Tagable,org.semanticwb.model.Referensable,org.semanticwb.social.PostTextable,org.semanticwb.social.PostDataable
{
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * PostIn que sirvió como origen de esta instancia de PostOut
   */
    public static final org.semanticwb.platform.SemanticProperty social_postInSource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postInSource");
   /**
   * Relación de Privacidad entre PostOut y las redes sociales a las cuales se envía.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutPrivacyRelation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutPrivacyRelation");
   /**
   * Propiedad que lista todas las instancias de PostOutPrivacyRelation que tenga un determinado PostOut. Si se elimina el PostOut se eliminarían todas las instancias de PostOutPrivacyRelation que tengan ese PostOut asociado.
   */
    public static final org.semanticwb.platform.SemanticProperty social_haspopr_postOutInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#haspopr_postOutInv");
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
   * Clase a Cambiar despues por "Relacional". Clase que contiene TODOS los post de salida (PostOut) que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutContainer");
   /**
   * Propiedad inversa que me regresa los postContainers con los cuales tiene referencia un objeto(instancia) PostOut, recordemos que se crea una instancia de PostContainer por una red social, de modo que si se envía un postOut a varias redes sociales(lo cual puede pasar) pues se deverían de crear varios postContainers (Si es que no hay ya creados) por cada una de estas redes sociales a las cuales se envía el PostOut.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostContainer_PostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostContainer_PostInv");
   /**
   * Define si un PostOut se encuentra en estado de publicado o no.
   */
    public static final org.semanticwb.platform.SemanticProperty social_published=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#published");
   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto PostOut (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese postOut para c/red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutNet");
   /**
   * Con esta inversa, cuando se elimine un postOut, se eliminaran todos los objetos PostOutNet que tenga asociados.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostOutNetInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostOutNetInv");
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
       * Gets all org.semanticwb.social.PostOut with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByGeoStateMap(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined GeoStateMap
       * @param value GeoStateMap of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByGeoStateMap(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_geoStateMap,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostInSource
       * @param value PostInSource of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostInSource(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postInSource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostInSource
       * @param value PostInSource of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostInSource(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postInSource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined Popr_postOutInv
       * @param value Popr_postOutInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_postOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined Popr_postOutInv
       * @param value Popr_postOutInv of the type org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_postOutInv,value.getSemanticObject(),sclass));
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
       * Gets all org.semanticwb.social.PostOut with a determined PostOutNetInv
       * @param value PostOutNetInv of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostOutNetInv(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNetInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostOutNetInv
       * @param value PostOutNetInv of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostOutNetInv(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOutNetInv,value.getSemanticObject(),sclass));
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
       * Gets all org.semanticwb.social.PostOut with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
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
   * Sets the value for the property PostInSource
   * @param value PostInSource to set
   */

    public void setPostInSource(org.semanticwb.social.PostIn value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postInSource, value.getSemanticObject());
        }else
        {
            removePostInSource();
        }
    }
   /**
   * Remove the value for PostInSource property
   */

    public void removePostInSource()
    {
        getSemanticObject().removeProperty(social_postInSource);
    }

   /**
   * Gets the PostInSource
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInSource()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postInSource);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.PostOutPrivacyRelation
   * @return A GenericIterator with all the org.semanticwb.social.PostOutPrivacyRelation
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation> listpopr_postOutInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation>(getSemanticObject().listObjectProperties(social_haspopr_postOutInv));
    }

   /**
   * Gets true if has a popr_postOutInv
   * @param value org.semanticwb.social.PostOutPrivacyRelation to verify
   * @return true if the org.semanticwb.social.PostOutPrivacyRelation exists, false otherwise
   */
    public boolean haspopr_postOutInv(org.semanticwb.social.PostOutPrivacyRelation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_haspopr_postOutInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the popr_postOutInv
   * @return a org.semanticwb.social.PostOutPrivacyRelation
   */
    public org.semanticwb.social.PostOutPrivacyRelation getpopr_postOutInv()
    {
         org.semanticwb.social.PostOutPrivacyRelation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_haspopr_postOutInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOutPrivacyRelation)obj.createGenericInstance();
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
* Gets the Published property
* @return boolean with the Published
*/
    public boolean isPublished()
    {
        return getSemanticObject().getBooleanProperty(social_published);
    }

/**
* Sets the Published property
* @param value long with the Published
*/
    public void setPublished(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_published, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostOutNet
   * @return A GenericIterator with all the org.semanticwb.social.PostOutNet
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet> listPostOutNetInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet>(getSemanticObject().listObjectProperties(social_hasPostOutNetInv));
    }

   /**
   * Gets true if has a PostOutNetInv
   * @param value org.semanticwb.social.PostOutNet to verify
   * @return true if the org.semanticwb.social.PostOutNet exists, false otherwise
   */
    public boolean hasPostOutNetInv(org.semanticwb.social.PostOutNet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostOutNetInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostOutNetInv
   * @return a org.semanticwb.social.PostOutNet
   */
    public org.semanticwb.social.PostOutNet getPostOutNetInv()
    {
         org.semanticwb.social.PostOutNet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostOutNetInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOutNet)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.CalendarRef
   * @return A GenericIterator with all the org.semanticwb.model.CalendarRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

   /**
   * Gets true if has a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to verify
   * @return true if the org.semanticwb.model.CalendarRef exists, false otherwise
   */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to add
   */

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }
   /**
   * Removes all the CalendarRef
   */

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }
   /**
   * Removes a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to remove
   */

    public void removeCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
    }

   /**
   * Gets the CalendarRef
   * @return a org.semanticwb.model.CalendarRef
   */
    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
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
