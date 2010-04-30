package org.semanticwb.process.model.base;


public abstract class EscalationBase extends org.semanticwb.process.model.BPMNElement 
{
    public static final org.semanticwb.platform.SemanticClass swp_Escalation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Escalation");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Escalation");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Escalation> listEscalations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Escalation>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Escalation> listEscalations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Escalation>(it, true);
        }

        public static org.semanticwb.process.model.Escalation getEscalation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Escalation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Escalation createEscalation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Escalation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeEscalation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasEscalation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEscalation(id, model)!=null);
        }
    }

    public EscalationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
