package org.semanticwb.social.base;


   /**
   * Acción específica mediante la cual se marca un mensaje como prioritario. Esto en la propiedad "IsPrioritary" de un mensaje (Post). 
   */
public abstract class MarkMsgAsPrioritaryBase extends org.semanticwb.social.Action 
{
   /**
   * Acción específica mediante la cual se marca un mensaje como prioritario. Esto en la propiedad "IsPrioritary" de un mensaje (Post).
   */
    public static final org.semanticwb.platform.SemanticClass social_MarkMsgAsPrioritary=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#MarkMsgAsPrioritary");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#MarkMsgAsPrioritary");

    public static class ClassMgr
    {
       /**
       * Returns a list of MarkMsgAsPrioritary for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.MarkMsgAsPrioritary
       */

        public static java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaries(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.MarkMsgAsPrioritary>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.MarkMsgAsPrioritary for all models
       * @return Iterator of org.semanticwb.social.MarkMsgAsPrioritary
       */

        public static java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaries()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.MarkMsgAsPrioritary>(it, true);
        }

        public static org.semanticwb.social.MarkMsgAsPrioritary createMarkMsgAsPrioritary(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.MarkMsgAsPrioritary.ClassMgr.createMarkMsgAsPrioritary(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.MarkMsgAsPrioritary
       * @param id Identifier for org.semanticwb.social.MarkMsgAsPrioritary
       * @param model Model of the org.semanticwb.social.MarkMsgAsPrioritary
       * @return A org.semanticwb.social.MarkMsgAsPrioritary
       */
        public static org.semanticwb.social.MarkMsgAsPrioritary getMarkMsgAsPrioritary(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.MarkMsgAsPrioritary)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.MarkMsgAsPrioritary
       * @param id Identifier for org.semanticwb.social.MarkMsgAsPrioritary
       * @param model Model of the org.semanticwb.social.MarkMsgAsPrioritary
       * @return A org.semanticwb.social.MarkMsgAsPrioritary
       */
        public static org.semanticwb.social.MarkMsgAsPrioritary createMarkMsgAsPrioritary(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.MarkMsgAsPrioritary)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.MarkMsgAsPrioritary
       * @param id Identifier for org.semanticwb.social.MarkMsgAsPrioritary
       * @param model Model of the org.semanticwb.social.MarkMsgAsPrioritary
       */
        public static void removeMarkMsgAsPrioritary(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.MarkMsgAsPrioritary
       * @param id Identifier for org.semanticwb.social.MarkMsgAsPrioritary
       * @param model Model of the org.semanticwb.social.MarkMsgAsPrioritary
       * @return true if the org.semanticwb.social.MarkMsgAsPrioritary exists, false otherwise
       */

        public static boolean hasMarkMsgAsPrioritary(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMarkMsgAsPrioritary(id, model)!=null);
        }
    }

    public static MarkMsgAsPrioritaryBase.ClassMgr getMarkMsgAsPrioritaryClassMgr()
    {
        return new MarkMsgAsPrioritaryBase.ClassMgr();
    }

   /**
   * Constructs a MarkMsgAsPrioritaryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MarkMsgAsPrioritary
   */
    public MarkMsgAsPrioritaryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
