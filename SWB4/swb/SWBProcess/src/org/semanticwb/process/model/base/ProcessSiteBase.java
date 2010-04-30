package org.semanticwb.process.model.base;


public abstract class ProcessSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Undeleteable,org.semanticwb.model.Activeable,org.semanticwb.model.Indexable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Localeable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableNode
{
    public static final org.semanticwb.platform.SemanticClass swp_LaneSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneSet");
    public static final org.semanticwb.platform.SemanticClass swp_GlobalBusinessRuleTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalBusinessRuleTask");
    public static final org.semanticwb.platform.SemanticClass swp_DataObjectShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataObjectShape");
    public static final org.semanticwb.platform.SemanticClass swp_SignalEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SignalEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ActivityInstance");
    public static final org.semanticwb.platform.SemanticClass swp_AdHocSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AdHocSubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_Performer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Performer");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNLabel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNLabel");
    public static final org.semanticwb.platform.SemanticClass swp_CategoryValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CategoryValue");
    public static final org.semanticwb.platform.SemanticClass swp_BoundaryEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BoundaryEvent");
    public static final org.semanticwb.platform.SemanticClass swp_DataStoreReference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStoreReference");
    public static final org.semanticwb.platform.SemanticClass swp_Escalation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Escalation");
    public static final org.semanticwb.platform.SemanticClass swp_Operation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Operation");
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographySubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographySubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNInstance");
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographyTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyTask");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexBehaviorDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexBehaviorDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_GlobalTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalTask");
    public static final org.semanticwb.platform.SemanticClass swp_ConditionalFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConditionalFlow");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNConnector");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexGateway");
    public static final org.semanticwb.platform.SemanticClass swp_Artifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Artifact");
    public static final org.semanticwb.platform.SemanticClass swp_CallConversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CallConversation");
    public static final org.semanticwb.platform.SemanticClass swp_PartnerRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PartnerRole");
    public static final org.semanticwb.platform.SemanticClass swp_Conversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conversation");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexGatewayInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexGatewayInstance");
    public static final org.semanticwb.platform.SemanticClass swp_ActivityResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ActivityResource");
    public static final org.semanticwb.platform.SemanticClass swp_GlobalScriptTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalScriptTask");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNElement");
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticClass swp_Message=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Message");
    public static final org.semanticwb.platform.SemanticClass swp_Monitoring=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Monitoring");
    public static final org.semanticwb.platform.SemanticClass swp_CallChoreographyActivity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CallChoreographyActivity");
    public static final org.semanticwb.platform.SemanticClass swp_EmbeddedSubProcessShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EmbeddedSubProcessShape");
    public static final org.semanticwb.platform.SemanticClass swp_ActivityShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ActivityShape");
    public static final org.semanticwb.platform.SemanticClass swp_StandardLoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#StandardLoopCharacteristics");
    public static final org.semanticwb.platform.SemanticClass swp_GlobalUserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalUserTask");
    public static final org.semanticwb.platform.SemanticClass swp_LoopActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LoopActivityInstance");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationSubscription");
    public static final org.semanticwb.platform.SemanticClass swp_CallConversationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CallConversationShape");
    public static final org.semanticwb.platform.SemanticClass swp_SequenceFlowConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlowConnector");
    public static final org.semanticwb.platform.SemanticClass swp_Relationship=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Relationship");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_DefaultFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DefaultFlow");
    public static final org.semanticwb.platform.SemanticClass swp_InclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InclusiveGateway");
    public static final org.semanticwb.platform.SemanticClass swp_EventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantMultiplicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantMultiplicity");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNShape");
    public static final org.semanticwb.platform.SemanticClass swp_DataOutputShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutputShape");
    public static final org.semanticwb.platform.SemanticClass swp_LinkEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LinkEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_SubConversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SubConversation");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationKey");
    public static final org.semanticwb.platform.SemanticClass swp_LoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LoopCharacteristics");
    public static final org.semanticwb.platform.SemanticClass swp_MessageEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_FormalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FormalExpression");
    public static final org.semanticwb.platform.SemanticClass swp_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Task");
    public static final org.semanticwb.platform.SemanticClass swp_GroupShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GroupShape");
    public static final org.semanticwb.platform.SemanticClass swp_BusinessRuleTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BusinessRuleTask");
    public static final org.semanticwb.platform.SemanticClass swp_Extension=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Extension");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionAttributeValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeValue");
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographyCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyCompartment");
    public static final org.semanticwb.platform.SemanticClass swp_Documentation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Documentation");
    public static final org.semanticwb.platform.SemanticClass swp_LaneCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#LaneCompartment");
    public static final org.semanticwb.platform.SemanticClass swp_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Category");
    public static final org.semanticwb.platform.SemanticClass swp_TerminateEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TerminateEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_Lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Lane");
    public static final org.semanticwb.platform.SemanticClass swp_ResourceAssignmentExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ResourceAssignmentExpression");
    public static final org.semanticwb.platform.SemanticClass swp_EndPoint=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EndPoint");
    public static final org.semanticwb.platform.SemanticClass swp_ParallelGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParallelGateway");
    public static final org.semanticwb.platform.SemanticClass swp_MultiInstanceActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MultiInstanceActivityInstance");
    public static final org.semanticwb.platform.SemanticClass swp_GatewayShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GatewayShape");
    public static final org.semanticwb.platform.SemanticClass swp_UserTaskInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#UserTaskInstance");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInterface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessInterface");
    public static final org.semanticwb.platform.SemanticClass swp_DataInputShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputShape");
    public static final org.semanticwb.platform.SemanticClass swp_IntermediateThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#IntermediateThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationPropertyRetrievalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationPropertyRetrievalExpression");
    public static final org.semanticwb.platform.SemanticClass swp_Communication=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Communication");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlow");
    public static final org.semanticwb.platform.SemanticClass swp_InputOutputBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputOutputBinding");
    public static final org.semanticwb.platform.SemanticClass swp_TextAnnotationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TextAnnotationShape");
    public static final org.semanticwb.platform.SemanticClass swp_GlobalCommunication=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalCommunication");
    public static final org.semanticwb.platform.SemanticClass swp_EmbeddedSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EmbeddedSubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_Choreography=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Choreography");
    public static final org.semanticwb.platform.SemanticClass swp_HumanPerformer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#HumanPerformer");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNBaseElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNBaseElement");
    public static final org.semanticwb.platform.SemanticClass swp_Participant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNChoreographyDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNChoreographyDiagram");
    public static final org.semanticwb.platform.SemanticClass swp_PoolCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PoolCompartment");
    public static final org.semanticwb.platform.SemanticClass swp_FlowElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowElement");
    public static final org.semanticwb.platform.SemanticClass swp_DataOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutput");
    public static final org.semanticwb.platform.SemanticClass swp_ConversationLinkConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationLinkConnector");
    public static final org.semanticwb.platform.SemanticClass swp_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Activity");
    public static final org.semanticwb.platform.SemanticClass swp_TimerEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_Group=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Group");
    public static final org.semanticwb.platform.SemanticClass swp_ImplicitThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ImplicitThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ErrorEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ErrorEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_ExclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExclusiveGateway");
    public static final org.semanticwb.platform.SemanticClass swp_DataStoreShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStoreShape");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlowAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlowAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_GlobalManualTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalManualTask");
    public static final org.semanticwb.platform.SemanticClass swp_CancelEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CancelEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_NormalFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalFlow");
    public static final org.semanticwb.platform.SemanticClass swp_CompensateEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CompensateEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_DataInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInput");
    public static final org.semanticwb.platform.SemanticClass swp_PotentialOwner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PotentialOwner");
    public static final org.semanticwb.platform.SemanticClass swp_CallActivity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CallActivity");
    public static final org.semanticwb.platform.SemanticClass swp_Gateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Gateway");
    public static final org.semanticwb.platform.SemanticClass swp_ConditionEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConditionEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_Error=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Error");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNStyleDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNStyleDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_SubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SubProcess");
    public static final org.semanticwb.platform.SemanticClass swp_EventBasedGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventBasedGateway");
    public static final org.semanticwb.platform.SemanticClass swp_Transaction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Transaction");
    public static final org.semanticwb.platform.SemanticClass swp_DataStore=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStore");
    public static final org.semanticwb.platform.SemanticClass swp_StartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#StartEvent");
    public static final org.semanticwb.platform.SemanticClass swp_DataInputAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_EventShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventShape");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowNodeShape");
    public static final org.semanticwb.platform.SemanticClass swp_Collaboration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Collaboration");
    public static final org.semanticwb.platform.SemanticClass swp_EscalateEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EscalateEventDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNDiagram");
    public static final org.semanticwb.platform.SemanticClass swp_ConversationCommunicationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationCommunicationShape");
    public static final org.semanticwb.platform.SemanticClass swp_DataAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_CalledSubProcessShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CalledSubProcessShape");
    public static final org.semanticwb.platform.SemanticClass swp_UserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#UserTask");
    public static final org.semanticwb.platform.SemanticClass swp_CommunicationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CommunicationShape");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionAttributeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_ItemDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ItemDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNCollaborationDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNCollaborationDiagram");
    public static final org.semanticwb.platform.SemanticClass swp_Auditing=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Auditing");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessInstance");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNConversationDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNConversationDiagram");
    public static final org.semanticwb.platform.SemanticClass swp_AssociationConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AssociationConnector");
    public static final org.semanticwb.platform.SemanticClass swp_ConversationAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_OutputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");
    public static final org.semanticwb.platform.SemanticClass swp_DataObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataObject");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNProcessDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNProcessDiagram");
    public static final org.semanticwb.platform.SemanticClass swp_Pool=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Pool");
    public static final org.semanticwb.platform.SemanticClass swp_Assignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Assignment");
    public static final org.semanticwb.platform.SemanticClass swp_ManualTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ManualTask");
    public static final org.semanticwb.platform.SemanticClass swp_MessageShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageShape");
    public static final org.semanticwb.platform.SemanticClass swp_DataOutputAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutputAssociation");
    public static final org.semanticwb.platform.SemanticClass swp_EndEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EndEvent");
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowNode");
    public static final org.semanticwb.platform.SemanticClass swp_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Event");
    public static final org.semanticwb.platform.SemanticClass swp_PartnerEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PartnerEntity");
    public static final org.semanticwb.platform.SemanticClass swp_SendTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SendTask");
    public static final org.semanticwb.platform.SemanticClass swp_ConversationNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationNode");
    public static final org.semanticwb.platform.SemanticClass swp_InputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputSet");
    public static final org.semanticwb.platform.SemanticClass swp_ParticipantMultiplicityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantMultiplicityInstance");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNNode");
    public static final org.semanticwb.platform.SemanticClass swp_ReceiveTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ReceiveTask");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_MultiInstanceLoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MultiInstanceLoopCharacteristics");
    public static final org.semanticwb.platform.SemanticClass swp_ScriptTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ScriptTask");
    public static final org.semanticwb.platform.SemanticClass swp_SubConversationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SubConversationShape");
    public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlow");
    public static final org.semanticwb.platform.SemanticClass swp_TextAnnotation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TextAnnotation");
    public static final org.semanticwb.platform.SemanticClass swp_Signal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Signal");
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographyActivityShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyActivityShape");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationPropertyBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationPropertyBinding");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationProperty");
    public static final org.semanticwb.platform.SemanticClass swp_GlobalChoreographyTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GlobalChoreographyTask");
    public static final org.semanticwb.platform.SemanticClass swp_Definition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Definition");
    public static final org.semanticwb.platform.SemanticClass swp_IntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#IntermediateCatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ChoreographyActivity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ChoreographyActivity");
    public static final org.semanticwb.platform.SemanticClass swp_ExceptionFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExceptionFlow");
    public static final org.semanticwb.platform.SemanticClass swp_InputOutputSpecification=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputOutputSpecification");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNCompartment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNCompartment");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessImport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessImport");
    public static final org.semanticwb.platform.SemanticClass swp_ThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ThrowEvent");
    public static final org.semanticwb.platform.SemanticClass swp_DataAssociationConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataAssociationConnector");
    public static final org.semanticwb.platform.SemanticClass swp_CompensationFlowConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CompensationFlowConnector");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Process");
    public static final org.semanticwb.platform.SemanticClass swp_MessageFlowConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlowConnector");
    public static final org.semanticwb.platform.SemanticClass swp_ServiceTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ServiceTask");
    public static final org.semanticwb.platform.SemanticClass swp_CatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CatchEvent");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessSite");

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

    public org.semanticwb.process.model.LaneSet getLaneSet(String id)
    {
        return org.semanticwb.process.model.LaneSet.ClassMgr.getLaneSet(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LaneSet> listLaneSets()
    {
        return org.semanticwb.process.model.LaneSet.ClassMgr.listLaneSets(this);
    }

    public org.semanticwb.process.model.LaneSet createLaneSet(String id)
    {
        return org.semanticwb.process.model.LaneSet.ClassMgr.createLaneSet(id,this);
    }

    public org.semanticwb.process.model.LaneSet createLaneSet()
    {
        long id=getSemanticObject().getModel().getCounter(swp_LaneSet);
        return org.semanticwb.process.model.LaneSet.ClassMgr.createLaneSet(String.valueOf(id),this);
    } 

    public void removeLaneSet(String id)
    {
        org.semanticwb.process.model.LaneSet.ClassMgr.removeLaneSet(id, this);
    }
    public boolean hasLaneSet(String id)
    {
        return org.semanticwb.process.model.LaneSet.ClassMgr.hasLaneSet(id, this);
    }

    public org.semanticwb.process.model.GlobalBusinessRuleTask getGlobalBusinessRuleTask(String id)
    {
        return org.semanticwb.process.model.GlobalBusinessRuleTask.ClassMgr.getGlobalBusinessRuleTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GlobalBusinessRuleTask> listGlobalBusinessRuleTasks()
    {
        return org.semanticwb.process.model.GlobalBusinessRuleTask.ClassMgr.listGlobalBusinessRuleTasks(this);
    }

    public org.semanticwb.process.model.GlobalBusinessRuleTask createGlobalBusinessRuleTask(String id)
    {
        return org.semanticwb.process.model.GlobalBusinessRuleTask.ClassMgr.createGlobalBusinessRuleTask(id,this);
    }

    public org.semanticwb.process.model.GlobalBusinessRuleTask createGlobalBusinessRuleTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GlobalBusinessRuleTask);
        return org.semanticwb.process.model.GlobalBusinessRuleTask.ClassMgr.createGlobalBusinessRuleTask(String.valueOf(id),this);
    } 

    public void removeGlobalBusinessRuleTask(String id)
    {
        org.semanticwb.process.model.GlobalBusinessRuleTask.ClassMgr.removeGlobalBusinessRuleTask(id, this);
    }
    public boolean hasGlobalBusinessRuleTask(String id)
    {
        return org.semanticwb.process.model.GlobalBusinessRuleTask.ClassMgr.hasGlobalBusinessRuleTask(id, this);
    }

    public org.semanticwb.process.model.DataObjectShape getDataObjectShape(String id)
    {
        return org.semanticwb.process.model.DataObjectShape.ClassMgr.getDataObjectShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataObjectShape> listDataObjectShapes()
    {
        return org.semanticwb.process.model.DataObjectShape.ClassMgr.listDataObjectShapes(this);
    }

    public org.semanticwb.process.model.DataObjectShape createDataObjectShape(String id)
    {
        return org.semanticwb.process.model.DataObjectShape.ClassMgr.createDataObjectShape(id,this);
    }

    public org.semanticwb.process.model.DataObjectShape createDataObjectShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataObjectShape);
        return org.semanticwb.process.model.DataObjectShape.ClassMgr.createDataObjectShape(String.valueOf(id),this);
    } 

    public void removeDataObjectShape(String id)
    {
        org.semanticwb.process.model.DataObjectShape.ClassMgr.removeDataObjectShape(id, this);
    }
    public boolean hasDataObjectShape(String id)
    {
        return org.semanticwb.process.model.DataObjectShape.ClassMgr.hasDataObjectShape(id, this);
    }

    public org.semanticwb.process.model.SignalEventDefinition getSignalEventDefinition(String id)
    {
        return org.semanticwb.process.model.SignalEventDefinition.ClassMgr.getSignalEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitions()
    {
        return org.semanticwb.process.model.SignalEventDefinition.ClassMgr.listSignalEventDefinitions(this);
    }

    public org.semanticwb.process.model.SignalEventDefinition createSignalEventDefinition(String id)
    {
        return org.semanticwb.process.model.SignalEventDefinition.ClassMgr.createSignalEventDefinition(id,this);
    }

    public org.semanticwb.process.model.SignalEventDefinition createSignalEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SignalEventDefinition);
        return org.semanticwb.process.model.SignalEventDefinition.ClassMgr.createSignalEventDefinition(String.valueOf(id),this);
    } 

    public void removeSignalEventDefinition(String id)
    {
        org.semanticwb.process.model.SignalEventDefinition.ClassMgr.removeSignalEventDefinition(id, this);
    }
    public boolean hasSignalEventDefinition(String id)
    {
        return org.semanticwb.process.model.SignalEventDefinition.ClassMgr.hasSignalEventDefinition(id, this);
    }

    public org.semanticwb.process.model.ActivityInstance getActivityInstance(String id)
    {
        return org.semanticwb.process.model.ActivityInstance.ClassMgr.getActivityInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ActivityInstance> listActivityInstances()
    {
        return org.semanticwb.process.model.ActivityInstance.ClassMgr.listActivityInstances(this);
    }

    public org.semanticwb.process.model.ActivityInstance createActivityInstance(String id)
    {
        return org.semanticwb.process.model.ActivityInstance.ClassMgr.createActivityInstance(id,this);
    }

    public void removeActivityInstance(String id)
    {
        org.semanticwb.process.model.ActivityInstance.ClassMgr.removeActivityInstance(id, this);
    }
    public boolean hasActivityInstance(String id)
    {
        return org.semanticwb.process.model.ActivityInstance.ClassMgr.hasActivityInstance(id, this);
    }

    public org.semanticwb.process.model.AdHocSubProcess getAdHocSubProcess(String id)
    {
        return org.semanticwb.process.model.AdHocSubProcess.ClassMgr.getAdHocSubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcesses()
    {
        return org.semanticwb.process.model.AdHocSubProcess.ClassMgr.listAdHocSubProcesses(this);
    }

    public org.semanticwb.process.model.AdHocSubProcess createAdHocSubProcess(String id)
    {
        return org.semanticwb.process.model.AdHocSubProcess.ClassMgr.createAdHocSubProcess(id,this);
    }

    public org.semanticwb.process.model.AdHocSubProcess createAdHocSubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_AdHocSubProcess);
        return org.semanticwb.process.model.AdHocSubProcess.ClassMgr.createAdHocSubProcess(String.valueOf(id),this);
    } 

    public void removeAdHocSubProcess(String id)
    {
        org.semanticwb.process.model.AdHocSubProcess.ClassMgr.removeAdHocSubProcess(id, this);
    }
    public boolean hasAdHocSubProcess(String id)
    {
        return org.semanticwb.process.model.AdHocSubProcess.ClassMgr.hasAdHocSubProcess(id, this);
    }

    public org.semanticwb.process.model.Performer getPerformer(String id)
    {
        return org.semanticwb.process.model.Performer.ClassMgr.getPerformer(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Performer> listPerformers()
    {
        return org.semanticwb.process.model.Performer.ClassMgr.listPerformers(this);
    }

    public org.semanticwb.process.model.Performer createPerformer(String id)
    {
        return org.semanticwb.process.model.Performer.ClassMgr.createPerformer(id,this);
    }

    public org.semanticwb.process.model.Performer createPerformer()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Performer);
        return org.semanticwb.process.model.Performer.ClassMgr.createPerformer(String.valueOf(id),this);
    } 

    public void removePerformer(String id)
    {
        org.semanticwb.process.model.Performer.ClassMgr.removePerformer(id, this);
    }
    public boolean hasPerformer(String id)
    {
        return org.semanticwb.process.model.Performer.ClassMgr.hasPerformer(id, this);
    }

    public org.semanticwb.process.model.BPMNLabel getBPMNLabel(String id)
    {
        return org.semanticwb.process.model.BPMNLabel.ClassMgr.getBPMNLabel(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNLabel> listBPMNLabels()
    {
        return org.semanticwb.process.model.BPMNLabel.ClassMgr.listBPMNLabels(this);
    }

    public org.semanticwb.process.model.BPMNLabel createBPMNLabel(String id)
    {
        return org.semanticwb.process.model.BPMNLabel.ClassMgr.createBPMNLabel(id,this);
    }

    public org.semanticwb.process.model.BPMNLabel createBPMNLabel()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNLabel);
        return org.semanticwb.process.model.BPMNLabel.ClassMgr.createBPMNLabel(String.valueOf(id),this);
    } 

    public void removeBPMNLabel(String id)
    {
        org.semanticwb.process.model.BPMNLabel.ClassMgr.removeBPMNLabel(id, this);
    }
    public boolean hasBPMNLabel(String id)
    {
        return org.semanticwb.process.model.BPMNLabel.ClassMgr.hasBPMNLabel(id, this);
    }

    public org.semanticwb.process.model.CategoryValue getCategoryValue(String id)
    {
        return org.semanticwb.process.model.CategoryValue.ClassMgr.getCategoryValue(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValues()
    {
        return org.semanticwb.process.model.CategoryValue.ClassMgr.listCategoryValues(this);
    }

    public org.semanticwb.process.model.CategoryValue createCategoryValue(String id)
    {
        return org.semanticwb.process.model.CategoryValue.ClassMgr.createCategoryValue(id,this);
    }

    public org.semanticwb.process.model.CategoryValue createCategoryValue()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CategoryValue);
        return org.semanticwb.process.model.CategoryValue.ClassMgr.createCategoryValue(String.valueOf(id),this);
    } 

    public void removeCategoryValue(String id)
    {
        org.semanticwb.process.model.CategoryValue.ClassMgr.removeCategoryValue(id, this);
    }
    public boolean hasCategoryValue(String id)
    {
        return org.semanticwb.process.model.CategoryValue.ClassMgr.hasCategoryValue(id, this);
    }

    public org.semanticwb.process.model.BoundaryEvent getBoundaryEvent(String id)
    {
        return org.semanticwb.process.model.BoundaryEvent.ClassMgr.getBoundaryEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BoundaryEvent> listBoundaryEvents()
    {
        return org.semanticwb.process.model.BoundaryEvent.ClassMgr.listBoundaryEvents(this);
    }

    public org.semanticwb.process.model.BoundaryEvent createBoundaryEvent(String id)
    {
        return org.semanticwb.process.model.BoundaryEvent.ClassMgr.createBoundaryEvent(id,this);
    }

    public org.semanticwb.process.model.BoundaryEvent createBoundaryEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BoundaryEvent);
        return org.semanticwb.process.model.BoundaryEvent.ClassMgr.createBoundaryEvent(String.valueOf(id),this);
    } 

    public void removeBoundaryEvent(String id)
    {
        org.semanticwb.process.model.BoundaryEvent.ClassMgr.removeBoundaryEvent(id, this);
    }
    public boolean hasBoundaryEvent(String id)
    {
        return org.semanticwb.process.model.BoundaryEvent.ClassMgr.hasBoundaryEvent(id, this);
    }

    public org.semanticwb.process.model.DataStoreReference getDataStoreReference(String id)
    {
        return org.semanticwb.process.model.DataStoreReference.ClassMgr.getDataStoreReference(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataStoreReference> listDataStoreReferences()
    {
        return org.semanticwb.process.model.DataStoreReference.ClassMgr.listDataStoreReferences(this);
    }

    public org.semanticwb.process.model.DataStoreReference createDataStoreReference(String id)
    {
        return org.semanticwb.process.model.DataStoreReference.ClassMgr.createDataStoreReference(id,this);
    }

    public org.semanticwb.process.model.DataStoreReference createDataStoreReference()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataStoreReference);
        return org.semanticwb.process.model.DataStoreReference.ClassMgr.createDataStoreReference(String.valueOf(id),this);
    } 

    public void removeDataStoreReference(String id)
    {
        org.semanticwb.process.model.DataStoreReference.ClassMgr.removeDataStoreReference(id, this);
    }
    public boolean hasDataStoreReference(String id)
    {
        return org.semanticwb.process.model.DataStoreReference.ClassMgr.hasDataStoreReference(id, this);
    }

    public org.semanticwb.process.model.Escalation getEscalation(String id)
    {
        return org.semanticwb.process.model.Escalation.ClassMgr.getEscalation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Escalation> listEscalations()
    {
        return org.semanticwb.process.model.Escalation.ClassMgr.listEscalations(this);
    }

    public org.semanticwb.process.model.Escalation createEscalation(String id)
    {
        return org.semanticwb.process.model.Escalation.ClassMgr.createEscalation(id,this);
    }

    public void removeEscalation(String id)
    {
        org.semanticwb.process.model.Escalation.ClassMgr.removeEscalation(id, this);
    }
    public boolean hasEscalation(String id)
    {
        return org.semanticwb.process.model.Escalation.ClassMgr.hasEscalation(id, this);
    }

    public org.semanticwb.process.model.Operation getOperation(String id)
    {
        return org.semanticwb.process.model.Operation.ClassMgr.getOperation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Operation> listOperations()
    {
        return org.semanticwb.process.model.Operation.ClassMgr.listOperations(this);
    }

    public org.semanticwb.process.model.Operation createOperation(String id)
    {
        return org.semanticwb.process.model.Operation.ClassMgr.createOperation(id,this);
    }

    public org.semanticwb.process.model.Operation createOperation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Operation);
        return org.semanticwb.process.model.Operation.ClassMgr.createOperation(String.valueOf(id),this);
    } 

    public void removeOperation(String id)
    {
        org.semanticwb.process.model.Operation.ClassMgr.removeOperation(id, this);
    }
    public boolean hasOperation(String id)
    {
        return org.semanticwb.process.model.Operation.ClassMgr.hasOperation(id, this);
    }

    public org.semanticwb.process.model.ChoreographySubProcess getChoreographySubProcess(String id)
    {
        return org.semanticwb.process.model.ChoreographySubProcess.ClassMgr.getChoreographySubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ChoreographySubProcess> listChoreographySubProcesses()
    {
        return org.semanticwb.process.model.ChoreographySubProcess.ClassMgr.listChoreographySubProcesses(this);
    }

    public org.semanticwb.process.model.ChoreographySubProcess createChoreographySubProcess(String id)
    {
        return org.semanticwb.process.model.ChoreographySubProcess.ClassMgr.createChoreographySubProcess(id,this);
    }

    public org.semanticwb.process.model.ChoreographySubProcess createChoreographySubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ChoreographySubProcess);
        return org.semanticwb.process.model.ChoreographySubProcess.ClassMgr.createChoreographySubProcess(String.valueOf(id),this);
    } 

    public void removeChoreographySubProcess(String id)
    {
        org.semanticwb.process.model.ChoreographySubProcess.ClassMgr.removeChoreographySubProcess(id, this);
    }
    public boolean hasChoreographySubProcess(String id)
    {
        return org.semanticwb.process.model.ChoreographySubProcess.ClassMgr.hasChoreographySubProcess(id, this);
    }

    public org.semanticwb.process.model.BPMNInstance getBPMNInstance(String id)
    {
        return org.semanticwb.process.model.BPMNInstance.ClassMgr.getBPMNInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNInstance> listBPMNInstances()
    {
        return org.semanticwb.process.model.BPMNInstance.ClassMgr.listBPMNInstances(this);
    }

    public org.semanticwb.process.model.BPMNInstance createBPMNInstance(String id)
    {
        return org.semanticwb.process.model.BPMNInstance.ClassMgr.createBPMNInstance(id,this);
    }

    public void removeBPMNInstance(String id)
    {
        org.semanticwb.process.model.BPMNInstance.ClassMgr.removeBPMNInstance(id, this);
    }
    public boolean hasBPMNInstance(String id)
    {
        return org.semanticwb.process.model.BPMNInstance.ClassMgr.hasBPMNInstance(id, this);
    }

    public org.semanticwb.process.model.ChoreographyTask getChoreographyTask(String id)
    {
        return org.semanticwb.process.model.ChoreographyTask.ClassMgr.getChoreographyTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ChoreographyTask> listChoreographyTasks()
    {
        return org.semanticwb.process.model.ChoreographyTask.ClassMgr.listChoreographyTasks(this);
    }

    public org.semanticwb.process.model.ChoreographyTask createChoreographyTask(String id)
    {
        return org.semanticwb.process.model.ChoreographyTask.ClassMgr.createChoreographyTask(id,this);
    }

    public org.semanticwb.process.model.ChoreographyTask createChoreographyTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ChoreographyTask);
        return org.semanticwb.process.model.ChoreographyTask.ClassMgr.createChoreographyTask(String.valueOf(id),this);
    } 

    public void removeChoreographyTask(String id)
    {
        org.semanticwb.process.model.ChoreographyTask.ClassMgr.removeChoreographyTask(id, this);
    }
    public boolean hasChoreographyTask(String id)
    {
        return org.semanticwb.process.model.ChoreographyTask.ClassMgr.hasChoreographyTask(id, this);
    }

    public org.semanticwb.process.model.ComplexBehaviorDefinition getComplexBehaviorDefinition(String id)
    {
        return org.semanticwb.process.model.ComplexBehaviorDefinition.ClassMgr.getComplexBehaviorDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitions()
    {
        return org.semanticwb.process.model.ComplexBehaviorDefinition.ClassMgr.listComplexBehaviorDefinitions(this);
    }

    public org.semanticwb.process.model.ComplexBehaviorDefinition createComplexBehaviorDefinition(String id)
    {
        return org.semanticwb.process.model.ComplexBehaviorDefinition.ClassMgr.createComplexBehaviorDefinition(id,this);
    }

    public org.semanticwb.process.model.ComplexBehaviorDefinition createComplexBehaviorDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ComplexBehaviorDefinition);
        return org.semanticwb.process.model.ComplexBehaviorDefinition.ClassMgr.createComplexBehaviorDefinition(String.valueOf(id),this);
    } 

    public void removeComplexBehaviorDefinition(String id)
    {
        org.semanticwb.process.model.ComplexBehaviorDefinition.ClassMgr.removeComplexBehaviorDefinition(id, this);
    }
    public boolean hasComplexBehaviorDefinition(String id)
    {
        return org.semanticwb.process.model.ComplexBehaviorDefinition.ClassMgr.hasComplexBehaviorDefinition(id, this);
    }

    public org.semanticwb.process.model.GlobalTask getGlobalTask(String id)
    {
        return org.semanticwb.process.model.GlobalTask.ClassMgr.getGlobalTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GlobalTask> listGlobalTasks()
    {
        return org.semanticwb.process.model.GlobalTask.ClassMgr.listGlobalTasks(this);
    }

    public org.semanticwb.process.model.GlobalTask createGlobalTask(String id)
    {
        return org.semanticwb.process.model.GlobalTask.ClassMgr.createGlobalTask(id,this);
    }

    public org.semanticwb.process.model.GlobalTask createGlobalTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GlobalTask);
        return org.semanticwb.process.model.GlobalTask.ClassMgr.createGlobalTask(String.valueOf(id),this);
    } 

    public void removeGlobalTask(String id)
    {
        org.semanticwb.process.model.GlobalTask.ClassMgr.removeGlobalTask(id, this);
    }
    public boolean hasGlobalTask(String id)
    {
        return org.semanticwb.process.model.GlobalTask.ClassMgr.hasGlobalTask(id, this);
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

    public org.semanticwb.process.model.BPMNConnector getBPMNConnector(String id)
    {
        return org.semanticwb.process.model.BPMNConnector.ClassMgr.getBPMNConnector(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectors()
    {
        return org.semanticwb.process.model.BPMNConnector.ClassMgr.listBPMNConnectors(this);
    }

    public org.semanticwb.process.model.BPMNConnector createBPMNConnector(String id)
    {
        return org.semanticwb.process.model.BPMNConnector.ClassMgr.createBPMNConnector(id,this);
    }

    public void removeBPMNConnector(String id)
    {
        org.semanticwb.process.model.BPMNConnector.ClassMgr.removeBPMNConnector(id, this);
    }
    public boolean hasBPMNConnector(String id)
    {
        return org.semanticwb.process.model.BPMNConnector.ClassMgr.hasBPMNConnector(id, this);
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

    public org.semanticwb.process.model.CallConversation getCallConversation(String id)
    {
        return org.semanticwb.process.model.CallConversation.ClassMgr.getCallConversation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CallConversation> listCallConversations()
    {
        return org.semanticwb.process.model.CallConversation.ClassMgr.listCallConversations(this);
    }

    public org.semanticwb.process.model.CallConversation createCallConversation(String id)
    {
        return org.semanticwb.process.model.CallConversation.ClassMgr.createCallConversation(id,this);
    }

    public org.semanticwb.process.model.CallConversation createCallConversation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CallConversation);
        return org.semanticwb.process.model.CallConversation.ClassMgr.createCallConversation(String.valueOf(id),this);
    } 

    public void removeCallConversation(String id)
    {
        org.semanticwb.process.model.CallConversation.ClassMgr.removeCallConversation(id, this);
    }
    public boolean hasCallConversation(String id)
    {
        return org.semanticwb.process.model.CallConversation.ClassMgr.hasCallConversation(id, this);
    }

    public org.semanticwb.process.model.PartnerRole getPartnerRole(String id)
    {
        return org.semanticwb.process.model.PartnerRole.ClassMgr.getPartnerRole(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.PartnerRole> listPartnerRoles()
    {
        return org.semanticwb.process.model.PartnerRole.ClassMgr.listPartnerRoles(this);
    }

    public org.semanticwb.process.model.PartnerRole createPartnerRole(String id)
    {
        return org.semanticwb.process.model.PartnerRole.ClassMgr.createPartnerRole(id,this);
    }

    public org.semanticwb.process.model.PartnerRole createPartnerRole()
    {
        long id=getSemanticObject().getModel().getCounter(swp_PartnerRole);
        return org.semanticwb.process.model.PartnerRole.ClassMgr.createPartnerRole(String.valueOf(id),this);
    } 

    public void removePartnerRole(String id)
    {
        org.semanticwb.process.model.PartnerRole.ClassMgr.removePartnerRole(id, this);
    }
    public boolean hasPartnerRole(String id)
    {
        return org.semanticwb.process.model.PartnerRole.ClassMgr.hasPartnerRole(id, this);
    }

    public org.semanticwb.process.model.Conversation getConversation(String id)
    {
        return org.semanticwb.process.model.Conversation.ClassMgr.getConversation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Conversation> listConversations()
    {
        return org.semanticwb.process.model.Conversation.ClassMgr.listConversations(this);
    }

    public org.semanticwb.process.model.Conversation createConversation(String id)
    {
        return org.semanticwb.process.model.Conversation.ClassMgr.createConversation(id,this);
    }

    public org.semanticwb.process.model.Conversation createConversation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Conversation);
        return org.semanticwb.process.model.Conversation.ClassMgr.createConversation(String.valueOf(id),this);
    } 

    public void removeConversation(String id)
    {
        org.semanticwb.process.model.Conversation.ClassMgr.removeConversation(id, this);
    }
    public boolean hasConversation(String id)
    {
        return org.semanticwb.process.model.Conversation.ClassMgr.hasConversation(id, this);
    }

    public org.semanticwb.process.model.ComplexGatewayInstance getComplexGatewayInstance(String id)
    {
        return org.semanticwb.process.model.ComplexGatewayInstance.ClassMgr.getComplexGatewayInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ComplexGatewayInstance> listComplexGatewayInstances()
    {
        return org.semanticwb.process.model.ComplexGatewayInstance.ClassMgr.listComplexGatewayInstances(this);
    }

    public org.semanticwb.process.model.ComplexGatewayInstance createComplexGatewayInstance(String id)
    {
        return org.semanticwb.process.model.ComplexGatewayInstance.ClassMgr.createComplexGatewayInstance(id,this);
    }

    public void removeComplexGatewayInstance(String id)
    {
        org.semanticwb.process.model.ComplexGatewayInstance.ClassMgr.removeComplexGatewayInstance(id, this);
    }
    public boolean hasComplexGatewayInstance(String id)
    {
        return org.semanticwb.process.model.ComplexGatewayInstance.ClassMgr.hasComplexGatewayInstance(id, this);
    }

    public org.semanticwb.process.model.ActivityResource getActivityResource(String id)
    {
        return org.semanticwb.process.model.ActivityResource.ClassMgr.getActivityResource(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResources()
    {
        return org.semanticwb.process.model.ActivityResource.ClassMgr.listActivityResources(this);
    }

    public org.semanticwb.process.model.ActivityResource createActivityResource(String id)
    {
        return org.semanticwb.process.model.ActivityResource.ClassMgr.createActivityResource(id,this);
    }

    public org.semanticwb.process.model.ActivityResource createActivityResource()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ActivityResource);
        return org.semanticwb.process.model.ActivityResource.ClassMgr.createActivityResource(String.valueOf(id),this);
    } 

    public void removeActivityResource(String id)
    {
        org.semanticwb.process.model.ActivityResource.ClassMgr.removeActivityResource(id, this);
    }
    public boolean hasActivityResource(String id)
    {
        return org.semanticwb.process.model.ActivityResource.ClassMgr.hasActivityResource(id, this);
    }

    public org.semanticwb.process.model.GlobalScriptTask getGlobalScriptTask(String id)
    {
        return org.semanticwb.process.model.GlobalScriptTask.ClassMgr.getGlobalScriptTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GlobalScriptTask> listGlobalScriptTasks()
    {
        return org.semanticwb.process.model.GlobalScriptTask.ClassMgr.listGlobalScriptTasks(this);
    }

    public org.semanticwb.process.model.GlobalScriptTask createGlobalScriptTask(String id)
    {
        return org.semanticwb.process.model.GlobalScriptTask.ClassMgr.createGlobalScriptTask(id,this);
    }

    public org.semanticwb.process.model.GlobalScriptTask createGlobalScriptTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GlobalScriptTask);
        return org.semanticwb.process.model.GlobalScriptTask.ClassMgr.createGlobalScriptTask(String.valueOf(id),this);
    } 

    public void removeGlobalScriptTask(String id)
    {
        org.semanticwb.process.model.GlobalScriptTask.ClassMgr.removeGlobalScriptTask(id, this);
    }
    public boolean hasGlobalScriptTask(String id)
    {
        return org.semanticwb.process.model.GlobalScriptTask.ClassMgr.hasGlobalScriptTask(id, this);
    }

    public org.semanticwb.process.model.BPMNElement getBPMNElement(String id)
    {
        return org.semanticwb.process.model.BPMNElement.ClassMgr.getBPMNElement(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNElement> listBPMNElements()
    {
        return org.semanticwb.process.model.BPMNElement.ClassMgr.listBPMNElements(this);
    }

    public org.semanticwb.process.model.BPMNElement createBPMNElement(String id)
    {
        return org.semanticwb.process.model.BPMNElement.ClassMgr.createBPMNElement(id,this);
    }

    public void removeBPMNElement(String id)
    {
        org.semanticwb.process.model.BPMNElement.ClassMgr.removeBPMNElement(id, this);
    }
    public boolean hasBPMNElement(String id)
    {
        return org.semanticwb.process.model.BPMNElement.ClassMgr.hasBPMNElement(id, this);
    }

    public org.semanticwb.process.model.Expression getExpression(String id)
    {
        return org.semanticwb.process.model.Expression.ClassMgr.getExpression(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Expression> listExpressions()
    {
        return org.semanticwb.process.model.Expression.ClassMgr.listExpressions(this);
    }

    public org.semanticwb.process.model.Expression createExpression(String id)
    {
        return org.semanticwb.process.model.Expression.ClassMgr.createExpression(id,this);
    }

    public org.semanticwb.process.model.Expression createExpression()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Expression);
        return org.semanticwb.process.model.Expression.ClassMgr.createExpression(String.valueOf(id),this);
    } 

    public void removeExpression(String id)
    {
        org.semanticwb.process.model.Expression.ClassMgr.removeExpression(id, this);
    }
    public boolean hasExpression(String id)
    {
        return org.semanticwb.process.model.Expression.ClassMgr.hasExpression(id, this);
    }

    public org.semanticwb.process.model.Message getMessage(String id)
    {
        return org.semanticwb.process.model.Message.ClassMgr.getMessage(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Message> listMessages()
    {
        return org.semanticwb.process.model.Message.ClassMgr.listMessages(this);
    }

    public org.semanticwb.process.model.Message createMessage(String id)
    {
        return org.semanticwb.process.model.Message.ClassMgr.createMessage(id,this);
    }

    public org.semanticwb.process.model.Message createMessage()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Message);
        return org.semanticwb.process.model.Message.ClassMgr.createMessage(String.valueOf(id),this);
    } 

    public void removeMessage(String id)
    {
        org.semanticwb.process.model.Message.ClassMgr.removeMessage(id, this);
    }
    public boolean hasMessage(String id)
    {
        return org.semanticwb.process.model.Message.ClassMgr.hasMessage(id, this);
    }

    public org.semanticwb.process.model.Monitoring getMonitoring(String id)
    {
        return org.semanticwb.process.model.Monitoring.ClassMgr.getMonitoring(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Monitoring> listMonitorings()
    {
        return org.semanticwb.process.model.Monitoring.ClassMgr.listMonitorings(this);
    }

    public org.semanticwb.process.model.Monitoring createMonitoring(String id)
    {
        return org.semanticwb.process.model.Monitoring.ClassMgr.createMonitoring(id,this);
    }

    public org.semanticwb.process.model.Monitoring createMonitoring()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Monitoring);
        return org.semanticwb.process.model.Monitoring.ClassMgr.createMonitoring(String.valueOf(id),this);
    } 

    public void removeMonitoring(String id)
    {
        org.semanticwb.process.model.Monitoring.ClassMgr.removeMonitoring(id, this);
    }
    public boolean hasMonitoring(String id)
    {
        return org.semanticwb.process.model.Monitoring.ClassMgr.hasMonitoring(id, this);
    }

    public org.semanticwb.process.model.CallChoreographyActivity getCallChoreographyActivity(String id)
    {
        return org.semanticwb.process.model.CallChoreographyActivity.ClassMgr.getCallChoreographyActivity(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CallChoreographyActivity> listCallChoreographyActivities()
    {
        return org.semanticwb.process.model.CallChoreographyActivity.ClassMgr.listCallChoreographyActivities(this);
    }

    public org.semanticwb.process.model.CallChoreographyActivity createCallChoreographyActivity(String id)
    {
        return org.semanticwb.process.model.CallChoreographyActivity.ClassMgr.createCallChoreographyActivity(id,this);
    }

    public org.semanticwb.process.model.CallChoreographyActivity createCallChoreographyActivity()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CallChoreographyActivity);
        return org.semanticwb.process.model.CallChoreographyActivity.ClassMgr.createCallChoreographyActivity(String.valueOf(id),this);
    } 

    public void removeCallChoreographyActivity(String id)
    {
        org.semanticwb.process.model.CallChoreographyActivity.ClassMgr.removeCallChoreographyActivity(id, this);
    }
    public boolean hasCallChoreographyActivity(String id)
    {
        return org.semanticwb.process.model.CallChoreographyActivity.ClassMgr.hasCallChoreographyActivity(id, this);
    }

    public org.semanticwb.process.model.EmbeddedSubProcessShape getEmbeddedSubProcessShape(String id)
    {
        return org.semanticwb.process.model.EmbeddedSubProcessShape.ClassMgr.getEmbeddedSubProcessShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcessShape> listEmbeddedSubProcessShapes()
    {
        return org.semanticwb.process.model.EmbeddedSubProcessShape.ClassMgr.listEmbeddedSubProcessShapes(this);
    }

    public org.semanticwb.process.model.EmbeddedSubProcessShape createEmbeddedSubProcessShape(String id)
    {
        return org.semanticwb.process.model.EmbeddedSubProcessShape.ClassMgr.createEmbeddedSubProcessShape(id,this);
    }

    public org.semanticwb.process.model.EmbeddedSubProcessShape createEmbeddedSubProcessShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_EmbeddedSubProcessShape);
        return org.semanticwb.process.model.EmbeddedSubProcessShape.ClassMgr.createEmbeddedSubProcessShape(String.valueOf(id),this);
    } 

    public void removeEmbeddedSubProcessShape(String id)
    {
        org.semanticwb.process.model.EmbeddedSubProcessShape.ClassMgr.removeEmbeddedSubProcessShape(id, this);
    }
    public boolean hasEmbeddedSubProcessShape(String id)
    {
        return org.semanticwb.process.model.EmbeddedSubProcessShape.ClassMgr.hasEmbeddedSubProcessShape(id, this);
    }

    public org.semanticwb.process.model.ActivityShape getActivityShape(String id)
    {
        return org.semanticwb.process.model.ActivityShape.ClassMgr.getActivityShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ActivityShape> listActivityShapes()
    {
        return org.semanticwb.process.model.ActivityShape.ClassMgr.listActivityShapes(this);
    }

    public org.semanticwb.process.model.ActivityShape createActivityShape(String id)
    {
        return org.semanticwb.process.model.ActivityShape.ClassMgr.createActivityShape(id,this);
    }

    public org.semanticwb.process.model.ActivityShape createActivityShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ActivityShape);
        return org.semanticwb.process.model.ActivityShape.ClassMgr.createActivityShape(String.valueOf(id),this);
    } 

    public void removeActivityShape(String id)
    {
        org.semanticwb.process.model.ActivityShape.ClassMgr.removeActivityShape(id, this);
    }
    public boolean hasActivityShape(String id)
    {
        return org.semanticwb.process.model.ActivityShape.ClassMgr.hasActivityShape(id, this);
    }

    public org.semanticwb.process.model.StandardLoopCharacteristics getStandardLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.StandardLoopCharacteristics.ClassMgr.getStandardLoopCharacteristics(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.StandardLoopCharacteristics> listStandardLoopCharacteristicses()
    {
        return org.semanticwb.process.model.StandardLoopCharacteristics.ClassMgr.listStandardLoopCharacteristicses(this);
    }

    public org.semanticwb.process.model.StandardLoopCharacteristics createStandardLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.StandardLoopCharacteristics.ClassMgr.createStandardLoopCharacteristics(id,this);
    }

    public org.semanticwb.process.model.StandardLoopCharacteristics createStandardLoopCharacteristics()
    {
        long id=getSemanticObject().getModel().getCounter(swp_StandardLoopCharacteristics);
        return org.semanticwb.process.model.StandardLoopCharacteristics.ClassMgr.createStandardLoopCharacteristics(String.valueOf(id),this);
    } 

    public void removeStandardLoopCharacteristics(String id)
    {
        org.semanticwb.process.model.StandardLoopCharacteristics.ClassMgr.removeStandardLoopCharacteristics(id, this);
    }
    public boolean hasStandardLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.StandardLoopCharacteristics.ClassMgr.hasStandardLoopCharacteristics(id, this);
    }

    public org.semanticwb.process.model.GlobalUserTask getGlobalUserTask(String id)
    {
        return org.semanticwb.process.model.GlobalUserTask.ClassMgr.getGlobalUserTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GlobalUserTask> listGlobalUserTasks()
    {
        return org.semanticwb.process.model.GlobalUserTask.ClassMgr.listGlobalUserTasks(this);
    }

    public org.semanticwb.process.model.GlobalUserTask createGlobalUserTask(String id)
    {
        return org.semanticwb.process.model.GlobalUserTask.ClassMgr.createGlobalUserTask(id,this);
    }

    public org.semanticwb.process.model.GlobalUserTask createGlobalUserTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GlobalUserTask);
        return org.semanticwb.process.model.GlobalUserTask.ClassMgr.createGlobalUserTask(String.valueOf(id),this);
    } 

    public void removeGlobalUserTask(String id)
    {
        org.semanticwb.process.model.GlobalUserTask.ClassMgr.removeGlobalUserTask(id, this);
    }
    public boolean hasGlobalUserTask(String id)
    {
        return org.semanticwb.process.model.GlobalUserTask.ClassMgr.hasGlobalUserTask(id, this);
    }

    public org.semanticwb.process.model.LoopActivityInstance getLoopActivityInstance(String id)
    {
        return org.semanticwb.process.model.LoopActivityInstance.ClassMgr.getLoopActivityInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LoopActivityInstance> listLoopActivityInstances()
    {
        return org.semanticwb.process.model.LoopActivityInstance.ClassMgr.listLoopActivityInstances(this);
    }

    public org.semanticwb.process.model.LoopActivityInstance createLoopActivityInstance(String id)
    {
        return org.semanticwb.process.model.LoopActivityInstance.ClassMgr.createLoopActivityInstance(id,this);
    }

    public void removeLoopActivityInstance(String id)
    {
        org.semanticwb.process.model.LoopActivityInstance.ClassMgr.removeLoopActivityInstance(id, this);
    }
    public boolean hasLoopActivityInstance(String id)
    {
        return org.semanticwb.process.model.LoopActivityInstance.ClassMgr.hasLoopActivityInstance(id, this);
    }

    public org.semanticwb.process.model.CorrelationSubscription getCorrelationSubscription(String id)
    {
        return org.semanticwb.process.model.CorrelationSubscription.ClassMgr.getCorrelationSubscription(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CorrelationSubscription> listCorrelationSubscriptions()
    {
        return org.semanticwb.process.model.CorrelationSubscription.ClassMgr.listCorrelationSubscriptions(this);
    }

    public org.semanticwb.process.model.CorrelationSubscription createCorrelationSubscription(String id)
    {
        return org.semanticwb.process.model.CorrelationSubscription.ClassMgr.createCorrelationSubscription(id,this);
    }

    public org.semanticwb.process.model.CorrelationSubscription createCorrelationSubscription()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CorrelationSubscription);
        return org.semanticwb.process.model.CorrelationSubscription.ClassMgr.createCorrelationSubscription(String.valueOf(id),this);
    } 

    public void removeCorrelationSubscription(String id)
    {
        org.semanticwb.process.model.CorrelationSubscription.ClassMgr.removeCorrelationSubscription(id, this);
    }
    public boolean hasCorrelationSubscription(String id)
    {
        return org.semanticwb.process.model.CorrelationSubscription.ClassMgr.hasCorrelationSubscription(id, this);
    }

    public org.semanticwb.process.model.CallConversationShape getCallConversationShape(String id)
    {
        return org.semanticwb.process.model.CallConversationShape.ClassMgr.getCallConversationShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CallConversationShape> listCallConversationShapes()
    {
        return org.semanticwb.process.model.CallConversationShape.ClassMgr.listCallConversationShapes(this);
    }

    public org.semanticwb.process.model.CallConversationShape createCallConversationShape(String id)
    {
        return org.semanticwb.process.model.CallConversationShape.ClassMgr.createCallConversationShape(id,this);
    }

    public org.semanticwb.process.model.CallConversationShape createCallConversationShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CallConversationShape);
        return org.semanticwb.process.model.CallConversationShape.ClassMgr.createCallConversationShape(String.valueOf(id),this);
    } 

    public void removeCallConversationShape(String id)
    {
        org.semanticwb.process.model.CallConversationShape.ClassMgr.removeCallConversationShape(id, this);
    }
    public boolean hasCallConversationShape(String id)
    {
        return org.semanticwb.process.model.CallConversationShape.ClassMgr.hasCallConversationShape(id, this);
    }

    public org.semanticwb.process.model.SequenceFlowConnector getSequenceFlowConnector(String id)
    {
        return org.semanticwb.process.model.SequenceFlowConnector.ClassMgr.getSequenceFlowConnector(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SequenceFlowConnector> listSequenceFlowConnectors()
    {
        return org.semanticwb.process.model.SequenceFlowConnector.ClassMgr.listSequenceFlowConnectors(this);
    }

    public org.semanticwb.process.model.SequenceFlowConnector createSequenceFlowConnector(String id)
    {
        return org.semanticwb.process.model.SequenceFlowConnector.ClassMgr.createSequenceFlowConnector(id,this);
    }

    public void removeSequenceFlowConnector(String id)
    {
        org.semanticwb.process.model.SequenceFlowConnector.ClassMgr.removeSequenceFlowConnector(id, this);
    }
    public boolean hasSequenceFlowConnector(String id)
    {
        return org.semanticwb.process.model.SequenceFlowConnector.ClassMgr.hasSequenceFlowConnector(id, this);
    }

    public org.semanticwb.process.model.Relationship getRelationship(String id)
    {
        return org.semanticwb.process.model.Relationship.ClassMgr.getRelationship(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Relationship> listRelationships()
    {
        return org.semanticwb.process.model.Relationship.ClassMgr.listRelationships(this);
    }

    public org.semanticwb.process.model.Relationship createRelationship(String id)
    {
        return org.semanticwb.process.model.Relationship.ClassMgr.createRelationship(id,this);
    }

    public org.semanticwb.process.model.Relationship createRelationship()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Relationship);
        return org.semanticwb.process.model.Relationship.ClassMgr.createRelationship(String.valueOf(id),this);
    } 

    public void removeRelationship(String id)
    {
        org.semanticwb.process.model.Relationship.ClassMgr.removeRelationship(id, this);
    }
    public boolean hasRelationship(String id)
    {
        return org.semanticwb.process.model.Relationship.ClassMgr.hasRelationship(id, this);
    }

    public org.semanticwb.process.model.ParticipantAssociation getParticipantAssociation(String id)
    {
        return org.semanticwb.process.model.ParticipantAssociation.ClassMgr.getParticipantAssociation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ParticipantAssociation> listParticipantAssociations()
    {
        return org.semanticwb.process.model.ParticipantAssociation.ClassMgr.listParticipantAssociations(this);
    }

    public org.semanticwb.process.model.ParticipantAssociation createParticipantAssociation(String id)
    {
        return org.semanticwb.process.model.ParticipantAssociation.ClassMgr.createParticipantAssociation(id,this);
    }

    public org.semanticwb.process.model.ParticipantAssociation createParticipantAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ParticipantAssociation);
        return org.semanticwb.process.model.ParticipantAssociation.ClassMgr.createParticipantAssociation(String.valueOf(id),this);
    } 

    public void removeParticipantAssociation(String id)
    {
        org.semanticwb.process.model.ParticipantAssociation.ClassMgr.removeParticipantAssociation(id, this);
    }
    public boolean hasParticipantAssociation(String id)
    {
        return org.semanticwb.process.model.ParticipantAssociation.ClassMgr.hasParticipantAssociation(id, this);
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

    public org.semanticwb.process.model.EventDefinition getEventDefinition(String id)
    {
        return org.semanticwb.process.model.EventDefinition.ClassMgr.getEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.EventDefinition> listEventDefinitions()
    {
        return org.semanticwb.process.model.EventDefinition.ClassMgr.listEventDefinitions(this);
    }

    public org.semanticwb.process.model.EventDefinition createEventDefinition(String id)
    {
        return org.semanticwb.process.model.EventDefinition.ClassMgr.createEventDefinition(id,this);
    }

    public org.semanticwb.process.model.EventDefinition createEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_EventDefinition);
        return org.semanticwb.process.model.EventDefinition.ClassMgr.createEventDefinition(String.valueOf(id),this);
    } 

    public void removeEventDefinition(String id)
    {
        org.semanticwb.process.model.EventDefinition.ClassMgr.removeEventDefinition(id, this);
    }
    public boolean hasEventDefinition(String id)
    {
        return org.semanticwb.process.model.EventDefinition.ClassMgr.hasEventDefinition(id, this);
    }

    public org.semanticwb.process.model.ParticipantMultiplicity getParticipantMultiplicity(String id)
    {
        return org.semanticwb.process.model.ParticipantMultiplicity.ClassMgr.getParticipantMultiplicity(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ParticipantMultiplicity> listParticipantMultiplicities()
    {
        return org.semanticwb.process.model.ParticipantMultiplicity.ClassMgr.listParticipantMultiplicities(this);
    }

    public org.semanticwb.process.model.ParticipantMultiplicity createParticipantMultiplicity(String id)
    {
        return org.semanticwb.process.model.ParticipantMultiplicity.ClassMgr.createParticipantMultiplicity(id,this);
    }

    public org.semanticwb.process.model.ParticipantMultiplicity createParticipantMultiplicity()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ParticipantMultiplicity);
        return org.semanticwb.process.model.ParticipantMultiplicity.ClassMgr.createParticipantMultiplicity(String.valueOf(id),this);
    } 

    public void removeParticipantMultiplicity(String id)
    {
        org.semanticwb.process.model.ParticipantMultiplicity.ClassMgr.removeParticipantMultiplicity(id, this);
    }
    public boolean hasParticipantMultiplicity(String id)
    {
        return org.semanticwb.process.model.ParticipantMultiplicity.ClassMgr.hasParticipantMultiplicity(id, this);
    }

    public org.semanticwb.process.model.BPMNShape getBPMNShape(String id)
    {
        return org.semanticwb.process.model.BPMNShape.ClassMgr.getBPMNShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNShape> listBPMNShapes()
    {
        return org.semanticwb.process.model.BPMNShape.ClassMgr.listBPMNShapes(this);
    }

    public org.semanticwb.process.model.BPMNShape createBPMNShape(String id)
    {
        return org.semanticwb.process.model.BPMNShape.ClassMgr.createBPMNShape(id,this);
    }

    public org.semanticwb.process.model.BPMNShape createBPMNShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNShape);
        return org.semanticwb.process.model.BPMNShape.ClassMgr.createBPMNShape(String.valueOf(id),this);
    } 

    public void removeBPMNShape(String id)
    {
        org.semanticwb.process.model.BPMNShape.ClassMgr.removeBPMNShape(id, this);
    }
    public boolean hasBPMNShape(String id)
    {
        return org.semanticwb.process.model.BPMNShape.ClassMgr.hasBPMNShape(id, this);
    }

    public org.semanticwb.process.model.DataOutputShape getDataOutputShape(String id)
    {
        return org.semanticwb.process.model.DataOutputShape.ClassMgr.getDataOutputShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataOutputShape> listDataOutputShapes()
    {
        return org.semanticwb.process.model.DataOutputShape.ClassMgr.listDataOutputShapes(this);
    }

    public org.semanticwb.process.model.DataOutputShape createDataOutputShape(String id)
    {
        return org.semanticwb.process.model.DataOutputShape.ClassMgr.createDataOutputShape(id,this);
    }

    public org.semanticwb.process.model.DataOutputShape createDataOutputShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataOutputShape);
        return org.semanticwb.process.model.DataOutputShape.ClassMgr.createDataOutputShape(String.valueOf(id),this);
    } 

    public void removeDataOutputShape(String id)
    {
        org.semanticwb.process.model.DataOutputShape.ClassMgr.removeDataOutputShape(id, this);
    }
    public boolean hasDataOutputShape(String id)
    {
        return org.semanticwb.process.model.DataOutputShape.ClassMgr.hasDataOutputShape(id, this);
    }

    public org.semanticwb.process.model.LinkEventDefinition getLinkEventDefinition(String id)
    {
        return org.semanticwb.process.model.LinkEventDefinition.ClassMgr.getLinkEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LinkEventDefinition> listLinkEventDefinitions()
    {
        return org.semanticwb.process.model.LinkEventDefinition.ClassMgr.listLinkEventDefinitions(this);
    }

    public org.semanticwb.process.model.LinkEventDefinition createLinkEventDefinition(String id)
    {
        return org.semanticwb.process.model.LinkEventDefinition.ClassMgr.createLinkEventDefinition(id,this);
    }

    public org.semanticwb.process.model.LinkEventDefinition createLinkEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_LinkEventDefinition);
        return org.semanticwb.process.model.LinkEventDefinition.ClassMgr.createLinkEventDefinition(String.valueOf(id),this);
    } 

    public void removeLinkEventDefinition(String id)
    {
        org.semanticwb.process.model.LinkEventDefinition.ClassMgr.removeLinkEventDefinition(id, this);
    }
    public boolean hasLinkEventDefinition(String id)
    {
        return org.semanticwb.process.model.LinkEventDefinition.ClassMgr.hasLinkEventDefinition(id, this);
    }

    public org.semanticwb.process.model.SubConversation getSubConversation(String id)
    {
        return org.semanticwb.process.model.SubConversation.ClassMgr.getSubConversation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SubConversation> listSubConversations()
    {
        return org.semanticwb.process.model.SubConversation.ClassMgr.listSubConversations(this);
    }

    public org.semanticwb.process.model.SubConversation createSubConversation(String id)
    {
        return org.semanticwb.process.model.SubConversation.ClassMgr.createSubConversation(id,this);
    }

    public org.semanticwb.process.model.SubConversation createSubConversation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SubConversation);
        return org.semanticwb.process.model.SubConversation.ClassMgr.createSubConversation(String.valueOf(id),this);
    } 

    public void removeSubConversation(String id)
    {
        org.semanticwb.process.model.SubConversation.ClassMgr.removeSubConversation(id, this);
    }
    public boolean hasSubConversation(String id)
    {
        return org.semanticwb.process.model.SubConversation.ClassMgr.hasSubConversation(id, this);
    }

    public org.semanticwb.process.model.CorrelationKey getCorrelationKey(String id)
    {
        return org.semanticwb.process.model.CorrelationKey.ClassMgr.getCorrelationKey(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeys()
    {
        return org.semanticwb.process.model.CorrelationKey.ClassMgr.listCorrelationKeys(this);
    }

    public org.semanticwb.process.model.CorrelationKey createCorrelationKey(String id)
    {
        return org.semanticwb.process.model.CorrelationKey.ClassMgr.createCorrelationKey(id,this);
    }

    public org.semanticwb.process.model.CorrelationKey createCorrelationKey()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CorrelationKey);
        return org.semanticwb.process.model.CorrelationKey.ClassMgr.createCorrelationKey(String.valueOf(id),this);
    } 

    public void removeCorrelationKey(String id)
    {
        org.semanticwb.process.model.CorrelationKey.ClassMgr.removeCorrelationKey(id, this);
    }
    public boolean hasCorrelationKey(String id)
    {
        return org.semanticwb.process.model.CorrelationKey.ClassMgr.hasCorrelationKey(id, this);
    }

    public org.semanticwb.process.model.LoopCharacteristics getLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.LoopCharacteristics.ClassMgr.getLoopCharacteristics(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LoopCharacteristics> listLoopCharacteristicses()
    {
        return org.semanticwb.process.model.LoopCharacteristics.ClassMgr.listLoopCharacteristicses(this);
    }

    public org.semanticwb.process.model.LoopCharacteristics createLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.LoopCharacteristics.ClassMgr.createLoopCharacteristics(id,this);
    }

    public org.semanticwb.process.model.LoopCharacteristics createLoopCharacteristics()
    {
        long id=getSemanticObject().getModel().getCounter(swp_LoopCharacteristics);
        return org.semanticwb.process.model.LoopCharacteristics.ClassMgr.createLoopCharacteristics(String.valueOf(id),this);
    } 

    public void removeLoopCharacteristics(String id)
    {
        org.semanticwb.process.model.LoopCharacteristics.ClassMgr.removeLoopCharacteristics(id, this);
    }
    public boolean hasLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.LoopCharacteristics.ClassMgr.hasLoopCharacteristics(id, this);
    }

    public org.semanticwb.process.model.MessageEventDefinition getMessageEventDefinition(String id)
    {
        return org.semanticwb.process.model.MessageEventDefinition.ClassMgr.getMessageEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageEventDefinition> listMessageEventDefinitions()
    {
        return org.semanticwb.process.model.MessageEventDefinition.ClassMgr.listMessageEventDefinitions(this);
    }

    public org.semanticwb.process.model.MessageEventDefinition createMessageEventDefinition(String id)
    {
        return org.semanticwb.process.model.MessageEventDefinition.ClassMgr.createMessageEventDefinition(id,this);
    }

    public org.semanticwb.process.model.MessageEventDefinition createMessageEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MessageEventDefinition);
        return org.semanticwb.process.model.MessageEventDefinition.ClassMgr.createMessageEventDefinition(String.valueOf(id),this);
    } 

    public void removeMessageEventDefinition(String id)
    {
        org.semanticwb.process.model.MessageEventDefinition.ClassMgr.removeMessageEventDefinition(id, this);
    }
    public boolean hasMessageEventDefinition(String id)
    {
        return org.semanticwb.process.model.MessageEventDefinition.ClassMgr.hasMessageEventDefinition(id, this);
    }

    public org.semanticwb.process.model.FormalExpression getFormalExpression(String id)
    {
        return org.semanticwb.process.model.FormalExpression.ClassMgr.getFormalExpression(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressions()
    {
        return org.semanticwb.process.model.FormalExpression.ClassMgr.listFormalExpressions(this);
    }

    public org.semanticwb.process.model.FormalExpression createFormalExpression(String id)
    {
        return org.semanticwb.process.model.FormalExpression.ClassMgr.createFormalExpression(id,this);
    }

    public org.semanticwb.process.model.FormalExpression createFormalExpression()
    {
        long id=getSemanticObject().getModel().getCounter(swp_FormalExpression);
        return org.semanticwb.process.model.FormalExpression.ClassMgr.createFormalExpression(String.valueOf(id),this);
    } 

    public void removeFormalExpression(String id)
    {
        org.semanticwb.process.model.FormalExpression.ClassMgr.removeFormalExpression(id, this);
    }
    public boolean hasFormalExpression(String id)
    {
        return org.semanticwb.process.model.FormalExpression.ClassMgr.hasFormalExpression(id, this);
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

    public org.semanticwb.process.model.GroupShape getGroupShape(String id)
    {
        return org.semanticwb.process.model.GroupShape.ClassMgr.getGroupShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GroupShape> listGroupShapes()
    {
        return org.semanticwb.process.model.GroupShape.ClassMgr.listGroupShapes(this);
    }

    public org.semanticwb.process.model.GroupShape createGroupShape(String id)
    {
        return org.semanticwb.process.model.GroupShape.ClassMgr.createGroupShape(id,this);
    }

    public org.semanticwb.process.model.GroupShape createGroupShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GroupShape);
        return org.semanticwb.process.model.GroupShape.ClassMgr.createGroupShape(String.valueOf(id),this);
    } 

    public void removeGroupShape(String id)
    {
        org.semanticwb.process.model.GroupShape.ClassMgr.removeGroupShape(id, this);
    }
    public boolean hasGroupShape(String id)
    {
        return org.semanticwb.process.model.GroupShape.ClassMgr.hasGroupShape(id, this);
    }

    public org.semanticwb.process.model.BusinessRuleTask getBusinessRuleTask(String id)
    {
        return org.semanticwb.process.model.BusinessRuleTask.ClassMgr.getBusinessRuleTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BusinessRuleTask> listBusinessRuleTasks()
    {
        return org.semanticwb.process.model.BusinessRuleTask.ClassMgr.listBusinessRuleTasks(this);
    }

    public org.semanticwb.process.model.BusinessRuleTask createBusinessRuleTask(String id)
    {
        return org.semanticwb.process.model.BusinessRuleTask.ClassMgr.createBusinessRuleTask(id,this);
    }

    public org.semanticwb.process.model.BusinessRuleTask createBusinessRuleTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BusinessRuleTask);
        return org.semanticwb.process.model.BusinessRuleTask.ClassMgr.createBusinessRuleTask(String.valueOf(id),this);
    } 

    public void removeBusinessRuleTask(String id)
    {
        org.semanticwb.process.model.BusinessRuleTask.ClassMgr.removeBusinessRuleTask(id, this);
    }
    public boolean hasBusinessRuleTask(String id)
    {
        return org.semanticwb.process.model.BusinessRuleTask.ClassMgr.hasBusinessRuleTask(id, this);
    }

    public org.semanticwb.process.model.Extension getExtension(String id)
    {
        return org.semanticwb.process.model.Extension.ClassMgr.getExtension(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Extension> listExtensions()
    {
        return org.semanticwb.process.model.Extension.ClassMgr.listExtensions(this);
    }

    public org.semanticwb.process.model.Extension createExtension(String id)
    {
        return org.semanticwb.process.model.Extension.ClassMgr.createExtension(id,this);
    }

    public void removeExtension(String id)
    {
        org.semanticwb.process.model.Extension.ClassMgr.removeExtension(id, this);
    }
    public boolean hasExtension(String id)
    {
        return org.semanticwb.process.model.Extension.ClassMgr.hasExtension(id, this);
    }

    public org.semanticwb.process.model.ExtensionAttributeValue getExtensionAttributeValue(String id)
    {
        return org.semanticwb.process.model.ExtensionAttributeValue.ClassMgr.getExtensionAttributeValue(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ExtensionAttributeValue> listExtensionAttributeValues()
    {
        return org.semanticwb.process.model.ExtensionAttributeValue.ClassMgr.listExtensionAttributeValues(this);
    }

    public org.semanticwb.process.model.ExtensionAttributeValue createExtensionAttributeValue(String id)
    {
        return org.semanticwb.process.model.ExtensionAttributeValue.ClassMgr.createExtensionAttributeValue(id,this);
    }

    public org.semanticwb.process.model.ExtensionAttributeValue createExtensionAttributeValue()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ExtensionAttributeValue);
        return org.semanticwb.process.model.ExtensionAttributeValue.ClassMgr.createExtensionAttributeValue(String.valueOf(id),this);
    } 

    public void removeExtensionAttributeValue(String id)
    {
        org.semanticwb.process.model.ExtensionAttributeValue.ClassMgr.removeExtensionAttributeValue(id, this);
    }
    public boolean hasExtensionAttributeValue(String id)
    {
        return org.semanticwb.process.model.ExtensionAttributeValue.ClassMgr.hasExtensionAttributeValue(id, this);
    }

    public org.semanticwb.process.model.ChoreographyCompartment getChoreographyCompartment(String id)
    {
        return org.semanticwb.process.model.ChoreographyCompartment.ClassMgr.getChoreographyCompartment(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ChoreographyCompartment> listChoreographyCompartments()
    {
        return org.semanticwb.process.model.ChoreographyCompartment.ClassMgr.listChoreographyCompartments(this);
    }

    public org.semanticwb.process.model.ChoreographyCompartment createChoreographyCompartment(String id)
    {
        return org.semanticwb.process.model.ChoreographyCompartment.ClassMgr.createChoreographyCompartment(id,this);
    }

    public org.semanticwb.process.model.ChoreographyCompartment createChoreographyCompartment()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ChoreographyCompartment);
        return org.semanticwb.process.model.ChoreographyCompartment.ClassMgr.createChoreographyCompartment(String.valueOf(id),this);
    } 

    public void removeChoreographyCompartment(String id)
    {
        org.semanticwb.process.model.ChoreographyCompartment.ClassMgr.removeChoreographyCompartment(id, this);
    }
    public boolean hasChoreographyCompartment(String id)
    {
        return org.semanticwb.process.model.ChoreographyCompartment.ClassMgr.hasChoreographyCompartment(id, this);
    }

    public org.semanticwb.process.model.Documentation getDocumentation(String id)
    {
        return org.semanticwb.process.model.Documentation.ClassMgr.getDocumentation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Documentation> listDocumentations()
    {
        return org.semanticwb.process.model.Documentation.ClassMgr.listDocumentations(this);
    }

    public org.semanticwb.process.model.Documentation createDocumentation(String id)
    {
        return org.semanticwb.process.model.Documentation.ClassMgr.createDocumentation(id,this);
    }

    public org.semanticwb.process.model.Documentation createDocumentation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Documentation);
        return org.semanticwb.process.model.Documentation.ClassMgr.createDocumentation(String.valueOf(id),this);
    } 

    public void removeDocumentation(String id)
    {
        org.semanticwb.process.model.Documentation.ClassMgr.removeDocumentation(id, this);
    }
    public boolean hasDocumentation(String id)
    {
        return org.semanticwb.process.model.Documentation.ClassMgr.hasDocumentation(id, this);
    }

    public org.semanticwb.process.model.LaneCompartment getLaneCompartment(String id)
    {
        return org.semanticwb.process.model.LaneCompartment.ClassMgr.getLaneCompartment(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartments()
    {
        return org.semanticwb.process.model.LaneCompartment.ClassMgr.listLaneCompartments(this);
    }

    public org.semanticwb.process.model.LaneCompartment createLaneCompartment(String id)
    {
        return org.semanticwb.process.model.LaneCompartment.ClassMgr.createLaneCompartment(id,this);
    }

    public org.semanticwb.process.model.LaneCompartment createLaneCompartment()
    {
        long id=getSemanticObject().getModel().getCounter(swp_LaneCompartment);
        return org.semanticwb.process.model.LaneCompartment.ClassMgr.createLaneCompartment(String.valueOf(id),this);
    } 

    public void removeLaneCompartment(String id)
    {
        org.semanticwb.process.model.LaneCompartment.ClassMgr.removeLaneCompartment(id, this);
    }
    public boolean hasLaneCompartment(String id)
    {
        return org.semanticwb.process.model.LaneCompartment.ClassMgr.hasLaneCompartment(id, this);
    }

    public org.semanticwb.process.model.Category getCategory(String id)
    {
        return org.semanticwb.process.model.Category.ClassMgr.getCategory(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Category> listCategories()
    {
        return org.semanticwb.process.model.Category.ClassMgr.listCategories(this);
    }

    public org.semanticwb.process.model.Category createCategory(String id)
    {
        return org.semanticwb.process.model.Category.ClassMgr.createCategory(id,this);
    }

    public org.semanticwb.process.model.Category createCategory()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Category);
        return org.semanticwb.process.model.Category.ClassMgr.createCategory(String.valueOf(id),this);
    } 

    public void removeCategory(String id)
    {
        org.semanticwb.process.model.Category.ClassMgr.removeCategory(id, this);
    }
    public boolean hasCategory(String id)
    {
        return org.semanticwb.process.model.Category.ClassMgr.hasCategory(id, this);
    }

    public org.semanticwb.process.model.TerminateEventDefinition getTerminateEventDefinition(String id)
    {
        return org.semanticwb.process.model.TerminateEventDefinition.ClassMgr.getTerminateEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.TerminateEventDefinition> listTerminateEventDefinitions()
    {
        return org.semanticwb.process.model.TerminateEventDefinition.ClassMgr.listTerminateEventDefinitions(this);
    }

    public org.semanticwb.process.model.TerminateEventDefinition createTerminateEventDefinition(String id)
    {
        return org.semanticwb.process.model.TerminateEventDefinition.ClassMgr.createTerminateEventDefinition(id,this);
    }

    public void removeTerminateEventDefinition(String id)
    {
        org.semanticwb.process.model.TerminateEventDefinition.ClassMgr.removeTerminateEventDefinition(id, this);
    }
    public boolean hasTerminateEventDefinition(String id)
    {
        return org.semanticwb.process.model.TerminateEventDefinition.ClassMgr.hasTerminateEventDefinition(id, this);
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

    public org.semanticwb.process.model.ResourceAssignmentExpression getResourceAssignmentExpression(String id)
    {
        return org.semanticwb.process.model.ResourceAssignmentExpression.ClassMgr.getResourceAssignmentExpression(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressions()
    {
        return org.semanticwb.process.model.ResourceAssignmentExpression.ClassMgr.listResourceAssignmentExpressions(this);
    }

    public org.semanticwb.process.model.ResourceAssignmentExpression createResourceAssignmentExpression(String id)
    {
        return org.semanticwb.process.model.ResourceAssignmentExpression.ClassMgr.createResourceAssignmentExpression(id,this);
    }

    public org.semanticwb.process.model.ResourceAssignmentExpression createResourceAssignmentExpression()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ResourceAssignmentExpression);
        return org.semanticwb.process.model.ResourceAssignmentExpression.ClassMgr.createResourceAssignmentExpression(String.valueOf(id),this);
    } 

    public void removeResourceAssignmentExpression(String id)
    {
        org.semanticwb.process.model.ResourceAssignmentExpression.ClassMgr.removeResourceAssignmentExpression(id, this);
    }
    public boolean hasResourceAssignmentExpression(String id)
    {
        return org.semanticwb.process.model.ResourceAssignmentExpression.ClassMgr.hasResourceAssignmentExpression(id, this);
    }

    public org.semanticwb.process.model.EndPoint getEndPoint(String id)
    {
        return org.semanticwb.process.model.EndPoint.ClassMgr.getEndPoint(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.EndPoint> listEndPoints()
    {
        return org.semanticwb.process.model.EndPoint.ClassMgr.listEndPoints(this);
    }

    public org.semanticwb.process.model.EndPoint createEndPoint(String id)
    {
        return org.semanticwb.process.model.EndPoint.ClassMgr.createEndPoint(id,this);
    }

    public org.semanticwb.process.model.EndPoint createEndPoint()
    {
        long id=getSemanticObject().getModel().getCounter(swp_EndPoint);
        return org.semanticwb.process.model.EndPoint.ClassMgr.createEndPoint(String.valueOf(id),this);
    } 

    public void removeEndPoint(String id)
    {
        org.semanticwb.process.model.EndPoint.ClassMgr.removeEndPoint(id, this);
    }
    public boolean hasEndPoint(String id)
    {
        return org.semanticwb.process.model.EndPoint.ClassMgr.hasEndPoint(id, this);
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

    public org.semanticwb.process.model.MultiInstanceActivityInstance getMultiInstanceActivityInstance(String id)
    {
        return org.semanticwb.process.model.MultiInstanceActivityInstance.ClassMgr.getMultiInstanceActivityInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MultiInstanceActivityInstance> listMultiInstanceActivityInstances()
    {
        return org.semanticwb.process.model.MultiInstanceActivityInstance.ClassMgr.listMultiInstanceActivityInstances(this);
    }

    public org.semanticwb.process.model.MultiInstanceActivityInstance createMultiInstanceActivityInstance(String id)
    {
        return org.semanticwb.process.model.MultiInstanceActivityInstance.ClassMgr.createMultiInstanceActivityInstance(id,this);
    }

    public org.semanticwb.process.model.MultiInstanceActivityInstance createMultiInstanceActivityInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MultiInstanceActivityInstance);
        return org.semanticwb.process.model.MultiInstanceActivityInstance.ClassMgr.createMultiInstanceActivityInstance(String.valueOf(id),this);
    } 

    public void removeMultiInstanceActivityInstance(String id)
    {
        org.semanticwb.process.model.MultiInstanceActivityInstance.ClassMgr.removeMultiInstanceActivityInstance(id, this);
    }
    public boolean hasMultiInstanceActivityInstance(String id)
    {
        return org.semanticwb.process.model.MultiInstanceActivityInstance.ClassMgr.hasMultiInstanceActivityInstance(id, this);
    }

    public org.semanticwb.process.model.GatewayShape getGatewayShape(String id)
    {
        return org.semanticwb.process.model.GatewayShape.ClassMgr.getGatewayShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GatewayShape> listGatewayShapes()
    {
        return org.semanticwb.process.model.GatewayShape.ClassMgr.listGatewayShapes(this);
    }

    public org.semanticwb.process.model.GatewayShape createGatewayShape(String id)
    {
        return org.semanticwb.process.model.GatewayShape.ClassMgr.createGatewayShape(id,this);
    }

    public org.semanticwb.process.model.GatewayShape createGatewayShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GatewayShape);
        return org.semanticwb.process.model.GatewayShape.ClassMgr.createGatewayShape(String.valueOf(id),this);
    } 

    public void removeGatewayShape(String id)
    {
        org.semanticwb.process.model.GatewayShape.ClassMgr.removeGatewayShape(id, this);
    }
    public boolean hasGatewayShape(String id)
    {
        return org.semanticwb.process.model.GatewayShape.ClassMgr.hasGatewayShape(id, this);
    }

    public org.semanticwb.process.model.UserTaskInstance getUserTaskInstance(String id)
    {
        return org.semanticwb.process.model.UserTaskInstance.ClassMgr.getUserTaskInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.UserTaskInstance> listUserTaskInstances()
    {
        return org.semanticwb.process.model.UserTaskInstance.ClassMgr.listUserTaskInstances(this);
    }

    public org.semanticwb.process.model.UserTaskInstance createUserTaskInstance(String id)
    {
        return org.semanticwb.process.model.UserTaskInstance.ClassMgr.createUserTaskInstance(id,this);
    }

    public void removeUserTaskInstance(String id)
    {
        org.semanticwb.process.model.UserTaskInstance.ClassMgr.removeUserTaskInstance(id, this);
    }
    public boolean hasUserTaskInstance(String id)
    {
        return org.semanticwb.process.model.UserTaskInstance.ClassMgr.hasUserTaskInstance(id, this);
    }

    public org.semanticwb.process.model.ProcessInterface getProcessInterface(String id)
    {
        return org.semanticwb.process.model.ProcessInterface.ClassMgr.getProcessInterface(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ProcessInterface> listProcessInterfaces()
    {
        return org.semanticwb.process.model.ProcessInterface.ClassMgr.listProcessInterfaces(this);
    }

    public org.semanticwb.process.model.ProcessInterface createProcessInterface(String id)
    {
        return org.semanticwb.process.model.ProcessInterface.ClassMgr.createProcessInterface(id,this);
    }

    public org.semanticwb.process.model.ProcessInterface createProcessInterface()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ProcessInterface);
        return org.semanticwb.process.model.ProcessInterface.ClassMgr.createProcessInterface(String.valueOf(id),this);
    } 

    public void removeProcessInterface(String id)
    {
        org.semanticwb.process.model.ProcessInterface.ClassMgr.removeProcessInterface(id, this);
    }
    public boolean hasProcessInterface(String id)
    {
        return org.semanticwb.process.model.ProcessInterface.ClassMgr.hasProcessInterface(id, this);
    }

    public org.semanticwb.process.model.DataInputShape getDataInputShape(String id)
    {
        return org.semanticwb.process.model.DataInputShape.ClassMgr.getDataInputShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataInputShape> listDataInputShapes()
    {
        return org.semanticwb.process.model.DataInputShape.ClassMgr.listDataInputShapes(this);
    }

    public org.semanticwb.process.model.DataInputShape createDataInputShape(String id)
    {
        return org.semanticwb.process.model.DataInputShape.ClassMgr.createDataInputShape(id,this);
    }

    public org.semanticwb.process.model.DataInputShape createDataInputShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataInputShape);
        return org.semanticwb.process.model.DataInputShape.ClassMgr.createDataInputShape(String.valueOf(id),this);
    } 

    public void removeDataInputShape(String id)
    {
        org.semanticwb.process.model.DataInputShape.ClassMgr.removeDataInputShape(id, this);
    }
    public boolean hasDataInputShape(String id)
    {
        return org.semanticwb.process.model.DataInputShape.ClassMgr.hasDataInputShape(id, this);
    }

    public org.semanticwb.process.model.IntermediateThrowEvent getIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.IntermediateThrowEvent.ClassMgr.getIntermediateThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.IntermediateThrowEvent> listIntermediateThrowEvents()
    {
        return org.semanticwb.process.model.IntermediateThrowEvent.ClassMgr.listIntermediateThrowEvents(this);
    }

    public org.semanticwb.process.model.IntermediateThrowEvent createIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.IntermediateThrowEvent.ClassMgr.createIntermediateThrowEvent(id,this);
    }

    public org.semanticwb.process.model.IntermediateThrowEvent createIntermediateThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_IntermediateThrowEvent);
        return org.semanticwb.process.model.IntermediateThrowEvent.ClassMgr.createIntermediateThrowEvent(String.valueOf(id),this);
    } 

    public void removeIntermediateThrowEvent(String id)
    {
        org.semanticwb.process.model.IntermediateThrowEvent.ClassMgr.removeIntermediateThrowEvent(id, this);
    }
    public boolean hasIntermediateThrowEvent(String id)
    {
        return org.semanticwb.process.model.IntermediateThrowEvent.ClassMgr.hasIntermediateThrowEvent(id, this);
    }

    public org.semanticwb.process.model.CorrelationPropertyRetrievalExpression getCorrelationPropertyRetrievalExpression(String id)
    {
        return org.semanticwb.process.model.CorrelationPropertyRetrievalExpression.ClassMgr.getCorrelationPropertyRetrievalExpression(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressions()
    {
        return org.semanticwb.process.model.CorrelationPropertyRetrievalExpression.ClassMgr.listCorrelationPropertyRetrievalExpressions(this);
    }

    public org.semanticwb.process.model.CorrelationPropertyRetrievalExpression createCorrelationPropertyRetrievalExpression(String id)
    {
        return org.semanticwb.process.model.CorrelationPropertyRetrievalExpression.ClassMgr.createCorrelationPropertyRetrievalExpression(id,this);
    }

    public org.semanticwb.process.model.CorrelationPropertyRetrievalExpression createCorrelationPropertyRetrievalExpression()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CorrelationPropertyRetrievalExpression);
        return org.semanticwb.process.model.CorrelationPropertyRetrievalExpression.ClassMgr.createCorrelationPropertyRetrievalExpression(String.valueOf(id),this);
    } 

    public void removeCorrelationPropertyRetrievalExpression(String id)
    {
        org.semanticwb.process.model.CorrelationPropertyRetrievalExpression.ClassMgr.removeCorrelationPropertyRetrievalExpression(id, this);
    }
    public boolean hasCorrelationPropertyRetrievalExpression(String id)
    {
        return org.semanticwb.process.model.CorrelationPropertyRetrievalExpression.ClassMgr.hasCorrelationPropertyRetrievalExpression(id, this);
    }

    public org.semanticwb.process.model.Communication getCommunication(String id)
    {
        return org.semanticwb.process.model.Communication.ClassMgr.getCommunication(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Communication> listCommunications()
    {
        return org.semanticwb.process.model.Communication.ClassMgr.listCommunications(this);
    }

    public org.semanticwb.process.model.Communication createCommunication(String id)
    {
        return org.semanticwb.process.model.Communication.ClassMgr.createCommunication(id,this);
    }

    public org.semanticwb.process.model.Communication createCommunication()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Communication);
        return org.semanticwb.process.model.Communication.ClassMgr.createCommunication(String.valueOf(id),this);
    } 

    public void removeCommunication(String id)
    {
        org.semanticwb.process.model.Communication.ClassMgr.removeCommunication(id, this);
    }
    public boolean hasCommunication(String id)
    {
        return org.semanticwb.process.model.Communication.ClassMgr.hasCommunication(id, this);
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

    public org.semanticwb.process.model.InputOutputBinding getInputOutputBinding(String id)
    {
        return org.semanticwb.process.model.InputOutputBinding.ClassMgr.getInputOutputBinding(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.InputOutputBinding> listInputOutputBindings()
    {
        return org.semanticwb.process.model.InputOutputBinding.ClassMgr.listInputOutputBindings(this);
    }

    public org.semanticwb.process.model.InputOutputBinding createInputOutputBinding(String id)
    {
        return org.semanticwb.process.model.InputOutputBinding.ClassMgr.createInputOutputBinding(id,this);
    }

    public org.semanticwb.process.model.InputOutputBinding createInputOutputBinding()
    {
        long id=getSemanticObject().getModel().getCounter(swp_InputOutputBinding);
        return org.semanticwb.process.model.InputOutputBinding.ClassMgr.createInputOutputBinding(String.valueOf(id),this);
    } 

    public void removeInputOutputBinding(String id)
    {
        org.semanticwb.process.model.InputOutputBinding.ClassMgr.removeInputOutputBinding(id, this);
    }
    public boolean hasInputOutputBinding(String id)
    {
        return org.semanticwb.process.model.InputOutputBinding.ClassMgr.hasInputOutputBinding(id, this);
    }

    public org.semanticwb.process.model.TextAnnotationShape getTextAnnotationShape(String id)
    {
        return org.semanticwb.process.model.TextAnnotationShape.ClassMgr.getTextAnnotationShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.TextAnnotationShape> listTextAnnotationShapes()
    {
        return org.semanticwb.process.model.TextAnnotationShape.ClassMgr.listTextAnnotationShapes(this);
    }

    public org.semanticwb.process.model.TextAnnotationShape createTextAnnotationShape(String id)
    {
        return org.semanticwb.process.model.TextAnnotationShape.ClassMgr.createTextAnnotationShape(id,this);
    }

    public org.semanticwb.process.model.TextAnnotationShape createTextAnnotationShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_TextAnnotationShape);
        return org.semanticwb.process.model.TextAnnotationShape.ClassMgr.createTextAnnotationShape(String.valueOf(id),this);
    } 

    public void removeTextAnnotationShape(String id)
    {
        org.semanticwb.process.model.TextAnnotationShape.ClassMgr.removeTextAnnotationShape(id, this);
    }
    public boolean hasTextAnnotationShape(String id)
    {
        return org.semanticwb.process.model.TextAnnotationShape.ClassMgr.hasTextAnnotationShape(id, this);
    }

    public org.semanticwb.process.model.GlobalCommunication getGlobalCommunication(String id)
    {
        return org.semanticwb.process.model.GlobalCommunication.ClassMgr.getGlobalCommunication(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GlobalCommunication> listGlobalCommunications()
    {
        return org.semanticwb.process.model.GlobalCommunication.ClassMgr.listGlobalCommunications(this);
    }

    public org.semanticwb.process.model.GlobalCommunication createGlobalCommunication(String id)
    {
        return org.semanticwb.process.model.GlobalCommunication.ClassMgr.createGlobalCommunication(id,this);
    }

    public org.semanticwb.process.model.GlobalCommunication createGlobalCommunication()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GlobalCommunication);
        return org.semanticwb.process.model.GlobalCommunication.ClassMgr.createGlobalCommunication(String.valueOf(id),this);
    } 

    public void removeGlobalCommunication(String id)
    {
        org.semanticwb.process.model.GlobalCommunication.ClassMgr.removeGlobalCommunication(id, this);
    }
    public boolean hasGlobalCommunication(String id)
    {
        return org.semanticwb.process.model.GlobalCommunication.ClassMgr.hasGlobalCommunication(id, this);
    }

    public org.semanticwb.process.model.EmbeddedSubProcess getEmbeddedSubProcess(String id)
    {
        return org.semanticwb.process.model.EmbeddedSubProcess.ClassMgr.getEmbeddedSubProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcesses()
    {
        return org.semanticwb.process.model.EmbeddedSubProcess.ClassMgr.listEmbeddedSubProcesses(this);
    }

    public org.semanticwb.process.model.EmbeddedSubProcess createEmbeddedSubProcess(String id)
    {
        return org.semanticwb.process.model.EmbeddedSubProcess.ClassMgr.createEmbeddedSubProcess(id,this);
    }

    public org.semanticwb.process.model.EmbeddedSubProcess createEmbeddedSubProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swp_EmbeddedSubProcess);
        return org.semanticwb.process.model.EmbeddedSubProcess.ClassMgr.createEmbeddedSubProcess(String.valueOf(id),this);
    } 

    public void removeEmbeddedSubProcess(String id)
    {
        org.semanticwb.process.model.EmbeddedSubProcess.ClassMgr.removeEmbeddedSubProcess(id, this);
    }
    public boolean hasEmbeddedSubProcess(String id)
    {
        return org.semanticwb.process.model.EmbeddedSubProcess.ClassMgr.hasEmbeddedSubProcess(id, this);
    }

    public org.semanticwb.process.model.Choreography getChoreography(String id)
    {
        return org.semanticwb.process.model.Choreography.ClassMgr.getChoreography(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Choreography> listChoreographies()
    {
        return org.semanticwb.process.model.Choreography.ClassMgr.listChoreographies(this);
    }

    public org.semanticwb.process.model.Choreography createChoreography(String id)
    {
        return org.semanticwb.process.model.Choreography.ClassMgr.createChoreography(id,this);
    }

    public org.semanticwb.process.model.Choreography createChoreography()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Choreography);
        return org.semanticwb.process.model.Choreography.ClassMgr.createChoreography(String.valueOf(id),this);
    } 

    public void removeChoreography(String id)
    {
        org.semanticwb.process.model.Choreography.ClassMgr.removeChoreography(id, this);
    }
    public boolean hasChoreography(String id)
    {
        return org.semanticwb.process.model.Choreography.ClassMgr.hasChoreography(id, this);
    }

    public org.semanticwb.process.model.HumanPerformer getHumanPerformer(String id)
    {
        return org.semanticwb.process.model.HumanPerformer.ClassMgr.getHumanPerformer(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformers()
    {
        return org.semanticwb.process.model.HumanPerformer.ClassMgr.listHumanPerformers(this);
    }

    public org.semanticwb.process.model.HumanPerformer createHumanPerformer(String id)
    {
        return org.semanticwb.process.model.HumanPerformer.ClassMgr.createHumanPerformer(id,this);
    }

    public org.semanticwb.process.model.HumanPerformer createHumanPerformer()
    {
        long id=getSemanticObject().getModel().getCounter(swp_HumanPerformer);
        return org.semanticwb.process.model.HumanPerformer.ClassMgr.createHumanPerformer(String.valueOf(id),this);
    } 

    public void removeHumanPerformer(String id)
    {
        org.semanticwb.process.model.HumanPerformer.ClassMgr.removeHumanPerformer(id, this);
    }
    public boolean hasHumanPerformer(String id)
    {
        return org.semanticwb.process.model.HumanPerformer.ClassMgr.hasHumanPerformer(id, this);
    }

    public org.semanticwb.process.model.BPMNBaseElement getBPMNBaseElement(String id)
    {
        return org.semanticwb.process.model.BPMNBaseElement.ClassMgr.getBPMNBaseElement(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElements()
    {
        return org.semanticwb.process.model.BPMNBaseElement.ClassMgr.listBPMNBaseElements(this);
    }

    public org.semanticwb.process.model.BPMNBaseElement createBPMNBaseElement(String id)
    {
        return org.semanticwb.process.model.BPMNBaseElement.ClassMgr.createBPMNBaseElement(id,this);
    }

    public org.semanticwb.process.model.BPMNBaseElement createBPMNBaseElement()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNBaseElement);
        return org.semanticwb.process.model.BPMNBaseElement.ClassMgr.createBPMNBaseElement(String.valueOf(id),this);
    } 

    public void removeBPMNBaseElement(String id)
    {
        org.semanticwb.process.model.BPMNBaseElement.ClassMgr.removeBPMNBaseElement(id, this);
    }
    public boolean hasBPMNBaseElement(String id)
    {
        return org.semanticwb.process.model.BPMNBaseElement.ClassMgr.hasBPMNBaseElement(id, this);
    }

    public org.semanticwb.process.model.Participant getParticipant(String id)
    {
        return org.semanticwb.process.model.Participant.ClassMgr.getParticipant(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Participant> listParticipants()
    {
        return org.semanticwb.process.model.Participant.ClassMgr.listParticipants(this);
    }

    public org.semanticwb.process.model.Participant createParticipant(String id)
    {
        return org.semanticwb.process.model.Participant.ClassMgr.createParticipant(id,this);
    }

    public org.semanticwb.process.model.Participant createParticipant()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Participant);
        return org.semanticwb.process.model.Participant.ClassMgr.createParticipant(String.valueOf(id),this);
    } 

    public void removeParticipant(String id)
    {
        org.semanticwb.process.model.Participant.ClassMgr.removeParticipant(id, this);
    }
    public boolean hasParticipant(String id)
    {
        return org.semanticwb.process.model.Participant.ClassMgr.hasParticipant(id, this);
    }

    public org.semanticwb.process.model.BPMNChoreographyDiagram getBPMNChoreographyDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNChoreographyDiagram.ClassMgr.getBPMNChoreographyDiagram(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNChoreographyDiagram> listBPMNChoreographyDiagrams()
    {
        return org.semanticwb.process.model.BPMNChoreographyDiagram.ClassMgr.listBPMNChoreographyDiagrams(this);
    }

    public org.semanticwb.process.model.BPMNChoreographyDiagram createBPMNChoreographyDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNChoreographyDiagram.ClassMgr.createBPMNChoreographyDiagram(id,this);
    }

    public org.semanticwb.process.model.BPMNChoreographyDiagram createBPMNChoreographyDiagram()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNChoreographyDiagram);
        return org.semanticwb.process.model.BPMNChoreographyDiagram.ClassMgr.createBPMNChoreographyDiagram(String.valueOf(id),this);
    } 

    public void removeBPMNChoreographyDiagram(String id)
    {
        org.semanticwb.process.model.BPMNChoreographyDiagram.ClassMgr.removeBPMNChoreographyDiagram(id, this);
    }
    public boolean hasBPMNChoreographyDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNChoreographyDiagram.ClassMgr.hasBPMNChoreographyDiagram(id, this);
    }

    public org.semanticwb.process.model.PoolCompartment getPoolCompartment(String id)
    {
        return org.semanticwb.process.model.PoolCompartment.ClassMgr.getPoolCompartment(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.PoolCompartment> listPoolCompartments()
    {
        return org.semanticwb.process.model.PoolCompartment.ClassMgr.listPoolCompartments(this);
    }

    public org.semanticwb.process.model.PoolCompartment createPoolCompartment(String id)
    {
        return org.semanticwb.process.model.PoolCompartment.ClassMgr.createPoolCompartment(id,this);
    }

    public org.semanticwb.process.model.PoolCompartment createPoolCompartment()
    {
        long id=getSemanticObject().getModel().getCounter(swp_PoolCompartment);
        return org.semanticwb.process.model.PoolCompartment.ClassMgr.createPoolCompartment(String.valueOf(id),this);
    } 

    public void removePoolCompartment(String id)
    {
        org.semanticwb.process.model.PoolCompartment.ClassMgr.removePoolCompartment(id, this);
    }
    public boolean hasPoolCompartment(String id)
    {
        return org.semanticwb.process.model.PoolCompartment.ClassMgr.hasPoolCompartment(id, this);
    }

    public org.semanticwb.process.model.FlowElement getFlowElement(String id)
    {
        return org.semanticwb.process.model.FlowElement.ClassMgr.getFlowElement(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.FlowElement> listFlowElements()
    {
        return org.semanticwb.process.model.FlowElement.ClassMgr.listFlowElements(this);
    }

    public org.semanticwb.process.model.FlowElement createFlowElement(String id)
    {
        return org.semanticwb.process.model.FlowElement.ClassMgr.createFlowElement(id,this);
    }

    public org.semanticwb.process.model.FlowElement createFlowElement()
    {
        long id=getSemanticObject().getModel().getCounter(swp_FlowElement);
        return org.semanticwb.process.model.FlowElement.ClassMgr.createFlowElement(String.valueOf(id),this);
    } 

    public void removeFlowElement(String id)
    {
        org.semanticwb.process.model.FlowElement.ClassMgr.removeFlowElement(id, this);
    }
    public boolean hasFlowElement(String id)
    {
        return org.semanticwb.process.model.FlowElement.ClassMgr.hasFlowElement(id, this);
    }

    public org.semanticwb.process.model.DataOutput getDataOutput(String id)
    {
        return org.semanticwb.process.model.DataOutput.ClassMgr.getDataOutput(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataOutput> listDataOutputs()
    {
        return org.semanticwb.process.model.DataOutput.ClassMgr.listDataOutputs(this);
    }

    public org.semanticwb.process.model.DataOutput createDataOutput(String id)
    {
        return org.semanticwb.process.model.DataOutput.ClassMgr.createDataOutput(id,this);
    }

    public org.semanticwb.process.model.DataOutput createDataOutput()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataOutput);
        return org.semanticwb.process.model.DataOutput.ClassMgr.createDataOutput(String.valueOf(id),this);
    } 

    public void removeDataOutput(String id)
    {
        org.semanticwb.process.model.DataOutput.ClassMgr.removeDataOutput(id, this);
    }
    public boolean hasDataOutput(String id)
    {
        return org.semanticwb.process.model.DataOutput.ClassMgr.hasDataOutput(id, this);
    }

    public org.semanticwb.process.model.ConversationLinkConnector getConversationLinkConnector(String id)
    {
        return org.semanticwb.process.model.ConversationLinkConnector.ClassMgr.getConversationLinkConnector(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ConversationLinkConnector> listConversationLinkConnectors()
    {
        return org.semanticwb.process.model.ConversationLinkConnector.ClassMgr.listConversationLinkConnectors(this);
    }

    public org.semanticwb.process.model.ConversationLinkConnector createConversationLinkConnector(String id)
    {
        return org.semanticwb.process.model.ConversationLinkConnector.ClassMgr.createConversationLinkConnector(id,this);
    }

    public void removeConversationLinkConnector(String id)
    {
        org.semanticwb.process.model.ConversationLinkConnector.ClassMgr.removeConversationLinkConnector(id, this);
    }
    public boolean hasConversationLinkConnector(String id)
    {
        return org.semanticwb.process.model.ConversationLinkConnector.ClassMgr.hasConversationLinkConnector(id, this);
    }

    public org.semanticwb.process.model.Activity getActivity(String id)
    {
        return org.semanticwb.process.model.Activity.ClassMgr.getActivity(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Activity> listActivities()
    {
        return org.semanticwb.process.model.Activity.ClassMgr.listActivities(this);
    }

    public org.semanticwb.process.model.Activity createActivity(String id)
    {
        return org.semanticwb.process.model.Activity.ClassMgr.createActivity(id,this);
    }

    public org.semanticwb.process.model.Activity createActivity()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Activity);
        return org.semanticwb.process.model.Activity.ClassMgr.createActivity(String.valueOf(id),this);
    } 

    public void removeActivity(String id)
    {
        org.semanticwb.process.model.Activity.ClassMgr.removeActivity(id, this);
    }
    public boolean hasActivity(String id)
    {
        return org.semanticwb.process.model.Activity.ClassMgr.hasActivity(id, this);
    }

    public org.semanticwb.process.model.TimerEventDefinition getTimerEventDefinition(String id)
    {
        return org.semanticwb.process.model.TimerEventDefinition.ClassMgr.getTimerEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitions()
    {
        return org.semanticwb.process.model.TimerEventDefinition.ClassMgr.listTimerEventDefinitions(this);
    }

    public org.semanticwb.process.model.TimerEventDefinition createTimerEventDefinition(String id)
    {
        return org.semanticwb.process.model.TimerEventDefinition.ClassMgr.createTimerEventDefinition(id,this);
    }

    public org.semanticwb.process.model.TimerEventDefinition createTimerEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_TimerEventDefinition);
        return org.semanticwb.process.model.TimerEventDefinition.ClassMgr.createTimerEventDefinition(String.valueOf(id),this);
    } 

    public void removeTimerEventDefinition(String id)
    {
        org.semanticwb.process.model.TimerEventDefinition.ClassMgr.removeTimerEventDefinition(id, this);
    }
    public boolean hasTimerEventDefinition(String id)
    {
        return org.semanticwb.process.model.TimerEventDefinition.ClassMgr.hasTimerEventDefinition(id, this);
    }

    public org.semanticwb.process.model.Group getGroup(String id)
    {
        return org.semanticwb.process.model.Group.ClassMgr.getGroup(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Group> listGroups()
    {
        return org.semanticwb.process.model.Group.ClassMgr.listGroups(this);
    }

    public org.semanticwb.process.model.Group createGroup(String id)
    {
        return org.semanticwb.process.model.Group.ClassMgr.createGroup(id,this);
    }

    public org.semanticwb.process.model.Group createGroup()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Group);
        return org.semanticwb.process.model.Group.ClassMgr.createGroup(String.valueOf(id),this);
    } 

    public void removeGroup(String id)
    {
        org.semanticwb.process.model.Group.ClassMgr.removeGroup(id, this);
    }
    public boolean hasGroup(String id)
    {
        return org.semanticwb.process.model.Group.ClassMgr.hasGroup(id, this);
    }

    public org.semanticwb.process.model.ImplicitThrowEvent getImplicitThrowEvent(String id)
    {
        return org.semanticwb.process.model.ImplicitThrowEvent.ClassMgr.getImplicitThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ImplicitThrowEvent> listImplicitThrowEvents()
    {
        return org.semanticwb.process.model.ImplicitThrowEvent.ClassMgr.listImplicitThrowEvents(this);
    }

    public org.semanticwb.process.model.ImplicitThrowEvent createImplicitThrowEvent(String id)
    {
        return org.semanticwb.process.model.ImplicitThrowEvent.ClassMgr.createImplicitThrowEvent(id,this);
    }

    public org.semanticwb.process.model.ImplicitThrowEvent createImplicitThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ImplicitThrowEvent);
        return org.semanticwb.process.model.ImplicitThrowEvent.ClassMgr.createImplicitThrowEvent(String.valueOf(id),this);
    } 

    public void removeImplicitThrowEvent(String id)
    {
        org.semanticwb.process.model.ImplicitThrowEvent.ClassMgr.removeImplicitThrowEvent(id, this);
    }
    public boolean hasImplicitThrowEvent(String id)
    {
        return org.semanticwb.process.model.ImplicitThrowEvent.ClassMgr.hasImplicitThrowEvent(id, this);
    }

    public org.semanticwb.process.model.ErrorEventDefinition getErrorEventDefinition(String id)
    {
        return org.semanticwb.process.model.ErrorEventDefinition.ClassMgr.getErrorEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ErrorEventDefinition> listErrorEventDefinitions()
    {
        return org.semanticwb.process.model.ErrorEventDefinition.ClassMgr.listErrorEventDefinitions(this);
    }

    public org.semanticwb.process.model.ErrorEventDefinition createErrorEventDefinition(String id)
    {
        return org.semanticwb.process.model.ErrorEventDefinition.ClassMgr.createErrorEventDefinition(id,this);
    }

    public org.semanticwb.process.model.ErrorEventDefinition createErrorEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ErrorEventDefinition);
        return org.semanticwb.process.model.ErrorEventDefinition.ClassMgr.createErrorEventDefinition(String.valueOf(id),this);
    } 

    public void removeErrorEventDefinition(String id)
    {
        org.semanticwb.process.model.ErrorEventDefinition.ClassMgr.removeErrorEventDefinition(id, this);
    }
    public boolean hasErrorEventDefinition(String id)
    {
        return org.semanticwb.process.model.ErrorEventDefinition.ClassMgr.hasErrorEventDefinition(id, this);
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

    public org.semanticwb.process.model.DataStoreShape getDataStoreShape(String id)
    {
        return org.semanticwb.process.model.DataStoreShape.ClassMgr.getDataStoreShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataStoreShape> listDataStoreShapes()
    {
        return org.semanticwb.process.model.DataStoreShape.ClassMgr.listDataStoreShapes(this);
    }

    public org.semanticwb.process.model.DataStoreShape createDataStoreShape(String id)
    {
        return org.semanticwb.process.model.DataStoreShape.ClassMgr.createDataStoreShape(id,this);
    }

    public org.semanticwb.process.model.DataStoreShape createDataStoreShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataStoreShape);
        return org.semanticwb.process.model.DataStoreShape.ClassMgr.createDataStoreShape(String.valueOf(id),this);
    } 

    public void removeDataStoreShape(String id)
    {
        org.semanticwb.process.model.DataStoreShape.ClassMgr.removeDataStoreShape(id, this);
    }
    public boolean hasDataStoreShape(String id)
    {
        return org.semanticwb.process.model.DataStoreShape.ClassMgr.hasDataStoreShape(id, this);
    }

    public org.semanticwb.process.model.MessageFlowAssociation getMessageFlowAssociation(String id)
    {
        return org.semanticwb.process.model.MessageFlowAssociation.ClassMgr.getMessageFlowAssociation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageFlowAssociation> listMessageFlowAssociations()
    {
        return org.semanticwb.process.model.MessageFlowAssociation.ClassMgr.listMessageFlowAssociations(this);
    }

    public org.semanticwb.process.model.MessageFlowAssociation createMessageFlowAssociation(String id)
    {
        return org.semanticwb.process.model.MessageFlowAssociation.ClassMgr.createMessageFlowAssociation(id,this);
    }

    public org.semanticwb.process.model.MessageFlowAssociation createMessageFlowAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MessageFlowAssociation);
        return org.semanticwb.process.model.MessageFlowAssociation.ClassMgr.createMessageFlowAssociation(String.valueOf(id),this);
    } 

    public void removeMessageFlowAssociation(String id)
    {
        org.semanticwb.process.model.MessageFlowAssociation.ClassMgr.removeMessageFlowAssociation(id, this);
    }
    public boolean hasMessageFlowAssociation(String id)
    {
        return org.semanticwb.process.model.MessageFlowAssociation.ClassMgr.hasMessageFlowAssociation(id, this);
    }

    public org.semanticwb.process.model.GlobalManualTask getGlobalManualTask(String id)
    {
        return org.semanticwb.process.model.GlobalManualTask.ClassMgr.getGlobalManualTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GlobalManualTask> listGlobalManualTasks()
    {
        return org.semanticwb.process.model.GlobalManualTask.ClassMgr.listGlobalManualTasks(this);
    }

    public org.semanticwb.process.model.GlobalManualTask createGlobalManualTask(String id)
    {
        return org.semanticwb.process.model.GlobalManualTask.ClassMgr.createGlobalManualTask(id,this);
    }

    public org.semanticwb.process.model.GlobalManualTask createGlobalManualTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GlobalManualTask);
        return org.semanticwb.process.model.GlobalManualTask.ClassMgr.createGlobalManualTask(String.valueOf(id),this);
    } 

    public void removeGlobalManualTask(String id)
    {
        org.semanticwb.process.model.GlobalManualTask.ClassMgr.removeGlobalManualTask(id, this);
    }
    public boolean hasGlobalManualTask(String id)
    {
        return org.semanticwb.process.model.GlobalManualTask.ClassMgr.hasGlobalManualTask(id, this);
    }

    public org.semanticwb.process.model.CancelEventDefinition getCancelEventDefinition(String id)
    {
        return org.semanticwb.process.model.CancelEventDefinition.ClassMgr.getCancelEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CancelEventDefinition> listCancelEventDefinitions()
    {
        return org.semanticwb.process.model.CancelEventDefinition.ClassMgr.listCancelEventDefinitions(this);
    }

    public org.semanticwb.process.model.CancelEventDefinition createCancelEventDefinition(String id)
    {
        return org.semanticwb.process.model.CancelEventDefinition.ClassMgr.createCancelEventDefinition(id,this);
    }

    public void removeCancelEventDefinition(String id)
    {
        org.semanticwb.process.model.CancelEventDefinition.ClassMgr.removeCancelEventDefinition(id, this);
    }
    public boolean hasCancelEventDefinition(String id)
    {
        return org.semanticwb.process.model.CancelEventDefinition.ClassMgr.hasCancelEventDefinition(id, this);
    }

    public org.semanticwb.process.model.NormalFlow getNormalFlow(String id)
    {
        return org.semanticwb.process.model.NormalFlow.ClassMgr.getNormalFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.NormalFlow> listNormalFlows()
    {
        return org.semanticwb.process.model.NormalFlow.ClassMgr.listNormalFlows(this);
    }

    public org.semanticwb.process.model.NormalFlow createNormalFlow(String id)
    {
        return org.semanticwb.process.model.NormalFlow.ClassMgr.createNormalFlow(id,this);
    }

    public org.semanticwb.process.model.NormalFlow createNormalFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swp_NormalFlow);
        return org.semanticwb.process.model.NormalFlow.ClassMgr.createNormalFlow(String.valueOf(id),this);
    } 

    public void removeNormalFlow(String id)
    {
        org.semanticwb.process.model.NormalFlow.ClassMgr.removeNormalFlow(id, this);
    }
    public boolean hasNormalFlow(String id)
    {
        return org.semanticwb.process.model.NormalFlow.ClassMgr.hasNormalFlow(id, this);
    }

    public org.semanticwb.process.model.CompensateEventDefinition getCompensateEventDefinition(String id)
    {
        return org.semanticwb.process.model.CompensateEventDefinition.ClassMgr.getCompensateEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CompensateEventDefinition> listCompensateEventDefinitions()
    {
        return org.semanticwb.process.model.CompensateEventDefinition.ClassMgr.listCompensateEventDefinitions(this);
    }

    public org.semanticwb.process.model.CompensateEventDefinition createCompensateEventDefinition(String id)
    {
        return org.semanticwb.process.model.CompensateEventDefinition.ClassMgr.createCompensateEventDefinition(id,this);
    }

    public org.semanticwb.process.model.CompensateEventDefinition createCompensateEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CompensateEventDefinition);
        return org.semanticwb.process.model.CompensateEventDefinition.ClassMgr.createCompensateEventDefinition(String.valueOf(id),this);
    } 

    public void removeCompensateEventDefinition(String id)
    {
        org.semanticwb.process.model.CompensateEventDefinition.ClassMgr.removeCompensateEventDefinition(id, this);
    }
    public boolean hasCompensateEventDefinition(String id)
    {
        return org.semanticwb.process.model.CompensateEventDefinition.ClassMgr.hasCompensateEventDefinition(id, this);
    }

    public org.semanticwb.process.model.DataInput getDataInput(String id)
    {
        return org.semanticwb.process.model.DataInput.ClassMgr.getDataInput(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataInput> listDataInputs()
    {
        return org.semanticwb.process.model.DataInput.ClassMgr.listDataInputs(this);
    }

    public org.semanticwb.process.model.DataInput createDataInput(String id)
    {
        return org.semanticwb.process.model.DataInput.ClassMgr.createDataInput(id,this);
    }

    public org.semanticwb.process.model.DataInput createDataInput()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataInput);
        return org.semanticwb.process.model.DataInput.ClassMgr.createDataInput(String.valueOf(id),this);
    } 

    public void removeDataInput(String id)
    {
        org.semanticwb.process.model.DataInput.ClassMgr.removeDataInput(id, this);
    }
    public boolean hasDataInput(String id)
    {
        return org.semanticwb.process.model.DataInput.ClassMgr.hasDataInput(id, this);
    }

    public org.semanticwb.process.model.PotentialOwner getPotentialOwner(String id)
    {
        return org.semanticwb.process.model.PotentialOwner.ClassMgr.getPotentialOwner(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwners()
    {
        return org.semanticwb.process.model.PotentialOwner.ClassMgr.listPotentialOwners(this);
    }

    public org.semanticwb.process.model.PotentialOwner createPotentialOwner(String id)
    {
        return org.semanticwb.process.model.PotentialOwner.ClassMgr.createPotentialOwner(id,this);
    }

    public org.semanticwb.process.model.PotentialOwner createPotentialOwner()
    {
        long id=getSemanticObject().getModel().getCounter(swp_PotentialOwner);
        return org.semanticwb.process.model.PotentialOwner.ClassMgr.createPotentialOwner(String.valueOf(id),this);
    } 

    public void removePotentialOwner(String id)
    {
        org.semanticwb.process.model.PotentialOwner.ClassMgr.removePotentialOwner(id, this);
    }
    public boolean hasPotentialOwner(String id)
    {
        return org.semanticwb.process.model.PotentialOwner.ClassMgr.hasPotentialOwner(id, this);
    }

    public org.semanticwb.process.model.CallActivity getCallActivity(String id)
    {
        return org.semanticwb.process.model.CallActivity.ClassMgr.getCallActivity(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivities()
    {
        return org.semanticwb.process.model.CallActivity.ClassMgr.listCallActivities(this);
    }

    public org.semanticwb.process.model.CallActivity createCallActivity(String id)
    {
        return org.semanticwb.process.model.CallActivity.ClassMgr.createCallActivity(id,this);
    }

    public org.semanticwb.process.model.CallActivity createCallActivity()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CallActivity);
        return org.semanticwb.process.model.CallActivity.ClassMgr.createCallActivity(String.valueOf(id),this);
    } 

    public void removeCallActivity(String id)
    {
        org.semanticwb.process.model.CallActivity.ClassMgr.removeCallActivity(id, this);
    }
    public boolean hasCallActivity(String id)
    {
        return org.semanticwb.process.model.CallActivity.ClassMgr.hasCallActivity(id, this);
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

    public org.semanticwb.process.model.ConditionEventDefinition getConditionEventDefinition(String id)
    {
        return org.semanticwb.process.model.ConditionEventDefinition.ClassMgr.getConditionEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitions()
    {
        return org.semanticwb.process.model.ConditionEventDefinition.ClassMgr.listConditionEventDefinitions(this);
    }

    public org.semanticwb.process.model.ConditionEventDefinition createConditionEventDefinition(String id)
    {
        return org.semanticwb.process.model.ConditionEventDefinition.ClassMgr.createConditionEventDefinition(id,this);
    }

    public org.semanticwb.process.model.ConditionEventDefinition createConditionEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ConditionEventDefinition);
        return org.semanticwb.process.model.ConditionEventDefinition.ClassMgr.createConditionEventDefinition(String.valueOf(id),this);
    } 

    public void removeConditionEventDefinition(String id)
    {
        org.semanticwb.process.model.ConditionEventDefinition.ClassMgr.removeConditionEventDefinition(id, this);
    }
    public boolean hasConditionEventDefinition(String id)
    {
        return org.semanticwb.process.model.ConditionEventDefinition.ClassMgr.hasConditionEventDefinition(id, this);
    }

    public org.semanticwb.process.model.Error getError(String id)
    {
        return org.semanticwb.process.model.Error.ClassMgr.getError(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Error> listErrors()
    {
        return org.semanticwb.process.model.Error.ClassMgr.listErrors(this);
    }

    public org.semanticwb.process.model.Error createError(String id)
    {
        return org.semanticwb.process.model.Error.ClassMgr.createError(id,this);
    }

    public org.semanticwb.process.model.Error createError()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Error);
        return org.semanticwb.process.model.Error.ClassMgr.createError(String.valueOf(id),this);
    } 

    public void removeError(String id)
    {
        org.semanticwb.process.model.Error.ClassMgr.removeError(id, this);
    }
    public boolean hasError(String id)
    {
        return org.semanticwb.process.model.Error.ClassMgr.hasError(id, this);
    }

    public org.semanticwb.process.model.BPMNStyleDefinition getBPMNStyleDefinition(String id)
    {
        return org.semanticwb.process.model.BPMNStyleDefinition.ClassMgr.getBPMNStyleDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNStyleDefinition> listBPMNStyleDefinitions()
    {
        return org.semanticwb.process.model.BPMNStyleDefinition.ClassMgr.listBPMNStyleDefinitions(this);
    }

    public org.semanticwb.process.model.BPMNStyleDefinition createBPMNStyleDefinition(String id)
    {
        return org.semanticwb.process.model.BPMNStyleDefinition.ClassMgr.createBPMNStyleDefinition(id,this);
    }

    public org.semanticwb.process.model.BPMNStyleDefinition createBPMNStyleDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNStyleDefinition);
        return org.semanticwb.process.model.BPMNStyleDefinition.ClassMgr.createBPMNStyleDefinition(String.valueOf(id),this);
    } 

    public void removeBPMNStyleDefinition(String id)
    {
        org.semanticwb.process.model.BPMNStyleDefinition.ClassMgr.removeBPMNStyleDefinition(id, this);
    }
    public boolean hasBPMNStyleDefinition(String id)
    {
        return org.semanticwb.process.model.BPMNStyleDefinition.ClassMgr.hasBPMNStyleDefinition(id, this);
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

    public org.semanticwb.process.model.EventBasedGateway createEventBasedGateway()
    {
        long id=getSemanticObject().getModel().getCounter(swp_EventBasedGateway);
        return org.semanticwb.process.model.EventBasedGateway.ClassMgr.createEventBasedGateway(String.valueOf(id),this);
    } 

    public void removeEventBasedGateway(String id)
    {
        org.semanticwb.process.model.EventBasedGateway.ClassMgr.removeEventBasedGateway(id, this);
    }
    public boolean hasEventBasedGateway(String id)
    {
        return org.semanticwb.process.model.EventBasedGateway.ClassMgr.hasEventBasedGateway(id, this);
    }

    public org.semanticwb.process.model.Transaction getTransaction(String id)
    {
        return org.semanticwb.process.model.Transaction.ClassMgr.getTransaction(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Transaction> listTransactions()
    {
        return org.semanticwb.process.model.Transaction.ClassMgr.listTransactions(this);
    }

    public org.semanticwb.process.model.Transaction createTransaction(String id)
    {
        return org.semanticwb.process.model.Transaction.ClassMgr.createTransaction(id,this);
    }

    public org.semanticwb.process.model.Transaction createTransaction()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Transaction);
        return org.semanticwb.process.model.Transaction.ClassMgr.createTransaction(String.valueOf(id),this);
    } 

    public void removeTransaction(String id)
    {
        org.semanticwb.process.model.Transaction.ClassMgr.removeTransaction(id, this);
    }
    public boolean hasTransaction(String id)
    {
        return org.semanticwb.process.model.Transaction.ClassMgr.hasTransaction(id, this);
    }

    public org.semanticwb.process.model.DataStore getDataStore(String id)
    {
        return org.semanticwb.process.model.DataStore.ClassMgr.getDataStore(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataStore> listDataStores()
    {
        return org.semanticwb.process.model.DataStore.ClassMgr.listDataStores(this);
    }

    public org.semanticwb.process.model.DataStore createDataStore(String id)
    {
        return org.semanticwb.process.model.DataStore.ClassMgr.createDataStore(id,this);
    }

    public org.semanticwb.process.model.DataStore createDataStore()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataStore);
        return org.semanticwb.process.model.DataStore.ClassMgr.createDataStore(String.valueOf(id),this);
    } 

    public void removeDataStore(String id)
    {
        org.semanticwb.process.model.DataStore.ClassMgr.removeDataStore(id, this);
    }
    public boolean hasDataStore(String id)
    {
        return org.semanticwb.process.model.DataStore.ClassMgr.hasDataStore(id, this);
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

    public org.semanticwb.process.model.DataInputAssociation getDataInputAssociation(String id)
    {
        return org.semanticwb.process.model.DataInputAssociation.ClassMgr.getDataInputAssociation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataInputAssociation> listDataInputAssociations()
    {
        return org.semanticwb.process.model.DataInputAssociation.ClassMgr.listDataInputAssociations(this);
    }

    public org.semanticwb.process.model.DataInputAssociation createDataInputAssociation(String id)
    {
        return org.semanticwb.process.model.DataInputAssociation.ClassMgr.createDataInputAssociation(id,this);
    }

    public org.semanticwb.process.model.DataInputAssociation createDataInputAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataInputAssociation);
        return org.semanticwb.process.model.DataInputAssociation.ClassMgr.createDataInputAssociation(String.valueOf(id),this);
    } 

    public void removeDataInputAssociation(String id)
    {
        org.semanticwb.process.model.DataInputAssociation.ClassMgr.removeDataInputAssociation(id, this);
    }
    public boolean hasDataInputAssociation(String id)
    {
        return org.semanticwb.process.model.DataInputAssociation.ClassMgr.hasDataInputAssociation(id, this);
    }

    public org.semanticwb.process.model.EventShape getEventShape(String id)
    {
        return org.semanticwb.process.model.EventShape.ClassMgr.getEventShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.EventShape> listEventShapes()
    {
        return org.semanticwb.process.model.EventShape.ClassMgr.listEventShapes(this);
    }

    public org.semanticwb.process.model.EventShape createEventShape(String id)
    {
        return org.semanticwb.process.model.EventShape.ClassMgr.createEventShape(id,this);
    }

    public org.semanticwb.process.model.EventShape createEventShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_EventShape);
        return org.semanticwb.process.model.EventShape.ClassMgr.createEventShape(String.valueOf(id),this);
    } 

    public void removeEventShape(String id)
    {
        org.semanticwb.process.model.EventShape.ClassMgr.removeEventShape(id, this);
    }
    public boolean hasEventShape(String id)
    {
        return org.semanticwb.process.model.EventShape.ClassMgr.hasEventShape(id, this);
    }

    public org.semanticwb.process.model.FlowNodeShape getFlowNodeShape(String id)
    {
        return org.semanticwb.process.model.FlowNodeShape.ClassMgr.getFlowNodeShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.FlowNodeShape> listFlowNodeShapes()
    {
        return org.semanticwb.process.model.FlowNodeShape.ClassMgr.listFlowNodeShapes(this);
    }

    public org.semanticwb.process.model.FlowNodeShape createFlowNodeShape(String id)
    {
        return org.semanticwb.process.model.FlowNodeShape.ClassMgr.createFlowNodeShape(id,this);
    }

    public void removeFlowNodeShape(String id)
    {
        org.semanticwb.process.model.FlowNodeShape.ClassMgr.removeFlowNodeShape(id, this);
    }
    public boolean hasFlowNodeShape(String id)
    {
        return org.semanticwb.process.model.FlowNodeShape.ClassMgr.hasFlowNodeShape(id, this);
    }

    public org.semanticwb.process.model.Collaboration getCollaboration(String id)
    {
        return org.semanticwb.process.model.Collaboration.ClassMgr.getCollaboration(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Collaboration> listCollaborations()
    {
        return org.semanticwb.process.model.Collaboration.ClassMgr.listCollaborations(this);
    }

    public org.semanticwb.process.model.Collaboration createCollaboration(String id)
    {
        return org.semanticwb.process.model.Collaboration.ClassMgr.createCollaboration(id,this);
    }

    public org.semanticwb.process.model.Collaboration createCollaboration()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Collaboration);
        return org.semanticwb.process.model.Collaboration.ClassMgr.createCollaboration(String.valueOf(id),this);
    } 

    public void removeCollaboration(String id)
    {
        org.semanticwb.process.model.Collaboration.ClassMgr.removeCollaboration(id, this);
    }
    public boolean hasCollaboration(String id)
    {
        return org.semanticwb.process.model.Collaboration.ClassMgr.hasCollaboration(id, this);
    }

    public org.semanticwb.process.model.EscalateEventDefinition getEscalateEventDefinition(String id)
    {
        return org.semanticwb.process.model.EscalateEventDefinition.ClassMgr.getEscalateEventDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitions()
    {
        return org.semanticwb.process.model.EscalateEventDefinition.ClassMgr.listEscalateEventDefinitions(this);
    }

    public org.semanticwb.process.model.EscalateEventDefinition createEscalateEventDefinition(String id)
    {
        return org.semanticwb.process.model.EscalateEventDefinition.ClassMgr.createEscalateEventDefinition(id,this);
    }

    public org.semanticwb.process.model.EscalateEventDefinition createEscalateEventDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_EscalateEventDefinition);
        return org.semanticwb.process.model.EscalateEventDefinition.ClassMgr.createEscalateEventDefinition(String.valueOf(id),this);
    } 

    public void removeEscalateEventDefinition(String id)
    {
        org.semanticwb.process.model.EscalateEventDefinition.ClassMgr.removeEscalateEventDefinition(id, this);
    }
    public boolean hasEscalateEventDefinition(String id)
    {
        return org.semanticwb.process.model.EscalateEventDefinition.ClassMgr.hasEscalateEventDefinition(id, this);
    }

    public org.semanticwb.process.model.BPMNDiagram getBPMNDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNDiagram.ClassMgr.getBPMNDiagram(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNDiagram> listBPMNDiagrams()
    {
        return org.semanticwb.process.model.BPMNDiagram.ClassMgr.listBPMNDiagrams(this);
    }

    public org.semanticwb.process.model.BPMNDiagram createBPMNDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNDiagram.ClassMgr.createBPMNDiagram(id,this);
    }

    public org.semanticwb.process.model.BPMNDiagram createBPMNDiagram()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNDiagram);
        return org.semanticwb.process.model.BPMNDiagram.ClassMgr.createBPMNDiagram(String.valueOf(id),this);
    } 

    public void removeBPMNDiagram(String id)
    {
        org.semanticwb.process.model.BPMNDiagram.ClassMgr.removeBPMNDiagram(id, this);
    }
    public boolean hasBPMNDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNDiagram.ClassMgr.hasBPMNDiagram(id, this);
    }

    public org.semanticwb.process.model.ConversationCommunicationShape getConversationCommunicationShape(String id)
    {
        return org.semanticwb.process.model.ConversationCommunicationShape.ClassMgr.getConversationCommunicationShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ConversationCommunicationShape> listConversationCommunicationShapes()
    {
        return org.semanticwb.process.model.ConversationCommunicationShape.ClassMgr.listConversationCommunicationShapes(this);
    }

    public org.semanticwb.process.model.ConversationCommunicationShape createConversationCommunicationShape(String id)
    {
        return org.semanticwb.process.model.ConversationCommunicationShape.ClassMgr.createConversationCommunicationShape(id,this);
    }

    public void removeConversationCommunicationShape(String id)
    {
        org.semanticwb.process.model.ConversationCommunicationShape.ClassMgr.removeConversationCommunicationShape(id, this);
    }
    public boolean hasConversationCommunicationShape(String id)
    {
        return org.semanticwb.process.model.ConversationCommunicationShape.ClassMgr.hasConversationCommunicationShape(id, this);
    }

    public org.semanticwb.process.model.DataAssociation getDataAssociation(String id)
    {
        return org.semanticwb.process.model.DataAssociation.ClassMgr.getDataAssociation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataAssociation> listDataAssociations()
    {
        return org.semanticwb.process.model.DataAssociation.ClassMgr.listDataAssociations(this);
    }

    public org.semanticwb.process.model.DataAssociation createDataAssociation(String id)
    {
        return org.semanticwb.process.model.DataAssociation.ClassMgr.createDataAssociation(id,this);
    }

    public org.semanticwb.process.model.DataAssociation createDataAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataAssociation);
        return org.semanticwb.process.model.DataAssociation.ClassMgr.createDataAssociation(String.valueOf(id),this);
    } 

    public void removeDataAssociation(String id)
    {
        org.semanticwb.process.model.DataAssociation.ClassMgr.removeDataAssociation(id, this);
    }
    public boolean hasDataAssociation(String id)
    {
        return org.semanticwb.process.model.DataAssociation.ClassMgr.hasDataAssociation(id, this);
    }

    public org.semanticwb.process.model.CalledSubProcessShape getCalledSubProcessShape(String id)
    {
        return org.semanticwb.process.model.CalledSubProcessShape.ClassMgr.getCalledSubProcessShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CalledSubProcessShape> listCalledSubProcessShapes()
    {
        return org.semanticwb.process.model.CalledSubProcessShape.ClassMgr.listCalledSubProcessShapes(this);
    }

    public org.semanticwb.process.model.CalledSubProcessShape createCalledSubProcessShape(String id)
    {
        return org.semanticwb.process.model.CalledSubProcessShape.ClassMgr.createCalledSubProcessShape(id,this);
    }

    public org.semanticwb.process.model.CalledSubProcessShape createCalledSubProcessShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CalledSubProcessShape);
        return org.semanticwb.process.model.CalledSubProcessShape.ClassMgr.createCalledSubProcessShape(String.valueOf(id),this);
    } 

    public void removeCalledSubProcessShape(String id)
    {
        org.semanticwb.process.model.CalledSubProcessShape.ClassMgr.removeCalledSubProcessShape(id, this);
    }
    public boolean hasCalledSubProcessShape(String id)
    {
        return org.semanticwb.process.model.CalledSubProcessShape.ClassMgr.hasCalledSubProcessShape(id, this);
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

    public org.semanticwb.process.model.CommunicationShape getCommunicationShape(String id)
    {
        return org.semanticwb.process.model.CommunicationShape.ClassMgr.getCommunicationShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CommunicationShape> listCommunicationShapes()
    {
        return org.semanticwb.process.model.CommunicationShape.ClassMgr.listCommunicationShapes(this);
    }

    public org.semanticwb.process.model.CommunicationShape createCommunicationShape(String id)
    {
        return org.semanticwb.process.model.CommunicationShape.ClassMgr.createCommunicationShape(id,this);
    }

    public org.semanticwb.process.model.CommunicationShape createCommunicationShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CommunicationShape);
        return org.semanticwb.process.model.CommunicationShape.ClassMgr.createCommunicationShape(String.valueOf(id),this);
    } 

    public void removeCommunicationShape(String id)
    {
        org.semanticwb.process.model.CommunicationShape.ClassMgr.removeCommunicationShape(id, this);
    }
    public boolean hasCommunicationShape(String id)
    {
        return org.semanticwb.process.model.CommunicationShape.ClassMgr.hasCommunicationShape(id, this);
    }

    public org.semanticwb.process.model.ExtensionAttributeDefinition getExtensionAttributeDefinition(String id)
    {
        return org.semanticwb.process.model.ExtensionAttributeDefinition.ClassMgr.getExtensionAttributeDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ExtensionAttributeDefinition> listExtensionAttributeDefinitions()
    {
        return org.semanticwb.process.model.ExtensionAttributeDefinition.ClassMgr.listExtensionAttributeDefinitions(this);
    }

    public org.semanticwb.process.model.ExtensionAttributeDefinition createExtensionAttributeDefinition(String id)
    {
        return org.semanticwb.process.model.ExtensionAttributeDefinition.ClassMgr.createExtensionAttributeDefinition(id,this);
    }

    public org.semanticwb.process.model.ExtensionAttributeDefinition createExtensionAttributeDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ExtensionAttributeDefinition);
        return org.semanticwb.process.model.ExtensionAttributeDefinition.ClassMgr.createExtensionAttributeDefinition(String.valueOf(id),this);
    } 

    public void removeExtensionAttributeDefinition(String id)
    {
        org.semanticwb.process.model.ExtensionAttributeDefinition.ClassMgr.removeExtensionAttributeDefinition(id, this);
    }
    public boolean hasExtensionAttributeDefinition(String id)
    {
        return org.semanticwb.process.model.ExtensionAttributeDefinition.ClassMgr.hasExtensionAttributeDefinition(id, this);
    }

    public org.semanticwb.process.model.ItemDefinition getItemDefinition(String id)
    {
        return org.semanticwb.process.model.ItemDefinition.ClassMgr.getItemDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitions()
    {
        return org.semanticwb.process.model.ItemDefinition.ClassMgr.listItemDefinitions(this);
    }

    public org.semanticwb.process.model.ItemDefinition createItemDefinition(String id)
    {
        return org.semanticwb.process.model.ItemDefinition.ClassMgr.createItemDefinition(id,this);
    }

    public org.semanticwb.process.model.ItemDefinition createItemDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ItemDefinition);
        return org.semanticwb.process.model.ItemDefinition.ClassMgr.createItemDefinition(String.valueOf(id),this);
    } 

    public void removeItemDefinition(String id)
    {
        org.semanticwb.process.model.ItemDefinition.ClassMgr.removeItemDefinition(id, this);
    }
    public boolean hasItemDefinition(String id)
    {
        return org.semanticwb.process.model.ItemDefinition.ClassMgr.hasItemDefinition(id, this);
    }

    public org.semanticwb.process.model.BPMNCollaborationDiagram getBPMNCollaborationDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNCollaborationDiagram.ClassMgr.getBPMNCollaborationDiagram(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNCollaborationDiagram> listBPMNCollaborationDiagrams()
    {
        return org.semanticwb.process.model.BPMNCollaborationDiagram.ClassMgr.listBPMNCollaborationDiagrams(this);
    }

    public org.semanticwb.process.model.BPMNCollaborationDiagram createBPMNCollaborationDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNCollaborationDiagram.ClassMgr.createBPMNCollaborationDiagram(id,this);
    }

    public org.semanticwb.process.model.BPMNCollaborationDiagram createBPMNCollaborationDiagram()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNCollaborationDiagram);
        return org.semanticwb.process.model.BPMNCollaborationDiagram.ClassMgr.createBPMNCollaborationDiagram(String.valueOf(id),this);
    } 

    public void removeBPMNCollaborationDiagram(String id)
    {
        org.semanticwb.process.model.BPMNCollaborationDiagram.ClassMgr.removeBPMNCollaborationDiagram(id, this);
    }
    public boolean hasBPMNCollaborationDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNCollaborationDiagram.ClassMgr.hasBPMNCollaborationDiagram(id, this);
    }

    public org.semanticwb.process.model.Auditing getAuditing(String id)
    {
        return org.semanticwb.process.model.Auditing.ClassMgr.getAuditing(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Auditing> listAuditings()
    {
        return org.semanticwb.process.model.Auditing.ClassMgr.listAuditings(this);
    }

    public org.semanticwb.process.model.Auditing createAuditing(String id)
    {
        return org.semanticwb.process.model.Auditing.ClassMgr.createAuditing(id,this);
    }

    public org.semanticwb.process.model.Auditing createAuditing()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Auditing);
        return org.semanticwb.process.model.Auditing.ClassMgr.createAuditing(String.valueOf(id),this);
    } 

    public void removeAuditing(String id)
    {
        org.semanticwb.process.model.Auditing.ClassMgr.removeAuditing(id, this);
    }
    public boolean hasAuditing(String id)
    {
        return org.semanticwb.process.model.Auditing.ClassMgr.hasAuditing(id, this);
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

    public void removeProcessInstance(String id)
    {
        org.semanticwb.process.model.ProcessInstance.ClassMgr.removeProcessInstance(id, this);
    }
    public boolean hasProcessInstance(String id)
    {
        return org.semanticwb.process.model.ProcessInstance.ClassMgr.hasProcessInstance(id, this);
    }

    public org.semanticwb.process.model.BPMNConversationDiagram getBPMNConversationDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNConversationDiagram.ClassMgr.getBPMNConversationDiagram(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNConversationDiagram> listBPMNConversationDiagrams()
    {
        return org.semanticwb.process.model.BPMNConversationDiagram.ClassMgr.listBPMNConversationDiagrams(this);
    }

    public org.semanticwb.process.model.BPMNConversationDiagram createBPMNConversationDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNConversationDiagram.ClassMgr.createBPMNConversationDiagram(id,this);
    }

    public org.semanticwb.process.model.BPMNConversationDiagram createBPMNConversationDiagram()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNConversationDiagram);
        return org.semanticwb.process.model.BPMNConversationDiagram.ClassMgr.createBPMNConversationDiagram(String.valueOf(id),this);
    } 

    public void removeBPMNConversationDiagram(String id)
    {
        org.semanticwb.process.model.BPMNConversationDiagram.ClassMgr.removeBPMNConversationDiagram(id, this);
    }
    public boolean hasBPMNConversationDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNConversationDiagram.ClassMgr.hasBPMNConversationDiagram(id, this);
    }

    public org.semanticwb.process.model.AssociationConnector getAssociationConnector(String id)
    {
        return org.semanticwb.process.model.AssociationConnector.ClassMgr.getAssociationConnector(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.AssociationConnector> listAssociationConnectors()
    {
        return org.semanticwb.process.model.AssociationConnector.ClassMgr.listAssociationConnectors(this);
    }

    public org.semanticwb.process.model.AssociationConnector createAssociationConnector(String id)
    {
        return org.semanticwb.process.model.AssociationConnector.ClassMgr.createAssociationConnector(id,this);
    }

    public void removeAssociationConnector(String id)
    {
        org.semanticwb.process.model.AssociationConnector.ClassMgr.removeAssociationConnector(id, this);
    }
    public boolean hasAssociationConnector(String id)
    {
        return org.semanticwb.process.model.AssociationConnector.ClassMgr.hasAssociationConnector(id, this);
    }

    public org.semanticwb.process.model.ConversationAssociation getConversationAssociation(String id)
    {
        return org.semanticwb.process.model.ConversationAssociation.ClassMgr.getConversationAssociation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ConversationAssociation> listConversationAssociations()
    {
        return org.semanticwb.process.model.ConversationAssociation.ClassMgr.listConversationAssociations(this);
    }

    public org.semanticwb.process.model.ConversationAssociation createConversationAssociation(String id)
    {
        return org.semanticwb.process.model.ConversationAssociation.ClassMgr.createConversationAssociation(id,this);
    }

    public org.semanticwb.process.model.ConversationAssociation createConversationAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ConversationAssociation);
        return org.semanticwb.process.model.ConversationAssociation.ClassMgr.createConversationAssociation(String.valueOf(id),this);
    } 

    public void removeConversationAssociation(String id)
    {
        org.semanticwb.process.model.ConversationAssociation.ClassMgr.removeConversationAssociation(id, this);
    }
    public boolean hasConversationAssociation(String id)
    {
        return org.semanticwb.process.model.ConversationAssociation.ClassMgr.hasConversationAssociation(id, this);
    }

    public org.semanticwb.process.model.OutputSet getOutputSet(String id)
    {
        return org.semanticwb.process.model.OutputSet.ClassMgr.getOutputSet(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSets()
    {
        return org.semanticwb.process.model.OutputSet.ClassMgr.listOutputSets(this);
    }

    public org.semanticwb.process.model.OutputSet createOutputSet(String id)
    {
        return org.semanticwb.process.model.OutputSet.ClassMgr.createOutputSet(id,this);
    }

    public org.semanticwb.process.model.OutputSet createOutputSet()
    {
        long id=getSemanticObject().getModel().getCounter(swp_OutputSet);
        return org.semanticwb.process.model.OutputSet.ClassMgr.createOutputSet(String.valueOf(id),this);
    } 

    public void removeOutputSet(String id)
    {
        org.semanticwb.process.model.OutputSet.ClassMgr.removeOutputSet(id, this);
    }
    public boolean hasOutputSet(String id)
    {
        return org.semanticwb.process.model.OutputSet.ClassMgr.hasOutputSet(id, this);
    }

    public org.semanticwb.process.model.DataObject getDataObject(String id)
    {
        return org.semanticwb.process.model.DataObject.ClassMgr.getDataObject(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjects()
    {
        return org.semanticwb.process.model.DataObject.ClassMgr.listDataObjects(this);
    }

    public org.semanticwb.process.model.DataObject createDataObject(String id)
    {
        return org.semanticwb.process.model.DataObject.ClassMgr.createDataObject(id,this);
    }

    public org.semanticwb.process.model.DataObject createDataObject()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataObject);
        return org.semanticwb.process.model.DataObject.ClassMgr.createDataObject(String.valueOf(id),this);
    } 

    public void removeDataObject(String id)
    {
        org.semanticwb.process.model.DataObject.ClassMgr.removeDataObject(id, this);
    }
    public boolean hasDataObject(String id)
    {
        return org.semanticwb.process.model.DataObject.ClassMgr.hasDataObject(id, this);
    }

    public org.semanticwb.process.model.BPMNProcessDiagram getBPMNProcessDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNProcessDiagram.ClassMgr.getBPMNProcessDiagram(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNProcessDiagram> listBPMNProcessDiagrams()
    {
        return org.semanticwb.process.model.BPMNProcessDiagram.ClassMgr.listBPMNProcessDiagrams(this);
    }

    public org.semanticwb.process.model.BPMNProcessDiagram createBPMNProcessDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNProcessDiagram.ClassMgr.createBPMNProcessDiagram(id,this);
    }

    public org.semanticwb.process.model.BPMNProcessDiagram createBPMNProcessDiagram()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNProcessDiagram);
        return org.semanticwb.process.model.BPMNProcessDiagram.ClassMgr.createBPMNProcessDiagram(String.valueOf(id),this);
    } 

    public void removeBPMNProcessDiagram(String id)
    {
        org.semanticwb.process.model.BPMNProcessDiagram.ClassMgr.removeBPMNProcessDiagram(id, this);
    }
    public boolean hasBPMNProcessDiagram(String id)
    {
        return org.semanticwb.process.model.BPMNProcessDiagram.ClassMgr.hasBPMNProcessDiagram(id, this);
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

    public org.semanticwb.process.model.Assignment getAssignment(String id)
    {
        return org.semanticwb.process.model.Assignment.ClassMgr.getAssignment(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignments()
    {
        return org.semanticwb.process.model.Assignment.ClassMgr.listAssignments(this);
    }

    public org.semanticwb.process.model.Assignment createAssignment(String id)
    {
        return org.semanticwb.process.model.Assignment.ClassMgr.createAssignment(id,this);
    }

    public org.semanticwb.process.model.Assignment createAssignment()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Assignment);
        return org.semanticwb.process.model.Assignment.ClassMgr.createAssignment(String.valueOf(id),this);
    } 

    public void removeAssignment(String id)
    {
        org.semanticwb.process.model.Assignment.ClassMgr.removeAssignment(id, this);
    }
    public boolean hasAssignment(String id)
    {
        return org.semanticwb.process.model.Assignment.ClassMgr.hasAssignment(id, this);
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

    public org.semanticwb.process.model.MessageShape getMessageShape(String id)
    {
        return org.semanticwb.process.model.MessageShape.ClassMgr.getMessageShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageShape> listMessageShapes()
    {
        return org.semanticwb.process.model.MessageShape.ClassMgr.listMessageShapes(this);
    }

    public org.semanticwb.process.model.MessageShape createMessageShape(String id)
    {
        return org.semanticwb.process.model.MessageShape.ClassMgr.createMessageShape(id,this);
    }

    public org.semanticwb.process.model.MessageShape createMessageShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MessageShape);
        return org.semanticwb.process.model.MessageShape.ClassMgr.createMessageShape(String.valueOf(id),this);
    } 

    public void removeMessageShape(String id)
    {
        org.semanticwb.process.model.MessageShape.ClassMgr.removeMessageShape(id, this);
    }
    public boolean hasMessageShape(String id)
    {
        return org.semanticwb.process.model.MessageShape.ClassMgr.hasMessageShape(id, this);
    }

    public org.semanticwb.process.model.DataOutputAssociation getDataOutputAssociation(String id)
    {
        return org.semanticwb.process.model.DataOutputAssociation.ClassMgr.getDataOutputAssociation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataOutputAssociation> listDataOutputAssociations()
    {
        return org.semanticwb.process.model.DataOutputAssociation.ClassMgr.listDataOutputAssociations(this);
    }

    public org.semanticwb.process.model.DataOutputAssociation createDataOutputAssociation(String id)
    {
        return org.semanticwb.process.model.DataOutputAssociation.ClassMgr.createDataOutputAssociation(id,this);
    }

    public org.semanticwb.process.model.DataOutputAssociation createDataOutputAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_DataOutputAssociation);
        return org.semanticwb.process.model.DataOutputAssociation.ClassMgr.createDataOutputAssociation(String.valueOf(id),this);
    } 

    public void removeDataOutputAssociation(String id)
    {
        org.semanticwb.process.model.DataOutputAssociation.ClassMgr.removeDataOutputAssociation(id, this);
    }
    public boolean hasDataOutputAssociation(String id)
    {
        return org.semanticwb.process.model.DataOutputAssociation.ClassMgr.hasDataOutputAssociation(id, this);
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

    public org.semanticwb.process.model.FlowNode createFlowNode()
    {
        long id=getSemanticObject().getModel().getCounter(swp_FlowNode);
        return org.semanticwb.process.model.FlowNode.ClassMgr.createFlowNode(String.valueOf(id),this);
    } 

    public void removeFlowNode(String id)
    {
        org.semanticwb.process.model.FlowNode.ClassMgr.removeFlowNode(id, this);
    }
    public boolean hasFlowNode(String id)
    {
        return org.semanticwb.process.model.FlowNode.ClassMgr.hasFlowNode(id, this);
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

    public org.semanticwb.process.model.Event createEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Event);
        return org.semanticwb.process.model.Event.ClassMgr.createEvent(String.valueOf(id),this);
    } 

    public void removeEvent(String id)
    {
        org.semanticwb.process.model.Event.ClassMgr.removeEvent(id, this);
    }
    public boolean hasEvent(String id)
    {
        return org.semanticwb.process.model.Event.ClassMgr.hasEvent(id, this);
    }

    public org.semanticwb.process.model.PartnerEntity getPartnerEntity(String id)
    {
        return org.semanticwb.process.model.PartnerEntity.ClassMgr.getPartnerEntity(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.PartnerEntity> listPartnerEntities()
    {
        return org.semanticwb.process.model.PartnerEntity.ClassMgr.listPartnerEntities(this);
    }

    public org.semanticwb.process.model.PartnerEntity createPartnerEntity(String id)
    {
        return org.semanticwb.process.model.PartnerEntity.ClassMgr.createPartnerEntity(id,this);
    }

    public org.semanticwb.process.model.PartnerEntity createPartnerEntity()
    {
        long id=getSemanticObject().getModel().getCounter(swp_PartnerEntity);
        return org.semanticwb.process.model.PartnerEntity.ClassMgr.createPartnerEntity(String.valueOf(id),this);
    } 

    public void removePartnerEntity(String id)
    {
        org.semanticwb.process.model.PartnerEntity.ClassMgr.removePartnerEntity(id, this);
    }
    public boolean hasPartnerEntity(String id)
    {
        return org.semanticwb.process.model.PartnerEntity.ClassMgr.hasPartnerEntity(id, this);
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

    public org.semanticwb.process.model.ConversationNode getConversationNode(String id)
    {
        return org.semanticwb.process.model.ConversationNode.ClassMgr.getConversationNode(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ConversationNode> listConversationNodes()
    {
        return org.semanticwb.process.model.ConversationNode.ClassMgr.listConversationNodes(this);
    }

    public org.semanticwb.process.model.ConversationNode createConversationNode(String id)
    {
        return org.semanticwb.process.model.ConversationNode.ClassMgr.createConversationNode(id,this);
    }

    public org.semanticwb.process.model.ConversationNode createConversationNode()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ConversationNode);
        return org.semanticwb.process.model.ConversationNode.ClassMgr.createConversationNode(String.valueOf(id),this);
    } 

    public void removeConversationNode(String id)
    {
        org.semanticwb.process.model.ConversationNode.ClassMgr.removeConversationNode(id, this);
    }
    public boolean hasConversationNode(String id)
    {
        return org.semanticwb.process.model.ConversationNode.ClassMgr.hasConversationNode(id, this);
    }

    public org.semanticwb.process.model.InputSet getInputSet(String id)
    {
        return org.semanticwb.process.model.InputSet.ClassMgr.getInputSet(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSets()
    {
        return org.semanticwb.process.model.InputSet.ClassMgr.listInputSets(this);
    }

    public org.semanticwb.process.model.InputSet createInputSet(String id)
    {
        return org.semanticwb.process.model.InputSet.ClassMgr.createInputSet(id,this);
    }

    public org.semanticwb.process.model.InputSet createInputSet()
    {
        long id=getSemanticObject().getModel().getCounter(swp_InputSet);
        return org.semanticwb.process.model.InputSet.ClassMgr.createInputSet(String.valueOf(id),this);
    } 

    public void removeInputSet(String id)
    {
        org.semanticwb.process.model.InputSet.ClassMgr.removeInputSet(id, this);
    }
    public boolean hasInputSet(String id)
    {
        return org.semanticwb.process.model.InputSet.ClassMgr.hasInputSet(id, this);
    }

    public org.semanticwb.process.model.ParticipantMultiplicityInstance getParticipantMultiplicityInstance(String id)
    {
        return org.semanticwb.process.model.ParticipantMultiplicityInstance.ClassMgr.getParticipantMultiplicityInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ParticipantMultiplicityInstance> listParticipantMultiplicityInstances()
    {
        return org.semanticwb.process.model.ParticipantMultiplicityInstance.ClassMgr.listParticipantMultiplicityInstances(this);
    }

    public org.semanticwb.process.model.ParticipantMultiplicityInstance createParticipantMultiplicityInstance(String id)
    {
        return org.semanticwb.process.model.ParticipantMultiplicityInstance.ClassMgr.createParticipantMultiplicityInstance(id,this);
    }

    public void removeParticipantMultiplicityInstance(String id)
    {
        org.semanticwb.process.model.ParticipantMultiplicityInstance.ClassMgr.removeParticipantMultiplicityInstance(id, this);
    }
    public boolean hasParticipantMultiplicityInstance(String id)
    {
        return org.semanticwb.process.model.ParticipantMultiplicityInstance.ClassMgr.hasParticipantMultiplicityInstance(id, this);
    }

    public org.semanticwb.process.model.BPMNNode getBPMNNode(String id)
    {
        return org.semanticwb.process.model.BPMNNode.ClassMgr.getBPMNNode(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNNode> listBPMNNodes()
    {
        return org.semanticwb.process.model.BPMNNode.ClassMgr.listBPMNNodes(this);
    }

    public org.semanticwb.process.model.BPMNNode createBPMNNode(String id)
    {
        return org.semanticwb.process.model.BPMNNode.ClassMgr.createBPMNNode(id,this);
    }

    public org.semanticwb.process.model.BPMNNode createBPMNNode()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNNode);
        return org.semanticwb.process.model.BPMNNode.ClassMgr.createBPMNNode(String.valueOf(id),this);
    } 

    public void removeBPMNNode(String id)
    {
        org.semanticwb.process.model.BPMNNode.ClassMgr.removeBPMNNode(id, this);
    }
    public boolean hasBPMNNode(String id)
    {
        return org.semanticwb.process.model.BPMNNode.ClassMgr.hasBPMNNode(id, this);
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

    public org.semanticwb.process.model.ExtensionDefinition getExtensionDefinition(String id)
    {
        return org.semanticwb.process.model.ExtensionDefinition.ClassMgr.getExtensionDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ExtensionDefinition> listExtensionDefinitions()
    {
        return org.semanticwb.process.model.ExtensionDefinition.ClassMgr.listExtensionDefinitions(this);
    }

    public org.semanticwb.process.model.ExtensionDefinition createExtensionDefinition(String id)
    {
        return org.semanticwb.process.model.ExtensionDefinition.ClassMgr.createExtensionDefinition(id,this);
    }

    public org.semanticwb.process.model.ExtensionDefinition createExtensionDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ExtensionDefinition);
        return org.semanticwb.process.model.ExtensionDefinition.ClassMgr.createExtensionDefinition(String.valueOf(id),this);
    } 

    public void removeExtensionDefinition(String id)
    {
        org.semanticwb.process.model.ExtensionDefinition.ClassMgr.removeExtensionDefinition(id, this);
    }
    public boolean hasExtensionDefinition(String id)
    {
        return org.semanticwb.process.model.ExtensionDefinition.ClassMgr.hasExtensionDefinition(id, this);
    }

    public org.semanticwb.process.model.MultiInstanceLoopCharacteristics getMultiInstanceLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.MultiInstanceLoopCharacteristics.ClassMgr.getMultiInstanceLoopCharacteristics(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicses()
    {
        return org.semanticwb.process.model.MultiInstanceLoopCharacteristics.ClassMgr.listMultiInstanceLoopCharacteristicses(this);
    }

    public org.semanticwb.process.model.MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(id,this);
    }

    public org.semanticwb.process.model.MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics()
    {
        long id=getSemanticObject().getModel().getCounter(swp_MultiInstanceLoopCharacteristics);
        return org.semanticwb.process.model.MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(String.valueOf(id),this);
    } 

    public void removeMultiInstanceLoopCharacteristics(String id)
    {
        org.semanticwb.process.model.MultiInstanceLoopCharacteristics.ClassMgr.removeMultiInstanceLoopCharacteristics(id, this);
    }
    public boolean hasMultiInstanceLoopCharacteristics(String id)
    {
        return org.semanticwb.process.model.MultiInstanceLoopCharacteristics.ClassMgr.hasMultiInstanceLoopCharacteristics(id, this);
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

    public org.semanticwb.process.model.SubConversationShape getSubConversationShape(String id)
    {
        return org.semanticwb.process.model.SubConversationShape.ClassMgr.getSubConversationShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.SubConversationShape> listSubConversationShapes()
    {
        return org.semanticwb.process.model.SubConversationShape.ClassMgr.listSubConversationShapes(this);
    }

    public org.semanticwb.process.model.SubConversationShape createSubConversationShape(String id)
    {
        return org.semanticwb.process.model.SubConversationShape.ClassMgr.createSubConversationShape(id,this);
    }

    public org.semanticwb.process.model.SubConversationShape createSubConversationShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_SubConversationShape);
        return org.semanticwb.process.model.SubConversationShape.ClassMgr.createSubConversationShape(String.valueOf(id),this);
    } 

    public void removeSubConversationShape(String id)
    {
        org.semanticwb.process.model.SubConversationShape.ClassMgr.removeSubConversationShape(id, this);
    }
    public boolean hasSubConversationShape(String id)
    {
        return org.semanticwb.process.model.SubConversationShape.ClassMgr.hasSubConversationShape(id, this);
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

    public org.semanticwb.process.model.TextAnnotation getTextAnnotation(String id)
    {
        return org.semanticwb.process.model.TextAnnotation.ClassMgr.getTextAnnotation(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.TextAnnotation> listTextAnnotations()
    {
        return org.semanticwb.process.model.TextAnnotation.ClassMgr.listTextAnnotations(this);
    }

    public org.semanticwb.process.model.TextAnnotation createTextAnnotation(String id)
    {
        return org.semanticwb.process.model.TextAnnotation.ClassMgr.createTextAnnotation(id,this);
    }

    public org.semanticwb.process.model.TextAnnotation createTextAnnotation()
    {
        long id=getSemanticObject().getModel().getCounter(swp_TextAnnotation);
        return org.semanticwb.process.model.TextAnnotation.ClassMgr.createTextAnnotation(String.valueOf(id),this);
    } 

    public void removeTextAnnotation(String id)
    {
        org.semanticwb.process.model.TextAnnotation.ClassMgr.removeTextAnnotation(id, this);
    }
    public boolean hasTextAnnotation(String id)
    {
        return org.semanticwb.process.model.TextAnnotation.ClassMgr.hasTextAnnotation(id, this);
    }

    public org.semanticwb.process.model.Signal getSignal(String id)
    {
        return org.semanticwb.process.model.Signal.ClassMgr.getSignal(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Signal> listSignals()
    {
        return org.semanticwb.process.model.Signal.ClassMgr.listSignals(this);
    }

    public org.semanticwb.process.model.Signal createSignal(String id)
    {
        return org.semanticwb.process.model.Signal.ClassMgr.createSignal(id,this);
    }

    public org.semanticwb.process.model.Signal createSignal()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Signal);
        return org.semanticwb.process.model.Signal.ClassMgr.createSignal(String.valueOf(id),this);
    } 

    public void removeSignal(String id)
    {
        org.semanticwb.process.model.Signal.ClassMgr.removeSignal(id, this);
    }
    public boolean hasSignal(String id)
    {
        return org.semanticwb.process.model.Signal.ClassMgr.hasSignal(id, this);
    }

    public org.semanticwb.process.model.ChoreographyActivityShape getChoreographyActivityShape(String id)
    {
        return org.semanticwb.process.model.ChoreographyActivityShape.ClassMgr.getChoreographyActivityShape(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ChoreographyActivityShape> listChoreographyActivityShapes()
    {
        return org.semanticwb.process.model.ChoreographyActivityShape.ClassMgr.listChoreographyActivityShapes(this);
    }

    public org.semanticwb.process.model.ChoreographyActivityShape createChoreographyActivityShape(String id)
    {
        return org.semanticwb.process.model.ChoreographyActivityShape.ClassMgr.createChoreographyActivityShape(id,this);
    }

    public org.semanticwb.process.model.ChoreographyActivityShape createChoreographyActivityShape()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ChoreographyActivityShape);
        return org.semanticwb.process.model.ChoreographyActivityShape.ClassMgr.createChoreographyActivityShape(String.valueOf(id),this);
    } 

    public void removeChoreographyActivityShape(String id)
    {
        org.semanticwb.process.model.ChoreographyActivityShape.ClassMgr.removeChoreographyActivityShape(id, this);
    }
    public boolean hasChoreographyActivityShape(String id)
    {
        return org.semanticwb.process.model.ChoreographyActivityShape.ClassMgr.hasChoreographyActivityShape(id, this);
    }

    public org.semanticwb.process.model.CorrelationPropertyBinding getCorrelationPropertyBinding(String id)
    {
        return org.semanticwb.process.model.CorrelationPropertyBinding.ClassMgr.getCorrelationPropertyBinding(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindings()
    {
        return org.semanticwb.process.model.CorrelationPropertyBinding.ClassMgr.listCorrelationPropertyBindings(this);
    }

    public org.semanticwb.process.model.CorrelationPropertyBinding createCorrelationPropertyBinding(String id)
    {
        return org.semanticwb.process.model.CorrelationPropertyBinding.ClassMgr.createCorrelationPropertyBinding(id,this);
    }

    public org.semanticwb.process.model.CorrelationPropertyBinding createCorrelationPropertyBinding()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CorrelationPropertyBinding);
        return org.semanticwb.process.model.CorrelationPropertyBinding.ClassMgr.createCorrelationPropertyBinding(String.valueOf(id),this);
    } 

    public void removeCorrelationPropertyBinding(String id)
    {
        org.semanticwb.process.model.CorrelationPropertyBinding.ClassMgr.removeCorrelationPropertyBinding(id, this);
    }
    public boolean hasCorrelationPropertyBinding(String id)
    {
        return org.semanticwb.process.model.CorrelationPropertyBinding.ClassMgr.hasCorrelationPropertyBinding(id, this);
    }

    public org.semanticwb.process.model.CorrelationProperty getCorrelationProperty(String id)
    {
        return org.semanticwb.process.model.CorrelationProperty.ClassMgr.getCorrelationProperty(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationProperties()
    {
        return org.semanticwb.process.model.CorrelationProperty.ClassMgr.listCorrelationProperties(this);
    }

    public org.semanticwb.process.model.CorrelationProperty createCorrelationProperty(String id)
    {
        return org.semanticwb.process.model.CorrelationProperty.ClassMgr.createCorrelationProperty(id,this);
    }

    public org.semanticwb.process.model.CorrelationProperty createCorrelationProperty()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CorrelationProperty);
        return org.semanticwb.process.model.CorrelationProperty.ClassMgr.createCorrelationProperty(String.valueOf(id),this);
    } 

    public void removeCorrelationProperty(String id)
    {
        org.semanticwb.process.model.CorrelationProperty.ClassMgr.removeCorrelationProperty(id, this);
    }
    public boolean hasCorrelationProperty(String id)
    {
        return org.semanticwb.process.model.CorrelationProperty.ClassMgr.hasCorrelationProperty(id, this);
    }

    public org.semanticwb.process.model.GlobalChoreographyTask getGlobalChoreographyTask(String id)
    {
        return org.semanticwb.process.model.GlobalChoreographyTask.ClassMgr.getGlobalChoreographyTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.GlobalChoreographyTask> listGlobalChoreographyTasks()
    {
        return org.semanticwb.process.model.GlobalChoreographyTask.ClassMgr.listGlobalChoreographyTasks(this);
    }

    public org.semanticwb.process.model.GlobalChoreographyTask createGlobalChoreographyTask(String id)
    {
        return org.semanticwb.process.model.GlobalChoreographyTask.ClassMgr.createGlobalChoreographyTask(id,this);
    }

    public org.semanticwb.process.model.GlobalChoreographyTask createGlobalChoreographyTask()
    {
        long id=getSemanticObject().getModel().getCounter(swp_GlobalChoreographyTask);
        return org.semanticwb.process.model.GlobalChoreographyTask.ClassMgr.createGlobalChoreographyTask(String.valueOf(id),this);
    } 

    public void removeGlobalChoreographyTask(String id)
    {
        org.semanticwb.process.model.GlobalChoreographyTask.ClassMgr.removeGlobalChoreographyTask(id, this);
    }
    public boolean hasGlobalChoreographyTask(String id)
    {
        return org.semanticwb.process.model.GlobalChoreographyTask.ClassMgr.hasGlobalChoreographyTask(id, this);
    }

    public org.semanticwb.process.model.Definition getDefinition(String id)
    {
        return org.semanticwb.process.model.Definition.ClassMgr.getDefinition(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.Definition> listDefinitions()
    {
        return org.semanticwb.process.model.Definition.ClassMgr.listDefinitions(this);
    }

    public org.semanticwb.process.model.Definition createDefinition(String id)
    {
        return org.semanticwb.process.model.Definition.ClassMgr.createDefinition(id,this);
    }

    public org.semanticwb.process.model.Definition createDefinition()
    {
        long id=getSemanticObject().getModel().getCounter(swp_Definition);
        return org.semanticwb.process.model.Definition.ClassMgr.createDefinition(String.valueOf(id),this);
    } 

    public void removeDefinition(String id)
    {
        org.semanticwb.process.model.Definition.ClassMgr.removeDefinition(id, this);
    }
    public boolean hasDefinition(String id)
    {
        return org.semanticwb.process.model.Definition.ClassMgr.hasDefinition(id, this);
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

    public org.semanticwb.process.model.ChoreographyActivity getChoreographyActivity(String id)
    {
        return org.semanticwb.process.model.ChoreographyActivity.ClassMgr.getChoreographyActivity(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ChoreographyActivity> listChoreographyActivities()
    {
        return org.semanticwb.process.model.ChoreographyActivity.ClassMgr.listChoreographyActivities(this);
    }

    public org.semanticwb.process.model.ChoreographyActivity createChoreographyActivity(String id)
    {
        return org.semanticwb.process.model.ChoreographyActivity.ClassMgr.createChoreographyActivity(id,this);
    }

    public org.semanticwb.process.model.ChoreographyActivity createChoreographyActivity()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ChoreographyActivity);
        return org.semanticwb.process.model.ChoreographyActivity.ClassMgr.createChoreographyActivity(String.valueOf(id),this);
    } 

    public void removeChoreographyActivity(String id)
    {
        org.semanticwb.process.model.ChoreographyActivity.ClassMgr.removeChoreographyActivity(id, this);
    }
    public boolean hasChoreographyActivity(String id)
    {
        return org.semanticwb.process.model.ChoreographyActivity.ClassMgr.hasChoreographyActivity(id, this);
    }

    public org.semanticwb.process.model.ExceptionFlow getExceptionFlow(String id)
    {
        return org.semanticwb.process.model.ExceptionFlow.ClassMgr.getExceptionFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ExceptionFlow> listExceptionFlows()
    {
        return org.semanticwb.process.model.ExceptionFlow.ClassMgr.listExceptionFlows(this);
    }

    public org.semanticwb.process.model.ExceptionFlow createExceptionFlow(String id)
    {
        return org.semanticwb.process.model.ExceptionFlow.ClassMgr.createExceptionFlow(id,this);
    }

    public org.semanticwb.process.model.ExceptionFlow createExceptionFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ExceptionFlow);
        return org.semanticwb.process.model.ExceptionFlow.ClassMgr.createExceptionFlow(String.valueOf(id),this);
    } 

    public void removeExceptionFlow(String id)
    {
        org.semanticwb.process.model.ExceptionFlow.ClassMgr.removeExceptionFlow(id, this);
    }
    public boolean hasExceptionFlow(String id)
    {
        return org.semanticwb.process.model.ExceptionFlow.ClassMgr.hasExceptionFlow(id, this);
    }

    public org.semanticwb.process.model.InputOutputSpecification getInputOutputSpecification(String id)
    {
        return org.semanticwb.process.model.InputOutputSpecification.ClassMgr.getInputOutputSpecification(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.InputOutputSpecification> listInputOutputSpecifications()
    {
        return org.semanticwb.process.model.InputOutputSpecification.ClassMgr.listInputOutputSpecifications(this);
    }

    public org.semanticwb.process.model.InputOutputSpecification createInputOutputSpecification(String id)
    {
        return org.semanticwb.process.model.InputOutputSpecification.ClassMgr.createInputOutputSpecification(id,this);
    }

    public org.semanticwb.process.model.InputOutputSpecification createInputOutputSpecification()
    {
        long id=getSemanticObject().getModel().getCounter(swp_InputOutputSpecification);
        return org.semanticwb.process.model.InputOutputSpecification.ClassMgr.createInputOutputSpecification(String.valueOf(id),this);
    } 

    public void removeInputOutputSpecification(String id)
    {
        org.semanticwb.process.model.InputOutputSpecification.ClassMgr.removeInputOutputSpecification(id, this);
    }
    public boolean hasInputOutputSpecification(String id)
    {
        return org.semanticwb.process.model.InputOutputSpecification.ClassMgr.hasInputOutputSpecification(id, this);
    }

    public org.semanticwb.process.model.BPMNCompartment getBPMNCompartment(String id)
    {
        return org.semanticwb.process.model.BPMNCompartment.ClassMgr.getBPMNCompartment(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.BPMNCompartment> listBPMNCompartments()
    {
        return org.semanticwb.process.model.BPMNCompartment.ClassMgr.listBPMNCompartments(this);
    }

    public org.semanticwb.process.model.BPMNCompartment createBPMNCompartment(String id)
    {
        return org.semanticwb.process.model.BPMNCompartment.ClassMgr.createBPMNCompartment(id,this);
    }

    public org.semanticwb.process.model.BPMNCompartment createBPMNCompartment()
    {
        long id=getSemanticObject().getModel().getCounter(swp_BPMNCompartment);
        return org.semanticwb.process.model.BPMNCompartment.ClassMgr.createBPMNCompartment(String.valueOf(id),this);
    } 

    public void removeBPMNCompartment(String id)
    {
        org.semanticwb.process.model.BPMNCompartment.ClassMgr.removeBPMNCompartment(id, this);
    }
    public boolean hasBPMNCompartment(String id)
    {
        return org.semanticwb.process.model.BPMNCompartment.ClassMgr.hasBPMNCompartment(id, this);
    }

    public org.semanticwb.process.model.ProcessImport getProcessImport(String id)
    {
        return org.semanticwb.process.model.ProcessImport.ClassMgr.getProcessImport(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ProcessImport> listProcessImports()
    {
        return org.semanticwb.process.model.ProcessImport.ClassMgr.listProcessImports(this);
    }

    public org.semanticwb.process.model.ProcessImport createProcessImport(String id)
    {
        return org.semanticwb.process.model.ProcessImport.ClassMgr.createProcessImport(id,this);
    }

    public org.semanticwb.process.model.ProcessImport createProcessImport()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ProcessImport);
        return org.semanticwb.process.model.ProcessImport.ClassMgr.createProcessImport(String.valueOf(id),this);
    } 

    public void removeProcessImport(String id)
    {
        org.semanticwb.process.model.ProcessImport.ClassMgr.removeProcessImport(id, this);
    }
    public boolean hasProcessImport(String id)
    {
        return org.semanticwb.process.model.ProcessImport.ClassMgr.hasProcessImport(id, this);
    }

    public org.semanticwb.process.model.ThrowEvent getThrowEvent(String id)
    {
        return org.semanticwb.process.model.ThrowEvent.ClassMgr.getThrowEvent(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.ThrowEvent> listThrowEvents()
    {
        return org.semanticwb.process.model.ThrowEvent.ClassMgr.listThrowEvents(this);
    }

    public org.semanticwb.process.model.ThrowEvent createThrowEvent(String id)
    {
        return org.semanticwb.process.model.ThrowEvent.ClassMgr.createThrowEvent(id,this);
    }

    public org.semanticwb.process.model.ThrowEvent createThrowEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_ThrowEvent);
        return org.semanticwb.process.model.ThrowEvent.ClassMgr.createThrowEvent(String.valueOf(id),this);
    } 

    public void removeThrowEvent(String id)
    {
        org.semanticwb.process.model.ThrowEvent.ClassMgr.removeThrowEvent(id, this);
    }
    public boolean hasThrowEvent(String id)
    {
        return org.semanticwb.process.model.ThrowEvent.ClassMgr.hasThrowEvent(id, this);
    }

    public org.semanticwb.process.model.DataAssociationConnector getDataAssociationConnector(String id)
    {
        return org.semanticwb.process.model.DataAssociationConnector.ClassMgr.getDataAssociationConnector(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.DataAssociationConnector> listDataAssociationConnectors()
    {
        return org.semanticwb.process.model.DataAssociationConnector.ClassMgr.listDataAssociationConnectors(this);
    }

    public org.semanticwb.process.model.DataAssociationConnector createDataAssociationConnector(String id)
    {
        return org.semanticwb.process.model.DataAssociationConnector.ClassMgr.createDataAssociationConnector(id,this);
    }

    public void removeDataAssociationConnector(String id)
    {
        org.semanticwb.process.model.DataAssociationConnector.ClassMgr.removeDataAssociationConnector(id, this);
    }
    public boolean hasDataAssociationConnector(String id)
    {
        return org.semanticwb.process.model.DataAssociationConnector.ClassMgr.hasDataAssociationConnector(id, this);
    }

    public org.semanticwb.process.model.CompensationFlowConnector getCompensationFlowConnector(String id)
    {
        return org.semanticwb.process.model.CompensationFlowConnector.ClassMgr.getCompensationFlowConnector(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.CompensationFlowConnector> listCompensationFlowConnectors()
    {
        return org.semanticwb.process.model.CompensationFlowConnector.ClassMgr.listCompensationFlowConnectors(this);
    }

    public org.semanticwb.process.model.CompensationFlowConnector createCompensationFlowConnector(String id)
    {
        return org.semanticwb.process.model.CompensationFlowConnector.ClassMgr.createCompensationFlowConnector(id,this);
    }

    public void removeCompensationFlowConnector(String id)
    {
        org.semanticwb.process.model.CompensationFlowConnector.ClassMgr.removeCompensationFlowConnector(id, this);
    }
    public boolean hasCompensationFlowConnector(String id)
    {
        return org.semanticwb.process.model.CompensationFlowConnector.ClassMgr.hasCompensationFlowConnector(id, this);
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

    public org.semanticwb.process.model.MessageFlowConnector getMessageFlowConnector(String id)
    {
        return org.semanticwb.process.model.MessageFlowConnector.ClassMgr.getMessageFlowConnector(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.model.MessageFlowConnector> listMessageFlowConnectors()
    {
        return org.semanticwb.process.model.MessageFlowConnector.ClassMgr.listMessageFlowConnectors(this);
    }

    public org.semanticwb.process.model.MessageFlowConnector createMessageFlowConnector(String id)
    {
        return org.semanticwb.process.model.MessageFlowConnector.ClassMgr.createMessageFlowConnector(id,this);
    }

    public void removeMessageFlowConnector(String id)
    {
        org.semanticwb.process.model.MessageFlowConnector.ClassMgr.removeMessageFlowConnector(id, this);
    }
    public boolean hasMessageFlowConnector(String id)
    {
        return org.semanticwb.process.model.MessageFlowConnector.ClassMgr.hasMessageFlowConnector(id, this);
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

    public org.semanticwb.process.model.CatchEvent createCatchEvent()
    {
        long id=getSemanticObject().getModel().getCounter(swp_CatchEvent);
        return org.semanticwb.process.model.CatchEvent.ClassMgr.createCatchEvent(String.valueOf(id),this);
    } 

    public void removeCatchEvent(String id)
    {
        org.semanticwb.process.model.CatchEvent.ClassMgr.removeCatchEvent(id, this);
    }
    public boolean hasCatchEvent(String id)
    {
        return org.semanticwb.process.model.CatchEvent.ClassMgr.hasCatchEvent(id, this);
    }
}
