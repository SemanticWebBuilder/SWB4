package org.semanticwb.process.model.base;


public abstract class ProcessSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Undeleteable,org.semanticwb.model.Localeable,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.Indexable
{
    public static final org.semanticwb.platform.SemanticClass swp_AdhocSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AdhocSubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriod");
    public static final org.semanticwb.platform.SemanticClass swp_MultipleSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleSubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_ParallelStartEventGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelStartEventGateway");
    public static final org.semanticwb.platform.SemanticClass swp_ExclusiveStartEventGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ExclusiveStartEventGateway");
    public static final org.semanticwb.platform.SemanticClass swp_RuleIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RuleIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_RuleStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RuleStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_AssociationFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AssociationFlow");
    public static final org.semanticwb.platform.SemanticClass swp_ScalationEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ScalationEndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ScalationIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ScalationIntermediateThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_SignalEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SignalEndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_StartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StartEvent");
    public static final org.semanticwb.platform.SemanticClass swbxf_FormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
    public static final org.semanticwb.platform.SemanticClass swp_AdhocTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AdhocTask");
    public static final org.semanticwb.platform.SemanticClass swp_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");
    public static final org.semanticwb.platform.SemanticClass swp_EventBasedGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EventBasedGateway");
    public static final org.semanticwb.platform.SemanticClass swp_ServiceTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ServiceTask");
    public static final org.semanticwb.platform.SemanticClass swp_CompensationStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_MultipleIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleIntermediateThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_SignalIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SignalIntermediateThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
    public static final org.semanticwb.platform.SemanticClass swp_ScalationStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ScalationStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_UserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObject");
    public static final org.semanticwb.platform.SemanticClass swp_MultipleStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_CompensationTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationTask");
    public static final org.semanticwb.platform.SemanticClass swp_MessageIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRule");
    public static final org.semanticwb.platform.SemanticClass swp_ReceiveTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ReceiveTask");
    public static final org.semanticwb.platform.SemanticClass swp_Lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Lane");
    public static final org.semanticwb.platform.SemanticClass swp_ConditionalFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConditionalFlow");
    public static final org.semanticwb.platform.SemanticClass swp_TransactionSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TransactionSubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_MessageEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageEndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_LinkIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LinkIntermediateThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ErrorIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ErrorIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_SubProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcessInstance");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageFlow");
    public static final org.semanticwb.platform.SemanticClass swp_IntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#IntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_MultipleEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleEndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ManualTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ManualTask");
    public static final org.semanticwb.platform.SemanticClass swp_AnnotationArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AnnotationArtifact");
    public static final org.semanticwb.platform.SemanticClass swp_SubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ComplexGateway");
    public static final org.semanticwb.platform.SemanticClass swp_ErrorEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ErrorEndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_TerminationEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TerminationEndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_LoopSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LoopSubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_TimerIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TimerIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Event");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticClass swp_ParallelGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelGateway");
    public static final org.semanticwb.platform.SemanticClass swp_ParallelStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_DirectionalAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DirectionalAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_MessageStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_Pool=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Pool");
    public static final org.semanticwb.platform.SemanticClass swp_MessageIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MessageIntermediateThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ScriptTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ScriptTask");
    public static final org.semanticwb.platform.SemanticClass swp_LoopTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LoopTask");
    public static final org.semanticwb.platform.SemanticClass swp_MultipleTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleTask");
    public static final org.semanticwb.platform.SemanticClass swp_LinkIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LinkIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_DataStoreArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DataStoreArtifact");
    public static final org.semanticwb.platform.SemanticClass swp_CompensationIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ScalationIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ScalationIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_Gateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Gateway");
    public static final org.semanticwb.platform.SemanticClass swp_EndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#EndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_GroupArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GroupArtifact");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
    public static final org.semanticwb.platform.SemanticClass swp_ParallelIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ParallelIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_InclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InclusiveGateway");
    public static final org.semanticwb.platform.SemanticClass swp_CancelationEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CancelationEndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ErrorStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ErrorStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_SignalIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SignalIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ExclusiveIntermediateEventGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ExclusiveIntermediateEventGateway");
    public static final org.semanticwb.platform.SemanticClass swp_DefaultFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DefaultFlow");
    public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SequenceFlow");
    public static final org.semanticwb.platform.SemanticClass swp_CompensationIntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationIntermediateThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_CancelationIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CancelationIntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_CatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_SendTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SendTask");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticClass swp_CompensationEndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationEndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ExclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ExclusiveGateway");
    public static final org.semanticwb.platform.SemanticClass swp_TimerStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TimerStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GraphicalElement");
    public static final org.semanticwb.platform.SemanticClass swp_SignalStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SignalStartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_Artifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Artifact");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticClass swp_CompensationSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationSubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_MultipleIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleIntermediateCatchEvent");
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

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessSite> listProcessSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
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

    public org.semanticwb.process.model.AdhocSubProcess getAdhocSubProcess(String id)
    {
        return org.semanticwb.process.model.AdhocSubProcess.ClassMgr.getAdhocSubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.AdhocSubProcess> listAdhocSubProcesses()
    {
        return org.semanticwb.process.model.AdhocSubProcess.ClassMgr.listAdhocSubProcesses(this);
    }

    public org.semanticwb.process.model.AdhocSubProcess createAdhocSubProcess(String id)
    {
        return org.semanticwb.process.model.AdhocSubProcess.ClassMgr.createAdhocSubProcess(id,this);
    }

    public org.semanticwb.process.model.AdhocSubProcess createAdhocSubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_AdhocSubProcess);
        return org.semanticwb.process.model.AdhocSubProcess.ClassMgr.createAdhocSubProcess(String.valueOf(id),this);
    } 

    public void removeAdhocSubProcess(String id)
    {
        org.semanticwb.process.model.AdhocSubProcess.ClassMgr.removeAdhocSubProcess(id, this);
    }
    public boolean hasAdhocSubProcess(String id)
    {
        return org.semanticwb.process.model.AdhocSubProcess.ClassMgr.hasAdhocSubProcess(id, this);
    }

    public org.semanticwb.process.model.ProcessPeriod getProcessPeriod(String id)
    {
        return org.semanticwb.process.model.ProcessPeriod.ClassMgr.getProcessPeriod(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriods()
    {
        return org.semanticwb.process.model.ProcessPeriod.ClassMgr.listProcessPeriods(this);
    }

    public org.semanticwb.process.model.ProcessPeriod createProcessPeriod(String id)
    {
        return org.semanticwb.process.model.ProcessPeriod.ClassMgr.createProcessPeriod(id,this);
    }

    public void removeProcessPeriod(String id)
    {
        org.semanticwb.process.model.ProcessPeriod.ClassMgr.removeProcessPeriod(id, this);
    }
    public boolean hasProcessPeriod(String id)
    {
        return org.semanticwb.process.model.ProcessPeriod.ClassMgr.hasProcessPeriod(id, this);
    }

    public org.semanticwb.process.model.MultipleSubProcess getMultipleSubProcess(String id)
    {
        return org.semanticwb.process.model.MultipleSubProcess.ClassMgr.getMultipleSubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcesses()
    {
        return org.semanticwb.process.model.MultipleSubProcess.ClassMgr.listMultipleSubProcesses(this);
    }

    public org.semanticwb.process.model.MultipleSubProcess createMultipleSubProcess(String id)
    {
        return org.semanticwb.process.model.MultipleSubProcess.ClassMgr.createMultipleSubProcess(id,this);
    }

    public org.semanticwb.process.model.MultipleSubProcess createMultipleSubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MultipleSubProcess);
        return org.semanticwb.process.model.MultipleSubProcess.ClassMgr.createMultipleSubProcess(String.valueOf(id),this);
    } 

    public void removeMultipleSubProcess(String id)
    {
        org.semanticwb.process.model.MultipleSubProcess.ClassMgr.removeMultipleSubProcess(id, this);
    }
    public boolean hasMultipleSubProcess(String id)
    {
        return org.semanticwb.process.model.MultipleSubProcess.ClassMgr.hasMultipleSubProcess(id, this);
    }

    public org.semanticwb.process.model.ParallelStartEventGateway getParallelStartEventGateway(String id)
    {
        return org.semanticwb.process.model.ParallelStartEventGateway.ClassMgr.getParallelStartEventGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ParallelStartEventGateway> listParallelStartEventGateways()
    {
        return org.semanticwb.process.model.ParallelStartEventGateway.ClassMgr.listParallelStartEventGateways(this);
    }

    public org.semanticwb.process.model.ParallelStartEventGateway createParallelStartEventGateway(String id)
    {
        return org.semanticwb.process.model.ParallelStartEventGateway.ClassMgr.createParallelStartEventGateway(id,this);
    }

    public org.semanticwb.process.model.ParallelStartEventGateway createParallelStartEventGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ParallelStartEventGateway);
        return org.semanticwb.process.model.ParallelStartEventGateway.ClassMgr.createParallelStartEventGateway(String.valueOf(id),this);
    } 

    public void removeParallelStartEventGateway(String id)
    {
        org.semanticwb.process.model.ParallelStartEventGateway.ClassMgr.removeParallelStartEventGateway(id, this);
    }
    public boolean hasParallelStartEventGateway(String id)
    {
        return org.semanticwb.process.model.ParallelStartEventGateway.ClassMgr.hasParallelStartEventGateway(id, this);
    }

    public org.semanticwb.process.model.ExclusiveStartEventGateway getExclusiveStartEventGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveStartEventGateway.ClassMgr.getExclusiveStartEventGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ExclusiveStartEventGateway> listExclusiveStartEventGateways()
    {
        return org.semanticwb.process.model.ExclusiveStartEventGateway.ClassMgr.listExclusiveStartEventGateways(this);
    }

    public org.semanticwb.process.model.ExclusiveStartEventGateway createExclusiveStartEventGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveStartEventGateway.ClassMgr.createExclusiveStartEventGateway(id,this);
    }

    public org.semanticwb.process.model.ExclusiveStartEventGateway createExclusiveStartEventGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ExclusiveStartEventGateway);
        return org.semanticwb.process.model.ExclusiveStartEventGateway.ClassMgr.createExclusiveStartEventGateway(String.valueOf(id),this);
    } 

    public void removeExclusiveStartEventGateway(String id)
    {
        org.semanticwb.process.model.ExclusiveStartEventGateway.ClassMgr.removeExclusiveStartEventGateway(id, this);
    }
    public boolean hasExclusiveStartEventGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveStartEventGateway.ClassMgr.hasExclusiveStartEventGateway(id, this);
    }

    public org.semanticwb.process.model.RuleIntermediateCatchEvent getRuleIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.RuleIntermediateCatchEvent.ClassMgr.getRuleIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.RuleIntermediateCatchEvent.ClassMgr.listRuleIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.RuleIntermediateCatchEvent createRuleIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.RuleIntermediateCatchEvent.ClassMgr.createRuleIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.RuleIntermediateCatchEvent createRuleIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_RuleIntermediateCatchEvent);
        return org.semanticwb.process.model.RuleIntermediateCatchEvent.ClassMgr.createRuleIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeRuleIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.RuleIntermediateCatchEvent.ClassMgr.removeRuleIntermediateCatchEvent(id, this);
    }
    public boolean hasRuleIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.RuleIntermediateCatchEvent.ClassMgr.hasRuleIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.RuleStartEvent getRuleStartEvent(String id)
    {
        return org.semanticwb.process.model.RuleStartEvent.ClassMgr.getRuleStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.RuleStartEvent> listRuleStartEvents()
    {
        return org.semanticwb.process.model.RuleStartEvent.ClassMgr.listRuleStartEvents(this);
    }

    public org.semanticwb.process.model.RuleStartEvent createRuleStartEvent(String id)
    {
        return org.semanticwb.process.model.RuleStartEvent.ClassMgr.createRuleStartEvent(id,this);
    }

    public org.semanticwb.process.model.RuleStartEvent createRuleStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_RuleStartEvent);
        return org.semanticwb.process.model.RuleStartEvent.ClassMgr.createRuleStartEvent(String.valueOf(id),this);
    } 

    public void removeRuleStartEvent(String id)
    {
        org.semanticwb.process.model.RuleStartEvent.ClassMgr.removeRuleStartEvent(id, this);
    }
    public boolean hasRuleStartEvent(String id)
    {
        return org.semanticwb.process.model.RuleStartEvent.ClassMgr.hasRuleStartEvent(id, this);
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

    public org.semanticwb.process.model.ScalationEndEvent getScalationEndEvent(String id)
    {
        return org.semanticwb.process.model.ScalationEndEvent.ClassMgr.getScalationEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ScalationEndEvent> listScalationEndEvents()
    {
        return org.semanticwb.process.model.ScalationEndEvent.ClassMgr.listScalationEndEvents(this);
    }

    public org.semanticwb.process.model.ScalationEndEvent createScalationEndEvent(String id)
    {
        return org.semanticwb.process.model.ScalationEndEvent.ClassMgr.createScalationEndEvent(id,this);
    }

    public org.semanticwb.process.model.ScalationEndEvent createScalationEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ScalationEndEvent);
        return org.semanticwb.process.model.ScalationEndEvent.ClassMgr.createScalationEndEvent(String.valueOf(id),this);
    } 

    public void removeScalationEndEvent(String id)
    {
        org.semanticwb.process.model.ScalationEndEvent.ClassMgr.removeScalationEndEvent(id, this);
    }
    public boolean hasScalationEndEvent(String id)
    {
        return org.semanticwb.process.model.ScalationEndEvent.ClassMgr.hasScalationEndEvent(id, this);
    }

    public org.semanticwb.process.model.ScalationIntermediateThrowEvent getScalationIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.ScalationIntermediateThrowEvent.ClassMgr.getScalationIntermediateThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ScalationIntermediateThrowEvent> listScalationIntermediateThrowEvents()
    {
        return org.semanticwb.process.model.ScalationIntermediateThrowEvent.ClassMgr.listScalationIntermediateThrowEvents(this);
    }

    public org.semanticwb.process.model.ScalationIntermediateThrowEvent createScalationIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.ScalationIntermediateThrowEvent.ClassMgr.createScalationIntermediateThrowEvent(id,this);
    }

    public org.semanticwb.process.model.ScalationIntermediateThrowEvent createScalationIntermediateThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ScalationIntermediateThrowEvent);
        return org.semanticwb.process.model.ScalationIntermediateThrowEvent.ClassMgr.createScalationIntermediateThrowEvent(String.valueOf(id),this);
    } 

    public void removeScalationIntermediateThrowEvent(String id)
    {
        org.semanticwb.process.model.ScalationIntermediateThrowEvent.ClassMgr.removeScalationIntermediateThrowEvent(id, this);
    }
    public boolean hasScalationIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.ScalationIntermediateThrowEvent.ClassMgr.hasScalationIntermediateThrowEvent(id, this);
    }

    public org.semanticwb.process.model.SignalEndEvent getSignalEndEvent(String id)
    {
        return org.semanticwb.process.model.SignalEndEvent.ClassMgr.getSignalEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SignalEndEvent> listSignalEndEvents()
    {
        return org.semanticwb.process.model.SignalEndEvent.ClassMgr.listSignalEndEvents(this);
    }

    public org.semanticwb.process.model.SignalEndEvent createSignalEndEvent(String id)
    {
        return org.semanticwb.process.model.SignalEndEvent.ClassMgr.createSignalEndEvent(id,this);
    }

    public org.semanticwb.process.model.SignalEndEvent createSignalEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SignalEndEvent);
        return org.semanticwb.process.model.SignalEndEvent.ClassMgr.createSignalEndEvent(String.valueOf(id),this);
    } 

    public void removeSignalEndEvent(String id)
    {
        org.semanticwb.process.model.SignalEndEvent.ClassMgr.removeSignalEndEvent(id, this);
    }
    public boolean hasSignalEndEvent(String id)
    {
        return org.semanticwb.process.model.SignalEndEvent.ClassMgr.hasSignalEndEvent(id, this);
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

    public org.semanticwb.process.model.AdhocTask getAdhocTask(String id)
    {
        return org.semanticwb.process.model.AdhocTask.ClassMgr.getAdhocTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.AdhocTask> listAdhocTasks()
    {
        return org.semanticwb.process.model.AdhocTask.ClassMgr.listAdhocTasks(this);
    }

    public org.semanticwb.process.model.AdhocTask createAdhocTask(String id)
    {
        return org.semanticwb.process.model.AdhocTask.ClassMgr.createAdhocTask(id,this);
    }

    public org.semanticwb.process.model.AdhocTask createAdhocTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_AdhocTask);
        return org.semanticwb.process.model.AdhocTask.ClassMgr.createAdhocTask(String.valueOf(id),this);
    } 

    public void removeAdhocTask(String id)
    {
        org.semanticwb.process.model.AdhocTask.ClassMgr.removeAdhocTask(id, this);
    }
    public boolean hasAdhocTask(String id)
    {
        return org.semanticwb.process.model.AdhocTask.ClassMgr.hasAdhocTask(id, this);
    }

    public org.semanticwb.process.model.Task getTask(String id)
    {
        return org.semanticwb.process.model.Task.ClassMgr.getTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Task> listTasks()
    {
        return org.semanticwb.process.model.Task.ClassMgr.listTasks(this);
    }

    public org.semanticwb.process.model.Task createTask(String id)
    {
        return org.semanticwb.process.model.Task.ClassMgr.createTask(id,this);
    }

    public org.semanticwb.process.model.Task createTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Task);
        return org.semanticwb.process.model.Task.ClassMgr.createTask(String.valueOf(id),this);
    } 

    public void removeTask(String id)
    {
        org.semanticwb.process.model.Task.ClassMgr.removeTask(id, this);
    }
    public boolean hasTask(String id)
    {
        return org.semanticwb.process.model.Task.ClassMgr.hasTask(id, this);
    }

    public org.semanticwb.process.model.EventBasedGateway getEventBasedGateway(String id)
    {
        return org.semanticwb.process.model.EventBasedGateway.ClassMgr.getEventBasedGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.EventBasedGateway> listEventBasedGateways()
    {
        return org.semanticwb.process.model.EventBasedGateway.ClassMgr.listEventBasedGateways(this);
    }

    public org.semanticwb.process.model.EventBasedGateway createEventBasedGateway(String id)
    {
        return org.semanticwb.process.model.EventBasedGateway.ClassMgr.createEventBasedGateway(id,this);
    }

    public void removeEventBasedGateway(String id)
    {
        org.semanticwb.process.model.EventBasedGateway.ClassMgr.removeEventBasedGateway(id, this);
    }
    public boolean hasEventBasedGateway(String id)
    {
        return org.semanticwb.process.model.EventBasedGateway.ClassMgr.hasEventBasedGateway(id, this);
    }

    public org.semanticwb.process.model.ServiceTask getServiceTask(String id)
    {
        return org.semanticwb.process.model.ServiceTask.ClassMgr.getServiceTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ServiceTask> listServiceTasks()
    {
        return org.semanticwb.process.model.ServiceTask.ClassMgr.listServiceTasks(this);
    }

    public org.semanticwb.process.model.ServiceTask createServiceTask(String id)
    {
        return org.semanticwb.process.model.ServiceTask.ClassMgr.createServiceTask(id,this);
    }

    public org.semanticwb.process.model.ServiceTask createServiceTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ServiceTask);
        return org.semanticwb.process.model.ServiceTask.ClassMgr.createServiceTask(String.valueOf(id),this);
    } 

    public void removeServiceTask(String id)
    {
        org.semanticwb.process.model.ServiceTask.ClassMgr.removeServiceTask(id, this);
    }
    public boolean hasServiceTask(String id)
    {
        return org.semanticwb.process.model.ServiceTask.ClassMgr.hasServiceTask(id, this);
    }

    public org.semanticwb.process.model.CompensationStartEvent getCompensationStartEvent(String id)
    {
        return org.semanticwb.process.model.CompensationStartEvent.ClassMgr.getCompensationStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CompensationStartEvent> listCompensationStartEvents()
    {
        return org.semanticwb.process.model.CompensationStartEvent.ClassMgr.listCompensationStartEvents(this);
    }

    public org.semanticwb.process.model.CompensationStartEvent createCompensationStartEvent(String id)
    {
        return org.semanticwb.process.model.CompensationStartEvent.ClassMgr.createCompensationStartEvent(id,this);
    }

    public org.semanticwb.process.model.CompensationStartEvent createCompensationStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CompensationStartEvent);
        return org.semanticwb.process.model.CompensationStartEvent.ClassMgr.createCompensationStartEvent(String.valueOf(id),this);
    } 

    public void removeCompensationStartEvent(String id)
    {
        org.semanticwb.process.model.CompensationStartEvent.ClassMgr.removeCompensationStartEvent(id, this);
    }
    public boolean hasCompensationStartEvent(String id)
    {
        return org.semanticwb.process.model.CompensationStartEvent.ClassMgr.hasCompensationStartEvent(id, this);
    }

    public org.semanticwb.process.model.MultipleIntermediateThrowEvent getMultipleIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.MultipleIntermediateThrowEvent.ClassMgr.getMultipleIntermediateThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateThrowEvent> listMultipleIntermediateThrowEvents()
    {
        return org.semanticwb.process.model.MultipleIntermediateThrowEvent.ClassMgr.listMultipleIntermediateThrowEvents(this);
    }

    public org.semanticwb.process.model.MultipleIntermediateThrowEvent createMultipleIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.MultipleIntermediateThrowEvent.ClassMgr.createMultipleIntermediateThrowEvent(id,this);
    }

    public org.semanticwb.process.model.MultipleIntermediateThrowEvent createMultipleIntermediateThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MultipleIntermediateThrowEvent);
        return org.semanticwb.process.model.MultipleIntermediateThrowEvent.ClassMgr.createMultipleIntermediateThrowEvent(String.valueOf(id),this);
    } 

    public void removeMultipleIntermediateThrowEvent(String id)
    {
        org.semanticwb.process.model.MultipleIntermediateThrowEvent.ClassMgr.removeMultipleIntermediateThrowEvent(id, this);
    }
    public boolean hasMultipleIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.MultipleIntermediateThrowEvent.ClassMgr.hasMultipleIntermediateThrowEvent(id, this);
    }

    public org.semanticwb.process.model.SignalIntermediateThrowEvent getSignalIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.SignalIntermediateThrowEvent.ClassMgr.getSignalIntermediateThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SignalIntermediateThrowEvent> listSignalIntermediateThrowEvents()
    {
        return org.semanticwb.process.model.SignalIntermediateThrowEvent.ClassMgr.listSignalIntermediateThrowEvents(this);
    }

    public org.semanticwb.process.model.SignalIntermediateThrowEvent createSignalIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.SignalIntermediateThrowEvent.ClassMgr.createSignalIntermediateThrowEvent(id,this);
    }

    public org.semanticwb.process.model.SignalIntermediateThrowEvent createSignalIntermediateThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SignalIntermediateThrowEvent);
        return org.semanticwb.process.model.SignalIntermediateThrowEvent.ClassMgr.createSignalIntermediateThrowEvent(String.valueOf(id),this);
    } 

    public void removeSignalIntermediateThrowEvent(String id)
    {
        org.semanticwb.process.model.SignalIntermediateThrowEvent.ClassMgr.removeSignalIntermediateThrowEvent(id, this);
    }
    public boolean hasSignalIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.SignalIntermediateThrowEvent.ClassMgr.hasSignalIntermediateThrowEvent(id, this);
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

    public org.semanticwb.process.model.ScalationStartEvent getScalationStartEvent(String id)
    {
        return org.semanticwb.process.model.ScalationStartEvent.ClassMgr.getScalationStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ScalationStartEvent> listScalationStartEvents()
    {
        return org.semanticwb.process.model.ScalationStartEvent.ClassMgr.listScalationStartEvents(this);
    }

    public org.semanticwb.process.model.ScalationStartEvent createScalationStartEvent(String id)
    {
        return org.semanticwb.process.model.ScalationStartEvent.ClassMgr.createScalationStartEvent(id,this);
    }

    public org.semanticwb.process.model.ScalationStartEvent createScalationStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ScalationStartEvent);
        return org.semanticwb.process.model.ScalationStartEvent.ClassMgr.createScalationStartEvent(String.valueOf(id),this);
    } 

    public void removeScalationStartEvent(String id)
    {
        org.semanticwb.process.model.ScalationStartEvent.ClassMgr.removeScalationStartEvent(id, this);
    }
    public boolean hasScalationStartEvent(String id)
    {
        return org.semanticwb.process.model.ScalationStartEvent.ClassMgr.hasScalationStartEvent(id, this);
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

    public org.semanticwb.process.model.MultipleStartEvent getMultipleStartEvent(String id)
    {
        return org.semanticwb.process.model.MultipleStartEvent.ClassMgr.getMultipleStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MultipleStartEvent> listMultipleStartEvents()
    {
        return org.semanticwb.process.model.MultipleStartEvent.ClassMgr.listMultipleStartEvents(this);
    }

    public org.semanticwb.process.model.MultipleStartEvent createMultipleStartEvent(String id)
    {
        return org.semanticwb.process.model.MultipleStartEvent.ClassMgr.createMultipleStartEvent(id,this);
    }

    public org.semanticwb.process.model.MultipleStartEvent createMultipleStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MultipleStartEvent);
        return org.semanticwb.process.model.MultipleStartEvent.ClassMgr.createMultipleStartEvent(String.valueOf(id),this);
    } 

    public void removeMultipleStartEvent(String id)
    {
        org.semanticwb.process.model.MultipleStartEvent.ClassMgr.removeMultipleStartEvent(id, this);
    }
    public boolean hasMultipleStartEvent(String id)
    {
        return org.semanticwb.process.model.MultipleStartEvent.ClassMgr.hasMultipleStartEvent(id, this);
    }

    public org.semanticwb.process.model.CompensationTask getCompensationTask(String id)
    {
        return org.semanticwb.process.model.CompensationTask.ClassMgr.getCompensationTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CompensationTask> listCompensationTasks()
    {
        return org.semanticwb.process.model.CompensationTask.ClassMgr.listCompensationTasks(this);
    }

    public org.semanticwb.process.model.CompensationTask createCompensationTask(String id)
    {
        return org.semanticwb.process.model.CompensationTask.ClassMgr.createCompensationTask(id,this);
    }

    public org.semanticwb.process.model.CompensationTask createCompensationTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CompensationTask);
        return org.semanticwb.process.model.CompensationTask.ClassMgr.createCompensationTask(String.valueOf(id),this);
    } 

    public void removeCompensationTask(String id)
    {
        org.semanticwb.process.model.CompensationTask.ClassMgr.removeCompensationTask(id, this);
    }
    public boolean hasCompensationTask(String id)
    {
        return org.semanticwb.process.model.CompensationTask.ClassMgr.hasCompensationTask(id, this);
    }

    public org.semanticwb.process.model.MessageIntermediateCatchEvent getMessageIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.MessageIntermediateCatchEvent.ClassMgr.getMessageIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageIntermediateCatchEvent> listMessageIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.MessageIntermediateCatchEvent.ClassMgr.listMessageIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.MessageIntermediateCatchEvent createMessageIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.MessageIntermediateCatchEvent.ClassMgr.createMessageIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.MessageIntermediateCatchEvent createMessageIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MessageIntermediateCatchEvent);
        return org.semanticwb.process.model.MessageIntermediateCatchEvent.ClassMgr.createMessageIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeMessageIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.MessageIntermediateCatchEvent.ClassMgr.removeMessageIntermediateCatchEvent(id, this);
    }
    public boolean hasMessageIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.MessageIntermediateCatchEvent.ClassMgr.hasMessageIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.ProcessRule getProcessRule(String id)
    {
        return org.semanticwb.process.model.ProcessRule.ClassMgr.getProcessRule(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRules()
    {
        return org.semanticwb.process.model.ProcessRule.ClassMgr.listProcessRules(this);
    }

    public org.semanticwb.process.model.ProcessRule createProcessRule(String id)
    {
        return org.semanticwb.process.model.ProcessRule.ClassMgr.createProcessRule(id,this);
    }

    public void removeProcessRule(String id)
    {
        org.semanticwb.process.model.ProcessRule.ClassMgr.removeProcessRule(id, this);
    }
    public boolean hasProcessRule(String id)
    {
        return org.semanticwb.process.model.ProcessRule.ClassMgr.hasProcessRule(id, this);
    }

    public org.semanticwb.process.model.ReceiveTask getReceiveTask(String id)
    {
        return org.semanticwb.process.model.ReceiveTask.ClassMgr.getReceiveTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ReceiveTask> listReceiveTasks()
    {
        return org.semanticwb.process.model.ReceiveTask.ClassMgr.listReceiveTasks(this);
    }

    public org.semanticwb.process.model.ReceiveTask createReceiveTask(String id)
    {
        return org.semanticwb.process.model.ReceiveTask.ClassMgr.createReceiveTask(id,this);
    }

    public org.semanticwb.process.model.ReceiveTask createReceiveTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ReceiveTask);
        return org.semanticwb.process.model.ReceiveTask.ClassMgr.createReceiveTask(String.valueOf(id),this);
    } 

    public void removeReceiveTask(String id)
    {
        org.semanticwb.process.model.ReceiveTask.ClassMgr.removeReceiveTask(id, this);
    }
    public boolean hasReceiveTask(String id)
    {
        return org.semanticwb.process.model.ReceiveTask.ClassMgr.hasReceiveTask(id, this);
    }

    public org.semanticwb.process.model.Lane getLane(String id)
    {
        return org.semanticwb.process.model.Lane.ClassMgr.getLane(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Lane> listLanes()
    {
        return org.semanticwb.process.model.Lane.ClassMgr.listLanes(this);
    }

    public org.semanticwb.process.model.Lane createLane(String id)
    {
        return org.semanticwb.process.model.Lane.ClassMgr.createLane(id,this);
    }

    public org.semanticwb.process.model.Lane createLane()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Lane);
        return org.semanticwb.process.model.Lane.ClassMgr.createLane(String.valueOf(id),this);
    } 

    public void removeLane(String id)
    {
        org.semanticwb.process.model.Lane.ClassMgr.removeLane(id, this);
    }
    public boolean hasLane(String id)
    {
        return org.semanticwb.process.model.Lane.ClassMgr.hasLane(id, this);
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

    public org.semanticwb.process.model.TransactionSubProcess getTransactionSubProcess(String id)
    {
        return org.semanticwb.process.model.TransactionSubProcess.ClassMgr.getTransactionSubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcesses()
    {
        return org.semanticwb.process.model.TransactionSubProcess.ClassMgr.listTransactionSubProcesses(this);
    }

    public org.semanticwb.process.model.TransactionSubProcess createTransactionSubProcess(String id)
    {
        return org.semanticwb.process.model.TransactionSubProcess.ClassMgr.createTransactionSubProcess(id,this);
    }

    public org.semanticwb.process.model.TransactionSubProcess createTransactionSubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_TransactionSubProcess);
        return org.semanticwb.process.model.TransactionSubProcess.ClassMgr.createTransactionSubProcess(String.valueOf(id),this);
    } 

    public void removeTransactionSubProcess(String id)
    {
        org.semanticwb.process.model.TransactionSubProcess.ClassMgr.removeTransactionSubProcess(id, this);
    }
    public boolean hasTransactionSubProcess(String id)
    {
        return org.semanticwb.process.model.TransactionSubProcess.ClassMgr.hasTransactionSubProcess(id, this);
    }

    public org.semanticwb.process.model.MessageEndEvent getMessageEndEvent(String id)
    {
        return org.semanticwb.process.model.MessageEndEvent.ClassMgr.getMessageEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageEndEvent> listMessageEndEvents()
    {
        return org.semanticwb.process.model.MessageEndEvent.ClassMgr.listMessageEndEvents(this);
    }

    public org.semanticwb.process.model.MessageEndEvent createMessageEndEvent(String id)
    {
        return org.semanticwb.process.model.MessageEndEvent.ClassMgr.createMessageEndEvent(id,this);
    }

    public org.semanticwb.process.model.MessageEndEvent createMessageEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MessageEndEvent);
        return org.semanticwb.process.model.MessageEndEvent.ClassMgr.createMessageEndEvent(String.valueOf(id),this);
    } 

    public void removeMessageEndEvent(String id)
    {
        org.semanticwb.process.model.MessageEndEvent.ClassMgr.removeMessageEndEvent(id, this);
    }
    public boolean hasMessageEndEvent(String id)
    {
        return org.semanticwb.process.model.MessageEndEvent.ClassMgr.hasMessageEndEvent(id, this);
    }

    public org.semanticwb.process.model.LinkIntermediateThrowEvent getLinkIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.LinkIntermediateThrowEvent.ClassMgr.getLinkIntermediateThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LinkIntermediateThrowEvent> listLinkIntermediateThrowEvents()
    {
        return org.semanticwb.process.model.LinkIntermediateThrowEvent.ClassMgr.listLinkIntermediateThrowEvents(this);
    }

    public org.semanticwb.process.model.LinkIntermediateThrowEvent createLinkIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.LinkIntermediateThrowEvent.ClassMgr.createLinkIntermediateThrowEvent(id,this);
    }

    public org.semanticwb.process.model.LinkIntermediateThrowEvent createLinkIntermediateThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_LinkIntermediateThrowEvent);
        return org.semanticwb.process.model.LinkIntermediateThrowEvent.ClassMgr.createLinkIntermediateThrowEvent(String.valueOf(id),this);
    } 

    public void removeLinkIntermediateThrowEvent(String id)
    {
        org.semanticwb.process.model.LinkIntermediateThrowEvent.ClassMgr.removeLinkIntermediateThrowEvent(id, this);
    }
    public boolean hasLinkIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.LinkIntermediateThrowEvent.ClassMgr.hasLinkIntermediateThrowEvent(id, this);
    }

    public org.semanticwb.process.model.ErrorIntermediateCatchEvent getErrorIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ErrorIntermediateCatchEvent.ClassMgr.getErrorIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ErrorIntermediateCatchEvent> listErrorIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.ErrorIntermediateCatchEvent.ClassMgr.listErrorIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.ErrorIntermediateCatchEvent createErrorIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ErrorIntermediateCatchEvent.ClassMgr.createErrorIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.ErrorIntermediateCatchEvent createErrorIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ErrorIntermediateCatchEvent);
        return org.semanticwb.process.model.ErrorIntermediateCatchEvent.ClassMgr.createErrorIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeErrorIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.ErrorIntermediateCatchEvent.ClassMgr.removeErrorIntermediateCatchEvent(id, this);
    }
    public boolean hasErrorIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ErrorIntermediateCatchEvent.ClassMgr.hasErrorIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.SubProcessInstance getSubProcessInstance(String id)
    {
        return org.semanticwb.process.model.SubProcessInstance.ClassMgr.getSubProcessInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SubProcessInstance> listSubProcessInstances()
    {
        return org.semanticwb.process.model.SubProcessInstance.ClassMgr.listSubProcessInstances(this);
    }

    public org.semanticwb.process.model.SubProcessInstance createSubProcessInstance(String id)
    {
        return org.semanticwb.process.model.SubProcessInstance.ClassMgr.createSubProcessInstance(id,this);
    }

    public org.semanticwb.process.model.SubProcessInstance createSubProcessInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SubProcessInstance);
        return org.semanticwb.process.model.SubProcessInstance.ClassMgr.createSubProcessInstance(String.valueOf(id),this);
    } 

    public void removeSubProcessInstance(String id)
    {
        org.semanticwb.process.model.SubProcessInstance.ClassMgr.removeSubProcessInstance(id, this);
    }
    public boolean hasSubProcessInstance(String id)
    {
        return org.semanticwb.process.model.SubProcessInstance.ClassMgr.hasSubProcessInstance(id, this);
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

    public org.semanticwb.process.model.MultipleEndEvent getMultipleEndEvent(String id)
    {
        return org.semanticwb.process.model.MultipleEndEvent.ClassMgr.getMultipleEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MultipleEndEvent> listMultipleEndEvents()
    {
        return org.semanticwb.process.model.MultipleEndEvent.ClassMgr.listMultipleEndEvents(this);
    }

    public org.semanticwb.process.model.MultipleEndEvent createMultipleEndEvent(String id)
    {
        return org.semanticwb.process.model.MultipleEndEvent.ClassMgr.createMultipleEndEvent(id,this);
    }

    public org.semanticwb.process.model.MultipleEndEvent createMultipleEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MultipleEndEvent);
        return org.semanticwb.process.model.MultipleEndEvent.ClassMgr.createMultipleEndEvent(String.valueOf(id),this);
    } 

    public void removeMultipleEndEvent(String id)
    {
        org.semanticwb.process.model.MultipleEndEvent.ClassMgr.removeMultipleEndEvent(id, this);
    }
    public boolean hasMultipleEndEvent(String id)
    {
        return org.semanticwb.process.model.MultipleEndEvent.ClassMgr.hasMultipleEndEvent(id, this);
    }

    public org.semanticwb.process.model.ManualTask getManualTask(String id)
    {
        return org.semanticwb.process.model.ManualTask.ClassMgr.getManualTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ManualTask> listManualTasks()
    {
        return org.semanticwb.process.model.ManualTask.ClassMgr.listManualTasks(this);
    }

    public org.semanticwb.process.model.ManualTask createManualTask(String id)
    {
        return org.semanticwb.process.model.ManualTask.ClassMgr.createManualTask(id,this);
    }

    public org.semanticwb.process.model.ManualTask createManualTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ManualTask);
        return org.semanticwb.process.model.ManualTask.ClassMgr.createManualTask(String.valueOf(id),this);
    } 

    public void removeManualTask(String id)
    {
        org.semanticwb.process.model.ManualTask.ClassMgr.removeManualTask(id, this);
    }
    public boolean hasManualTask(String id)
    {
        return org.semanticwb.process.model.ManualTask.ClassMgr.hasManualTask(id, this);
    }

    public org.semanticwb.process.model.AnnotationArtifact getAnnotationArtifact(String id)
    {
        return org.semanticwb.process.model.AnnotationArtifact.ClassMgr.getAnnotationArtifact(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifacts()
    {
        return org.semanticwb.process.model.AnnotationArtifact.ClassMgr.listAnnotationArtifacts(this);
    }

    public org.semanticwb.process.model.AnnotationArtifact createAnnotationArtifact(String id)
    {
        return org.semanticwb.process.model.AnnotationArtifact.ClassMgr.createAnnotationArtifact(id,this);
    }

    public org.semanticwb.process.model.AnnotationArtifact createAnnotationArtifact()
    {
        long id=getSemanticObject().getModel().getCounter(swp_AnnotationArtifact);
        return org.semanticwb.process.model.AnnotationArtifact.ClassMgr.createAnnotationArtifact(String.valueOf(id),this);
    } 

    public void removeAnnotationArtifact(String id)
    {
        org.semanticwb.process.model.AnnotationArtifact.ClassMgr.removeAnnotationArtifact(id, this);
    }
    public boolean hasAnnotationArtifact(String id)
    {
        return org.semanticwb.process.model.AnnotationArtifact.ClassMgr.hasAnnotationArtifact(id, this);
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

    public org.semanticwb.process.model.ErrorEndEvent getErrorEndEvent(String id)
    {
        return org.semanticwb.process.model.ErrorEndEvent.ClassMgr.getErrorEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ErrorEndEvent> listErrorEndEvents()
    {
        return org.semanticwb.process.model.ErrorEndEvent.ClassMgr.listErrorEndEvents(this);
    }

    public org.semanticwb.process.model.ErrorEndEvent createErrorEndEvent(String id)
    {
        return org.semanticwb.process.model.ErrorEndEvent.ClassMgr.createErrorEndEvent(id,this);
    }

    public org.semanticwb.process.model.ErrorEndEvent createErrorEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ErrorEndEvent);
        return org.semanticwb.process.model.ErrorEndEvent.ClassMgr.createErrorEndEvent(String.valueOf(id),this);
    } 

    public void removeErrorEndEvent(String id)
    {
        org.semanticwb.process.model.ErrorEndEvent.ClassMgr.removeErrorEndEvent(id, this);
    }
    public boolean hasErrorEndEvent(String id)
    {
        return org.semanticwb.process.model.ErrorEndEvent.ClassMgr.hasErrorEndEvent(id, this);
    }

    public org.semanticwb.process.model.TerminationEndEvent getTerminationEndEvent(String id)
    {
        return org.semanticwb.process.model.TerminationEndEvent.ClassMgr.getTerminationEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.TerminationEndEvent> listTerminationEndEvents()
    {
        return org.semanticwb.process.model.TerminationEndEvent.ClassMgr.listTerminationEndEvents(this);
    }

    public org.semanticwb.process.model.TerminationEndEvent createTerminationEndEvent(String id)
    {
        return org.semanticwb.process.model.TerminationEndEvent.ClassMgr.createTerminationEndEvent(id,this);
    }

    public org.semanticwb.process.model.TerminationEndEvent createTerminationEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_TerminationEndEvent);
        return org.semanticwb.process.model.TerminationEndEvent.ClassMgr.createTerminationEndEvent(String.valueOf(id),this);
    } 

    public void removeTerminationEndEvent(String id)
    {
        org.semanticwb.process.model.TerminationEndEvent.ClassMgr.removeTerminationEndEvent(id, this);
    }
    public boolean hasTerminationEndEvent(String id)
    {
        return org.semanticwb.process.model.TerminationEndEvent.ClassMgr.hasTerminationEndEvent(id, this);
    }

    public org.semanticwb.process.model.LoopSubProcess getLoopSubProcess(String id)
    {
        return org.semanticwb.process.model.LoopSubProcess.ClassMgr.getLoopSubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcesses()
    {
        return org.semanticwb.process.model.LoopSubProcess.ClassMgr.listLoopSubProcesses(this);
    }

    public org.semanticwb.process.model.LoopSubProcess createLoopSubProcess(String id)
    {
        return org.semanticwb.process.model.LoopSubProcess.ClassMgr.createLoopSubProcess(id,this);
    }

    public org.semanticwb.process.model.LoopSubProcess createLoopSubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_LoopSubProcess);
        return org.semanticwb.process.model.LoopSubProcess.ClassMgr.createLoopSubProcess(String.valueOf(id),this);
    } 

    public void removeLoopSubProcess(String id)
    {
        org.semanticwb.process.model.LoopSubProcess.ClassMgr.removeLoopSubProcess(id, this);
    }
    public boolean hasLoopSubProcess(String id)
    {
        return org.semanticwb.process.model.LoopSubProcess.ClassMgr.hasLoopSubProcess(id, this);
    }

    public org.semanticwb.process.model.TimerIntermediateCatchEvent getTimerIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.TimerIntermediateCatchEvent.ClassMgr.getTimerIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.TimerIntermediateCatchEvent.ClassMgr.listTimerIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.TimerIntermediateCatchEvent createTimerIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.TimerIntermediateCatchEvent.ClassMgr.createTimerIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.TimerIntermediateCatchEvent createTimerIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_TimerIntermediateCatchEvent);
        return org.semanticwb.process.model.TimerIntermediateCatchEvent.ClassMgr.createTimerIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeTimerIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.TimerIntermediateCatchEvent.ClassMgr.removeTimerIntermediateCatchEvent(id, this);
    }
    public boolean hasTimerIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.TimerIntermediateCatchEvent.ClassMgr.hasTimerIntermediateCatchEvent(id, this);
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

    public org.semanticwb.process.model.Process getProcess(String id)
    {
        return org.semanticwb.process.model.Process.ClassMgr.getProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Process> listProcesses()
    {
        return org.semanticwb.process.model.Process.ClassMgr.listProcesses(this);
    }

    public org.semanticwb.process.model.Process createProcess(String id)
    {
        return org.semanticwb.process.model.Process.ClassMgr.createProcess(id,this);
    }

    public org.semanticwb.process.model.Process createProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Process);
        return org.semanticwb.process.model.Process.ClassMgr.createProcess(String.valueOf(id),this);
    } 

    public void removeProcess(String id)
    {
        org.semanticwb.process.model.Process.ClassMgr.removeProcess(id, this);
    }
    public boolean hasProcess(String id)
    {
        return org.semanticwb.process.model.Process.ClassMgr.hasProcess(id, this);
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

    public org.semanticwb.process.model.ParallelStartEvent getParallelStartEvent(String id)
    {
        return org.semanticwb.process.model.ParallelStartEvent.ClassMgr.getParallelStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ParallelStartEvent> listParallelStartEvents()
    {
        return org.semanticwb.process.model.ParallelStartEvent.ClassMgr.listParallelStartEvents(this);
    }

    public org.semanticwb.process.model.ParallelStartEvent createParallelStartEvent(String id)
    {
        return org.semanticwb.process.model.ParallelStartEvent.ClassMgr.createParallelStartEvent(id,this);
    }

    public org.semanticwb.process.model.ParallelStartEvent createParallelStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ParallelStartEvent);
        return org.semanticwb.process.model.ParallelStartEvent.ClassMgr.createParallelStartEvent(String.valueOf(id),this);
    } 

    public void removeParallelStartEvent(String id)
    {
        org.semanticwb.process.model.ParallelStartEvent.ClassMgr.removeParallelStartEvent(id, this);
    }
    public boolean hasParallelStartEvent(String id)
    {
        return org.semanticwb.process.model.ParallelStartEvent.ClassMgr.hasParallelStartEvent(id, this);
    }

    public org.semanticwb.process.model.DirectionalAssociation getDirectionalAssociation(String id)
    {
        return org.semanticwb.process.model.DirectionalAssociation.ClassMgr.getDirectionalAssociation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociations()
    {
        return org.semanticwb.process.model.DirectionalAssociation.ClassMgr.listDirectionalAssociations(this);
    }

    public org.semanticwb.process.model.DirectionalAssociation createDirectionalAssociation(String id)
    {
        return org.semanticwb.process.model.DirectionalAssociation.ClassMgr.createDirectionalAssociation(id,this);
    }

    public org.semanticwb.process.model.DirectionalAssociation createDirectionalAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DirectionalAssociation);
        return org.semanticwb.process.model.DirectionalAssociation.ClassMgr.createDirectionalAssociation(String.valueOf(id),this);
    } 

    public void removeDirectionalAssociation(String id)
    {
        org.semanticwb.process.model.DirectionalAssociation.ClassMgr.removeDirectionalAssociation(id, this);
    }
    public boolean hasDirectionalAssociation(String id)
    {
        return org.semanticwb.process.model.DirectionalAssociation.ClassMgr.hasDirectionalAssociation(id, this);
    }

    public org.semanticwb.process.model.MessageStartEvent getMessageStartEvent(String id)
    {
        return org.semanticwb.process.model.MessageStartEvent.ClassMgr.getMessageStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageStartEvent> listMessageStartEvents()
    {
        return org.semanticwb.process.model.MessageStartEvent.ClassMgr.listMessageStartEvents(this);
    }

    public org.semanticwb.process.model.MessageStartEvent createMessageStartEvent(String id)
    {
        return org.semanticwb.process.model.MessageStartEvent.ClassMgr.createMessageStartEvent(id,this);
    }

    public org.semanticwb.process.model.MessageStartEvent createMessageStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MessageStartEvent);
        return org.semanticwb.process.model.MessageStartEvent.ClassMgr.createMessageStartEvent(String.valueOf(id),this);
    } 

    public void removeMessageStartEvent(String id)
    {
        org.semanticwb.process.model.MessageStartEvent.ClassMgr.removeMessageStartEvent(id, this);
    }
    public boolean hasMessageStartEvent(String id)
    {
        return org.semanticwb.process.model.MessageStartEvent.ClassMgr.hasMessageStartEvent(id, this);
    }

    public org.semanticwb.process.model.Pool getPool(String id)
    {
        return org.semanticwb.process.model.Pool.ClassMgr.getPool(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Pool> listPools()
    {
        return org.semanticwb.process.model.Pool.ClassMgr.listPools(this);
    }

    public org.semanticwb.process.model.Pool createPool(String id)
    {
        return org.semanticwb.process.model.Pool.ClassMgr.createPool(id,this);
    }

    public org.semanticwb.process.model.Pool createPool()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Pool);
        return org.semanticwb.process.model.Pool.ClassMgr.createPool(String.valueOf(id),this);
    } 

    public void removePool(String id)
    {
        org.semanticwb.process.model.Pool.ClassMgr.removePool(id, this);
    }
    public boolean hasPool(String id)
    {
        return org.semanticwb.process.model.Pool.ClassMgr.hasPool(id, this);
    }

    public org.semanticwb.process.model.MessageIntermediateThrowEvent getMessageIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.MessageIntermediateThrowEvent.ClassMgr.getMessageIntermediateThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageIntermediateThrowEvent> listMessageIntermediateThrowEvents()
    {
        return org.semanticwb.process.model.MessageIntermediateThrowEvent.ClassMgr.listMessageIntermediateThrowEvents(this);
    }

    public org.semanticwb.process.model.MessageIntermediateThrowEvent createMessageIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.MessageIntermediateThrowEvent.ClassMgr.createMessageIntermediateThrowEvent(id,this);
    }

    public org.semanticwb.process.model.MessageIntermediateThrowEvent createMessageIntermediateThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MessageIntermediateThrowEvent);
        return org.semanticwb.process.model.MessageIntermediateThrowEvent.ClassMgr.createMessageIntermediateThrowEvent(String.valueOf(id),this);
    } 

    public void removeMessageIntermediateThrowEvent(String id)
    {
        org.semanticwb.process.model.MessageIntermediateThrowEvent.ClassMgr.removeMessageIntermediateThrowEvent(id, this);
    }
    public boolean hasMessageIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.MessageIntermediateThrowEvent.ClassMgr.hasMessageIntermediateThrowEvent(id, this);
    }

    public org.semanticwb.process.model.ScriptTask getScriptTask(String id)
    {
        return org.semanticwb.process.model.ScriptTask.ClassMgr.getScriptTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ScriptTask> listScriptTasks()
    {
        return org.semanticwb.process.model.ScriptTask.ClassMgr.listScriptTasks(this);
    }

    public org.semanticwb.process.model.ScriptTask createScriptTask(String id)
    {
        return org.semanticwb.process.model.ScriptTask.ClassMgr.createScriptTask(id,this);
    }

    public org.semanticwb.process.model.ScriptTask createScriptTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ScriptTask);
        return org.semanticwb.process.model.ScriptTask.ClassMgr.createScriptTask(String.valueOf(id),this);
    } 

    public void removeScriptTask(String id)
    {
        org.semanticwb.process.model.ScriptTask.ClassMgr.removeScriptTask(id, this);
    }
    public boolean hasScriptTask(String id)
    {
        return org.semanticwb.process.model.ScriptTask.ClassMgr.hasScriptTask(id, this);
    }

    public org.semanticwb.process.model.LoopTask getLoopTask(String id)
    {
        return org.semanticwb.process.model.LoopTask.ClassMgr.getLoopTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LoopTask> listLoopTasks()
    {
        return org.semanticwb.process.model.LoopTask.ClassMgr.listLoopTasks(this);
    }

    public org.semanticwb.process.model.LoopTask createLoopTask(String id)
    {
        return org.semanticwb.process.model.LoopTask.ClassMgr.createLoopTask(id,this);
    }

    public org.semanticwb.process.model.LoopTask createLoopTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_LoopTask);
        return org.semanticwb.process.model.LoopTask.ClassMgr.createLoopTask(String.valueOf(id),this);
    } 

    public void removeLoopTask(String id)
    {
        org.semanticwb.process.model.LoopTask.ClassMgr.removeLoopTask(id, this);
    }
    public boolean hasLoopTask(String id)
    {
        return org.semanticwb.process.model.LoopTask.ClassMgr.hasLoopTask(id, this);
    }

    public org.semanticwb.process.model.MultipleTask getMultipleTask(String id)
    {
        return org.semanticwb.process.model.MultipleTask.ClassMgr.getMultipleTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MultipleTask> listMultipleTasks()
    {
        return org.semanticwb.process.model.MultipleTask.ClassMgr.listMultipleTasks(this);
    }

    public org.semanticwb.process.model.MultipleTask createMultipleTask(String id)
    {
        return org.semanticwb.process.model.MultipleTask.ClassMgr.createMultipleTask(id,this);
    }

    public org.semanticwb.process.model.MultipleTask createMultipleTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MultipleTask);
        return org.semanticwb.process.model.MultipleTask.ClassMgr.createMultipleTask(String.valueOf(id),this);
    } 

    public void removeMultipleTask(String id)
    {
        org.semanticwb.process.model.MultipleTask.ClassMgr.removeMultipleTask(id, this);
    }
    public boolean hasMultipleTask(String id)
    {
        return org.semanticwb.process.model.MultipleTask.ClassMgr.hasMultipleTask(id, this);
    }

    public org.semanticwb.process.model.LinkIntermediateCatchEvent getLinkIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.LinkIntermediateCatchEvent.ClassMgr.getLinkIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LinkIntermediateCatchEvent> listLinkIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.LinkIntermediateCatchEvent.ClassMgr.listLinkIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.LinkIntermediateCatchEvent createLinkIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.LinkIntermediateCatchEvent.ClassMgr.createLinkIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.LinkIntermediateCatchEvent createLinkIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_LinkIntermediateCatchEvent);
        return org.semanticwb.process.model.LinkIntermediateCatchEvent.ClassMgr.createLinkIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeLinkIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.LinkIntermediateCatchEvent.ClassMgr.removeLinkIntermediateCatchEvent(id, this);
    }
    public boolean hasLinkIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.LinkIntermediateCatchEvent.ClassMgr.hasLinkIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.DataStoreArtifact getDataStoreArtifact(String id)
    {
        return org.semanticwb.process.model.DataStoreArtifact.ClassMgr.getDataStoreArtifact(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataStoreArtifact> listDataStoreArtifacts()
    {
        return org.semanticwb.process.model.DataStoreArtifact.ClassMgr.listDataStoreArtifacts(this);
    }

    public org.semanticwb.process.model.DataStoreArtifact createDataStoreArtifact(String id)
    {
        return org.semanticwb.process.model.DataStoreArtifact.ClassMgr.createDataStoreArtifact(id,this);
    }

    public org.semanticwb.process.model.DataStoreArtifact createDataStoreArtifact()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataStoreArtifact);
        return org.semanticwb.process.model.DataStoreArtifact.ClassMgr.createDataStoreArtifact(String.valueOf(id),this);
    } 

    public void removeDataStoreArtifact(String id)
    {
        org.semanticwb.process.model.DataStoreArtifact.ClassMgr.removeDataStoreArtifact(id, this);
    }
    public boolean hasDataStoreArtifact(String id)
    {
        return org.semanticwb.process.model.DataStoreArtifact.ClassMgr.hasDataStoreArtifact(id, this);
    }

    public org.semanticwb.process.model.CompensationIntermediateCatchEvent getCompensationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.CompensationIntermediateCatchEvent.ClassMgr.getCompensationIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateCatchEvent> listCompensationIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.CompensationIntermediateCatchEvent.ClassMgr.listCompensationIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.CompensationIntermediateCatchEvent createCompensationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.CompensationIntermediateCatchEvent.ClassMgr.createCompensationIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.CompensationIntermediateCatchEvent createCompensationIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CompensationIntermediateCatchEvent);
        return org.semanticwb.process.model.CompensationIntermediateCatchEvent.ClassMgr.createCompensationIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeCompensationIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.CompensationIntermediateCatchEvent.ClassMgr.removeCompensationIntermediateCatchEvent(id, this);
    }
    public boolean hasCompensationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.CompensationIntermediateCatchEvent.ClassMgr.hasCompensationIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.ScalationIntermediateCatchEvent getScalationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ScalationIntermediateCatchEvent.ClassMgr.getScalationIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ScalationIntermediateCatchEvent> listScalationIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.ScalationIntermediateCatchEvent.ClassMgr.listScalationIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.ScalationIntermediateCatchEvent createScalationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ScalationIntermediateCatchEvent.ClassMgr.createScalationIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.ScalationIntermediateCatchEvent createScalationIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ScalationIntermediateCatchEvent);
        return org.semanticwb.process.model.ScalationIntermediateCatchEvent.ClassMgr.createScalationIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeScalationIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.ScalationIntermediateCatchEvent.ClassMgr.removeScalationIntermediateCatchEvent(id, this);
    }
    public boolean hasScalationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ScalationIntermediateCatchEvent.ClassMgr.hasScalationIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.Gateway getGateway(String id)
    {
        return org.semanticwb.process.model.Gateway.ClassMgr.getGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Gateway> listGateways()
    {
        return org.semanticwb.process.model.Gateway.ClassMgr.listGateways(this);
    }

    public org.semanticwb.process.model.Gateway createGateway(String id)
    {
        return org.semanticwb.process.model.Gateway.ClassMgr.createGateway(id,this);
    }

    public org.semanticwb.process.model.Gateway createGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Gateway);
        return org.semanticwb.process.model.Gateway.ClassMgr.createGateway(String.valueOf(id),this);
    } 

    public void removeGateway(String id)
    {
        org.semanticwb.process.model.Gateway.ClassMgr.removeGateway(id, this);
    }
    public boolean hasGateway(String id)
    {
        return org.semanticwb.process.model.Gateway.ClassMgr.hasGateway(id, this);
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

    public org.semanticwb.process.model.GroupArtifact getGroupArtifact(String id)
    {
        return org.semanticwb.process.model.GroupArtifact.ClassMgr.getGroupArtifact(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifacts()
    {
        return org.semanticwb.process.model.GroupArtifact.ClassMgr.listGroupArtifacts(this);
    }

    public org.semanticwb.process.model.GroupArtifact createGroupArtifact(String id)
    {
        return org.semanticwb.process.model.GroupArtifact.ClassMgr.createGroupArtifact(id,this);
    }

    public org.semanticwb.process.model.GroupArtifact createGroupArtifact()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GroupArtifact);
        return org.semanticwb.process.model.GroupArtifact.ClassMgr.createGroupArtifact(String.valueOf(id),this);
    } 

    public void removeGroupArtifact(String id)
    {
        org.semanticwb.process.model.GroupArtifact.ClassMgr.removeGroupArtifact(id, this);
    }
    public boolean hasGroupArtifact(String id)
    {
        return org.semanticwb.process.model.GroupArtifact.ClassMgr.hasGroupArtifact(id, this);
    }

    public org.semanticwb.process.model.FlowNode getFlowNode(String id)
    {
        return org.semanticwb.process.model.FlowNode.ClassMgr.getFlowNode(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.FlowNode> listFlowNodes()
    {
        return org.semanticwb.process.model.FlowNode.ClassMgr.listFlowNodes(this);
    }

    public org.semanticwb.process.model.FlowNode createFlowNode(String id)
    {
        return org.semanticwb.process.model.FlowNode.ClassMgr.createFlowNode(id,this);
    }

    public void removeFlowNode(String id)
    {
        org.semanticwb.process.model.FlowNode.ClassMgr.removeFlowNode(id, this);
    }
    public boolean hasFlowNode(String id)
    {
        return org.semanticwb.process.model.FlowNode.ClassMgr.hasFlowNode(id, this);
    }

    public org.semanticwb.process.model.ParallelIntermediateCatchEvent getParallelIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ParallelIntermediateCatchEvent.ClassMgr.getParallelIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ParallelIntermediateCatchEvent> listParallelIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.ParallelIntermediateCatchEvent.ClassMgr.listParallelIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.ParallelIntermediateCatchEvent createParallelIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ParallelIntermediateCatchEvent.ClassMgr.createParallelIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.ParallelIntermediateCatchEvent createParallelIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ParallelIntermediateCatchEvent);
        return org.semanticwb.process.model.ParallelIntermediateCatchEvent.ClassMgr.createParallelIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeParallelIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.ParallelIntermediateCatchEvent.ClassMgr.removeParallelIntermediateCatchEvent(id, this);
    }
    public boolean hasParallelIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.ParallelIntermediateCatchEvent.ClassMgr.hasParallelIntermediateCatchEvent(id, this);
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

    public org.semanticwb.process.model.CancelationEndEvent getCancelationEndEvent(String id)
    {
        return org.semanticwb.process.model.CancelationEndEvent.ClassMgr.getCancelationEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CancelationEndEvent> listCancelationEndEvents()
    {
        return org.semanticwb.process.model.CancelationEndEvent.ClassMgr.listCancelationEndEvents(this);
    }

    public org.semanticwb.process.model.CancelationEndEvent createCancelationEndEvent(String id)
    {
        return org.semanticwb.process.model.CancelationEndEvent.ClassMgr.createCancelationEndEvent(id,this);
    }

    public org.semanticwb.process.model.CancelationEndEvent createCancelationEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CancelationEndEvent);
        return org.semanticwb.process.model.CancelationEndEvent.ClassMgr.createCancelationEndEvent(String.valueOf(id),this);
    } 

    public void removeCancelationEndEvent(String id)
    {
        org.semanticwb.process.model.CancelationEndEvent.ClassMgr.removeCancelationEndEvent(id, this);
    }
    public boolean hasCancelationEndEvent(String id)
    {
        return org.semanticwb.process.model.CancelationEndEvent.ClassMgr.hasCancelationEndEvent(id, this);
    }

    public org.semanticwb.process.model.ErrorStartEvent getErrorStartEvent(String id)
    {
        return org.semanticwb.process.model.ErrorStartEvent.ClassMgr.getErrorStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ErrorStartEvent> listErrorStartEvents()
    {
        return org.semanticwb.process.model.ErrorStartEvent.ClassMgr.listErrorStartEvents(this);
    }

    public org.semanticwb.process.model.ErrorStartEvent createErrorStartEvent(String id)
    {
        return org.semanticwb.process.model.ErrorStartEvent.ClassMgr.createErrorStartEvent(id,this);
    }

    public org.semanticwb.process.model.ErrorStartEvent createErrorStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ErrorStartEvent);
        return org.semanticwb.process.model.ErrorStartEvent.ClassMgr.createErrorStartEvent(String.valueOf(id),this);
    } 

    public void removeErrorStartEvent(String id)
    {
        org.semanticwb.process.model.ErrorStartEvent.ClassMgr.removeErrorStartEvent(id, this);
    }
    public boolean hasErrorStartEvent(String id)
    {
        return org.semanticwb.process.model.ErrorStartEvent.ClassMgr.hasErrorStartEvent(id, this);
    }

    public org.semanticwb.process.model.SignalIntermediateCatchEvent getSignalIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.SignalIntermediateCatchEvent.ClassMgr.getSignalIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SignalIntermediateCatchEvent> listSignalIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.SignalIntermediateCatchEvent.ClassMgr.listSignalIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.SignalIntermediateCatchEvent createSignalIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.SignalIntermediateCatchEvent.ClassMgr.createSignalIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.SignalIntermediateCatchEvent createSignalIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SignalIntermediateCatchEvent);
        return org.semanticwb.process.model.SignalIntermediateCatchEvent.ClassMgr.createSignalIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeSignalIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.SignalIntermediateCatchEvent.ClassMgr.removeSignalIntermediateCatchEvent(id, this);
    }
    public boolean hasSignalIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.SignalIntermediateCatchEvent.ClassMgr.hasSignalIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.ExclusiveIntermediateEventGateway getExclusiveIntermediateEventGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveIntermediateEventGateway.ClassMgr.getExclusiveIntermediateEventGateway(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ExclusiveIntermediateEventGateway> listExclusiveIntermediateEventGateways()
    {
        return org.semanticwb.process.model.ExclusiveIntermediateEventGateway.ClassMgr.listExclusiveIntermediateEventGateways(this);
    }

    public org.semanticwb.process.model.ExclusiveIntermediateEventGateway createExclusiveIntermediateEventGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveIntermediateEventGateway.ClassMgr.createExclusiveIntermediateEventGateway(id,this);
    }

    public org.semanticwb.process.model.ExclusiveIntermediateEventGateway createExclusiveIntermediateEventGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ExclusiveIntermediateEventGateway);
        return org.semanticwb.process.model.ExclusiveIntermediateEventGateway.ClassMgr.createExclusiveIntermediateEventGateway(String.valueOf(id),this);
    } 

    public void removeExclusiveIntermediateEventGateway(String id)
    {
        org.semanticwb.process.model.ExclusiveIntermediateEventGateway.ClassMgr.removeExclusiveIntermediateEventGateway(id, this);
    }
    public boolean hasExclusiveIntermediateEventGateway(String id)
    {
        return org.semanticwb.process.model.ExclusiveIntermediateEventGateway.ClassMgr.hasExclusiveIntermediateEventGateway(id, this);
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

    public org.semanticwb.process.model.CompensationIntermediateThrowEvent getCompensationIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.CompensationIntermediateThrowEvent.ClassMgr.getCompensationIntermediateThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CompensationIntermediateThrowEvent> listCompensationIntermediateThrowEvents()
    {
        return org.semanticwb.process.model.CompensationIntermediateThrowEvent.ClassMgr.listCompensationIntermediateThrowEvents(this);
    }

    public org.semanticwb.process.model.CompensationIntermediateThrowEvent createCompensationIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.CompensationIntermediateThrowEvent.ClassMgr.createCompensationIntermediateThrowEvent(id,this);
    }

    public org.semanticwb.process.model.CompensationIntermediateThrowEvent createCompensationIntermediateThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CompensationIntermediateThrowEvent);
        return org.semanticwb.process.model.CompensationIntermediateThrowEvent.ClassMgr.createCompensationIntermediateThrowEvent(String.valueOf(id),this);
    } 

    public void removeCompensationIntermediateThrowEvent(String id)
    {
        org.semanticwb.process.model.CompensationIntermediateThrowEvent.ClassMgr.removeCompensationIntermediateThrowEvent(id, this);
    }
    public boolean hasCompensationIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.CompensationIntermediateThrowEvent.ClassMgr.hasCompensationIntermediateThrowEvent(id, this);
    }

    public org.semanticwb.process.model.CancelationIntermediateCatchEvent getCancelationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.CancelationIntermediateCatchEvent.ClassMgr.getCancelationIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CancelationIntermediateCatchEvent> listCancelationIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.CancelationIntermediateCatchEvent.ClassMgr.listCancelationIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.CancelationIntermediateCatchEvent createCancelationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.CancelationIntermediateCatchEvent.ClassMgr.createCancelationIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.CancelationIntermediateCatchEvent createCancelationIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CancelationIntermediateCatchEvent);
        return org.semanticwb.process.model.CancelationIntermediateCatchEvent.ClassMgr.createCancelationIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeCancelationIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.CancelationIntermediateCatchEvent.ClassMgr.removeCancelationIntermediateCatchEvent(id, this);
    }
    public boolean hasCancelationIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.CancelationIntermediateCatchEvent.ClassMgr.hasCancelationIntermediateCatchEvent(id, this);
    }

    public org.semanticwb.process.model.CatchEvent getCatchEvent(String id)
    {
        return org.semanticwb.process.model.CatchEvent.ClassMgr.getCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CatchEvent> listCatchEvents()
    {
        return org.semanticwb.process.model.CatchEvent.ClassMgr.listCatchEvents(this);
    }

    public org.semanticwb.process.model.CatchEvent createCatchEvent(String id)
    {
        return org.semanticwb.process.model.CatchEvent.ClassMgr.createCatchEvent(id,this);
    }

    public void removeCatchEvent(String id)
    {
        org.semanticwb.process.model.CatchEvent.ClassMgr.removeCatchEvent(id, this);
    }
    public boolean hasCatchEvent(String id)
    {
        return org.semanticwb.process.model.CatchEvent.ClassMgr.hasCatchEvent(id, this);
    }

    public org.semanticwb.process.model.SendTask getSendTask(String id)
    {
        return org.semanticwb.process.model.SendTask.ClassMgr.getSendTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SendTask> listSendTasks()
    {
        return org.semanticwb.process.model.SendTask.ClassMgr.listSendTasks(this);
    }

    public org.semanticwb.process.model.SendTask createSendTask(String id)
    {
        return org.semanticwb.process.model.SendTask.ClassMgr.createSendTask(id,this);
    }

    public org.semanticwb.process.model.SendTask createSendTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SendTask);
        return org.semanticwb.process.model.SendTask.ClassMgr.createSendTask(String.valueOf(id),this);
    } 

    public void removeSendTask(String id)
    {
        org.semanticwb.process.model.SendTask.ClassMgr.removeSendTask(id, this);
    }
    public boolean hasSendTask(String id)
    {
        return org.semanticwb.process.model.SendTask.ClassMgr.hasSendTask(id, this);
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

    public org.semanticwb.process.model.CompensationEndEvent getCompensationEndEvent(String id)
    {
        return org.semanticwb.process.model.CompensationEndEvent.ClassMgr.getCompensationEndEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CompensationEndEvent> listCompensationEndEvents()
    {
        return org.semanticwb.process.model.CompensationEndEvent.ClassMgr.listCompensationEndEvents(this);
    }

    public org.semanticwb.process.model.CompensationEndEvent createCompensationEndEvent(String id)
    {
        return org.semanticwb.process.model.CompensationEndEvent.ClassMgr.createCompensationEndEvent(id,this);
    }

    public org.semanticwb.process.model.CompensationEndEvent createCompensationEndEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CompensationEndEvent);
        return org.semanticwb.process.model.CompensationEndEvent.ClassMgr.createCompensationEndEvent(String.valueOf(id),this);
    } 

    public void removeCompensationEndEvent(String id)
    {
        org.semanticwb.process.model.CompensationEndEvent.ClassMgr.removeCompensationEndEvent(id, this);
    }
    public boolean hasCompensationEndEvent(String id)
    {
        return org.semanticwb.process.model.CompensationEndEvent.ClassMgr.hasCompensationEndEvent(id, this);
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

    public org.semanticwb.process.model.TimerStartEvent getTimerStartEvent(String id)
    {
        return org.semanticwb.process.model.TimerStartEvent.ClassMgr.getTimerStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEvents()
    {
        return org.semanticwb.process.model.TimerStartEvent.ClassMgr.listTimerStartEvents(this);
    }

    public org.semanticwb.process.model.TimerStartEvent createTimerStartEvent(String id)
    {
        return org.semanticwb.process.model.TimerStartEvent.ClassMgr.createTimerStartEvent(id,this);
    }

    public org.semanticwb.process.model.TimerStartEvent createTimerStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_TimerStartEvent);
        return org.semanticwb.process.model.TimerStartEvent.ClassMgr.createTimerStartEvent(String.valueOf(id),this);
    } 

    public void removeTimerStartEvent(String id)
    {
        org.semanticwb.process.model.TimerStartEvent.ClassMgr.removeTimerStartEvent(id, this);
    }
    public boolean hasTimerStartEvent(String id)
    {
        return org.semanticwb.process.model.TimerStartEvent.ClassMgr.hasTimerStartEvent(id, this);
    }

    public org.semanticwb.process.model.GraphicalElement getGraphicalElement(String id)
    {
        return org.semanticwb.process.model.GraphicalElement.ClassMgr.getGraphicalElement(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements()
    {
        return org.semanticwb.process.model.GraphicalElement.ClassMgr.listGraphicalElements(this);
    }

    public org.semanticwb.process.model.GraphicalElement createGraphicalElement(String id)
    {
        return org.semanticwb.process.model.GraphicalElement.ClassMgr.createGraphicalElement(id,this);
    }

    public void removeGraphicalElement(String id)
    {
        org.semanticwb.process.model.GraphicalElement.ClassMgr.removeGraphicalElement(id, this);
    }
    public boolean hasGraphicalElement(String id)
    {
        return org.semanticwb.process.model.GraphicalElement.ClassMgr.hasGraphicalElement(id, this);
    }

    public org.semanticwb.process.model.SignalStartEvent getSignalStartEvent(String id)
    {
        return org.semanticwb.process.model.SignalStartEvent.ClassMgr.getSignalStartEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SignalStartEvent> listSignalStartEvents()
    {
        return org.semanticwb.process.model.SignalStartEvent.ClassMgr.listSignalStartEvents(this);
    }

    public org.semanticwb.process.model.SignalStartEvent createSignalStartEvent(String id)
    {
        return org.semanticwb.process.model.SignalStartEvent.ClassMgr.createSignalStartEvent(id,this);
    }

    public org.semanticwb.process.model.SignalStartEvent createSignalStartEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SignalStartEvent);
        return org.semanticwb.process.model.SignalStartEvent.ClassMgr.createSignalStartEvent(String.valueOf(id),this);
    } 

    public void removeSignalStartEvent(String id)
    {
        org.semanticwb.process.model.SignalStartEvent.ClassMgr.removeSignalStartEvent(id, this);
    }
    public boolean hasSignalStartEvent(String id)
    {
        return org.semanticwb.process.model.SignalStartEvent.ClassMgr.hasSignalStartEvent(id, this);
    }

    public org.semanticwb.process.model.Artifact getArtifact(String id)
    {
        return org.semanticwb.process.model.Artifact.ClassMgr.getArtifact(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifacts()
    {
        return org.semanticwb.process.model.Artifact.ClassMgr.listArtifacts(this);
    }

    public org.semanticwb.process.model.Artifact createArtifact(String id)
    {
        return org.semanticwb.process.model.Artifact.ClassMgr.createArtifact(id,this);
    }

    public org.semanticwb.process.model.Artifact createArtifact()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Artifact);
        return org.semanticwb.process.model.Artifact.ClassMgr.createArtifact(String.valueOf(id),this);
    } 

    public void removeArtifact(String id)
    {
        org.semanticwb.process.model.Artifact.ClassMgr.removeArtifact(id, this);
    }
    public boolean hasArtifact(String id)
    {
        return org.semanticwb.process.model.Artifact.ClassMgr.hasArtifact(id, this);
    }

    public org.semanticwb.process.model.FlowNodeInstance getFlowNodeInstance(String id)
    {
        return org.semanticwb.process.model.FlowNodeInstance.ClassMgr.getFlowNodeInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.FlowNodeInstance> listFlowNodeInstances()
    {
        return org.semanticwb.process.model.FlowNodeInstance.ClassMgr.listFlowNodeInstances(this);
    }

    public org.semanticwb.process.model.FlowNodeInstance createFlowNodeInstance(String id)
    {
        return org.semanticwb.process.model.FlowNodeInstance.ClassMgr.createFlowNodeInstance(id,this);
    }

    public org.semanticwb.process.model.FlowNodeInstance createFlowNodeInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swp_FlowNodeInstance);
        return org.semanticwb.process.model.FlowNodeInstance.ClassMgr.createFlowNodeInstance(String.valueOf(id),this);
    } 

    public void removeFlowNodeInstance(String id)
    {
        org.semanticwb.process.model.FlowNodeInstance.ClassMgr.removeFlowNodeInstance(id, this);
    }
    public boolean hasFlowNodeInstance(String id)
    {
        return org.semanticwb.process.model.FlowNodeInstance.ClassMgr.hasFlowNodeInstance(id, this);
    }

    public org.semanticwb.process.model.CompensationSubProcess getCompensationSubProcess(String id)
    {
        return org.semanticwb.process.model.CompensationSubProcess.ClassMgr.getCompensationSubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcesses()
    {
        return org.semanticwb.process.model.CompensationSubProcess.ClassMgr.listCompensationSubProcesses(this);
    }

    public org.semanticwb.process.model.CompensationSubProcess createCompensationSubProcess(String id)
    {
        return org.semanticwb.process.model.CompensationSubProcess.ClassMgr.createCompensationSubProcess(id,this);
    }

    public org.semanticwb.process.model.CompensationSubProcess createCompensationSubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CompensationSubProcess);
        return org.semanticwb.process.model.CompensationSubProcess.ClassMgr.createCompensationSubProcess(String.valueOf(id),this);
    } 

    public void removeCompensationSubProcess(String id)
    {
        org.semanticwb.process.model.CompensationSubProcess.ClassMgr.removeCompensationSubProcess(id, this);
    }
    public boolean hasCompensationSubProcess(String id)
    {
        return org.semanticwb.process.model.CompensationSubProcess.ClassMgr.hasCompensationSubProcess(id, this);
    }

    public org.semanticwb.process.model.MultipleIntermediateCatchEvent getMultipleIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.MultipleIntermediateCatchEvent.ClassMgr.getMultipleIntermediateCatchEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MultipleIntermediateCatchEvent> listMultipleIntermediateCatchEvents()
    {
        return org.semanticwb.process.model.MultipleIntermediateCatchEvent.ClassMgr.listMultipleIntermediateCatchEvents(this);
    }

    public org.semanticwb.process.model.MultipleIntermediateCatchEvent createMultipleIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.MultipleIntermediateCatchEvent.ClassMgr.createMultipleIntermediateCatchEvent(id,this);
    }

    public org.semanticwb.process.model.MultipleIntermediateCatchEvent createMultipleIntermediateCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MultipleIntermediateCatchEvent);
        return org.semanticwb.process.model.MultipleIntermediateCatchEvent.ClassMgr.createMultipleIntermediateCatchEvent(String.valueOf(id),this);
    } 

    public void removeMultipleIntermediateCatchEvent(String id)
    {
        org.semanticwb.process.model.MultipleIntermediateCatchEvent.ClassMgr.removeMultipleIntermediateCatchEvent(id, this);
    }
    public boolean hasMultipleIntermediateCatchEvent(String id)
    {
        return org.semanticwb.process.model.MultipleIntermediateCatchEvent.ClassMgr.hasMultipleIntermediateCatchEvent(id, this);
    }
}
