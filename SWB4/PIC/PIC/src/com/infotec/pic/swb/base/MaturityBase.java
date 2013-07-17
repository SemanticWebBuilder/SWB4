package com.infotec.pic.swb.base;


public abstract class MaturityBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_Maturity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Maturity");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Maturity");

    public static class ClassMgr
    {
       /**
       * Returns a list of Maturity for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Maturity
       */

        public static java.util.Iterator<com.infotec.pic.swb.Maturity> listMaturities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Maturity>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Maturity for all models
       * @return Iterator of com.infotec.pic.swb.Maturity
       */

        public static java.util.Iterator<com.infotec.pic.swb.Maturity> listMaturities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Maturity>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.Maturity
       * @param id Identifier for com.infotec.pic.swb.Maturity
       * @param model Model of the com.infotec.pic.swb.Maturity
       * @return A com.infotec.pic.swb.Maturity
       */
        public static com.infotec.pic.swb.Maturity getMaturity(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Maturity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Maturity
       * @param id Identifier for com.infotec.pic.swb.Maturity
       * @param model Model of the com.infotec.pic.swb.Maturity
       * @return A com.infotec.pic.swb.Maturity
       */
        public static com.infotec.pic.swb.Maturity createMaturity(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Maturity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Maturity
       * @param id Identifier for com.infotec.pic.swb.Maturity
       * @param model Model of the com.infotec.pic.swb.Maturity
       */
        public static void removeMaturity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Maturity
       * @param id Identifier for com.infotec.pic.swb.Maturity
       * @param model Model of the com.infotec.pic.swb.Maturity
       * @return true if the com.infotec.pic.swb.Maturity exists, false otherwise
       */

        public static boolean hasMaturity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMaturity(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Maturity with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Maturity
       * @return Iterator with all the com.infotec.pic.swb.Maturity
       */

        public static java.util.Iterator<com.infotec.pic.swb.Maturity> listMaturityByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Maturity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Maturity with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Maturity
       */

        public static java.util.Iterator<com.infotec.pic.swb.Maturity> listMaturityByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Maturity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Maturity with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Maturity
       * @return Iterator with all the com.infotec.pic.swb.Maturity
       */

        public static java.util.Iterator<com.infotec.pic.swb.Maturity> listMaturityByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Maturity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Maturity with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Maturity
       */

        public static java.util.Iterator<com.infotec.pic.swb.Maturity> listMaturityByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Maturity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MaturityBase.ClassMgr getMaturityClassMgr()
    {
        return new MaturityBase.ClassMgr();
    }

   /**
   * Constructs a MaturityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Maturity
   */
    public MaturityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
