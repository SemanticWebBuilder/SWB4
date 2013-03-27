package org.semanticwb.social.base;


   /**
   * Clase a Cambiar despues por "Relacional".  Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales.Si se eliminan un post que han sifo tomados como base(PostIn), se debe de eliminar la instancia asociada de esta clase (en la propiedad plcb_Post). 
   */
public abstract class PostListenerContainerBaseBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * Referencia al Post que es creado por un post que llega por el Listener  y que es tomado como base para crear nuevos post desde la herramienta y que sera o seran enviados a una o mas redes sociales.
   */
    public static final org.semanticwb.platform.SemanticProperty social_plcb_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#plcb_Post");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Cuenta de Red Social a la que pertenece el post que llega por el Listener y que es tomado como base para crear un nuevo post desde la herramienta y que sera enviado a una o mas redes sociales. Ver si quito esta propiedad, no me acuerdo porque la puse, talvez para hacer las busquedas por red social y que estas fueran mas rapidas.
   */
    public static final org.semanticwb.platform.SemanticProperty social_plcb_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#plcb_SocialNetwork");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * Propiedad inversa que nos proporciona todos los post que han sido creados desde el SSMCC en cuencecuencia de una instancia de Post que llega por el listener, es decir, de un post que es tomado como base para crear nuevos post, estos post que son tomados como base, estan en la clase PostListenerContainerBase (Osea en esta)
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasNewCreatedPostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasNewCreatedPostInv");
   /**
   * Clase a Cambiar despues por "Relacional".  Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales.Si se eliminan un post que han sifo tomados como base(PostIn), se debe de eliminar la instancia asociada de esta clase (en la propiedad plcb_Post).
   */
    public static final org.semanticwb.platform.SemanticClass social_PostListenerContainerBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainerBase");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainerBase");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostListenerContainerBase for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBases(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostListenerContainerBase for all models
       * @return Iterator of org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBases()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostListenerContainerBase
       * @param id Identifier for org.semanticwb.social.PostListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       * @return A org.semanticwb.social.PostListenerContainerBase
       */
        public static org.semanticwb.social.PostListenerContainerBase getPostListenerContainerBase(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostListenerContainerBase)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostListenerContainerBase
       * @param id Identifier for org.semanticwb.social.PostListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       * @return A org.semanticwb.social.PostListenerContainerBase
       */
        public static org.semanticwb.social.PostListenerContainerBase createPostListenerContainerBase(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostListenerContainerBase)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostListenerContainerBase
       * @param id Identifier for org.semanticwb.social.PostListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       */
        public static void removePostListenerContainerBase(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostListenerContainerBase
       * @param id Identifier for org.semanticwb.social.PostListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       * @return true if the org.semanticwb.social.PostListenerContainerBase exists, false otherwise
       */

        public static boolean hasPostListenerContainerBase(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostListenerContainerBase(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined Plcb_Post
       * @param value Plcb_Post of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByPlcb_Post(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_plcb_Post, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined Plcb_Post
       * @param value Plcb_Post of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByPlcb_Post(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_plcb_Post,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined Plcb_SocialNetwork
       * @param value Plcb_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByPlcb_SocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_plcb_SocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined Plcb_SocialNetwork
       * @param value Plcb_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByPlcb_SocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_plcb_SocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined NewCreatedPostInv
       * @param value NewCreatedPostInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByNewCreatedPostInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasNewCreatedPostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostListenerContainerBase with a determined NewCreatedPostInv
       * @param value NewCreatedPostInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostListenerContainerBase> listPostListenerContainerBaseByNewCreatedPostInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasNewCreatedPostInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostListenerContainerBaseBase.ClassMgr getPostListenerContainerBaseClassMgr()
    {
        return new PostListenerContainerBaseBase.ClassMgr();
    }

   /**
   * Constructs a PostListenerContainerBaseBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostListenerContainerBase
   */
    public PostListenerContainerBaseBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property Plcb_Post
   * @param value Plcb_Post to set
   */

    public void setPlcb_Post(org.semanticwb.social.PostIn value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_plcb_Post, value.getSemanticObject());
        }else
        {
            removePlcb_Post();
        }
    }
   /**
   * Remove the value for Plcb_Post property
   */

    public void removePlcb_Post()
    {
        getSemanticObject().removeProperty(social_plcb_Post);
    }

   /**
   * Gets the Plcb_Post
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPlcb_Post()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_plcb_Post);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property Plcb_SocialNetwork
   * @param value Plcb_SocialNetwork to set
   */

    public void setPlcb_SocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_plcb_SocialNetwork, value.getSemanticObject());
        }else
        {
            removePlcb_SocialNetwork();
        }
    }
   /**
   * Remove the value for Plcb_SocialNetwork property
   */

    public void removePlcb_SocialNetwork()
    {
        getSemanticObject().removeProperty(social_plcb_SocialNetwork);
    }

   /**
   * Gets the Plcb_SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getPlcb_SocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_plcb_SocialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.PostOut
   * @return A GenericIterator with all the org.semanticwb.social.PostOut
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> listNewCreatedPostInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(getSemanticObject().listObjectProperties(social_hasNewCreatedPostInv));
    }

   /**
   * Gets true if has a NewCreatedPostInv
   * @param value org.semanticwb.social.PostOut to verify
   * @return true if the org.semanticwb.social.PostOut exists, false otherwise
   */
    public boolean hasNewCreatedPostInv(org.semanticwb.social.PostOut value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasNewCreatedPostInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the NewCreatedPostInv
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getNewCreatedPostInv()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasNewCreatedPostInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
         }
         return ret;
    }
}
