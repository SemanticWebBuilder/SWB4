package com.infotec.pic.swb.base;


public abstract class SalesRangeBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_SalesRange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#SalesRange");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#SalesRange");

    public static class ClassMgr
    {
       /**
       * Returns a list of SalesRange for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.SalesRange
       */

        public static java.util.Iterator<com.infotec.pic.swb.SalesRange> listSalesRanges(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SalesRange>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.SalesRange for all models
       * @return Iterator of com.infotec.pic.swb.SalesRange
       */

        public static java.util.Iterator<com.infotec.pic.swb.SalesRange> listSalesRanges()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SalesRange>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.SalesRange
       * @param id Identifier for com.infotec.pic.swb.SalesRange
       * @param model Model of the com.infotec.pic.swb.SalesRange
       * @return A com.infotec.pic.swb.SalesRange
       */
        public static com.infotec.pic.swb.SalesRange getSalesRange(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.SalesRange)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.SalesRange
       * @param id Identifier for com.infotec.pic.swb.SalesRange
       * @param model Model of the com.infotec.pic.swb.SalesRange
       * @return A com.infotec.pic.swb.SalesRange
       */
        public static com.infotec.pic.swb.SalesRange createSalesRange(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.SalesRange)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.SalesRange
       * @param id Identifier for com.infotec.pic.swb.SalesRange
       * @param model Model of the com.infotec.pic.swb.SalesRange
       */
        public static void removeSalesRange(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.SalesRange
       * @param id Identifier for com.infotec.pic.swb.SalesRange
       * @param model Model of the com.infotec.pic.swb.SalesRange
       * @return true if the com.infotec.pic.swb.SalesRange exists, false otherwise
       */

        public static boolean hasSalesRange(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSalesRange(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.SalesRange with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.SalesRange
       * @return Iterator with all the com.infotec.pic.swb.SalesRange
       */

        public static java.util.Iterator<com.infotec.pic.swb.SalesRange> listSalesRangeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SalesRange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.SalesRange with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.SalesRange
       */

        public static java.util.Iterator<com.infotec.pic.swb.SalesRange> listSalesRangeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SalesRange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.SalesRange with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.SalesRange
       * @return Iterator with all the com.infotec.pic.swb.SalesRange
       */

        public static java.util.Iterator<com.infotec.pic.swb.SalesRange> listSalesRangeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SalesRange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.SalesRange with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.SalesRange
       */

        public static java.util.Iterator<com.infotec.pic.swb.SalesRange> listSalesRangeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SalesRange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SalesRangeBase.ClassMgr getSalesRangeClassMgr()
    {
        return new SalesRangeBase.ClassMgr();
    }

   /**
   * Constructs a SalesRangeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SalesRange
   */
    public SalesRangeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
