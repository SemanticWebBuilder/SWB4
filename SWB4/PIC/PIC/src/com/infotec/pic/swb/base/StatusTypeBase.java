package com.infotec.pic.swb.base;


public abstract class StatusTypeBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_StatusType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#StatusType");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#StatusType");

    public static class ClassMgr
    {
       /**
       * Returns a list of StatusType for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.StatusType
       */

        public static java.util.Iterator<com.infotec.pic.swb.StatusType> listStatusTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.StatusType>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.StatusType for all models
       * @return Iterator of com.infotec.pic.swb.StatusType
       */

        public static java.util.Iterator<com.infotec.pic.swb.StatusType> listStatusTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.StatusType>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.StatusType
       * @param id Identifier for com.infotec.pic.swb.StatusType
       * @param model Model of the com.infotec.pic.swb.StatusType
       * @return A com.infotec.pic.swb.StatusType
       */
        public static com.infotec.pic.swb.StatusType getStatusType(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.StatusType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.StatusType
       * @param id Identifier for com.infotec.pic.swb.StatusType
       * @param model Model of the com.infotec.pic.swb.StatusType
       * @return A com.infotec.pic.swb.StatusType
       */
        public static com.infotec.pic.swb.StatusType createStatusType(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.StatusType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.StatusType
       * @param id Identifier for com.infotec.pic.swb.StatusType
       * @param model Model of the com.infotec.pic.swb.StatusType
       */
        public static void removeStatusType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.StatusType
       * @param id Identifier for com.infotec.pic.swb.StatusType
       * @param model Model of the com.infotec.pic.swb.StatusType
       * @return true if the com.infotec.pic.swb.StatusType exists, false otherwise
       */

        public static boolean hasStatusType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStatusType(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.StatusType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.StatusType
       * @return Iterator with all the com.infotec.pic.swb.StatusType
       */

        public static java.util.Iterator<com.infotec.pic.swb.StatusType> listStatusTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.StatusType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.StatusType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.StatusType
       */

        public static java.util.Iterator<com.infotec.pic.swb.StatusType> listStatusTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.StatusType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.StatusType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.StatusType
       * @return Iterator with all the com.infotec.pic.swb.StatusType
       */

        public static java.util.Iterator<com.infotec.pic.swb.StatusType> listStatusTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.StatusType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.StatusType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.StatusType
       */

        public static java.util.Iterator<com.infotec.pic.swb.StatusType> listStatusTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.StatusType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StatusTypeBase.ClassMgr getStatusTypeClassMgr()
    {
        return new StatusTypeBase.ClassMgr();
    }

   /**
   * Constructs a StatusTypeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StatusType
   */
    public StatusTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
