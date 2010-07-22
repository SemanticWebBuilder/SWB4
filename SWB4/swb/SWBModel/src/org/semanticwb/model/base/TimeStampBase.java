package org.semanticwb.model.base;


public abstract class TimeStampBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_TimeStamp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TimeStamp");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TimeStamp");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.TimeStamp> listTimeStamps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TimeStamp>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.TimeStamp> listTimeStamps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TimeStamp>(it, true);
        }

        public static org.semanticwb.model.TimeStamp getTimeStamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TimeStamp)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.TimeStamp createTimeStamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TimeStamp)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeTimeStamp(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasTimeStamp(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTimeStamp(id, model)!=null);
        }
    }

    public TimeStampBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
