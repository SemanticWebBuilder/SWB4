package org.semanticwb.process.model.base;


public abstract class ComplexGatewayInstanceBase extends org.semanticwb.process.model.BPMNInstance 
{
    public static final org.semanticwb.platform.SemanticProperty swp_activationCount=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#activationCount");
    public static final org.semanticwb.platform.SemanticProperty swp_waitingForStart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#waitingForStart");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexGatewayInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexGatewayInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexGatewayInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGatewayInstance> listComplexGatewayInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGatewayInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexGatewayInstance> listComplexGatewayInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGatewayInstance>(it, true);
        }

        public static org.semanticwb.process.model.ComplexGatewayInstance getComplexGatewayInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ComplexGatewayInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ComplexGatewayInstance createComplexGatewayInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ComplexGatewayInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeComplexGatewayInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasComplexGatewayInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getComplexGatewayInstance(id, model)!=null);
        }
    }

    public ComplexGatewayInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getActivationCount()
    {
        return getSemanticObject().getIntProperty(swp_activationCount);
    }

    public void setActivationCount(int value)
    {
        getSemanticObject().setIntProperty(swp_activationCount, value);
    }

    public boolean isWaitingForStart()
    {
        return getSemanticObject().getBooleanProperty(swp_waitingForStart);
    }

    public void setWaitingForStart(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_waitingForStart, value);
    }
}
