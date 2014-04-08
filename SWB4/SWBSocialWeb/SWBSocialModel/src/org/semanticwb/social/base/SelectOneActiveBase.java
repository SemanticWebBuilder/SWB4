package org.semanticwb.social.base;


   /**
   * social:SelectOneActive 
   */
public abstract class SelectOneActiveBase extends org.semanticwb.model.SelectOne 
{
   /**
   * social:SelectOneActive
   */
    public static final org.semanticwb.platform.SemanticClass social_SelectOneActive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SelectOneActive");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SelectOneActive");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectOneActive for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SelectOneActive
       */

        public static java.util.Iterator<org.semanticwb.social.SelectOneActive> listSelectOneActives(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SelectOneActive>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SelectOneActive for all models
       * @return Iterator of org.semanticwb.social.SelectOneActive
       */

        public static java.util.Iterator<org.semanticwb.social.SelectOneActive> listSelectOneActives()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SelectOneActive>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SelectOneActive
       * @param id Identifier for org.semanticwb.social.SelectOneActive
       * @param model Model of the org.semanticwb.social.SelectOneActive
       * @return A org.semanticwb.social.SelectOneActive
       */
        public static org.semanticwb.social.SelectOneActive getSelectOneActive(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SelectOneActive)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SelectOneActive
       * @param id Identifier for org.semanticwb.social.SelectOneActive
       * @param model Model of the org.semanticwb.social.SelectOneActive
       * @return A org.semanticwb.social.SelectOneActive
       */
        public static org.semanticwb.social.SelectOneActive createSelectOneActive(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SelectOneActive)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SelectOneActive
       * @param id Identifier for org.semanticwb.social.SelectOneActive
       * @param model Model of the org.semanticwb.social.SelectOneActive
       */
        public static void removeSelectOneActive(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SelectOneActive
       * @param id Identifier for org.semanticwb.social.SelectOneActive
       * @param model Model of the org.semanticwb.social.SelectOneActive
       * @return true if the org.semanticwb.social.SelectOneActive exists, false otherwise
       */

        public static boolean hasSelectOneActive(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectOneActive(id, model)!=null);
        }
    }

    public static SelectOneActiveBase.ClassMgr getSelectOneActiveClassMgr()
    {
        return new SelectOneActiveBase.ClassMgr();
    }

   /**
   * Constructs a SelectOneActiveBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectOneActive
   */
    public SelectOneActiveBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
