package org.semanticwb.process.model.base;


public abstract class InstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.process.model.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_iteration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#iteration");
    public static final org.semanticwb.platform.SemanticProperty swp_action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#action");
    public static final org.semanticwb.platform.SemanticProperty swp_execution=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#execution");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareReference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareReference");
    public static final org.semanticwb.platform.SemanticProperty swp_hasItemAwareReference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasItemAwareReference");
    public static final org.semanticwb.platform.SemanticClass swp_Instance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Instance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Instance");

    public static class ClassMgr
    {
       /**
       * Returns a list of Instance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.Instance for all models
       * @return Iterator of org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.Instance
       * @param id Identifier for org.semanticwb.process.model.Instance
       * @param model Model of the org.semanticwb.process.model.Instance
       * @return A org.semanticwb.process.model.Instance
       */
        public static org.semanticwb.process.model.Instance getInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Instance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.Instance
       * @param id Identifier for org.semanticwb.process.model.Instance
       * @param model Model of the org.semanticwb.process.model.Instance
       * @return A org.semanticwb.process.model.Instance
       */
        public static org.semanticwb.process.model.Instance createInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Instance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.Instance
       * @param id Identifier for org.semanticwb.process.model.Instance
       * @param model Model of the org.semanticwb.process.model.Instance
       */
        public static void removeInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.Instance
       * @param id Identifier for org.semanticwb.process.model.Instance
       * @param model Model of the org.semanticwb.process.model.Instance
       * @return true if the org.semanticwb.process.model.Instance exists, false otherwise
       */

        public static boolean hasInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Instance
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByEndedby(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_endedby, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined Endedby
       * @param value Endedby of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByEndedby(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_endedby,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Instance
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Instance
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByAssignedto(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined Assignedto
       * @param value Assignedto of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByAssignedto(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_assignedto,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Instance
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @param model Model of the org.semanticwb.process.model.Instance
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Instance with a determined ItemAwareReference
       * @param value ItemAwareReference of the type org.semanticwb.process.model.ItemAwareReference
       * @return Iterator with all the org.semanticwb.process.model.Instance
       */

        public static java.util.Iterator<org.semanticwb.process.model.Instance> listInstanceByItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Instance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasItemAwareReference,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static InstanceBase.ClassMgr getInstanceClassMgr()
    {
        return new InstanceBase.ClassMgr();
    }

   /**
   * Constructs a InstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Instance
   */
    public InstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Endedby
   * @param value Endedby to set
   */

    public void setEndedby(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_endedby, value.getSemanticObject());
        }else
        {
            removeEndedby();
        }
    }
   /**
   * Remove the value for Endedby property
   */

    public void removeEndedby()
    {
        getSemanticObject().removeProperty(swp_endedby);
    }

   /**
   * Gets the Endedby
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getEndedby()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_endedby);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
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
* Gets the Ended property
* @return java.util.Date with the Ended
*/
    public java.util.Date getEnded()
    {
        return getSemanticObject().getDateProperty(swp_ended);
    }

/**
* Sets the Ended property
* @param value long with the Ended
*/
    public void setEnded(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swp_ended, value);
    }

/**
* Gets the Iteration property
* @return int with the Iteration
*/
    public int getIteration()
    {
        return getSemanticObject().getIntProperty(swp_iteration);
    }

/**
* Sets the Iteration property
* @param value long with the Iteration
*/
    public void setIteration(int value)
    {
        getSemanticObject().setIntProperty(swp_iteration, value);
    }
   /**
   * Sets the value for the property Assignedto
   * @param value Assignedto to set
   */

    public void setAssignedto(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_assignedto, value.getSemanticObject());
        }else
        {
            removeAssignedto();
        }
    }
   /**
   * Remove the value for Assignedto property
   */

    public void removeAssignedto()
    {
        getSemanticObject().removeProperty(swp_assignedto);
    }

   /**
   * Gets the Assignedto
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getAssignedto()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_assignedto);
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
* Gets the Action property
* @return String with the Action
*/
    public String getAction()
    {
        return getSemanticObject().getProperty(swp_action);
    }

/**
* Sets the Action property
* @param value long with the Action
*/
    public void setAction(String value)
    {
        getSemanticObject().setProperty(swp_action, value);
    }

/**
* Gets the Execution property
* @return int with the Execution
*/
    public int getExecution()
    {
        return getSemanticObject().getIntProperty(swp_execution);
    }

/**
* Sets the Execution property
* @param value long with the Execution
*/
    public void setExecution(int value)
    {
        getSemanticObject().setIntProperty(swp_execution, value);
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
   * Gets all the org.semanticwb.process.model.ItemAwareReference
   * @return A GenericIterator with all the org.semanticwb.process.model.ItemAwareReference
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareReference> listItemAwareReferences()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAwareReference>(getSemanticObject().listObjectProperties(swp_hasItemAwareReference));
    }

   /**
   * Gets true if has a ItemAwareReference
   * @param value org.semanticwb.process.model.ItemAwareReference to verify
   * @return true if the org.semanticwb.process.model.ItemAwareReference exists, false otherwise
   */
    public boolean hasItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasItemAwareReference,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ItemAwareReference
   * @param value org.semanticwb.process.model.ItemAwareReference to add
   */

    public void addItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
    {
        getSemanticObject().addObjectProperty(swp_hasItemAwareReference, value.getSemanticObject());
    }
   /**
   * Removes all the ItemAwareReference
   */

    public void removeAllItemAwareReference()
    {
        getSemanticObject().removeProperty(swp_hasItemAwareReference);
    }
   /**
   * Removes a ItemAwareReference
   * @param value org.semanticwb.process.model.ItemAwareReference to remove
   */

    public void removeItemAwareReference(org.semanticwb.process.model.ItemAwareReference value)
    {
        getSemanticObject().removeObjectProperty(swp_hasItemAwareReference,value.getSemanticObject());
    }

   /**
   * Gets the ItemAwareReference
   * @return a org.semanticwb.process.model.ItemAwareReference
   */
    public org.semanticwb.process.model.ItemAwareReference getItemAwareReference()
    {
         org.semanticwb.process.model.ItemAwareReference ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasItemAwareReference);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemAwareReference)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Assigned property
* @return java.util.Date with the Assigned
*/
    public java.util.Date getAssigned()
    {
        return getSemanticObject().getDateProperty(swp_assigned);
    }

/**
* Sets the Assigned property
* @param value long with the Assigned
*/
    public void setAssigned(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swp_assigned, value);
    }
}
