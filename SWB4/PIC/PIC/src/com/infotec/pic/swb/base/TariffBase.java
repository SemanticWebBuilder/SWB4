package com.infotec.pic.swb.base;


public abstract class TariffBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_Tariff=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Tariff");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Tariff");

    public static class ClassMgr
    {
       /**
       * Returns a list of Tariff for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Tariff
       */

        public static java.util.Iterator<com.infotec.pic.swb.Tariff> listTariffs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Tariff>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Tariff for all models
       * @return Iterator of com.infotec.pic.swb.Tariff
       */

        public static java.util.Iterator<com.infotec.pic.swb.Tariff> listTariffs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Tariff>(it, true);
        }

        public static com.infotec.pic.swb.Tariff createTariff(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.Tariff.ClassMgr.createTariff(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.Tariff
       * @param id Identifier for com.infotec.pic.swb.Tariff
       * @param model Model of the com.infotec.pic.swb.Tariff
       * @return A com.infotec.pic.swb.Tariff
       */
        public static com.infotec.pic.swb.Tariff getTariff(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Tariff)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Tariff
       * @param id Identifier for com.infotec.pic.swb.Tariff
       * @param model Model of the com.infotec.pic.swb.Tariff
       * @return A com.infotec.pic.swb.Tariff
       */
        public static com.infotec.pic.swb.Tariff createTariff(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Tariff)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Tariff
       * @param id Identifier for com.infotec.pic.swb.Tariff
       * @param model Model of the com.infotec.pic.swb.Tariff
       */
        public static void removeTariff(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Tariff
       * @param id Identifier for com.infotec.pic.swb.Tariff
       * @param model Model of the com.infotec.pic.swb.Tariff
       * @return true if the com.infotec.pic.swb.Tariff exists, false otherwise
       */

        public static boolean hasTariff(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTariff(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Tariff with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Tariff
       * @return Iterator with all the com.infotec.pic.swb.Tariff
       */

        public static java.util.Iterator<com.infotec.pic.swb.Tariff> listTariffByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Tariff> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Tariff with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Tariff
       */

        public static java.util.Iterator<com.infotec.pic.swb.Tariff> listTariffByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Tariff> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Tariff with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Tariff
       * @return Iterator with all the com.infotec.pic.swb.Tariff
       */

        public static java.util.Iterator<com.infotec.pic.swb.Tariff> listTariffByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Tariff> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Tariff with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Tariff
       */

        public static java.util.Iterator<com.infotec.pic.swb.Tariff> listTariffByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Tariff> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TariffBase.ClassMgr getTariffClassMgr()
    {
        return new TariffBase.ClassMgr();
    }

   /**
   * Constructs a TariffBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Tariff
   */
    public TariffBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
