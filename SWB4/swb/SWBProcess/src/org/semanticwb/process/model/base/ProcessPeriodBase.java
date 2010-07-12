package org.semanticwb.process.model.base;


public abstract class ProcessPeriodBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Filterable,org.semanticwb.model.XMLable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriod");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriod");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriods(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriods()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod>(it, true);
        }

        public static org.semanticwb.process.model.ProcessPeriod createProcessPeriod(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessPeriod.ClassMgr.createProcessPeriod(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ProcessPeriod getProcessPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessPeriod)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ProcessPeriod createProcessPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessPeriod)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcessPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcessPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessPeriod(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProcessPeriodBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
