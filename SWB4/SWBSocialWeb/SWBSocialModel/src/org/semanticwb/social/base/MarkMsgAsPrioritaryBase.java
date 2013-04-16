package org.semanticwb.social.base;


   /**
   * Acción específica mediante la cual se marca un mensaje como prioritario. Esto en la propiedad "IsPrioritary" de un mensaje (Post). 
   */
public abstract class MarkMsgAsPrioritaryBase extends org.semanticwb.social.Action implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
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
       /**
       * Gets all org.semanticwb.social.MarkMsgAsPrioritary with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.MarkMsgAsPrioritary
       * @return Iterator with all the org.semanticwb.social.MarkMsgAsPrioritary
       */

        public static java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaryByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MarkMsgAsPrioritary> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MarkMsgAsPrioritary with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.MarkMsgAsPrioritary
       */

        public static java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaryByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MarkMsgAsPrioritary> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MarkMsgAsPrioritary with a determined ActionRuleInv
       * @param value ActionRuleInv of the type org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.MarkMsgAsPrioritary
       * @return Iterator with all the org.semanticwb.social.MarkMsgAsPrioritary
       */

        public static java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaryByActionRuleInv(org.semanticwb.social.SocialRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MarkMsgAsPrioritary> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_actionRuleInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MarkMsgAsPrioritary with a determined ActionRuleInv
       * @param value ActionRuleInv of the type org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.MarkMsgAsPrioritary
       */

        public static java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaryByActionRuleInv(org.semanticwb.social.SocialRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MarkMsgAsPrioritary> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_actionRuleInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MarkMsgAsPrioritary with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.MarkMsgAsPrioritary
       * @return Iterator with all the org.semanticwb.social.MarkMsgAsPrioritary
       */

        public static java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaryByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MarkMsgAsPrioritary> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.MarkMsgAsPrioritary with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.MarkMsgAsPrioritary
       */

        public static java.util.Iterator<org.semanticwb.social.MarkMsgAsPrioritary> listMarkMsgAsPrioritaryByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.MarkMsgAsPrioritary> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
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

   /**
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
