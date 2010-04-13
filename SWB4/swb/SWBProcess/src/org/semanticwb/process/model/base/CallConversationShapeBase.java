package org.semanticwb.process.model.base;


public abstract class CallConversationShapeBase extends org.semanticwb.process.model.ConversationCommunicationShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_CallConversationShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CallConversationShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CallConversationShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CallConversationShape> listCallConversationShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversationShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversationShape> listCallConversationShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversationShape>(it, true);
        }

        public static org.semanticwb.process.model.CallConversationShape createCallConversationShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CallConversationShape.ClassMgr.createCallConversationShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CallConversationShape getCallConversationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallConversationShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CallConversationShape createCallConversationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallConversationShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCallConversationShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCallConversationShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCallConversationShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversationShape> listCallConversationShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversationShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CallConversationShape> listCallConversationShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallConversationShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CallConversationShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
