package org.semanticwb.process.model.base;


public abstract class ProcessBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Artifactable,org.semanticwb.process.model.FlowElementsContainerable,org.semanticwb.process.model.Propertyable,org.semanticwb.process.model.Callable,org.semanticwb.process.model.IOSpecificable,org.semanticwb.process.model.Auditable,org.semanticwb.process.model.Collapsable,org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Monitorable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Process");
    public static final org.semanticwb.platform.SemanticProperty swp_hasSupportedProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasSupportedProcess");
    public static final org.semanticwb.platform.SemanticProperty swp_processType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#processType");
    public static final org.semanticwb.platform.SemanticClass swp_LaneSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneSet");
    public static final org.semanticwb.platform.SemanticProperty swp_hasLaneSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasLaneSet");
    public static final org.semanticwb.platform.SemanticClass swp_Collaboration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Collaboration");
    public static final org.semanticwb.platform.SemanticProperty swp_definitionalCollaborationRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#definitionalCollaborationRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Process");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(it, true);
        }

        public static org.semanticwb.process.model.Process createProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Process.ClassMgr.createProcess(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Process getProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Process)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Process createProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Process)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcess(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByIOBinding(org.semanticwb.process.model.InputOutputBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByIOBinding(org.semanticwb.process.model.InputOutputBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasIOBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessBySupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedInterfaceRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessBySupportedProcess(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedProcess, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessBySupportedProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSupportedProcess,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ioSpecification,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByArtifact(org.semanticwb.process.model.Artifact value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByArtifact(org.semanticwb.process.model.Artifact value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasArtifact,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByLaneSet(org.semanticwb.process.model.LaneSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasLaneSet, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByLaneSet(org.semanticwb.process.model.LaneSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasLaneSet,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByAuditing(org.semanticwb.process.model.Auditing value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_auditing, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByAuditing(org.semanticwb.process.model.Auditing value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_auditing,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProperty(org.semanticwb.process.model.Property value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProperty, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProperty(org.semanticwb.process.model.Property value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProperty,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByDefinitionalCollaborationRef(org.semanticwb.process.model.Collaboration value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_definitionalCollaborationRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByDefinitionalCollaborationRef(org.semanticwb.process.model.Collaboration value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_definitionalCollaborationRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByFlowElement(org.semanticwb.process.model.FlowElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByFlowElement(org.semanticwb.process.model.FlowElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByMonitoring(org.semanticwb.process.model.Monitoring value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByMonitoring(org.semanticwb.process.model.Monitoring value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_monitoring,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding> listIOBindings()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputOutputBinding>(getSemanticObject().listObjectProperties(swp_hasIOBinding));
    }

    public boolean hasIOBinding(org.semanticwb.process.model.InputOutputBinding value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasIOBinding,value.getSemanticObject());
        }
        return ret;
    }

    public void addIOBinding(org.semanticwb.process.model.InputOutputBinding value)
    {
        getSemanticObject().addObjectProperty(swp_hasIOBinding, value.getSemanticObject());
    }

    public void removeAllIOBinding()
    {
        getSemanticObject().removeProperty(swp_hasIOBinding);
    }

    public void removeIOBinding(org.semanticwb.process.model.InputOutputBinding value)
    {
        getSemanticObject().removeObjectProperty(swp_hasIOBinding,value.getSemanticObject());
    }

    public org.semanticwb.process.model.InputOutputBinding getIOBinding()
    {
         org.semanticwb.process.model.InputOutputBinding ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasIOBinding);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputOutputBinding)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface> listSupportedInterfaceRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInterface>(getSemanticObject().listObjectProperties(swp_hasSupportedInterfaceRef));
    }

    public boolean hasSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasSupportedInterfaceRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        getSemanticObject().addObjectProperty(swp_hasSupportedInterfaceRef, value.getSemanticObject());
    }

    public void removeAllSupportedInterfaceRef()
    {
        getSemanticObject().removeProperty(swp_hasSupportedInterfaceRef);
    }

    public void removeSupportedInterfaceRef(org.semanticwb.process.model.ProcessInterface value)
    {
        getSemanticObject().removeObjectProperty(swp_hasSupportedInterfaceRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ProcessInterface getSupportedInterfaceRef()
    {
         org.semanticwb.process.model.ProcessInterface ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasSupportedInterfaceRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessInterface)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> listSupportedProcesses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(getSemanticObject().listObjectProperties(swp_hasSupportedProcess));
    }

    public boolean hasSupportedProcess(org.semanticwb.process.model.Process value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasSupportedProcess,value.getSemanticObject());
        }
        return ret;
    }

    public void addSupportedProcess(org.semanticwb.process.model.Process value)
    {
        getSemanticObject().addObjectProperty(swp_hasSupportedProcess, value.getSemanticObject());
    }

    public void removeAllSupportedProcess()
    {
        getSemanticObject().removeProperty(swp_hasSupportedProcess);
    }

    public void removeSupportedProcess(org.semanticwb.process.model.Process value)
    {
        getSemanticObject().removeObjectProperty(swp_hasSupportedProcess,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Process getSupportedProcess()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasSupportedProcess);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public void setIoSpecification(org.semanticwb.process.model.InputOutputSpecification value)
    {
        getSemanticObject().setObjectProperty(swp_ioSpecification, value.getSemanticObject());
    }

    public void removeIoSpecification()
    {
        getSemanticObject().removeProperty(swp_ioSpecification);
    }

    public org.semanticwb.process.model.InputOutputSpecification getIoSpecification()
    {
         org.semanticwb.process.model.InputOutputSpecification ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_ioSpecification);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputOutputSpecification)obj.createGenericInstance();
         }
         return ret;
    }

    public String getProcessType()
    {
        return getSemanticObject().getProperty(swp_processType);
    }

    public void setProcessType(String value)
    {
        getSemanticObject().setProperty(swp_processType, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> listArtifacts()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact>(getSemanticObject().listObjectProperties(swp_hasArtifact));
    }

    public boolean hasArtifact(org.semanticwb.process.model.Artifact value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasArtifact,value.getSemanticObject());
        }
        return ret;
    }

    public void addArtifact(org.semanticwb.process.model.Artifact value)
    {
        getSemanticObject().addObjectProperty(swp_hasArtifact, value.getSemanticObject());
    }

    public void removeAllArtifact()
    {
        getSemanticObject().removeProperty(swp_hasArtifact);
    }

    public void removeArtifact(org.semanticwb.process.model.Artifact value)
    {
        getSemanticObject().removeObjectProperty(swp_hasArtifact,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Artifact getArtifact()
    {
         org.semanticwb.process.model.Artifact ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasArtifact);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Artifact)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet> listLaneSets()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneSet>(getSemanticObject().listObjectProperties(swp_hasLaneSet));
    }

    public boolean hasLaneSet(org.semanticwb.process.model.LaneSet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasLaneSet,value.getSemanticObject());
        }
        return ret;
    }

    public void addLaneSet(org.semanticwb.process.model.LaneSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasLaneSet, value.getSemanticObject());
    }

    public void removeAllLaneSet()
    {
        getSemanticObject().removeProperty(swp_hasLaneSet);
    }

    public void removeLaneSet(org.semanticwb.process.model.LaneSet value)
    {
        getSemanticObject().removeObjectProperty(swp_hasLaneSet,value.getSemanticObject());
    }

    public org.semanticwb.process.model.LaneSet getLaneSet()
    {
         org.semanticwb.process.model.LaneSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasLaneSet);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.LaneSet)obj.createGenericInstance();
         }
         return ret;
    }

    public void setAuditing(org.semanticwb.process.model.Auditing value)
    {
        getSemanticObject().setObjectProperty(swp_auditing, value.getSemanticObject());
    }

    public void removeAuditing()
    {
        getSemanticObject().removeProperty(swp_auditing);
    }

    public org.semanticwb.process.model.Auditing getAuditing()
    {
         org.semanticwb.process.model.Auditing ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_auditing);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Auditing)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> listProperties()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property>(getSemanticObject().listObjectProperties(swp_hasProperty));
    }

    public boolean hasProperty(org.semanticwb.process.model.Property value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProperty,value.getSemanticObject());
        }
        return ret;
    }

    public void addProperty(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().addObjectProperty(swp_hasProperty, value.getSemanticObject());
    }

    public void removeAllProperty()
    {
        getSemanticObject().removeProperty(swp_hasProperty);
    }

    public void removeProperty(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().removeObjectProperty(swp_hasProperty,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Property getProperty()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProperty);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }

    public void setDefinitionalCollaborationRef(org.semanticwb.process.model.Collaboration value)
    {
        getSemanticObject().setObjectProperty(swp_definitionalCollaborationRef, value.getSemanticObject());
    }

    public void removeDefinitionalCollaborationRef()
    {
        getSemanticObject().removeProperty(swp_definitionalCollaborationRef);
    }

    public org.semanticwb.process.model.Collaboration getDefinitionalCollaborationRef()
    {
         org.semanticwb.process.model.Collaboration ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_definitionalCollaborationRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Collaboration)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCalledElement(org.semanticwb.process.model.Callable value)
    {
        getSemanticObject().setObjectProperty(swp_calledElement, value.getSemanticObject());
    }

    public void removeCalledElement()
    {
        getSemanticObject().removeProperty(swp_calledElement);
    }

    public org.semanticwb.process.model.Callable getCalledElement()
    {
         org.semanticwb.process.model.Callable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_calledElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Callable)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement> listFlowElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement>(getSemanticObject().listObjectProperties(swp_hasFlowElement));
    }

    public boolean hasFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFlowElement,value.getSemanticObject());
        }
        return ret;
    }

    public void addFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        getSemanticObject().addObjectProperty(swp_hasFlowElement, value.getSemanticObject());
    }

    public void removeAllFlowElement()
    {
        getSemanticObject().removeProperty(swp_hasFlowElement);
    }

    public void removeFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        getSemanticObject().removeObjectProperty(swp_hasFlowElement,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowElement getFlowElement()
    {
         org.semanticwb.process.model.FlowElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowElement)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isCollapsed()
    {
        return getSemanticObject().getBooleanProperty(swp_collapsed);
    }

    public void setCollapsed(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_collapsed, value);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public void setMonitoring(org.semanticwb.process.model.Monitoring value)
    {
        getSemanticObject().setObjectProperty(swp_monitoring, value.getSemanticObject());
    }

    public void removeMonitoring()
    {
        getSemanticObject().removeProperty(swp_monitoring);
    }

    public org.semanticwb.process.model.Monitoring getMonitoring()
    {
         org.semanticwb.process.model.Monitoring ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_monitoring);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Monitoring)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
