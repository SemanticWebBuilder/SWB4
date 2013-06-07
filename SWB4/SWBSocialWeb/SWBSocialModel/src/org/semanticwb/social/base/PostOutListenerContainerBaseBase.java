package org.semanticwb.social.base;


   /**
   * BORRAR---->Clase a Cambiar despues por "Relacional".  Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales.Si se elimina un post que ha sido tomado como base(PostIn), se debe de eliminar la instancia asociada de esta clase (en la propiedad plcb_Post). NO UTILIZAR ESTA CLASE, YA REEMPLACE SU FUNCIONALIDAD COLOCANDO 2 PROPIEDADES A LA CLASE PostOut:postInOrigen y hasSocialNetwork. 
   */
public abstract class PostOutListenerContainerBaseBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
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
   * BORRAR---->Clase a Cambiar despues por "Relacional".  Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales.Si se elimina un post que ha sido tomado como base(PostIn), se debe de eliminar la instancia asociada de esta clase (en la propiedad plcb_Post). NO UTILIZAR ESTA CLASE, YA REEMPLACE SU FUNCIONALIDAD COLOCANDO 2 PROPIEDADES A LA CLASE PostOut:postInOrigen y hasSocialNetwork.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutListenerContainerBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutListenerContainerBase");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutListenerContainerBase");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutListenerContainerBase for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBases(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutListenerContainerBase for all models
       * @return Iterator of org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBases()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostOutListenerContainerBase
       * @param id Identifier for org.semanticwb.social.PostOutListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostOutListenerContainerBase
       * @return A org.semanticwb.social.PostOutListenerContainerBase
       */
        public static org.semanticwb.social.PostOutListenerContainerBase getPostOutListenerContainerBase(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutListenerContainerBase)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutListenerContainerBase
       * @param id Identifier for org.semanticwb.social.PostOutListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostOutListenerContainerBase
       * @return A org.semanticwb.social.PostOutListenerContainerBase
       */
        public static org.semanticwb.social.PostOutListenerContainerBase createPostOutListenerContainerBase(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutListenerContainerBase)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutListenerContainerBase
       * @param id Identifier for org.semanticwb.social.PostOutListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostOutListenerContainerBase
       */
        public static void removePostOutListenerContainerBase(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutListenerContainerBase
       * @param id Identifier for org.semanticwb.social.PostOutListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostOutListenerContainerBase
       * @return true if the org.semanticwb.social.PostOutListenerContainerBase exists, false otherwise
       */

        public static boolean hasPostOutListenerContainerBase(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutListenerContainerBase(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutListenerContainerBase with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBaseByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutListenerContainerBase with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBaseByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutListenerContainerBase with a determined Plcb_Post
       * @param value Plcb_Post of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostOutListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBaseByPlcb_Post(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_plcb_Post, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutListenerContainerBase with a determined Plcb_Post
       * @param value Plcb_Post of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBaseByPlcb_Post(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_plcb_Post,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutListenerContainerBase with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBaseByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutListenerContainerBase with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBaseByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutListenerContainerBase with a determined Plcb_SocialNetwork
       * @param value Plcb_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostOutListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBaseByPlcb_SocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_plcb_SocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutListenerContainerBase with a determined Plcb_SocialNetwork
       * @param value Plcb_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostOutListenerContainerBase
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutListenerContainerBase> listPostOutListenerContainerBaseByPlcb_SocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutListenerContainerBase> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_plcb_SocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutListenerContainerBaseBase.ClassMgr getPostOutListenerContainerBaseClassMgr()
    {
        return new PostOutListenerContainerBaseBase.ClassMgr();
    }

   /**
   * Constructs a PostOutListenerContainerBaseBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutListenerContainerBase
   */
    public PostOutListenerContainerBaseBase(org.semanticwb.platform.SemanticObject base)
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
}
