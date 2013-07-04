package org.semanticwb.social.base;


   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales. 
   */
public abstract class SocialNetworkBase extends org.semanticwb.model.SWBClass implements org.semanticwb.social.Secreteable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable,org.semanticwb.social.Listenerable,org.semanticwb.model.Activeable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable
{
   /**
   * Clase a Cambiar despues por "Relacional".  En esta clase se guardan todos los post que lleguan por el listener, se estima que toda la info. que se guarde en este objeto debe de eliminarse aproximadamente c/mes, siendo este parametro configurable de acuerdo al tiempo que la organización quiera guardar  la información sobre los mensajes que lleguen por el listener. Se almacenan por mes y año, de esta manera sera mucho mas rapido hacer las busquedas sobre las instancias de esta clase.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostInContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostInContainer");
   /**
   * Con esta propiedad se puede obtener cuales son los objetos de tipo "PostListenerContainer" que tiene una determinada red social, de esta manera se pudiera saber cuales son los post que han sido recibidos desde una determinada cuenta de una red social, siendo agrupados por año y mes.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostListenerContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostListenerContainer");
   /**
   * Bandera que indica si la red social se encuentra antenticada o no, esta propiedad se maneja desde el sistema. Si la red social regresa que la fecha de expiración de la autenticación ha concluido, se pone en false, para que despues se autentique nuevamente la cuenta de manera manual desde el modulo de cuentas de redes sociales.
   */
    public static final org.semanticwb.platform.SemanticProperty social_sn_authenticated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#sn_authenticated");
   /**
   * Clase a Cambiar despues por "Relacional". Clase que contiene TODOS los post de salida (PostOut) que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutContainer");
   /**
   * Con esta propiedad se puede obtener cuales son los objetos de tipo "PostContainer" que tiene una determinada red social, de esta manera se pudiera saber cuales son los post que han sido enviados a una determinada cuenta de una red social, siendo agrupados por año y mes.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostContainer");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * Esta propiedad inversa me puede decir cuales son los mensajes que han llegado por el Listener por una determinada red social. Si se elimina una red social, se eliminan todos los mensajes de entrada PostIn.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInSocialNetworkInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInSocialNetworkInv");
   /**
   * Clase en la que se guardan datos que sirven para realizar una siguiente busqueda en una determinada red social y en un determinado stream.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetStreamSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetStreamSearch");
   /**
   * Una red social puede tener varias fechas proximas de busqueda, una por cada stream en la que este configurada. Si se elimina una red social, se eliminan los objetos de esta clase (SocialNetStreamSerch) Asociados.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetStreamSearchInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetStreamSearchInv");
   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto PostOut (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese postOut para c/red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutNet");
   /**
   * Con esta inversa, Cuando se elimine una red social, se eliminaran todos los objetos de tipo PostOutNet que este asociados a la misma.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialPostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialPostInv");
   /**
   * Código de pais que servira como filtro, es decir, solo se guardaran los mensajes que se hayan generado en el pais que pertenesca al mismo código de pais que se capture en esta propiedad, esto podría ser para cada instancia (cuenta) de cada red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_countryCodetoSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#countryCodetoSearch");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * PostOut que tiene una red social. Si se elimina una red social, se eliminan todos los mensajes que tiene de salida.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialNetworkPostOutInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialNetworkPostOutInv");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialNetwork for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialNetwork for all models
       * @return Iterator of org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(it, true);
        }

        public static org.semanticwb.social.SocialNetwork createSocialNetwork(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialNetwork.ClassMgr.createSocialNetwork(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return A org.semanticwb.social.SocialNetwork
       */
        public static org.semanticwb.social.SocialNetwork getSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetwork)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return A org.semanticwb.social.SocialNetwork
       */
        public static org.semanticwb.social.SocialNetwork createSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetwork)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       */
        public static void removeSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialNetwork
       * @param id Identifier for org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return true if the org.semanticwb.social.SocialNetwork exists, false otherwise
       */

        public static boolean hasSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialNetwork(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostInContainer
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostListenerContainer(org.semanticwb.social.PostInContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostListenerContainer
       * @param value PostListenerContainer of the type org.semanticwb.social.PostInContainer
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostListenerContainer(org.semanticwb.social.PostInContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostListenerContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostContainer(org.semanticwb.social.PostOutContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostContainer
       * @param value PostContainer of the type org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostContainer(org.semanticwb.social.PostOutContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostInSocialNetworkInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined PostInSocialNetworkInv
       * @param value PostInSocialNetworkInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInSocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialNetStreamSearchInv
       * @param value SocialNetStreamSearchInv of the type org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearchInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialNetStreamSearchInv
       * @param value SocialNetStreamSearchInv of the type org.semanticwb.social.SocialNetStreamSearch
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetStreamSearchInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialPostInv(org.semanticwb.social.PostOutNet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialPostInv
       * @param value SocialPostInv of the type org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialPostInv(org.semanticwb.social.PostOutNet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPostInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialNetworkPostOutInv
       * @param value SocialNetworkPostOutInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialNetworkPostOutInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkPostOutInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined SocialNetworkPostOutInv
       * @param value SocialNetworkPostOutInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkBySocialNetworkPostOutInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworkPostOutInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetwork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetwork
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetwork> listSocialNetworkByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialNetworkBase.ClassMgr getSocialNetworkClassMgr()
    {
        return new SocialNetworkBase.ClassMgr();
    }

   /**
   * Constructs a SocialNetworkBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialNetwork
   */
    public SocialNetworkBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.social.PostInContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostInContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostInContainer> listPostListenerContainers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostInContainer>(getSemanticObject().listObjectProperties(social_hasPostListenerContainer));
    }

   /**
   * Gets true if has a PostListenerContainer
   * @param value org.semanticwb.social.PostInContainer to verify
   * @return true if the org.semanticwb.social.PostInContainer exists, false otherwise
   */
    public boolean hasPostListenerContainer(org.semanticwb.social.PostInContainer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostListenerContainer,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PostListenerContainer
   * @param value org.semanticwb.social.PostInContainer to add
   */

    public void addPostListenerContainer(org.semanticwb.social.PostInContainer value)
    {
        getSemanticObject().addObjectProperty(social_hasPostListenerContainer, value.getSemanticObject());
    }
   /**
   * Removes all the PostListenerContainer
   */

    public void removeAllPostListenerContainer()
    {
        getSemanticObject().removeProperty(social_hasPostListenerContainer);
    }
   /**
   * Removes a PostListenerContainer
   * @param value org.semanticwb.social.PostInContainer to remove
   */

    public void removePostListenerContainer(org.semanticwb.social.PostInContainer value)
    {
        getSemanticObject().removeObjectProperty(social_hasPostListenerContainer,value.getSemanticObject());
    }

   /**
   * Gets the PostListenerContainer
   * @return a org.semanticwb.social.PostInContainer
   */
    public org.semanticwb.social.PostInContainer getPostListenerContainer()
    {
         org.semanticwb.social.PostInContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostListenerContainer);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostInContainer)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Sn_authenticated property
* @return boolean with the Sn_authenticated
*/
    public boolean isSn_authenticated()
    {
        return getSemanticObject().getBooleanProperty(social_sn_authenticated);
    }

/**
* Sets the Sn_authenticated property
* @param value long with the Sn_authenticated
*/
    public void setSn_authenticated(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_sn_authenticated, value);
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
   * Gets all the org.semanticwb.social.PostOutContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostOutContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> listPostContainers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer>(getSemanticObject().listObjectProperties(social_hasPostContainer));
    }

   /**
   * Gets true if has a PostContainer
   * @param value org.semanticwb.social.PostOutContainer to verify
   * @return true if the org.semanticwb.social.PostOutContainer exists, false otherwise
   */
    public boolean hasPostContainer(org.semanticwb.social.PostOutContainer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostContainer,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PostContainer
   * @param value org.semanticwb.social.PostOutContainer to add
   */

    public void addPostContainer(org.semanticwb.social.PostOutContainer value)
    {
        getSemanticObject().addObjectProperty(social_hasPostContainer, value.getSemanticObject());
    }
   /**
   * Removes all the PostContainer
   */

    public void removeAllPostContainer()
    {
        getSemanticObject().removeProperty(social_hasPostContainer);
    }
   /**
   * Removes a PostContainer
   * @param value org.semanticwb.social.PostOutContainer to remove
   */

    public void removePostContainer(org.semanticwb.social.PostOutContainer value)
    {
        getSemanticObject().removeObjectProperty(social_hasPostContainer,value.getSemanticObject());
    }

   /**
   * Gets the PostContainer
   * @return a org.semanticwb.social.PostOutContainer
   */
    public org.semanticwb.social.PostOutContainer getPostContainer()
    {
         org.semanticwb.social.PostOutContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostContainer);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOutContainer)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInSocialNetworkInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInSocialNetworkInv));
    }

   /**
   * Gets true if has a PostInSocialNetworkInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInSocialNetworkInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInSocialNetworkInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInSocialNetworkInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInSocialNetworkInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInSocialNetworkInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.SocialNetStreamSearch
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetStreamSearch
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearchInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch>(getSemanticObject().listObjectProperties(social_hasSocialNetStreamSearchInv));
    }

   /**
   * Gets true if has a SocialNetStreamSearchInv
   * @param value org.semanticwb.social.SocialNetStreamSearch to verify
   * @return true if the org.semanticwb.social.SocialNetStreamSearch exists, false otherwise
   */
    public boolean hasSocialNetStreamSearchInv(org.semanticwb.social.SocialNetStreamSearch value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetStreamSearchInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialNetStreamSearchInv
   * @return a org.semanticwb.social.SocialNetStreamSearch
   */
    public org.semanticwb.social.SocialNetStreamSearch getSocialNetStreamSearchInv()
    {
         org.semanticwb.social.SocialNetStreamSearch ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetStreamSearchInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetStreamSearch)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.PostOutNet
   * @return A GenericIterator with all the org.semanticwb.social.PostOutNet
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet> listSocialPostInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet>(getSemanticObject().listObjectProperties(social_hasSocialPostInv));
    }

   /**
   * Gets true if has a SocialPostInv
   * @param value org.semanticwb.social.PostOutNet to verify
   * @return true if the org.semanticwb.social.PostOutNet exists, false otherwise
   */
    public boolean hasSocialPostInv(org.semanticwb.social.PostOutNet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialPostInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialPostInv
   * @return a org.semanticwb.social.PostOutNet
   */
    public org.semanticwb.social.PostOutNet getSocialPostInv()
    {
         org.semanticwb.social.PostOutNet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialPostInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOutNet)obj.createGenericInstance();
         }
         return ret;
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
* Gets the CountryCodetoSearch property
* @return String with the CountryCodetoSearch
*/
    public String getCountryCodetoSearch()
    {
        return getSemanticObject().getProperty(social_countryCodetoSearch);
    }

/**
* Sets the CountryCodetoSearch property
* @param value long with the CountryCodetoSearch
*/
    public void setCountryCodetoSearch(String value)
    {
        getSemanticObject().setProperty(social_countryCodetoSearch, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostOut
   * @return A GenericIterator with all the org.semanticwb.social.PostOut
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> listSocialNetworkPostOutInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(getSemanticObject().listObjectProperties(social_hasSocialNetworkPostOutInv));
    }

   /**
   * Gets true if has a SocialNetworkPostOutInv
   * @param value org.semanticwb.social.PostOut to verify
   * @return true if the org.semanticwb.social.PostOut exists, false otherwise
   */
    public boolean hasSocialNetworkPostOutInv(org.semanticwb.social.PostOut value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetworkPostOutInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialNetworkPostOutInv
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getSocialNetworkPostOutInv()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetworkPostOutInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the AppKey property
* @return String with the AppKey
*/
    public String getAppKey()
    {
        return getSemanticObject().getProperty(social_appKey);
    }

/**
* Sets the AppKey property
* @param value long with the AppKey
*/
    public void setAppKey(String value)
    {
        getSemanticObject().setProperty(social_appKey, value);
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
* Gets the SecretKey property
* @return String with the SecretKey
*/
    public String getSecretKey()
    {
        return getSemanticObject().getProperty(social_secretKey);
    }

/**
* Sets the SecretKey property
* @param value long with the SecretKey
*/
    public void setSecretKey(String value)
    {
        getSemanticObject().setProperty(social_secretKey, value);
    }
}
