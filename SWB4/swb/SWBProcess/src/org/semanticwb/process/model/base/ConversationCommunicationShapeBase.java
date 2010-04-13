package org.semanticwb.process.model.base;


public abstract class ConversationCommunicationShapeBase extends org.semanticwb.process.model.FlowNodeShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_ConversationCommunicationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationCommunicationShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConversationCommunicationShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ConversationCommunicationShape> listConversationCommunicationShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationCommunicationShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationCommunicationShape> listConversationCommunicationShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationCommunicationShape>(it, true);
        }

        public static org.semanticwb.process.model.ConversationCommunicationShape getConversationCommunicationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConversationCommunicationShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ConversationCommunicationShape createConversationCommunicationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConversationCommunicationShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeConversationCommunicationShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasConversationCommunicationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getConversationCommunicationShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationCommunicationShape> listConversationCommunicationShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationCommunicationShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConversationCommunicationShape> listConversationCommunicationShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConversationCommunicationShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ConversationCommunicationShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
