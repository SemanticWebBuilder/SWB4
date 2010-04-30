package org.semanticwb.process.model.base;


public abstract class InputOutputBindingBase extends org.semanticwb.process.model.BPMNElement implements org.semanticwb.process.model.OperationReferensable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInput");
    public static final org.semanticwb.platform.SemanticProperty swp_inputDataRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#inputDataRef");
    public static final org.semanticwb.platform.SemanticClass swp_DataOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutput");
    public static final org.semanticwb.platform.SemanticProperty swp_outputDataRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#outputDataRef");
    public static final org.semanticwb.platform.SemanticClass swp_InputOutputBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputOutputBinding");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputOutputBinding");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindings(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindings()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding>(it, true);
        }

        public static org.semanticwb.process.model.InputOutputBinding createInputOutputBinding(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.InputOutputBinding.ClassMgr.createInputOutputBinding(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.InputOutputBinding getInputOutputBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InputOutputBinding)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.InputOutputBinding createInputOutputBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InputOutputBinding)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeInputOutputBinding(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasInputOutputBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInputOutputBinding(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindingByInputDataRef(org.semanticwb.process.model.DataInput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_inputDataRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindingByInputDataRef(org.semanticwb.process.model.DataInput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_inputDataRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindingByOperationRef(org.semanticwb.process.model.Operation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_operationRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindingByOperationRef(org.semanticwb.process.model.Operation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_operationRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindingByOutputDataRef(org.semanticwb.process.model.DataOutput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_outputDataRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindingByOutputDataRef(org.semanticwb.process.model.DataOutput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_outputDataRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public InputOutputBindingBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setInputDataRef(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().setObjectProperty(swp_inputDataRef, value.getSemanticObject());
    }

    public void removeInputDataRef()
    {
        getSemanticObject().removeProperty(swp_inputDataRef);
    }

    public org.semanticwb.process.model.DataInput getInputDataRef()
    {
         org.semanticwb.process.model.DataInput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_inputDataRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataInput)obj.createGenericInstance();
         }
         return ret;
    }

    public void setOperationRef(org.semanticwb.process.model.Operation value)
    {
        getSemanticObject().setObjectProperty(swp_operationRef, value.getSemanticObject());
    }

    public void removeOperationRef()
    {
        getSemanticObject().removeProperty(swp_operationRef);
    }

    public org.semanticwb.process.model.Operation getOperationRef()
    {
         org.semanticwb.process.model.Operation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_operationRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Operation)obj.createGenericInstance();
         }
         return ret;
    }

    public void setOutputDataRef(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().setObjectProperty(swp_outputDataRef, value.getSemanticObject());
    }

    public void removeOutputDataRef()
    {
        getSemanticObject().removeProperty(swp_outputDataRef);
    }

    public org.semanticwb.process.model.DataOutput getOutputDataRef()
    {
         org.semanticwb.process.model.DataOutput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outputDataRef);
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
