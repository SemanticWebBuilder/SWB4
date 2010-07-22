package org.semanticwb.model.base;


public abstract class HtmlElementBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_HtmlElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HtmlElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HtmlElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.HtmlElement> listHtmlElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HtmlElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.HtmlElement> listHtmlElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HtmlElement>(it, true);
        }

        public static org.semanticwb.model.HtmlElement getHtmlElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.HtmlElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.HtmlElement createHtmlElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.HtmlElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeHtmlElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasHtmlElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getHtmlElement(id, model)!=null);
        }
    }

    public HtmlElementBase(org.semanticwb.platform.SemanticObject base)
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
