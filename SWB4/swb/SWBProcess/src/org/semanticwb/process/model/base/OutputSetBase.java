package org.semanticwb.process.model.base;


public abstract class OutputSetBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.DataOutputReferensable,org.semanticwb.process.model.InputSetReferensable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutput");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOptionalOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOptionalOutput");
    public static final org.semanticwb.platform.SemanticProperty swp_hasWhileExecutingOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasWhileExecutingOutput");
    public static final org.semanticwb.platform.SemanticClass swp_OutputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSets(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSets()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(it, true);
        }

        public static org.semanticwb.process.model.OutputSet createOutputSet(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.OutputSet.ClassMgr.createOutputSet(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.OutputSet getOutputSet(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.OutputSet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.OutputSet createOutputSet(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.OutputSet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeOutputSet(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasOutputSet(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOutputSet(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByInputSetRef(org.semanticwb.process.model.InputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputSetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByInputSetRef(org.semanticwb.process.model.InputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputSetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByDataOutputRef(org.semanticwb.process.model.DataOutput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByDataOutputRef(org.semanticwb.process.model.DataOutput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByOptionalOutput(org.semanticwb.process.model.DataOutput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOptionalOutput, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByOptionalOutput(org.semanticwb.process.model.DataOutput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOptionalOutput,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByWhileExecutingOutput(org.semanticwb.process.model.DataOutput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasWhileExecutingOutput, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByWhileExecutingOutput(org.semanticwb.process.model.DataOutput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasWhileExecutingOutput,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public OutputSetBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> listInputSetRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet>(getSemanticObject().listObjectProperties(swp_hasInputSetRef));
    }

    public boolean hasInputSetRef(org.semanticwb.process.model.InputSet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasInputSetRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addInputSetRef(org.semanticwb.process.model.InputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasInputSetRef, value.getSemanticObject());
    }

    public void removeAllInputSetRef()
    {
        getSemanticObject().removeProperty(swp_hasInputSetRef);
    }

    public void removeInputSetRef(org.semanticwb.process.model.InputSet value)
    {
        getSemanticObject().removeObjectProperty(swp_hasInputSetRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.InputSet getInputSetRef()
    {
         org.semanticwb.process.model.InputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasInputSetRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> listDataOutputRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput>(getSemanticObject().listObjectProperties(swp_hasDataOutputRef));
    }

    public boolean hasDataOutputRef(org.semanticwb.process.model.DataOutput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDataOutputRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addDataOutputRef(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().addObjectProperty(swp_hasDataOutputRef, value.getSemanticObject());
    }

    public void removeAllDataOutputRef()
    {
        getSemanticObject().removeProperty(swp_hasDataOutputRef);
    }

    public void removeDataOutputRef(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDataOutputRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataOutput getDataOutputRef()
    {
         org.semanticwb.process.model.DataOutput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDataOutputRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataOutput)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> listOptionalOutputs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput>(getSemanticObject().listObjectProperties(swp_hasOptionalOutput));
    }

    public boolean hasOptionalOutput(org.semanticwb.process.model.DataOutput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOptionalOutput,value.getSemanticObject());
        }
        return ret;
    }

    public void addOptionalOutput(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().addObjectProperty(swp_hasOptionalOutput, value.getSemanticObject());
    }

    public void removeAllOptionalOutput()
    {
        getSemanticObject().removeProperty(swp_hasOptionalOutput);
    }

    public void removeOptionalOutput(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOptionalOutput,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataOutput getOptionalOutput()
    {
         org.semanticwb.process.model.DataOutput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOptionalOutput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataOutput)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput> listWhileExecutingOutputs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataOutput>(getSemanticObject().listObjectProperties(swp_hasWhileExecutingOutput));
    }

    public boolean hasWhileExecutingOutput(org.semanticwb.process.model.DataOutput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasWhileExecutingOutput,value.getSemanticObject());
        }
        return ret;
    }

    public void addWhileExecutingOutput(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().addObjectProperty(swp_hasWhileExecutingOutput, value.getSemanticObject());
    }

    public void removeAllWhileExecutingOutput()
    {
        getSemanticObject().removeProperty(swp_hasWhileExecutingOutput);
    }

    public void removeWhileExecutingOutput(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasWhileExecutingOutput,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataOutput getWhileExecutingOutput()
    {
         org.semanticwb.process.model.DataOutput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasWhileExecutingOutput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataOutput)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
