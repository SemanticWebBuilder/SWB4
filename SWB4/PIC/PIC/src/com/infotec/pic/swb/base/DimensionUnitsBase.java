package com.infotec.pic.swb.base;


public abstract class DimensionUnitsBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_DimensionUnits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#DimensionUnits");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#DimensionUnits");

    public static class ClassMgr
    {
       /**
       * Returns a list of DimensionUnits for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.DimensionUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.DimensionUnits> listDimensionUnitses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.DimensionUnits>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.DimensionUnits for all models
       * @return Iterator of com.infotec.pic.swb.DimensionUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.DimensionUnits> listDimensionUnitses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.DimensionUnits>(it, true);
        }

        public static com.infotec.pic.swb.DimensionUnits createDimensionUnits(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.DimensionUnits.ClassMgr.createDimensionUnits(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.DimensionUnits
       * @param id Identifier for com.infotec.pic.swb.DimensionUnits
       * @param model Model of the com.infotec.pic.swb.DimensionUnits
       * @return A com.infotec.pic.swb.DimensionUnits
       */
        public static com.infotec.pic.swb.DimensionUnits getDimensionUnits(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.DimensionUnits)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.DimensionUnits
       * @param id Identifier for com.infotec.pic.swb.DimensionUnits
       * @param model Model of the com.infotec.pic.swb.DimensionUnits
       * @return A com.infotec.pic.swb.DimensionUnits
       */
        public static com.infotec.pic.swb.DimensionUnits createDimensionUnits(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.DimensionUnits)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.DimensionUnits
       * @param id Identifier for com.infotec.pic.swb.DimensionUnits
       * @param model Model of the com.infotec.pic.swb.DimensionUnits
       */
        public static void removeDimensionUnits(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.DimensionUnits
       * @param id Identifier for com.infotec.pic.swb.DimensionUnits
       * @param model Model of the com.infotec.pic.swb.DimensionUnits
       * @return true if the com.infotec.pic.swb.DimensionUnits exists, false otherwise
       */

        public static boolean hasDimensionUnits(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDimensionUnits(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.DimensionUnits with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.DimensionUnits
       * @return Iterator with all the com.infotec.pic.swb.DimensionUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.DimensionUnits> listDimensionUnitsByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.DimensionUnits> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.DimensionUnits with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.DimensionUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.DimensionUnits> listDimensionUnitsByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.DimensionUnits> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.DimensionUnits with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.DimensionUnits
       * @return Iterator with all the com.infotec.pic.swb.DimensionUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.DimensionUnits> listDimensionUnitsByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.DimensionUnits> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.DimensionUnits with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.DimensionUnits
       */

        public static java.util.Iterator<com.infotec.pic.swb.DimensionUnits> listDimensionUnitsByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.DimensionUnits> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DimensionUnitsBase.ClassMgr getDimensionUnitsClassMgr()
    {
        return new DimensionUnitsBase.ClassMgr();
    }

   /**
   * Constructs a DimensionUnitsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DimensionUnits
   */
    public DimensionUnitsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
