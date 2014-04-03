package org.semanticwb.social.base;


   /**
   * Clase que guarda la relación de los links que se encuentran en el texto de un PostOuts y los hits (clicks) que tienen por parte de los usuarios en las redes sociales a las cuales se envío (dicho PostOut). 
   */
public abstract class PostOutLinksHitsBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Short Url definida para el link enviado a la cuenta de red social y en el PostOut dados.
   */
    public static final org.semanticwb.platform.SemanticProperty social_pol_code=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pol_code");
   /**
   * Url a la cual se va a redirigir a los usuarios que den click en la liga corta generada para la red social definida en cada instancia de esta clase.
   */
    public static final org.semanticwb.platform.SemanticProperty social_targetUrl=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#targetUrl");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * PostOut que contiene el Link
   */
    public static final org.semanticwb.platform.SemanticProperty social_postOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postOut");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * SocialNetwork al cual fué enviado el PostOut y a la cual se le estaran sumando Htis cada que los usuarios den click en dicha cuenta de red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_SocialNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#SocialNet");
   /**
   * Número de hits (Clicks) que tiene un link en un PostOut y en una cuenta de red social dados.
   */
    public static final org.semanticwb.platform.SemanticProperty social_pol_hits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pol_hits");
   /**
   * Clase que guarda la relación de los links que se encuentran en el texto de un PostOuts y los hits (clicks) que tienen por parte de los usuarios en las redes sociales a las cuales se envío (dicho PostOut).
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutLinksHits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutLinksHits");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutLinksHits");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutLinksHits for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutLinksHits for all models
       * @return Iterator of org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits>(it, true);
        }

        public static org.semanticwb.social.PostOutLinksHits createPostOutLinksHits(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PostOutLinksHits.ClassMgr.createPostOutLinksHits(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PostOutLinksHits
       * @param id Identifier for org.semanticwb.social.PostOutLinksHits
       * @param model Model of the org.semanticwb.social.PostOutLinksHits
       * @return A org.semanticwb.social.PostOutLinksHits
       */
        public static org.semanticwb.social.PostOutLinksHits getPostOutLinksHits(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutLinksHits)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutLinksHits
       * @param id Identifier for org.semanticwb.social.PostOutLinksHits
       * @param model Model of the org.semanticwb.social.PostOutLinksHits
       * @return A org.semanticwb.social.PostOutLinksHits
       */
        public static org.semanticwb.social.PostOutLinksHits createPostOutLinksHits(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutLinksHits)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutLinksHits
       * @param id Identifier for org.semanticwb.social.PostOutLinksHits
       * @param model Model of the org.semanticwb.social.PostOutLinksHits
       */
        public static void removePostOutLinksHits(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutLinksHits
       * @param id Identifier for org.semanticwb.social.PostOutLinksHits
       * @param model Model of the org.semanticwb.social.PostOutLinksHits
       * @return true if the org.semanticwb.social.PostOutLinksHits exists, false otherwise
       */

        public static boolean hasPostOutLinksHits(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutLinksHits(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHits with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutLinksHits
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHits with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHits with a determined PostOut
       * @param value PostOut of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOutLinksHits
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitsByPostOut(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postOut, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHits with a determined PostOut
       * @param value PostOut of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitsByPostOut(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postOut,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHits with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutLinksHits
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHits with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHits with a determined SocialNet
       * @param value SocialNet of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostOutLinksHits
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitsBySocialNet(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_SocialNet, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutLinksHits with a determined SocialNet
       * @param value SocialNet of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostOutLinksHits
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutLinksHits> listPostOutLinksHitsBySocialNet(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutLinksHits> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_SocialNet,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutLinksHitsBase.ClassMgr getPostOutLinksHitsClassMgr()
    {
        return new PostOutLinksHitsBase.ClassMgr();
    }

   /**
   * Constructs a PostOutLinksHitsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutLinksHits
   */
    public PostOutLinksHitsBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Pol_code property
* @return String with the Pol_code
*/
    public String getPol_code()
    {
        return getSemanticObject().getProperty(social_pol_code);
    }

/**
* Sets the Pol_code property
* @param value long with the Pol_code
*/
    public void setPol_code(String value)
    {
        getSemanticObject().setProperty(social_pol_code, value);
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
* Gets the TargetUrl property
* @return String with the TargetUrl
*/
    public String getTargetUrl()
    {
        return getSemanticObject().getProperty(social_targetUrl);
    }

/**
* Sets the TargetUrl property
* @param value long with the TargetUrl
*/
    public void setTargetUrl(String value)
    {
        getSemanticObject().setProperty(social_targetUrl, value);
    }
   /**
   * Sets the value for the property PostOut
   * @param value PostOut to set
   */

    public void setPostOut(org.semanticwb.social.PostOut value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postOut, value.getSemanticObject());
        }else
        {
            removePostOut();
        }
    }
   /**
   * Remove the value for PostOut property
   */

    public void removePostOut()
    {
        getSemanticObject().removeProperty(social_postOut);
    }

   /**
   * Gets the PostOut
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getPostOut()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postOut);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
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
   * Sets the value for the property SocialNet
   * @param value SocialNet to set
   */

    public void setSocialNet(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_SocialNet, value.getSemanticObject());
        }else
        {
            removeSocialNet();
        }
    }
   /**
   * Remove the value for SocialNet property
   */

    public void removeSocialNet()
    {
        getSemanticObject().removeProperty(social_SocialNet);
    }

   /**
   * Gets the SocialNet
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSocialNet()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_SocialNet);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Pol_hits property
* @return int with the Pol_hits
*/
    public int getPol_hits()
    {
        return getSemanticObject().getIntProperty(social_pol_hits);
    }

/**
* Sets the Pol_hits property
* @param value long with the Pol_hits
*/
    public void setPol_hits(int value)
    {
        getSemanticObject().setIntProperty(social_pol_hits, value);
    }
}
