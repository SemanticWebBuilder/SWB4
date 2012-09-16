package org.semanticwb.process.model.base;


public abstract class LoopCharacteristicsBase extends org.semanticwb.process.model.BaseElement implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_LoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LoopCharacteristics");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LoopCharacteristics");

    public static class ClassMgr
    {
       /**
       * Returns a list of LoopCharacteristics for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.LoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopCharacteristics> listLoopCharacteristicses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopCharacteristics>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.LoopCharacteristics for all models
       * @return Iterator of org.semanticwb.process.model.LoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopCharacteristics> listLoopCharacteristicses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopCharacteristics>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.LoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.LoopCharacteristics
       * @return A org.semanticwb.process.model.LoopCharacteristics
       */
        public static org.semanticwb.process.model.LoopCharacteristics getLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LoopCharacteristics)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.LoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.LoopCharacteristics
       * @return A org.semanticwb.process.model.LoopCharacteristics
       */
        public static org.semanticwb.process.model.LoopCharacteristics createLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LoopCharacteristics)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.LoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.LoopCharacteristics
       */
        public static void removeLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.LoopCharacteristics
       * @param id Identifier for org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.LoopCharacteristics
       * @return true if the org.semanticwb.process.model.LoopCharacteristics exists, false otherwise
       */

        public static boolean hasLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLoopCharacteristics(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.LoopCharacteristics with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.LoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.LoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopCharacteristics> listLoopCharacteristicsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopCharacteristics with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.LoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopCharacteristics> listLoopCharacteristicsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopCharacteristics with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.LoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.LoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopCharacteristics> listLoopCharacteristicsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopCharacteristics with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.LoopCharacteristics
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopCharacteristics> listLoopCharacteristicsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static LoopCharacteristicsBase.ClassMgr getLoopCharacteristicsClassMgr()
    {
        return new LoopCharacteristicsBase.ClassMgr();
    }

   /**
   * Constructs a LoopCharacteristicsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the LoopCharacteristics
   */
    public LoopCharacteristicsBase(org.semanticwb.platform.SemanticObject base)
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
}
