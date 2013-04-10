package org.semanticwb.social.base;


   /**
   * Clase padre de todas las acciones posibles en swbsocial 
   */
public abstract class ActionBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Clase padre de todas las acciones posibles en swbsocial
   */
    public static final org.semanticwb.platform.SemanticClass social_Action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Action");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Action");

    public static class ClassMgr
    {
       /**
       * Returns a list of Action for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Action>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Action for all models
       * @return Iterator of org.semanticwb.social.Action
       */

        public static java.util.Iterator<org.semanticwb.social.Action> listActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Action>(it, true);
        }

        public static org.semanticwb.social.Action createAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Action.ClassMgr.createAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Action
       * @param id Identifier for org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.Action
       * @return A org.semanticwb.social.Action
       */
        public static org.semanticwb.social.Action getAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Action)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Action
       * @param id Identifier for org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.Action
       * @return A org.semanticwb.social.Action
       */
        public static org.semanticwb.social.Action createAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Action)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Action
       * @param id Identifier for org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.Action
       */
        public static void removeAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Action
       * @param id Identifier for org.semanticwb.social.Action
       * @param model Model of the org.semanticwb.social.Action
       * @return true if the org.semanticwb.social.Action exists, false otherwise
       */

        public static boolean hasAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAction(id, model)!=null);
        }
    }

    public static ActionBase.ClassMgr getActionClassMgr()
    {
        return new ActionBase.ClassMgr();
    }

   /**
   * Constructs a ActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Action
   */
    public ActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
