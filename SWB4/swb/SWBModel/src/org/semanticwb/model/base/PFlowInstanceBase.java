package org.semanticwb.model.base;


public class PFlowInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.PFlowable
{
    public static final org.semanticwb.platform.SemanticProperty swb_pfiTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiTime");
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiPFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStatus");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStep");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");

    public PFlowInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.PFlowInstance getPFlowInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(org.semanticwb.model.PFlowInstance.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(org.semanticwb.model.PFlowInstance.class, it, true);
    }

    public static org.semanticwb.model.PFlowInstance createPFlowInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.model.PFlowInstance createPFlowInstance(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.model.PFlowInstance.createPFlowInstance(String.valueOf(id), model);
    }

    public static void removePFlowInstance(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPFlowInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPFlowInstance(id, model)!=null);
    }

    public java.util.Date getTime()
    {
        return getSemanticObject().getDateProperty(swb_pfiTime);
    }

    public void setTime(java.util.Date pfiTime)
    {
        getSemanticObject().setDateProperty(swb_pfiTime, pfiTime);
    }

    public void setPflow(org.semanticwb.model.PFlow pflow)
    {
        getSemanticObject().setObjectProperty(swb_pfiPFlow, pflow.getSemanticObject());
    }

    public void removePflow()
    {
        getSemanticObject().removeProperty(swb_pfiPFlow);
    }

    public org.semanticwb.model.PFlow getPflow()
    {
         org.semanticwb.model.PFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pfiPFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlow)obj.createGenericInstance();
         }
         return ret;
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swb_pfiStatus);
    }

    public void setStatus(int pfiStatus)
    {
        getSemanticObject().setLongProperty(swb_pfiStatus, pfiStatus);
    }

    public String getStep()
    {
        return getSemanticObject().getProperty(swb_pfiStep);
    }

    public void setStep(String pfiStep)
    {
        getSemanticObject().setProperty(swb_pfiStep, pfiStep);
    }
}
