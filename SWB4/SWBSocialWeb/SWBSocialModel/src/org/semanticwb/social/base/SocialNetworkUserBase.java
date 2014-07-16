package org.semanticwb.social.base;


   /**
   * Clase a Cambiar despues por "Relacional".Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. No se puso como identificador de las instancias de esta clase el id que maneja el usuario en la red social, ya que un identificador de una red social, puede ser el mismo para otra red social, pero obviamnete para otro usuario.Es por ello que se puso como AutoGenID esta clase y por ello se maneja por separado el id de un usuario en una determinada red social, esto en la propiedad snu_id. 
   */
public abstract class SocialNetworkUserBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Genero registrado en el red social para el usuario. 1=Hombre; 2=Mujer; 3=Indefinido.
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_gender=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_gender");
   /**
   * Cantidad de seguidores que tiene el usuario que es almacenado en c/instancia de esta clase (en su propiedad id)
   */
    public static final org.semanticwb.platform.SemanticProperty social_followers=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#followers");
   /**
   * Número de usuarios a los que yo sigo (así se maneja en twitter), en facebook aqui pondría a mis amigos y la de followers (que son los que me siguen) según yo no la utilizaría (en facebook)
   */
    public static final org.semanticwb.platform.SemanticProperty social_friends=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#friends");
   /**
   * Clase que contendra los streams que configurados para cada usuario
   */
    public static final org.semanticwb.platform.SemanticClass social_Stream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Stream");
   /**
   * Stream o Streams con los que tiene relación un usuario (SocialNetworkUser)
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasStream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasStream");
   /**
   * Educación definida del usuario en la red social.    1=HighSchool; 2=College; 3=Graduate; 4=Undefined;
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_education=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_education");
   /**
   * Propiedad en la que se graba un thirdPartyId que tiene una red social para el usuario. Facebook tiene un third party id para c/usuario, ese id es el que utiliza Klout para medir la influencia de ese usuario en esa red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_third_party_id=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_third_party_id");
   /**
   * Define las diferentes etapas de la vida de una persona.
   */
    public static final org.semanticwb.platform.SemanticClass social_LifeStage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#LifeStage");
   /**
   * Etapa de vida del usuario de la red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_LifeStage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_LifeStage");
   /**
   * Localización del usuario que se encuentra en el profile de la red social específica. Ej. Tampico, Tamaulipas.
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_profileGeoLocation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_profileGeoLocation");
   /**
   * Id del usuario en una determinada red social, es decir, en twitter, facebook, google+, youtube, flicker, etc.
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_id=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_id");
   /**
   * Url del usuario en la red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_userUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#userUrl");
   /**
   * Relación sentimental del usuario en la red social. 1=Soltero; 2=Unión Libre; 3=Casado; 4=Divordiado; 5=Viudo;
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_relationShipStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_relationShipStatus");
   /**
   * Nombre del usuario en la Red Social
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_name");
   /**
   * Email registrado para el usuario en la red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_email=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_email");
   /**
   * Url de la foto del usuario. La foto nunca se va ha guardar en SWBSocial, solo se guarda la url de la misma en el servidor de la red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_photoUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_photoUrl");
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * Propiedad inversa que regresa los PostIn pertenecientes a un usuario de una red social. Si se borra un SocialNetWorkUser no se borran los PostIn que tiene asociados, esto debido a que existe un onservador desde código que si se borra un PostIn cuyo usuario ya no tiene PostIns, se borra el SocialUserNetWork, así que esto haría que se me ciclara el programa, ya que cuando borre el SocialUserNetwork querría notificar nuevamente a PostIn y esto nuevamente querría borrar al UserSocialNetwork y así se quedaría como un ciclo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInInv");
   /**
   * Klout de un usuario en una determinada red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_klout=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_klout");
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
   /**
   * Red social a la cual pertenece este usuario (usuario de red social). Tiene relación con swb:Class y no con SocialNetwork porque lo que agrego aquí es una clase, ya sea Twitter, Facebook, Flicker, etc. No asoció solo una instancia de la clase Twitter o Facebook, etc, porque un usuario de una red social (Twitter, Facebook, etc) es para todas las cuentas de las mismas, no solo para una, es por ello que como menciono, se graba la clase de la red social, no una instancia (objeto).
   */
    public static final org.semanticwb.platform.SemanticProperty social_snu_SocialNetworkObj=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snu_SocialNetworkObj");
   /**
   * Clase a Cambiar despues por "Relacional".Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. No se puso como identificador de las instancias de esta clase el id que maneja el usuario en la red social, ya que un identificador de una red social, puede ser el mismo para otra red social, pero obviamnete para otro usuario.Es por ello que se puso como AutoGenID esta clase y por ello se maneja por separado el id de un usuario en una determinada red social, esto en la propiedad snu_id.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetworkUser=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetworkUser");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialNetworkUser for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUsers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialNetworkUser for all models
       * @return Iterator of org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUsers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser>(it, true);
        }

        public static org.semanticwb.social.SocialNetworkUser createSocialNetworkUser(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialNetworkUser.ClassMgr.createSocialNetworkUser(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialNetworkUser
       * @param id Identifier for org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return A org.semanticwb.social.SocialNetworkUser
       */
        public static org.semanticwb.social.SocialNetworkUser getSocialNetworkUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetworkUser)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialNetworkUser
       * @param id Identifier for org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return A org.semanticwb.social.SocialNetworkUser
       */
        public static org.semanticwb.social.SocialNetworkUser createSocialNetworkUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetworkUser)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialNetworkUser
       * @param id Identifier for org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       */
        public static void removeSocialNetworkUser(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialNetworkUser
       * @param id Identifier for org.semanticwb.social.SocialNetworkUser
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return true if the org.semanticwb.social.SocialNetworkUser exists, false otherwise
       */

        public static boolean hasSocialNetworkUser(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialNetworkUser(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Stream
       * @param value Stream of the type org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByStream(org.semanticwb.social.Stream value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasStream, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Stream
       * @param value Stream of the type org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByStream(org.semanticwb.social.Stream value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasStream,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Snu_LifeStage
       * @param value Snu_LifeStage of the type org.semanticwb.social.LifeStage
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserBySnu_LifeStage(org.semanticwb.social.LifeStage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_snu_LifeStage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Snu_LifeStage
       * @param value Snu_LifeStage of the type org.semanticwb.social.LifeStage
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserBySnu_LifeStage(org.semanticwb.social.LifeStage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_snu_LifeStage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined PostInInv
       * @param value PostInInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.SocialNetworkUser
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByPostInInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetworkUser with a determined PostInInv
       * @param value PostInInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.SocialNetworkUser
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetworkUser> listSocialNetworkUserByPostInInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetworkUser> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialNetworkUserBase.ClassMgr getSocialNetworkUserClassMgr()
    {
        return new SocialNetworkUserBase.ClassMgr();
    }

   /**
   * Constructs a SocialNetworkUserBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialNetworkUser
   */
    public SocialNetworkUserBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Snu_gender property
* @return int with the Snu_gender
*/
    public int getSnu_gender()
    {
        return getSemanticObject().getIntProperty(social_snu_gender);
    }

/**
* Sets the Snu_gender property
* @param value long with the Snu_gender
*/
    public void setSnu_gender(int value)
    {
        getSemanticObject().setIntProperty(social_snu_gender, value);
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
* Gets the Followers property
* @return int with the Followers
*/
    public int getFollowers()
    {
        return getSemanticObject().getIntProperty(social_followers);
    }

/**
* Sets the Followers property
* @param value long with the Followers
*/
    public void setFollowers(int value)
    {
        getSemanticObject().setIntProperty(social_followers, value);
    }

/**
* Gets the Friends property
* @return int with the Friends
*/
    public int getFriends()
    {
        return getSemanticObject().getIntProperty(social_friends);
    }

/**
* Sets the Friends property
* @param value long with the Friends
*/
    public void setFriends(int value)
    {
        getSemanticObject().setIntProperty(social_friends, value);
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
   * Gets all the org.semanticwb.social.Stream
   * @return A GenericIterator with all the org.semanticwb.social.Stream
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream> listStreams()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Stream>(getSemanticObject().listObjectProperties(social_hasStream));
    }

   /**
   * Gets true if has a Stream
   * @param value org.semanticwb.social.Stream to verify
   * @return true if the org.semanticwb.social.Stream exists, false otherwise
   */
    public boolean hasStream(org.semanticwb.social.Stream value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasStream,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Stream
   * @param value org.semanticwb.social.Stream to add
   */

    public void addStream(org.semanticwb.social.Stream value)
    {
        getSemanticObject().addObjectProperty(social_hasStream, value.getSemanticObject());
    }
   /**
   * Removes all the Stream
   */

    public void removeAllStream()
    {
        getSemanticObject().removeProperty(social_hasStream);
    }
   /**
   * Removes a Stream
   * @param value org.semanticwb.social.Stream to remove
   */

    public void removeStream(org.semanticwb.social.Stream value)
    {
        getSemanticObject().removeObjectProperty(social_hasStream,value.getSemanticObject());
    }

   /**
   * Gets the Stream
   * @return a org.semanticwb.social.Stream
   */
    public org.semanticwb.social.Stream getStream()
    {
         org.semanticwb.social.Stream ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasStream);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Stream)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Snu_education property
* @return int with the Snu_education
*/
    public int getSnu_education()
    {
        return getSemanticObject().getIntProperty(social_snu_education);
    }

/**
* Sets the Snu_education property
* @param value long with the Snu_education
*/
    public void setSnu_education(int value)
    {
        getSemanticObject().setIntProperty(social_snu_education, value);
    }

/**
* Gets the Snu_third_party_id property
* @return String with the Snu_third_party_id
*/
    public String getSnu_third_party_id()
    {
        return getSemanticObject().getProperty(social_snu_third_party_id);
    }

/**
* Sets the Snu_third_party_id property
* @param value long with the Snu_third_party_id
*/
    public void setSnu_third_party_id(String value)
    {
        getSemanticObject().setProperty(social_snu_third_party_id, value);
    }
   /**
   * Sets the value for the property Snu_LifeStage
   * @param value Snu_LifeStage to set
   */

    public void setSnu_LifeStage(org.semanticwb.social.LifeStage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_snu_LifeStage, value.getSemanticObject());
        }else
        {
            removeSnu_LifeStage();
        }
    }
   /**
   * Remove the value for Snu_LifeStage property
   */

    public void removeSnu_LifeStage()
    {
        getSemanticObject().removeProperty(social_snu_LifeStage);
    }

   /**
   * Gets the Snu_LifeStage
   * @return a org.semanticwb.social.LifeStage
   */
    public org.semanticwb.social.LifeStage getSnu_LifeStage()
    {
         org.semanticwb.social.LifeStage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_snu_LifeStage);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.LifeStage)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Snu_profileGeoLocation property
* @return String with the Snu_profileGeoLocation
*/
    public String getSnu_profileGeoLocation()
    {
        return getSemanticObject().getProperty(social_snu_profileGeoLocation);
    }

/**
* Sets the Snu_profileGeoLocation property
* @param value long with the Snu_profileGeoLocation
*/
    public void setSnu_profileGeoLocation(String value)
    {
        getSemanticObject().setProperty(social_snu_profileGeoLocation, value);
    }

/**
* Gets the Snu_id property
* @return String with the Snu_id
*/
    public String getSnu_id()
    {
        return getSemanticObject().getProperty(social_snu_id);
    }

/**
* Sets the Snu_id property
* @param value long with the Snu_id
*/
    public void setSnu_id(String value)
    {
        getSemanticObject().setProperty(social_snu_id, value);
    }

/**
* Gets the UserUrl property
* @return String with the UserUrl
*/
    public String getUserUrl()
    {
        return getSemanticObject().getProperty(social_userUrl);
    }

/**
* Sets the UserUrl property
* @param value long with the UserUrl
*/
    public void setUserUrl(String value)
    {
        getSemanticObject().setProperty(social_userUrl, value);
    }

/**
* Gets the Snu_relationShipStatus property
* @return int with the Snu_relationShipStatus
*/
    public int getSnu_relationShipStatus()
    {
        return getSemanticObject().getIntProperty(social_snu_relationShipStatus);
    }

/**
* Sets the Snu_relationShipStatus property
* @param value long with the Snu_relationShipStatus
*/
    public void setSnu_relationShipStatus(int value)
    {
        getSemanticObject().setIntProperty(social_snu_relationShipStatus, value);
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
* Gets the Snu_name property
* @return String with the Snu_name
*/
    public String getSnu_name()
    {
        return getSemanticObject().getProperty(social_snu_name);
    }

/**
* Sets the Snu_name property
* @param value long with the Snu_name
*/
    public void setSnu_name(String value)
    {
        getSemanticObject().setProperty(social_snu_name, value);
    }

/**
* Gets the Snu_email property
* @return String with the Snu_email
*/
    public String getSnu_email()
    {
        return getSemanticObject().getProperty(social_snu_email);
    }

/**
* Sets the Snu_email property
* @param value long with the Snu_email
*/
    public void setSnu_email(String value)
    {
        getSemanticObject().setProperty(social_snu_email, value);
    }

/**
* Gets the Snu_photoUrl property
* @return String with the Snu_photoUrl
*/
    public String getSnu_photoUrl()
    {
        return getSemanticObject().getProperty(social_snu_photoUrl);
    }

/**
* Sets the Snu_photoUrl property
* @param value long with the Snu_photoUrl
*/
    public void setSnu_photoUrl(String value)
    {
        getSemanticObject().setProperty(social_snu_photoUrl, value);
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInInv));
    }

   /**
   * Gets true if has a PostInInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Snu_klout property
* @return int with the Snu_klout
*/
    public int getSnu_klout()
    {
        return getSemanticObject().getIntProperty(social_snu_klout);
    }

/**
* Sets the Snu_klout property
* @param value long with the Snu_klout
*/
    public void setSnu_klout(int value)
    {
        getSemanticObject().setIntProperty(social_snu_klout, value);
    }

    public void setSnu_SocialNetworkObj(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(social_snu_SocialNetworkObj, value);
    }

    public void removeSnu_SocialNetworkObj()
    {
        getSemanticObject().removeProperty(social_snu_SocialNetworkObj);
    }

/**
* Gets the Snu_SocialNetworkObj property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getSnu_SocialNetworkObj()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(social_snu_SocialNetworkObj);
         return ret;
    }
}
