package com.infotec.pic.swb.base;


public abstract class INCOTERMSBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_INCOTERMS=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#INCOTERMS");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#INCOTERMS");

    public static class ClassMgr
    {
       /**
       * Returns a list of INCOTERMS for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.INCOTERMS
       */

        public static java.util.Iterator<com.infotec.pic.swb.INCOTERMS> listINCOTERMSs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.INCOTERMS>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.INCOTERMS for all models
       * @return Iterator of com.infotec.pic.swb.INCOTERMS
       */

        public static java.util.Iterator<com.infotec.pic.swb.INCOTERMS> listINCOTERMSs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.INCOTERMS>(it, true);
        }

        public static com.infotec.pic.swb.INCOTERMS createINCOTERMS(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.INCOTERMS.ClassMgr.createINCOTERMS(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.INCOTERMS
       * @param id Identifier for com.infotec.pic.swb.INCOTERMS
       * @param model Model of the com.infotec.pic.swb.INCOTERMS
       * @return A com.infotec.pic.swb.INCOTERMS
       */
        public static com.infotec.pic.swb.INCOTERMS getINCOTERMS(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.INCOTERMS)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.INCOTERMS
       * @param id Identifier for com.infotec.pic.swb.INCOTERMS
       * @param model Model of the com.infotec.pic.swb.INCOTERMS
       * @return A com.infotec.pic.swb.INCOTERMS
       */
        public static com.infotec.pic.swb.INCOTERMS createINCOTERMS(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.INCOTERMS)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.INCOTERMS
       * @param id Identifier for com.infotec.pic.swb.INCOTERMS
       * @param model Model of the com.infotec.pic.swb.INCOTERMS
       */
        public static void removeINCOTERMS(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.INCOTERMS
       * @param id Identifier for com.infotec.pic.swb.INCOTERMS
       * @param model Model of the com.infotec.pic.swb.INCOTERMS
       * @return true if the com.infotec.pic.swb.INCOTERMS exists, false otherwise
       */

        public static boolean hasINCOTERMS(String id, org.semanticwb.model.SWBModel model)
        {
            return (getINCOTERMS(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.INCOTERMS with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.INCOTERMS
       * @return Iterator with all the com.infotec.pic.swb.INCOTERMS
       */

        public static java.util.Iterator<com.infotec.pic.swb.INCOTERMS> listINCOTERMSByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.INCOTERMS> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.INCOTERMS with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.INCOTERMS
       */

        public static java.util.Iterator<com.infotec.pic.swb.INCOTERMS> listINCOTERMSByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.INCOTERMS> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.INCOTERMS with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.INCOTERMS
       * @return Iterator with all the com.infotec.pic.swb.INCOTERMS
       */

        public static java.util.Iterator<com.infotec.pic.swb.INCOTERMS> listINCOTERMSByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.INCOTERMS> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.INCOTERMS with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.INCOTERMS
       */

        public static java.util.Iterator<com.infotec.pic.swb.INCOTERMS> listINCOTERMSByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.INCOTERMS> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static INCOTERMSBase.ClassMgr getINCOTERMSClassMgr()
    {
        return new INCOTERMSBase.ClassMgr();
    }

   /**
   * Constructs a INCOTERMSBase with a SemanticObject
   * @param base The SemanticObject with the properties for the INCOTERMS
   */
    public INCOTERMSBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
