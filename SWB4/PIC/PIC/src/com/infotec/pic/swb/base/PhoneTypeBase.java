package com.infotec.pic.swb.base;


public abstract class PhoneTypeBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_PhoneType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#PhoneType");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#PhoneType");

    public static class ClassMgr
    {
       /**
       * Returns a list of PhoneType for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.PhoneType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PhoneType> listPhoneTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PhoneType>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.PhoneType for all models
       * @return Iterator of com.infotec.pic.swb.PhoneType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PhoneType> listPhoneTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PhoneType>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.PhoneType
       * @param id Identifier for com.infotec.pic.swb.PhoneType
       * @param model Model of the com.infotec.pic.swb.PhoneType
       * @return A com.infotec.pic.swb.PhoneType
       */
        public static com.infotec.pic.swb.PhoneType getPhoneType(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.PhoneType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.PhoneType
       * @param id Identifier for com.infotec.pic.swb.PhoneType
       * @param model Model of the com.infotec.pic.swb.PhoneType
       * @return A com.infotec.pic.swb.PhoneType
       */
        public static com.infotec.pic.swb.PhoneType createPhoneType(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.PhoneType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.PhoneType
       * @param id Identifier for com.infotec.pic.swb.PhoneType
       * @param model Model of the com.infotec.pic.swb.PhoneType
       */
        public static void removePhoneType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.PhoneType
       * @param id Identifier for com.infotec.pic.swb.PhoneType
       * @param model Model of the com.infotec.pic.swb.PhoneType
       * @return true if the com.infotec.pic.swb.PhoneType exists, false otherwise
       */

        public static boolean hasPhoneType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhoneType(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.PhoneType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.PhoneType
       * @return Iterator with all the com.infotec.pic.swb.PhoneType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PhoneType> listPhoneTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PhoneType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.PhoneType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.PhoneType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PhoneType> listPhoneTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PhoneType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.PhoneType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.PhoneType
       * @return Iterator with all the com.infotec.pic.swb.PhoneType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PhoneType> listPhoneTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PhoneType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.PhoneType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.PhoneType
       */

        public static java.util.Iterator<com.infotec.pic.swb.PhoneType> listPhoneTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.PhoneType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PhoneTypeBase.ClassMgr getPhoneTypeClassMgr()
    {
        return new PhoneTypeBase.ClassMgr();
    }

   /**
   * Constructs a PhoneTypeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PhoneType
   */
    public PhoneTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
