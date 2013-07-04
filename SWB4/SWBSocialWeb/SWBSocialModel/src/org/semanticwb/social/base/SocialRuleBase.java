package org.semanticwb.social.base;


   /**
   * Clase principal para manejo de reglas en swbSocial 
   */
public abstract class SocialRuleBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.XMLable,org.semanticwb.model.Filterable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Clase padre de todas las acciones posibles en swbsocial
   */
    public static final org.semanticwb.platform.SemanticClass social_Action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Action");
   /**
   * Una Regla puede tener acciones
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasAction");
    public static final org.semanticwb.platform.SemanticClass social_SocialRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRuleRef");
    public static final org.semanticwb.platform.SemanticProperty social_hasSocialRuleRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasSocialRuleRefInv");
   /**
   * Clase principal para manejo de reglas en swbSocial
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRule");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRule");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialRule for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRules(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialRule for all models
       * @return Iterator of org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRules()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule>(it, true);
        }

        public static org.semanticwb.social.SocialRule createSocialRule(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialRule.ClassMgr.createSocialRule(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialRule
       * @param id Identifier for org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.SocialRule
       * @return A org.semanticwb.social.SocialRule
       */
        public static org.semanticwb.social.SocialRule getSocialRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialRule)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialRule
       * @param id Identifier for org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.SocialRule
       * @return A org.semanticwb.social.SocialRule
       */
        public static org.semanticwb.social.SocialRule createSocialRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialRule)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialRule
       * @param id Identifier for org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.SocialRule
       */
        public static void removeSocialRule(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialRule
       * @param id Identifier for org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.SocialRule
       * @return true if the org.semanticwb.social.SocialRule exists, false otherwise
       */

        public static boolean hasSocialRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialRule(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialRule with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRuleByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialRule with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRuleByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialRule with a determined Action
       * @param value Action of the type org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRuleByAction(org.semanticwb.social.Action value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialRule with a determined Action
       * @param value Action of the type org.semanticwb.social.Action
       * @return Iterator with all the org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRuleByAction(org.semanticwb.social.Action value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialRule with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRuleByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialRule with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRuleByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialRule with a determined SocialRuleRefInv
       * @param value SocialRuleRefInv of the type org.semanticwb.social.SocialRuleRef
       * @param model Model of the org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRuleBySocialRuleRefInv(org.semanticwb.social.SocialRuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialRuleRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialRule with a determined SocialRuleRefInv
       * @param value SocialRuleRefInv of the type org.semanticwb.social.SocialRuleRef
       * @return Iterator with all the org.semanticwb.social.SocialRule
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRule> listSocialRuleBySocialRuleRefInv(org.semanticwb.social.SocialRuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialRuleRefInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialRuleBase.ClassMgr getSocialRuleClassMgr()
    {
        return new SocialRuleBase.ClassMgr();
    }

   /**
   * Constructs a SocialRuleBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialRule
   */
    public SocialRuleBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.social.Action
   * @return A GenericIterator with all the org.semanticwb.social.Action
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.Action> listActions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Action>(getSemanticObject().listObjectProperties(social_hasAction));
    }

   /**
   * Gets true if has a Action
   * @param value org.semanticwb.social.Action to verify
   * @return true if the org.semanticwb.social.Action exists, false otherwise
   */
    public boolean hasAction(org.semanticwb.social.Action value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasAction,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Action
   * @param value org.semanticwb.social.Action to add
   */

    public void addAction(org.semanticwb.social.Action value)
    {
        getSemanticObject().addObjectProperty(social_hasAction, value.getSemanticObject());
    }
   /**
   * Removes all the Action
   */

    public void removeAllAction()
    {
        getSemanticObject().removeProperty(social_hasAction);
    }
   /**
   * Removes a Action
   * @param value org.semanticwb.social.Action to remove
   */

    public void removeAction(org.semanticwb.social.Action value)
    {
        getSemanticObject().removeObjectProperty(social_hasAction,value.getSemanticObject());
    }

   /**
   * Gets the Action
   * @return a org.semanticwb.social.Action
   */
    public org.semanticwb.social.Action getAction()
    {
         org.semanticwb.social.Action ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasAction);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Action)obj.createGenericInstance();
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
   /**
   * Gets all the org.semanticwb.social.SocialRuleRef
   * @return A GenericIterator with all the org.semanticwb.social.SocialRuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef> listSocialRuleRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef>(getSemanticObject().listObjectProperties(social_hasSocialRuleRefInv));
    }

   /**
   * Gets true if has a SocialRuleRefInv
   * @param value org.semanticwb.social.SocialRuleRef to verify
   * @return true if the org.semanticwb.social.SocialRuleRef exists, false otherwise
   */
    public boolean hasSocialRuleRefInv(org.semanticwb.social.SocialRuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialRuleRefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SocialRuleRefInv
   * @return a org.semanticwb.social.SocialRuleRef
   */
    public org.semanticwb.social.SocialRuleRef getSocialRuleRefInv()
    {
         org.semanticwb.social.SocialRuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialRuleRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialRuleRef)obj.createGenericInstance();
         }
         return ret;
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
