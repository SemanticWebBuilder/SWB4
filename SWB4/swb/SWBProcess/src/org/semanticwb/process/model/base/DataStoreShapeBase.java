package org.semanticwb.process.model.base;


public abstract class DataStoreShapeBase extends org.semanticwb.process.model.FlowNodeShape 
{
    public static final org.semanticwb.platform.SemanticClass swp_DataStoreShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStoreShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataStoreShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreShape> listDataStoreShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreShape> listDataStoreShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreShape>(it, true);
        }

        public static org.semanticwb.process.model.DataStoreShape createDataStoreShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DataStoreShape.ClassMgr.createDataStoreShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.DataStoreShape getDataStoreShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStoreShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.DataStoreShape createDataStoreShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DataStoreShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDataStoreShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDataStoreShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDataStoreShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreShape> listDataStoreShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.DataStoreShape> listDataStoreShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataStoreShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DataStoreShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
