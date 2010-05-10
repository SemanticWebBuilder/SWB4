package org.semanticwb.process.model.base;


public abstract class ProcessSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.Localeable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
    public static final org.semanticwb.platform.SemanticClass swp_AssociationFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AssociationFlow");
    public static final org.semanticwb.platform.SemanticClass swp_InclusiveStartEventGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InclusiveStartEventGateway");
    public static final org.semanticwb.platform.SemanticClass swp_StartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StartEvent");
    public static final org.semanticwb.platform.SemanticClass swbxf_FormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
    public static final org.semanticwb.platform.SemanticClass swp_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
    public static final org.semanticwb.platform.SemanticClass swp_UserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");
    public static final org.semanticwb.platform.SemanticClass swp_ConditionalFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConditionalFlow");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageFlow");
    public static final org.semanticwb.platform.SemanticClass swp_IntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#IntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_SubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ComplexGateway");
    public static final org.semanticwb.platform.SemanticClass swp_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Event");
    public static final org.semanticwb.platform.SemanticClass swp_ParallelGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelGateway");
    public static final org.semanticwb.platform.SemanticClass swp_EndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_InclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InclusiveGateway");
    public static final org.semanticwb.platform.SemanticClass swp_DefaultFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DefaultFlow");
    public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SequenceFlow");
    public static final org.semanticwb.platform.SemanticClass swp_GateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GateWay");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticClass swp_ExclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ExclusiveGateway");
    public static final org.semanticwb.platform.SemanticClass swp_InclusiveIntermediateEventGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InclusiveIntermediateEventGateway");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessSite");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite>(it, true);
        }

        public static org.semanticwb.process.model.ProcessSite getProcessSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.process.model.ProcessSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.process.model.ProcessSite)obj.createGenericInstance();
                }
            }
            return ret;
        }

        public static org.semanticwb.process.model.ProcessSite createProcessSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.process.model.ProcessSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        public static void removeProcessSite(String id)
        {
            org.semanticwb.process.model.ProcessSite obj=getProcessSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        public static boolean hasProcessSite(String id)
        {
            return (getProcessSite(id)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProcessSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.model.FlowObjectInstance getFlowObjectInstance(String id)
    {
        return org.semanticwb.process.model.FlowObjectInstance.ClassMgr.getFlowObjectInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.FlowObjectInstance> listFlowObjectInstances()
    {
        return org.semanticwb.process.model.FlowObjectInstance.ClassMgr.listFlowObjectInstances(this);
    }

    public org.semanticwb.process.model.FlowObjectInstance createFlowObjectInstance(String id)
    {
        return org.semanticwb.process.model.FlowObjectInstance.ClassMgr.createFlowObjectInstance(id,this);
    }

    public org.semanticwb.process.model.FlowObjectInstance createFlowObjectInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swp_FlowObjectInstance);
        return org.semanticwb.process.model.FlowObjectInstance.ClassMgr.createFlowObjectInstance(String.valueOf(id),this);
    } 

    public void removeFlowObjectInstance(String id)
    {
        org.semanticwb.process.model.FlowObjectInstance.ClassMgr.removeFlowObjectInstance(id, this);
    }
    public boolean hasFlowObjectInstance(String id)
    {
        return org.semanticwb.process.model.FlowObjectInstance.ClassMgr.hasFlowObjectInstance(id, this);
    }

    public org.semanticwb.process.model.AssociationFlow getAssociationFlow(String id)
    {
        return org.semanticwb.process.model.AssociationFlow.ClassMgr.getAssociationFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlows()
    {
        return org.semanticwb.process.model.AssociationFlow.ClassMgr.listAssociationFlows(this);
    }

    public org.semanticwb.process.model.AssociationFlow createAssociationFlow(String id)
    {
        return org.semanticwb.process.model.AssociationFlow.ClassMgr.createAssociationFlow(id,this);
    }

    public org.semanticwb.process.model.AssociationFlow createAssociationFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swp_AssociationFlow);
        return org.semanticwb.process.model.AssociationFlow.ClassMgr.createAssociationFlow(String.valueOf(id),this);
    } 

    public void removeAssociationFlow(String id)
    {
        org.semanticwb.process.model.AssociationFlow.ClassMgr.removeAssociationFlow(id, this);
    }
    public boolean hasAssociationFlow(String id)
    {
        return org.semanticwb.process.model.AssociationFlow.ClassMgr.hasAssociationFlow(id, this);
    }

    public org.semanticwb.process.model.InclusiveStartEventGateway getInclusiveStartEventGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveStartEventGateway.ClassMgr.getInclusiveStartEventGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.InclusiveStartEventGateway> listInclusiveStartEventGateways()
    {
        return org.semanticwb.process.model.InclusiveStartEventGateway.ClassMgr.listInclusiveStartEventGateways(this);
    }

    public org.semanticwb.process.model.InclusiveStartEventGateway createInclusiveStartEventGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveStartEventGateway.ClassMgr.createInclusiveStartEventGateway(id,this);
    }

    public org.semanticwb.process.model.InclusiveStartEventGateway createInclusiveStartEventGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_InclusiveStartEventGateway);
        return org.semanticwb.process.model.InclusiveStartEventGateway.ClassMgr.createInclusiveStartEventGateway(String.valueOf(id),this);
    } 

    public void removeInclusiveStartEventGateway(String id)
    {
        org.semanticwb.process.model.InclusiveStartEventGateway.ClassMgr.removeInclusiveStartEventGateway(id, this);
    }
    public boolean hasInclusiveStartEventGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveStartEventGateway.ClassMgr.hasInclusiveStartEventGateway(id, this);
    }

    public org.semanticwb.process.model.StartEvent getStartEvent(String id)
    {
        return org.semanticwb.process.model.StartEvent.ClassMgr.getStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.StartEvent> listStartEvents()
    {
        return org.semanticwb.process.model.StartEvent.ClassMgr.listStartEvents(this);
    }

    public org.semanticwb.process.model.StartEvent createStartEvent(String id)
    {
        return org.semanticwb.process.model.StartEvent.ClassMgr.createStartEvent(id,this);
    }

    public org.semanticwb.process.model.StartEvent createStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_StartEvent);
        return org.semanticwb.process.model.StartEvent.ClassMgr.createStartEvent(String.valueOf(id),this);
    } 

    public void removeStartEvent(String id)
    {
        org.semanticwb.process.model.StartEvent.ClassMgr.removeStartEvent(id, this);
    }
    public boolean hasStartEvent(String id)
    {
        return org.semanticwb.process.model.StartEvent.ClassMgr.hasStartEvent(id, this);
    }

    public org.semanticwb.model.FormView getFormView(String id)
    {
        return org.semanticwb.model.FormView.ClassMgr.getFormView(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.FormView> listFormViews()
    {
        return org.semanticwb.model.FormView.ClassMgr.listFormViews(this);
    }

    public org.semanticwb.model.FormView createFormView(String id)
    {
        return org.semanticwb.model.FormView.ClassMgr.createFormView(id,this);
    }

    public org.semanticwb.model.FormView createFormView()
    {
        long id=getSemanticObject().getModel().getCounter(swbxf_FormView);
        return org.semanticwb.model.FormView.ClassMgr.createFormView(String.valueOf(id),this);
    } 

    public void removeFormView(String id)
    {
        org.semanticwb.model.FormView.ClassMgr.removeFormView(id, this);
    }
    public boolean hasFormView(String id)
    {
        return org.semanticwb.model.FormView.ClassMgr.hasFormView(id, this);
    }

    public org.semanticwb.process.model.ConnectionObject getConnectionObject(String id)
    {
        return org.semanticwb.process.model.ConnectionObject.ClassMgr.getConnectionObject(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjects()
    {
        return org.semanticwb.process.model.ConnectionObject.ClassMgr.listConnectionObjects(this);
    }

    public org.semanticwb.process.model.ConnectionObject createConnectionObject(String id)
    {
        return org.semanticwb.process.model.ConnectionObject.ClassMgr.createConnectionObject(id,this);
    }

    public void removeConnectionObject(String id)
    {
        org.semanticwb.process.model.ConnectionObject.ClassMgr.removeConnectionObject(id, this);
    }
    public boolean hasConnectionObject(String id)
    {
        return org.semanticwb.process.model.ConnectionObject.ClassMgr.hasConnectionObject(id, this);
    }

    public org.semanticwb.process.model.ProcessWebPage getProcessWebPage(String id)
    {
        return org.semanticwb.process.model.ProcessWebPage.ClassMgr.getProcessWebPage(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ProcessWebPage> listProcessWebPages()
    {
        return org.semanticwb.process.model.ProcessWebPage.ClassMgr.listProcessWebPages(this);
    }

    public org.semanticwb.process.model.ProcessWebPage createProcessWebPage(String id)
    {
        return org.semanticwb.process.model.ProcessWebPage.ClassMgr.createProcessWebPage(id,this);
    }

    public void removeProcessWebPage(String id)
    {
        org.semanticwb.process.model.ProcessWebPage.ClassMgr.removeProcessWebPage(id, this);
    }
    public boolean hasProcessWebPage(String id)
    {
        return org.semanticwb.process.model.ProcessWebPage.ClassMgr.hasProcessWebPage(id, this);
    }

    public org.semanticwb.process.model.UserTask getUserTask(String id)
    {
        return org.semanticwb.process.model.UserTask.ClassMgr.getUserTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.UserTask> listUserTasks()
    {
        return org.semanticwb.process.model.UserTask.ClassMgr.listUserTasks(this);
    }

    public org.semanticwb.process.model.UserTask createUserTask(String id)
    {
        return org.semanticwb.process.model.UserTask.ClassMgr.createUserTask(id,this);
    }

    public org.semanticwb.process.model.UserTask createUserTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_UserTask);
        return org.semanticwb.process.model.UserTask.ClassMgr.createUserTask(String.valueOf(id),this);
    } 

    public void removeUserTask(String id)
    {
        org.semanticwb.process.model.UserTask.ClassMgr.removeUserTask(id, this);
    }
    public boolean hasUserTask(String id)
    {
        return org.semanticwb.process.model.UserTask.ClassMgr.hasUserTask(id, this);
    }

    public org.semanticwb.process.model.ProcessObject getProcessObject(String id)
    {
        return org.semanticwb.process.model.ProcessObject.ClassMgr.getProcessObject(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ProcessObject> listProcessObjects()
    {
        return org.semanticwb.process.model.ProcessObject.ClassMgr.listProcessObjects(this);
    }

    public org.semanticwb.process.model.ProcessObject createProcessObject(String id)
    {
        return org.semanticwb.process.model.ProcessObject.ClassMgr.createProcessObject(id,this);
    }

    public void removeProcessObject(String id)
    {
        org.semanticwb.process.model.ProcessObject.ClassMgr.removeProcessObject(id, this);
    }
    public boolean hasProcessObject(String id)
    {
        return org.semanticwb.process.model.ProcessObject.ClassMgr.hasProcessObject(id, this);
    }

    public org.semanticwb.process.model.ConditionalFlow getConditionalFlow(String id)
    {
        return org.semanticwb.process.model.ConditionalFlow.ClassMgr.getConditionalFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ConditionalFlow> listConditionalFlows()
    {
        return org.semanticwb.process.model.ConditionalFlow.ClassMgr.listConditionalFlows(this);
    }

    public org.semanticwb.process.model.ConditionalFlow createConditionalFlow(String id)
    {
        return org.semanticwb.process.model.ConditionalFlow.ClassMgr.createConditionalFlow(id,this);
    }

    public org.semanticwb.process.model.ConditionalFlow createConditionalFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ConditionalFlow);
        return org.semanticwb.process.model.ConditionalFlow.ClassMgr.createConditionalFlow(String.valueOf(id),this);
    } 

    public void removeConditionalFlow(String id)
    {
        org.semanticwb.process.model.ConditionalFlow.ClassMgr.removeConditionalFlow(id, this);
    }
    public boolean hasConditionalFlow(String id)
    {
        return org.semanticwb.process.model.ConditionalFlow.ClassMgr.hasConditionalFlow(id, this);
    }

    public org.semanticwb.process.model.MessageFlow getMessageFlow(String id)
    {
        return org.semanticwb.process.model.MessageFlow.ClassMgr.getMessageFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlows()
    {
        return org.semanticwb.process.model.MessageFlow.ClassMgr.listMessageFlows(this);
    }

    public org.semanticwb.process.model.MessageFlow createMessageFlow(String id)
    {
        return org.semanticwb.process.model.MessageFlow.ClassMgr.createMessageFlow(id,this);
    }

    public org.semanticwb.process.model.MessageFlow createMessageFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MessageFlow);
        return org.semanticwb.process.model.MessageFlow.ClassMgr.createMessageFlow(String.valueOf(id),this);
    } 

    public void removeMessageFlow(String id)
    {
        org.semanticwb.process.model.MessageFlow.ClassMgr.removeMessageFlow(id, this);
    }
    public boolean hasMessageFlow(String id)
    {
        return org.semanticwb.process.model.MessageFlow.ClassMgr.hasMessageFlow(id, this);
    }

    public org.semanticwb.process.model.IntermediateCatchEvent getIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.IntermediateCatchEvent.ClassMgr.getIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.IntermediateCatchEvent> listIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.IntermediateCatchEvent.ClassMgr.listIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.IntermediateCatchEvent createIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.IntermediateCatchEvent.ClassMgr.createIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.IntermediateCatchEvent createIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_IntermediateCatchEvent);
        return org.semanticwb.process.model.IntermediateCatchEvent.ClassMgr.createIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.IntermediateCatchEvent.ClassMgr.removeIntermediateCatchEvent(id, this);
    }
    public boolean hasIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.IntermediateCatchEvent.ClassMgr.hasIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.SubProcess getSubProcess(String id)
    {
        return org.semanticwb.process.model.SubProcess.ClassMgr.getSubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcesses()
    {
        return org.semanticwb.process.model.SubProcess.ClassMgr.listSubProcesses(this);
    }

    public org.semanticwb.process.model.SubProcess createSubProcess(String id)
    {
        return org.semanticwb.process.model.SubProcess.ClassMgr.createSubProcess(id,this);
    }

    public org.semanticwb.process.model.SubProcess createSubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SubProcess);
        return org.semanticwb.process.model.SubProcess.ClassMgr.createSubProcess(String.valueOf(id),this);
    } 

    public void removeSubProcess(String id)
    {
        org.semanticwb.process.model.SubProcess.ClassMgr.removeSubProcess(id, this);
    }
    public boolean hasSubProcess(String id)
    {
        return org.semanticwb.process.model.SubProcess.ClassMgr.hasSubProcess(id, this);
    }

    public org.semanticwb.process.model.ComplexGateway getComplexGateway(String id)
    {
        return org.semanticwb.process.model.ComplexGateway.ClassMgr.getComplexGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGateways()
    {
        return org.semanticwb.process.model.ComplexGateway.ClassMgr.listComplexGateways(this);
    }

    public org.semanticwb.process.model.ComplexGateway createComplexGateway(String id)
    {
        return org.semanticwb.process.model.ComplexGateway.ClassMgr.createComplexGateway(id,this);
    }

    public org.semanticwb.process.model.ComplexGateway createComplexGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ComplexGateway);
        return org.semanticwb.process.model.ComplexGateway.ClassMgr.createComplexGateway(String.valueOf(id),this);
    } 

    public void removeComplexGateway(String id)
    {
        org.semanticwb.process.model.ComplexGateway.ClassMgr.removeComplexGateway(id, this);
    }
    public boolean hasComplexGateway(String id)
    {
        return org.semanticwb.process.model.ComplexGateway.ClassMgr.hasComplexGateway(id, this);
    }

    public org.semanticwb.process.model.Event getEvent(String id)
    {
        return org.semanticwb.process.model.Event.ClassMgr.getEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Event> listEvents()
    {
        return org.semanticwb.process.model.Event.ClassMgr.listEvents(this);
    }

    public org.semanticwb.process.model.Event createEvent(String id)
    {
        return org.semanticwb.process.model.Event.ClassMgr.createEvent(id,this);
    }

    public void removeEvent(String id)
    {
        org.semanticwb.process.model.Event.ClassMgr.removeEvent(id, this);
    }
    public boolean hasEvent(String id)
    {
        return org.semanticwb.process.model.Event.ClassMgr.hasEvent(id, this);
    }

    public org.semanticwb.process.model.ParallelGateway getParallelGateway(String id)
    {
        return org.semanticwb.process.model.ParallelGateway.ClassMgr.getParallelGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ParallelGateway> listParallelGateways()
    {
        return org.semanticwb.process.model.ParallelGateway.ClassMgr.listParallelGateways(this);
    }

    public org.semanticwb.process.model.ParallelGateway createParallelGateway(String id)
    {
        return org.semanticwb.process.model.ParallelGateway.ClassMgr.createParallelGateway(id,this);
    }

    public org.semanticwb.process.model.ParallelGateway createParallelGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ParallelGateway);
        return org.semanticwb.process.model.ParallelGateway.ClassMgr.createParallelGateway(String.valueOf(id),this);
    } 

    public void removeParallelGateway(String id)
    {
        org.semanticwb.process.model.ParallelGateway.ClassMgr.removeParallelGateway(id, this);
    }
    public boolean hasParallelGateway(String id)
    {
        return org.semanticwb.process.model.ParallelGateway.ClassMgr.hasParallelGateway(id, this);
    }

    public org.semanticwb.process.model.EndEvent getEndEvent(String id)
    {
        return org.semanticwb.process.model.EndEvent.ClassMgr.getEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.EndEvent> listEndEvents()
    {
        return org.semanticwb.process.model.EndEvent.ClassMgr.listEndEvents(this);
    }

    public org.semanticwb.process.model.EndEvent createEndEvent(String id)
    {
        return org.semanticwb.process.model.EndEvent.ClassMgr.createEndEvent(id,this);
    }

    public org.semanticwb.process.model.EndEvent createEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_EndEvent);
        return org.semanticwb.process.model.EndEvent.ClassMgr.createEndEvent(String.valueOf(id),this);
    } 

    public void removeEndEvent(String id)
    {
        org.semanticwb.process.model.EndEvent.ClassMgr.removeEndEvent(id, this);
    }
    public boolean hasEndEvent(String id)
    {
        return org.semanticwb.process.model.EndEvent.ClassMgr.hasEndEvent(id, this);
    }

    public org.semanticwb.process.model.InclusiveGateway getInclusiveGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveGateway.ClassMgr.getInclusiveGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.InclusiveGateway> listInclusiveGateways()
    {
        return org.semanticwb.process.model.InclusiveGateway.ClassMgr.listInclusiveGateways(this);
    }

    public org.semanticwb.process.model.InclusiveGateway createInclusiveGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveGateway.ClassMgr.createInclusiveGateway(id,this);
    }

    public org.semanticwb.process.model.InclusiveGateway createInclusiveGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_InclusiveGateway);
        return org.semanticwb.process.model.InclusiveGateway.ClassMgr.createInclusiveGateway(String.valueOf(id),this);
    } 

    public void removeInclusiveGateway(String id)
    {
        org.semanticwb.process.model.InclusiveGateway.ClassMgr.removeInclusiveGateway(id, this);
    }
    public boolean hasInclusiveGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveGateway.ClassMgr.hasInclusiveGateway(id, this);
    }

    public org.semanticwb.process.model.DefaultFlow getDefaultFlow(String id)
    {
        return org.semanticwb.process.model.DefaultFlow.ClassMgr.getDefaultFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlows()
    {
        return org.semanticwb.process.model.DefaultFlow.ClassMgr.listDefaultFlows(this);
    }

    public org.semanticwb.process.model.DefaultFlow createDefaultFlow(String id)
    {
        return org.semanticwb.process.model.DefaultFlow.ClassMgr.createDefaultFlow(id,this);
    }

    public org.semanticwb.process.model.DefaultFlow createDefaultFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DefaultFlow);
        return org.semanticwb.process.model.DefaultFlow.ClassMgr.createDefaultFlow(String.valueOf(id),this);
    } 

    public void removeDefaultFlow(String id)
    {
        org.semanticwb.process.model.DefaultFlow.ClassMgr.removeDefaultFlow(id, this);
    }
    public boolean hasDefaultFlow(String id)
    {
        return org.semanticwb.process.model.DefaultFlow.ClassMgr.hasDefaultFlow(id, this);
    }

    public org.semanticwb.process.model.SequenceFlow getSequenceFlow(String id)
    {
        return org.semanticwb.process.model.SequenceFlow.ClassMgr.getSequenceFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlows()
    {
        return org.semanticwb.process.model.SequenceFlow.ClassMgr.listSequenceFlows(this);
    }

    public org.semanticwb.process.model.SequenceFlow createSequenceFlow(String id)
    {
        return org.semanticwb.process.model.SequenceFlow.ClassMgr.createSequenceFlow(id,this);
    }

    public org.semanticwb.process.model.SequenceFlow createSequenceFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SequenceFlow);
        return org.semanticwb.process.model.SequenceFlow.ClassMgr.createSequenceFlow(String.valueOf(id),this);
    } 

    public void removeSequenceFlow(String id)
    {
        org.semanticwb.process.model.SequenceFlow.ClassMgr.removeSequenceFlow(id, this);
    }
    public boolean hasSequenceFlow(String id)
    {
        return org.semanticwb.process.model.SequenceFlow.ClassMgr.hasSequenceFlow(id, this);
    }

    public org.semanticwb.process.model.GateWay getGateWay(String id)
    {
        return org.semanticwb.process.model.GateWay.ClassMgr.getGateWay(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GateWay> listGateWays()
    {
        return org.semanticwb.process.model.GateWay.ClassMgr.listGateWays(this);
    }

    public org.semanticwb.process.model.GateWay createGateWay(String id)
    {
        return org.semanticwb.process.model.GateWay.ClassMgr.createGateWay(id,this);
    }

    public org.semanticwb.process.model.GateWay createGateWay()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GateWay);
        return org.semanticwb.process.model.GateWay.ClassMgr.createGateWay(String.valueOf(id),this);
    } 

    public void removeGateWay(String id)
    {
        org.semanticwb.process.model.GateWay.ClassMgr.removeGateWay(id, this);
    }
    public boolean hasGateWay(String id)
    {
        return org.semanticwb.process.model.GateWay.ClassMgr.hasGateWay(id, this);
    }

    public org.semanticwb.process.model.ProcessInstance getProcessInstance(String id)
    {
        return org.semanticwb.process.model.ProcessInstance.ClassMgr.getProcessInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ProcessInstance> listProcessInstances()
    {
        return org.semanticwb.process.model.ProcessInstance.ClassMgr.listProcessInstances(this);
    }

    public org.semanticwb.process.model.ProcessInstance createProcessInstance(String id)
    {
        return org.semanticwb.process.model.ProcessInstance.ClassMgr.createProcessInstance(id,this);
    }

    public org.semanticwb.process.model.ProcessInstance createProcessInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ProcessInstance);
        return org.semanticwb.process.model.ProcessInstance.ClassMgr.createProcessInstance(String.valueOf(id),this);
    } 

    public void removeProcessInstance(String id)
    {
        org.semanticwb.process.model.ProcessInstance.ClassMgr.removeProcessInstance(id, this);
    }
    public boolean hasProcessInstance(String id)
    {
        return org.semanticwb.process.model.ProcessInstance.ClassMgr.hasProcessInstance(id, this);
    }

    public org.semanticwb.process.model.ExclusiveGateway getExclusiveGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveGateway.ClassMgr.getExclusiveGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGateways()
    {
        return org.semanticwb.process.model.ExclusiveGateway.ClassMgr.listExclusiveGateways(this);
    }

    public org.semanticwb.process.model.ExclusiveGateway createExclusiveGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveGateway.ClassMgr.createExclusiveGateway(id,this);
    }

    public org.semanticwb.process.model.ExclusiveGateway createExclusiveGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ExclusiveGateway);
        return org.semanticwb.process.model.ExclusiveGateway.ClassMgr.createExclusiveGateway(String.valueOf(id),this);
    } 

    public void removeExclusiveGateway(String id)
    {
        org.semanticwb.process.model.ExclusiveGateway.ClassMgr.removeExclusiveGateway(id, this);
    }
    public boolean hasExclusiveGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveGateway.ClassMgr.hasExclusiveGateway(id, this);
    }

    public org.semanticwb.process.model.InclusiveIntermediateEventGateway getInclusiveIntermediateEventGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveIntermediateEventGateway.ClassMgr.getInclusiveIntermediateEventGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.InclusiveIntermediateEventGateway> listInclusiveIntermediateEventGateways()
    {
        return org.semanticwb.process.model.InclusiveIntermediateEventGateway.ClassMgr.listInclusiveIntermediateEventGateways(this);
    }

    public org.semanticwb.process.model.InclusiveIntermediateEventGateway createInclusiveIntermediateEventGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveIntermediateEventGateway.ClassMgr.createInclusiveIntermediateEventGateway(id,this);
    }

    public org.semanticwb.process.model.InclusiveIntermediateEventGateway createInclusiveIntermediateEventGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_InclusiveIntermediateEventGateway);
        return org.semanticwb.process.model.InclusiveIntermediateEventGateway.ClassMgr.createInclusiveIntermediateEventGateway(String.valueOf(id),this);
    } 

    public void removeInclusiveIntermediateEventGateway(String id)
    {
        org.semanticwb.process.model.InclusiveIntermediateEventGateway.ClassMgr.removeInclusiveIntermediateEventGateway(id, this);
    }
    public boolean hasInclusiveIntermediateEventGateway(String id)
    {
        return org.semanticwb.process.model.InclusiveIntermediateEventGateway.ClassMgr.hasInclusiveIntermediateEventGateway(id, this);
    }
}
