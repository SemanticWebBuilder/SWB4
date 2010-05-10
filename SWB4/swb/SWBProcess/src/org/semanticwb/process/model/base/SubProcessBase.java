package org.semanticwb.process.model.base;


public abstract class SubProcessBase extends org.semanticwb.process.model.Activity implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
    public static final org.semanticwb.platform.SemanticProperty swp_processWebPageInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processWebPageInv");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasFlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowObject");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessClass");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessClass");
    public static final org.semanticwb.platform.SemanticClass swp_SubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcess");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcess");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess>(it, true);
        }

        public static org.semanticwb.process.model.SubProcess createSubProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SubProcess.ClassMgr.createSubProcess(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.SubProcess getSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.SubProcess createSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSubProcess(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByProcessWebPage(org.semanticwb.process.model.ProcessWebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processWebPageInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByProcessWebPage(org.semanticwb.process.model.ProcessWebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processWebPageInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByFromConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFromConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByFromConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFromConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByToConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasToConnectionObject, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByToConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasToConnectionObject,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByFlowObject(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowObject, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByFlowObject(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowObject,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowObjectInstansInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowObjectInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowObjectInstansInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByParentProcess(org.semanticwb.process.model.SubProcess value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByParentProcess(org.semanticwb.process.model.SubProcess value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parentProcessInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public SubProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setProcessWebPage(org.semanticwb.process.model.ProcessWebPage value)
    {
        getSemanticObject().setObjectProperty(swp_processWebPageInv, value.getSemanticObject());
    }

    public void removeProcessWebPage()
    {
        getSemanticObject().removeProperty(swp_processWebPageInv);
    }

    public org.semanticwb.process.model.ProcessWebPage getProcessWebPage()
    {
         org.semanticwb.process.model.ProcessWebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processWebPageInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessWebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode> listFlowObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNode>(getSemanticObject().listObjectProperties(swp_hasFlowObject));
    }

    public boolean hasFlowObject(org.semanticwb.process.model.FlowNode value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasFlowObject,value.getSemanticObject());
        }
        return ret;
    }

    public void addFlowObject(org.semanticwb.process.model.FlowNode value)
    {
        getSemanticObject().addObjectProperty(swp_hasFlowObject, value.getSemanticObject());
    }

    public void removeAllFlowObject()
    {
        getSemanticObject().removeProperty(swp_hasFlowObject);
    }

    public void removeFlowObject(org.semanticwb.process.model.FlowNode value)
    {
        getSemanticObject().removeObjectProperty(swp_hasFlowObject,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowNode getFlowObject()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasFlowObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listProcessClasses()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swp_hasProcessClass.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addProcessClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swp_hasProcessClass, value);
    }

    public void removeAllProcessClass()
    {
        getSemanticObject().removeProperty(swp_hasProcessClass);
    }

    public void removeProcessClass(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(swp_hasProcessClass,semanticobject);
    }

    public org.semanticwb.platform.SemanticObject getProcessClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swp_hasProcessClass);
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
