package org.semanticwb.process.model.base;


public abstract class ProcessPeriodRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriod");
    public static final org.semanticwb.platform.SemanticProperty swp_processPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processPeriod");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriodRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriodRef");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriodRef> listProcessPeriodRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriodRef> listProcessPeriodRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef>(it, true);
        }

        public static org.semanticwb.process.model.ProcessPeriodRef createProcessPeriodRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessPeriodRef.ClassMgr.createProcessPeriodRef(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ProcessPeriodRef getProcessPeriodRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessPeriodRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ProcessPeriodRef createProcessPeriodRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessPeriodRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcessPeriodRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcessPeriodRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessPeriodRef(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriodRef> listProcessPeriodRefByProcessPeriod(org.semanticwb.process.model.ProcessPeriod value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processPeriod, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriodRef> listProcessPeriodRefByProcessPeriod(org.semanticwb.process.model.ProcessPeriod value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processPeriod,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProcessPeriodRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setProcessPeriod(org.semanticwb.process.model.ProcessPeriod value)
    {
        getSemanticObject().setObjectProperty(swp_processPeriod, value.getSemanticObject());
    }

    public void removeProcessPeriod()
    {
        getSemanticObject().removeProperty(swp_processPeriod);
    }

    public org.semanticwb.process.model.ProcessPeriod getProcessPeriod()
    {
         org.semanticwb.process.model.ProcessPeriod ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processPeriod);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessPeriod)obj.createGenericInstance();
         }
         return ret;
    }
}
