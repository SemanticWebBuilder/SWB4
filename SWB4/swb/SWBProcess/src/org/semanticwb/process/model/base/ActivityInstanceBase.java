package org.semanticwb.process.model.base;


public abstract class ActivityInstanceBase extends org.semanticwb.process.model.BPMNInstance 
{
    public static final org.semanticwb.platform.SemanticProperty swp_activityState=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#activityState");
    public static final org.semanticwb.platform.SemanticClass swp_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ActivityInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ActivityInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ActivityInstance> listActivityInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityInstance> listActivityInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityInstance>(it, true);
        }

        public static org.semanticwb.process.model.ActivityInstance getActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ActivityInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ActivityInstance createActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ActivityInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasActivityInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivityInstance(id, model)!=null);
        }
    }

    public ActivityInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getActivityState()
    {
        return getSemanticObject().getProperty(swp_activityState);
    }

    public void setActivityState(String value)
    {
        getSemanticObject().setProperty(swp_activityState, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
