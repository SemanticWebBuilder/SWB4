package org.semanticwb.bsc.tracing.base;


   /**
   * Las acciones darán especial atención a los casos en que el o los controles se hayan determinado Deficientes o Inexistentes, y por tanto el Riesgo NO este Controlado Suficientemente. 
   */
public abstract class MitigationActionBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help,org.semanticwb.model.Roleable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_progressPercentage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#progressPercentage");
    public static final org.semanticwb.platform.SemanticProperty bsc_progressDescription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#progressDescription");
   /**
   * Las acciones darán especial atención a los casos en que el o los controles se hayan determinado Deficientes o Inexistentes, y por tanto el Riesgo NO este Controlado Suficientemente.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_MitigationAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MitigationAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#MitigationAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of MitigationAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.MitigationAction for all models
       * @return Iterator of org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction>(it, true);
        }

        public static org.semanticwb.bsc.tracing.MitigationAction createMitigationAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.MitigationAction.ClassMgr.createMitigationAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.MitigationAction
       * @param id Identifier for org.semanticwb.bsc.tracing.MitigationAction
       * @param model Model of the org.semanticwb.bsc.tracing.MitigationAction
       * @return A org.semanticwb.bsc.tracing.MitigationAction
       */
        public static org.semanticwb.bsc.tracing.MitigationAction getMitigationAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.MitigationAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.MitigationAction
       * @param id Identifier for org.semanticwb.bsc.tracing.MitigationAction
       * @param model Model of the org.semanticwb.bsc.tracing.MitigationAction
       * @return A org.semanticwb.bsc.tracing.MitigationAction
       */
        public static org.semanticwb.bsc.tracing.MitigationAction createMitigationAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.MitigationAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.MitigationAction
       * @param id Identifier for org.semanticwb.bsc.tracing.MitigationAction
       * @param model Model of the org.semanticwb.bsc.tracing.MitigationAction
       */
        public static void removeMitigationAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.MitigationAction
       * @param id Identifier for org.semanticwb.bsc.tracing.MitigationAction
       * @param model Model of the org.semanticwb.bsc.tracing.MitigationAction
       * @return true if the org.semanticwb.bsc.tracing.MitigationAction exists, false otherwise
       */

        public static boolean hasMitigationAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMitigationAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MitigationAction with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.MitigationAction
       * @return Iterator with all the org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MitigationAction with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MitigationAction with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.MitigationAction
       * @return Iterator with all the org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActionByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MitigationAction with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActionByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MitigationAction with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.MitigationAction
       * @return Iterator with all the org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MitigationAction with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MitigationAction with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.MitigationAction
       * @return Iterator with all the org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActionByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.MitigationAction with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.MitigationAction
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.MitigationAction> listMitigationActionByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.MitigationAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MitigationActionBase.ClassMgr getMitigationActionClassMgr()
    {
        return new MitigationActionBase.ClassMgr();
    }

   /**
   * Constructs a MitigationActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MitigationAction
   */
    public MitigationActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ProgressPercentage property
* @return String with the ProgressPercentage
*/
    public String getProgressPercentage()
    {
        return getSemanticObject().getProperty(bsc_progressPercentage);
    }

/**
* Sets the ProgressPercentage property
* @param value long with the ProgressPercentage
*/
    public void setProgressPercentage(String value)
    {
        getSemanticObject().setProperty(bsc_progressPercentage, value);
    }

/**
* Gets the ProgressDescription property
* @return String with the ProgressDescription
*/
    public String getProgressDescription()
    {
        return getSemanticObject().getProperty(bsc_progressDescription);
    }

/**
* Sets the ProgressDescription property
* @param value long with the ProgressDescription
*/
    public void setProgressDescription(String value)
    {
        getSemanticObject().setProperty(bsc_progressDescription, value);
    }
}
