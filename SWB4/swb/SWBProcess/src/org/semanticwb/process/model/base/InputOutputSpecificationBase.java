package org.semanticwb.process.model.base;


public abstract class InputOutputSpecificationBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.OutputSetReferensable,org.semanticwb.process.model.Documentable,org.semanticwb.process.model.DataOutputReferensable,org.semanticwb.process.model.InputSetReferensable,org.semanticwb.process.model.DataInputReferensable
{
    public static final org.semanticwb.platform.SemanticClass swp_InputOutputSpecification=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputOutputSpecification");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputOutputSpecification");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecifications(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecifications()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification>(it, true);
        }

        public static org.semanticwb.process.model.InputOutputSpecification createInputOutputSpecification(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.InputOutputSpecification.ClassMgr.createInputOutputSpecification(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.InputOutputSpecification getInputOutputSpecification(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InputOutputSpecification)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.InputOutputSpecification createInputOutputSpecification(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InputOutputSpecification)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeInputOutputSpecification(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasInputOutputSpecification(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInputOutputSpecification(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByInputSetRef(org.semanticwb.process.model.InputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputSetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByInputSetRef(org.semanticwb.process.model.InputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputSetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByDataOutputRef(org.semanticwb.process.model.DataOutput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByDataOutputRef(org.semanticwb.process.model.DataOutput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataOutputRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByOutputSetRef(org.semanticwb.process.model.OutputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputSetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByOutputSetRef(org.semanticwb.process.model.OutputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputSetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByDataInputRef(org.semanticwb.process.model.DataInput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByDataInputRef(org.semanticwb.process.model.DataInput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecificationByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputSpecification> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public InputOutputSpecificationBase(org.semanticwb.platform.SemanticObject base)
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> listOutputSetRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(getSemanticObject().listObjectProperties(swp_hasOutputSetRef));
    }

    public boolean hasOutputSetRef(org.semanticwb.process.model.OutputSet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOutputSetRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addOutputSetRef(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasOutputSetRef, value.getSemanticObject());
    }

    public void removeAllOutputSetRef()
    {
        getSemanticObject().removeProperty(swp_hasOutputSetRef);
    }

    public void removeOutputSetRef(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOutputSetRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.OutputSet getOutputSetRef()
    {
         org.semanticwb.process.model.OutputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOutputSetRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.OutputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> listDataInputRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(getSemanticObject().listObjectProperties(swp_hasDataInputRef));
    }

    public boolean hasDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDataInputRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().addObjectProperty(swp_hasDataInputRef, value.getSemanticObject());
    }

    public void removeAllDataInputRef()
    {
        getSemanticObject().removeProperty(swp_hasDataInputRef);
    }

    public void removeDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDataInputRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataInput getDataInputRef()
    {
         org.semanticwb.process.model.DataInput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDataInputRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataInput)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
