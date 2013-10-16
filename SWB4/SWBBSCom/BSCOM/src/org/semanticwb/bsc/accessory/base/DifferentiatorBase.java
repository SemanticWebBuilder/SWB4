package org.semanticwb.bsc.accessory.base;


   /**
   * Un Differentiator se dibuja en el mapa estratégico del scorecard. Los Differentiator unicamente sirvan para alojar etiquetas de differenciadores dentro de un mapa estratégico 
   */
public abstract class DifferentiatorBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.Activeable,org.semanticwb.model.Undeleteable,org.semanticwb.bsc.Seasonable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.bsc.Sortable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable
{
   /**
   * Un DifferentiatorGroup es una clase que permitir contener uno o varios Differentiator que se dibujan en el mapa estratégico del scorecard.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DifferentiatorGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DifferentiatorGroup");
    public static final org.semanticwb.platform.SemanticProperty bsc_differentiatorGroupInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#differentiatorGroupInv");
   /**
   * Un Differentiator se dibuja en el mapa estratégico del scorecard. Los Differentiator unicamente sirvan para alojar etiquetas de differenciadores dentro de un mapa estratégico
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Differentiator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Differentiator");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Differentiator");

    public static class ClassMgr
    {
       /**
       * Returns a list of Differentiator for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiators(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.Differentiator for all models
       * @return Iterator of org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiators()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator>(it, true);
        }

        public static org.semanticwb.bsc.accessory.Differentiator createDifferentiator(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.accessory.Differentiator.ClassMgr.createDifferentiator(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.Differentiator
       * @param id Identifier for org.semanticwb.bsc.accessory.Differentiator
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return A org.semanticwb.bsc.accessory.Differentiator
       */
        public static org.semanticwb.bsc.accessory.Differentiator getDifferentiator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.Differentiator)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.Differentiator
       * @param id Identifier for org.semanticwb.bsc.accessory.Differentiator
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return A org.semanticwb.bsc.accessory.Differentiator
       */
        public static org.semanticwb.bsc.accessory.Differentiator createDifferentiator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.Differentiator)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.Differentiator
       * @param id Identifier for org.semanticwb.bsc.accessory.Differentiator
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       */
        public static void removeDifferentiator(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.Differentiator
       * @param id Identifier for org.semanticwb.bsc.accessory.Differentiator
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return true if the org.semanticwb.bsc.accessory.Differentiator exists, false otherwise
       */

        public static boolean hasDifferentiator(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDifferentiator(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByPeriod(org.semanticwb.bsc.accessory.Period value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriod, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByPeriod(org.semanticwb.bsc.accessory.Period value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasPeriod,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined DifferentiatorGroup
       * @param value DifferentiatorGroup of the type org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByDifferentiatorGroup(org.semanticwb.bsc.accessory.DifferentiatorGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_differentiatorGroupInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined DifferentiatorGroup
       * @param value DifferentiatorGroup of the type org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByDifferentiatorGroup(org.semanticwb.bsc.accessory.DifferentiatorGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_differentiatorGroupInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.bsc.accessory.Differentiator
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Differentiator with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.bsc.accessory.Differentiator
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiatorByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DifferentiatorBase.ClassMgr getDifferentiatorClassMgr()
    {
        return new DifferentiatorBase.ClassMgr();
    }

   /**
   * Constructs a DifferentiatorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Differentiator
   */
    public DifferentiatorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.accessory.Period
   * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Period
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> listPeriods()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period>(getSemanticObject().listObjectProperties(bsc_hasPeriod));
    }

   /**
   * Gets true if has a Period
   * @param value org.semanticwb.bsc.accessory.Period to verify
   * @return true if the org.semanticwb.bsc.accessory.Period exists, false otherwise
   */
    public boolean hasPeriod(org.semanticwb.bsc.accessory.Period value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasPeriod,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Period
   * @param value org.semanticwb.bsc.accessory.Period to add
   */

    public void addPeriod(org.semanticwb.bsc.accessory.Period value)
    {
        getSemanticObject().addObjectProperty(bsc_hasPeriod, value.getSemanticObject());
    }
   /**
   * Removes all the Period
   */

    public void removeAllPeriod()
    {
        getSemanticObject().removeProperty(bsc_hasPeriod);
    }
   /**
   * Removes a Period
   * @param value org.semanticwb.bsc.accessory.Period to remove
   */

    public void removePeriod(org.semanticwb.bsc.accessory.Period value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasPeriod,value.getSemanticObject());
    }

   /**
   * Gets the Period
   * @return a org.semanticwb.bsc.accessory.Period
   */
    public org.semanticwb.bsc.accessory.Period getPeriod()
    {
         org.semanticwb.bsc.accessory.Period ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasPeriod);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.Period)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property DifferentiatorGroup
   * @param value DifferentiatorGroup to set
   */

    public void setDifferentiatorGroup(org.semanticwb.bsc.accessory.DifferentiatorGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_differentiatorGroupInv, value.getSemanticObject());
        }else
        {
            removeDifferentiatorGroup();
        }
    }
   /**
   * Remove the value for DifferentiatorGroup property
   */

    public void removeDifferentiatorGroup()
    {
        getSemanticObject().removeProperty(bsc_differentiatorGroupInv);
    }

   /**
   * Gets the DifferentiatorGroup
   * @return a org.semanticwb.bsc.accessory.DifferentiatorGroup
   */
    public org.semanticwb.bsc.accessory.DifferentiatorGroup getDifferentiatorGroup()
    {
         org.semanticwb.bsc.accessory.DifferentiatorGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_differentiatorGroupInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.DifferentiatorGroup)obj.createGenericInstance();
         }
         return ret;
    }
}
