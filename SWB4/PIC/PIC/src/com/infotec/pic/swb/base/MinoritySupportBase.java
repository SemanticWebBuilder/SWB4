package com.infotec.pic.swb.base;


public abstract class MinoritySupportBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_MinoritySupport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#MinoritySupport");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#MinoritySupport");

    public static class ClassMgr
    {
       /**
       * Returns a list of MinoritySupport for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.MinoritySupport
       */

        public static java.util.Iterator<com.infotec.pic.swb.MinoritySupport> listMinoritySupports(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.MinoritySupport>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.MinoritySupport for all models
       * @return Iterator of com.infotec.pic.swb.MinoritySupport
       */

        public static java.util.Iterator<com.infotec.pic.swb.MinoritySupport> listMinoritySupports()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.MinoritySupport>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.MinoritySupport
       * @param id Identifier for com.infotec.pic.swb.MinoritySupport
       * @param model Model of the com.infotec.pic.swb.MinoritySupport
       * @return A com.infotec.pic.swb.MinoritySupport
       */
        public static com.infotec.pic.swb.MinoritySupport getMinoritySupport(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.MinoritySupport)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.MinoritySupport
       * @param id Identifier for com.infotec.pic.swb.MinoritySupport
       * @param model Model of the com.infotec.pic.swb.MinoritySupport
       * @return A com.infotec.pic.swb.MinoritySupport
       */
        public static com.infotec.pic.swb.MinoritySupport createMinoritySupport(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.MinoritySupport)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.MinoritySupport
       * @param id Identifier for com.infotec.pic.swb.MinoritySupport
       * @param model Model of the com.infotec.pic.swb.MinoritySupport
       */
        public static void removeMinoritySupport(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.MinoritySupport
       * @param id Identifier for com.infotec.pic.swb.MinoritySupport
       * @param model Model of the com.infotec.pic.swb.MinoritySupport
       * @return true if the com.infotec.pic.swb.MinoritySupport exists, false otherwise
       */

        public static boolean hasMinoritySupport(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMinoritySupport(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.MinoritySupport with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.MinoritySupport
       * @return Iterator with all the com.infotec.pic.swb.MinoritySupport
       */

        public static java.util.Iterator<com.infotec.pic.swb.MinoritySupport> listMinoritySupportByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.MinoritySupport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.MinoritySupport with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.MinoritySupport
       */

        public static java.util.Iterator<com.infotec.pic.swb.MinoritySupport> listMinoritySupportByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.MinoritySupport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.MinoritySupport with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.MinoritySupport
       * @return Iterator with all the com.infotec.pic.swb.MinoritySupport
       */

        public static java.util.Iterator<com.infotec.pic.swb.MinoritySupport> listMinoritySupportByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.MinoritySupport> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.MinoritySupport with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.MinoritySupport
       */

        public static java.util.Iterator<com.infotec.pic.swb.MinoritySupport> listMinoritySupportByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.MinoritySupport> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MinoritySupportBase.ClassMgr getMinoritySupportClassMgr()
    {
        return new MinoritySupportBase.ClassMgr();
    }

   /**
   * Constructs a MinoritySupportBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MinoritySupport
   */
    public MinoritySupportBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
