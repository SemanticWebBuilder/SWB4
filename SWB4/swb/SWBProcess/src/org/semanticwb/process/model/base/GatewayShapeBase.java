package org.semanticwb.process.model.base;


public abstract class GatewayShapeBase extends org.semanticwb.process.model.FlowNodeShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_GatewayShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GatewayShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GatewayShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.GatewayShape> listGatewayShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GatewayShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.GatewayShape> listGatewayShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GatewayShape>(it, true);
        }

        public static org.semanticwb.process.model.GatewayShape createGatewayShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.GatewayShape.ClassMgr.createGatewayShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.GatewayShape getGatewayShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GatewayShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.GatewayShape createGatewayShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GatewayShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeGatewayShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasGatewayShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGatewayShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.GatewayShape> listGatewayShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GatewayShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GatewayShape> listGatewayShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GatewayShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public GatewayShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
