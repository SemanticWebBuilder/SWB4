package com.infotec.pic.swb.base;


   /**
   * Giro comercial 
   */
public abstract class BusinessLineBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Giro comercial
   */
    public static final org.semanticwb.platform.SemanticClass pic_BusinessLine=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#BusinessLine");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#BusinessLine");

    public static class ClassMgr
    {
       /**
       * Returns a list of BusinessLine for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.BusinessLine
       */

        public static java.util.Iterator<com.infotec.pic.swb.BusinessLine> listBusinessLines(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.BusinessLine>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.BusinessLine for all models
       * @return Iterator of com.infotec.pic.swb.BusinessLine
       */

        public static java.util.Iterator<com.infotec.pic.swb.BusinessLine> listBusinessLines()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.BusinessLine>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.BusinessLine
       * @param id Identifier for com.infotec.pic.swb.BusinessLine
       * @param model Model of the com.infotec.pic.swb.BusinessLine
       * @return A com.infotec.pic.swb.BusinessLine
       */
        public static com.infotec.pic.swb.BusinessLine getBusinessLine(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.BusinessLine)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.BusinessLine
       * @param id Identifier for com.infotec.pic.swb.BusinessLine
       * @param model Model of the com.infotec.pic.swb.BusinessLine
       * @return A com.infotec.pic.swb.BusinessLine
       */
        public static com.infotec.pic.swb.BusinessLine createBusinessLine(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.BusinessLine)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.BusinessLine
       * @param id Identifier for com.infotec.pic.swb.BusinessLine
       * @param model Model of the com.infotec.pic.swb.BusinessLine
       */
        public static void removeBusinessLine(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.BusinessLine
       * @param id Identifier for com.infotec.pic.swb.BusinessLine
       * @param model Model of the com.infotec.pic.swb.BusinessLine
       * @return true if the com.infotec.pic.swb.BusinessLine exists, false otherwise
       */

        public static boolean hasBusinessLine(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBusinessLine(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.BusinessLine with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.BusinessLine
       * @return Iterator with all the com.infotec.pic.swb.BusinessLine
       */

        public static java.util.Iterator<com.infotec.pic.swb.BusinessLine> listBusinessLineByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.BusinessLine> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.BusinessLine with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.BusinessLine
       */

        public static java.util.Iterator<com.infotec.pic.swb.BusinessLine> listBusinessLineByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.BusinessLine> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.BusinessLine with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.BusinessLine
       * @return Iterator with all the com.infotec.pic.swb.BusinessLine
       */

        public static java.util.Iterator<com.infotec.pic.swb.BusinessLine> listBusinessLineByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.BusinessLine> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.BusinessLine with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.BusinessLine
       */

        public static java.util.Iterator<com.infotec.pic.swb.BusinessLine> listBusinessLineByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.BusinessLine> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static BusinessLineBase.ClassMgr getBusinessLineClassMgr()
    {
        return new BusinessLineBase.ClassMgr();
    }

   /**
   * Constructs a BusinessLineBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BusinessLine
   */
    public BusinessLineBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
