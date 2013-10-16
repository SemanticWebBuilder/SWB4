package org.semanticwb.bsc.accessory.base;


   /**
   * Un DifferentiatorGroup es una clase que permitir contener uno o varios Differentiator que se dibujan en el mapa estratégico del scorecard. 
   */
public abstract class DifferentiatorGroupBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.Activeable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Perspective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Perspective");
    public static final org.semanticwb.platform.SemanticProperty bsc_perspectiveOfDiffGroupInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#perspectiveOfDiffGroupInv");
   /**
   * Un Differentiator se dibuja en el mapa estratégico del scorecard. Los Differentiator unicamente sirvan para alojar etiquetas de differenciadores dentro de un mapa estratégico
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Differentiator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Differentiator");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasDifferentiator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasDifferentiator");
   /**
   * Un DifferentiatorGroup es una clase que permitir contener uno o varios Differentiator que se dibujan en el mapa estratégico del scorecard.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_DifferentiatorGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DifferentiatorGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DifferentiatorGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of DifferentiatorGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.DifferentiatorGroup for all models
       * @return Iterator of org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup>(it, true);
        }

        public static org.semanticwb.bsc.accessory.DifferentiatorGroup createDifferentiatorGroup(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.accessory.DifferentiatorGroup.ClassMgr.createDifferentiatorGroup(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param id Identifier for org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return A org.semanticwb.bsc.accessory.DifferentiatorGroup
       */
        public static org.semanticwb.bsc.accessory.DifferentiatorGroup getDifferentiatorGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.DifferentiatorGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param id Identifier for org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return A org.semanticwb.bsc.accessory.DifferentiatorGroup
       */
        public static org.semanticwb.bsc.accessory.DifferentiatorGroup createDifferentiatorGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.DifferentiatorGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param id Identifier for org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */
        public static void removeDifferentiatorGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param id Identifier for org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return true if the org.semanticwb.bsc.accessory.DifferentiatorGroup exists, false otherwise
       */

        public static boolean hasDifferentiatorGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDifferentiatorGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined Perspective
       * @param value Perspective of the type org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByPerspective(org.semanticwb.bsc.element.Perspective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_perspectiveOfDiffGroupInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined Perspective
       * @param value Perspective of the type org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByPerspective(org.semanticwb.bsc.element.Perspective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_perspectiveOfDiffGroupInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined Differentiator
       * @param value Differentiator of the type org.semanticwb.bsc.accessory.Differentiator
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByDifferentiator(org.semanticwb.bsc.accessory.Differentiator value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDifferentiator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined Differentiator
       * @param value Differentiator of the type org.semanticwb.bsc.accessory.Differentiator
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByDifferentiator(org.semanticwb.bsc.accessory.Differentiator value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasDifferentiator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.bsc.accessory.DifferentiatorGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.DifferentiatorGroup with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.bsc.accessory.DifferentiatorGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> listDifferentiatorGroupByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.DifferentiatorGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DifferentiatorGroupBase.ClassMgr getDifferentiatorGroupClassMgr()
    {
        return new DifferentiatorGroupBase.ClassMgr();
    }

   /**
   * Constructs a DifferentiatorGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DifferentiatorGroup
   */
    public DifferentiatorGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Perspective
   * @param value Perspective to set
   */

    public void setPerspective(org.semanticwb.bsc.element.Perspective value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_perspectiveOfDiffGroupInv, value.getSemanticObject());
        }else
        {
            removePerspective();
        }
    }
   /**
   * Remove the value for Perspective property
   */

    public void removePerspective()
    {
        getSemanticObject().removeProperty(bsc_perspectiveOfDiffGroupInv);
    }

   /**
   * Gets the Perspective
   * @return a org.semanticwb.bsc.element.Perspective
   */
    public org.semanticwb.bsc.element.Perspective getPerspective()
    {
         org.semanticwb.bsc.element.Perspective ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_perspectiveOfDiffGroupInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Perspective)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.bsc.accessory.Differentiator
   * @return A GenericIterator with all the org.semanticwb.bsc.accessory.Differentiator
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator> listDifferentiators()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Differentiator>(getSemanticObject().listObjectProperties(bsc_hasDifferentiator));
    }

   /**
   * Gets true if has a Differentiator
   * @param value org.semanticwb.bsc.accessory.Differentiator to verify
   * @return true if the org.semanticwb.bsc.accessory.Differentiator exists, false otherwise
   */
    public boolean hasDifferentiator(org.semanticwb.bsc.accessory.Differentiator value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasDifferentiator,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Differentiator
   * @param value org.semanticwb.bsc.accessory.Differentiator to add
   */

    public void addDifferentiator(org.semanticwb.bsc.accessory.Differentiator value)
    {
        getSemanticObject().addObjectProperty(bsc_hasDifferentiator, value.getSemanticObject());
    }
   /**
   * Removes all the Differentiator
   */

    public void removeAllDifferentiator()
    {
        getSemanticObject().removeProperty(bsc_hasDifferentiator);
    }
   /**
   * Removes a Differentiator
   * @param value org.semanticwb.bsc.accessory.Differentiator to remove
   */

    public void removeDifferentiator(org.semanticwb.bsc.accessory.Differentiator value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasDifferentiator,value.getSemanticObject());
    }

   /**
   * Gets the Differentiator
   * @return a org.semanticwb.bsc.accessory.Differentiator
   */
    public org.semanticwb.bsc.accessory.Differentiator getDifferentiator()
    {
         org.semanticwb.bsc.accessory.Differentiator ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasDifferentiator);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.Differentiator)obj.createGenericInstance();
         }
         return ret;
    }
}
