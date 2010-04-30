package org.semanticwb.process.model.base;


public abstract class UserTaskInstanceBase extends org.semanticwb.process.model.BPMNInstance 
{
    public static final org.semanticwb.platform.SemanticProperty swp_taskPriority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#taskPriority");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swp_actualOwner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#actualOwner");
    public static final org.semanticwb.platform.SemanticClass swp_UserTaskInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#UserTaskInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#UserTaskInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.UserTaskInstance> listUserTaskInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTaskInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.UserTaskInstance> listUserTaskInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTaskInstance>(it, true);
        }

        public static org.semanticwb.process.model.UserTaskInstance getUserTaskInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.UserTaskInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.UserTaskInstance createUserTaskInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.UserTaskInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeUserTaskInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasUserTaskInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserTaskInstance(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.UserTaskInstance> listUserTaskInstanceByActualOwner(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTaskInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_actualOwner, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.UserTaskInstance> listUserTaskInstanceByActualOwner(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.UserTaskInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_actualOwner,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public UserTaskInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getTaskPriority()
    {
        return getSemanticObject().getIntProperty(swp_taskPriority);
    }

    public void setTaskPriority(int value)
    {
        getSemanticObject().setIntProperty(swp_taskPriority, value);
    }

    public void setActualOwner(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swp_actualOwner, value.getSemanticObject());
    }

    public void removeActualOwner()
    {
        getSemanticObject().removeProperty(swp_actualOwner);
    }

    public org.semanticwb.model.User getActualOwner()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_actualOwner);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
