package org.semanticwb.bsc.element.base;


public abstract class ObjectiveBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.bsc.Serializable,org.semanticwb.model.Descriptiveable,org.semanticwb.bsc.Preference,org.semanticwb.bsc.Help,org.semanticwb.bsc.Committable,org.semanticwb.bsc.Seasonable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.bsc.Recognizable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Indicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasIndicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasIndicator");
    public static final org.semanticwb.platform.SemanticClass bsc_Theme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Theme");
    public static final org.semanticwb.platform.SemanticProperty bsc_themeInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#themeInv");
    public static final org.semanticwb.platform.SemanticClass bsc_Initiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Initiative");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasInitiative=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasInitiative");
    public static final org.semanticwb.platform.SemanticProperty bsc_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#index");
    public static final org.semanticwb.platform.SemanticClass bsc_Objective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");
   /**
   * Especifica el objetivo al cual está alineado el objetivo en cuestión, o sea el objetivo padre
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_parentObjective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#parentObjective");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Un usuario que se asigna como dueño del objetivo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_sponsor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#sponsor");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Objective");

    public static class ClassMgr
    {
       /**
       * Returns a list of Objective for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectives(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Objective for all models
       * @return Iterator of org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectives()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective>(it, true);
        }

        public static org.semanticwb.bsc.element.Objective createObjective(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Objective.ClassMgr.createObjective(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Objective
       * @param id Identifier for org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return A org.semanticwb.bsc.element.Objective
       */
        public static org.semanticwb.bsc.element.Objective getObjective(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Objective)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Objective
       * @param id Identifier for org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return A org.semanticwb.bsc.element.Objective
       */
        public static org.semanticwb.bsc.element.Objective createObjective(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Objective)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Objective
       * @param id Identifier for org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       */
        public static void removeObjective(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Objective
       * @param id Identifier for org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return true if the org.semanticwb.bsc.element.Objective exists, false otherwise
       */

        public static boolean hasObjective(String id, org.semanticwb.model.SWBModel model)
        {
            return (getObjective(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Indicator
       * @param value Indicator of the type org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByIndicator(org.semanticwb.bsc.element.Indicator value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasIndicator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Indicator
       * @param value Indicator of the type org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByIndicator(org.semanticwb.bsc.element.Indicator value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasIndicator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Theme
       * @param value Theme of the type org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByTheme(org.semanticwb.bsc.element.Theme value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_themeInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Theme
       * @param value Theme of the type org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByTheme(org.semanticwb.bsc.element.Theme value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_themeInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Initiative
       * @param value Initiative of the type org.semanticwb.bsc.element.Initiative
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByInitiative(org.semanticwb.bsc.element.Initiative value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasInitiative, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Initiative
       * @param value Initiative of the type org.semanticwb.bsc.element.Initiative
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByInitiative(org.semanticwb.bsc.element.Initiative value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasInitiative,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined ParentObjective
       * @param value ParentObjective of the type org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByParentObjective(org.semanticwb.bsc.element.Objective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_parentObjective, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined ParentObjective
       * @param value ParentObjective of the type org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveByParentObjective(org.semanticwb.bsc.element.Objective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_parentObjective,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Sponsor
       * @param value Sponsor of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveBySponsor(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_sponsor, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Objective with a determined Sponsor
       * @param value Sponsor of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Objective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Objective> listObjectiveBySponsor(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Objective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_sponsor,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a ObjectiveBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Objective
   */
    public ObjectiveBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Indicator
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Indicator
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> listIndicators()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator>(getSemanticObject().listObjectProperties(bsc_hasIndicator));
    }

   /**
   * Gets true if has a Indicator
   * @param value org.semanticwb.bsc.element.Indicator to verify
   * @return true if the org.semanticwb.bsc.element.Indicator exists, false otherwise
   */
    public boolean hasIndicator(org.semanticwb.bsc.element.Indicator value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasIndicator,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Indicator
   * @param value org.semanticwb.bsc.element.Indicator to add
   */

    public void addIndicator(org.semanticwb.bsc.element.Indicator value)
    {
        getSemanticObject().addObjectProperty(bsc_hasIndicator, value.getSemanticObject());
    }
   /**
   * Removes all the Indicator
   */

    public void removeAllIndicator()
    {
        getSemanticObject().removeProperty(bsc_hasIndicator);
    }
   /**
   * Removes a Indicator
   * @param value org.semanticwb.bsc.element.Indicator to remove
   */

    public void removeIndicator(org.semanticwb.bsc.element.Indicator value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasIndicator,value.getSemanticObject());
    }

   /**
   * Gets the Indicator
   * @return a org.semanticwb.bsc.element.Indicator
   */
    public org.semanticwb.bsc.element.Indicator getIndicator()
    {
         org.semanticwb.bsc.element.Indicator ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasIndicator);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Indicator)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Help property
* @return String with the Help
*/
    public String getHelp()
    {
        return getSemanticObject().getProperty(bsc_help);
    }

/**
* Sets the Help property
* @param value long with the Help
*/
    public void setHelp(String value)
    {
        getSemanticObject().setProperty(bsc_help, value);
    }
   /**
   * Sets the value for the property Theme
   * @param value Theme to set
   */

    public void setTheme(org.semanticwb.bsc.element.Theme value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_themeInv, value.getSemanticObject());
        }else
        {
            removeTheme();
        }
    }
   /**
   * Remove the value for Theme property
   */

    public void removeTheme()
    {
        getSemanticObject().removeProperty(bsc_themeInv);
    }

   /**
   * Gets the Theme
   * @return a org.semanticwb.bsc.element.Theme
   */
    public org.semanticwb.bsc.element.Theme getTheme()
    {
         org.semanticwb.bsc.element.Theme ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_themeInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Theme)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.bsc.element.Initiative
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Initiative
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative> listInitiatives()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Initiative>(getSemanticObject().listObjectProperties(bsc_hasInitiative));
    }

   /**
   * Gets true if has a Initiative
   * @param value org.semanticwb.bsc.element.Initiative to verify
   * @return true if the org.semanticwb.bsc.element.Initiative exists, false otherwise
   */
    public boolean hasInitiative(org.semanticwb.bsc.element.Initiative value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasInitiative,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Initiative
   * @param value org.semanticwb.bsc.element.Initiative to add
   */

    public void addInitiative(org.semanticwb.bsc.element.Initiative value)
    {
        getSemanticObject().addObjectProperty(bsc_hasInitiative, value.getSemanticObject());
    }
   /**
   * Removes all the Initiative
   */

    public void removeAllInitiative()
    {
        getSemanticObject().removeProperty(bsc_hasInitiative);
    }
   /**
   * Removes a Initiative
   * @param value org.semanticwb.bsc.element.Initiative to remove
   */

    public void removeInitiative(org.semanticwb.bsc.element.Initiative value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasInitiative,value.getSemanticObject());
    }

   /**
   * Gets the Initiative
   * @return a org.semanticwb.bsc.element.Initiative
   */
    public org.semanticwb.bsc.element.Initiative getInitiative()
    {
         org.semanticwb.bsc.element.Initiative ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasInitiative);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Initiative)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Priority property
* @return String with the Priority
*/
    public String getPriority()
    {
        return getSemanticObject().getProperty(bsc_priority);
    }

/**
* Sets the Priority property
* @param value long with the Priority
*/
    public void setPriority(String value)
    {
        getSemanticObject().setProperty(bsc_priority, value);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(bsc_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(bsc_index, value);
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        //Override this method in Objective object
        return getSemanticObject().getProperty(bsc_prefix,false);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        //Override this method in Objective object
        getSemanticObject().setProperty(bsc_prefix, value,false);
    }
   /**
   * Sets the value for the property ParentObjective
   * @param value ParentObjective to set
   */

    public void setParentObjective(org.semanticwb.bsc.element.Objective value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_parentObjective, value.getSemanticObject());
        }else
        {
            removeParentObjective();
        }
    }
   /**
   * Remove the value for ParentObjective property
   */

    public void removeParentObjective()
    {
        getSemanticObject().removeProperty(bsc_parentObjective);
    }

   /**
   * Gets the ParentObjective
   * @return a org.semanticwb.bsc.element.Objective
   */
    public org.semanticwb.bsc.element.Objective getParentObjective()
    {
         org.semanticwb.bsc.element.Objective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_parentObjective);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Objective)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Commited property
* @return boolean with the Commited
*/
    public boolean isCommited()
    {
        return getSemanticObject().getBooleanProperty(bsc_commited);
    }

/**
* Sets the Commited property
* @param value long with the Commited
*/
    public void setCommited(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_commited, value);
    }
   /**
   * Sets the value for the property Sponsor
   * @param value Sponsor to set
   */

    public void setSponsor(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_sponsor, value.getSemanticObject());
        }else
        {
            removeSponsor();
        }
    }
   /**
   * Remove the value for Sponsor property
   */

    public void removeSponsor()
    {
        getSemanticObject().removeProperty(bsc_sponsor);
    }

   /**
   * Gets the Sponsor
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getSponsor()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_sponsor);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Serial property
* @return int with the Serial
*/
    public int getSerial()
    {
        return getSemanticObject().getIntProperty(bsc_serial);
    }

/**
* Sets the Serial property
* @param value long with the Serial
*/
    public void setSerial(int value)
    {
        getSemanticObject().setIntProperty(bsc_serial, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
