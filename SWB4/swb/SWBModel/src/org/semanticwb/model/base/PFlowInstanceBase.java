package org.semanticwb.model.base;


public abstract class PFlowInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.PFlowable
{
    public static final org.semanticwb.platform.SemanticProperty swb_pfiTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiTime");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_pfinstResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfinstResource");
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiPFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStatus");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiVersion");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStep");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(it, true);
        }

        public static org.semanticwb.model.PFlowInstance createPFlowInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(String.valueOf(id), model);
        }

        public static org.semanticwb.model.PFlowInstance getPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.PFlowInstance createPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removePFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPFlowInstance(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPfinstResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_pfinstResource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPfinstResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_pfinstResource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPflow(org.semanticwb.model.PFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_pfiPFlow, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPflow(org.semanticwb.model.PFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_pfiPFlow,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public PFlowInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getTime()
    {
        return getSemanticObject().getDateProperty(swb_pfiTime);
    }

    public void setTime(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_pfiTime, value);
    }

    public void setPfinstResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_pfinstResource, value.getSemanticObject());
        }else
        {
            removePfinstResource();
        }
    }

    public void removePfinstResource()
    {
        getSemanticObject().removeProperty(swb_pfinstResource);
    }

    public org.semanticwb.model.Resource getPfinstResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pfinstResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    public void setPflow(org.semanticwb.model.PFlow value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_pfiPFlow, value.getSemanticObject());
        }else
        {
            removePflow();
        }
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

    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swb_pfiStatus, value);
    }

    public int getVersion()
    {
        return getSemanticObject().getIntProperty(swb_pfiVersion);
    }

    public void setVersion(int value)
    {
        getSemanticObject().setIntProperty(swb_pfiVersion, value);
    }

    public String getStep()
    {
        return getSemanticObject().getProperty(swb_pfiStep);
    }

    public void setStep(String value)
    {
        getSemanticObject().setProperty(swb_pfiStep, value);
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
