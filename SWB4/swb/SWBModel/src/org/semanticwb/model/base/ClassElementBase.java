package org.semanticwb.model.base;


public abstract class ClassElementBase extends org.semanticwb.model.Text 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_ClassElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ClassElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ClassElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.ClassElement> listClassElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ClassElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.ClassElement> listClassElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ClassElement>(it, true);
        }

        public static org.semanticwb.model.ClassElement getClassElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ClassElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.ClassElement createClassElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ClassElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeClassElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasClassElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getClassElement(id, model)!=null);
        }
    }

    public ClassElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
