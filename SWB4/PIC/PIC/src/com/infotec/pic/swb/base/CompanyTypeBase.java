package com.infotec.pic.swb.base;


   /**
   * Tipo de empresa 
   */
public abstract class CompanyTypeBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Tipo de empresa
   */
    public static final org.semanticwb.platform.SemanticClass pic_CompanyType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#CompanyType");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#CompanyType");

    public static class ClassMgr
    {
       /**
       * Returns a list of CompanyType for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.CompanyType
       */

        public static java.util.Iterator<com.infotec.pic.swb.CompanyType> listCompanyTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CompanyType>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.CompanyType for all models
       * @return Iterator of com.infotec.pic.swb.CompanyType
       */

        public static java.util.Iterator<com.infotec.pic.swb.CompanyType> listCompanyTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CompanyType>(it, true);
        }

        public static com.infotec.pic.swb.CompanyType createCompanyType(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.CompanyType.ClassMgr.createCompanyType(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.CompanyType
       * @param id Identifier for com.infotec.pic.swb.CompanyType
       * @param model Model of the com.infotec.pic.swb.CompanyType
       * @return A com.infotec.pic.swb.CompanyType
       */
        public static com.infotec.pic.swb.CompanyType getCompanyType(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.CompanyType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.CompanyType
       * @param id Identifier for com.infotec.pic.swb.CompanyType
       * @param model Model of the com.infotec.pic.swb.CompanyType
       * @return A com.infotec.pic.swb.CompanyType
       */
        public static com.infotec.pic.swb.CompanyType createCompanyType(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.CompanyType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.CompanyType
       * @param id Identifier for com.infotec.pic.swb.CompanyType
       * @param model Model of the com.infotec.pic.swb.CompanyType
       */
        public static void removeCompanyType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.CompanyType
       * @param id Identifier for com.infotec.pic.swb.CompanyType
       * @param model Model of the com.infotec.pic.swb.CompanyType
       * @return true if the com.infotec.pic.swb.CompanyType exists, false otherwise
       */

        public static boolean hasCompanyType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCompanyType(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.CompanyType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.CompanyType
       * @return Iterator with all the com.infotec.pic.swb.CompanyType
       */

        public static java.util.Iterator<com.infotec.pic.swb.CompanyType> listCompanyTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CompanyType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CompanyType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.CompanyType
       */

        public static java.util.Iterator<com.infotec.pic.swb.CompanyType> listCompanyTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CompanyType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CompanyType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.CompanyType
       * @return Iterator with all the com.infotec.pic.swb.CompanyType
       */

        public static java.util.Iterator<com.infotec.pic.swb.CompanyType> listCompanyTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CompanyType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.CompanyType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.CompanyType
       */

        public static java.util.Iterator<com.infotec.pic.swb.CompanyType> listCompanyTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.CompanyType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CompanyTypeBase.ClassMgr getCompanyTypeClassMgr()
    {
        return new CompanyTypeBase.ClassMgr();
    }

   /**
   * Constructs a CompanyTypeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CompanyType
   */
    public CompanyTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
