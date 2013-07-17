package com.infotec.pic.swb.base;


public abstract class ForeignBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass pic_Foreign=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Foreign");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Foreign");

    public static class ClassMgr
    {
       /**
       * Returns a list of Foreign for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Foreign
       */

        public static java.util.Iterator<com.infotec.pic.swb.Foreign> listForeigns(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Foreign>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Foreign for all models
       * @return Iterator of com.infotec.pic.swb.Foreign
       */

        public static java.util.Iterator<com.infotec.pic.swb.Foreign> listForeigns()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Foreign>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.Foreign
       * @param id Identifier for com.infotec.pic.swb.Foreign
       * @param model Model of the com.infotec.pic.swb.Foreign
       * @return A com.infotec.pic.swb.Foreign
       */
        public static com.infotec.pic.swb.Foreign getForeign(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Foreign)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Foreign
       * @param id Identifier for com.infotec.pic.swb.Foreign
       * @param model Model of the com.infotec.pic.swb.Foreign
       * @return A com.infotec.pic.swb.Foreign
       */
        public static com.infotec.pic.swb.Foreign createForeign(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Foreign)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Foreign
       * @param id Identifier for com.infotec.pic.swb.Foreign
       * @param model Model of the com.infotec.pic.swb.Foreign
       */
        public static void removeForeign(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Foreign
       * @param id Identifier for com.infotec.pic.swb.Foreign
       * @param model Model of the com.infotec.pic.swb.Foreign
       * @return true if the com.infotec.pic.swb.Foreign exists, false otherwise
       */

        public static boolean hasForeign(String id, org.semanticwb.model.SWBModel model)
        {
            return (getForeign(id, model)!=null);
        }
    }

    public static ForeignBase.ClassMgr getForeignClassMgr()
    {
        return new ForeignBase.ClassMgr();
    }

   /**
   * Constructs a ForeignBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Foreign
   */
    public ForeignBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
