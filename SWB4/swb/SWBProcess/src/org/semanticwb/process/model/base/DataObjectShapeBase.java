package org.semanticwb.process.model.base;


public abstract class DataObjectShapeBase extends org.semanticwb.process.model.FlowNodeShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_DataObjectShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataObjectShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataObjectShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataObjectShape> listDataObjectShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObjectShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataObjectShape> listDataObjectShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObjectShape>(it, true);
        }

        public static org.semanticwb.process.model.DataObjectShape createDataObjectShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataObjectShape.ClassMgr.createDataObjectShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataObjectShape getDataObjectShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataObjectShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataObjectShape createDataObjectShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataObjectShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataObjectShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataObjectShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataObjectShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataObjectShape> listDataObjectShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObjectShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataObjectShape> listDataObjectShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObjectShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataObjectShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
