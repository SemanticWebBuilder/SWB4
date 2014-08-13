package org.semanticwb.social.base;


   /**
   * Clase padre de todas las acciones posibles en swbsocial 
   */
public abstract class ActionBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Clase principal para manejo de reglas en swbSocial
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRule");
    public static final org.semanticwb.platform.SemanticProperty social_actionRuleInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#actionRuleInv");
   /**
   * Clase padre de todas las acciones posibles en swbsocial
   */
    public static final org.semanticwb.platform.SemanticClass social_Action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Action");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Action");

    public static class ClassMgr
    {
       /**
       * Returns a list of Action for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Action>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Action for all models
       * @return Iterator of org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Action>(it, true);
        }

        public static org.semanticwb.social.Action createAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Action.ClassMgr.createAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Action
       * @param id Identifier for org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.Action
       * @return A org.semanticwb.social.Action
       */
        public static org.semanticwb.social.Action getAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Action)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Action
       * @param id Identifier for org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.Action
       * @return A org.semanticwb.social.Action
       */
        public static org.semanticwb.social.Action createAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Action)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Action
       * @param id Identifier for org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.Action
       */
        public static void removeAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Action
       * @param id Identifier for org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.Action
       * @return true if the org.semanticwb.social.Action exists, false otherwise
       */

        public static boolean hasAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Action with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Action
       * @return Iterator with all the org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Action> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Action with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Action> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Action with a determined ActionRuleInv
       * @param value ActionRuleInv of the type org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.Action
       * @return Iterator with all the org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActionByActionRuleInv(org.semanticwb.social.SocialRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Action> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_actionRuleInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Action with a determined ActionRuleInv
       * @param value ActionRuleInv of the type org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActionByActionRuleInv(org.semanticwb.social.SocialRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Action> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_actionRuleInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Action with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Action
       * @return Iterator with all the org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Action> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Action with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Action> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ActionBase.ClassMgr getActionClassMgr()
    {
        return new ActionBase.ClassMgr();
    }

   /**
   * Constructs a ActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Action
   */
    public ActionBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property ActionRuleInv
   * @param value ActionRuleInv to set
   */

    public void setActionRuleInv(org.semanticwb.social.SocialRule value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_actionRuleInv, value.getSemanticObject());
        }else
        {
            removeActionRuleInv();
        }
    }
   /**
   * Remove the value for ActionRuleInv property
   */

    public void removeActionRuleInv()
    {
        getSemanticObject().removeProperty(social_actionRuleInv);
    }

   /**
   * Gets the ActionRuleInv
   * @return a org.semanticwb.social.SocialRule
   */
    public org.semanticwb.social.SocialRule getActionRuleInv()
    {
         org.semanticwb.social.SocialRule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_actionRuleInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialRule)obj.createGenericInstance();
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
}
