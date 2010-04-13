package org.semanticwb.process.model.base;


public abstract class DataInputShapeBase extends org.semanticwb.process.model.FlowNodeShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_DataInputShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInputShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataInputShape> listDataInputShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputShape> listDataInputShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputShape>(it, true);
        }

        public static org.semanticwb.process.model.DataInputShape createDataInputShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataInputShape.ClassMgr.createDataInputShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataInputShape getDataInputShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataInputShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataInputShape createDataInputShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataInputShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataInputShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataInputShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataInputShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputShape> listDataInputShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataInputShape> listDataInputShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInputShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataInputShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
