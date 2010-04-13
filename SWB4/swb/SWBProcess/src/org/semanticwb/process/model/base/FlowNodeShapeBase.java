package org.semanticwb.process.model.base;


public abstract class FlowNodeShapeBase extends org.semanticwb.process.model.BPMNShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowNodeShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowNodeShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeShape> listFlowNodeShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeShape> listFlowNodeShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeShape>(it, true);
        }

        public static org.semanticwb.process.model.FlowNodeShape getFlowNodeShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNodeShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.FlowNodeShape createFlowNodeShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FlowNodeShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeFlowNodeShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFlowNodeShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFlowNodeShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeShape> listFlowNodeShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FlowNodeShape> listFlowNodeShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public FlowNodeShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
