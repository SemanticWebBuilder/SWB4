package org.semanticwb.social.base;


   /**
   * Clase de tipo catálogo que define las privacidades para los Post 
   */
public abstract class PostOutPrivacyBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Relación de Privacidad entre PostOut y las redes sociales a las cuales se envía.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutPrivacyRelation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutPrivacyRelation");
   /**
   * Propiedad que lista todas las instancias de PostOutPrivacyRelation que tengan asociada un tipo de privacidad. Si se elimina un tipo de privacidad (Una instancia) se eliminarían todas las instancias de PostOutPrivacyRelation que tengan relacionada ese tipo de Privacidad.
   */
    public static final org.semanticwb.platform.SemanticProperty social_haspopr_privacy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#haspopr_privacy");
   /**
   * Clase en la que se agregan instancias de tipo SocialNetwork, para que alguna  sea asignada a un tipo de privacidad (Privacy) por parte de un administrador
   */
    public static final org.semanticwb.platform.SemanticClass social_NetworkType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#NetworkType");
   /**
   * Redes Sociales a las que aplica
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasNetworkType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasNetworkType");
   /**
   * Clase de tipo catálogo que define las privacidades para los Post
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutPrivacy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutPrivacy");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutPrivacy");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutPrivacy for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutPrivacy
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacy> listPostOutPrivacies(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacy>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutPrivacy for all models
       * @return Iterator of org.semanticwb.social.PostOutPrivacy
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacy> listPostOutPrivacies()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacy>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostOutPrivacy
       * @param id Identifier for org.semanticwb.social.PostOutPrivacy
       * @param model Model of the org.semanticwb.social.PostOutPrivacy
       * @return A org.semanticwb.social.PostOutPrivacy
       */
        public static org.semanticwb.social.PostOutPrivacy getPostOutPrivacy(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutPrivacy)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutPrivacy
       * @param id Identifier for org.semanticwb.social.PostOutPrivacy
       * @param model Model of the org.semanticwb.social.PostOutPrivacy
       * @return A org.semanticwb.social.PostOutPrivacy
       */
        public static org.semanticwb.social.PostOutPrivacy createPostOutPrivacy(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutPrivacy)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutPrivacy
       * @param id Identifier for org.semanticwb.social.PostOutPrivacy
       * @param model Model of the org.semanticwb.social.PostOutPrivacy
       */
        public static void removePostOutPrivacy(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutPrivacy
       * @param id Identifier for org.semanticwb.social.PostOutPrivacy
       * @param model Model of the org.semanticwb.social.PostOutPrivacy
       * @return true if the org.semanticwb.social.PostOutPrivacy exists, false otherwise
       */

        public static boolean hasPostOutPrivacy(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutPrivacy(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacy with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutPrivacy
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacy
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacy> listPostOutPrivacyByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacy> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacy with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacy
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacy> listPostOutPrivacyByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacy> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacy with a determined Popr_privacy
       * @param value Popr_privacy of the type org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.PostOutPrivacy
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacy
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacy> listPostOutPrivacyByPopr_privacy(org.semanticwb.social.PostOutPrivacyRelation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacy> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_privacy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacy with a determined Popr_privacy
       * @param value Popr_privacy of the type org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacy
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacy> listPostOutPrivacyByPopr_privacy(org.semanticwb.social.PostOutPrivacyRelation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacy> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_haspopr_privacy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacy with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOutPrivacy
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacy
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacy> listPostOutPrivacyByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacy> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacy with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacy
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacy> listPostOutPrivacyByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacy> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutPrivacyBase.ClassMgr getPostOutPrivacyClassMgr()
    {
        return new PostOutPrivacyBase.ClassMgr();
    }

   /**
   * Constructs a PostOutPrivacyBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutPrivacy
   */
    public PostOutPrivacyBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.social.PostOutPrivacyRelation
   * @return A GenericIterator with all the org.semanticwb.social.PostOutPrivacyRelation
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation> listpopr_privacies()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation>(getSemanticObject().listObjectProperties(social_haspopr_privacy));
    }

   /**
   * Gets true if has a popr_privacy
   * @param value org.semanticwb.social.PostOutPrivacyRelation to verify
   * @return true if the org.semanticwb.social.PostOutPrivacyRelation exists, false otherwise
   */
    public boolean haspopr_privacy(org.semanticwb.social.PostOutPrivacyRelation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_haspopr_privacy,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the popr_privacy
   * @return a org.semanticwb.social.PostOutPrivacyRelation
   */
    public org.semanticwb.social.PostOutPrivacyRelation getpopr_privacy()
    {
         org.semanticwb.social.PostOutPrivacyRelation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_haspopr_privacy);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOutPrivacyRelation)obj.createGenericInstance();
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

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listNetworkTypes()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(social_hasNetworkType.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addNetworkType(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(social_hasNetworkType, value);
    }

    public void removeAllNetworkType()
    {
        getSemanticObject().removeProperty(social_hasNetworkType);
    }

    public void removeNetworkType(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().removeObjectProperty(social_hasNetworkType,value);
    }

/**
* Gets the NetworkType property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getNetworkType()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(social_hasNetworkType);
         return ret;
    }
}
