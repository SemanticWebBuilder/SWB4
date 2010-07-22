package org.semanticwb.model.base;


public abstract class TimeElementBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_timeConstraints=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#timeConstraints");
    public static final org.semanticwb.platform.SemanticClass swbxf_TimeElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TimeElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TimeElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.TimeElement> listTimeElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TimeElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.TimeElement> listTimeElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TimeElement>(it, true);
        }

        public static org.semanticwb.model.TimeElement getTimeElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TimeElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.TimeElement createTimeElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TimeElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeTimeElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasTimeElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTimeElement(id, model)!=null);
        }
    }

    public TimeElementBase(org.semanticwb.platform.SemanticObject base)
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
