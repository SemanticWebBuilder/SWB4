package com.infotec.pic.swb.base;


public abstract class WeightUnitsBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_WeightUnits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#WeightUnits");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#WeightUnits");

    public static class ClassMgr
    {
       /**
       * Returns a list of WeightUnits for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.WeightUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.WeightUnits> listWeightUnitses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.WeightUnits>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.WeightUnits for all models
       * @return Iterator of com.infotec.pic.swb.WeightUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.WeightUnits> listWeightUnitses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.WeightUnits>(it, true);
        }

        public static com.infotec.pic.swb.WeightUnits createWeightUnits(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.WeightUnits.ClassMgr.createWeightUnits(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.WeightUnits
       * @param id Identifier for com.infotec.pic.swb.WeightUnits
       * @param model Model of the com.infotec.pic.swb.WeightUnits
       * @return A com.infotec.pic.swb.WeightUnits
       */
        public static com.infotec.pic.swb.WeightUnits getWeightUnits(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.WeightUnits)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.WeightUnits
       * @param id Identifier for com.infotec.pic.swb.WeightUnits
       * @param model Model of the com.infotec.pic.swb.WeightUnits
       * @return A com.infotec.pic.swb.WeightUnits
       */
        public static com.infotec.pic.swb.WeightUnits createWeightUnits(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.WeightUnits)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.WeightUnits
       * @param id Identifier for com.infotec.pic.swb.WeightUnits
       * @param model Model of the com.infotec.pic.swb.WeightUnits
       */
        public static void removeWeightUnits(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.WeightUnits
       * @param id Identifier for com.infotec.pic.swb.WeightUnits
       * @param model Model of the com.infotec.pic.swb.WeightUnits
       * @return true if the com.infotec.pic.swb.WeightUnits exists, false otherwise
       */

        public static boolean hasWeightUnits(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWeightUnits(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.WeightUnits with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.WeightUnits
       * @return Iterator with all the com.infotec.pic.swb.WeightUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.WeightUnits> listWeightUnitsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.WeightUnits> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.WeightUnits with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.WeightUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.WeightUnits> listWeightUnitsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.WeightUnits> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.WeightUnits with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.WeightUnits
       * @return Iterator with all the com.infotec.pic.swb.WeightUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.WeightUnits> listWeightUnitsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.WeightUnits> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.WeightUnits with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.WeightUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.WeightUnits> listWeightUnitsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.WeightUnits> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static WeightUnitsBase.ClassMgr getWeightUnitsClassMgr()
    {
        return new WeightUnitsBase.ClassMgr();
    }

   /**
   * Constructs a WeightUnitsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WeightUnits
   */
    public WeightUnitsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
