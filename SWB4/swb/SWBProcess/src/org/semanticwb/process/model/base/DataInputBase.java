package org.semanticwb.process.model.base;


public abstract class DataInputBase extends org.semanticwb.process.model.DataObject implements org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.InformationAssociable,org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Monitorable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_InputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputSet");
    public static final org.semanticwb.platform.SemanticProperty swp_hasInputSetWithWhileExecuting=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasInputSetWithWhileExecuting");
    public static final org.semanticwb.platform.SemanticProperty swp_hasInputSetWithOptional=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasInputSetWithOptional");
    public static final org.semanticwb.platform.SemanticClass swp_DataInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInput");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInput");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(it, true);
        }

        public static org.semanticwb.process.model.DataInput createDataInput(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataInput.ClassMgr.createDataInput(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataInput getDataInput(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataInput)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataInput createDataInput(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataInput)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataInput(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataInput(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataInput(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByInputSetWithWhileExecuting(org.semanticwb.process.model.InputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputSetWithWhileExecuting, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByInputSetWithWhileExecuting(org.semanticwb.process.model.InputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputSetWithWhileExecuting,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByInputSetWithOptional(org.semanticwb.process.model.InputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputSetWithOptional, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByInputSetWithOptional(org.semanticwb.process.model.InputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputSetWithOptional,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByItemSubjectRef(org.semanticwb.process.model.ItemDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_itemSubjectRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByItemSubjectRef(org.semanticwb.process.model.ItemDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_itemSubjectRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataInputBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> listInputSetWithWhileExecutings()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet>(getSemanticObject().listObjectProperties(swp_hasInputSetWithWhileExecuting));
    }

    public boolean hasInputSetWithWhileExecuting(org.semanticwb.process.model.InputSet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasInputSetWithWhileExecuting,value.getSemanticObject());
        }
        return ret;
    }

    public void addInputSetWithWhileExecuting(org.semanticwb.process.model.InputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasInputSetWithWhileExecuting, value.getSemanticObject());
    }

    public void removeAllInputSetWithWhileExecuting()
    {
        getSemanticObject().removeProperty(swp_hasInputSetWithWhileExecuting);
    }

    public void removeInputSetWithWhileExecuting(org.semanticwb.process.model.InputSet value)
    {
        getSemanticObject().removeObjectProperty(swp_hasInputSetWithWhileExecuting,value.getSemanticObject());
    }

    public org.semanticwb.process.model.InputSet getInputSetWithWhileExecuting()
    {
         org.semanticwb.process.model.InputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasInputSetWithWhileExecuting);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> listInputSetWithOptionals()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet>(getSemanticObject().listObjectProperties(swp_hasInputSetWithOptional));
    }

    public boolean hasInputSetWithOptional(org.semanticwb.process.model.InputSet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasInputSetWithOptional,value.getSemanticObject());
        }
        return ret;
    }

    public void addInputSetWithOptional(org.semanticwb.process.model.InputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasInputSetWithOptional, value.getSemanticObject());
    }

    public void removeAllInputSetWithOptional()
    {
        getSemanticObject().removeProperty(swp_hasInputSetWithOptional);
    }

    public void removeInputSetWithOptional(org.semanticwb.process.model.InputSet value)
    {
        getSemanticObject().removeObjectProperty(swp_hasInputSetWithOptional,value.getSemanticObject());
    }

    public org.semanticwb.process.model.InputSet getInputSetWithOptional()
    {
         org.semanticwb.process.model.InputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasInputSetWithOptional);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
