package org.semanticwb.social.base;


public abstract class ColaborationBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass social_Colaboration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Colaboration");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Colaboration");

    public static class ClassMgr
    {
       /**
       * Returns a list of Colaboration for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Colaboration
       */

        public static java.util.Iterator<org.semanticwb.social.Colaboration> listColaborations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Colaboration>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Colaboration for all models
       * @return Iterator of org.semanticwb.social.Colaboration
       */

        public static java.util.Iterator<org.semanticwb.social.Colaboration> listColaborations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Colaboration>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Colaboration
       * @param id Identifier for org.semanticwb.social.Colaboration
       * @param model Model of the org.semanticwb.social.Colaboration
       * @return A org.semanticwb.social.Colaboration
       */
        public static org.semanticwb.social.Colaboration getColaboration(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Colaboration)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Colaboration
       * @param id Identifier for org.semanticwb.social.Colaboration
       * @param model Model of the org.semanticwb.social.Colaboration
       * @return A org.semanticwb.social.Colaboration
       */
        public static org.semanticwb.social.Colaboration createColaboration(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Colaboration)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Colaboration
       * @param id Identifier for org.semanticwb.social.Colaboration
       * @param model Model of the org.semanticwb.social.Colaboration
       */
        public static void removeColaboration(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Colaboration
       * @param id Identifier for org.semanticwb.social.Colaboration
       * @param model Model of the org.semanticwb.social.Colaboration
       * @return true if the org.semanticwb.social.Colaboration exists, false otherwise
       */

        public static boolean hasColaboration(String id, org.semanticwb.model.SWBModel model)
        {
            return (getColaboration(id, model)!=null);
        }
    }

    public static ColaborationBase.ClassMgr getColaborationClassMgr()
    {
        return new ColaborationBase.ClassMgr();
    }

   /**
   * Constructs a ColaborationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Colaboration
   */
    public ColaborationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
