package org.semanticwb.social.base;


   /**
   * Clase que agrupa acciónes que contienen un mesaje a escribir en cada una de las instancias 
   */
public abstract class ActionMsgBase extends org.semanticwb.social.Action 
{
   /**
   * Mensaje
   */
    public static final org.semanticwb.platform.SemanticProperty social_actionMsg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#actionMsg");
   /**
   * Clase que agrupa acciónes que contienen un mesaje a escribir en cada una de las instancias
   */
    public static final org.semanticwb.platform.SemanticClass social_ActionMsg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#ActionMsg");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#ActionMsg");

    public static class ClassMgr
    {
       /**
       * Returns a list of ActionMsg for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.ActionMsg
       */

        public static java.util.Iterator<org.semanticwb.social.ActionMsg> listActionMsgs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.ActionMsg>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.ActionMsg for all models
       * @return Iterator of org.semanticwb.social.ActionMsg
       */

        public static java.util.Iterator<org.semanticwb.social.ActionMsg> listActionMsgs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.ActionMsg>(it, true);
        }

        public static org.semanticwb.social.ActionMsg createActionMsg(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.ActionMsg.ClassMgr.createActionMsg(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.ActionMsg
       * @param id Identifier for org.semanticwb.social.ActionMsg
       * @param model Model of the org.semanticwb.social.ActionMsg
       * @return A org.semanticwb.social.ActionMsg
       */
        public static org.semanticwb.social.ActionMsg getActionMsg(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.ActionMsg)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.ActionMsg
       * @param id Identifier for org.semanticwb.social.ActionMsg
       * @param model Model of the org.semanticwb.social.ActionMsg
       * @return A org.semanticwb.social.ActionMsg
       */
        public static org.semanticwb.social.ActionMsg createActionMsg(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.ActionMsg)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.ActionMsg
       * @param id Identifier for org.semanticwb.social.ActionMsg
       * @param model Model of the org.semanticwb.social.ActionMsg
       */
        public static void removeActionMsg(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.ActionMsg
       * @param id Identifier for org.semanticwb.social.ActionMsg
       * @param model Model of the org.semanticwb.social.ActionMsg
       * @return true if the org.semanticwb.social.ActionMsg exists, false otherwise
       */

        public static boolean hasActionMsg(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActionMsg(id, model)!=null);
        }
    }

    public static ActionMsgBase.ClassMgr getActionMsgClassMgr()
    {
        return new ActionMsgBase.ClassMgr();
    }

   /**
   * Constructs a ActionMsgBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ActionMsg
   */
    public ActionMsgBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ActionMsg property
* @return String with the ActionMsg
*/
    public String getActionMsg()
    {
        return getSemanticObject().getProperty(social_actionMsg);
    }

/**
* Sets the ActionMsg property
* @param value long with the ActionMsg
*/
    public void setActionMsg(String value)
    {
        getSemanticObject().setProperty(social_actionMsg, value);
    }
}
