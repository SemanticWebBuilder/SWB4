package org.semanticwb.social.base;


   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web 
   */
public abstract class SocialPFlowBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Filterable,org.semanticwb.social.Relationable,org.semanticwb.model.XMLable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableNode,org.semanticwb.model.FilterableClass
{
   /**
   * Referencia a un objeto de tipo SocialPFlow
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowRef");
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialPFlowRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialPFlowRefInv");
   /**
   * Instancia de un recurso asociado a un flujo de publicación.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowInstance");
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialPFlowinstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialPFlowinstance");
   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlow");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlow");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialPFlow for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialPFlow for all models
       * @return Iterator of org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow>(it, true);
        }

        public static org.semanticwb.social.SocialPFlow createSocialPFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialPFlow.ClassMgr.createSocialPFlow(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialPFlow
       * @param id Identifier for org.semanticwb.social.SocialPFlow
       * @param model Model of the org.semanticwb.social.SocialPFlow
       * @return A org.semanticwb.social.SocialPFlow
       */
        public static org.semanticwb.social.SocialPFlow getSocialPFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialPFlow
       * @param id Identifier for org.semanticwb.social.SocialPFlow
       * @param model Model of the org.semanticwb.social.SocialPFlow
       * @return A org.semanticwb.social.SocialPFlow
       */
        public static org.semanticwb.social.SocialPFlow createSocialPFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialPFlow
       * @param id Identifier for org.semanticwb.social.SocialPFlow
       * @param model Model of the org.semanticwb.social.SocialPFlow
       */
        public static void removeSocialPFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialPFlow
       * @param id Identifier for org.semanticwb.social.SocialPFlow
       * @param model Model of the org.semanticwb.social.SocialPFlow
       * @return true if the org.semanticwb.social.SocialPFlow exists, false otherwise
       */

        public static boolean hasSocialPFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialPFlow(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialPFlow
       * @return Iterator with all the org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlowByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlowByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlow with a determined SocialPFlowRefInv
       * @param value SocialPFlowRefInv of the type org.semanticwb.social.SocialPFlowRef
       * @param model Model of the org.semanticwb.social.SocialPFlow
       * @return Iterator with all the org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlowBySocialPFlowRefInv(org.semanticwb.social.SocialPFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPFlowRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlow with a determined SocialPFlowRefInv
       * @param value SocialPFlowRefInv of the type org.semanticwb.social.SocialPFlowRef
       * @return Iterator with all the org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlowBySocialPFlowRefInv(org.semanticwb.social.SocialPFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPFlowRefInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialPFlow
       * @return Iterator with all the org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlowByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlowByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlow with a determined SocialPFlowinstance
       * @param value SocialPFlowinstance of the type org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.SocialPFlow
       * @return Iterator with all the org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlowBySocialPFlowinstance(org.semanticwb.social.SocialPFlowInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPFlowinstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlow with a determined SocialPFlowinstance
       * @param value SocialPFlowinstance of the type org.semanticwb.social.SocialPFlowInstance
       * @return Iterator with all the org.semanticwb.social.SocialPFlow
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlow> listSocialPFlowBySocialPFlowinstance(org.semanticwb.social.SocialPFlowInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialPFlowinstance,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialPFlowBase.ClassMgr getSocialPFlowClassMgr()
    {
        return new SocialPFlowBase.ClassMgr();
    }

   /**
   * Constructs a SocialPFlowBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialPFlow
   */
    public SocialPFlowBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Xml property
* @return String with the Xml
*/
    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

/**
* Sets the Xml property
* @param value long with the Xml
*/
    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
    }
   /**
   * Gets all the org.semanticwb.social.SocialPFlowRef
   * @return A GenericIterator with all the org.semanticwb.social.SocialPFlowRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowRef> listSocialPFlowRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowRef>(getSemanticObject().listObjectProperties(social_hasSocialPFlowRefInv));
    }

   /**
   * Gets true if has a SocialPFlowRefInv
   * @param value org.semanticwb.social.SocialPFlowRef to verify
   * @return true if the org.semanticwb.social.SocialPFlowRef exists, false otherwise
   */
    public boolean hasSocialPFlowRefInv(org.semanticwb.social.SocialPFlowRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialPFlowRefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialPFlowRefInv
   * @return a org.semanticwb.social.SocialPFlowRef
   */
    public org.semanticwb.social.SocialPFlowRef getSocialPFlowRefInv()
    {
         org.semanticwb.social.SocialPFlowRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialPFlowRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialPFlowRef)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.social.SocialPFlowInstance
   * @return A GenericIterator with all the org.semanticwb.social.SocialPFlowInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowInstance> listSocialPFlowinstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowInstance>(getSemanticObject().listObjectProperties(social_hasSocialPFlowinstance));
    }

   /**
   * Gets true if has a SocialPFlowinstance
   * @param value org.semanticwb.social.SocialPFlowInstance to verify
   * @return true if the org.semanticwb.social.SocialPFlowInstance exists, false otherwise
   */
    public boolean hasSocialPFlowinstance(org.semanticwb.social.SocialPFlowInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialPFlowinstance,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialPFlowinstance
   * @return a org.semanticwb.social.SocialPFlowInstance
   */
    public org.semanticwb.social.SocialPFlowInstance getSocialPFlowinstance()
    {
         org.semanticwb.social.SocialPFlowInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialPFlowinstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialPFlowInstance)obj.createGenericInstance();
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
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
