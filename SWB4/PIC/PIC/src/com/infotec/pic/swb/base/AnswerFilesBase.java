package com.infotec.pic.swb.base;


public abstract class AnswerFilesBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass pic_AnswerFiles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#AnswerFiles");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#AnswerFiles");

    public static class ClassMgr
    {
       /**
       * Returns a list of AnswerFiles for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.AnswerFiles
       */

        public static java.util.Iterator<com.infotec.pic.swb.AnswerFiles> listAnswerFileses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.AnswerFiles>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.AnswerFiles for all models
       * @return Iterator of com.infotec.pic.swb.AnswerFiles
       */

        public static java.util.Iterator<com.infotec.pic.swb.AnswerFiles> listAnswerFileses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.AnswerFiles>(it, true);
        }
       /**
       * Gets a com.infotec.pic.swb.AnswerFiles
       * @param id Identifier for com.infotec.pic.swb.AnswerFiles
       * @param model Model of the com.infotec.pic.swb.AnswerFiles
       * @return A com.infotec.pic.swb.AnswerFiles
       */
        public static com.infotec.pic.swb.AnswerFiles getAnswerFiles(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.AnswerFiles)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.AnswerFiles
       * @param id Identifier for com.infotec.pic.swb.AnswerFiles
       * @param model Model of the com.infotec.pic.swb.AnswerFiles
       * @return A com.infotec.pic.swb.AnswerFiles
       */
        public static com.infotec.pic.swb.AnswerFiles createAnswerFiles(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.AnswerFiles)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.AnswerFiles
       * @param id Identifier for com.infotec.pic.swb.AnswerFiles
       * @param model Model of the com.infotec.pic.swb.AnswerFiles
       */
        public static void removeAnswerFiles(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.AnswerFiles
       * @param id Identifier for com.infotec.pic.swb.AnswerFiles
       * @param model Model of the com.infotec.pic.swb.AnswerFiles
       * @return true if the com.infotec.pic.swb.AnswerFiles exists, false otherwise
       */

        public static boolean hasAnswerFiles(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAnswerFiles(id, model)!=null);
        }
    }

    public static AnswerFilesBase.ClassMgr getAnswerFilesClassMgr()
    {
        return new AnswerFilesBase.ClassMgr();
    }

   /**
   * Constructs a AnswerFilesBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AnswerFiles
   */
    public AnswerFilesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
