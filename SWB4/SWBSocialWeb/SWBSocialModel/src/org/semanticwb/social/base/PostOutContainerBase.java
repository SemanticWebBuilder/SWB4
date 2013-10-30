package org.semanticwb.social.base;


   /**
   * Clase a Cambiar despues por "Relacional". Clase que contiene TODOS los post de salida (PostOut) que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas.EN ESTE MOMENTO NO SE ESTA UTILIZANDO ESTA CLASE. SE UTILIZA POSTOUTNET EN LUGAR DE ESTA, 
   */
public abstract class PostOutContainerBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Año en el cual es creada una instancia de PostContainer, esta propiedad servira mucho  para buscar los postOut de manera optima, ya que pudieran ser muchos en un corto tiempo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_year=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#year");
   /**
   * Mes en el cual es creada una instancia de PostContainer,esta propiedad servira mucho  para buscar los postOut de manera optima, ya que pudieran ser muchos en un corto tiempo.
   */
    public static final org.semanticwb.platform.SemanticProperty social_month=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#month");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Con esta propiedad inversa, se puede saber a que red social pertenece este grupo de post agrupados por mes y año
   */
    public static final org.semanticwb.platform.SemanticProperty social_pc_SocialNetworkInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pc_SocialNetworkInv");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * Post de Salida (PostOut)
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPost");
   /**
   * Clase a Cambiar despues por "Relacional". Clase que contiene TODOS los post de salida (PostOut) que han sido enviados a una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas.EN ESTE MOMENTO NO SE ESTA UTILIZANDO ESTA CLASE. SE UTILIZA POSTOUTNET EN LUGAR DE ESTA,
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutContainer");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutContainer");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutContainer for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutContainer for all models
       * @return Iterator of org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer>(it, true);
        }

        public static org.semanticwb.social.PostOutContainer createPostOutContainer(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PostOutContainer.ClassMgr.createPostOutContainer(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PostOutContainer
       * @param id Identifier for org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.PostOutContainer
       * @return A org.semanticwb.social.PostOutContainer
       */
        public static org.semanticwb.social.PostOutContainer getPostOutContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutContainer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutContainer
       * @param id Identifier for org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.PostOutContainer
       * @return A org.semanticwb.social.PostOutContainer
       */
        public static org.semanticwb.social.PostOutContainer createPostOutContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutContainer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutContainer
       * @param id Identifier for org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.PostOutContainer
       */
        public static void removePostOutContainer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutContainer
       * @param id Identifier for org.semanticwb.social.PostOutContainer
       * @param model Model of the org.semanticwb.social.PostOutContainer
       * @return true if the org.semanticwb.social.PostOutContainer exists, false otherwise
       */

        public static boolean hasPostOutContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutContainer(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutContainer with a determined Pc_SocialNetworkInv
       * @param value Pc_SocialNetworkInv of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainerByPc_SocialNetworkInv(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_pc_SocialNetworkInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutContainer with a determined Pc_SocialNetworkInv
       * @param value Pc_SocialNetworkInv of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainerByPc_SocialNetworkInv(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_pc_SocialNetworkInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutContainer with a determined Post
       * @param value Post of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOutContainer
       * @return Iterator with all the org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainerByPost(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutContainer with a determined Post
       * @param value Post of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOutContainer
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutContainer> listPostOutContainerByPost(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPost,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutContainerBase.ClassMgr getPostOutContainerClassMgr()
    {
        return new PostOutContainerBase.ClassMgr();
    }

   /**
   * Constructs a PostOutContainerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutContainer
   */
    public PostOutContainerBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Year property
* @return String with the Year
*/
    public String getYear()
    {
        return getSemanticObject().getProperty(social_year);
    }

/**
* Sets the Year property
* @param value long with the Year
*/
    public void setYear(String value)
    {
        getSemanticObject().setProperty(social_year, value);
    }

/**
* Gets the Month property
* @return String with the Month
*/
    public String getMonth()
    {
        return getSemanticObject().getProperty(social_month);
    }

/**
* Sets the Month property
* @param value long with the Month
*/
    public void setMonth(String value)
    {
        getSemanticObject().setProperty(social_month, value);
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
   * Sets the value for the property Pc_SocialNetworkInv
   * @param value Pc_SocialNetworkInv to set
   */

    public void setPc_SocialNetworkInv(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_pc_SocialNetworkInv, value.getSemanticObject());
        }else
        {
            removePc_SocialNetworkInv();
        }
    }
   /**
   * Remove the value for Pc_SocialNetworkInv property
   */

    public void removePc_SocialNetworkInv()
    {
        getSemanticObject().removeProperty(social_pc_SocialNetworkInv);
    }

   /**
   * Gets the Pc_SocialNetworkInv
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getPc_SocialNetworkInv()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_pc_SocialNetworkInv);
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> listPosts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(getSemanticObject().listObjectProperties(social_hasPost));
    }

   /**
   * Gets true if has a Post
   * @param value org.semanticwb.social.PostOut to verify
   * @return true if the org.semanticwb.social.PostOut exists, false otherwise
   */
    public boolean hasPost(org.semanticwb.social.PostOut value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPost,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Post
   * @param value org.semanticwb.social.PostOut to add
   */

    public void addPost(org.semanticwb.social.PostOut value)
    {
        getSemanticObject().addObjectProperty(social_hasPost, value.getSemanticObject());
    }
   /**
   * Removes all the Post
   */

    public void removeAllPost()
    {
        getSemanticObject().removeProperty(social_hasPost);
    }
   /**
   * Removes a Post
   * @param value org.semanticwb.social.PostOut to remove
   */

    public void removePost(org.semanticwb.social.PostOut value)
    {
        getSemanticObject().removeObjectProperty(social_hasPost,value.getSemanticObject());
    }

   /**
   * Gets the Post
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getPost()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
         }
         return ret;
    }
}
