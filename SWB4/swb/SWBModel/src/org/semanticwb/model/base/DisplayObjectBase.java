package org.semanticwb.model.base;


public class DisplayObjectBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_dispatcher=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#dispatcher");
    public static final org.semanticwb.platform.SemanticClass swbxf_DisplayObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayObject");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayObject");

    public DisplayObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.DisplayObject getDisplayObject(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.DisplayObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.DisplayObject> listDisplayObjects(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayObject>(org.semanticwb.model.DisplayObject.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.DisplayObject> listDisplayObjects()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayObject>(org.semanticwb.model.DisplayObject.class, it, true);
    }

    public static org.semanticwb.model.DisplayObject createDisplayObject(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.DisplayObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeDisplayObject(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasDisplayObject(String id, org.semanticwb.model.SWBModel model)
    {
        return (getDisplayObject(id, model)!=null);
    }

    public String getDispatcher()
    {
        return getSemanticObject().getProperty(swbxf_dispatcher);
    }

    public void setDispatcher(String dispatcher)
    {
        getSemanticObject().setProperty(swbxf_dispatcher, dispatcher);
    }
}
