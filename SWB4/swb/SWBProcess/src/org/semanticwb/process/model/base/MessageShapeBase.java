package org.semanticwb.process.model.base;


public abstract class MessageShapeBase extends org.semanticwb.process.model.FlowNodeShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_MessageShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.MessageShape> listMessageShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageShape> listMessageShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageShape>(it, true);
        }

        public static org.semanticwb.process.model.MessageShape createMessageShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MessageShape.ClassMgr.createMessageShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.MessageShape getMessageShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.MessageShape createMessageShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MessageShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeMessageShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasMessageShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMessageShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageShape> listMessageShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MessageShape> listMessageShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public MessageShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
