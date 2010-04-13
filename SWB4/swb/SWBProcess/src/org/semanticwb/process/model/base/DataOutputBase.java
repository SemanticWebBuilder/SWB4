package org.semanticwb.process.model.base;


public abstract class DataOutputBase extends org.semanticwb.process.model.DataObject implements org.semanticwb.process.model.Auditable,org.semanticwb.process.model.InformationAssociable,org.semanticwb.process.model.Monitorable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_OutputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOutputSetWithWhileExecuting=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOutputSetWithWhileExecuting");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOutputSetWithOptional=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOutputSetWithOptional");
    public static final org.semanticwb.platform.SemanticClass swp_DataOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutput");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutput");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput>(it, true);
        }

        public static org.semanticwb.process.model.DataOutput createDataOutput(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataOutput.ClassMgr.createDataOutput(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataOutput getDataOutput(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataOutput)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataOutput createDataOutput(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataOutput)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataOutput(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataOutput(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataOutput(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByOutputSetWithWhileExecuting(org.semanticwb.process.model.OutputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputSetWithWhileExecuting, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByOutputSetWithWhileExecuting(org.semanticwb.process.model.OutputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputSetWithWhileExecuting,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByOutputSetWithOptional(org.semanticwb.process.model.OutputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputSetWithOptional, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByOutputSetWithOptional(org.semanticwb.process.model.OutputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputSetWithOptional,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByItemSubjectRef(org.semanticwb.process.model.ItemDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_itemSubjectRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByItemSubjectRef(org.semanticwb.process.model.ItemDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_itemSubjectRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataOutputBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> listOutputSetWithWhileExecutings()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(getSemanticObject().listObjectProperties(swp_hasOutputSetWithWhileExecuting));
    }

    public boolean hasOutputSetWithWhileExecuting(org.semanticwb.process.model.OutputSet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOutputSetWithWhileExecuting,value.getSemanticObject());
        }
        return ret;
    }

    public void addOutputSetWithWhileExecuting(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasOutputSetWithWhileExecuting, value.getSemanticObject());
    }

    public void removeAllOutputSetWithWhileExecuting()
    {
        getSemanticObject().removeProperty(swp_hasOutputSetWithWhileExecuting);
    }

    public void removeOutputSetWithWhileExecuting(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOutputSetWithWhileExecuting,value.getSemanticObject());
    }

    public org.semanticwb.process.model.OutputSet getOutputSetWithWhileExecuting()
    {
         org.semanticwb.process.model.OutputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOutputSetWithWhileExecuting);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.OutputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> listOutputSetWithOptionals()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(getSemanticObject().listObjectProperties(swp_hasOutputSetWithOptional));
    }

    public boolean hasOutputSetWithOptional(org.semanticwb.process.model.OutputSet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOutputSetWithOptional,value.getSemanticObject());
        }
        return ret;
    }

    public void addOutputSetWithOptional(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasOutputSetWithOptional, value.getSemanticObject());
    }

    public void removeAllOutputSetWithOptional()
    {
        getSemanticObject().removeProperty(swp_hasOutputSetWithOptional);
    }

    public void removeOutputSetWithOptional(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOutputSetWithOptional,value.getSemanticObject());
    }

    public org.semanticwb.process.model.OutputSet getOutputSetWithOptional()
    {
         org.semanticwb.process.model.OutputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOutputSetWithOptional);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.OutputSet)obj.createGenericInstance();
         }
         return ret;
    }
}
