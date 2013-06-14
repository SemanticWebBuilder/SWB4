package org.semanticwb.bsc.element.base;


public abstract class DistinctiveBase extends org.semanticwb.bsc.element.Theme implements org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Distinctive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Distinctive");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Distinctive");

    public static class ClassMgr
    {
       /**
       * Returns a list of Distinctive for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctives(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Distinctive for all models
       * @return Iterator of org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctives()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive>(it, true);
        }

        public static org.semanticwb.bsc.element.Distinctive createDistinctive(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Distinctive.ClassMgr.createDistinctive(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Distinctive
       * @param id Identifier for org.semanticwb.bsc.element.Distinctive
       * @param model Model of the org.semanticwb.bsc.element.Distinctive
       * @return A org.semanticwb.bsc.element.Distinctive
       */
        public static org.semanticwb.bsc.element.Distinctive getDistinctive(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Distinctive)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Distinctive
       * @param id Identifier for org.semanticwb.bsc.element.Distinctive
       * @param model Model of the org.semanticwb.bsc.element.Distinctive
       * @return A org.semanticwb.bsc.element.Distinctive
       */
        public static org.semanticwb.bsc.element.Distinctive createDistinctive(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Distinctive)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Distinctive
       * @param id Identifier for org.semanticwb.bsc.element.Distinctive
       * @param model Model of the org.semanticwb.bsc.element.Distinctive
       */
        public static void removeDistinctive(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Distinctive
       * @param id Identifier for org.semanticwb.bsc.element.Distinctive
       * @param model Model of the org.semanticwb.bsc.element.Distinctive
       * @return true if the org.semanticwb.bsc.element.Distinctive exists, false otherwise
       */

        public static boolean hasDistinctive(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDistinctive(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Distinctive with a determined Objective
       * @param value Objective of the type org.semanticwb.bsc.element.Objective
       * @param model Model of the org.semanticwb.bsc.element.Distinctive
       * @return Iterator with all the org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctiveByObjective(org.semanticwb.bsc.element.Objective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasObjective, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Distinctive with a determined Objective
       * @param value Objective of the type org.semanticwb.bsc.element.Objective
       * @return Iterator with all the org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctiveByObjective(org.semanticwb.bsc.element.Objective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasObjective,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Distinctive with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Distinctive
       * @return Iterator with all the org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctiveByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Distinctive with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctiveByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Distinctive with a determined Perspective
       * @param value Perspective of the type org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Distinctive
       * @return Iterator with all the org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctiveByPerspective(org.semanticwb.bsc.element.Perspective value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_perspectiveInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Distinctive with a determined Perspective
       * @param value Perspective of the type org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctiveByPerspective(org.semanticwb.bsc.element.Perspective value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_perspectiveInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Distinctive with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Distinctive
       * @return Iterator with all the org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctiveByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Distinctive with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Distinctive
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Distinctive> listDistinctiveByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Distinctive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a DistinctiveBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Distinctive
   */
    public DistinctiveBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
